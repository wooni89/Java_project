package bank_project.myapp.handler;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import bank_project.myapp.vo.Account;

@WebServlet("/account/delete")
public class AccountDeleteServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    while (true) {
      Account tempAccount = new Account();
      tempAccount.setAccNum(request.getParameter("accNum"));
      tempAccount.setPassword(request.getParameter("password"));
      Account account = InitServlet.accountDao.findAccountPassword(tempAccount);

      try {
        if (account != null) {
          Account deletedAccount = InitServlet.accountDao.delete(account);
          if (deletedAccount != null) {
            InitServlet.sqlSessionFactory.openSession(false).commit();
          } else {
            throw new Exception("계좌 삭제에 실패했습니다. 다시 시도해주세요.");
          }
        } else {
          throw new Exception("해당하는 계좌번호와 비밀번호가 일치하는 계좌가 없습니다.");
        }
      } catch (Exception e) {
        InitServlet.sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
      break;
    }
    

  }

}
