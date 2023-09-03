package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class BankingWithrawListener implements ActionListener {

  AccountDao accountDao;

  public BankingWithrawListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    while (true) {
      String accNum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Account tempAccount = new Account();
      tempAccount.setAccNum(accNum);
      tempAccount.setPassword(pwd);
      Account account = accountDao.findAccountPassword(tempAccount);
      
      if (account == null) {
        System.out.println("회원이 없습니다.");
        return;
      }
     
      while (true) {
        int minus = prompt.inputInt("출금금액 : ");
        if (account.getBalance() < minus) {
          System.out.println("잔고가 부족하여 출금이 불가합니다.");
        } else if (minus == 0) {
          System.out.println("$0는 출금할 수 없습니다.");
        } else {
          account.getBalanceMinus(minus);
          System.out.println("-------------------");
          System.out.printf("이름 : %s\n", account.getName());
          System.out.printf("은행명 : %s\n", account.getBankName());
          System.out.printf("계좌번호 : %s\n",
              account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
          System.out.printf("출금 후 잔액 : $%,d \n", account.getBalance());
          System.out.println("-------------------");
          
          accountDao.withraw(account);
          break;
        }
      }
      break;
    }
  }

}
