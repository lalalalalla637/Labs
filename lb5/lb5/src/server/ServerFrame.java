package server;

import javax.swing.*;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class ServerFrame extends JFrame {

    private JLabel label;
    private JButton startS,stopS;
    private JTextField numberPort;
    private JTextArea console;
    private Server server;
    private ServerFrame serverFrame;

    public static void main(String[] args) {
        ServerFrame serverFrame=new ServerFrame();
        serverFrame.setTitle("Server");
        serverFrame.setSize(700,400);
        serverFrame.setVisible(true);
    }
    public ServerFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        JPanel northPanel=new JPanel(new FlowLayout());
        northPanel.add(label=new JLabel("Номер порта"));
        northPanel.add(numberPort=new JTextField());
        numberPort.setColumns(5);
        numberPort.setDocument(new PlainDocument(){
            @Override
            public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
                if (numberPort.getText().length()<5)
                    super.insertString(offs, str, a);
            }
        });
        getContentPane().add(northPanel,BorderLayout.NORTH);

        console=new JTextArea();
        JScrollPane scrollPane=new JScrollPane(console);
        getContentPane().add(scrollPane,BorderLayout.CENTER);
        console.setEnabled(false);

        JPanel eastPanel=new JPanel(new FlowLayout());
        eastPanel.add(startS=new JButton("Start Server"));
        eastPanel.add(stopS=new JButton("Stop Server"));
        getContentPane().add(eastPanel,BorderLayout.SOUTH);

        try {
            this.server=new Server(9595,this);
            numberPort.setText(String.valueOf(server.getServersocket().getLocalPort()));
            console.append("Server running IP "+server.getInetAddress().getHostAddress()+" port "+server.getServersocket().getLocalPort()+"\r\n");
        } catch (IOException e) {
            console.append("Не удалось запустить сервер\r\n");
        }
        serverFrame=this;

        startS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(server!=null) {
                        server.offServer();
                        console.append("Сервер отключен\r\n");
                    }
                    server=new Server(Integer.valueOf(numberPort.getText()),serverFrame);
                    console.append("Server running IP "+server.getInetAddress().getHostAddress()+" port "+server.getServersocket().getLocalPort()+"\r\n");
                } catch (IOException e1) {
                    console.append("Не удалось запустить сервер");
                    server=null;
                }
            }
        });
        stopS.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(server!=null) {
                    try {
                        server.offServer();
                        server=null;
                        console.append("Сервер отключен\r\n");
                    } catch (IOException e1) {
                        console.append("Не удалось остановить сервер");
                    }
                }
            }
        });
    }

    public void showString(String str){
        console.append(str);
    }

}
