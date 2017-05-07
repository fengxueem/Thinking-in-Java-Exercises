//: concurrency/ToastOMatic.java
// A toaster that uses queues.
import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Toast {
  public enum Status { DRY, PEANUTBUTTERED, JELLIED }
  private Status status = Status.DRY;
  private final int id;
  public Toast(int idn) { id = idn; }
  public void peanutButter() { status = Status.PEANUTBUTTERED; }
  public void jelly() { status = Status.JELLIED; }
  public Status getStatus() { return status; }
  public int getId() { return id; }
  public String toString() {
    return "Toast " + id + ": " + status;
  }
}

class Sandwich {
  private Toast top, bottom;
  private final int id;
  public Sandwich(int idn, Toast top, Toast bottom) {
   id = idn; 
   this.top = top;
   this.bottom = bottom;
  }
  public int getId() { return id; }
  public Toast getTop() { return top; }
  public Toast getBottom() { return bottom; }
  public String toString() {
    return "Sandwich " + id + ": " + 
        "top " + top + "; bottom " + bottom;
  }
}

class ToastQueue extends LinkedBlockingQueue<Toast> {}
class SandwichQueue extends LinkedBlockingQueue<Sandwich> {}

class Toaster implements Runnable {
  private ToastQueue toastQueue;
  private int count = 0;
  private Random rand = new Random(47);
  public Toaster(ToastQueue tq) { toastQueue = tq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(
          100 + rand.nextInt(500));
        // Make toast
        Toast t = new Toast(count++);
        print(t);
        // Insert into queue
        toastQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("Toaster interrupted");
    }
    print("Toaster off");
  }
}

class PeanutButterer implements Runnable {
  private ToastQueue dryQueue, butteredQueue;
  public PeanutButterer(ToastQueue dry, ToastQueue buttered) {
    dryQueue = dry;
    butteredQueue = buttered;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Toast t = dryQueue.take();
        t.peanutButter();
        print(t);
        butteredQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("PeanutButterer interrupted");
    }
    print("PeanutButterer off");
  }
}

class Jellyer implements Runnable {
  private ToastQueue dryQueue, jelliedQueue;
  public Jellyer(ToastQueue dry, ToastQueue jellied) {
    dryQueue = dry;
    jelliedQueue = jellied;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Toast t = dryQueue.take();
        t.jelly();
        print(t);
        jelliedQueue.put(t);
      }
    } catch(InterruptedException e) {
      print("Jellyer interrupted");
    }
    print("Jellyer off");
  }
}

class SandwichMaker implements Runnable {
  private SandwichQueue sandwichQueue;
  private ToastQueue jelliedQueue, peanutButteredQueue;
  private int count = 0;
  public SandwichMaker(SandwichQueue sandwichQueue,
     ToastQueue jelliedQueue, ToastQueue peanutButteredQueue) {
    this.sandwichQueue = sandwichQueue;
    this.jelliedQueue = jelliedQueue;
    this.peanutButteredQueue = peanutButteredQueue;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // TimeUnit.MILLISECONDS.sleep(
        //   100 + rand.nextInt(500));
        // Make toast
        Sandwich sandwich = new Sandwich(count++, jelliedQueue.take(),
           peanutButteredQueue.take());
        print(sandwich);
        // Insert into queue
        sandwichQueue.put(sandwich);
      }
    } catch(InterruptedException e) {
      print("SandwichMaker interrupted");
    }
    print("SandwichMaker off");
  }
}

// Consume the toast:
class SandwichEater implements Runnable {
  private SandwichQueue finishedQueue;
  public SandwichEater(SandwichQueue finished) {
    finishedQueue = finished;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until next piece of toast is available:
        Sandwich s = finishedQueue.take();
        // Verify that the toast is coming in order,
        // and that all pieces are getting jammed:
        if(s.getTop().getStatus() != Toast.Status.JELLIED ||
           s.getBottom().getStatus() != Toast.Status.PEANUTBUTTERED) {
          print(">>>> Error: " + s);
          System.exit(1);
        } else
          print("Chomp! " + s);
      }
    } catch(InterruptedException e) {
      print("SandwichEater interrupted");
    }
    print("SandwichEater off");
  }
}

class ToastOMatic {
  public static void main(String[] args) throws Exception {
    ToastQueue dryQueue = new ToastQueue(),
               topQueue = new ToastQueue(),
               bottomQueue = new ToastQueue();
    SandwichQueue finishedQueue = new SandwichQueue();
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(new Toaster(dryQueue));
    exec.execute(new PeanutButterer(dryQueue, bottomQueue));
    exec.execute(new Jellyer(dryQueue, topQueue));
    exec.execute(new SandwichMaker(finishedQueue, topQueue, bottomQueue));
    exec.execute(new SandwichEater(finishedQueue));
    TimeUnit.SECONDS.sleep(5);
    exec.shutdownNow();
  }
}

public class ExerciseTwentyNine {
  public static void main(String[] args) throws Exception {
    ToastOMatic.main(args); 
  }  
}