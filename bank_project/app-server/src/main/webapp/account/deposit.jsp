<%@ page import="bank_project.myapp.vo.Account" %>
<%@ page import="bank_project.myapp.vo.Transaction" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<jsp:useBean id="accountDao" type="bank_project.myapp.dao.AccountDao" scope="application"/>
<jsp:useBean id="transactionDao" type="bank_project.myapp.dao.TransactionDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    int amount = Integer.parseInt(request.getParameter("amount"));

    Account account = accountDao.findAccount(request.getParameter("accNum"));

    Account acc = new Account();
    acc.setAccNum(account.getAccNum());
    if (amount != 0) {
        acc.setBalance(account.getBalance() + amount);
    }

    Transaction transaction = new Transaction();
    transaction.setAcc_num(account);
    transaction.setTradeType("입금");
    transaction.setAmount(amount);
    transaction.setCustomer(request.getParameter("customer"));

    accountDao.deposit(acc, amount);
    transactionDao.insert(transaction);
    sqlSessionFactory.openSession(false).commit();

    response.sendRedirect("list.jsp");
%>

<p><%= account.getAccNum() %> 계좌로 <%=amount%>원 입금 완료!</p>