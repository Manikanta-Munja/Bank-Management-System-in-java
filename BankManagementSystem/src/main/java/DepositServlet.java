

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import com.mani.dao.DBConnection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/deposit")
public class DepositServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        int userId = (Integer) session.getAttribute("user_id");
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection con = DBConnection.getConnection()) {
            // Update balance
            String sql = "UPDATE accounts SET balance = balance + ? WHERE user_id = ?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            ps.executeUpdate();

            // Update session balance
            double newBalance = (Double) session.getAttribute("balance") + amount;
            session.setAttribute("balance", newBalance);

        } catch (Exception e) {
            e.printStackTrace();
        }

        response.sendRedirect("dashboard?msg=Deposit successful");
    }
}
