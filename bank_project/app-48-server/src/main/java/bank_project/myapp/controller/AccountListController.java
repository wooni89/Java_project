package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/account/list")
public class AccountListController extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
    request.setAttribute("list", accountDao.findAll());
    response.setContentType("text/html;charset=UTF-8");
    request.getRequestDispatcher("/WEB-INF/jsp/account/list.jsp").include(request, response);

  }

}
