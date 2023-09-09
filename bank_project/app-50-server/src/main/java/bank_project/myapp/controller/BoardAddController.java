package bank_project.myapp.controller;

import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.vo.AttachedFile;
import bank_project.myapp.vo.Board;
import bank_project.myapp.vo.Customer;
import bank_project.util.NcpObjectStorageService;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.util.ArrayList;

public class BoardAddController implements PageController {

    BoardDao boardDao;
    SqlSessionFactory sqlSessionFactory;
    NcpObjectStorageService ncpObjectStorageService;

    public BoardAddController(
            BoardDao boardDao,
            SqlSessionFactory sqlSessionFactory,
            NcpObjectStorageService ncpObjectStorageService) {
        this.boardDao = boardDao;
        this.sqlSessionFactory = sqlSessionFactory;
        this.ncpObjectStorageService = ncpObjectStorageService;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getMethod().equals("GET")) {
            return "/WEB-INF/jsp/board/form.jsp";
        }

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            request.getParts();

            return "redirect:../auth/login";
        }

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
            return  "redirect:list?category=" + request.getParameter("category");

        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("message", "게시글 등록 오류!");
            request.setAttribute("refresh", "2;url=list?category=" + request.getParameter("category"));
            throw e;
        }
    }
}
