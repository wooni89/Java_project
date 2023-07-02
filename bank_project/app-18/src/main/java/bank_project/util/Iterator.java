package bank_project.util;

public interface Iterator<E> {

  boolean hasNext(); // 값을 꺼내기전에 꺼낼 값이 있는지 확인할때

  E next(); // 목록에서 값이 있을때 꺼낸다.
}
