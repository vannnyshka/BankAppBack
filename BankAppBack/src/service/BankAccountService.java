package service;

import entity.Account;
import repository.BankAccountRepository;
import repository.jdbc.JdbcBankAccountRepository;

import java.util.List;

public class BankAccountService {

    private final BankAccountRepository bankAccountRepository;

    public BankAccountService() {
        this.bankAccountRepository = new JdbcBankAccountRepository();
    }

    public void reduceMoney(String id, String money) {
        bankAccountRepository.reduceMoney(id, Double.parseDouble(money));
    }

    public void increaseMoney(String id, String money) {
        bankAccountRepository.increaseMoney(id, Double.parseDouble(money));
    }

    public List<String> getAllAccountsJson() {
        List<Account> accounts = bankAccountRepository.findAll();
        return accounts.stream()
                            .map(Account::convert)
                            .toList();
    }

    public void save (Account account) {
        bankAccountRepository.save(account);
    }


    public void updateStatusByACCNO (String id, String status) {
        bankAccountRepository.updateStatusByACCNO(id, status);
    }
}
