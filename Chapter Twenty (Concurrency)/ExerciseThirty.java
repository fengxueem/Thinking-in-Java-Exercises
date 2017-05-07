import java.util.concurrent.*;
import java.io.*;
import java.util.*;
import static net.mindview.util.Print.*;

class Sender implements Runnable {
  private Random rand = new Random(47);
  private BlockingQueue<Character> out = new LinkedBlockingQueue<Character>();
  public BlockingQueue<Character> getWriter() { return out; }
  public void run() {
    try {
      while(true)
        for(char c = 'A'; c <= 'z'; c++) {
          out.put(c);
          TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
        }
    } catch(InterruptedException e) {
      print(e + " Sender sleep interrupted");
    }
  }
}

class Receiver implements Runnable {
  private BlockingQueue<Character> in;
  public Receiver(Sender sender) throws IOException {
    in = sender.getWriter();
  }
  public void run() {
    try {
      while(true) {
        // Blocks until characters are there:
        printnb("Read: " + (char)in.take() + ", ");
      }
    } catch(InterruptedException e) {
      print(e + " Receiver read exception");
    }
  }
}

class PipedIO {
  public static void main(String[] args) throws Exception {
    Sender sender = new Sender();
    Receiver receiver = new Receiver(sender);
    ExecutorService exec = Executors.newCachedThreadPool();
    exec.execute(sender);
    exec.execute(receiver);
    TimeUnit.SECONDS.sleep(4);
    exec.shutdownNow();
  }
}

public class ExerciseThirty {
  public static void main(String[] args) throws Exception {
    PipedIO.main(args);    
  }  
}