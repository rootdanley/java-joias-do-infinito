package org.joias.projeto.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://danleylab.czys4uquy2ii.us-east-2.rds.amazonaws.com:3306/joias_infinito";
    private static final String USER = "admin";
    private static final String PASSWORD = "13592239759";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}