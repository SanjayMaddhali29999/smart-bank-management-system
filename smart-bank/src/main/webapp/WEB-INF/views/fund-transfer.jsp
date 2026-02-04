<%@ page language="java"
    contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Fund Transfer</title>

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

    input {
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
        <a href="/smart-bank/customer/dashboard">Back to Dashboard</a>
        <a href="/smart-bank/customer/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Fund Transfer</h2>

<form action="/smart-bank/customer/transfer" method="post">

    <label>To Account Number:</label>
    <input type="text" name="toAccount" required />

    <label>Amount:</label>
    <input type="number" name="amount" step="0.01" required />

    <button type="submit">Transfer</button>
</form>

<p class="error">${error}</p>
<p class="message">${message}</p>

</div>

</body>
</html>
