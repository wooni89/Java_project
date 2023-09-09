package bank_project.myapp.controller;

import bank_project.myapp.dao.BoardDao;
import bank_project.myapp.vo.AttachedFile;
import bank_project.myapp.vo.Board;
import bank_project.myapp.vo.Customer;
import org.apache.ibatis.session.SqlSessionFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BoardFileDeleteController implements PageController {

    BoardDao boardDao;
    SqlSessionFactory sqlSessionFactory;

    public BoardFileDeleteController(BoardDao boardDao, SqlSessionFactory sqlSessionFactory) {
        this.boardDao = boardDao;
        this.sqlSessionFactory = sqlSessionFactory;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

        Customer loginUser = (Customer) request.getSession().getAttribute("loginUser");
        if (loginUser == null) {
            return "redirect:../auth/login";
        }

        Board board = null;
        try {
            int category = Integer.parseInt(request.getParameter("category"));
            int fileNo = Integer.parseInt(request.getParameter("no"));

            AttachedFile attachedFile = boardDao.findFileBy(fileNo);
            board = boardDao.findBy(category, attachedFile.getBoardNo());
            if (board.getWriter().getNo() != loginUser.getNo()) {
                throw new ServletException("게시글 변경 권한이 없습니다!");
            }

            if (boardDao.deleteFile(fileNo) == 0) {
                throw new Exception("해당 번호의 첨부파일이 없거나 삭제 권한이 없습니다.");
            } else {
            sqlSessionFactory.openSession(false).commit();
                return "redirect:detail?category=" + category + "&no=" + board.getNo();
            }
        } catch (Exception e) {
            sqlSessionFactory.openSession(false).rollback();
            request.setAttribute("refresh", "2;url=/board/detail?category=" + request.getParameter("category") +
                    "&no=" + board.getNo());
            throw e;
        }
    }
}