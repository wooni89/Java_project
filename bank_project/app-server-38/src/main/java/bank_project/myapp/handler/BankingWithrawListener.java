package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.DataSource;

public class BankingWithrawListener implements ActionListener {

  AccountDao accountDao;
  DataSource ds;

  public BankingWithrawListener(AccountDao accountDao, DataSource ds) {
    this.accountDao = accountDao;
    this.ds = ds;
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
     
      while (true) {
        int minus = prompt.inputInt("출금금액 : ");
        if (account.getBalance() < minus) {
          prompt.println("잔고가 부족하여 출금이 불가합니다.");
        } else if (minus == 0) {
          prompt.println("$0는 출금할 수 없습니다.");
        } else {
          account.getBalanceMinus(minus);
          prompt.println("-------------------");
          prompt.printf("이름 : %s\n", account.getName());
          prompt.printf("은행명 : %s\n", account.getBankName());
          prompt.printf("계좌번호 : %s\n",
              account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
          prompt.printf("출금 후 잔액 : $%,d \n", account.getBalance());
          prompt.println("-------------------");
          

          try {
            accountDao.withraw(account);
            ds.getConnection().commit();

          } catch (Exception e) {
            try {ds.getConnection().rollback();} catch (Exception e2) {}
            throw new RuntimeException(e);
          }
          break;
        }
      }
      break;
    }
  }

}
