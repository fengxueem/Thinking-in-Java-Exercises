import java.util.concurrent.*;
import java.util.concurrent.atomic.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.*;
import static net.mindview.util.Print.*;

class FastSimulation {
  static final int N_ELEMENTS = 7;
  static final int N_GENES = 7;
  static final int N_EVOLVERS = 50;
  static final AtomicInteger[][] GRID =
    new AtomicInteger[N_ELEMENTS][N_GENES];
  static Random rand = new Random(47);
  static class Evolver implements Runnable {
    public void run() {
      while(!Thread.interrupted()) {
        // Randomly select an element to work on:
        int element = rand.nextInt(N_ELEMENTS);
        for(int i = 0; i < N_GENES; i++) {
          int previous = element - 1;
          if(previous < 0) previous = N_ELEMENTS - 1;
          int next = element + 1;
          if(next >= N_ELEMENTS) next = 0;
          int oldvalue = GRID[element][i].get();
          // Perform some kind of modeling calculation:
          int newvalue = oldvalue +
            GRID[previous][i].get() + GRID[next][i].get();
          newvalue /= 3; // Average the three values
          if(!GRID[element][i]
            .compareAndSet(oldvalue, newvalue)) {
            // Policy here to deal with failure. Here, we
            // just report it and ignore it; our model
            // will eventually deal with it.
            // print("Old value changed from " + oldvalue);
          }
        }
      }
    }
  }
  static String matrix() {
    StringBuilder builder = new StringBuilder();    
    for(int i = 0; i < N_ELEMENTS; i++) {
      builder.append("[ ");
      for(int j = 0; j < N_GENES; j++) {
        builder.append(GRID[i][j] + " ");
      }
      builder.append("]" + System.getProperty("line.separator"));
    }
    return builder.toString();
  }
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < N_ELEMENTS; i++)
      for(int j = 0; j < N_GENES; j++)
        GRID[i][j] = new AtomicInteger(rand.nextInt(1000));
    print(matrix());
    for(int i = 0; i < N_EVOLVERS; i++)
      exec.execute(new Evolver());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
    print(matrix());
  }
}

class FastSimulationInt {
  static final int N_ELEMENTS = 7;
  static final int N_GENES = 7;
  static final int N_EVOLVERS = 50;
  static final int[][] GRID =
    new int[N_ELEMENTS][N_GENES];
  static Random rand = new Random(47);
  static Lock lock = new ReentrantLock();
  static class Evolver implements Runnable {
    public void run() {
      while(!Thread.interrupted()) {
        // Randomly select an element to work on:
        int element = rand.nextInt(N_ELEMENTS);
        for(int i = 0; i < N_GENES; i++) {
          int previous = element - 1;
          if(previous < 0) previous = N_ELEMENTS - 1;
          int next = element + 1;
          if(next >= N_ELEMENTS) next = 0;
          lock.lock();
          try {
            int oldvalue = GRID[element][i];
            // Perform some kind of modeling calculation:
            int newvalue = oldvalue +
                GRID[previous][i] + GRID[next][i];
            newvalue /= 3; // Average the three values
            GRID[element][i] = newvalue;
          } finally {
            lock.unlock();
          }
        }
      }
    }
  }
  static String matrix() {
    StringBuilder builder = new StringBuilder();    
    for(int i = 0; i < N_ELEMENTS; i++) {
      builder.append("[ ");
      for(int j = 0; j < N_GENES; j++) {
        builder.append(GRID[i][j] + " ");
      }
      builder.append("]" + System.getProperty("line.separator"));
    }
    return builder.toString();
  }
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i = 0; i < N_ELEMENTS; i++)
      for(int j = 0; j < N_GENES; j++)
        GRID[i][j] = rand.nextInt(1000);
    print(matrix());
    for(int i = 0; i < N_EVOLVERS; i++)
      exec.execute(new Evolver());
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
    print(matrix());
  }
}

public class ExerciseThirtyNine {
  public static void main(String[] args) throws Exception{
    print("lock version");
    FastSimulationInt.main(args);
    print("optimistic version");
    FastSimulation.main(args);
    // clearly lock version gets bigger numbers every time
    // this optimistic version doesn't guarantee performance
    // improvement
  }  
}