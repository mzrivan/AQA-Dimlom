package ru.netology.data;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBhelper {
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/app", "app", "pass");
                    //"jdbc:postgresql://localhost:5432/app", "app", "pass");
    }

    public static void cleanDB() {
        var delTableCredit = "DELETE FROM credit_request_entity;";
        var delTableOrder = "DELETE FROM order_entity;";
        var delTablePayment = "DELETE FROM payment_entity;";
        var runner = new QueryRunner();
        try (Connection conn = getConnection()) {
            runner.update(conn, delTableCredit);
            runner.update(conn, delTableOrder);
            runner.update(conn, delTablePayment);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static String getStatus(String table) {
        var sql = "SELECT status FROM "+table;
        var runner = new QueryRunner();
        String status = null;
        try (Connection conn = getConnection()) {
            status = runner.query(conn, sql, new ScalarHandler<>());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return status;
    }

    public static String getPaymentStatus() {
        return getStatus("payment_entity");
    }
    public static String getCreditStatus() {
        return getStatus("credit_request_entity");
    }
    public static String getPaymentAmount() {
        var sql = "SELECT amount FROM payment_entity";
        var runner = new QueryRunner();
        String amount = null;
        try (Connection conn = getConnection()) {
            amount = Integer.toString(runner.query(conn, sql, new ScalarHandler<>()));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return amount;
    }

}
