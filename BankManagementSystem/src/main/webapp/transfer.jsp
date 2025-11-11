<%@ page session="true" %>
<%
    if (session.getAttribute("username") == null) {
        response.sendRedirect("login.jsp?error=Please login first");
        return;
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Money Transfer</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="dashboard">MyBank</a>
        <div class="d-flex">
            <span class="navbar-text text-white me-3">
                Welcome, <%= session.getAttribute("username") %>
            </span>
            <a href="logout" class="btn btn-danger">Logout</a>
        </div>
    </div>
</nav>

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-5">
            <div class="card shadow">
                <div class="card-header bg-primary text-white text-center">
                    <h4>Transfer Money</h4>
                </div>
                <div class="card-body">
                    <form action="transfer" method="post">
                        <div class="mb-3">
                            <label class="form-label">Receiver Account Number</label>
                            <input type="text" name="receiverAccount" class="form-control" required>
                        </div>
                        <div class="mb-3">
                            <label class="form-label">Amount</label>
                            <input type="number" step="0.01" name="amount" class="form-control" required>
                        </div>
                        <button type="submit" class="btn btn-success w-100">
                            <i class="bi bi-arrow-right-circle me-2"></i>Transfer
                        </button>
                    </form>

                    <% if (request.getParameter("error") != null) { %>
                        <div class="alert alert-danger mt-3"><%= request.getParameter("error") %></div>
                    <% } else if (request.getParameter("msg") != null) { %>
                        <div class="alert alert-success mt-3"><%= request.getParameter("msg") %></div>
                    <% } %>
                </div>
                <div class="card-footer text-center">
                    <a href="dashboard" class="btn btn-secondary">
                        <i class="bi bi-arrow-left-circle me-2"></i>Back to Dashboard
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
