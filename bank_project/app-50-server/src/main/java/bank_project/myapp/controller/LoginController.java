package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginController implements PageController {

  CustomerDao customerDao;

  public LoginController(CustomerDao customerDao) {
    this.customerDao = customerDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    if (request.getMethod().equals("GET")) {
      return "/WEB-INF/jsp/auth/form.jsp";
    }

  Customer customer = new Customer();
    customer.setEmail(request.getParameter("email"));
    customer.setPassword(request.getParameter("password"));

    if (request.getParameter("saveEmail") != null) {
      Cookie cookie = new Cookie("email", customer.getEmail());
      response.addCookie(cookie);
    } else {
      Cookie cookie = new Cookie("email", "no");
      cookie.setMaxAge(0);
      response.addCookie(cookie);
    }

    Customer loginUser = customerDao.findByEmailAndPassword(customer);

    if (loginUser == null) {
      request.setAttribute("exception", new Exception("회원 정보가 일치하지 않습니다."));
      request.setAttribute("refresh", "1;url=/auth/login");
    }
      request.getSession().setAttribute("loginUser", loginUser);
      return  "redirect:/";
  }
}
