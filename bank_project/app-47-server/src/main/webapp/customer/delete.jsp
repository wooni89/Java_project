<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>

<jsp:useBean id="customerDao" type="bank_project.myapp.dao.CustomerDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>

<%
    request.setAttribute("refresh", "2;url=list");
    if (customerDao.delete(request.getParameter("email")) == 0) {
        throw new Exception("해당 번호의 회원이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("/customer/list.jsp");
    }
%>