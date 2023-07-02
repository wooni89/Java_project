package bank_project.util;

import java.lang.reflect.Array;

public class ArrayList<E> extends AbstractList<E> {

  private final static int DEFAULT_SIZE = 3;

  private Object[] list = new Object[DEFAULT_SIZE];
  private int length = 0;

  @Override
  public boolean add(E obj) {

    if (this.length == list.length) {
      increase();
    }
    this.list[this.length++] = obj;
    return true;
  }

  public void increase() {
    Object[] arr = new Object[list.length + (list.length >> 1)];
    for (int i = 0; i < list.length; i++) {
      arr[i] = list[i];
    }
    list = arr;
  }

  @Override
  public Object[] toArray() {
    Object[] arr = new Object[this.length];
    for (int i = 0; i < this.length; i++) {
      arr[i] = this.list[i];
    }
    return arr;
  }

  @SuppressWarnings("unchecked")
  @Override
  public <T> T[] toArray(T[] arr) {
    T[] values = null;

    if (arr.length < this.length) {
      values = (T[]) Array.newInstance(arr.getClass().getComponentType(), this.length);
    } else {
      values = arr;
    }

    for (int i = 0; i < this.length; i++) {
      values[i] = (T) list[i];
    }
    return values;
  }

  @SuppressWarnings("unchecked")
  @Override
  public E get(int index) {

    if (!isValid(index)) {
      return null;
    }
    return (E) this.list[index];
  }



  @Override
  public boolean remove(E obj) {
    int deleteIndex = indexOf(obj);
    if (deleteIndex == -1) {
      return false;
    }

    for (int i = deleteIndex; i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }
    this.list[this.length - 1] = null;
    this.length--;
    return true;
  }


  @SuppressWarnings("unchecked")
  @Override
  public E remove(int index) {
    if (!isValid(index)) {
      return null;
    }

    Object old = this.list[index];

    for (int i = index; i < this.length - 1; i++) {
      this.list[i] = this.list[i + 1];
    }
    this.list[--this.length] = null;

    return (E) old;
  }


  private int indexOf(Object obj) {
    for (int i = 0; i < this.length; i++) {
      Object item = this.list[i];
      if (item.equals(obj)) {
        return i;
      }
    }
    return -1;
  }


}
