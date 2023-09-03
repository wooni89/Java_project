package bank_project.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.util.List;
import bank_project.myapp.ClientApp;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class Daobuilder {

  DataInputStream in;
  DataOutputStream out;
  
  public Daobuilder(DataInputStream in, DataOutputStream out) {
    this.in = in;
    this.out = out;
  }
  
  @SuppressWarnings("unchecked")
  public <T> T build(String dataName, Class<T> type) {
    return (T) Proxy.newProxyInstance(
        ClientApp.class.getClassLoader(),
        new Class[] {type},
        (proxy, method, args) -> {
          RequestEntity requestEntity = new RequestEntity();
          requestEntity.command(dataName + "/" + method.getName());
          if (args != null) {
            requestEntity.data(args[0]);
          }
          
          out.writeUTF(requestEntity.toJson());
          
          ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
          if (response.getStatus().equals(ResponseEntity.ERROR)) {
            throw new RuntimeException(response.getResult());
          }
          
          Class<?> returnType = method.getReturnType();
          
          if (returnType == int.class) {
            return response.getObject(int.class);
            
          } else if (returnType == void.class) {
            return null;
            
          } else if (returnType == List.class) {
            ParameterizedType paramType = (ParameterizedType)method.getGenericReturnType();
            Class<?> itemType = (Class<?>) paramType.getActualTypeArguments()[0];
            return response.getList(itemType);
            
          } else {
            return response.getObject(returnType);
          }
        });
  }
  
}
