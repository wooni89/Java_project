package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountDetailListener implements ActionListener {

  AccountDao accountDao;

  public AccountDetailListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    while (true) {
      String accNum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Account account = accountDao.findAccountPassword(accNum, pwd);

      if (account == null) {
        System.out.println("회원이 없습니다.");
        return;
      }
      if (!account.getAccNum().equals(accNum)) {
        System.out.println("입력한 계좌번호가 없습니다.");
        return;
      }
      if (!account.getPassword().equals(pwd)) {
        System.out.println("비밀번호가 다릅니다.");
        return;
      }

      System.out.println("-------------------");
      System.out.printf("이름 : %s\n", account.getName());
      System.out.printf("계좌번호 : %s\n", account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      System.out.printf("은행이름 : %s\n", account.getBankName());
      System.out.printf("잔고 : $%,d\n", account.getBalance());
      System.out.println("-------------------");
      break;
    }

  }

}
