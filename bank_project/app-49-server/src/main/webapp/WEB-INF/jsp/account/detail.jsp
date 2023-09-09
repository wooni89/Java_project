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
    <title>계좌정보</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h1>계좌 상세정보</h1>
<c:if test="${empty account}">
    <p>해당 계좌가 없습니다!</p>
</c:if>

<c:if test="${not empty account}">
    <form action='/account/update' method='post'>
        <table border='1'>
            <tr>
                <th style='width:120px;'>번호</th>
                <td style='width:300px;'>${account.no}</td>
            </tr>
            <tr>
                <th>이름</th>
                <td>${account.owner.name}</td>
            </tr>
            <tr>
                <th>계좌번호</th>
                <td pattern='[0-9]{3}-[0-9]{2}-[0-9]{3}'>${account.accNum}</td>
            </tr>
            <tr>
                <th>암호</th>
                <td><input type='password' name='password'></td>
            </tr>
            <tr>
                <th>계좌잔액</th>
                <td>${account.balance}</td>
            </tr>
        </table>

        <div>
            <button>변경</button>
            <button type='reset'>초기화</button>
            <a href='/account/delete?accNum=${account.accNum}'>삭제</a>
            <a href='/account/list'>목록</a>
        </div>
    </form>
</c:if>
<jsp:include page="../footer.jsp"/>

</body>
</html>