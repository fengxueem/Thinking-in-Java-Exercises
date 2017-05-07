import java.util.concurrent.*;
import java.util.*;

class Fibonacci {
  private static int counter = 0;
  private final int id = counter++;
  private ExecutorService service = Executors.newCachedThreadPool();
  
  private class Inner implements Callable<Long> {
    private int n;

    public Inner(int n) {
      this.n = n;
    }

    public Long call() {
      long sum = 0l;
      for (int i = 0; i < n; i++) {
        sum += fib(i);
      }
      return sum;
    }
  }

  private long fib(int n) {
    if(n < 2) return 1l;
    return fib(n-2) + fib(n-1);
  }

  public Future<Long> runTask(int n) {
    return service.submit(new Inner(n));
  } 
  public void shutdownExecutor() {
    service.shutdown();
  }
}

public class ExerciseTen {
  public static void main(String[] args) {
    List<Future<Long>> futureList = new ArrayList<Future<Long>>();
    Fibonacci f = new Fibonacci();
    for (int i = 0; i < 3; i++) {
      futureList.add(f.runTask(1 + i));
    }
    for (Future<Long> future : futureList) {
      try {
        System.out.println(future.get());
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    f.shutdownExecutor();
  }
}