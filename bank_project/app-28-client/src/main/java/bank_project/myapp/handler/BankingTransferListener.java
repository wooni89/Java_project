package bank_project.myapp.handler;

import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class BankingTransferListener implements ActionListener {

  AccountDao accountDao;

  public BankingTransferListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }


  @Override
  public void service(BreadcrumbPrompt prompt) {
    String accnum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
    String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
    Account account = accountDao.findAccountPassword(accnum, pwd);

    if (account == null) {
      System.out.println("회원이 없습니다.");
      return;
    }

    int money = prompt.inputInt("출금액을 입력하세요 : ");
    if (money > account.getBalance()) {
      System.out.println("계좌에 금액이 부족합니다.");
      return;
    } else if (money == 0) {
      System.out.println("0원은 송금할 수 없습니다.");
    }
    account.setBalance(account.getBalance() - money);

    String depositNum = prompt.inputString("입금할 계좌번호를 입력하세요 : ");
    
    Account depositAccount = accountDao.findAccount(depositNum);
 
    if (depositAccount == null) {
      System.out.println("입금하실 계좌가 존재하지 않습니다.");
      return;
      
    } else {
      depositAccount.setBalance(depositAccount.getBalance() + money);
      System.out.println("-------------------");
      System.out.printf("$%,d 송금이 완료되었습니다.\n", money);
      System.out.println("-------------------");
      System.out.printf("입금자명 : %s\n", account.getName());
      System.out.printf("출금계좌번호 : %s\n",
          account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      System.out.printf("거래 후 잔액 : $%,d\n", account.getBalance());
      System.out.println("-------------------");
      System.out.printf("이름 : %s\n", depositAccount.getName());
      System.out.printf("입금계좌번호 : %s\n",
          depositAccount.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      System.out.printf("은행이름 : %s\n", depositAccount.getBankName());
      System.out.printf("송금액 : $%,d\n", money);
      System.out.println("-------------------");
      
      accountDao.transfer(account);
    }

  }
}
