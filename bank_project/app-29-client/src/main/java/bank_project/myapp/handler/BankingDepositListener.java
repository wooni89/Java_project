package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class BankingDepositListener implements ActionListener {

  AccountDao accountDao;

  public BankingDepositListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    String accNum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
    String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
    Account tempAccount = new Account();
    tempAccount.setAccNum(accNum);
    tempAccount.setPassword(pwd);
    Account account = accountDao.findAccountPassword(tempAccount);

    if (account == null) {
      System.out.println("해당 회원이 없습니다.");
      return;
    }
    
    while (true) {
      int plus = prompt.inputInt("입금금액 : $ ");
      if (plus > 0) {
        account.getBalancePlus(plus);
        System.out.println("-------------------");
        System.out.printf("이름 : %s\n", account.getName());
        System.out.printf("은행명 : %s\n", account.getBankName());
        System.out.printf("계좌번호 : %s\n",
            account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
        System.out.printf("입금 후 잔액 : $%,d \n", account.getBalance());
        System.out.println("-------------------");
        
        accountDao.deposit(account);
        break;
        
      } else if (plus == 0) {
        System.out.println("$0 이하의 금액은 입금이 불가합니다.");
      }
    }
  }


}
