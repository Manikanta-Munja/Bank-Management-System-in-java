

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
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection con = DBConnection.getConnection()) {

            String sql = "SELECT u.user_id, u.username, a.account_number, a.balance " +
                         "FROM users u " +
                         "JOIN accounts a ON u.user_id = a.user_id " +
                         "WHERE u.username = ? AND u.password = ?";

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("user_id", rs.getInt("user_id"));
                session.setAttribute("username", rs.getString("username"));
                session.setAttribute("account_number", rs.getLong("account_number"));
                session.setAttribute("balance", rs.getDouble("balance"));

                response.sendRedirect("dashboard");
            } else {
                response.sendRedirect("login.jsp?error=Invalid username or password");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.jsp?error=Something went wrong");
        }
    }
}
