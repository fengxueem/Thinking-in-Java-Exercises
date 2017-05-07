import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Count {
  private int count = 0;
  private Random rand = new Random(47);
  // Remove the synchronized keyword to see counting fail:
  public synchronized int increment() {
    int temp = count;
    if(rand.nextBoolean()) // Yield half the time
      Thread.yield();
    return (count = ++temp);
  }
  public synchronized int value() { return count; }
}

class Entrance implements Runnable {
  // private static Count count = new Count();
  private Count count;
  private CountDownLatch countDownLatch;
  private static List<Entrance> entrances =
    new ArrayList<Entrance>();
  private int number = 0;
  // Doesn't need synchronization to read:
  private final int id;
  private static volatile boolean canceled = false;
  private static Thread adderThread = null;
  // Atomic operation on a volatile field:
  public static void cancel() { canceled = true; }
  public Entrance(int id, Count count, CountDownLatch countDownLatch) {
    this.id = id;
    this.count = count;
    this.countDownLatch = countDownLatch;
    // Keep this task in a list. Also prevents
    // garbage collection of dead tasks:
    entrances.add(this);
    if (adderThread == null) {
      adderThread = new Thread(new EntranceAdder());
      adderThread.start();
    }
  }
  public void run() {
    while(!canceled) {
      synchronized(this) {
        ++number;
      }
      print(this + " Total: " + count.increment());
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch(InterruptedException e) {
        print("sleep interrupted");
      }
    }
    print("Stopping " + this);
    countDownLatch.countDown();
  }
  public synchronized int getValue() { return number; }
  public String toString() {
    return "Entrance " + id + ": " + getValue();
  }
  class EntranceAdder implements Runnable {
    public void run() {
      try {
        countDownLatch.await();
        print("Total: " + getTotalCount());
        print("Sum of Entrances: " + sumEntrances());
      } catch(InterruptedException er) {
        print(this + " interrupted");
      }
    }
    public int getTotalCount() {
      return count.value();
    }
    public int sumEntrances() {
      int sum = 0;
      for(Entrance entrance : entrances)
        sum += entrance.getValue();
      return sum;
    }
  }
}

class OrnamentalGarden {
  private static final int entranceNumber = 5;
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    Count count = new Count();
    CountDownLatch countDownLatch = new CountDownLatch(entranceNumber);
    for(int i = 0; i < entranceNumber; i++)
      exec.execute(new Entrance(i, count, countDownLatch));
    // Run for a while, then stop and collect the data:
    TimeUnit.SECONDS.sleep(3);
    Entrance.cancel();
    exec.shutdown();
    if(!exec.awaitTermination(250, TimeUnit.MILLISECONDS))
      print("Some tasks were not terminated!");
  }
}

public class ExerciseThirtyTwo {
  public static void main(String[] args) throws Exception {
    OrnamentalGarden.main(args);    
  }  
}