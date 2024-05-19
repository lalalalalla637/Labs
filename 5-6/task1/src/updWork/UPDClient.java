package updWork;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.*;
import java.util.Scanner;

public class UPDClient {

    private Scanner scanner = new Scanner(System.in);
    private ActiveUsers activeUsersList;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private InetAddress serverAddress;
    private int serverPort = -1;
    private static final int answerWaitingTime = 1000;
    private int bufferSize = 256;

    public UPDClient(InetAddress serverAddress, int serverPort){
        activeUsersList = new ActiveUsers();
        this.serverPort = serverPort;
        try{
            this.serverAddress = serverAddress;
            datagramSocket = new DatagramSocket(64344);
            datagramSocket.setSoTimeout(answerWaitingTime);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public void checkServerTurningOff(int normalBufferSize){
        System.out.print("Вы хотите выключить сервер после получения данных? [1 - да, 2 - нет] -> ");
        int choice = scanner.nextInt();

        if (choice == 1)
            bufferSize = 0;
        else
            bufferSize = normalBufferSize;
    }

    public void work(int normalBufferSize){
        byte[] buffer = new byte[normalBufferSize];
        try{
            checkServerTurningOff(normalBufferSize);
            tryingConnectToTheServer(buffer);
            System.out.println("Получаю список пользователей..");
            while(true){
                boolean isAccepted = false;
                while (!isAccepted)
                    try {
                        datagramPacket = new DatagramPacket(buffer, buffer.length);
                        datagramSocket.receive(datagramPacket);
                        isAccepted = true;
                    } catch (SocketTimeoutException e){
                        isAccepted = false;
                        System.out.println("Врем ожидания приема вышло. Выполняется повторный запрос.");
                        tryingConnectToTheServer(buffer);
                    }

                if (datagramPacket.getLength() == 0) {
                    System.out.println("Список пользователей получен!");
                    break;
                }

                ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(datagramPacket.getData(), 0, datagramPacket.getLength()));
                User user = (User) in.readObject();
                activeUsersList.registerUser(user);
                clearBuffer(buffer);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        finally {
            datagramSocket.close();
        }

        System.out.println("\nПользователи в сети: " + activeUsersList.getUsersCount());
        System.out.println(activeUsersList.toString());
        if (bufferSize == 0)
            System.out.println("Сервер выключен!");
    }

    private void tryingConnectToTheServer(byte[] buffer) throws IOException {
        System.out.print("Посылаю запрос серверу... ");
        datagramPacket = new DatagramPacket(buffer, bufferSize, serverAddress, serverPort);
        datagramSocket.send(datagramPacket);
        System.out.println("Запрос отправлен!\n");
    }

    private void clearBuffer(byte[] buffer){
        for (int i = 0; i < buffer.length; i++) {
            buffer[i] = 0;
        }
    }

    public static void main(String[] args) throws Exception {
        UPDClient udpClient = new UPDClient(InetAddress.getLocalHost(), 1502);
        udpClient.work(256);
    }

}
