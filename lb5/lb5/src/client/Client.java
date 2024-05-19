package client;

import interfaces.Result;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        Socket client=null;
        try {
            client=new Socket(InetAddress.getLocalHost(),9595);
            ObjectOutputStream oos=new ObjectOutputStream(client.getOutputStream());
            String nameClassFile="D:\\doc\\moskalec\\lb9\\src\\client\\JobOne.class";
            oos.writeObject(nameClassFile);
            FileInputStream fis=new FileInputStream(nameClassFile);
            byte[] b=new byte[fis.available()];
            fis.read(b);
            oos.writeObject(b);

            JobOne jobOne=new JobOne(5);
            oos.writeObject(jobOne);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            ObjectInputStream ois=new ObjectInputStream(client.getInputStream());
            String nameClasssFile=(String) ois.readObject();
            nameClasssFile=nameClasssFile.replaceFirst("server","client");
            FileOutputStream fos=new FileOutputStream(nameClasssFile);
            byte[] b=(byte[]) ois.readObject();
            fos.write(b);

            Result result=(Result) ois.readObject();
            System.out.println("Result"+result.output());
            System.out.println("Time"+result.scoreTime()+"ns");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
