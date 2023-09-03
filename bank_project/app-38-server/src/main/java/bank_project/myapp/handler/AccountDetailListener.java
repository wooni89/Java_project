package bank_project.myapp.handler;

import java.io.IOException;
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
  public void service(BreadcrumbPrompt prompt) throws IOException {

    while (true) {
      
      String accNum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Account tempAccount = new Account();
      tempAccount.setAccNum(accNum);
      tempAccount.setPassword(pwd);
      Account account = accountDao.findAccountPassword(tempAccount);
      

      if (account == null) {
        prompt.println("회원이 없습니다.");
        return;
      }

      prompt.println("-------------------");
      prompt.printf("이름 : %s\n", account.getName());
      prompt.printf("계좌번호 : %s\n", account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      prompt.printf("은행이름 : %s\n", account.getBankName());
      prompt.printf("잔고 : $%,d\n", account.getBalance());
      prompt.println("-------------------");
      break;
    }

  }

}
