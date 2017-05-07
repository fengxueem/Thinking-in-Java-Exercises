//: concurrency/BankTellerSimulation.java
// Using queues and multithreading.
// {Args: 5}
import java.util.concurrent.*;
import java.util.*;

// Read-only objects don't require synchronization:
class Request {
  private final int serviceTime;
  public Request(int tm) { serviceTime = tm; }
  public int getServiceTime() { return serviceTime; }
  public String toString() {
    return "[" + serviceTime + "]";
  }
}

// Teach the request line to display itself:
class RequestLine extends ArrayBlockingQueue<Request> {
  public RequestLine(int maxLineSize) {
    super(maxLineSize);
  }
  public String toString() {
    if(this.size() == 0)
      return "[Empty]";
    StringBuilder result = new StringBuilder();
    for(Request request : this)
      result.append(request);
    return result.toString();
  }
}

// Randomly add customers to a queue:
class RequestGenerator implements Runnable {
  // this interval is counted in millisecond
  private long generatorInterval = 200;
  private long recoverInterval = 200;
  private RequestLine requests;
  private static Random rand = new Random(47);
  public RequestGenerator(RequestLine rl) {
    requests = rl;
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(generatorInterval);
        try {
          requests.add(new Request(rand.nextInt(1000)));
          if (generatorInterval > 1) {
            generatorInterval--;
          }
        } catch(IllegalStateException e) {
          if (generatorInterval < recoverInterval / 2) {
            recoverInterval = recoverInterval / 2;
          } else {
            recoverInterval = generatorInterval * 2;
          }
          generatorInterval = recoverInterval;
        }
      }
    } catch(InterruptedException e) {
      System.out.println("RequestGenerator interrupted");
    }
    System.out.println("RequestGenerator terminating");
  }
  public String reportFrequency() {
    // StringBuilder sb =new StringBuilder();
    // float freq = 1000 / (float)generatorInterval;
    // String str = String.format("%.2f",freq);
    // return sb.append(" " + str).toString();
    return "" + generatorInterval;
  }
}

class Server implements Runnable, Comparable<Server> {
  private static int counter = 0;
  private final int id = counter++;
  // Customers served during this shift:
  private int requestsServed = 0;
  private RequestLine requests;
  private boolean servingRequestLine = true;
  public Server(RequestLine rl) { requests = rl; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        Request request = requests.take();
        TimeUnit.MILLISECONDS.sleep(
          request.getServiceTime());
        synchronized(this) {
          requestsServed++;
          while(!servingRequestLine)
            wait();
        }
      }
    } catch(InterruptedException e) {
      System.out.println(this + "interrupted");
    }
    System.out.println(this + "terminating");
  }
  public synchronized void doSomethingElse() {
    requestsServed = 0;
    servingRequestLine = false;
  }
  public synchronized void serveCustomerLine() {
    assert !servingRequestLine:"already serving: " + this;
    servingRequestLine = true;
    notifyAll();
  }
  public String toString() { return "Server " + id + " "; }
  public String shortString() { return "S" + id; }
  // Used by priority queue:
  public synchronized int compareTo(Server other) {
    return requestsServed < other.requestsServed ? -1 :
      (requestsServed == other.requestsServed ? 0 : 1);
  }
}

class ServerManager implements Runnable {
  private ExecutorService exec;
  private RequestLine requests;
  private PriorityQueue<Server> workingServer =
    new PriorityQueue<Server>();
  private Queue<Server> serverDoingOtherThings =
    new LinkedList<Server>();
  private int adjustmentPeriod;
  private static Random rand = new Random(47);
  public ServerManager(ExecutorService e,
    RequestLine requests, int adjustmentPeriod) {
    exec = e;
    this.requests = requests;
    this.adjustmentPeriod = adjustmentPeriod;
    // Start with a single server:
    for (int i = 0; i < 5; i++) {
      Server server = new Server(requests);
      exec.execute(server);
      workingServer.add(server);
    }
  }
  // public void adjustTellerNumber() {
  //   // This is actually a control system. By adjusting
  //   // the numbers, you can reveal stability issues in
  //   // the control mechanism.
  //   // If line is too long, add another teller:
  //   if(requests.size() / workingServer.size() > 2) {
  //       // If tellers are on break or doing
  //       // another job, bring one back:
  //       if(serverDoingOtherThings.size() > 0) {
  //         Server server = serverDoingOtherThings.remove();
  //         server.serveCustomerLine();
  //         workingServer.offer(server);
  //         return;
  //       }
  //     // Else create (hire) a new teller
  //     Server server = new Server(requests);
  //     exec.execute(server);
  //     workingServer.add(server);
  //     return;
  //   }
  //   // If line is short enough, remove a teller:
  //   if(workingServer.size() > 1 &&
  //     requests.size() / workingServer.size() < 2)
  //       reassignOneServer();
  //   // If there is no line, we only need one teller:
  //   if(requests.size() == 0)
  //     while(workingServer.size() > 1)
  //       reassignOneServer();
  // }
  // // Give a teller a different job or a break:
  // private void reassignOneServer() {
  //   Server server = workingServer.poll();
  //   server.doSomethingElse();
  //   serverDoingOtherThings.offer(server);
  // }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(adjustmentPeriod);
        // adjustTellerNumber();
        System.out.print(requests + " { ");
        for(Server server : workingServer)
          System.out.print(server.shortString() + " ");
        System.out.println("}");
      }
    } catch(InterruptedException e) {
      System.out.println(this + "interrupted");
    }
    System.out.println(this + "terminating");
  }
  public String toString() { return "TellerManager "; }
}

class ServerRequestSimulation {
  static final int MAX_LINE_SIZE = 50;
  static final int ADJUSTMENT_PERIOD = 1000;
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    // If line is too long, customers will leave:
    RequestLine requests =
      new RequestLine(MAX_LINE_SIZE);
    RequestGenerator gen = new RequestGenerator(requests);
    exec.execute(gen);
    // Manager will add and remove tellers as necessary:
    exec.execute(new ServerManager(
      exec, requests, ADJUSTMENT_PERIOD));
    exec.execute(new Runnable(){
        public void run(){
          try{
            while(!Thread.interrupted()){
              TimeUnit.MILLISECONDS.sleep(500);
              System.out.println(gen.reportFrequency() + " ["
                 + requests.size() + "/" + MAX_LINE_SIZE + "] ");
            }
          }catch(InterruptedException ie){
            System.out.println("Print interrupted!");
          }finally{
            System.out.println("Printer exit!");
          }
        }
    });
    if(args.length > 0) // Optional argument
      TimeUnit.SECONDS.sleep(new Integer(args[0]));
    else {
      System.out.println("Press 'Enter' to quit");
      System.in.read();
    }
    
    exec.shutdownNow();
  }
}

public class ExerciseThirtyFive {
  public static void main(String[] args) throws Exception
   {
    ServerRequestSimulation.main(args);    
  }  
}