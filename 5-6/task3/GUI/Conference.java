package task3.GUI;

import javax.swing.*;
import java.awt.event.*;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class Conference extends JDialog {
    private JPanel contentPane;
    private JButton Button2;
    private JButton Button3;
    private JTextField textField1;
    private JButton Button1;
    private JTextArea textArea1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JButton Button4;
    private JButton Button5;
    private Messanger messanger = null;

    public Conference() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(Button2);
        UITasksImpl uiTasks = new UITasksImpl();

        textField2.setText("224.0.0.1");
        textField3.setText("12345");

        Button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messanger.send();
                /*String txt = textField4.getText() + ": " + uiTasks.getMessage();
                uiTasks.setText(txt);*/
            }
        });

        Button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                UITasks ui = (UITasks) Proxy.newProxyInstance(getClass().getClassLoader(), new Class[]{UITasks.class}, new EDTInvocationHandler(new UITasksImpl()));
                try {
                    InetAddress addr = InetAddress.getByName(textField2.getText());
                    int port = Integer.parseInt(textField3.getText());
                    String name = textField4.getText();
                    messanger = new MessangerImpl(addr, port, name, ui);
                    messanger.start();
                } catch (UnknownHostException unknownHostException) {
                    unknownHostException.printStackTrace();
                }
            }
        });
        Button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                messanger.stop();
            }
        });
        Button4.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                textArea1.setText("");
            }
        });
        Button5.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (messanger != null){
                    messanger.stop();
                }
                System.exit(0);
            }
        });

    }

    public class UITasksImpl implements UITasks{

        @Override
        public String getMessage() {
            String res = textField1.getText();
            textField1.setText("");
            return res;
        }

        @Override
        public void setText(String txt) {
            textArea1.append(txt + "\n");
        }

    }

    public static void main(String[] args) {
        Conference dialog = new Conference();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
