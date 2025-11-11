package com.bank.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import com.mani.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DBConnection.getConnection();

            // 1️⃣ Insert user
            String sqlUser = "INSERT INTO users(username, password, full_name) VALUES (?, ?, ?)";
            PreparedStatement psUser = con.prepareStatement(sqlUser, PreparedStatement.RETURN_GENERATED_KEYS);
            psUser.setString(1, username);
            psUser.setString(2, password);
            psUser.setString(3, fullname);
            psUser.executeUpdate();

            // Get generated user_id
            ResultSet rs = psUser.getGeneratedKeys();
            rs.next();
            int userId = rs.getInt(1);

            // 2️⃣ Generate unique account number
            long accountNumber = generateAccountNumber(con);

            // 3️⃣ Insert account linked with user_id
            String sqlAcc = "INSERT INTO accounts(user_id, balance, account_number) VALUES (?, ?, ?)";
            PreparedStatement psAcc = con.prepareStatement(sqlAcc);
            psAcc.setInt(1, userId);
            psAcc.setDouble(2, 0.0); // default balance
            psAcc.setLong(3, accountNumber);
            psAcc.executeUpdate();

            con.close();
            response.sendRedirect("login.jsp?success=Registration successful");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("register.jsp?error=Username already exists or DB error!");
        }
    }

    // 🔹 Generate unique 10-digit account number
    private long generateAccountNumber(Connection con) throws Exception {
        long accountNumber;
        boolean exists;

        do {
            long base = 1000000000L;
            long random = (long) (Math.random() * 9000000000L);
            accountNumber = base + random;

            String checkSql = "SELECT COUNT(*) FROM accounts WHERE account_number = ?";
            try (PreparedStatement ps = con.prepareStatement(checkSql)) {
                ps.setLong(1, accountNumber);
                try (ResultSet rs = ps.executeQuery()) {
                    rs.next();
                    exists = rs.getInt(1) > 0;
                }
            }
        } while (exists);

        return accountNumber;
    }
}
