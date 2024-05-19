package tcpWork;

import java.io.Serializable;
import java.util.ArrayList;

public class MetroCardBank implements Serializable {

    private ArrayList<MetroCard> store;

    public MetroCardBank() {
        store = new ArrayList<>();
    }

    public int findMetroCard(String serNum) {

        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) return i;
        }
        return -1;
    }

    public int numCards() {
        return store.size();
    }

    public void addCard(MetroCard newCard) {
        if (checkOutCard(newCard))
            store.add(newCard);
        else
            System.out.println("Card was added!");
    }

    private boolean checkOutCard(MetroCard metroCard) {
        for (MetroCard m : store) {
            if (m.getSerNum().equals(metroCard.getSerNum())) return false;
        }
        return true;
    }

    public boolean removeCard(String serNum) {
        boolean b = false;
        for (int i = 0; i < store.size(); i++) {
            if (store.get(i).getSerNum().equals(serNum)) {
                store.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean addMoney(String serNum, double money){
        for (MetroCard m : store) {
            if (m.getSerNum().equals(serNum)){
                m.addMoneyOnBalance(money);
                return true;
            }
        }
        return false;
    }

    public boolean getMoney(String serNum, double money){
        for (MetroCard m : store) {
            if (m.getSerNum().equals(serNum)){
                return m.getMoneyFromBalance(money);
            }
        }
        return false;
    }

    public ArrayList<MetroCard> getStore() {
        return store;
    }

    @Override
    public String toString() {
        StringBuilder buf = new StringBuilder("List of MetroCards: ");
        for(MetroCard m:store){
            buf.append("\n\n" + m.toString());
        }
        return super.toString();
    }

}
