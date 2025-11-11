

import java.io.IOException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    	

    	HttpSession session = request.getSession(false);
    	if (session == null || session.getAttribute("user_id") == null) {
    	    response.sendRedirect("login.jsp?error=Please login first");
    	    return;
    	}

        // Dashboard JSP can now display account_number and balance from session
        RequestDispatcher rd = request.getRequestDispatcher("dashboard.jsp");
        rd.forward(request, response);
    }
}
