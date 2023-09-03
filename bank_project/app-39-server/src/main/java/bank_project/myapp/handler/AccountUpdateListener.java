package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountUpdateListener implements ActionListener {

  AccountDao accountDao;
  SqlSessionFactory sqlSessionFactory;

  public AccountUpdateListener(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
    this.accountDao = accountDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    while (true) {
      String accNum = prompt.inputString("변경할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Account tempAccount = new Account();
      tempAccount.setAccNum(accNum);
      tempAccount.setPassword(pwd);
      Account account = accountDao.findAccountPassword(tempAccount);
      

      if (account == null) {
        prompt.println("회원이 없습니다.");
        return;
      }

      account.setName(prompt.inputString("이름(%s) :", account.getName()));
      account.setPassword(prompt.inputString("새 비밀번호 : "));
      prompt.println("변경이 완료되었습니다.");

      try {
        accountDao.update(account);
        sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
      break;
    }

  }
}
