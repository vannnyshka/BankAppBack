package repository;

import entity.Account;

import java.util.List;

public interface BankAccountRepository {

    void save (Account account);

    void reduceMoney(String id, double money);
    void increaseMoney(String id, double money);

    void updateStatusByACCNO(String id, String status);

    List<Account> findAll();
}
