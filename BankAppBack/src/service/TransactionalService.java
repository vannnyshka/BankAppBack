package service;

import entity.Transaction;
import repository.TransactionalRepository;
import repository.jdbc.JdbcTransactionalRepository;

import java.util.List;

public class TransactionalService {

    private final TransactionalRepository transactionalRepository;

    public TransactionalService() {
        transactionalRepository = new JdbcTransactionalRepository();
    }


    public void save (String bank_account, String message) {
        transactionalRepository.save(bank_account, message);
    }

    public List<String> findAll() {
        List<Transaction> accounts = transactionalRepository.findAll();
        return accounts.stream()
                            .map(Transaction::convert)
                            .toList();
    }

    public String findAllToString() {
        return  transactionalRepository.findAllToString();

    }
}
