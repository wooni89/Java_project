<%@ page import="bank_project.myapp.vo.Board" %>
<%@ page import="bank_project.myapp.vo.AttachedFile" %>
<%@ page import="java.util.ArrayList" %>
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
    request.setAttribute("refresh", "1;url=list");
    Board board = new Board();
    board.setWriter(loginUser);
    board.setTitle(request.getParameter("title"));
    board.setContent(request.getParameter("content"));
    board.setCategory(Integer.parseInt(request.getParameter("category")));

    ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

    for (Part part : request.getParts()) {
        if (part.getName().equals("files") && part.getSize() > 0) {
            String uploadFileUrl =
                    ncpObjectStorageService.uploadFile("bank-bukit-1", "board/", part);
            AttachedFile attachedFile = new AttachedFile();
            attachedFile.setFilePath(uploadFileUrl);
            attachedFiles.add(attachedFile);
        }
    }
    board.setAttachedFiles(attachedFiles);

    boardDao.insert(board);
    if (attachedFiles.size() > 0) {
        boardDao.insertFiles(board);
    }
    sqlSessionFactory.openSession(false).commit();
    response.sendRedirect("list?category=" + request.getParameter("category"));

%>