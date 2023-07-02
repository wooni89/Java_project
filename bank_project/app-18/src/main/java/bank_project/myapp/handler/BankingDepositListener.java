package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.List;

public class BankingDepositListener extends AbstractAccountListener {

  public BankingDepositListener(List<Member> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    String accnum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
    String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
    Member m = findMember(accnum, pwd);

    if (m == null) {
      System.out.println("해당 회원이 없습니다.");
      return;
    }
    if (!m.getAccNum().equals(accnum)) {
      System.out.println("해당 계좌번호의 회원이 없습니다.");
      return;
    }
    if (!m.getPassword().equals(pwd)) {
      System.out.println("비밀번호가 다릅니다.");
      return;
    }
    while (true) {
      int plus = prompt.inputInt("입금금액 : $ ");
      if (plus > 0) {
        m.getBalancePlus(plus);
        System.out.println("-------------------");
        System.out.printf("이름 : %s\n", m.getName());
        System.out.printf("은행명 : %s\n", m.getBankName());
        System.out.printf("계좌번호 : %s\n", m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
        System.out.printf("입금 후 잔액 : $%,d \n", m.getBalance());
        System.out.println("-------------------");
        break;
      } else if (plus == 0) {
        System.out.println("$0 이하의 금액은 입금이 불가합니다.");
      }
    }

  }


}
