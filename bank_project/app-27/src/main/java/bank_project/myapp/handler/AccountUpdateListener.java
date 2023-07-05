package bank_project.myapp.handler;

import bank_project.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountUpdateListener implements ActionListener {

  AccountDao accountDao;

  public AccountUpdateListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    while (true) {
      String accnum = prompt.inputString("변경할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Account account = accountDao.findAccountPassword(accnum, pwd);

      if (account == null) {
        System.out.println("회원이 없습니다.");
        return;
      }
      if (!account.getAccNum().equals(accnum)) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
        return;
      }
      if (!account.getPassword().equals(pwd)) {
        System.out.println("비밀번호가 다릅니다.");
        return;
      }

      account.setName(prompt.inputString("이름(%s) :", account.getName()));
      account.setPassword(prompt.inputString("새 비밀번호 : "));
      System.out.println("변경이 완료되었습니다.");

      accountDao.update(account);
      break;
    }

  }
}
