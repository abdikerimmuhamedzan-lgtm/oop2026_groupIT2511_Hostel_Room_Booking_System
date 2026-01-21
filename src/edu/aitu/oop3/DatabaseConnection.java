package edu.aitu.oop3;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection implements IDB {

    @Override
    public Connection getConnection() throws SQLException, ClassNotFoundException {
        Properties props = new Properties();

        try (FileInputStream input = new FileInputStream("config.properties")) {
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Ошибка: не найден файл config.properties", e);
        }

        String connectionUrl = props.getProperty("db.url");
        String user = props.getProperty("db.user");
        String password = props.getProperty("db.password");

        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(connectionUrl, user, password);
        } catch (Exception e) {
            System.out.println("Connection failed!");
            throw e;
        }
    }
}