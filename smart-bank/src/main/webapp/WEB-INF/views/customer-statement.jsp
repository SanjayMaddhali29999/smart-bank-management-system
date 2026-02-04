<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Mini Statement</title>

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

    label {
        font-weight: bold;
    }

    select, input {
        padding: 6px;
        margin-right: 10px;
    }

    button {
        padding: 8px 15px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
    }

    button:hover {
        background-color: #0056b3;
    }

    table {
        width: 100%;
        border-collapse: collapse;
        margin-top: 20px;
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
        margin-top: 15px;
    }

    .download-btn {
        margin-top: 15px;
        text-align: center;
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

<h2>Transaction Statement</h2>

<!-- 🔹 FILTER FORM -->
<form action="${pageContext.request.contextPath}/customer/statement/filter" method="get">
    <label>Month:</label>
    <select name="month">
        <option value="">--All--</option>
        <c:forEach begin="1" end="12" var="m">
            <option value="${m}">${m}</option>
        </c:forEach>
    </select>

    <label>Year:</label>
    <input type="number" name="year" placeholder="2026" required />

    <button type="submit">View</button>
</form>

<!-- ❌ NO DATA -->
<c:if test="${empty transactions}">
    <p class="no-data">No transactions found</p>
</c:if>

<!-- ✅ DATA TABLE -->
<c:if test="${not empty transactions}">
<table>
<tr>
    <th>Date</th>
    <th>Type</th>
    <th>From</th>
    <th>To</th>
    <th>Amount</th>
</tr>

<c:forEach items="${transactions}" var="tx">
<tr>
    <td>${tx.transactionDate}</td>
    <td>${tx.type}</td>
    <td>${tx.fromAccount}</td>
    <td>${tx.toAccount}</td>
    <td>₹ ${tx.amount}</td>
</tr>
</c:forEach>
</table>

<!-- 🔽 DOWNLOAD CSV -->
<div class="download-btn">
<form action="${pageContext.request.contextPath}/customer/statement/download" method="get">
    <input type="hidden" name="month" value="${month}" />
    <input type="hidden" name="year" value="${year}" />
    <button type="submit">Download CSV</button>
</form>
</div>
</c:if>

</div>

</body>
</html>
