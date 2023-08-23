package bank_project.myapp.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import bank_project.myapp.vo.Board;

public class MySQLBoardDao implements BoardDao {
  
  Connection con;
  
  public MySQLBoardDao (Connection con) {
    this.con = con;
  }

  @Override
  public void insert(Board board) {
    try (Statement stmt = con.createStatement()) {
      
      stmt.executeUpdate(String.format(
          "insert into myapp_board(title, content, writer, password) values('%s','%s','%s','%s')",
          board.getTitle(),
          board.getContent(),
          board.getWriter(),
          board.getPassword()));
      
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    
  }

  @Override
  public List<Board> list() {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery("select board_no, title, writer, view_count, created_date"
            + " from myapp_board"
            + " order by board_no desc")) {
      
      List<Board> list = new ArrayList<>();
      
      while (rs.next()) {
        Board b = new Board();
        b.setNo(rs.getInt("board_no"));
        b.setTitle(rs.getString("title"));
        b.setWriter(rs.getString("writer"));
        b.setViewCount(rs.getInt("view_count"));
        b.setCreatedDate(rs.getTimestamp("created_date"));
      
        list.add(b);
      }
      
      return list;
   
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public Board findBy(int no) {
    try (Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(
            "select board_no, title, content, writer, view_count, created_date"
                + " from myapp_board"
                + " where board_no=" + no
                + " order by board_no desc")) {

      if (rs.next()) {
        Board b = new Board();
        b.setNo(rs.getInt("board_no"));
        b.setTitle(rs.getString("title"));
        b.setContent(rs.getString("content"));
        b.setWriter(rs.getString("writer"));
        b.setViewCount(rs.getInt("view_count"));
        b.setCreatedDate(rs.getTimestamp("created_date"));

        stmt.executeUpdate("update myapp_board set"
            + " view_count=view_count + 1"
            + " where board_no=" + no);

        return b;
      }

      return null;

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int update(Board board) {
    try (Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(String.format(
          "update myapp_board set"
              + " title='%s',"
              + " content='%s'"
              + " where board_no=%d and password='%s'",
              board.getTitle(),
              board.getContent(),
              board.getNo(),
              board.getPassword()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  public int delete(Board board) {
    try (Statement stmt = con.createStatement()) {

      return stmt.executeUpdate(String.format(
          "delete from myapp_board where board_no=%d and password='%s'",
          board.getNo(),
          board.getPassword()));

    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
