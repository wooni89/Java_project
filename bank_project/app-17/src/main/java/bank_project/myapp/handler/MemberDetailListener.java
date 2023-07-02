package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.List;

public class MemberDetailListener extends AbstractAccountListener {

  public MemberDetailListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    while (true) {
      String accnum = prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Member m = findMember(accnum, pwd);

      if (m == null) {
        System.out.println("회원이 없습니다.");
        return;
      }
      if (!m.getAccNum().equals(accnum)) {
        System.out.println("입력한 계좌번호가 없습니다.");
        return;
      }
      if (!m.getPassword().equals(pwd)) {
        System.out.println("비밀번호가 다릅니다.");
        return;
      }

      System.out.println("-------------------");
      System.out.printf("이름 : %s\n", m.getName());
      System.out.printf("계좌번호 : %s\n", m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      System.out.printf("은행이름 : %s\n", m.getBankName());
      System.out.printf("잔고 : $%d\n", m.getBalance());
      System.out.println("-------------------");
      break;
    }

  }

  protected Member findMember(String accNum, String pwd) {
    for (int i = 0; i < this.list.size(); i++) {
      Member member = (Member) list.get(i);
      if (member.getAccNum().equals(accNum) && member.getPassword().equals(pwd)) {
        return member;
      }
    }
    return null;
  }

}
