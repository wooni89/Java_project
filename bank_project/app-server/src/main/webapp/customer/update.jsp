<%@ page
        language="java"
        pageEncoding="UTF-8"
        contentType="text/html;charset=UTF-8"
        trimDirectiveWhitespaces="true"
        errorPage="/error.jsp" %>

<%@ page import="bank_project.myapp.vo.Customer" %>
<jsp:useBean id="customerDao" type="bank_project.myapp.dao.CustomerDao" scope="application"/>
<jsp:useBean id="sqlSessionFactory" type="org.apache.ibatis.session.SqlSessionFactory" scope="application"/>
<jsp:useBean id="ncpObjectStorageService" type="bank_project.util.NcpObjectStorageService" scope="application"/>

<%
    request.setAttribute("refresh", "2;url=list.jsp");

    Customer customer = new Customer();
    customer.setNo(Integer.parseInt(request.getParameter("no")));
    customer.setName(request.getParameter("name"));
    customer.setEmail(request.getParameter("email"));
    customer.setGender(request.getParameter("gender").charAt(0));
    customer.setPassword(request.getParameter("password"));
    customer.setAddress(request.getParameter("address"));
    customer.setCreditRating(Integer.parseInt(request.getParameter("creditRating")));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
        String uploadFileUrl = ncpObjectStorageService.uploadFile(
                "bank-bukit-1", "customer/", photoPart);
        customer.setPhoto(uploadFileUrl);
    }

    if (customerDao.update(customer) == 0) {
        throw new Exception("회원이 없습니다.");
    } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list.jsp");
    }


%>