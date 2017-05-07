// About 'Active Object' concurrency pattern,
// It isolates the call to a method from the
// execution. And java 1.5 has already implement
// this active-object way by ExecutorService and
// Future. So What I did here and Brucel wrote in
// the book is only using the built-in tool: ExecutorService & Future.
// Here we share a car between different threads, however,
// all threads are driven by a single thread executor, which
// somehow promises that this car won't be accessed by two tasks
// at same time. Thus, in this example, methods of Car 
// could not be synchronized.
import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Car {
  private boolean waxOn = false;
  public synchronized void waxed() {
    waxOn = true; // Ready to buff
  }
  public synchronized void buffed() {
    waxOn = false; // Ready for another coat of wax
  }
}

class CarActiveObject {
  private ExecutorService ex =
      Executors.newSingleThreadExecutor();
  private Car car = new Car();
  private Random rand = new Random(47);
  // Insert a random delay to produce the effect
  // of a calculation time:
  private void pause(int factor) {
    try {
      TimeUnit.MILLISECONDS.sleep(
        100 + rand.nextInt(factor));
    } catch(InterruptedException e) {
      print("sleep() interrupted");
    }
  }
  // isolate the call to car.waxed() via submitting a task to newSingleThreadExecutor
  // and let this executor to decide when to run the true method
  public Future<String> waxOn() {
    return ex.submit(new Callable<String>() {
      public String call() {
        car.waxed();
        // car.waitForBuffing();
        pause(500);
        return "Wax On! ";
      }
    });
  }
  // isolate the call to car.buffed() via submitting a task to newSingleThreadExecutor
  // and let this executor to decide when to run the true method
  public Future<String> waxOff() {
    return ex.submit(new Callable<String>() {
      public String call() {
        car.buffed();
        // car.waitForBuffing();
        pause(500);
        return "Wax Off! ";
      }
    });
  }
  public void shutdown() {
    ex.shutdown(); 
  }
}

class WaxOMatic {
  public static void main(String[] args) {
    CarActiveObject c1 = new CarActiveObject();
    // Prevents ConcurrentModificationException:
    List<Future<String>> results =
      new CopyOnWriteArrayList<Future<String>>();
    for(int i = 0; i < 5; i++) {
      results.add(c1.waxOn());
      results.add(c1.waxOff());
    }
    print("All asynch calls made");
    while(results.size() > 0) {
      for(Future<?> f : results)
        if(f.isDone()) {
          try {
            print(f.get());
          } catch(Exception e) {
            throw new RuntimeException(e);
          }
          results.remove(f);
        }
    }
    c1.shutdown();
  }
}

public class ExerciseFourtyTwo {
  public static void main(String[] args){
    WaxOMatic.main(args); 
  } 
}