package bank_project.myapp.handler;

import java.io.IOException;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Component;

@Component("/account/deposit")
public class BankingDepositListener implements ActionListener {

  AccountDao accountDao;
  SqlSessionFactory sqlSessionFactory;

  public BankingDepositListener(AccountDao accountDao, SqlSessionFactory sqlSessionFactory) {
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
      prompt.println("해당 회원이 없습니다.");
      return;
    }
    
    while (true) {
      int plus = prompt.inputInt("입금금액 : $ ");
      if (plus > 0) {
        account.getBalancePlus(plus);
        prompt.println("-------------------");
        prompt.printf("이름 : %s\n", account.getName());
        prompt.printf("은행명 : %s\n", account.getBankName());
        prompt.printf("계좌번호 : %s\n",
            account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
        prompt.printf("입금 후 잔액 : $%,d \n", account.getBalance());
        prompt.println("-------------------");
        

        try {
          accountDao.deposit(account);
          sqlSessionFactory.openSession(false).commit();

        } catch (Exception e) {
          sqlSessionFactory.openSession(false).rollback();
          throw new RuntimeException(e);
        }
        break;
        
      } else if (plus == 0) {
        prompt.println("$0 이하의 금액은 입금이 불가합니다.");
      }
    }
  }


}
