package bank_project.myapp.handler;

import bank_project.myapp.vo.Member;
import bank_project.util.Prompt;

public class MemberHandler {

  static final int MAX_SIZE = 50;
  static Member[] members = new Member[MAX_SIZE];
  static String[] bankName = {"신한은행", "국민은행", "기업은행", "하나은행", "농협은행"};


  static int length = 0;

  public static void inputAccounts() {
    if (!available()) { // 더이상 Heap메모리가 할당안될 경우 입력불가
      System.out.println("더이상 입력할 수 없습니다.");
      return;
    }

    Member m = new Member(); // 입력데이터 배열로 저장
    m.setName(Prompt.inputString("이름 ? "));
    while (true) {
      m.setPassword(Prompt.inputString("비밀번호 4자리를 입력하세요. : "));
      if (m.getPassword().matches("\\d{4}")) {
        break;
      }
      System.out.println("숫자 4자리만 입력해 주세요");
    }

    while (true) {
      m.setAccNum(Prompt.inputString("계좌번호를 입력하세요. ex) 12345678 : "));
      if (m.getAccNum().matches("\\d{8}")) {
        break;
      }
      System.out.println("8자리의 '숫자'만 입력해 주세요");
    }

    while (true) {
      System.out.println("은행을 선택하세요.");
      int num = Prompt.inputInt("1. 신한은행 2.국민은행 3.기업은행 4.하나은행 5.농협은행 \n");
      if (num >= 1 && num <= 5) {
        m.setBankName(bankName[num - 1]);
        break;
      } else {
        System.out.println("무효한 번호 입니다.");
      }
    }

    String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
    m.setBalance(0);
    members[length++] = m;

    System.out.println("축하합니다 계좌가 개설 되었습니다.");
    System.out.println("-------------------");
    System.out.println("이름 : " + m.getName());
    System.out.println("계좌비밀번호 : " + m.getPassword());
    System.out.println("계좌번호 : " + formattedAccNum);
    System.out.println("은행명 : " + m.getBankName());
    System.out.println("잔고 : " + m.getBalance());
    System.out.println("-------------------");
  }

  public static void deposit() { // 입금
    while (true) {
      Member m = findAccountNumber(Prompt.inputString("계좌번호 : "));
      Member mp = findPassword(Prompt.inputString("비밀번호 : "));

      if (m == null) {
        System.out.println("계좌번호를 찾을수 없습니다.");
      } else if (mp == null) {
        System.out.println("비밀번호가 다릅니다.");
      } else {
        while (true) {
          int plus = Prompt.inputInt("입금금액 : ");
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

  public static void withraw() { // 출금
    while (true) {
      Member m = findAccountNumber(Prompt.inputString("계좌번호 : "));
      Member mp = findPassword(Prompt.inputString("비밀번호 : "));
      if (m == null) {
        System.out.println("계좌번호를 찾을수 없습니다.");
      } else if (mp == null) {
        System.out.println("비밀번호가 다릅니다.");
      } else {
        System.out.printf("출금 가능금액 : %,d \n", m.getBalance());

        while (true) {
          int minus = Prompt.inputInt("출금금액 : ");
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

  public static Member findAccountNumber(String accNum) { // 계좌리스트 조회
    for (int i = 0; i < length; i++) {
      if (members[i].getAccNum().equals(accNum))
        return members[i];
    }
    return null;
  }

  public static Member findPassword(String password) { // 패스워드 리스트 조회
    for (int i = 0; i < length; i++) {
      if (members[i].getPassword().equals(password))
        return members[i];
    }
    return null;
  }

  public static Member findBankName(String bankName) { // 은행 찾기
    for (int i = 0; i < length; i++) {
      if (members[i].getBankName().equals(bankName))
        return members[i];
    }
    return null;
  }

  public static void printAccounts() { // 계좌리스트

    System.out.println("-----------------------------------------------");
    System.out.println("번호, 이름, 계좌번호, 은행명, 계좌잔액");
    System.out.println("------------------------------------------------");

    for (int i = 0; i < length; i++) {
      Member m = members[i];
      String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
      System.out.printf("%d, %s, %s, %s, $%d\n", m.getNo(), m.getName(), formattedAccNum,
          m.getBankName(), m.getBalance());
    }
  }

  public static void viewAccount() { // 계좌정보 확인
    while (true) {
      String memberNo = Prompt.inputString("계좌번호? ");
      boolean accountNumberFound = false;

      for (int i = 0; i < length; i++) {
        Member m = members[i];
        String formattedAccNum = m.getAccNum().replaceAll("(\\d{4})(\\d{4})", "$1-$2");
        if (m.getAccNum().equals(memberNo)) {
          System.out.println("-------------------");
          System.out.printf("이름 : %s\n", m.getName());
          System.out.printf("계좌번호 : %s\n", formattedAccNum);
          System.out.printf("은행이름 : %s\n", m.getBankName());
          System.out.printf("잔고 : %d\n", m.getBalance());
          System.out.println("-------------------");
          accountNumberFound = true;
          break;
        }
      }

      if (!accountNumberFound) {
        System.out.println("해당 계좌번호의 회원이 없습니다.");
      } else {
        break;
      }
    }
  }

  public static void updateAccount() { // 계좌 비밀번호 변경
    while (true) {
      String memberNo = Prompt.inputString("계좌번호? ");
      String passNo = Prompt.inputString("비밀번호? ");
      boolean memberFound = false;

      for (int i = 0; i < length; i++) {
        Member m = members[i];
        if (m.getAccNum().equals(memberNo) && m.getPassword().equals(passNo)) {
          System.out.printf("이름(%s) :", m.getName());
          m.setName(Prompt.inputString(""));
          System.out.print("새 비밀번호 : ");
          m.setPassword(Prompt.inputString(""));
          System.out.println("변경이 완료되었습니다.");
          memberFound = true;
          break;
        }
      }

      if (!memberFound) {
        System.out.println("계좌번호 또는 비밀번호가 일치하지 않습니다.");
      } else {
        break;
      }
    }
  }

  public static void deleteMember() { // 계좌삭제
    while (true) {
      printAccounts();
      int memberNo = Prompt.inputInt("번호? ");
      int deleteAccount = indexOf(memberNo);
      if (deleteAccount == -1) {
        System.out.println("해당 번호에 회원정보가 없습니다.");
        continue;
      }
      for (int i = deleteAccount; i < length - 1; i++) {
        members[i] = members[i + 1];
      }
      members[length--] = null;
      break;
    }
  }

  private static int indexOf(int memberNo) { // 삭제시 인덱스번호 조회
    for (int i = 0; i < length; i++) {
      Member m = members[i];
      if (m.getNo() == memberNo) {
        return i;
      }
    }
    return -1;
  }

  private static boolean available() {
    return length < MAX_SIZE;
  }



}
