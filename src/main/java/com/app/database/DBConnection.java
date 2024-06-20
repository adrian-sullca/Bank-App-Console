package com.app.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    public Connection connection;

    public DBConnection() {
    }

    public void connect() {
        Properties props = new Properties();
        try (FileInputStream fis = new FileInputStream("src/main/java/com/app/database/database.properties")) {
            props.load(fis);
    
            String url = props.getProperty("db.url");
            String user = props.getProperty("db.user");
            String password = props.getProperty("db.password");
    
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            System.out.println("Failed to connect to database " + ex.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ex) {
                System.out.println("Failed to close the connection " + ex.getMessage());
            }
        }
    }
}
