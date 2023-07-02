package bank_project.myapp.handler;

import bank_project.myapp.vo.Board;

public class BoardList {


  private final static int DEFAULT_SIZE = 3;
  private Board[] boards = new Board[DEFAULT_SIZE];
  private int length = 0;

  public boolean add(Board board) {

    if (this.length == boards.length) {
      increase();
    }
    this.boards[this.length++] = board;
    return true;
  }

  private void increase() {
    if (this.length == boards.length) {
      Board[] arr = new Board[boards.length + (boards.length >> 1)];

      for (int i = 0; i < boards.length; i++) {
        arr[i] = boards[i];
      }
      boards = arr;

    }
  }


  public Board[] list() {
    Board[] array = new Board[this.length];

    for (int i = 0; i < this.length; i++) {
      array[i] = this.boards[i];
    }
    return array;
  }

  public Board get(int no) {
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      if (board.getNo() == no) {
        board.setViewCount(board.getViewCount() + 1);
        return board;
      }
    }
    return null;
  }

  public boolean delete(int no) {
    int deletedIndex = indexOf(no);
    if (deletedIndex == -1) {
      return false;
    }

    for (int i = deletedIndex; i < this.length - 1; i++) {
      this.boards[i] = this.boards[i + 1];
    }

    this.boards[--this.length] = null;
    return true;
  }

  private int indexOf(int boardNo) {
    for (int i = 0; i < this.length; i++) {
      Board board = this.boards[i];
      if (board.getNo() == boardNo) {
        return i;
      }
    }
    return -1;

  }



}
