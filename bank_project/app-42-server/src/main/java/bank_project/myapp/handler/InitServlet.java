package bank_project.myapp.handler;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.dao.MySQLAccountDao;
import bank_project.myapp.dao.MySQLBoardDao;
import bank_project.myapp.dao.MySQLCustomerDao;
import bank_project.util.SqlSessionFactoryProxy;

@WebServlet(
    value="/init",
    loadOnStartup = 1
    )
public class InitServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  public static SqlSessionFactory sqlSessionFactory;
  public static BoardDao boardDao;
  public static CustomerDao customerDao;
  public static AccountDao accountDao;

  @Override
  public void init() throws ServletException {
    System.out.println("InitServlet.init() 호출됨!");

    try {
      sqlSessionFactory = new SqlSessionFactoryProxy(
          new SqlSessionFactoryBuilder().build(
              Resources.getResourceAsStream("bank_project/myapp/config/mybatis-config.xml")));

      boardDao = new MySQLBoardDao(sqlSessionFactory);
      customerDao = new MySQLCustomerDao(sqlSessionFactory);
      accountDao = new MySQLAccountDao(sqlSessionFactory);

    } catch (Exception e) {
      System.out.println("InitServlet.init() 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
