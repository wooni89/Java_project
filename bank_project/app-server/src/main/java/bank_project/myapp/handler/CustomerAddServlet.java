package bank_project.myapp.handler;

import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/customer/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class CustomerAddServlet extends HttpServlet {
  
  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {
    
    CustomerDao customerDao = (CustomerDao) this.getServletContext().getAttribute("customerDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");


    Customer customer = new Customer();
    customer.setName(request.getParameter("name"));
    customer.setEmail(request.getParameter("email"));
    customer.setPassword(request.getParameter("password"));
    customer.setGender(request.getParameter("gender").charAt(0));
    customer.setAddress(request.getParameter("address"));
    customer.setCreditRating(Integer.parseInt(request.getParameter("creditRating")));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bank-bukit-1", "customer/", photoPart);
      customer.setPhoto(uploadFileUrl);
    }

    response.setContentType("text/html;charset=UTF-8");
    PrintWriter out = response.getWriter();
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<meta charset='UTF-8'>");
    out.println("<meta http-equiv='refresh' content='1;url=/customer/list'>");
    out.println("<title>회원</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>회원 등록</h1>");
    
    try {
      customerDao.insert(customer);
      sqlSessionFactory.openSession(false).commit();
      response.sendRedirect("list");

    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "1;url=list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
    
    out.println("</body>");
    out.println("</html>");
    }
}
