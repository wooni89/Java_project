<%@ page import="bank_project.myapp.vo.Board" %>
<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp"%>

<jsp:useBean id="boardDao" type="bank_project.myapp.dao.BoardDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="bank_project.util.NcpObjectStorageService" scope="application"/>
<jsp:useBean id="loginUser" class="bank_project.myapp.vo.Customer" scope="session"/>

<%
    if (loginUser.getNo() == 0) {
        response.sendRedirect("/auth/form.jsp");
        return;
    }

    request.setAttribute("refresh", "2;url=list.jsp?category=" + request.getParameter("category"));
    int category = Integer.parseInt(request.getParameter("category"));

    Board b = new Board();
    b.setNo(Integer.parseInt(request.getParameter("no")));
    b.setWriter(loginUser);
    b.setCategory(category);

    boardDao.deleteFiles(b.getNo());

    if (boardDao.delete(b) == 0) {
        throw new Exception("해당 번호의 게시글이 없거나 삭제 권한이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp?category=" + request.getParameter("category"));
    }
%>