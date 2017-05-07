import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Chopstick {
  private boolean taken = false;
  public synchronized
  void take() throws InterruptedException {
    while(taken)
      wait();
    taken = true;
  }
  public synchronized void drop() {
    taken = false;
    notifyAll();
  }
}

class ChopstickBin extends LinkedBlockingQueue<Chopstick> {}

class Philosopher implements Runnable {
  private enum Status {THINKING, EATING}
  private Status status = Status.THINKING;
  private Chopstick left;
  private Chopstick right;
  private ChopstickBin bin;
  private final int id;
  private final int ponderFactor;
  private Random rand = new Random(47);
  private void pause() throws InterruptedException {
    if(ponderFactor == 0) return;
    TimeUnit.MILLISECONDS.sleep(
      rand.nextInt(ponderFactor * 250));
  }
  public Philosopher(ChopstickBin bin, int ident, int ponder) {
    this.bin = bin;
    id = ident;
    ponderFactor = ponder;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        switch(status) {
          case THINKING: 
              print(this + " " + "thinking");
              pause();
              status = Status.EATING;
              break;
          case EATING: 
              // Philosopher becomes hungry
              // synchronized(bin) {
                // if (bin.remainingCapacity() >= 2) {
                  // print(this + " " + "grabbing chopsticks");
                  right = bin.take();
                  left = bin.take();
                  print(this + " " + "eating");
                  pause();
                  bin.put(right);
                  bin.put(left);
                  status = Status.THINKING;
                // }
              // }
              break;
        }
      }
    } catch(InterruptedException e) {
      print(this + " " + "exiting via interrupt");
    }
  }
  public String toString() { return "Philosopher " + id; }
}

class DeadlockingDiningPhilosophers {
  public static void main(String[] args) throws Exception {
    int ponder = 5;
    if(args.length > 0)
      ponder = Integer.parseInt(args[0]);
    int size = 5;
    if(args.length > 1)
      size = Integer.parseInt(args[1]);
    ExecutorService exec = Executors.newCachedThreadPool();
    // Chopstick[] sticks = new Chopstick[size];
    ChopstickBin bin = new ChopstickBin();
    for(int i = 0; i < size; i++)
      bin.put(new Chopstick());
    for(int i = 0; i < size; i++)
      exec.execute(new Philosopher(bin, i, ponder));
    if(args.length == 3 && args[2].equals("timeout"))
      TimeUnit.SECONDS.sleep(5);
    else {
      System.out.println("Press 'Enter' to quit");
      System.in.read();
    }
    exec.shutdownNow();
  }
}

public class ExerciseThirtyOne {
  public static void main(String[] args) throws Exception {
    DeadlockingDiningPhilosophers.main(args);
  }  
}