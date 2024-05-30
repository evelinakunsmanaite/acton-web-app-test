package com.webTest.init;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class DatabaseConnection {
    private static final Logger logger = Logger.getLogger(DatabaseConnection.class.getName());
    private static final String URL = "jdbc:postgresql://localhost:5432/databasecourse?useUnicode=true&characterEncoding=UTF-8";
    private static final String USER = "userpg";
    private static final String PASSWORD = System.getenv("evelina2002");


    public Connection connect() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            logger.info("Database connected successfully");
        } catch (SQLException ex) {
            logger.severe("Connection exception: " + ex.getMessage());
        }
        return connection;
    }
}
