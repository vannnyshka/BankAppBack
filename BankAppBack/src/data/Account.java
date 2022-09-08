package data;

public class Account {
    private String id;
    private double money;
    private String status;
    private int clino;
    private String fname;
    private String lname;
    private String passport;
    private String password;

    public Account(String id, double money, String status, int clino, String fname, String lname, String passport, String password) {
        this.id = id;
        this.money = money;
        this.status = status;
        this.clino = clino;
        this.fname = fname;
        this.lname = lname;
        this.passport = passport;
        this.password = password;
    }

    public String convert(){
        StringBuffer sBuffer = new StringBuffer("{");
        sBuffer.append("\"id\": \"" + this.id + "\",");
        sBuffer.append("\"money\": " + String.valueOf(this.money) + ",");
        sBuffer.append("\"status\": \"" + this.status + "\",");
        sBuffer.append("\"clino\": " + String.valueOf(this.clino) + ",");
        sBuffer.append("\"fname\": \"" + this.fname + "\",");
        sBuffer.append("\"lname\": \"" + this.lname + "\",");
        sBuffer.append("\"passport\": \"" + this.passport + "\",");
        sBuffer.append("\"password\": \"" + this.password + "\"");
        sBuffer.append("}");
        return sBuffer.toString();
    }
}
