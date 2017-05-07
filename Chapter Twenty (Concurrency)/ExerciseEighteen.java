import java.util.concurrent.*;

class A {
  
  public synchronized static void stopForThreeSec() {
  	try {
      TimeUnit.SECONDS.sleep(3);
    } catch(InterruptedException e) {
      System.out.println("InterruptedException");
    }
  }

}

class ADriver implements Runnable {

  public void run() {
  	A.stopForThreeSec();
  }

}

public class ExerciseEighteen {

  public static void main(String[] args) {
    // ExecutorService service = Executors.newSingleThreadExecutor();
    // Future<?> f = service.submit(new ADriver());
    // service.shutdown();
    // f.cancel(true);
    Thread t = new Thread(new ADriver());
    t.start();
    t.interrupt();
  }

}