package tcpWork;

public class InfoCardOperation extends CardOperation{

    private String serNum;


    public InfoCardOperation(String serNum){
        this.serNum = serNum;
    }

    public InfoCardOperation(){

    }

    public String getSerNum(){
        return serNum;
    }

    public void setSerNum(String serNum) {
        this.serNum = serNum;
    }

}
