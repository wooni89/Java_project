<%@ page import="bank_project.myapp.vo.Account" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>

<div style='height:60px;background-color:white;'>
    <img src='/images/logo.png' style='height:60px'>
    <a href='/customer/list'>회원</a>
    <a href='/account/list'>계좌업무</a>

<c:choose>
    <c:when test="${not empty sessionScope.loginUser}">
        <c:set var="account" value="${accountDao.findByAccountAndOwner(sessionScope.loginUser.no)}"/>
        <c:if test="${not empty account}">
            <a href="/transaction/form?accNum=${account.accNum}">은행업무</a>
        </c:if>
    </c:when>
</c:choose>

    <a href='/board/list?category=1'>고객VOC</a>
    <a href='/board/list?category=2'>공지사항</a>

<c:choose>
    <c:when test="${empty sessionScope.loginUser}">
        <a href='/auth/login'>로그인</a>
    </c:when>
    <c:otherwise>
        <c:if test="${empty sessionScope.loginUser.photo}">
            <img style='height:40px' src='/images/avatar.png'>
        </c:if>
        <c:if test="${not empty sessionScope.loginUser.photo}">
            <img src='http://hfwoinxujgwq19373562.cdn.ntruss.com/customer/${loginUser.photo}?type=f&w=30&h=40&faceopt=true&ttype=jpg'>
        </c:if>
        ${sessionScope.loginUser.name} <a href='/auth/logout'>로그아웃</a>
    </c:otherwise>
</c:choose>

</div>