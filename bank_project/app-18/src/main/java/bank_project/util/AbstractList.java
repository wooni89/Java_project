package bank_project.util;

public abstract class AbstractList<E> implements List<E> {

  protected int size;

  @Override
  public int size() {
    return this.size;
  }

  protected boolean isValid(int index) {
    return index >= 0 && index < this.size;
  }

  @Override
  public Iterator<E> iterator() {

    return new Iterator<E>() {
      int cursor;

      @Override
      public boolean hasNext() {
        return cursor < AbstractList.this.size();
      }

      @Override
      public E next() {
        return AbstractList.this.get(cursor++);
      }
    };
  }

}
