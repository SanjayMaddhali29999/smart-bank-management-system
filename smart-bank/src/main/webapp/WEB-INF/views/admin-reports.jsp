<%@ page language="java"
         contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"
         isELIgnored="false" %>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Generate Reports</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
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
        margin-bottom: 10px;
    }

    .top-bar {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .back-btn {
        text-decoration: none;
        padding: 6px 12px;
        background-color: #6c757d;
        color: white;
        border-radius: 4px;
        font-size: 14px;
    }

    .back-btn:hover {
        background-color: #5a6268;
    }

    label {
        font-weight: bold;
    }

    select, input {
        padding: 6px;
        width: 250px;
        margin-top: 5px;
    }

    button {
        padding: 8px 15px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 10px;
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

    .error {
        color: red;
        text-align: center;
        font-weight: bold;
    }

    .download-btn {
        margin-top: 15px;
        text-align: center;
    }

    .bottom-back {
        text-align: center;
        margin-top: 20px;
    }
</style>

</head>
<body>

<div class="container">

<div class="top-bar">
    <h2>Transaction Reports</h2>
    
</div>

<!-- =================== REPORT FILTER FORM =================== -->
<form method="post" action="${pageContext.request.contextPath}/admin/reports">

    <label>Report Type:</label><br>
    <select name="reportType" required>
        <option value="">-- Select --</option>
        <option value="MONTHLY">Monthly</option>
        <option value="ANNUAL">Annual</option>
    </select>
    <br/><br/>

    <label>Month (for Monthly):</label><br>
    <input type="month" name="month"/>
    <br/><br/>

    <label>Year (for Annual):</label><br>
    <input type="number" name="year" min="2000" max="2100"/>
    <br/><br/>

    <label>Customer:</label><br>
    <select name="accountNumber">
        <option value="">-- All Customers --</option>
        <c:forEach items="${customers}" var="c">
            <option value="${c.accountNumber}">
                ${c.fullName} - ${c.accountNumber}
            </option>
        </c:forEach>
    </select>
    <br/><br/>

    <button type="submit">Generate Report</button>
</form>

<!-- =================== ERROR =================== -->
<c:if test="${not empty error}">
    <p class="error">${error}</p>
</c:if>

<!-- =================== RESULT TABLE =================== -->
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

    <div class="download-btn">
        <form method="get"
              action="${pageContext.request.contextPath}/admin/reports/download">

            <input type="hidden" name="reportType" value="${reportType}" />
            <input type="hidden" name="month" value="${month}" />
            <input type="hidden" name="year" value="${year}" />
            <input type="hidden" name="account" value="${account}" />

            <button type="submit">Download CSV</button>
        </form>
    </div>
</c:if>

<!-- Bottom Back Button -->
<div class="bottom-back">
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="back-btn">⬅ Back to Dashboard</a>
</div>

</div>

</body>
</html>
