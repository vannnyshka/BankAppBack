package data;

public class Transaction {
    public Transaction(int id, String bankaccno, String trancname, String date, String time) {
        this.id = id;
        this.bankaccno = bankaccno;
        this.trancname = trancname;
        this.date = date;
        this.time = time;
    }

    private int id;
    private String bankaccno;
    private String trancname;
    private String date;
    private String time;

    public String convert(){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"id\": " + String.valueOf(this.id) + ",");
        sBuffer.append("\"bankaccno\": \"" + this.bankaccno + "\",");
        sBuffer.append("\"trancname\": \"" + this.trancname + "\",");
        sBuffer.append("\"date\": \"" + this.date + "\",");
        sBuffer.append("\"time\": \"" + this.time + "\"");
        sBuffer.append("}");
        return sBuffer.toString();
    }
}
