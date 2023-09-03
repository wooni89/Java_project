package bank_project.myapp.dao;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;
import bank_project.myapp.vo.Account;
import bank_project.net.RequestEntity;
import bank_project.net.ResponseEntity;

public class AccountNetworkDao implements AccountDao {

  String dataName;
  DataInputStream in;
  DataOutputStream out;

  public AccountNetworkDao(String dataName, DataInputStream in, DataOutputStream out) {
    this.dataName = dataName;
    this.in = in;
    this.out = out;

  }

  @Override
  public void insert(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/insert").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

  }

  @Override
  public List<Account> list() {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/list").toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getList(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account findAccountPassword(Account account) {
    try {
      out.writeUTF(
          new RequestEntity().command(dataName + "/findAccountPassword").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      System.out.println(response.getResult());

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account findAccount(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/findAccount").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account update(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/update").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Account delete(Account account) {
    try {

      out.writeUTF(new RequestEntity().command(dataName + "/delete").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      response.status(ResponseEntity.SUCCESS);
      boolean deleteResult = false;
      if (deleteResult) {
        response.result(true);
      } else {
        response.result(false);

      }
      return response.getObject(Account.class);


    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Account deposit(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/deposit").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Account withraw(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/withraw").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public Account transfer(Account account) {
    try {
      out.writeUTF(new RequestEntity().command(dataName + "/transfer").data(account).toJson());

      ResponseEntity response = ResponseEntity.fromJson(in.readUTF());
      if (response.getStatus().equals(ResponseEntity.ERROR)) {
        throw new RuntimeException(response.getResult());
      }

      return response.getObject(Account.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}

