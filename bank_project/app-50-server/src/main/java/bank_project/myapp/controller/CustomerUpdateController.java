package bank_project.myapp.controller;

import bank_project.myapp.dao.CustomerDao;
import bank_project.myapp.vo.Customer;
import bank_project.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

public class CustomerUpdateController implements PageController {

    CustomerDao customerDao;
    SqlSessionFactory sqlSessionFactory;
    NcpObjectStorageService ncpObjectStorageService;

    public CustomerUpdateController(CustomerDao customerDao, SqlSessionFactory sqlSessionFactory, NcpObjectStorageService ncpObjectStorageService) {
        this.customerDao = customerDao;
        this.sqlSessionFactory = sqlSessionFactory;
        this.ncpObjectStorageService = ncpObjectStorageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        try {
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

            if (customerDao.update(customer) == 0) {
                throw new Exception("회원이 없습니다.");
            } else {
                sqlSessionFactory.openSession(false).commit();
                return "redirect:list";
            }
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=list");
            throw e;
        }
    }
}
