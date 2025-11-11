<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page session="true" %>
<%
    // Prevent browser cache
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    response.setHeader("Pragma", "no-cache");
    response.setDateHeader("Expires", 0);
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">
</head>
<body class="bg-light">

<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">MyBank</a>
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
        <div class="col-md-8">
            <div class="card shadow">
                <div class="card-header bg-primary text-white text-center">
                    <h3>Dashboard</h3>
                </div>
                <div class="card-body">
                    <p><strong>Account Number:</strong> <%= session.getAttribute("account_number") %></p>
                    

                    <% if(request.getParameter("msg") != null) { %>
                        <div class="alert alert-success mt-3"><%= request.getParameter("msg") %></div>
                    <% } %>

                    <div class="row mt-4">
                        <div class="col-md-4 mb-2">
                            <a href="Deposit.jsp" class="btn btn-success w-100"><i class="bi bi-cash-stack me-2"></i>Deposit</a>
                        </div>
                        <div class="col-md-4 mb-2">
                            <a href="withdrawl.jsp" class="btn btn-warning w-100"><i class="bi bi-wallet2 me-2"></i>Withdraw</a>
                        </div>
                        <div class="col-md-4 mb-2">
                            <a href="transfer.jsp" class="btn btn-info w-100 text-white"><i class="bi bi-arrow-right-circle me-2"></i>Transfer</a>
                        </div>
                        <div class="col-md-4 mb-2">
                            <a href="checkBalance" class="btn btn-secondary w-100"><i class="bi bi-bar-chart-line me-2"></i>Check Balance</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
