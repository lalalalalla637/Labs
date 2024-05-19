package task2.echoServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UPDEchoServer extends UPDServer{

    public final static int DEFAULT_PORT = 8888;

    public UPDEchoServer() {
        super(DEFAULT_PORT);
    }

    @Override
    public void respond(DatagramSocket socket, DatagramPacket request) throws IOException {

        DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(), request.getAddress(), request.getPort());
        socket.send(reply);

    }

    public static void main(String[] args) {

        UPDServer server = new UPDEchoServer();
        Thread t = new Thread(server);
        t.start();
        System.out.println("Start echo-server...");
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) { e.printStackTrace();
        }
        server.shutDown(); System.out.println("Finish echo-server...");


    }

}
