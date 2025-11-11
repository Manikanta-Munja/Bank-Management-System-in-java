import java.io.IOException;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import com.mani.dao.DBConnection;

@WebServlet("/checkBalance")
public class CheckBalanceServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("username") == null) {
            response.sendRedirect("login.jsp?error=Please login first");
            return;
        }

        String username = (String) session.getAttribute("username");
        double balance = 0.0;

        try (Connection con = DBConnection.getConnection()) {
            String sql = """
                SELECT a.balance 
                FROM accounts a
                JOIN users u ON a.user_id = u.user_id
                WHERE u.username = ?
            """;
            
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                balance = rs.getDouble("balance");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.setAttribute("balance", balance);
        RequestDispatcher rd = request.getRequestDispatcher("checkBalance.jsp");
        rd.forward(request, response);
    }
}
