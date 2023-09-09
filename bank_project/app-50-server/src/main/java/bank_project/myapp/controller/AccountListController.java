package bank_project.myapp.controller;

import bank_project.myapp.dao.AccountDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccountListController implements PageController {

  AccountDao accountDao;

  public AccountListController(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    request.setAttribute("list", accountDao.findAll());
    return "/WEB-INF/jsp/account/list.jsp";
  }
}
