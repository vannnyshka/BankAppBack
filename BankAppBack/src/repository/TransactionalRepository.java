package repository;

import entity.Transaction;

import java.util.List;

public interface TransactionalRepository {

    void save (String bank_account, String message);

    String findAllToString();

    List<Transaction> findAll();
}
