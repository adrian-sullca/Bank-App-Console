package com.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.app.enums.Type;

import com.app.database.DBConnection;
import com.app.models.Transaction;
public class TransactionDao extends DBConnection implements DAO<Transaction, Integer> {
    DBConnection db = new DBConnection();
    private final String INSERT = "INSERT INTO TRANSACTIONS (type, origin_account, target_account, amount) VALUES (?, ?, ?, ?)";
    private final String SELECT_BY_ACCOUNT = "SELECT * FROM TRANSACTIONS WHERE origin_account = ? OR target_account = ?";
    /**
     * A method to insert a transaction and return the generated ID.
     *
     * @param  t         The Transaction object to insert
     * @return          The generated ID of the inserted transaction
     */
    @Override
    public int insertReturnId(Transaction t) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = 0;
        try {
            db.connect();
            ps = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, t.getType().name());
            if (t.getType().name().equals("deposit") || t.getType().name().equals("withdraw")) {
                ps.setString(2, t.getOrigin_account());
                ps.setString(3, null);
            } else{
                ps.setString(2, t.getOrigin_account());
                ps.setString(3, t.getTarget_account());
            }
            ps.setDouble(4, t.getAmount());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Creating transaction failed, no rows affected.");
            } else {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {   
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            db.closeConnection();
        }
        return generatedId;
    }

    @Override
    public void update(Transaction t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }

    @Override
    public void delete(Transaction t) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'delete'");
    }

    @Override
    public Transaction selectById(Integer id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectById'");
    }

    @Override
    public List<Transaction> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    /**
     * Retrieves a list of Transaction objects based on the origin account number.
     *
     * @param  originAccountNumber    the account number of the origin account
     * @return                        a list of Transaction objects
     */
    public List<Transaction> selectByAccount(String originAccountNumber) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Transaction> transactions = new ArrayList<>();
        try {
            db.connect();
            ps = db.getConnection().prepareStatement(SELECT_BY_ACCOUNT);
            ps.setString(1, originAccountNumber);
            ps.setString(2, originAccountNumber);
            rs = ps.executeQuery();
            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setId(rs.getInt("id"));
                transaction.setType(Type.valueOf(rs.getString("type")));
                transaction.setOrigin_account(rs.getString("origin_account"));
                transaction.setTarget_account(rs.getString("target_account"));
                transaction.setAmount(rs.getDouble("amount"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close(); 
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            db.closeConnection();
        }
        return transactions;
    }
}
