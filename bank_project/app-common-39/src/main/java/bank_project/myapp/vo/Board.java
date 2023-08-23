package bank_project.myapp.vo;

import java.io.Serializable;
import java.sql.Timestamp;

public class Board implements Serializable {

  private static final long serialVersionUID = 1L;

  public static int boardNo = 1;
  private int no;
  private String title;
  private String content;
  private Customer writer;
  private String password;
  private int viewCount = 0;
  private Timestamp createdDate;

  public Board() {
    this.no = boardNo++;
  }

  public Board(int no) {
    this.no = no;
  }

  public boolean equals(Object obj) {

    if (obj == null) {
      return false;
    }

    if (this.getClass() != this.getClass()) {
      return false;
    }

    Board b = (Board) obj;

    if (this.getNo() != b.getNo()) {
      return false;
    }

    return true;
  }

  public int getNo() {
    return no;
  }

  public void setNo(int no) {
    this.no = no;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public Customer getWriter() {
    return writer;
  }
  
  public void setWriter(Customer writer) {
    this.writer = writer;
  }



  public int getViewCount() {
    return viewCount;
  }

  public void setViewCount(int viewCount) {
    this.viewCount = viewCount;
  }

  public Timestamp getCreatedDate() {
    return createdDate;
  }

  public void setCreatedDate(Timestamp createdDate) {
    this.createdDate = createdDate;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

}
