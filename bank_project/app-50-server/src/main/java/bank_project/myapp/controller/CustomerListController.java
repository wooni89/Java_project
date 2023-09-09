package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/customer/list")
public class CustomerListController implements PageController {

  CustomerDao customerDao;

  public CustomerListController(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("list", customerDao.findAll());
    return "/WEB-INF/jsp/customer/list.jsp";
  }

}
