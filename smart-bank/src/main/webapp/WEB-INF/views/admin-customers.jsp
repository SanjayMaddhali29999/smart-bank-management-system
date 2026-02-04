<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isELIgnored="false"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Manage Customers</title>

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
        background-color: #28a745;
        color: white;
        cursor: pointer;
    }

    button:hover {
        opacity: 0.8;
    }

    .deactivate {
        background-color: #dc3545;
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
    <div>Smart Bank - Admin</div>
    <div>
        <a href="${pageContext.request.contextPath}/admin/dashboard">Back to Dashboard</a>
        <a href="${pageContext.request.contextPath}/admin/logout">Logout</a>
    </div>
</div>

<div class="container">

<h2>Customer Management</h2>

<c:if test="${empty customers}">
    <p class="no-data">No customers found</p>
</c:if>

<c:if test="${not empty customers}">
<table>
<tr>
    <th>Account No</th>
    <th>Name</th>
    <th>Email</th>
    <th>Mobile</th>
    <th>Status</th>
    <th>Action</th>
</tr>

<c:forEach items="${customers}" var="c">
<tr>
    <td>${c.accountNumber}</td>
    <td>${c.fullName}</td>
    <td>${c.email}</td>
    <td>${c.mobile}</td>

    <td>
        <c:choose>
            <c:when test="${c.active}">ACTIVE</c:when>
            <c:otherwise>INACTIVE</c:otherwise>
        </c:choose>
    </td>

    <td>
        <form action="${pageContext.request.contextPath}/admin/customer/toggle" method="post">
            <input type="hidden" name="customerId" value="${c.id}" />
            <button type="submit"
                class="<c:choose>
                          <c:when test='${c.active}'>deactivate</c:when>
                          <c:otherwise></c:otherwise>
                       </c:choose>">
                <c:choose>
                    <c:when test="${c.active}">Deactivate</c:when>
                    <c:otherwise>Activate</c:otherwise>
                </c:choose>
            </button>
        </form>
    </td>
</tr>
</c:forEach>
</table>
</c:if>

</div>

</body>
</html>
