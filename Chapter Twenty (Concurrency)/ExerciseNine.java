import java.util.concurrent.*;
import static java.lang.Thread.*;

class PrioritiedThreadFactory implements ThreadFactory {
  private int priority = MIN_PRIORITY;

  public void setPriority(int priority) {
    this.priority = priority;
  }
  public int getPriority() {
    return priority;
  }
  public Thread newThread(Runnable r) {
    Thread t = new Thread(r);
    t.setPriority(priority);
    return t;
  }
}


class SimplePriorities implements Runnable {
  private int countDown = 5;
  private volatile double d; // No optimization
  // private int priority;
  // public SimplePriorities(int priority) {
  //   this.priority = priority;
  // }
  public String toString() {
    return Thread.currentThread() + ": " + countDown + " P: " + Thread.currentThread().getPriority();
  }
  public void run() {
    // Thread.currentThread().setPriority(priority);
    while(true) {
      // An expensive, interruptable operation:
      for(int i = 1; i < 100000; i++) {
        d += (Math.PI + Math.E) / (double)i;
        if(i % 1000 == 0)
          Thread.yield();
      }
      System.out.println(this);
      if(--countDown == 0) return;
    }
  }
  public static void main(String[] args) {
    PrioritiedThreadFactory factory = new PrioritiedThreadFactory();
    ExecutorService exec = Executors.newCachedThreadPool(factory);
    for(int i = 0; i < 5; i++)
      exec.execute(new SimplePriorities());
    factory.setPriority(MAX_PRIORITY);
    exec.execute(new SimplePriorities());
    exec.shutdown();
  }
}

public class ExerciseNine {
  public static void main(String[] args){
    SimplePriorities.main(args);
  }  
}