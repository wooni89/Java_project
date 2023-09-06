<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %> <%-- directive element --%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset='UTF-8'>
    <title>회원</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>회원 목록</h1>
<div style='margin:5px;'>
    <a href='/customer/form.jsp'>새 회원</a>
</div>
<table border='1'>
    <thead>
    <tr>
        <th>번호</th>
        <th>이름</th>
        <th>성별</th>
        <th>이메일</th>
        <th>신용등급</th>
    </tr>
    </thead>
    <tbody>
    <jsp:useBean id="customerDao" type="bank_project.myapp.dao.CustomerDao" scope="application"/>
    <c:set var="list" value="${customerDao.findAll()}" scope="page"/>

    <c:forEach items="${list}" var="customer">
        <tr>
            <td>${customer.no}</td>
            <td><img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/${customer.photo}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>${customer.name}
            </td>
            <td>${customer.gender}</td>
            <td><a href='/customer/detail.jsp?no=${customer.no}'>${customer.email}</a></td>
            <td>${customer.creditRating}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<a href='/'>메인</a>

<jsp:include page="../footer.jsp"/>

</body>
</html>