package bank_project.myapp.handler;


import bank_project.myapp.vo.Member;
import bank_project.util.ActionListener;
import bank_project.util.List;

public abstract class AbstractAccountListener implements ActionListener {

  protected List<Member> list;

  public AbstractAccountListener(List<Member> list) {
    this.list = list;
  }

  protected Member findMember(String accNum, String pwd) {
    for (int i = 0; i < this.list.size(); i++) {
      Member member = list.get(i);
      if (member.getAccNum().equals(accNum) && member.getPassword().equals(pwd)) {
        return member;
      }
    }
    return null;
  }


}
