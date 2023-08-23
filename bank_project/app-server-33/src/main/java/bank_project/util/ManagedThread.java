package bank_project.util;

public class ManagedThread extends Thread {
  static int count = 0;
  
  int no;
  ResourcePool<ManagedThread> pool;
  Job job;
  
  public ManagedThread(ResourcePool<ManagedThread> pool) {
    this.pool = pool;
    no = ++count;
  }
  
  public void setJob(Job job) {
    this.job = job;
    synchronized (this) {
      this.notify();
    }
  }
  
  @Override
  synchronized public void run() {
    while (true) {
      try {
        synchronized (this) {
          this.wait();
        }
        System.out.printf("%d번 쓰레드 실행", no);
        this.job.execute();
        pool.returnResource(this);
      
      } catch (Exception e) {
        System.out.println("스레드 실행중 오류 발생");
        e.printStackTrace();
      }
      
    }
  }
}
