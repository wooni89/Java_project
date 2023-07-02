package bank_project.myapp.handler;

import java.util.Iterator;
import java.util.List;
import bank_project.myapp.vo.Member;
import bank_project.util.BreadcrumbPrompt;

public class MemberListListener extends AbstractAccountListener {

  public MemberListListener(List<Member> list) {
    super(list);
  }

  @Override
  public void service(BreadcrumbPrompt prompt) {

    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    Iterator<Member> iterator = list.iterator();
    while (iterator.hasNext()) {
      Member m = iterator.next();
      String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
      System.out.printf("%d, %s, %s, %s, $%d\n", m.getNo(), m.getName(), formattedAccNum,
          m.getBankName(), m.getBalance());
    }

  }

}
