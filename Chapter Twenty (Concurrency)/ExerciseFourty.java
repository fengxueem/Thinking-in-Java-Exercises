import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

abstract class Tester<C> {
  static int testReps = 5;
  static int testCycles = 1000;
  static int containerSize = 1000;
  abstract C containerInitializer();
  abstract void startReadersAndWriters();
  C testContainer;
  String testId;
  int nReaders;
  int nWriters;
  volatile long readResult = 0;
  volatile long readTime = 0;
  volatile long writeTime = 0;
  CountDownLatch endLatch;
  static ExecutorService exec =
    Executors.newCachedThreadPool();
  Integer[] writeData;
  Tester(String testId, int nReaders, int nWriters) {
    this.testId = testId + " " +
      nReaders + "r " + nWriters + "w";
    this.nReaders = nReaders;
    this.nWriters = nWriters;
    writeData = Generated.array(Integer.class,
      new RandomGenerator.Integer(), containerSize);
    for(int i = 0; i < testReps; i++) {
      runTest();
      readTime = 0;
      writeTime = 0;
    }
  }
  void runTest() {
    endLatch = new CountDownLatch(nReaders + nWriters);
    testContainer = containerInitializer();
    startReadersAndWriters();
    try {
      endLatch.await();
    } catch(InterruptedException ex) {
      System.out.println("endLatch interrupted");
    }
    System.out.printf("%-27s %14d %14d\n",
      testId, readTime, writeTime);
    if(readTime != 0 && writeTime != 0)
      System.out.printf("%-27s %14d\n",
        "readTime + writeTime =", readTime + writeTime);
  }
  abstract class TestTask implements Runnable {
    abstract void test();
    abstract void putResults();
    long duration;
    public void run() {
      long startTime = System.nanoTime();
      test();
      duration = System.nanoTime() - startTime;
      synchronized(Tester.this) {
        putResults();
      }
      endLatch.countDown();
    }
  }
  public static void initMain(String[] args) {
    if(args.length > 0)
      testReps = new Integer(args[0]);
    if(args.length > 1)
      testCycles = new Integer(args[1]);
    if(args.length > 2)
      containerSize = new Integer(args[2]);
    System.out.printf("%-27s %14s %14s\n",
      "Type", "Read time", "Write time");
  }
}

abstract class MapTest
extends Tester<Map<Integer,Integer>> {
  MapTest(String testId, int nReaders, int nWriters) {
    super(testId, nReaders, nWriters);
  }
  class Reader extends TestTask {
    long result = 0;
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          result += testContainer.get(index);
    }
    void putResults() {
      readResult += result;
      readTime += duration;
    }
  }
  class Writer extends TestTask {
    void test() {
      for(long i = 0; i < testCycles; i++)
        for(int index = 0; index < containerSize; index++)
          testContainer.put(index, writeData[index]);
    }
    void putResults() {
      writeTime += duration;
    }
  }
  void startReadersAndWriters() {
    for(int i = 0; i < nReaders; i++)
      exec.execute(new Reader());
    for(int i = 0; i < nWriters; i++)
      exec.execute(new Writer());
  }
}

class ReaderWriterMap<T,U> extends AbstractMap<T,U> {
  // private HashMap<T,U> lockedMap;
  // Make the ordering fair:
  private HashMap<T,U> lockedMap;
  private ReentrantReadWriteLock lock =
      new ReentrantReadWriteLock();
  private Lock 
      wlock = lock.writeLock(),
      rlock = lock.readLock();
  public ReaderWriterMap(Generator<T> genT, Generator<U> genU,
      int quantity) {
    lockedMap = new HashMap<T,U>(new MapData<T,U>(genT, genU, quantity));
  }
  public U put(T key, U element) {
    wlock.lock();
    try {
      return lockedMap.put(key, element);
    } finally {
      wlock.unlock();
    }
  }
  public U get(Object key) {
    rlock.lock();
    try {
      // Show that multiple readers
      // may acquire the read lock:
      // if(lock.getReadLockCount() > 1)
        // print(lock.getReadLockCount());
      return lockedMap.get(key);
    } finally {
      rlock.unlock();
    }
  }
  public Set<Map.Entry<T, U>> entrySet() {
    // we don't need a entrySet over here.
    // just leave it alone.
    return null;
  }
}

class ReaderWriterMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return new ReaderWriterMap<Integer,Integer>(
        new CountingGenerator.Integer(),
        new CountingGenerator.Integer(), containerSize);
  }
  ReaderWriterMapTest(int nReaders, int nWriters) {
    super("ReaderWriterMap", nReaders, nWriters);
  }
}

class SynchronizedHashMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return Collections.synchronizedMap(
      new HashMap<Integer,Integer>(
        MapData.map(
          new CountingGenerator.Integer(),
          new CountingGenerator.Integer(),
          containerSize)));
  }
  SynchronizedHashMapTest(int nReaders, int nWriters) {
    super("Synched HashMap", nReaders, nWriters);
  }
}

class ConcurrentHashMapTest extends MapTest {
  Map<Integer,Integer> containerInitializer() {
    return new ConcurrentHashMap<Integer,Integer>(
      MapData.map(
        new CountingGenerator.Integer(),
        new CountingGenerator.Integer(), containerSize));
  }
  ConcurrentHashMapTest(int nReaders, int nWriters) {
    super("ConcurrentHashMap", nReaders, nWriters);
  }
}

public class ExerciseFourty {
  public static void main(String[] args) throws Exception {
    Tester.initMain(args);
    new SynchronizedHashMapTest(10, 0);
    new SynchronizedHashMapTest(9, 1);
    new SynchronizedHashMapTest(5, 5);
    new ConcurrentHashMapTest(10, 0);
    new ConcurrentHashMapTest(9, 1);
    new ConcurrentHashMapTest(5, 5);
    new ReaderWriterMapTest(10, 0);
    new ReaderWriterMapTest(9, 1);
    new ReaderWriterMapTest(5, 5);
    Tester.exec.shutdown();
    // ReaderWriterMap is way too slow in this comparison!
  }  
}
