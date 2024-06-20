package com.app.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.app.database.DBConnection;

public class UserSession {
    private static User sesion;
    public static User getSesion() {
        return sesion;
    }

    public static void setSesion(User user) {
        sesion = user;
    }

    DBConnection db = new DBConnection();
    public User validateUser(String username, String password) {
        db.connect();
        Connection connection = db.getConnection();
        User user = null;
        User usuario = new User();
        try {
            PreparedStatement ps = connection.prepareStatement("SELECT * FROM USERS WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, usuario.hashPassword(password));
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int id = rs.getInt("id");
                String usernameUser = rs.getString("username");
                String passwordUser = rs.getString("password");
                user = new User(id,usernameUser, passwordUser);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.closeConnection();
        }
        return user;
    }
}
