<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Dashboard</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
    }

    /* Navigation Bar */
    .nav-bar {
        width: 100%;
        background-color: #007bff;
        padding: 10px;
        display: flex;
        justify-content: space-between;
        color: white;
    }

    .nav-bar a {
        color: white;
        text-decoration: none;
        font-weight: bold;
        margin-left: 15px;
    }

    .nav-bar a:hover {
        text-decoration: underline;
    }

    .container {
        width: 80%;
        margin: 40px auto;
        background-color: white;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0px 0px 8px rgba(0,0,0,0.1);
    }

    h2 {
        text-align: center;
        margin-bottom: 20px;
    }

    .stats {
        display: flex;
        justify-content: space-around;
        margin-bottom: 25px;
    }

    .card {
        background-color: #f2f2f2;
        padding: 15px;
        width: 150px;
        text-align: center;
        border-radius: 5px;
        font-weight: bold;
    }

    ul {
        list-style: none;
        padding: 0;
        text-align: center;
    }

    ul li {
        margin: 10px 0;
    }

    ul li a {
        text-decoration: none;
        color: #007bff;
        font-size: 16px;
        font-weight: bold;
    }

    ul li a:hover {
        text-decoration: underline;
    }
</style>

</head>
<body>

<!-- Navigation Bar -->
<div class="nav-bar">
    <div>Smart Bank - Admin Dashboard</div>
    <div>
        <a href="/smart-bank/admin/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Welcome Admin</h2>

<div class="stats">
    <div class="card">Total Customers<br>${totalCustomers}</div>
    <div class="card">Active Customers<br>${activeCustomers}</div>
    <div class="card">Inactive Customers<br>${inactiveCustomers}</div>
    <div class="card">Pending Loans<br>${pendingLoans}</div>
    <div class="card">Approved Loans<br>${approvedLoans}</div>
</div>

<ul>
    <li><a href="/smart-bank/admin/create-customer">Create Customer</a></li>
    <li><a href="/smart-bank/admin/customers">Manage Customers</a></li>
    <li><a href="/smart-bank/admin/loans">Manage Loans</a></li>
    <li><a href="${pageContext.request.contextPath}/admin/reports">Generate Reports</a></li>
</ul>

</div>

</body>
</html>
