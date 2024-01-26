package ru.netology.data;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQL {
    @Getter
    private static Connection connection;

    static {
        try {
            String dbUrl = System.getProperty("db.url");
            String dbUser = System.getProperty("db.user");
            String dbPassword = System.getProperty("db.pass");
            connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static String getPaymentId() {
        String paymentId = null;
        var idSQL = "SELECT payment_id FROM order_entity order by created desc limit 1;";
        try (var statusStmt = getConnection().prepareStatement(idSQL)) {
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
        try (var statusStmt = getConnection().prepareStatement(statusSQL)) {
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
        try (var statusStmt = getConnection().prepareStatement(amountSQL)) {
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
        try (var statusStmt = getConnection().prepareStatement(statusSQL)) {
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

}

