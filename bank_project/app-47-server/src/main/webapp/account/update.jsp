<%@ page import="bank_project.myapp.vo.Account" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true" %>

<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="bank_project.myapp.vo.Customer" scope="session"/>

<%
    request.setAttribute("refresh", "1;url=list.jsp");

    if (loginUser == null) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    Account account = new Account();
    account.setNo(Integer.parseInt(request.getParameter("no")));
    account.setOwner(loginUser);
    account.setPassword(request.getParameter("password"));

    if (accountDao.update(account) == 0) {
        throw new Exception("회원이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }
%>