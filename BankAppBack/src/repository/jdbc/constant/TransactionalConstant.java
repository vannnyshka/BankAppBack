package repository.jdbc.constant;

public interface TransactionalConstant {

    String SAVE = "INSERT INTO TRANSACTION (BANK_ACCNO, TRANC_NAME, TRANC_DATE, "
            + "TRANC_TIME) VALUES (?,?, CURRENT_DATE(), CURRENT_TIME())";

    String FIND_ALL = "SELECT * FROM  TRANSACTION";
}
