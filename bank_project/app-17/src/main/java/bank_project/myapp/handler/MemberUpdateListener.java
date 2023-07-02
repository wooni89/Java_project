package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.List;

public class MemberUpdateListener extends AbstractAccountListener {

  public MemberUpdateListener(List list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {
    while (true) {
      String accnum = prompt.inputString("변경할 계좌번호 8자리를 입력하세요 : ");
      String pwd = prompt.inputString("비밀번호를 입력 하세요 : ");
      Member m = findMember(accnum, pwd);

      if (m == null) {
        System.out.println("회원이 없습니다.");
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

      m.setName(prompt.inputString("이름(%s) :", m.getName()));
      m.setPassword(prompt.inputString("새 비밀번호 : "));
      System.out.println("변경이 완료되었습니다.");
      break;
    }

  }
}
