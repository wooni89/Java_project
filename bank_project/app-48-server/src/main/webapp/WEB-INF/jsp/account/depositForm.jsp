<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <style>
        body {font-family: Arial, sans-serif; margin:0 auto; width:50%; padding-top:50px;}
        form {display:flex; flex-direction:column; gap:10px;}
        input[type=submit] {cursor:pointer;}
    </style>

    <title>입금</title>
</head>

<jsp:include page="../header.jsp"/>

<body>
<form method='post' action='/account/deposit'>
    <input type='hidden' name='accNum' value='${account.accNum}'>
    계좌 번호: <strong>${account.accNum}</strong>
    입금 금액:<br> <input type='number' name='amount'><br>
    입금자명 :<br> <input type='customer' name='customer'><br>
    <input type='submit' value='입금하기'>
</form>

<jsp:include page="../footer.jsp"/>

</body>
</html>