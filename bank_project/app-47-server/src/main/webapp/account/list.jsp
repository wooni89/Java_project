<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>계좌</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>계좌 목록</h1>
<div style='margin:5px;'>
    <a href='/account/form.jsp'>계좌 개설</a>
</div>
<table border='1'>
    <thead>
    <tr>
        <th>번호</th>
        <th>계좌번호</th>
        <th>이름</th>
        <th>계좌잔액</th>
    </tr>
    </thead>

    <tbody>
    <c:set var="list" value="${accountDao.findAll()}" scope="page"/>
    <c:forEach items="${list}" var="account">
        <tr>
            <td>${account.no}</td>
            <td><a href='/account/detail.jsp?accNum=${account.accNum}'>${account.accNum}</a></td>
            <td>${account.owner.name}</td>
            <td>${account.balance}</td>
        </tr>
    </c:forEach>

    </tbody>
</table>
<a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>

</body>
</html>