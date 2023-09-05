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

@WebServlet("/customer/update")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class CustomerUpdateServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response)
      throws ServletException, IOException {

    CustomerDao customerDao = (CustomerDao) this.getServletContext().getAttribute("customerDao");
    SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
    NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

    Customer customer = new Customer();
    customer.setNo(Integer.parseInt(request.getParameter("no")));
    customer.setName(request.getParameter("name"));
    customer.setEmail(request.getParameter("email"));
    customer.setGender(request.getParameter("gender").charAt(0));
    customer.setPassword(request.getParameter("password"));
    customer.setAddress(request.getParameter("address"));
    customer.setCreditRating(Integer.parseInt(request.getParameter("creditRating")));

    Part photoPart = request.getPart("photo");
    if (photoPart.getSize() > 0) {
      String uploadFileUrl = ncpObjectStorageService.uploadFile(
              "bank-bukit-1", "customer/", photoPart);
      customer.setPhoto(uploadFileUrl);
    }

    try {
      if (customerDao.update(customer) == 0) {
        throw new Exception("회원이 없습니다.");
      } else {
        sqlSessionFactory.openSession(false).commit();
        response.sendRedirect("list");
      }
    } catch (Exception e) {
      sqlSessionFactory.openSession(false).rollback();
      request.setAttribute("error", e);
      request.setAttribute("message", e.getMessage());
      request.setAttribute("refresh", "1;url=list");
      request.getRequestDispatcher("/error").forward(request, response);
    }
  }
}
