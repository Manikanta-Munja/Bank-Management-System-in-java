import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.mani.dao.DBConnection;

@WebServlet("/withdraw")
public class WithdrawServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1️⃣ Check if user is logged in
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user_id") == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        int userId = (Integer) session.getAttribute("user_id");

        // 2️⃣ Get withdrawal amount from request
        double amount;
        try {
            amount = Double.parseDouble(request.getParameter("amount"));
            if (amount <= 0) {
                response.sendRedirect("dashboard?error=Invalid withdrawal amount");
                return;
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("dashboard?error=Invalid input");
            return;
        }

        double currentBalance = (Double) session.getAttribute("balance");

        // 3️⃣ Check sufficient balance
        if (amount > currentBalance) {
            response.sendRedirect("dashboard?error=Insufficient balance");
            return;
        }

        // 4️⃣ Update balance in database
        try (Connection con = DBConnection.getConnection()) {
            String sql = "UPDATE accounts SET balance = balance - ? WHERE user_id=?";
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setDouble(1, amount);
            ps.setInt(2, userId);
            int updatedRows = ps.executeUpdate();

            if (updatedRows > 0) {
                // 5️⃣ Update session balance
                session.setAttribute("balance", currentBalance - amount);
                response.sendRedirect("dashboard?msg=Withdrawal successful");
            } else {
                response.sendRedirect("dashboard?error=Withdrawal failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("dashboard?error=Database error");
        }
    }
}
