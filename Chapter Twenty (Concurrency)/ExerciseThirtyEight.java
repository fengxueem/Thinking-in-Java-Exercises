import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;
import java.util.*;
import static net.mindview.util.Print.*;

class House {
  private final int id;
  // 1.footings
  // 2.steel, concreteForms
  // 3.concreteFoundation
  // 4.plumbing
  // 5.slab
  // 6.framing
  private boolean
      footings = false, steel = false, concreteForms = false,
      concreteFoundation = false, plumbing = false, slab = false,
      framing = false;
  public House(int idn)  { id = idn; }
  // Empty House object:
  public House()  { id = -1; }
  public synchronized int getId() {
      return id; 
  }
  public synchronized void addFootings() { 
    footings = true; 
    notifyAll();
  }
  public synchronized void addSteel() throws InterruptedException {
    while(!footings) {
      wait();
    }
    steel = true;
    notifyAll();
  }
  public synchronized void addConcreteForms() throws InterruptedException {
    while(!footings) {
      wait();
    }
    concreteForms = true;
    notifyAll();
  }
  public synchronized void addConcreteFoundation() throws InterruptedException {
    while(!(concreteForms && steel)) {
      wait();
    }
    concreteFoundation = true;
    notifyAll();
  }
  public synchronized void addPlumbing() throws InterruptedException {
    while(!concreteFoundation) {
      wait();
    }
    plumbing = true;
    notifyAll();
  }
  public synchronized void addSlab() throws InterruptedException {
    while(!plumbing) {
      wait();
    }
    slab = true;
    notifyAll();
  }
  public synchronized void addFraming() throws InterruptedException {
    while(!slab) {
      wait();
    }
    framing = true;
  }
  public synchronized String toString() {
    return "House " + id + " [" + " footings: " + footings
        + " steel: " + steel
        + " concreteForms: " + concreteForms
        + " concreteFoundation: " + concreteFoundation
        + " plumbing: " + plumbing
        + " slab: " + slab
        + " framing: " + framing + " ]";
  }
}

class HouseQueue extends LinkedBlockingQueue<House> {}

class BluePrintBuilder implements Runnable {
  private HouseQueue houseQueue;
  private int counter = 0;
  public BluePrintBuilder(HouseQueue hq) { houseQueue = hq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        TimeUnit.MILLISECONDS.sleep(500);
        // Make empty house:
        House h = new House(counter++);
        print("BluePrintBuilder created " + h);
        // Insert into queue
        houseQueue.put(h);
      }
    } catch(InterruptedException e) {
      print("Interrupted: BluePrintBuilder");
    }
    print("BluePrintBuilder off");
  }
}

class Assembler implements Runnable {
  private HouseQueue waitingQueue, finishingQueue;
  private House house;
  private CyclicBarrier barrier = new CyclicBarrier(8);
  private ConstructorPool constructorPool;
  public Assembler(HouseQueue wq, HouseQueue fq, ConstructorPool cp){
    waitingQueue = wq;
    finishingQueue = fq;
    constructorPool = cp;
  }
  public House house() { return house; }
  public CyclicBarrier barrier() { return barrier; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until chassis is available:
        house = waitingQueue.take();
        // Hire constructors to perform work:
        constructorPool.hire(FootingsConstructor.class, this);
        constructorPool.hire(SteelConstructor.class, this);
        constructorPool.hire(FormsConstructor.class, this);
        constructorPool.hire(FoundationConstructor.class, this);
        constructorPool.hire(PlumbingConstructor.class, this);
        constructorPool.hire(SlabConstructor.class, this);
        constructorPool.hire(FrameConstructor.class, this);
        barrier.await(); // Until the constructors finish
        // Put house into finishingQueue for further work
        finishingQueue.put(house);
      }
    } catch(InterruptedException e) {
      print("Exiting Assembler via interrupt");
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
    print("Assembler off");
  }
}

class Reporter implements Runnable {
  private HouseQueue houseQueue;
  public Reporter(HouseQueue hq) { houseQueue = hq; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        print(houseQueue.take());
      }
    } catch(InterruptedException e) {
      print("Exiting Reporter via interrupt");
    }
    print("Reporter off");
  }
}

abstract class Constructor implements Runnable {
  private ConstructorPool pool;
  public Constructor(ConstructorPool p) { pool = p; }
  protected Assembler assembler;
  public Constructor assignAssembler(Assembler assembler) {
    this.assembler = assembler;
    return this;
  }
  private boolean engage = false;
  public synchronized void engage() {
    engage = true;
    notifyAll();
  }
  // The part of run() that's different for each robot:
  abstract protected void performService() throws InterruptedException;
  public void run() {
    try {
      powerDown(); // Wait until needed
      while(!Thread.interrupted()) {
        performService();
        assembler.barrier().await(); // Synchronize
        // We're done with that job...
        powerDown();
      }
    } catch(InterruptedException e) {
      print("Exiting " + this + " via interrupt");
    } catch(BrokenBarrierException e) {
      // This one we want to know about
      throw new RuntimeException(e);
    }
    print(this + " off");
  }
  private synchronized void
  powerDown() throws InterruptedException {
    engage = false;
    assembler = null; // Disconnect from the Assembler
    // Put ourselves back in the available pool:
    pool.release(this);
    while(engage == false)  // Power down
      wait();
  }
  public String toString() { return getClass().getName(); }
}

class FootingsConstructor extends Constructor {
  public FootingsConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addFootings();
    print(this + " footings installed");
  }
}

class SteelConstructor extends Constructor {
  public SteelConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addSteel();
    print(this + " steel installed");
  }
}

class FormsConstructor extends Constructor {
  public FormsConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addConcreteForms();
    print(this + " forms installed");
  }
}

class FoundationConstructor extends Constructor {
  public FoundationConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addConcreteFoundation();
    print(this + " foundation installed");
  }
}

class PlumbingConstructor extends Constructor {
  public PlumbingConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addPlumbing();
    print(this + " plumbing installed");
  }
}

class SlabConstructor extends Constructor {
  public SlabConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addSlab();
    print(this + " slab installed");
  }
}

class FrameConstructor extends Constructor {
  public FrameConstructor(ConstructorPool pool) { super(pool); }
  protected void performService() throws InterruptedException {
    assembler.house().addFraming();
    print(this + " frame installed");
  }
}

class ConstructorPool {
  // Quietly prevents identical entries:
  private Set<Constructor> pool = new HashSet<Constructor>();
  public synchronized void add(Constructor r) {
    pool.add(r);
    notifyAll();
  }
  public synchronized void
  hire(Class<? extends Constructor> robotType, Assembler d)
  throws InterruptedException {
    for(Constructor r : pool)
      if(r.getClass().equals(robotType)) {
        pool.remove(r);
        r.assignAssembler(d);
        r.engage(); // Power it up to do the task
        // print(r.getClass().getName() + "working");
        return;
      }
    wait(); // None available
    hire(robotType, d); // Try again, recursively
  }
  public synchronized void release(Constructor r) { add(r); }
}

class HouseBuilder {
  public static void main(String[] args) throws Exception {
    HouseQueue waitingQueue = new HouseQueue(),
             finishingQueue = new HouseQueue();
    ExecutorService exec = Executors.newCachedThreadPool();
    ConstructorPool constructorPool = new ConstructorPool();
    // need to be changed to constructors
    exec.execute(new FootingsConstructor(constructorPool));
    exec.execute(new SteelConstructor(constructorPool));
    exec.execute(new FormsConstructor(constructorPool));
    exec.execute(new FoundationConstructor(constructorPool));
    exec.execute(new PlumbingConstructor(constructorPool));
    exec.execute(new SlabConstructor(constructorPool));
    exec.execute(new FrameConstructor(constructorPool));
    exec.execute(new Assembler(
      waitingQueue, finishingQueue, constructorPool));
    exec.execute(new Reporter(finishingQueue));
    // Start everything running by producing footings:
    exec.execute(new BluePrintBuilder(waitingQueue));
    TimeUnit.SECONDS.sleep(7);
    exec.shutdownNow();
  }
}

public class ExerciseThirtyEight {
  public static void main(String[] args) throws Exception {
    HouseBuilder.main(args);
  }  
}