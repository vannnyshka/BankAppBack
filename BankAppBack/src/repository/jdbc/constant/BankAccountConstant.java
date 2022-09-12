package repository.jdbc.constant;

public interface BankAccountConstant {

    String SAVE = "INSERT INTO BANK_ACCOUNTS (BANK_ACCNO, BANK_ACC, STATUS, "
            + "FNAME, LNAME, PASS_ID, PASSWORD) VALUES (?,?,?,?,?,?,?)";
    String REDUCE_MONEY = "UPDATE BANK_ACCOUNTS SET BANK_ACC = BANK_ACC - ?"
            + "WHERE BANK_ACCNO = ?";

    String INCREASE_MONEY = "UPDATE BANK_ACCOUNTS SET BANK_ACC = BANK_ACC + ?"
            + "WHERE BANK_ACCNO = ?";

    String UPDATE_STATUS_BY_ACCNO = "UPDATE BANK_ACCOUNTS SET STATUS = ? "
            + "WHERE BANK_ACCNO = ?";

    String FIND_ALL = "SELECT BANK_ACCNO, BANK_ACC, STATUS, CLINO, "
            + "SUBSTR(FNAME,1,8), SUBSTR(LNAME,1,10), PASS_ID, PASSWORD FROM BANK_ACCOUNTS ORDER BY CLINO";
}
