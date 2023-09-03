package bank_project.myapp.handler;

import java.io.IOException;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.vo.Account;
import bank_project.util.ActionListener;
import bank_project.util.BreadcrumbPrompt;

public class AccountAddListener implements ActionListener {

  private String[] bankName = {"신한은행", "국민은행", "기업은행", "하나은행", "농협은행"};

  AccountDao accountDao;

  public AccountAddListener(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {

    Account account = new Account(); // 입력데이터 배열로 저장

    account.setName(prompt.inputString("이름 ? "));

    while (true) {
      account.setPassword(prompt.inputString("비밀번호 4자리를 입력하세요. : "));
      if (account.getPassword().matches("\\d{4}")) {
        break;
      }
      System.out.println("숫자 4자리만 입력해 주세요");
    }

    while (true) {
      account.setAccNum(prompt.inputString("계좌번호를 입력하세요. ex) 12345678 : "));
      if (account.getAccNum().matches("\\d{8}")) {
        break;
      }
      System.out.println("8자리의 '숫자'만 입력해 주세요");
    }

    while (true) {
      System.out.println("은행을 선택하세요.");
      int num = prompt.inputInt("1. 신한은행 2.국민은행 3.기업은행 4.하나은행 5.농협은행 \n");
      if (num >= 1 && num <= 5) {
        account.setBankName(bankName[num - 1]);
        break;
      } else {
        System.out.println("무효한 번호 입니다.");
      }
    }

    String formattedAccNum = account.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");

    account.setBalance(0);

    System.out.println("축하합니다 계좌가 개설 되었습니다.");
    System.out.println("-------------------");
    System.out.println("이름 : " + account.getName());
    System.out.println("계좌비밀번호 : " + account.getPassword());
    System.out.println("계좌번호 : " + formattedAccNum);
    System.out.println("은행명 : " + account.getBankName());
    System.out.printf("잔고 : $%d\n", account.getBalance());
    System.out.println("-------------------");

    accountDao.insert(account);

  }
}
