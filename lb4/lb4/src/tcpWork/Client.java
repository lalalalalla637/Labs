package tcpWork;

import javax.swing.text.JTextComponent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client extends JTextComponent implements Serializable {

    private int port = -1;
    private String server = null;
    private Socket socket = null;
    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;

    public Client(String server, int port){
        this.port = port;
        this.server = server;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(server, port), 10000);
            os = new ObjectOutputStream(socket.getOutputStream());
            is = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void finish(){
        try {
            os.writeObject(new StopOperation());
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void applyOperation(CardOperation op){
        try {
            os.writeObject(op);
            os.flush();
            System.out.println(is.readObject());
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("localhost", 7891);
        AddMetroCardOperation op = new AddMetroCardOperation();
        op.getMetroCard().setUser(new User("Petr", "Petrov", "M", "25.12.1968"));
        op.getMetroCard().setSerNum("00001");
        op.getMetroCard().setCollege("KhNU");
        op.getMetroCard().setBalance(25);
        client.applyOperation(op);
        client.finish();

        client = new Client("localhost", 7891);
        client.applyOperation(new AddMoneyOperation("00001", 100));
        client.applyOperation(new ShowBalanceOperation("00001"));
        client.finish();
    }

}
