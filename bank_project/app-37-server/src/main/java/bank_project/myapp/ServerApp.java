package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
import bank_project.myapp.handler.LoginListener;
import bank_project.net.NetProtocol;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.Menu;
import bank_project.util.MenuGroup;

public class ServerApp {
  
  ExecutorService threadPool = Executors.newFixedThreadPool(10);
  
  Connection con;
  CustomerDao customerDao;
  AccountDao accountDao;
  BoardDao boardDao;
  
  MenuGroup mainMenu = new MenuGroup("메인");
  
  int port;

  public ServerApp(int port) throws Exception {
    
    this.port = port;
    
    con = DriverManager.getConnection("jdbc:mysql://bank:1@localhost:3306/bankdb");

    this.customerDao = new MySQLCustomerDao(con);
    this.accountDao = new MySQLAccountDao(con);
    this.boardDao = new MySQLBoardDao(con);
    
    prepareMenu();
  }

  public void close() throws Exception {
    con.close();

  }

  public static void main(String[] args) throws Exception {

    ServerApp app = new ServerApp(8888);
    app.execute();
    app.close();
  }

  public void execute() {

    try (ServerSocket serverSocket = new ServerSocket(this.port)) {
      System.out.println("서버 실행 중...");

      while (true) {
        Socket socket = serverSocket.accept();
        threadPool.execute(() -> processRequest(socket));
      }
    } catch (Exception e) {
      System.out.println("서버 실행 오류!");
      e.printStackTrace();
    }

  }
  
  private void processRequest(Socket socket) {
    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      BreadcrumbPrompt prompt = new BreadcrumbPrompt(in, out);

      InetSocketAddress clientAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s 클라이언트 접속함!\n", clientAddress.getHostString());

      out.writeUTF("[은행 업무 프로그램]\n"
          + "-----------------------------------------");

      new LoginListener(customerDao).service(prompt);

      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    }
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
