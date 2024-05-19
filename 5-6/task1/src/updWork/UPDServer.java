package updWork;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UPDServer {

    private ActiveUsers activeUsersList;
    private DatagramSocket datagramSocket;
    private DatagramPacket datagramPacket;
    private InetAddress address;
    private int userPort = -1;
    private boolean isServerWork = true;

    public UPDServer(int serverPort){
        try {
            this.datagramSocket = new DatagramSocket(serverPort);
        } catch (SocketException e) {
            e.printStackTrace();
        }
        this.activeUsersList = new ActiveUsers();
    }

    public void startServer(int bufferSize){
        try{
            System.out.println("Сервер запущен...\n");

            while (isServerWork) {
                getUserData(bufferSize);
                sendUserData();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Сервер остановлен... ");
            datagramSocket.close();
        }
    }

    public void getUserData(int bufferSize) throws IOException{
        byte[] buffer = new byte[bufferSize];
        datagramPacket = new DatagramPacket(buffer, buffer.length);

        System.out.print("Жду новго подключеия... ");
        datagramSocket.receive(datagramPacket);
        System.out.println("Пользователь подключен!");

        if (datagramPacket.getLength() == 0) {
            System.out.println("От пользователя пришла команда выключения сервера.");
            isServerWork = false;
        } else
            isServerWork = true;

        address = datagramPacket.getAddress();
        userPort = datagramPacket.getPort();
        User user = new User(address, userPort);

        System.out.print("Регистрирую пользователя... ");
        if (activeUsersList.isEmpty() || !activeUsersList.isUserRegistered(user)) {
            activeUsersList.registerUser(user);
            System.out.println(getUserInfo(user) + " зарегестрирован!");
        }
        else
            System.out.println(getUserInfo(user) + " уже есть в базе!");

        clearBuffer(buffer);
    }

    private void clearBuffer(byte[] buffer){
        for (int i = 0; i < buffer.length; i++)
            buffer[i] = 0;
    }

    private String getUserInfo(User user) {
        return  "Пользователь " + user.getAddress().getHostAddress() + " с портом: " + user.getPort();
    }

    private void sendUserData() throws IOException{
        byte[] buffer;
        System.out.print("Посилаю ответ...");
        for (int i = 0; i < activeUsersList.getUsersCount(); i++) {
            ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteOut);
            out.writeObject(activeUsersList.getUser(i));
            buffer = byteOut.toByteArray();
            datagramPacket = new DatagramPacket(buffer, buffer.length, address, userPort);
            datagramSocket.send(datagramPacket);
        }

        buffer = "end".getBytes();
        datagramPacket = new DatagramPacket(buffer, 0, address, userPort);
        datagramSocket.send(datagramPacket);
        System.out.println(" Ответ отправлен!\n");
    }

    public static void main(String[] args) throws Exception{
        UPDServer updServer = new UPDServer(1502);
        updServer.startServer(256);
    }

}
