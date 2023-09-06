<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>
<jsp:useBean id="loginUser" class="bank_project.myapp.vo.Customer" scope="session"/>
<c:set var="account" value="${accountDao.findByAccountAndOwner(loginUser.no)}"/>

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
    <li><a href='/account/depositForm.jsp?accNum=${account.accNum}'>입금</a></li>
    <li><a href='/account/withdrawForm.jsp?accNum=${account.accNum}'>출금</a></li>
    <li><a href='/account/transferForm.jsp?accNum=${account.accNum}'>송금</a></li>
    <li><a href='/transaction/passbook.jsp?accNum=${account.accNum}'>거래내역</a></li>
</ul>
<br/><br/>
<a href='/'>메인으로 가기

<jsp:include page="../footer.jsp"/>

</body>
</html>