package bank_project.myapp.handler;

import bank_project.myapp.dao.TransactionDao;
import bank_project.myapp.vo.Account;
import bank_project.myapp.vo.Transaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/transaction/history")
public class TransactionHistoryServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    // Assuming you have a TransactionDao instance here.
    private TransactionDao transactionDao;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");

        Account account = new Account();
        account.setAccNum(request.getParameter("accNum"));


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset='UTF-8'>");
        out.println("<title>거래 내역</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h1> 거래 내역</h1>");

        out.print(
            "<table border=\"1\">"
                + "<thead>"
                + "<tr>"
                + "<th>번호</th>"
                + "<th>계좌 번호</th>"
                + "<th>거래 날짜</th>"
                + "<th>거래 유형</th>"
                + "<th>금액</th>"
                + "<th>메모</td>"
                + "</tr></thead><tbody>");

        List<Transaction> transactions = InitServlet.transactionDao.findByAccountNumber(account);
        for (Transaction transaction : transactions) {
            out.printf(
                "<tr>"
                    +"<td>%d</td>" // no
                    +"<td>%d</td>" // 거래일자
                    +"<td>%s</td>" // acc_num
                    +"<td>%s</td>" // 거래유형
                    +"<td>%d</td></tr>\n",// 거래금액
                    transaction.getNo(),
                    dateFormatter.format(transaction.getCreatedDate()),
                    transaction.getAcc_num().getAccNum(),
                    transaction.getTradeType(),
                    transaction.getAmount()
            );
        }

// End of table
        out.print("</tbody></table>");

        out.print("</body></html>");


    }
}