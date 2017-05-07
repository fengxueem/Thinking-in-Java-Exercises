import java.util.concurrent.*;
import java.util.*;

class Fibonacci implements Callable<Long> {
  private static int counter = 0;
  private final int id = counter++;
  private int count = 0;
  private int n = 0;

  public Fibonacci(int n) {
    this.n = n;
  }

  private long fib(int n) {
    if(n < 2) return 1l;
    return fib(n-2) + fib(n-1);
  }
  public Long call() {
    long sum = 0l;
    for (int i = 0; i < n; i++) {
      sum += fib(i);
    }
    return sum;
  }
}

public class ExerciseFive {
  public static void main(String[] args) {
    ExecutorService service = Executors.newCachedThreadPool();
    List<Future<Long>> futureList = new ArrayList<Future<Long>>();
    for (int i = 0; i < 3; i++) {
      futureList.add(service.submit(new Fibonacci(1 + i)));
    }
    for (Future<Long> f : futureList) {
      try {
        System.out.println(f.get());
      } catch(Exception e) {
        e.printStackTrace();
      }
    }
    service.shutdown();
  }
}