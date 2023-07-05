package bank_project.myapp.handler;

import bank_project.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountDeleteListener implements ActionListener {

  AccountDao accountDao;

  public AccountDeleteListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    while (true) {
      String accNum = prompt.inputString("삭제할 계좌번호를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력하세요 : ");
      Account account = accountDao.findAccountPassword(accNum, pwd);

      boolean removed = accountDao.delete(account);

      if (removed) {
        System.out.println("계좌가 정상적으로 삭제되었습니다. 이용해주셔서 감사합니다.");
      } else {
        System.out.println("계좌 삭제에 실패했습니다. 다시 시도해주세요.");
      }
      break;
    }

  }

}
