class Fibonacci implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private int count = 0;
  private int n = 0;

  public Fibonacci(int n) {
    this.n = n;
  }

  private int fib(int n) {
    if(n < 2) return 1;
    return fib(n-2) + fib(n-1);
  }
  public void run() {
    for (int i = 0; i < n; i++) {
      System.out.println("thread" + id + " Fibonacci sequence: " + fib(i));
    }
  }
}

public class ExerciseTwo {
  public static void main(String[] args) {
    for (int i = 0; i < 3; i++) {
      new Thread(new Fibonacci(5)).start();
    }
  }
}