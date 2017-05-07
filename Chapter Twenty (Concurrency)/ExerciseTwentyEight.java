import java.util.concurrent.*;
import java.io.*;
import static net.mindview.util.Print.*;

class LiftOff implements Runnable {
  protected int countDown = 10; // Default
  private static int taskCount = 0;
  private final int id = taskCount++;
  public LiftOff() {}
  public LiftOff(int countDown) {
    this.countDown = countDown;
  }
  public String status() {
    return "#" + id + "(" +
      (countDown > 0 ? countDown : "Liftoff!") + "), ";
  }
  public void run() {
    while(countDown-- > 0) {
      System.out.print(status());
      Thread.yield();
    }
  }
}

class LiftOffRunner implements Runnable {
  private BlockingQueue<LiftOff> rockets;
  public LiftOffRunner(BlockingQueue<LiftOff> queue) {
    rockets = queue;
  }
  public void add(LiftOff lo) {
    try {
      rockets.put(lo);
    } catch(InterruptedException e) {
      print("Interrupted during put()");
    }
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        LiftOff rocket = rockets.take();
        rocket.run(); // Use this thread
      }
    } catch(InterruptedException e) {
      print("Waking from take()");
    }
    print("Exiting LiftOffRunner");
  }
}

class LiftOffAdder implements Runnable {
  private BlockingQueue<LiftOff> rockets;
  public LiftOffAdder(BlockingQueue<LiftOff> queue) {
    rockets = queue;
  }
  public void run() {
    try {
      rockets.put(new LiftOff(5));
      rockets.put(new LiftOff(5));
      rockets.put(new LiftOff(5));
      rockets.put(new LiftOff(5));
      rockets.put(new LiftOff(5));
    } catch(InterruptedException e) {
      print("Exiting LiftOffAdder");
    }
  }
}

class TestBlockingQueues {
  static void getkey() {
    try {
      // Compensate for Windows/Linux difference in the
      // length of the result produced by the Enter key:
      new BufferedReader(
        new InputStreamReader(System.in)).readLine();
    } catch(java.io.IOException e) {
      throw new RuntimeException(e);
    }
  }
  static void getkey(String message) {
    print(message);
    getkey();
  }
  static void
  test(String msg, BlockingQueue<LiftOff> queue) {
    print(msg);
    // LiftOffRunner runner = new LiftOffRunner(queue);
    // Thread t = new Thread(runner);
    // t.start();
    // for(int i = 0; i < 5; i++)
    //   runner.add(new LiftOff(5));
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new LiftOffAdder(queue));
    exec.execute(new LiftOffRunner(queue));
    getkey("Press 'Enter' (" + msg + ")");
    // t.interrupt();
    exec.shutdownNow();
    print("Finished " + msg + " test");
  }
  public static void main(String[] args) {
    test("LinkedBlockingQueue", // Unlimited size
      new LinkedBlockingQueue<LiftOff>());
    test("ArrayBlockingQueue", // Fixed size
      new ArrayBlockingQueue<LiftOff>(1));
    test("SynchronousQueue", // Size of 1
      new SynchronousQueue<LiftOff>());
  }
}

public class ExerciseTwentyEight {
  public static void main(String[] args){
    TestBlockingQueues.main(args);
  }  
}