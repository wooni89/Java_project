package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.List;
import bank_project.util.Prompt;

public class MemberHandler implements Handler {

  private List list;
  private Prompt prompt;
  private String title;
  private String[] bankName = {"신한은행", "국민은행", "기업은행", "하나은행", "농협은행"};

  public MemberHandler(Prompt prompt, String title, List list) {
    this.prompt = prompt;
    this.title = title;
    this.list = list;
  }

  public void execute() {
    printMenu();

    while (true) {
      String menuNo = prompt.inputString("%s > ", title);
      if (menuNo.equals("0")) {
        break;
      } else if (menuNo.equals("menu")) {
        printMenu();
      } else if (menuNo.equals("1")) {
        this.inputAccounts();
      } else if (menuNo.equals("2")) {
        this.printAccounts();
      } else if (menuNo.equals("3")) {
        this.viewAccount();
      } else if (menuNo.equals("4")) {
        this.updateAccount();
      } else if (menuNo.equals("5")) {
        this.deleteAccount();
      } else {
        System.out.println("메뉴 번호를 다시 눌러주세요");
      }
    }
  }

  private static void printMenu() {
    System.out.println("[1] 계좌개설");
    System.out.println("[2] 계좌리스트");
    System.out.println("[3] 계좌조회");
    System.out.println("[4] 계좌 비밀번호 변경");
    System.out.println("[5] 계좌삭제");
    System.out.println("[0] 메인메뉴 돌아가기");
  }

  private void inputAccounts() {

    Member m = new Member(); // 입력데이터 배열로 저장

    m.setName(this.prompt.inputString("이름 ? "));

    while (true) {
      m.setPassword(this.prompt.inputString("비밀번호 4자리를 입력하세요. : "));
      if (m.getPassword().matches("\\d{4}")) {
        break;
      }
      System.out.println("숫자 4자리만 입력해 주세요");
    }

    while (true) {
      m.setAccNum(this.prompt.inputString("계좌번호를 입력하세요. ex) 12345678 : "));
      if (m.getAccNum().matches("\\d{8}")) {
        break;
      }
      System.out.println("8자리의 '숫자'만 입력해 주세요");
    }

    while (true) {
      System.out.println("은행을 선택하세요.");
      int num = this.prompt.inputInt("1. 신한은행 2.국민은행 3.기업은행 4.하나은행 5.농협은행 \n");
      if (num >= 1 && num <= 5) {
        m.setBankName(bankName[num - 1]);
        break;
      } else {
        System.out.println("무효한 번호 입니다.");
      }
    }

    String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");

    m.setBalance(0);

    System.out.println("축하합니다 계좌가 개설 되었습니다.");
    System.out.println("-------------------");
    System.out.println("이름 : " + m.getName());
    System.out.println("계좌비밀번호 : " + m.getPassword());
    System.out.println("계좌번호 : " + formattedAccNum);
    System.out.println("은행명 : " + m.getBankName());
    System.out.println("잔고 : " + m.getBalance());
    System.out.println("-------------------");

    this.list.add(m);

  }

  public void printAccounts() { // 계좌리스트

    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    for (int i = 0; i < this.list.size(); i++) {
      Member m = (Member) this.list.get(i);
      String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
      System.out.printf("%d, %s, %s, %s, $%d\n", m.getNo(), m.getName(), formattedAccNum,
          m.getBankName(), m.getBalance());
    }
  }

  public void viewAccount() { // 계좌정보 확인

    while (true) {
      String accnum = this.prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = this.prompt.inputString("비밀번호를 입력 하세요 : ");
      Member m = findMember(accnum, pwd);

      if (m == null) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
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

      System.out.println("-------------------");
      System.out.printf("이름 : %s\n", m.getName());
      System.out.printf("계좌번호 : %s\n", m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
      System.out.printf("은행이름 : %s\n", m.getBankName());
      System.out.printf("잔고 : %d\n", m.getBalance());
      System.out.println("-------------------");
      break;
    }

  }



  public void updateAccount() { // 계좌 비밀번호 변경

    while (true) {
      String accnum = this.prompt.inputString("조회할 계좌번호 8자리를 입력하세요 : ");
      String pwd = this.prompt.inputString("비밀번호를 입력 하세요 : ");
      Member m = findMember(accnum, pwd);

      if (m == null) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
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

      m.setName(this.prompt.inputString("이름(%s) :", m.getName()));
      m.setPassword(this.prompt.inputString("새 비밀번호 : "));
      System.out.println("변경이 완료되었습니다.");
      break;
    }


  }

  private void deleteAccount() { // 계좌삭제
    while (true) {
      printAccounts();
      String accNum = this.prompt.inputString("삭제할 계좌번호를 입력하세요 : ");
      String pwd = this.prompt.inputString("비밀번호를 입력하세요 : ");
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


  private Member findMember(String accNum, String pwd) {
    for (int i = 0; i < this.list.size(); i++) {
      Member member = (Member) list.get(i);
      if (member.getAccNum().equals(accNum) && member.getPassword().equals(pwd)) {
        return member;
      }
    }
    return null;
  }


}
