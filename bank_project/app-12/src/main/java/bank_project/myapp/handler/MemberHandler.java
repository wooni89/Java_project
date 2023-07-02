package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.Prompt;

public class MemberHandler implements Handler {

  private ArrayList list = new ArrayList();
  private Prompt prompt;
  private String title;
  private String[] bankName = {"신한은행", "국민은행", "기업은행", "하나은행", "농협은행"};

  public MemberHandler(Prompt prompt, String title) {
    this.prompt = prompt;
    this.title = title;
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

  public void deposit() { // 입금
    while (true) {
      Member m = (Member) this.list.get(this.prompt.inputString("계좌번호 : "));
      Member mp = (Member) this.list.get(this.prompt.inputString("비밀번호 : "));

      if (m == null) {
        System.out.println("계좌번호를 찾을수 없습니다.");
      } else if (mp == null) {
        System.out.println("비밀번호가 다릅니다.");
      } else {
        while (true) {
          int plus = this.prompt.inputInt("입금금액 : ");
          if (plus > 0) {
            m.getBalancePlus(plus);
            System.out.println("-------------------");
            System.out.printf("이름 : %s\n", m.getName());
            System.out.printf("은행명 : %s\n", m.getBankName());
            System.out.printf("입금 후 잔액 : %,d \n", m.getBalance());
            System.out.println("-------------------");
            break;
          } else if (plus == 0) {
            System.out.println("0 이하의 금액은 입금이 불가합니다.");
          }
        }
        break;
      }
    }
  }

  public void withraw() { // 출금
    while (true) {
      Member m = (Member) this.list.get(this.prompt.inputString("계좌번호 : "));
      Member mp = (Member) this.list.get(this.prompt.inputString("비밀번호 : "));
      if (m == null) {
        System.out.println("계좌번호를 찾을수 없습니다.");
      } else if (mp == null) {
        System.out.println("비밀번호가 다릅니다.");
      } else {
        System.out.printf("출금 가능금액 : %,d \n", m.getBalance());

        while (true) {
          int minus = this.prompt.inputInt("출금금액 : ");
          if (m.getBalance() < minus) {
            System.out.println("잔고가 부족하여 출금이 불가합니다.");
          } else if (minus == 0) {
            System.out.println("0원은 출금할 수 없습니다.");
          } else {
            m.getBalanceMinus(minus);
            System.out.println("-------------------");
            System.out.printf("이름 : %s\n", m.getName());
            System.out.printf("은행명 : %s\n", m.getBankName());
            System.out.printf("출금 후 잔액 : %,d \n", m.getBalance());
            System.out.println("-------------------");
            return;
          }
          continue;
        }
      }
    }
  }



  public void printAccounts() { // 계좌리스트

    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    Object[] arr = this.list.list();

    for (Object obj : arr) {
      Member m = (Member) obj;
      String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
      System.out.printf("%d, %s, %s, %s, $%d\n", m.getNo(), m.getName(), formattedAccNum,
          m.getBankName(), m.getBalance());
    }
  }

  public void viewAccount() { // 계좌정보 확인

    Member m = (Member) this.list.get(new Member(this.prompt.inputString("조회할 계좌번호를 입력하세요 : ")));

    if (m == null) {
      System.out.println("해당 계좌번호의 회원이 없습니다.");
      return;
    }

    System.out.println("-------------------");
    System.out.printf("이름 : %s\n", m.getName());
    System.out.printf("계좌번호 : %s\n", m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2"));
    System.out.printf("은행이름 : %s\n", m.getBankName());
    System.out.printf("잔고 : %d\n", m.getBalance());
    System.out.println("-------------------");
    // String accNum ="1234";
    // Member member= Member.getMemberOnlyAccNum(accNum);

  }


  public void updateAccount() { // 계좌 비밀번호 변경
    while (true) {
      Member m = (Member) this.list.get(new Member(this.prompt.inputString("계좌번호? ")));
      Member mp = (Member) this.list.get(new Member(this.prompt.inputString("비밀번호? ")));
      if (m == null) {
        System.out.println("계좌번호가 일치하지 않습니다.");
        return;
      } else if (mp == null) {
        System.out.println("비밀번호가 일치하지 않습니다.");
        return;
      }

      m.setName(this.prompt.inputString("이름(%s) :", m.getName()));
      m.setPassword(this.prompt.inputString("새 비밀번호 : "));
      System.out.println("변경이 완료되었습니다.");
      break;
    }
  }

  public void deleteAccount() { // 계좌삭제
    while (true) {
      printAccounts();

      if (!this.list.delete(new Member(this.prompt.inputString("삭제할 계좌번호를 입력하세요 : ")))) {
        System.out.println("해당 번호에 회원정보가 없습니다.");
      }
      break;
    }
  }



}
