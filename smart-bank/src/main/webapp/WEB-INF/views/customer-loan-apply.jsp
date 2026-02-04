<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Apply for Loan</title>

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
        width: 350px;
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
        width: 100%;
        padding: 8px;
        margin-top: 5px;
        border: 1px solid #ccc;
        border-radius: 4px;
    }

    button {
        width: 100%;
        padding: 10px;
        background-color: #007bff;
        color: white;
        border: none;
        border-radius: 4px;
        cursor: pointer;
        margin-top: 15px;
    }

    button:hover {
        background-color: #0056b3;
    }

    .error {
        color: red;
        text-align: center;
        margin-top: 10px;
    }

    .message {
        color: green;
        text-align: center;
        margin-top: 10px;
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

<h2>Apply for Loan</h2>

<form action="${pageContext.request.contextPath}/customer/loan/apply" method="post">

    <label>Loan Type:</label>
    <select name="loanType" required>
        <option value="">Select</option>
        <option value="HOME">Home Loan</option>
        <option value="PERSONAL">Personal Loan</option>
        <option value="EDUCATION">Education Loan</option>
    </select>

    <label>Amount:</label>
    <input type="number" name="amount" required />

    <label>Tenure (Months):</label>
    <input type="number" name="tenure" required />

    <button type="submit">Apply</button>

</form>

<p class="error">${error}</p>
<p class="message">${message}</p>

</div>

</body>
</html>
