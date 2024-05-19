package server;

import interfaces.Executable;
import interfaces.Result;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    private Thread reciveCinnecttThread;
    private ServerSocket serversocket;
    public volatile static ArrayList<ServerConnection> serverConnections;
    private InetAddress inetAddress;
    private boolean isReciving;
    private ServerFrame frame;

    public Server(int port) throws IOException {
        serversocket = new ServerSocket(port,0,this.inetAddress=InetAddress.getLocalHost());
        serverConnections=new ArrayList<ServerConnection>();
        runRecivingConnect();
    }
    public Server(int port,ServerFrame frame) throws IOException {
        this(port);
        this.frame=frame;
    }
    public ServerSocket getServersocket() {
        return serversocket;
    }

    public ServerFrame getFrame() {
        return frame;
    }

    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        ServerSocket serversocket = null;
        try {
            serversocket = new ServerSocket(9595,0,InetAddress.getLocalHost());
            System.out.println("Сервер запущен "+serversocket.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        ObjectInputStream ois=null;
        Socket connectSocket=null;
        try {
            connectSocket=serversocket.accept();
            ois=new ObjectInputStream(connectSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Executable executable=null;
        try {
            String classFile=(String) ois.readObject();
            classFile=classFile.replaceFirst("client","server");
            byte[] b=(byte[])ois.readObject();
            FileOutputStream fos=new FileOutputStream(classFile);
            fos.write(b);

            executable=(Executable) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        long time=System.nanoTime();
        Object res=executable.execute();
        System.out.println(executable.execute());
        time=System.nanoTime()-time;

        try {
            String classFile="D:\\doc\\moskalec\\lb9\\src\\server\\ResultImpl.class";
            ObjectOutputStream oos=new ObjectOutputStream(connectSocket.getOutputStream());
            oos.writeObject(classFile);
            FileInputStream fis=new FileInputStream(classFile);
            byte[] b=new byte[fis.available()];
            fis.read(b);
            oos.writeObject(b);
            ResultImpl result=new ResultImpl(res,time);
            oos.writeObject(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runRecivingConnect(){
        reciveCinnecttThread=new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        if(serversocket.isClosed()) {return;}
                        serverConnections.add(new ServerConnection(Server.this,serversocket.accept()));
                        frame.showString("Новое подключение адресс "+serverConnections.get(serverConnections.size()-1).getSocket().getLocalAddress()+"\r\n");
                    } catch (IOException e) {
                        continue;
                    }
                    (new Thread(serverConnections.get(serverConnections.size() - 1))).start();

                }
            }
        });
        reciveCinnecttThread.start();
        isReciving=true;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public Result doJob(Executable executable){
        ResultImpl res=null;
        long time=System.nanoTime();
        res=new ResultImpl(executable.execute(),System.nanoTime()-time);
        return res;
    }

    public void offServer() throws IOException{
        this.stopReciving();
        for(ServerConnection sc:serverConnections)
            sc.offServerConnection();
        serverConnections.clear();
    }

    private void stopReciving(){
        try {
            serversocket.close();
        } catch (IOException e) {
            e.printStackTrace();
            frame.showString("Прием не удалось остановить\r\n");
        }
    }

    public void offConnection(ServerConnection serverConnection){
        serverConnections.remove(serverConnection);
        frame.showString("Отключился "+serverConnection.getSocket().getLocalAddress()+"\r\n");
    }

}
