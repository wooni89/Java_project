package bank_project.myapp;


import java.sql.Connection;
import java.sql.DriverManager;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.dao.MySQLAccountDao;
import bank_project.myapp.dao.MySQLBoardDao;
import bank_project.myapp.dao.MySQLCustomerDao;
import bank_project.myapp.handler.AccountAddListener;
import bank_project.myapp.handler.AccountDeleteListener;
import bank_project.myapp.handler.AccountDetailListener;
import bank_project.myapp.handler.AccountListListener;
import bank_project.myapp.handler.AccountUpdateListener;
import bank_project.myapp.handler.BankingDepositListener;
import bank_project.myapp.handler.BankingTransferListener;
import bank_project.myapp.handler.BankingWithrawListener;
import bank_project.myapp.handler.BoardAddListener;
import bank_project.myapp.handler.BoardDeleteListener;
import bank_project.myapp.handler.BoardDetailListener;
import bank_project.myapp.handler.BoardListListener;
import bank_project.myapp.handler.BoardUpdateListener;
import bank_project.myapp.handler.CustomerAddListener;
import bank_project.myapp.handler.CustomerDeleteListener;
import bank_project.myapp.handler.CustomerDetailListener;
import bank_project.myapp.handler.CustomerListListener;
import bank_project.myapp.handler.CustomerUpdateListener;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Menu;
import bank_project.util.MenuGroup;

public class ClientApp {
  
  CustomerDao customerDao;
  AccountDao accountDao;
  BoardDao boardDao;

  BreadcrumbPrompt prompt = new BreadcrumbPrompt();
  
  MenuGroup mainMenu = new MenuGroup("메인");

  public ClientApp(String ip, int port) throws Exception {
    
    Connection con = DriverManager.getConnection("jdbc:mysql://bank:1@localhost:3306/bankdb");

    this.customerDao = new MySQLCustomerDao(con);
    this.accountDao = new MySQLAccountDao(con);
    this.boardDao = new MySQLBoardDao(con);
    prepareMenu();
  }

  public void close() throws Exception {
    prompt.close();

  }

  public static void main(String[] args) throws Exception {

    if (args.length < 2) {
      System.out.println(" 실행 예) java ... bitcamp.myapp.ClientApp 서버주소 포트번호");
      return;
    }
    ClientApp app = new ClientApp(args[0], Integer.parseInt(args[1]));

    app.execute();
    app.close();
  }

  static void printTitle() {

    System.out.println("은행 업무 프로그램");
    System.out.println("----------------------------------");

  }

  public void execute() {

    printTitle();
    mainMenu.execute(prompt);

  }

  private void prepareMenu() {
    
    MenuGroup customerMenu = new MenuGroup("신규회원메뉴");
    customerMenu.add(new Menu("회원등록", new CustomerAddListener(customerDao)));
    customerMenu.add(new Menu("회원목록", new CustomerListListener(customerDao)));
    customerMenu.add(new Menu("정보조회", new CustomerDetailListener(customerDao)));
    customerMenu.add(new Menu("개인정보변경", new CustomerUpdateListener(customerDao)));
    customerMenu.add(new Menu("탈퇴", new CustomerDeleteListener(customerDao)));
    mainMenu.add(customerMenu);

    MenuGroup memberMenu = new MenuGroup("계좌업무");
    memberMenu.add(new Menu("계좌개설", new AccountAddListener(accountDao)));
    memberMenu.add(new Menu("계좌리스트", new AccountListListener(accountDao)));
    memberMenu.add(new Menu("계좌조회", new AccountDetailListener(accountDao)));
    memberMenu.add(new Menu("계좌비밀번호 변경", new AccountUpdateListener(accountDao)));
    memberMenu.add(new Menu("계좌삭제", new AccountDeleteListener(accountDao)));
    mainMenu.add(memberMenu);

    MenuGroup bankingMenu = new MenuGroup("은행업무");
    bankingMenu.add(new Menu("입금", new BankingDepositListener(accountDao)));
    bankingMenu.add(new Menu("출금", new BankingWithrawListener(accountDao)));
    bankingMenu.add(new Menu("계좌이체", new BankingTransferListener(accountDao)));
    mainMenu.add(bankingMenu);

    MenuGroup boardMenu = new MenuGroup("게시글");
    boardMenu.add(new Menu("고객불만작성", new BoardAddListener(boardDao)));
    boardMenu.add(new Menu("게시글목록", new BoardListListener(boardDao)));
    boardMenu.add(new Menu("게시글조회", new BoardDetailListener(boardDao)));
    boardMenu.add(new Menu("게시글변경", new BoardUpdateListener(boardDao)));
    boardMenu.add(new Menu("게시글삭제", new BoardDeleteListener(boardDao)));
    mainMenu.add(boardMenu);

  }

}
