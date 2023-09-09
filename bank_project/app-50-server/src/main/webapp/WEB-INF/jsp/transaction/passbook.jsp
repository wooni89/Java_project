<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>거래 내역</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1> 거래 내역</h1>

<table border=\"1\">
    <thead>
    <tr>
        <th>번호</th>
        <th>거래 날짜</th>
        <th>거래 유형</th>
        <th>거래 금액</th>
        <th>거래자명</th>
    </tr>
    </thead>
    <tbody>

    <c:forEach items="${list}" var="transaction">
        <tr>
            <td>${transaction.no}</td>
            <td><fmt:formatDate value="${transaction.createdDate}" pattern="yyyy-MM-dd"/></td>
            <td>${transaction.tradeType}</td>
            <td>${transaction.amount}</td>
            <td>${transaction.customer}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>
</body>
</html>