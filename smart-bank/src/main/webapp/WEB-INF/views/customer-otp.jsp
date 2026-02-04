<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>OTP Verification</title>

<style>
    body {
        font-family: Arial, sans-serif;
        background-color: #f4f6f8;
        margin: 0;
    }

    /* Header Bar */
    .nav-bar {
        width: 100%;
        background-color: #007bff;
        padding: 10px;
        color: white;
        font-weight: bold;
        text-align: center;
    }

    .container {
        width: 350px;
        margin: 80px auto;
        background-color: white;
        padding: 20px;
        border-radius: 6px;
        box-shadow: 0px 0px 8px rgba(0,0,0,0.1);
        text-align: center;
    }

    h2 {
        margin-bottom: 20px;
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
        margin-top: 10px;
        font-weight: bold;
    }
</style>

</head>
<body>

<!-- Header -->
<div class="nav-bar">
    Smart Bank - OTP Verification
</div>

<div class="container">

<h2>Enter OTP</h2>

<form action="/smart-bank/customer/otp" method="post">
    <input type="text" name="otp" placeholder="Enter OTP" required>
    <button type="submit">Verify</button>
</form>

<p class="error">${error}</p>

</div>

</body>
</html>
