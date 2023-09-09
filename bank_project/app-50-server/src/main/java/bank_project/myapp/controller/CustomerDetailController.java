package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerDetailController implements PageController {

  CustomerDao customerDao;

  public CustomerDetailController(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("customer", customerDao.findBy(Integer.parseInt(request.getParameter("no"))));
    return "/WEB-INF/jsp/customer/detail.jsp";
  }
}
