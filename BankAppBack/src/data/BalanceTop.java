package data;

public class BalanceTop{
    private String massage;

    public BalanceTop(String massage){
        this.massage = massage;
    }

    public String convert(){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"massage\": \"" + this.massage + "\"}");
        return sBuffer.toString();
    }
}
