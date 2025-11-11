


import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.mani.dao.DBConnection;

@WebServlet("/transfer")
public class TransferServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        String senderUsername = (String) session.getAttribute("username");
        long receiverAccNo = Long.parseLong(request.getParameter("receiverAccount"));
        double amount = Double.parseDouble(request.getParameter("amount"));

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false); // begin transaction

            // Step 1️⃣: Get sender's balance and account info
            String senderSql = """
                SELECT a.account_id, a.balance 
                FROM accounts a 
                JOIN users u ON a.user_id = u.user_id 
                WHERE u.username = ?
            """;
            PreparedStatement psSender = con.prepareStatement(senderSql);
            psSender.setString(1, senderUsername);
            ResultSet rsSender = psSender.executeQuery();

            if (!rsSender.next()) {
                response.sendRedirect("transfer.jsp?error=Sender account not found");
                return;
            }

            double senderBalance = rsSender.getDouble("balance");
            int senderAccountId = rsSender.getInt("account_id");

            // Step 2️⃣: Check balance
            if (senderBalance < amount) {
                response.sendRedirect("transfer.jsp?error=Insufficient balance");
                return;
            }

            // Step 3️⃣: Get receiver account
            String receiverSql = "SELECT account_id, balance FROM accounts WHERE account_number = ?";
            PreparedStatement psReceiver = con.prepareStatement(receiverSql);
            psReceiver.setLong(1, receiverAccNo);
            ResultSet rsReceiver = psReceiver.executeQuery();

            if (!rsReceiver.next()) {
                response.sendRedirect("transfer.jsp?error=Receiver account not found");
                return;
            }

            int receiverAccountId = rsReceiver.getInt("account_id");
            double receiverBalance = rsReceiver.getDouble("balance");

            // Step 4️⃣: Update balances
            PreparedStatement psUpdateSender = con.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateSender.setDouble(1, senderBalance - amount);
            psUpdateSender.setInt(2, senderAccountId);
            psUpdateSender.executeUpdate();

            PreparedStatement psUpdateReceiver = con.prepareStatement(
                "UPDATE accounts SET balance = ? WHERE account_id = ?");
            psUpdateReceiver.setDouble(1, receiverBalance + amount);
            psUpdateReceiver.setInt(2, receiverAccountId);
            psUpdateReceiver.executeUpdate();

            con.commit(); // commit transaction
            response.sendRedirect("dashboard.jsp?msg=Transfer successful!");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("transfer.jsp?error=Transaction failed");
        }
    }
}
