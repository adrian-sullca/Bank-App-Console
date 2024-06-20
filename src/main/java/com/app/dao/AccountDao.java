package com.app.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.database.DBConnection;
import com.app.models.Account;

public class AccountDao extends DBConnection implements DAO<Account, Integer> {

    private final String INSERT = "INSERT INTO ACCOUNTS (user_id, balance, account_number) VALUES (?, ?, ?)";
    private final String UPDATE = "UPDATE ACCOUNTS SET balance = ? WHERE id = ?";
    private final String DELETE = "DELETE FROM ACCOUNTS WHERE id = ?";
    private final String SELECT_BY_ID = "SELECT * FROM ACCOUNTS WHERE id = ?";
    private final String SELECT_BY_NUMBER = "SELECT * FROM ACCOUNTS WHERE account_number = ?";
    private final String SELECT_BY_USER_ID = "SELECT * FROM ACCOUNTS WHERE user_id = ?";

    DBConnection db = new DBConnection();

    /**
     * A method to insert an account and return the generated ID.
     *
     * @param  t         The Account object to insert
     * @return          The generated ID of the inserted account
     */
    @Override
    public int insertReturnId(Account t) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = 0;
        try {
            db.connect();
            ps = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setInt(1, t.getIdUser());
            ps.setDouble(2, t.getBalance());
            ps.setString(3, t.getAccount_number());
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Creating account failed, no rows affected.");
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

    /**
     * A method to update an account in the database.
     *
     * @param  t         The Account object to update
     */
    @Override
    public void update(Account t) {
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(UPDATE);
            ps.setDouble(1, t.getBalance());
            ps.setInt(2, t.getId());
            ps.executeUpdate();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Deletes an account from the database.
     *
     * @param  t         The Account object to delete
     */
    @Override
    public void delete(Account t) {
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(DELETE);
            ps.setInt(1, t.getId());
            ps.executeUpdate();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves an Account object from the database based on its ID.
     *
     * @param  id        The ID of the Account to retrieve
     * @return           The retrieved Account object, or null if not found
     */
    @Override
    public Account selectById(Integer id) {
        Account account = null;
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setIdUser(rs.getInt("user_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setAccount_number(rs.getString("account_number"));
                account.setCreated(rs.getTimestamp("created"));
            }
            rs.close();
            ps.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return account;
    }

    @Override
    public List<Account> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    /**
     * A method to check the account based on the provided number.
     *
     * @param  number   The account number to check
     * @return          The ID of the account if found, 0 otherwise
     */
    public int checkAccount(String number) {
        int id = 0;
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_NUMBER);
            ps.setString(1, number);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                id = rs.getInt("id");
            }
            rs.close();
            ps.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Retrieves a list of Account objects from the database based on the provided user ID.
     *
     * @param  id    The user ID to retrieve Account objects for
     * @return       A list of Account objects, or an empty list if none found
     */
    public List<Account> selectByUserId(int id) {
        List<Account> accounts = new ArrayList<>();
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_USER_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setIdUser(rs.getInt("user_id"));
                account.setBalance(rs.getDouble("balance"));
                account.setAccount_number(rs.getString("account_number"));
                account.setCreated(rs.getTimestamp("created"));
                accounts.add(account);
            }
            rs.close();
            ps.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();    
        }
        return accounts;
    }
}
