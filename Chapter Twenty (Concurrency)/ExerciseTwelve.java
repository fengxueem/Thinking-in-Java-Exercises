import java.util.concurrent.*;

class AtomicityTest implements Runnable {
  private volatile int i = 0;
  // public int getValue() { return i; }
  // filed 'i' is accessed by more than one threads
  // then all the writes to and reads from i should be synchronized 
  public synchronized int getValue() { return i; }
  private synchronized void evenIncrement() { i++; i++; }
  public void run() {
    while(true)
      evenIncrement();
  }
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    AtomicityTest at = new AtomicityTest();
    exec.execute(at);
    while(true) {
      int val = at.getValue();
      if(val % 2 != 0) {
        System.out.println(val);
        System.exit(0);
      }
    }
  }
}

public class ExerciseTwelve {
  public static void main(String[] args) {
    AtomicityTest.main(args);    
  }  
}