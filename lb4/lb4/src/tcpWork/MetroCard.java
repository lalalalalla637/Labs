package tcpWork;

import java.io.Serializable;

public class MetroCard implements Serializable {

    private String serNum;
    private User user;
    private String college;
    private double balance;

    @Override
    public String toString() {
        return "№: " + serNum +
                "\nUser: " + user +
                "\nCollege: " + college +
                "\nBalance: " + balance;
    }

    public void addMoneyOnBalance(double money){
        balance += money;
    }

    public boolean getMoneyFromBalance(double money){
        if(money <= balance) {
            balance -= money;
            return true;
        }
        return false;
    }

    public String getSerNum() {
        return serNum;
    }

    public String getCollege() {
        return college;
    }

    public double getBalance() {
        return balance;
    }

    public User getUser() {
        return user;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }


}
