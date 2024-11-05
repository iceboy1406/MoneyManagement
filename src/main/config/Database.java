package config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Connection conn;

    public static Connection getConnection() {
        if (conn == null) {
            try {
                String url = "jdbc:mariadb://localhost:3306/moneymanagement";
                String user = "root";
                String pass = "";

                conn = DriverManager.getConnection(url, user, pass);
            } catch (SQLException e) {
                System.out.println("Error database: " + e.getMessage());
            }
        }
        return conn;
    }

    public static void beginTransaction() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.setAutoCommit(false);
                System.out.println("Transaction Begin.");
            } else {
                System.out.println("Connection not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed start transactions: " + e.getMessage());
        }
    }

    public static void commitTransaction() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.commit();
                conn.setAutoCommit(true);
                System.out.println("Transaction successfully commited.");
            } else {
                System.out.println("Connection not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed commit transaction: " + e.getMessage());
        }
    }

    public static void rollbackTransaction() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.rollback();
                conn.setAutoCommit(true);
                System.out.println("Transaction has been rolled back.");
            } else {
                System.out.println("Connection not found.");
            }
        } catch (SQLException e) {
            System.out.println("Failed rollback transactions: " + e.getMessage());
        }
    }

    public static void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Failed close connection.: " + e.getMessage());
        }
    }
}