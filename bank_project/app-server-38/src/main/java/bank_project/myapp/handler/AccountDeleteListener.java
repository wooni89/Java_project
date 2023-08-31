package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.DataSource;

public class AccountDeleteListener implements ActionListener {

  AccountDao accountDao;
  DataSource ds;

  public AccountDeleteListener(AccountDao accountDao, DataSource ds) {
    this.accountDao = accountDao;
    this.ds = ds;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    while (true) {
      String accNum = prompt.inputString("삭제할 계좌번호를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력하세요 : ");
      Account tempAccount = new Account();
      tempAccount.setAccNum(accNum);
      tempAccount.setPassword(pwd);
      Account account = accountDao.findAccountPassword(tempAccount);

      try {
      if (account != null) {
        Account deletedAccount = accountDao.delete(account);

        if (deletedAccount != null) {
          prompt.println("계좌가 정상적으로 삭제되었습니다. 이용해주셔서 감사합니다.");
        } else {
          prompt.println("계좌 삭제에 실패했습니다. 다시 시도해주세요.");
          return;
        }
      } else {
        prompt.println("해당하는 계좌번호와 비밀번호가 일치하는 계좌가 없습니다.");
      }
      } catch (Exception e) {
        try {ds.getConnection().rollback();} catch (Exception e2) {}
        throw new RuntimeException(e);
      }
      break;
    }
    

  }

}