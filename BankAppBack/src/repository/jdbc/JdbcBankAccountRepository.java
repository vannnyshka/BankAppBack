package repository.jdbc;

import entity.Account;
import repository.BankAccountRepository;
import repository.config.JdbcConfig;
import repository.jdbc.constant.BankAccountConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcBankAccountRepository implements BankAccountRepository {


    @Override
    public void save(Account account) {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(BankAccountConstant.SAVE)) {
                ps.setString(1, account.getId());
                ps.setDouble(2, 0);
                ps.setString(3, account.getStatus().toUpperCase());
                ps.setString(4, account.getFname().toUpperCase());
                ps.setString(5, account.getLname().toUpperCase());
                ps.setString(6, account.getPassport().toUpperCase());
                ps.setString(7, account.getPassword());
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void reduceMoney(String id, double money) {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(BankAccountConstant.REDUCE_MONEY)) {
                ps.setDouble(1, money);
                ps.setString(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void increaseMoney(String id, double money) {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(BankAccountConstant.INCREASE_MONEY)) {
                ps.setDouble(1, money);
                ps.setString(2, id);
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void updateStatusByACCNO(String id, String status) {
        try {
            Connection connection = JdbcConfig.getConnection();


            try (PreparedStatement ps = connection.prepareStatement(BankAccountConstant.UPDATE_STATUS_BY_ACCNO)) {
                ps.setString(1, status);
                ps.setString(2, id);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    @Override
    public List<Account> findAll() {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(BankAccountConstant.FIND_ALL)) {
                return getAllAccounts(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Account> getAllAccounts(ResultSet rs) throws SQLException {
        List<Account> accounts = new ArrayList<>();
        while (rs.next()) {
            accounts.add(new Account(rs.getString(1), rs.getDouble(2),
                    rs.getString(3), rs.getInt(4), rs.getString(5),
                    rs.getString(6), rs.getString(7), rs.getString(8));
        }
        return accounts;
    }
}
