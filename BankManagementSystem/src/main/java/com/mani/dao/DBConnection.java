package com.mani.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String url = "jdbc:mysql://localhost:3306/bankDataBase";
    private static final String username = "root";
    private static final String password = "Munja@2003";

    // ✅ Make this method public
    public static Connection getConnection() throws SQLException {
        try {
            // Load driver (optional but good practice)
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(url, username, password);
    }
}
