package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CustomerDeleteController implements PageController {

  CustomerDao customerDao;
  SqlSessionFactory sqlSessionFactory;

  public CustomerDeleteController(CustomerDao customerDao, SqlSessionFactory sqlSessionFactory) {
    this.customerDao = customerDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

    try {
      if (customerDao.delete(request.getParameter("email")) == 0) {
        throw new Exception("해당 회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        return  "redirect:list";
      }

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("refresh", "2;url=list");
      throw e;
    }
  }
}
