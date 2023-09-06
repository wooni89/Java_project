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

  if (amount <= 0) {
    throw new ServletException ("송금 금액은 0원 이상 입력해 주세요");
  }

  Account accountFrom = accountDao.findAccount(request.getParameter("fromAccNum"));
  Account fromAcc = new Account();
  fromAcc.setAccNum(accountFrom.getAccNum());
  if (amount != 0) {
    fromAcc.setBalance(accountFrom.getBalance() - amount);
  }

  if (accountFrom.getBalance() < amount) {
    throw new ServletException ("계좌에 잔액이 부족합니다.");
  }

  Account accountTo = accountDao.findAccount(request.getParameter("toAccNum"));
  Account toAcc = new Account();
  toAcc.setAccNum(accountTo.getAccNum());
  if (amount != 0) {
    toAcc.setBalance(accountTo.getBalance() + amount);
  }

  if (accountTo == null) {
    throw new ServletException ("계좌가 존재하지 않습니다");
  }

    accountDao.withdraw(accountFrom, amount);
    Transaction fromTx = new Transaction();
    fromTx.setAcc_num(accountFrom);
    fromTx.setTradeType("출금(계좌이체)");
    fromTx.setAmount(amount);
    fromTx.setCustomer(accountTo.getOwner().getName());
    transactionDao.insert(fromTx);

    accountDao.deposit(accountTo, amount);
    Transaction toTx = new Transaction();
    toTx.setAcc_num(accountTo);
    toTx.setTradeType("입금(계좌이체)");
    toTx.setAmount(amount);
    toTx.setCustomer(accountFrom.getOwner().getName());
    transactionDao.insert(toTx);

    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("/transaction/form.jsp?accNum=" + accountFrom.getAccNum());
%>

<p><%=accountTo.getAccNum() %> 계좌로 <%=amount%>원 입금 완료!</p>