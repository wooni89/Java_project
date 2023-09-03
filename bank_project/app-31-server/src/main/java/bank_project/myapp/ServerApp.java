package bank_project.myapp;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import bank_project.myapp.dao.AccountListDao;
import bank_project.myapp.dao.BoardListDao;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class ServerApp {

  int port;
  ServerSocket serverSocket;

  HashMap<String, Object> daoMap = new HashMap<>();


  public ServerApp(int port) throws Exception {

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

    ServerApp app = new ServerApp(Integer.parseInt(args[0]));
    app.execute();
    app.close();
  }

  public static Method findMethod(Object obj, String methodName) {

    Method[] methods = obj.getClass().getDeclaredMethods();
    for (int i = 0; i < methods.length; i++) {
      if (methods[i].getName().equals(methodName)) {
        return methods[i];
      }
    }
    return null;
  }

  public static Object call(Object obj, Method method, RequestEntity request) throws Exception {

    Parameter[] params = method.getParameters();
    if (params.length > 0) {
      return method.invoke(obj, (Object) request.getObject(params[0].getType()));
    }
    return method.invoke(obj);
  }


  public void execute() throws Exception {

    System.out.println("[MyList 서버 Application]");
    this.serverSocket = new ServerSocket(port);
    System.out.println("Server Runing");

    while (true) {
     processRequest(serverSocket.accept());
      
    }

  }

  public void processRequest(Socket socket) {

    try (Socket s = socket;
        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream())) {

      InetSocketAddress socketAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
      System.out.printf("%s:%s 클라이언트가 접속했음\n", socketAddress.getHostString(),
          socketAddress.getPort());
      
     
      // 클라이언트 요청을 반복해서 처리하지 않는다.
      // => 접속 -> 요청 -> 실행 -> 응답 -> 연결 끊기
        RequestEntity request = RequestEntity.fromJson(in.readUTF());

        String command = request.getCommand();
        System.out.println(command);

        if (command.equals("quit")) {
            return;
        }

        String[] values = command.split("/");
        String dataName = values[0];
        String metodName = values[1];

        Object dao = daoMap.get(dataName);
        if (dao == null) {
          out.writeUTF(
              new ResponseEntity().status(ResponseEntity.ERROR)
              .result("데이터를 찾을 수 없습니다.")
              .toJson());
          return;
        }


        Method method = findMethod(dao, metodName);
        if (method == null) {
          out.writeUTF(
              new ResponseEntity().status(ResponseEntity.ERROR)
              .result("메서드를 찾을 수 없습니다.")
              .toJson());
          return;
        }

        try {
        Object result = call(dao, method, request);

        ResponseEntity response = new ResponseEntity();
        response.status(ResponseEntity.SUCCESS);
        response.result(result);
        out.writeUTF(response.toJson());
        
        } catch (Exception e) {
          ResponseEntity response = new ResponseEntity();
          response.status(ResponseEntity.ERROR);
          response.result(e.getMessage());
          out.writeUTF(response.toJson());
        }
      
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }


}
