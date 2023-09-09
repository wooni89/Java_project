package bank_project.myapp.controller;

import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.vo.AttachedFile;
import bank_project.myapp.vo.Board;
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
import java.util.ArrayList;

@WebServlet("/board/add")
@MultipartConfig(maxFileSize = 1024 * 1024 * 10)
public class BoardAddController extends HttpServlet {

    private static final long serialVersionUID = 1L;


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.getRequestDispatcher("/WEB-INF/jsp/board/form.jsp").include(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");

        if (loginUser == null) {
            response.sendRedirect("/auth/login");
            return;
        }

        BoardDao boardDao = (BoardDao) this.getServletContext().getAttribute("boardDao");
        SqlSessionFactory sqlSessionFactory = (SqlSessionFactory) this.getServletContext().getAttribute("sqlSessionFactory");
        NcpObjectStorageService ncpObjectStorageService = (NcpObjectStorageService) this.getServletContext().getAttribute("ncpObjectStorageService");

        try {
            Board board = new Board();
            board.setWriter(loginUser);
            board.setTitle(request.getParameter("title"));
            board.setContent(request.getParameter("content"));
            board.setCategory(Integer.parseInt(request.getParameter("category")));

            ArrayList<AttachedFile> attachedFiles = new ArrayList<>();

            for (Part part : request.getParts()) {
                if (part.getName().equals("files") && part.getSize() > 0) {
                    String uploadFileUrl =
                            ncpObjectStorageService.uploadFile("bank-bukit-1", "board/", part);
                    AttachedFile attachedFile = new AttachedFile();
                    attachedFile.setFilePath(uploadFileUrl);
                    attachedFiles.add(attachedFile);
                }
            }
            board.setAttachedFiles(attachedFiles);

            boardDao.insert(board);
            if (attachedFiles.size() > 0) {
                boardDao.insertFiles(board);
            }
            sqlSessionFactory.openSession(false).commit();
            response.sendRedirect("list?category=" + request.getParameter("category"));

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("message", "게시글 등록 오류!");
            request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
            throw new ServletException(e);
        }
    }
}
