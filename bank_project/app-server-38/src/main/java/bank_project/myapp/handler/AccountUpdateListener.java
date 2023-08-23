package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.DataSource;

public class AccountUpdateListener implements ActionListener {

  AccountDao accountDao;
  DataSource ds;

  public AccountUpdateListener(AccountDao accountDao, DataSource ds) {
    this.accountDao = accountDao;
    this.ds = ds;
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
        ds.getConnection().commit();

      } catch (Exception e) {
        try {ds.getConnection().rollback();} catch (Exception e2) {}
        throw new RuntimeException(e);
      }
      break;
    }

  }
}
