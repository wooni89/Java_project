package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import com.google.gson.reflect.TypeToken;
import bank_project.myapp.dao.AccountListDao;
import bank_project.myapp.dao.BoardListDao;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class ServerApp2 {

  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();

  public ServerApp2(int port) throws Exception {
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

    ServerApp2 app = new ServerApp2(Integer.parseInt(args[0]));
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

      CommandHandler commandHandler = getCommandHandler(command);

      ResponseEntity response = new ResponseEntity();

      if (commandHandler != null) {
        Parameter[] params = commandHandler.method.getParameters();
        Class<?> returnType = commandHandler.method.getReturnType();

        Object[] args = null;
        if (params.length == 0) {
          args = new Object[0];
        } else if (params.length == 1) {
          args = new Object[] {request.getObject(params[0].getType())};
        } else if (params.length == 2) {
          Type hashMapType = new TypeToken<HashMap<String, Object>>() {}.getType();
          HashMap<String, Object> dataMap = request.getObject(hashMapType);

          // 계좌 번호와 패스워드
          String accountNumber = (String) dataMap.get("accNum");
          String password = (String) dataMap.get("pwd");

          args = new Object[] {
              accountNumber,
              password
          };
        }

        if (returnType == void.class) {
          commandHandler.method.invoke(commandHandler.handler, args);
          response.status(ResponseEntity.SUCCESS);
        } else {
          Object result = commandHandler.method.invoke(commandHandler.handler, args);
          response.status(ResponseEntity.SUCCESS).result(result);
        }

      } else {
        response.status(ResponseEntity.ERROR).result("해당 명령을 지원하지 않습니다.");
      }

      out.writeUTF(response.toJson());
    }

    in.close();
    out.close();
    socket.close();
  }

  private CommandHandler getCommandHandler(String command) {
    String[] values = command.split("/");
    Object dao = daoMap.get(values[0]);
    if (dao == null) {
      return null;
    }

    Method[] methods = dao.getClass().getDeclaredMethods();
    for (Method method : methods) {
      if (method.getName().equals(values[1])) {
        return new CommandHandler(dao, method);
      }
    }
    return null;
  }

  static class CommandHandler {
    Object handler;
    Method method;

    public CommandHandler(Object handler, Method method) {
      this.handler = handler;
      this.method = method;
    }
  }

}
