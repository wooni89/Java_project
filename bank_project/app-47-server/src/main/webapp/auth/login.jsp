<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="refresh" value="2;url=/auth/form.jsp" scope="request"/>

<jsp:useBean id="customer" class="bank_project.myapp.vo.Customer" scope="page"/>
<c:set target="${pageScope.customer}" property="email" value="${param.email}"/>
<c:set target="${pageScope.customer}" property="password" value="${param.password}"/>

<c:if test="${not empty param.saveEmail}">
  <%
    Cookie cookie = new Cookie("email", customer.getEmail());
    response.addCookie(cookie);
  %>
</c:if>

<c:if test="${empty param.saveEmail}">
  <%
    Cookie cookie = new Cookie("email", "no");
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  %>
</c:if>

<jsp:useBean id="customerDao" type="bank_project.myapp.dao.CustomerDao" scope="application"/>
<c:set var="loginUser" value="${customerDao.findByEmailAndPassword(customer)}" scope="session"/>

<c:redirect url="/"/>
