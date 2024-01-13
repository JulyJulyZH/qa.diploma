package ru.netology.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {

//    private static String dbUrl = System.getProperty("db.url");
//    private static String dbUser = System.getProperty("db.user");
//    private static String dbPassword = System.getProperty("db.pass");

    private static String dbUrl = "jdbc:postgresql://localhost:5432/app";
//    private static String dbUrl = "jdbc:mysql://localhost:3306/app";

    private static String dbUser = "app";
    private static String dbPassword = "pass";

    public static Connection getConnection() {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public static String getPaymentId() {
        String paymentId = null;
        var idSQL = "SELECT payment_id FROM order_entity order by created desc limit 1;";
        try (var connection = getConnection();
            var statusStmt = connection.prepareStatement(idSQL)) {
            try (var rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    paymentId = rs.getString("payment_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return paymentId;
    }

    public static String getPaymentStatus(final String payment) {
        String statusSQL = "SELECT status FROM payment_entity WHERE transaction_id =?; ";
        String status = null;
        try (var connection = getConnection();
            var statusStmt = connection.prepareStatement(statusSQL)) {
            statusStmt.setString(1, payment);
            try (var rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

    public static String getPaymentAmount(final String payment) {
        String amountSQL = "SELECT amount FROM payment_entity WHERE transaction_id =?; ";
        String amount = null;
        try (var connection = getConnection();
            var statusStmt = connection.prepareStatement(amountSQL)) {
            statusStmt.setString(1, payment);
            try (var rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    amount = rs.getString("amount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return amount;
    }

    public static String getPaymentStatusCredit(String paymentId) {
        String statusSQL = "SELECT status FROM credit_request_entity WHERE bank_id =?; ";
        String status = null;
        try (var conn = getConnection();
            var statusStmt = conn.prepareStatement(statusSQL)) {
            statusStmt.setString(1, paymentId);
            try (var rs = statusStmt.executeQuery()) {
                if (rs.next()) {
                    status = rs.getString("status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return status;
    }

//    public static void dbClean() throws SQLException {
//        String cleanCreditTables = "DELETE FROM credit_request_entity;";
//        String cleanOrderTable = "DELETE FROM order_entity;";
//        String cleanPaymentTable = "DELETE FROM payment_entity;";
//        Connection c = getConnection();
//        Statement s = c.createStatement();
//        s.executeUpdate(cleanCreditTables);
//        s.executeUpdate(cleanOrderTable);
//        s.executeUpdate(cleanPaymentTable);
//        c.close();
//    }
}

