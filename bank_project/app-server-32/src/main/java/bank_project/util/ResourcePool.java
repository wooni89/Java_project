package bank_project.util;

public interface ResourcePool<T> {
  T getResource();
  void returnResource(T resource);
}
