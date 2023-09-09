package bank_project.myapp.servlet;

import bank_project.myapp.controller.*;
import bank_project.myapp.dao.AccountDao;
import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.dao.TransactionDao;
import bank_project.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/app/*")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class DispatcherServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    Map<String, PageController> controllerMap = new HashMap<>();

    @Override
    public void init() throws ServletException {
        CustomerDao customerDao = (CustomerDao) this.getServletContext().getAttribute("customerDao");
        BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
        AccountDao accountDao = (AccountDao) this.getServletContext().getAttribute("accountDao");
        TransactionDao transactionDao = (TransactionDao) this.getServletContext().getAttribute("transactionDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
        NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

        controllerMap.put("/", new HomeController());
        controllerMap.put("/auth/login", new LoginController(customerDao));
        controllerMap.put("/auth/logout", new LogoutController());
        controllerMap.put("/customer/list", new CustomerListController(customerDao));
        controllerMap.put("/customer/add", new CustomerAddController(customerDao, sqlSessionFactory, ncpObjectStorageService));
        controllerMap.put("/customer/detail", new CustomerDetailController(customerDao));
        controllerMap.put("/customer/update", new CustomerUpdateController(customerDao, sqlSessionFactory, ncpObjectStorageService));
        controllerMap.put("/customer/delete", new CustomerDeleteController(customerDao, sqlSessionFactory));
        controllerMap.put("/board/list", new BoardListController(boardDao));
        controllerMap.put("/board/add", new BoardAddController(boardDao, sqlSessionFactory, ncpObjectStorageService));
        controllerMap.put("/board/detail", new BoardDetailController(boardDao, sqlSessionFactory));
        controllerMap.put("/board/update", new BoardUpdateController(boardDao, sqlSessionFactory, ncpObjectStorageService));
        controllerMap.put("/board/delete", new BoardDeleteController(boardDao, sqlSessionFactory));
        controllerMap.put("/board/fileDelete", new BoardFileDeleteController(boardDao, sqlSessionFactory));
        controllerMap.put("/account/list", new AccountListController(accountDao));
        controllerMap.put("/account/add", new AccountAddController(accountDao, sqlSessionFactory));
        controllerMap.put("/account/detail", new AccountDetailController(accountDao));
        controllerMap.put("/account/update", new AccountUpdateController(accountDao, sqlSessionFactory));
        controllerMap.put("/account/delete", new AccountDeleteController(accountDao, sqlSessionFactory));
        controllerMap.put("/transaction/deposit", new TransactionDepositController(accountDao, transactionDao, sqlSessionFactory));
        controllerMap.put("/transaction/withdraw", new TransactionWithdrawController(accountDao, transactionDao ,sqlSessionFactory));
        controllerMap.put("/transaction/transfer", new TransactionTransferController(accountDao, transactionDao, sqlSessionFactory));
        controllerMap.put("/transaction/form", new TransactionFormController(accountDao));
        controllerMap.put("/transaction/passbook", new TransactionPassbookController(accountDao, transactionDao));

    }



    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pageControllerPath = request.getPathInfo();
        response.setContentType("text/html;charset=UTF-8");

        PageController pageController = controllerMap.get(pageControllerPath);
        if (pageController == null) {
            throw new ServletException("해당 요청을 처리할 수 없습니다!");
        }

        try {
            String viewUrl = pageController.execute(request, response);
            if (viewUrl.startsWith("redirect:")) {
                response.sendRedirect(viewUrl.substring(9)); // 예) redirect:/app/board/list
            } else {
                request.getRequestDispatcher(viewUrl).include(request, response);
            }

        } catch (Exception e) {
            // 페이지 컨트롤러 실행 중 오류가 발생했다면, 예외를 던진다.
            throw new ServletException("요청 처리 중 오류 발생!", e);
        }
    }
}
