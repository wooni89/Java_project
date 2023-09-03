package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.ibatis.session.SqlSessionFactory;
import bank_project.myapp.config.AppConfig;
import bank_project.net.NetProtocol;
import bank_project.util.ApplicationContext;
import bank_project.util.BreadcrumbPrompt;
import bank_project.util.DispatcherListener;
import bank_project.util.MenuGroup;
import bank_project.util.SqlSessionFactoryProxy;

public class ServerApp {
  
  ExecutorService threadPool = Executors.newFixedThreadPool(10);
  
  MenuGroup mainMenu = new MenuGroup("/", "메인");
  
  ApplicationContext iocContainer;
  DispatcherListener facadeListener;
  
  int port;

  public ServerApp(int port) throws Exception {
    
    this.port = port;
    iocContainer = new ApplicationContext(AppConfig.class);
    facadeListener = new DispatcherListener(iocContainer);
    
    prepareMenu();
  }

  public void close() throws Exception {

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

      prompt.setAttribute("menuPath", "/auth/login");
      facadeListener.service(prompt);
      
      mainMenu.execute(prompt);
      out.writeUTF(NetProtocol.NET_END);

    } catch (Exception e) {
      System.out.println("클라이언트 통신 오류!");
      e.printStackTrace();
    } finally {
      SqlSessionFactoryProxy sqlSessionFactoryProxy =
          (SqlSessionFactoryProxy) iocContainer.getBean(SqlSessionFactory.class);
      sqlSessionFactoryProxy.clean();
    }
  }


  private void prepareMenu() {
    
    MenuGroup customerMenu = new MenuGroup("/customer", "회원");
    customerMenu.add("/customer/add", "회원등록", facadeListener);
    customerMenu.add("/customer/list", "회원목록", facadeListener);
    customerMenu.add("/customer/detail", "회원정보", facadeListener);
    customerMenu.add("/customer/update", "회원정보변경", facadeListener);
    customerMenu.add("/customer/delete", "회원삭제", facadeListener);
    mainMenu.add(customerMenu);

    MenuGroup accountMenu = new MenuGroup("/account", "계좌업무");
    accountMenu.add("/account/add", "계좌개설", facadeListener);
    accountMenu.add("/account/list", "계좌리스트", facadeListener);
    accountMenu.add("/account/detail", "계좌조회", facadeListener);
    accountMenu.add("/account/update", "계좌비밀번호 변경", facadeListener);
    accountMenu.add("/account/delete", "계좌삭제", facadeListener);
    mainMenu.add(accountMenu);

    MenuGroup bankingMenu = new MenuGroup("/account", "은행업무");
    bankingMenu.add("/account/deposit", "입금", facadeListener);
    bankingMenu.add("/account/withraw", "출금", facadeListener);
    bankingMenu.add("/account/transfer", "계좌이체", facadeListener);
    mainMenu.add(bankingMenu);
    
    MenuGroup boardMenu = new MenuGroup("/board", "고객VOC");
    boardMenu.add("/board/add", "고객불만작성", facadeListener);
    boardMenu.add("/board/list", "게시글목록", facadeListener);
    boardMenu.add("/board/detail", "게시글조회", facadeListener);
    boardMenu.add("/board/update", "게시글변경", facadeListener);
    boardMenu.add("/board/delete", "게시글삭제", facadeListener);
    mainMenu.add(boardMenu);
  }

}
