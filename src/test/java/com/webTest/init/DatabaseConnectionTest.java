package com.webTest.init;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.lifecycle.Startable;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Testcontainers
public class DatabaseConnectionTest {

@Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:14.5")
            .withDatabaseName("testdb")
            .withUsername("test")
            .withPassword("test");

    private static DatabaseConnection databaseConnection;

    @BeforeAll
    public static void setUp() {
        postgreSQLContainer.start();
        databaseConnection = new DatabaseConnection() {
            @Override
            public Connection connect() {
                try {
                    return DriverManager.getConnection(
                            postgreSQLContainer.getJdbcUrl(),
                            postgreSQLContainer.getUsername(),
                            postgreSQLContainer.getPassword()
                    );
                } catch (SQLException e) {
                    throw new RuntimeException("Failed to create connection", e);
                }
            }
        };
    }

    @AfterAll
    public static void tearDown() {
        postgreSQLContainer.stop();
    }

    @Test
    void testConnect() {
        Connection connection = databaseConnection.connect();
        assertNotNull(connection, "Connection should not be null");
        try {
            assertTrue(connection.isValid(2), "Connection should be valid");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
