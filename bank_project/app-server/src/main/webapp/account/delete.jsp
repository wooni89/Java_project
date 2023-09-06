<%@ page import="bank_project.myapp.vo.Customer" %>
<%@ page import="bank_project.myapp.vo.Account" %>
<%@ page import="bank_project.myapp.dao.AccountDao" %>
<%@ page import="org.apache.ibatis.session.SqlSessionFactory" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>

<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="bank_project.myapp.vo.Customer" scope="session"/>

<%
    if (loginUser == null) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    Account account = new Account();
    account.setAccNum(request.getParameter("accNum"));
    account.setOwner(loginUser);

    if (accountDao.delete(account) == 0) {
        throw new ServletException("해당 계좌의 소유주가 아니기에 권한이 없습니다");
    } else {
        response.sendRedirect("list.jsp");
    }
    sqlSessionFactory.openSession(false).commit();
%>