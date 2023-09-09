package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/auth/login")
public class LoginController extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    request.setAttribute("viewUrl", "/WEB-INF/jsp/auth/form.jsp");
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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

    CustomerDao customerDao = (CustomerDao) this.getServletContext().getAttribute("customerDao");
    Customer loginUser = customerDao.findByEmailAndPassword(customer);

    if (loginUser != null) {
      request.getSession().setAttribute("loginUser", loginUser);
      request.setAttribute("viewUrl", "redirect:/");
      return;
    }
    request.setAttribute("exception", new Exception("회원 정보가 일치하지 않습니다."));
    request.setAttribute("refresh", "1;url=/auth/login");
  }
}
