<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Customer Dashboard</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
        margin: 0;
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

    .account-info {
        background-color: #f2f2f2;
        padding: 15px;
        border-radius: 5px;
        margin-bottom: 20px;
        text-align: center;
        font-size: 16px;
    }

    ul {
        list-style: none;
        padding: 0;
        text-align: center;
    }

    ul li {
        margin: 12px 0;
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
    <div>Smart Bank - Customer</div>
    <div>
        <a href="${pageContext.request.contextPath}/customer/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Welcome, ${customer.fullName}</h2>

<div class="account-info">
    <p><b>Account Number:</b> ${customer.accountNumber}</p>
    <p><b>Balance:</b> ₹ ${customer.balance}</p>
</div>

<hr/>

<ul>
    <li><a href="${pageContext.request.contextPath}/customer/transfer">Fund Transfer</a></li>
    <li><a href="${pageContext.request.contextPath}/customer/statement">Mini Statement</a></li>
    <li><a href="${pageContext.request.contextPath}/customer/loan/apply">Apply Loan</a></li>
    <li><a href="${pageContext.request.contextPath}/customer/loans">View Loan Status</a></li>
</ul>

</div>

</body>
</html>
