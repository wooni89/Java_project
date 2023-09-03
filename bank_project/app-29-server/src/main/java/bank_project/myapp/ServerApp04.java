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

public class ServerApp04 {

  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();
  

  public ServerApp04(int port) throws Exception {
    
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
    
    ServerApp04 app = new ServerApp04(Integer.parseInt(args[0]));
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
      
      Object returnValue = null;
      
      if (params.length > 0) {
        Object arg = request.getObject(params[0].getType());
        returnValue = method.invoke(dao, arg);
      } else {
        returnValue = method.invoke(dao);
      }

      ResponseEntity response = new ResponseEntity();
      response.status(ResponseEntity.SUCCESS);
      response.result(returnValue);
      out.writeUTF(response.toJson());
    }

    in.close();
    out.close();
    socket.close();
  }

}
