<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Admin Loan Approvals</title>

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

    table {
        width: 100%;
        border-collapse: collapse;
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

    button {
        padding: 6px 12px;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        color: white;
    }

    .approve-btn {
        background-color: #28a745;
    }

    .reject-btn {
        background-color: #dc3545;
    }

    button:hover {
        opacity: 0.85;
    }

    .no-data {
        text-align: center;
        color: red;
        font-size: 16px;
    }
</style>

</head>
<body>

<!-- Navigation Bar -->
<div class="nav-bar">
    <div>Smart Bank - Loan Approvals</div>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Pending Loan Applications</h2>

<c:if test="${empty loans}">
    <p class="no-data">No pending loans</p>
</c:if>

<c:if test="${not empty loans}">
<table>
<tr>
    <th>Account</th>
    <th>Type</th>
    <th>Amount</th>
    <th>Tenure</th>
    <th>Interest</th>
    <th>Action</th>
</tr>

<c:forEach items="${loans}" var="loan">
<tr>
    <td>${loan.accountNumber}</td>
    <td>${loan.loanType}</td>
    <td>₹ ${loan.amount}</td>
    <td>${loan.tenureMonths}</td>
    <td>${loan.interestRate}%</td>
    <td>
        <form action="${pageContext.request.contextPath}/admin/loan/approve" 
              method="post" style="display:inline">
            <input type="hidden" name="loanId" value="${loan.id}"/>
            <button type="submit" class="approve-btn">Approve</button>
        </form>

        <form action="${pageContext.request.contextPath}/admin/loan/reject" 
              method="post" style="display:inline">
            <input type="hidden" name="loanId" value="${loan.id}"/>
            <button type="submit" class="reject-btn">Reject</button>
        </form>
    </td>
</tr>
</c:forEach>
</table>
</c:if>

</div>

</body>
</html>
