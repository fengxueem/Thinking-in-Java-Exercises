import java.util.concurrent.*;
import java.util.*;
import net.mindview.util.*;

class ExchangerProducer<T> implements Runnable {
  private Generator<T> generator;
  private Exchanger<List<T>> exchanger;
  private List<T> holder;
  ExchangerProducer(Exchanger<List<T>> exchg,
  Generator<T> gen, List<T> holder) {
    exchanger = exchg;
    generator = gen;
    this.holder = holder;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        for(int i = 0; i < ExchangerDemo.size; i++)
          holder.add(generator.next());
        // Exchange full for empty:
        holder = exchanger.exchange(holder);
      }
    } catch(InterruptedException e) {
      // OK to terminate this way.
    }
  }
}

class ExchangerConsumer<T> implements Runnable {
  private Exchanger<List<T>> exchanger;
  private List<T> holder;
  private volatile T value;
  ExchangerConsumer(Exchanger<List<T>> ex, List<T> holder){
    exchanger = ex;
    this.holder = holder;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        holder = exchanger.exchange(holder);
        for(T x : holder) {
          value = x; // Fetch out value
          holder.remove(x); // OK for CopyOnWriteArrayList
        }
      }
    } catch(InterruptedException e) {
      // OK to terminate this way.
    }
    System.out.println("Final value: " + value);
  }
}

class ExchangerDemo {
  static int size = 10;
  static int delay = 5; // Seconds
  public static void main(String[] args) throws Exception {
    if(args.length > 0)
      size = new Integer(args[0]);
    if(args.length > 1)
      delay = new Integer(args[1]);
    ExecutorService exec = Executors.newCachedThreadPool();
    Exchanger<List<ExerciseThirtyFour>> xc = new Exchanger<List<ExerciseThirtyFour>>();
    List<ExerciseThirtyFour>
      producerList = new CopyOnWriteArrayList<ExerciseThirtyFour>(),
      consumerList = new CopyOnWriteArrayList<ExerciseThirtyFour>();
    exec.execute(new ExchangerProducer<ExerciseThirtyFour>(xc,
      BasicGenerator.create(ExerciseThirtyFour.class), producerList));
    exec.execute(
      new ExchangerConsumer<ExerciseThirtyFour>(xc,consumerList));
    TimeUnit.SECONDS.sleep(delay);
    exec.shutdownNow();
  }
}

public class ExerciseThirtyFour {
  private static int counter = 0;
  private int id = counter++;
  public static void main(String[] args) throws Exception {
    ExchangerDemo.main(args);    
  }  
  public String toString() {
    return ("" + id);
  }
}