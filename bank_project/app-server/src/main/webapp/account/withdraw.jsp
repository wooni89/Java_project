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
    int amount = Integer.parseInt(request.getParameter("amount"));

    Account account = accountDao.findAccount(request.getParameter("accNum"));

    Account acc = new Account();
    acc.setAccNum(account.getAccNum());
    if (amount != 0) {
        acc.setBalance(account.getBalance() - amount);
    }

    Transaction transaction = new Transaction();
    transaction.setAcc_num(account);
    transaction.setTradeType("출금");
    transaction.setAmount(amount);
    transaction.setCustomer(request.getParameter("customer"));

    accountDao.withdraw(acc, amount);
    transactionDao.insert(transaction);
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list.jsp");
%>

<p><%= amount%>원 출금 완료!</p>