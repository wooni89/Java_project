package bank_project.util;

public class LinkedList {

  static class Node {
    Object value;
    Node next;
  }

  Node head;
  Node tail;
  int size;

  public void add(Object value) {
    Node node = new Node();
    node.value = value;

    if (head == null) {
      head = node;
    } else if (this.tail != null) {
      this.tail.next = node;
    }

    this.tail = node;
    this.size++;
  }

  public Object[] getList() {
    Object[] arr = new Object[this.size];

    Node cursor = this.head;
    for (int i = 0; i < this.size; i++) {
      arr[i] = cursor.value;
      cursor = cursor.next;
    }

    return arr;
  }

  public Object retrieve(Object value) {
    Node cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        return cursor.value;
      }

      cursor = cursor.next;
    }

    return null;
  }

  public boolean remove(Object value) {
    Node prev = null;
    Node cursor = this.head;

    while (cursor != null) {
      if (cursor.value.equals(value)) {
        if (prev == null) {
          head = cursor.next;

          if (head == null) {
            tail = null;
          }

        } else if (cursor.next == null) {
          tail = prev;
          tail.next = null;
        } else {
          prev.next = cursor.next;
        }
        size--;

        cursor.next = null;
        cursor.value = null;

        return true;
      }

      prev = cursor;

      cursor = cursor.next;
    }
    return false;
  }

}
