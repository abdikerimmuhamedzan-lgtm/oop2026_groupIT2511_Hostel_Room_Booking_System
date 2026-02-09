package edu.aitu.oop3.Common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PostgresDB implements IDB {
    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties props = new Properties();
        String connectionUrl = "";
        String user = "";
        String pass = "";
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            connectionUrl = props.getProperty("db.url");
            user = props.getProperty("db.user");
            pass = props.getProperty("db.password");
        } catch (IOException e) {
            System.out.println("Error loading config.properties: " + e.getMessage());
            return null;
        }
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, user, pass);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}