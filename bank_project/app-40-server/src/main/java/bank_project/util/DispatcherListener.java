package bank_project.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
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

public class DispatcherListener implements ActionListener {
  // 객체 보관소
  Map<String, Object> beanContainer = new HashMap<>();

  public DispatcherListener() throws Exception {
    SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryProxy(new SqlSessionFactoryBuilder()
        .build(Resources.getResourceAsStream("bank_project/myapp/config/mybatis-config.xml")));
    beanContainer.put("sqlSessionFactory", sqlSessionFactory);

    // DAO 준비
    CustomerDao customerDao = new MySQLCustomerDao(sqlSessionFactory);
    AccountDao accountDao = new MySQLAccountDao(sqlSessionFactory);
    BoardDao boardDao = new MySQLBoardDao(sqlSessionFactory);
    beanContainer.put("customerDao", customerDao);
    beanContainer.put("accountDao", accountDao);
    beanContainer.put("boardDao", boardDao);

    // Listener 준비
    beanContainer.put("login", new LoginListener(customerDao));

    beanContainer.put("customer/add", new CustomerAddListener(customerDao, sqlSessionFactory));
    beanContainer.put("customer/list", new CustomerListListener(customerDao));
    beanContainer.put("customer/detail", new CustomerDetailListener(customerDao));
    beanContainer.put("customer/update", new CustomerUpdateListener(customerDao, sqlSessionFactory));
    beanContainer.put("customer/delete", new CustomerDeleteListener(customerDao, sqlSessionFactory));

    beanContainer.put("account/add", new AccountAddListener(accountDao, sqlSessionFactory));
    beanContainer.put("account/list", new AccountListListener(accountDao));
    beanContainer.put("account/detail", new AccountDetailListener(accountDao));
    beanContainer.put("account/update", new AccountUpdateListener(accountDao, sqlSessionFactory));
    beanContainer.put("account/delete", new AccountDeleteListener(accountDao, sqlSessionFactory));
    
    beanContainer.put("account/deposit", new BankingDepositListener(accountDao, sqlSessionFactory));
    beanContainer.put("account/withraw", new BankingWithrawListener(accountDao, sqlSessionFactory));
    beanContainer.put("account/transfer", new BankingTransferListener(accountDao, sqlSessionFactory));
    
    beanContainer.put("board/add", new BoardAddListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/list", new BoardListListener(boardDao));
    beanContainer.put("board/detail", new BoardDetailListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/update", new BoardUpdateListener(boardDao, sqlSessionFactory));
    beanContainer.put("board/delete", new BoardDeleteListener(boardDao, sqlSessionFactory));
  }
  
  @Override
  public void service(BreadcrumbPrompt prompt) throws IOException {
    ActionListener listener = (ActionListener) beanContainer.get(prompt.getAttribute("menuPath"));
    if (listener == null) {
      throw new RuntimeException("해당 요청을 처리할 수 없습니다.");
    }
    listener.service(prompt);
  }

  public Object getBean(String name) {
    return beanContainer.get(name);
  }
}
