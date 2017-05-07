import java.util.concurrent.*;

class Target {
  private boolean flag = false;

  public synchronized void set(boolean b) {
    flag = b;
    notify();
  }

  public synchronized boolean get() {
    return flag;
  }
}

class SleepAndSet implements Runnable {
  private Target t;

  public SleepAndSet(Target t) {
    this.t = t;
  }

  public void run() {
    try {
      TimeUnit.MILLISECONDS.sleep(317);
    } catch(InterruptedException e) {
      System.out.println("sleep interrupted in A");
    }
    t.set(true);
  }
}

class BusyWait implements Runnable {
  private Target t;

  public BusyWait(Target t) {
    this.t = t;
  }

  public void run() {
    long start = System.nanoTime();
    while (!Thread.interrupted()) {
      if (t.get()) {
        t.set(false);
        System.out.println("Busy: Report and Unset flag!" + (System.nanoTime() - start) + "ns");
      }
    }
  }
}

class NotifyAndWait implements Runnable {
  private Target t;

  public NotifyAndWait(Target t) {
    this.t = t;
  }

  public void run() {
    long start = 0l;
    start = System.nanoTime();
    while (true) {
      try {
        synchronized(t) {
          while (!t.get()) {
            t.wait();
          }
          t.set(false);
          System.out.println("Notify: Report and Unset flag!" + (System.nanoTime() - start) + "ns");
        }
      } catch(InterruptedException e) {
        break;
      }
    }
  }
}

public class ExerciseTwentyTwo {
  public static void main(String[] args){
    ExecutorService exec = Executors.newCachedThreadPool();
    Target t = new Target();
    exec.execute(new SleepAndSet(t));
    exec.execute(new BusyWait(t));
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
      System.out.println("sleep interrupted in main()");
    }
    System.out.println("");
    t = new Target();
    exec.execute(new SleepAndSet(t));
    exec.execute(new NotifyAndWait(t));
    try {
      TimeUnit.SECONDS.sleep(1);
    } catch(InterruptedException e) {
      System.out.println("sleep interrupted in main()");
    }
    exec.shutdownNow();
  }  
}