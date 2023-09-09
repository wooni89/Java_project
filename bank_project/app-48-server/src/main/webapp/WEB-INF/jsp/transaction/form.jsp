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
    <title>거래</title>
</head>
<body>

<jsp:include page="../header.jsp"/>

<h2>계좌 번호: ${account.accNum}</h2>
<h2>거래를 선택하세요</h2>
<ul>
    <li><a href='/account/deposit?accNum=${account.accNum}'>입금</a></li>
    <li><a href='/account/withdraw?accNum=${account.accNum}'>출금</a></li>
    <li><a href='/account/transfer?accNum=${account.accNum}'>송금</a></li>
    <li><a href='/transaction/passbook?accNum=${account.accNum}'>거래내역</a></li>
</ul>
<br/><br/>
<a href='/'>메인으로 가기</a>

<jsp:include page="../footer.jsp"/>

</body>
</html>