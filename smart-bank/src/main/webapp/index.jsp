<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Smart Bank</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
        margin: 0;
    }

    .header {
        width: 100%;
        background-color: #007bff;
        padding: 15px;
        color: white;
        text-align: center;
        font-size: 22px;
        font-weight: bold;
    }

    .container {
        width: 400px;
        margin: 120px auto;
        background-color: white;
        padding: 30px;
        border-radius: 8px;
        box-shadow: 0px 0px 10px rgba(0,0,0,0.1);
        text-align: center;
    }

    .btn {
        display: block;
        width: 100%;
        padding: 12px;
        margin: 15px 0;
        background-color: #007bff;
        color: white;
        text-decoration: none;
        border-radius: 5px;
        font-size: 16px;
        font-weight: bold;
    }

    .btn:hover {
        background-color: #0056b3;
    }

    .footer {
        text-align: center;
        margin-top: 30px;
        color: gray;
        font-size: 14px;
    }
</style>
</head>

<body>

<div class="header">
    Smart Bank Management System
</div>

<div class="container">
    <h2>Welcome to Smart Bank</h2>
    <p>Please choose your login type</p>

    <a class="btn" href="${pageContext.request.contextPath}/admin/login">
        Admin Login
    </a>

    <a class="btn" href="${pageContext.request.contextPath}/customer/login">
        Customer Login
    </a>
</div>


</body>
</html>
