package ru.netology.data;

import lombok.SneakyThrows;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhelper {
    private static String url = System.getProperty("url");
    private static String username = System.getProperty("username");
    private static String password = System.getProperty("password");

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                url, username, password);
    }

    @SneakyThrows
    public static void cleanDB() {
        String delTableCredit = "DELETE FROM credit_request_entity;";
        String delTableOrder = "DELETE FROM order_entity;";
        String delTablePayment = "DELETE FROM payment_entity;";
        QueryRunner runner = new QueryRunner();
        Connection conn = getConnection();
        runner.update(conn, delTableCredit);
        runner.update(conn, delTableOrder);
        runner.update(conn, delTablePayment);
    }

    @SneakyThrows
    public static String getStatus(String table) {
        String sql = "SELECT status FROM " + table;
        QueryRunner runner = new QueryRunner();
        String status = null;
        Connection conn = getConnection();
        status = runner.query(conn, sql, new ScalarHandler<>());
        return status;
    }

    public static String getPaymentStatus() {
        return getStatus("payment_entity");
    }

    public static String getCreditStatus() {
        return getStatus("credit_request_entity");
    }

    @SneakyThrows
    public static String getPaymentAmount() {
        String sql = "SELECT amount FROM payment_entity";
        QueryRunner runner = new QueryRunner();
        String amount = null;
        Connection conn = getConnection();
        amount = Integer.toString(runner.query(conn, sql, new ScalarHandler<>()));
        return amount;
    }
}
