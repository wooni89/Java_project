package bank_project.myapp.handler;

import bank_project.myapp.dao.*;
import bank_project.util.NcpConfig;
import bank_project.util.NcpObjectStorageService;
import bank_project.util.SqlSessionFactoryProxy;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

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
  public static TransactionDao transactionDao;
  public static NcpObjectStorageService ncpObjectStorageService;

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
      transactionDao = new MySQLTransactionDao(sqlSessionFactory);
      ncpObjectStorageService = new NcpObjectStorageService(new NcpConfig());

    } catch (Exception e) {
      System.out.println("InitServlet.init() 실행 중 오류 발생!");
      e.printStackTrace();
    }
  }
}
