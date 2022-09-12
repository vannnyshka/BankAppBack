package repository.jdbc;

import entity.Transaction;
import repository.TransactionalRepository;
import repository.config.JdbcConfig;
import repository.jdbc.constant.TransactionalConstant;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTransactionalRepository implements TransactionalRepository {

    @Override
    public void save(String bank_account, String message) {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (PreparedStatement ps = connection.prepareStatement(TransactionalConstant.SAVE)) {
                ps.setString(1, bank_account);
                ps.setString(2, message);
                ps.execute();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String findAllToString() {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(TransactionalConstant.FIND_ALL)) {
                return getAllToString(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        try {
            Connection connection = JdbcConfig.getConnection();

            try (Statement statement = connection.createStatement();
                 ResultSet rs = statement.executeQuery(TransactionalConstant.FIND_ALL)) {
                return findAll(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    private List<Transaction> findAll(ResultSet rs) throws SQLException {
        List<Transaction> transactions = new ArrayList<>();
        while (rs.next()) {
            transactions.add(new Transaction(rs.getInt(1), rs.getString(2), rs.getString(3),
                    String.valueOf(rs.getDate(4)), String.valueOf(rs.getTime(5))););
        }
        return transactions;
    }

    private String getAllToString(ResultSet rs) throws SQLException {
        String trancs = "";
        while (rs.next()) {
            trancs += " " + Double.toString(rs.getInt(1)) + " " + rs.getString(2) + " " + rs.getString(3) + "  " +
                    rs.getDate(4) + " " + rs.getTime(5) + "\n";
        }
        return trancs;
    }

}
