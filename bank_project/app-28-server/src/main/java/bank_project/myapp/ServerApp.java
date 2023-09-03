package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import com.google.gson.reflect.TypeToken;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.AccountListDao;
import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.dao.BoardListDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Board;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class ServerApp {

  int port;
  ServerSocket serverSocket;

  AccountDao accountDao = new AccountListDao("member.json");
  BoardDao boardDao = new BoardListDao("board.json");

  public ServerApp(int port) throws Exception {
    this.port = port;
  }

  public void close() throws Exception {

    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.out.println(" 실행 예) java ... bitcamp.myapp.ClientApp 포트번호");
      return;
    }
    ServerApp app = new ServerApp(Integer.parseInt(args[0]));

    app.execute();
    app.close();

  }

  public void execute() throws Exception {

    System.out.println("[MyList 서버 Application]");
    this.serverSocket = new ServerSocket(port);
    System.out.println("Server Runing");

    Socket socket = serverSocket.accept();
    DataInputStream in = new DataInputStream(socket.getInputStream());
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    while (true) {
      RequestEntity request = RequestEntity.fromJson(in.readUTF());

      String command = request.getCommand();
      System.out.println(command);


      if (command.equals("quit")) {
        break;
      }

      ResponseEntity response = new ResponseEntity();

      switch (command) {
        case "board/list":
          response.status(ResponseEntity.SUCCESS).result(boardDao.list());
          break;
        case "board/insert":
          boardDao.insert(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "board/findBy":
          Board board = boardDao.findBy(request.getObject(Integer.class));
          if (board == null) {
            response.status(ResponseEntity.FAILURE).result("해당번호의 게시글이 없습니다.");
          } else {
            response.status(ResponseEntity.SUCCESS).result(board);
          }
          break;
        case "board/update":
          int value = boardDao.update(request.getObject(Board.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;
        case "board/delete":
          value = boardDao.delete(request.getObject(Integer.class));
          response.status(ResponseEntity.SUCCESS).result(value);
          break;

        case "account/insert":
          accountDao.insert(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "account/list":
          response.status(ResponseEntity.SUCCESS).result(accountDao.list());
          break;
          
        case "account/findAccount":
          String withrawAccount = request.getObject(String.class);
          Account findAccount = accountDao.findAccount(withrawAccount);
          if (findAccount == null) {
            response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다.");
          } else {
            response.status(ResponseEntity.SUCCESS).result(findAccount);
          }
          break;

        case "account/findAccountPassword":
          HashMap<String, Object> requestData =
              request.getObject(new TypeToken<HashMap<String, Object>>() {}.getType());
          String accNum = (String) requestData.get("accNum");
          String pwd = (String) requestData.get("pwd");
          Account account = accountDao.findAccountPassword(accNum, pwd);
          if (account == null) {
            response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다.");
          } else {
            response.status(ResponseEntity.SUCCESS).result(account);
          }
          break;
        case "account/update":
          account = accountDao.update(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS).result(account);
          break;
        case "account/delete":
          accountDao.delete(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS);
          break;
        case "account/deposit":
          account = accountDao.deposit(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS).result(account);
          break;
        case "account/withraw":
          account = accountDao.withraw(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS).result(account);
          break;
        case "account/transfer":
          account = accountDao.withraw(request.getObject(Account.class));
          response.status(ResponseEntity.SUCCESS).result(account);
          break;
          
        default:
          response.status(ResponseEntity.ERROR).result("해당 명령을 지원하지 않습니다.");
      }

      out.writeUTF(response.toJson());
    }

    in.close();
    out.close();
    socket.close();
  }

}
