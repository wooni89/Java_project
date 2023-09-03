package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import bank_project.myapp.dao.AccountListDao;
import bank_project.myapp.dao.BoardListDao;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class ServerApp03 {

  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();
  

  public ServerApp03(int port) throws Exception {
    
    this.port = port;
    
    daoMap.put("account", new AccountListDao("member.json"));
    daoMap.put("board", new BoardListDao("board.json"));
  }

  public void close() throws Exception {

    serverSocket.close();
  }

  public static void main(String[] args) throws Exception {

    if (args.length < 1) {
      System.out.println(" 실행 예) java ... bitcamp.myapp.ClientApp 포트번호");
      return;
    }
    
    ServerApp03 app = new ServerApp03(Integer.parseInt(args[0]));
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
      
      String[] values = command.split("/");
      String dataName = values[0];
      String metodName = values[1];
      System.out.printf("%s.%s\n",dataName, metodName);
      
      Object dao = daoMap.get(dataName);
      if (dao == null) {
        out.writeUTF(new ResponseEntity()
            .status(ResponseEntity.ERROR)
            .result("데이터를 찾을 수 없습니다.")
            .toJson());
        continue;
      }
      
      Method[] methods = dao.getClass().getDeclaredMethods();
      Method method = null;
      for (int i = 0; i < methods.length; i++) {
        if (methods[i].getName().equals(metodName)) {
          method = methods[i];
          break;
        }
      }
      
      if (method == null) {
        out.writeUTF(new ResponseEntity()
            .status(ResponseEntity.ERROR)
            .result("메서드를 찾을 수 없습니다.")
            .toJson());
        continue;
      }
      
      Parameter[] params = method.getParameters();
      Class<?> returnType = method.getReturnType();
      
      if (params.length > 0) {
        System.out.println(params[0].getType().getSimpleName());
      }
      System.out.println(returnType.getSimpleName());

      ResponseEntity response = new ResponseEntity();

      switch (command) {
//        case "board/list":
//          response.status(ResponseEntity.SUCCESS).result(boardDao.list());
//          break;
//        case "board/insert":
//          boardDao.insert(request.getObject(Board.class));
//          response.status(ResponseEntity.SUCCESS);
//          break;
//        case "board/findBy":
//          Board board = boardDao.findBy(request.getObject(Integer.class));
//          if (board == null) {
//            response.status(ResponseEntity.FAILURE).result("해당번호의 게시글이 없습니다.");
//          } else {
//            response.status(ResponseEntity.SUCCESS).result(board);
//          }
//          break;
//        case "board/update":
//          int value = boardDao.update(request.getObject(Board.class));
//          response.status(ResponseEntity.SUCCESS).result(value);
//          break;
//        case "board/delete":
//          value = boardDao.delete(request.getObject(Integer.class));
//          response.status(ResponseEntity.SUCCESS).result(value);
//          break;
//
//        case "account/insert":
//          accountDao.insert(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS);
//          break;
//        case "account/list":
//          response.status(ResponseEntity.SUCCESS).result(accountDao.list());
//          break;
//          
//        case "account/findAccount":
//          Account withrawAccount = request.getObject(Account.class);
//          Account findAccount = accountDao.findAccount(withrawAccount);
//          if (findAccount == null) {
//            response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다.");
//          } else {
//            response.status(ResponseEntity.SUCCESS).result(findAccount);
//          }
//          break;
//
//        case "account/findAccountPassword":
//          Account inputAccount = request.getObject(Account.class);
//          Account account = accountDao.findAccount(inputAccount);
//
//          if (account == null) {
//              response.status(ResponseEntity.FAILURE).result("해당 번호의 회원이 없습니다.");
//          } else {
//            account = accountDao.findAccountPassword(inputAccount);
//              if (account == null) {
//                  response.status(ResponseEntity.FAILURE).result("비밀번호가 일치하지 않습니다.");
//              } else {
//                  response.status(ResponseEntity.SUCCESS).result(account);
//              }
//          }
//          break;
//        case "account/update":
//          account = accountDao.update(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS).result(account);
//          break;
//        case "account/delete":
//          accountDao.delete(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS);
//          break;
//        case "account/deposit":
//          account = accountDao.deposit(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS).result(account);
//          break;
//        case "account/withraw":
//          account = accountDao.withraw(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS).result(account);
//          break;
//        case "account/transfer":
//          account = accountDao.withraw(request.getObject(Account.class));
//          response.status(ResponseEntity.SUCCESS).result(account);
//          break;
          
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
