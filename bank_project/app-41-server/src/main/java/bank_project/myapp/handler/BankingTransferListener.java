package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Component;

@Component("/account/transfer")
public class BankingTransferListener implements ActionListener {

  AccountDao accountDao;
  SqlSessionFactory sqlSessionFactory;

  public BankingTransferListener(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
    this.accountDao = accountDao;
    this.sqlSessionFactory = sqlSessionFactory;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
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

    int money = prompt.inputInt("출금액을 입력하세요 : ");
    if (money > account.getBalance()) {
      prompt.println("계좌에 금액이 부족합니다.");
      return;
    } else if (money == 0) {
      prompt.println("0원은 송금할 수 없습니다.");
    }
    account.setBalance(account.getBalance() - money);

    
    String depositNum = prompt.inputString("입금할 계좌번호를 입력하세요 : ");
    Account middleAccount = new Account();
    middleAccount.setAccNum(depositNum);
    Account depositAccount = accountDao.findAccount(middleAccount);
 
    if (depositAccount == null) {
      prompt.println("입금하실 계좌가 존재하지 않습니다.");
      return;
      
    } else {
      prompt.println("-------------------");
      prompt.printf("$%,d 송금이 완료되었습니다.\n", money);
      prompt.println("-------------------");
      prompt.printf("입금자명 : %s\n", account.getName());
      prompt.printf("출금계좌번호 : %s\n",
          account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      prompt.printf("거래 후 잔액 : $%,d\n", account.getBalance());
      prompt.println("-------------------");
      prompt.printf("이름 : %s\n", depositAccount.getName());
      prompt.printf("입금계좌번호 : %s\n",
          depositAccount.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      prompt.printf("은행이름 : %s\n", depositAccount.getBankName());
      prompt.printf("송금액 : $%,d\n", money);
      prompt.println("-------------------");
      depositAccount.setBalance(depositAccount.getBalance() + money);
      
      try {
        accountDao.transfer(account);
        accountDao.transfer(depositAccount);
        sqlSessionFactory.openSession(false).commit();

      } catch (Exception e) {
        sqlSessionFactory.openSession(false).rollback();
        throw new RuntimeException(e);
      }
    }

  }
}
