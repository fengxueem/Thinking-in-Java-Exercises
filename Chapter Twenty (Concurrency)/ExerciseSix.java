import java.util.*;
import java.util.concurrent.*;

class Sleeper implements Runnable {
  private static Random random = new Random();
  private static int counter = 0;
  private final int id = counter++;

  public void run() {
  	int sleepTime = random.nextInt(11);
  	try {
  	  TimeUnit.SECONDS.sleep(sleepTime);
  	} catch(InterruptedException e) {
  	  System.out.print("Interrupted");
  	}
  	System.out.println(id + " sleeps " + sleepTime + "secs");
  }
}

public class ExerciseSix {
  public static void main(String[] args) {
  	// Note: the quantity of running threads is given a
  	// fixed number instead given on the command line.
  	ExecutorService service = Executors.newCachedThreadPool();
  	for (int i = 0; i < 5; i++) {
  	  service.execute(new Sleeper());
  	}
  	service.shutdown();
  }
}