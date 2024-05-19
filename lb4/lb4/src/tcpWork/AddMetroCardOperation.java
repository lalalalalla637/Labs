package tcpWork;

public class AddMetroCardOperation extends CardOperation{

    private MetroCard metroCard = null;

    public AddMetroCardOperation() {
        metroCard = new MetroCard();
    }

    public MetroCard getMetroCard() {
        return metroCard;
    }

    public void setMetroCard(MetroCard metroCard) {
        this.metroCard = metroCard;
    }

}
