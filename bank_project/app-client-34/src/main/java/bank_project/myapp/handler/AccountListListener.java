package bank_project.myapp.handler;

import java.util.List;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountListListener implements ActionListener {

  AccountDao accountDao;

  public AccountListListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    List<Account> list = accountDao.list();

    for (Account account : list) {
      String formattedAccNum = 
          account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
      System.out.printf("%d, %s, %s, %s, $%,d\n",
          account.getNo(),
          account.getName(),
          formattedAccNum,
          account.getBankName(),
          account.getBalance());
    }

  }

}
