package tcpWork;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends Thread{

    private ObjectInputStream is = null;
    private ObjectOutputStream os = null;
    private boolean work = true;
    private MetroCardBank metroCardBank = null;
    private Socket socket = null;

    public ClientHandler(MetroCardBank metroCardBank, Socket socket) {
        this.metroCardBank = metroCardBank;
        this.socket = socket;
        this.work = true;
        try {
            this.is = new ObjectInputStream(socket.getInputStream());
            this.os = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        synchronized (metroCardBank) {
            System.out.println("Client Handler Started for: " + socket);
            while (work) {
                Object object;
                try {
                    object = is.readObject();
                    processOperation(object);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void processOperation(Object object) throws IOException, ClassNotFoundException {
        if (object instanceof StopOperation) {
            finish();
        } else if (object instanceof AddMetroCardOperation) {
            addCard(object);
        } else if (object instanceof AddMoneyOperation) {
            addMoney(object);
        } else if (object instanceof PayMoneyOperation) {
            payMoney(object);
        } else if (object instanceof RemoveCardOperation) {
            removeCard(object);
        } else if (object instanceof ShowBalanceOperation) {
            showBalance(object);
        } else if(object instanceof InfoCardOperation) {
            getInfoCard(object);
        } else {
            error();
        }
    }

    private void finish() throws IOException {
        work = false;
        os.writeObject("Finish Work " + socket);
        os.flush();
    }

    private void addCard(Object object) throws IOException {
        metroCardBank.addCard(((AddMetroCardOperation) object).getMetroCard());
        os.writeObject("Card Added");
        os.flush();
    }

    private void addMoney(Object object) throws IOException {
        AddMoneyOperation addMoneyOperation = (AddMoneyOperation) object;
        boolean result = metroCardBank.addMoney(addMoneyOperation.getSerNum(), addMoneyOperation.getMoney());
        if (result) {
            os.writeObject("Balance Added");
            os.flush();
        } else {
            os.writeObject("Cannot Balance Added");
            os.flush();
        }
    }

    private void payMoney(Object object) throws IOException, ClassNotFoundException {
        PayMoneyOperation payMoneyOperation = (PayMoneyOperation) object;
        boolean result = metroCardBank.getMoney(payMoneyOperation.getSerNum(), payMoneyOperation.getMoney());
        if (result) {
            os.writeObject("Money Payed");
            os.flush();
        } else {
            os.writeObject("Cannot Pay Money");
            os.flush();
        }
    }

    private void removeCard(Object object) throws IOException {
        RemoveCardOperation removeCardOperation = (RemoveCardOperation) object;
        boolean result = metroCardBank.removeCard(removeCardOperation.getSerNum());
        if (result) {
            os.writeObject("Metro Card Successfully Remove: " + removeCardOperation.getSerNum());
            os.flush();
        } else {
            os.writeObject("Cannot Remove Card " + removeCardOperation.getSerNum());
            os.flush();
        }
    }

    private void showBalance(Object object) throws IOException {
        ShowBalanceOperation showBalanceOperation = (ShowBalanceOperation) object;
        int index = metroCardBank.findMetroCard(showBalanceOperation.getSerNum());
        if (index >= 0) {
            os.writeObject("Card: " + showBalanceOperation.getSerNum() + "\tbalance: " + metroCardBank.getStore().get(index).getBalance());
            os.flush();
        } else {
            os.writeObject("Cannot Show Balance for Card: " + showBalanceOperation.getSerNum());
        }
    }

    private void error() throws IOException {
        os.writeObject("Bad Operation");
        os.flush();
    }

    private void getInfoCard(Object object) throws IOException {
        InfoCardOperation infoCardOperation = (InfoCardOperation)object;
        int index = metroCardBank.findMetroCard(infoCardOperation.getSerNum());
        if (index >= 0) {
            os.writeObject("Card: " + metroCardBank.getStore().get(index).toString());
            os.flush();
        } else {
            os.writeObject("Cannot Show Info for Card: " + infoCardOperation.getSerNum());
        }
    }
}
