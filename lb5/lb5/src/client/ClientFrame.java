package client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;

public class ClientFrame extends JFrame {

    private Client client;
    private JLabel label1,label2,label3;
    private JTextField field1,field2,field3;
    private JTextArea console;
    private JButton calculate,clear,connectToServer;
    private ClientConnection clientConnection;
    private String oldAddr;
    private ClientFrame clientFrame;

    public static void main(String[] args) {
        ClientFrame clientFrame=new ClientFrame();
        clientFrame.setTitle("Server");
        clientFrame.setSize(700,400);
        clientFrame.setVisible(true);
    }

    public ClientFrame(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());
        oldAddr="";

        JPanel panel=new JPanel(new FlowLayout());
        panel.add(label1=new JLabel("IP Address"));
        panel.add(field1=new JTextField());
        field1.setColumns(15);
        panel.add(label2=new JLabel("Port"));
        panel.add(field2=new JTextField(5));
        panel.add(label3=new JLabel("№"));
        panel.add(field3=new JTextField(5));
        getContentPane().add(panel,BorderLayout.NORTH);

        console=new JTextArea();
        JScrollPane scrollPane=new JScrollPane(console);
        getContentPane().add(scrollPane,BorderLayout.CENTER);

        panel=new JPanel(new FlowLayout());
        panel.add(calculate=new JButton("Calculate"));
        panel.add(clear=new JButton("Clear text"));
        panel.add(connectToServer=new JButton("Connect"));
        getContentPane().add(panel,BorderLayout.SOUTH);

        field1.setText("192.168.0.104");
        field2.setText("9595");

        clientFrame=this;

        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                console.setText("");
            }
        });

        connectToServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field1.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Поле адреса не может быть пустым","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (field2.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Поле порта не может быть пустым","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    Integer.valueOf(field2.getText());
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Некорректные значения","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }


                if (clientConnection!=null) {
                    try {
                        clientConnection.offConnect();
                    } catch (IOException e1) {
                        console.append("Не удалось откл старое соединение\r\n");
                        return;
                    }
                }
                try {
                    clientConnection = new ClientConnection(InetAddress.getByName(field1.getText()), Integer.valueOf(field2.getText()),clientFrame);
                    (new Thread(clientConnection)).start();
                    console.append("Соединение создано\r\n");
                } catch (IOException e1) {
                    console.append("Не удалось подключится\r\n");
                    clientConnection=null;
                }

            }
        });

        calculate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (field1.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Поле адреса не может быть пустым","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (field2.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Поле порта не может быть пустым","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (field3.getText().equals("")){
                    JOptionPane.showMessageDialog(null,"Поле значения не может быть пустым","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                try{
                    Integer.valueOf(field3.getText());
                    Integer.valueOf(field2.getText());
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(null,"Некорректные значения","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (Integer.valueOf(field3.getText())<0){
                    JOptionPane.showMessageDialog(null,"Поле значения не может быть отрицательным","Error",JOptionPane.WARNING_MESSAGE);
                    return;
                }


                if (!oldAddr.equals(field1.getText()+field2.getText())||clientConnection==null) {
                    try {
                        oldAddr=field1.getText()+field2.getText();
                        if (clientConnection!=null) clientConnection.offConnect();
                        clientConnection = new ClientConnection(InetAddress.getByName(field1.getText()), Integer.valueOf(field2.getText()),clientFrame);
                        (new Thread(clientConnection)).start();
                        console.append("Соединение создано\r\n");
                    } catch (IOException e1) {
                        console.append("Не удалось подключится\r\n");
                        clientConnection=null;
                        return;
                    }
                }

                try {
                    clientConnection.sendJob(new JobOne(Integer.valueOf(field3.getText())));
                    console.append("Задание отправлено\r\n");
                } catch (IOException e1) {
                    console.append("Не удалось отправить задание\r\n");
                }
            }
        });
    }
    public void showString(String str){
        console.append(str);
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }

    public void setClientConnection(ClientConnection clientConnection) {
        this.clientConnection = clientConnection;
    }

}
