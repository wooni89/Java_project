package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet("/customer/list")
public class CustomerListController extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    CustomerDao customerDao = (CustomerDao) this.getServletContext().getAttribute("customerDao");
    request.setAttribute("list", customerDao.findAll());
    request.setAttribute("viewUrl", "/WEB-INF/jsp/customer/list.jsp");
  }

}
