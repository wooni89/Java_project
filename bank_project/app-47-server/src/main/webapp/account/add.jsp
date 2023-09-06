<%@ page import="bank_project.myapp.vo.Account" %>
<%@ page import="bank_project.myapp.dao.AccountDao" %>
<%@ page import="javax.servlet.ServletContext" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="loginUser" class="bank_project.myapp.vo.Customer" scope="session"/>

<%
    request.setAttribute("refresh", "1;url=list");

    if (loginUser == null) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    Account account = new Account(); // 입력데이터 배열로 저장
    account.setOwner(loginUser);
    account.setAccNum(generateAccountNumber(application)); // Here we generate a new account number.
    account.setPassword(request.getParameter("password"));
    account.setBalance(0);

    accountDao.insert(account);
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");
%>

<%!
    public String generateAccountNumber(ServletContext application) {
        try {
            String maxAccNum = ((AccountDao) application.getAttribute("accountDao")).findMaxAccNum();

            if (maxAccNum == null) {
                return "11100001";
            } else {
                int numberPart = Integer.parseInt(maxAccNum.substring(3));
                return "111" + String.format("%05d", numberPart + 1);
            }
        } catch (Exception e) {
            throw new RuntimeException("새 계좌를 발급할 수 없습니다.", e);
        }
    }
%>