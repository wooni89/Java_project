package bank_project.util;

public class Stack extends LinkedList {

  public void push(Object value) {
    this.add(value);
  }

  public Object pop() {
    if (this.empty()) {
      return null;
    }
    return this.remove(this.size() - 1);
  }

  public Object peek() {
    if (this.empty()) {
      return null;
    }

    return this.get(this.size() - 1);
  }

  public boolean empty() {
    return this.size() == 0;
  }
}
