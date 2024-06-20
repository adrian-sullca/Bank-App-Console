package com.app.dao;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.app.database.DBConnection;
import com.app.models.User;

public class UserDao extends DBConnection implements DAO<User, Integer> {

    private final String INSERT = "INSERT INTO USERS (username, password, dni) VALUES (?, ?, ?)";
    private final String SELECT_BY_ID = "SELECT * FROM USERS WHERE id = ?";
    private final String SELECT_BY_USERNAME = "SELECT * FROM USERS WHERE username = ?";
    private final String SELECT_BY_DNI = "SELECT * FROM USERS WHERE dni = ?";
    private final String SELECT_ALL = "SELECT * FROM USERS";
    
    DBConnection db = new DBConnection();
    
    /**
     * Inserts a new user into the database and returns the generated ID.
     *
     * @param  user  the User object to be inserted
     * @return       the generated ID of the inserted user, or 0 if the insertion failed
     */
    @Override
    public int insertReturnId(User user) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        int generatedId = 0;
        try {
            db.connect();
            ps = db.getConnection().prepareStatement(INSERT, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, user.getUsername());
            String hashedPassword = user.hashPassword(user.getPassword());
            ps.setString(2, hashedPassword);
            ps.setString(3, user.getDni());
            
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                System.out.println("Creating user failed, no rows affected.");
            } else {
                rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    generatedId = rs.getInt(1); // Obtener el ID generado
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
    public void update(User t) {
    }

    @Override
    public void delete(User t) {
    }

    /**
     * Retrieves a User object from the database based on the provided ID.
     *
     * @param  id  the ID of the User to retrieve
     * @return     the User object with the matching ID, or null if not found
     */
    @Override
    public User selectById(Integer id) {
        User user = null;
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_ID);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setDni(rs.getString("dni"));
            }
            rs.close();
            ps.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * Retrieves all User objects from the database.
     *
     * @return  a list of User objects representing all users in the database
     */
    @Override
    public List<User> selectAll() {
        ArrayList<User> users = new ArrayList<>();
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_ALL);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setDni(rs.getString("dni"));
                users.add(user);
            }
            rs.close();
            ps.close();
            db.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
    
    /**
     * Retrieves the ID of a user based on their username.
     * To ensure that the value is unique in the database
     *
     * @param  username the username of the user
     * @return          the ID of the user, or 0 if not found
     */
    public int checkUsername(String username) {
        int id = 0;
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_USERNAME);
            ps.setString(1, username);
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
     * Retrieves the ID of a user based on their DNI number.
     * To ensure that the value is unique in the database
     *
     * @param  dni  the DNI number of the user
     * @return      the ID of the user, or 0 if not found
     */
    public int checkDni(String dni) {
        int id = 0;
        try {
            db.connect();
            PreparedStatement ps = db.getConnection().prepareStatement(SELECT_BY_DNI);
            ps.setString(1, dni);
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
}