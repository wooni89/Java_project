package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.List;

public class MemberDeleteListener extends AbstractAccountListener {

  public MemberDeleteListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    while (true) {
      String accNum = prompt.inputString("삭제할 계좌번호를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력하세요 : ");
      Member account = new Member(accNum, pwd);

      boolean removed = this.list.remove(account);

      if (removed) {
        System.out.println("계좌가 정상적으로 삭제되었습니다. 이용해주셔서 감사합니다.");
      } else {
        System.out.println("계좌 삭제에 실패했습니다. 다시 시도해주세요.");
      }
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
