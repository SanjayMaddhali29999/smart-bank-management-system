<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Your Loans</title>

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

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 15px;
    }

    th {
        background-color: #007bff;
        color: white;
        padding: 10px;
    }

    td {
        padding: 8px;
        text-align: center;
        border: 1px solid #ddd;
    }

    tr:nth-child(even) {
        background-color: #f2f2f2;
    }

    .no-data {
        text-align: center;
        color: red;
        font-size: 16px;
        font-weight: bold;
    }
</style>

</head>
<body>

<!-- Navigation Bar -->
<div class="nav-bar">
    <div>Smart Bank - Customer</div>
    <div>
        <a href="${pageContext.request.contextPath}/customer/dashboard">Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/customer/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Your Loans</h2>

<!-- ✅ NO LOANS -->
<c:if test="${empty loans}">
    <p class="no-data">You have not applied for any loans yet.</p>
</c:if>

<!-- ✅ LOANS TABLE -->
<c:if test="${not empty loans}">
<table>
<tr>
    <th>Type</th>
    <th>Amount</th>
    <th>Tenure (Months)</th>
    <th>Interest (%)</th>
    <th>Status</th>
</tr>

<c:forEach items="${loans}" var="loan">
<tr>
    <td>${loan.loanType}</td>
    <td>₹ ${loan.amount}</td>
    <td>${loan.tenureMonths}</td>
    <td>${loan.interestRate}%</td>
    <td><b>${loan.status}</b></td>
</tr>
</c:forEach>
</table>
</c:if>

</div>

</body>
</html>
