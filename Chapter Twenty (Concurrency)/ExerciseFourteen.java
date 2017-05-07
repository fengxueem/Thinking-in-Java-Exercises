import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;

class TimerGenerator implements Runnable {
  private static int counter = 0;
  private static Random random = new Random();
  private static int outputCounter = 0;
  private static Lock lock = new ReentrantLock();
  private int id = counter++;

  public void run() {
  	try {
      Timer t = new Timer();
      t.schedule(new TimerTask() {
  		    public void run() {
  		  	  lock.lock();
  		  	  try {
  		  	    outputCounter++;
  		        System.out.println(outputCounter + "-> ID: " + id + " at " + System.currentTimeMillis());
  		  	  } finally {
  		  	    lock.unlock();
  		  	  }
            // The cancellation here is very important! As the API says:
            // After the last live reference to a Timer object goes away
            // and all outstanding tasks have completed execution, the 
            // timer's task execution thread terminates gracefully (and 
            // becomes subject to garbage collection). However, this can 
            // take arbitrarily long to occur. By default, the task execution 
            // thread does not run as a daemon thread, so it is capable 
            // of keeping an application from terminating. If a caller 
            // wants to terminate a timer's task execution thread rapidly, 
            // the caller should invoke the timer's cancel method.
            // Note that calling this method from within the run method 
            // of a timer task that was invoked by this timer absolutely 
            // guarantees that the ongoing task execution is the last task
            // execution that will ever be performed by this timer.
            t.cancel();
  		    }
  	  }, random.nextInt(5000));
  	} catch(IllegalArgumentException e) {
  	  System.out.println("ID: " + id + " with negative delay");
  	} catch(IllegalStateException e) {
  	  System.out.println("ID: " + id + " timer state went wrong");
  	}
  }
}

public class ExerciseFourteen {
  public static void main(String[] args) throws Exception {
  	ExecutorService service = Executors.newCachedThreadPool();
  	for (int i = 0; i < 4000; i++) {
  	  service.execute(new TimerGenerator());
  	}
  	TimeUnit.SECONDS.sleep(10);
  	System.out.println("Starting to shut down");
  	service.shutdownNow();
  } 
}