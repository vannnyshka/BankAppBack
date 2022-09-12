package entity;

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

    public Account(String id, String status, String fname, String lname, String passport, String password) {
        this.id = id;
        this.status = status;
        this.fname = fname;
        this.lname = lname;
        this.passport = passport;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getClino() {
        return clino;
    }

    public void setClino(int clino) {
        this.clino = clino;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
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
