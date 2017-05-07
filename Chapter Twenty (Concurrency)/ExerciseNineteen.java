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
  private static Count count = new Count();
  private static List<Entrance> entrances =
    new ArrayList<Entrance>();
  private int number = 0;
  // Doesn't need synchronization to read:
  private final int id;
  private static volatile boolean canceled = false;
  // Atomic operation on a volatile field:
  public static void cancel() { canceled = true; }
  public Entrance(int id) {
    this.id = id;
    // Keep this task in a list. Also prevents
    // garbage collection of dead tasks:
    entrances.add(this);
  }
  public void run() {
    while(!canceled) {
      // according to testbook page 1188, synchronized lock
      // kind of block is not interruptible. Thus, this task
      // could only be blocked during sleep(), and we need to 
      // put an exit there.
      synchronized(this) {
        ++number;
      }
      print(this + " Total: " + count.increment());
      try {
        TimeUnit.MILLISECONDS.sleep(100);
      } catch(InterruptedException e) {
        print("sleep interrupted");
        break; // this is the exit.
      }
    }
    // while(!canceled) {
    //   synchronized(this) {
    //     ++number;
    //   }
    //   print(this + " Total: " + count.increment());
    //   try {
    //     TimeUnit.MILLISECONDS.sleep(100);
    //   } catch(InterruptedException e) {
    //     print("sleep interrupted");
    //   }
    // }
    print("Stopping " + this);
  }
  public synchronized int getValue() { return number; }
  public String toString() {
    return "Entrance " + id + ": " + getValue();
  }
  public static int getTotalCount() {
    return count.value();
  }
  public static int sumEntrances() {
    int sum = 0;
    for(Entrance entrance : entrances)
      sum += entrance.getValue();
    return sum;
  }
}

public class ExerciseNineteen {
  public static void main(String[] args) throws Exception {
    List<Thread> threads = new ArrayList<Thread>();
    for(int i = 0; i < 5; i++)
      threads.add(new Thread(new Entrance(i)));
    for(int i = 0; i < 5; i++)
      threads.get(i).start();
    TimeUnit.SECONDS.sleep(3);
    for(int i = 0; i < 5; i++)
      threads.get(i).interrupt();
    print("Total: " + Entrance.getTotalCount());
    print("Sum of Entrances: " + Entrance.sumEntrances());
  }
}