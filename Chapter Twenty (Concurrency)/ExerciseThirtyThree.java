import java.util.concurrent.*;
import java.util.*;

import static java.util.concurrent.TimeUnit.*;
import static net.mindview.util.Print.*;

class GreenhouseScheduler {
  private volatile boolean light = false;
  private volatile boolean water = false;
  private String thermostat = "Day";
  private DelayQueue<DelayedTask> queue;
  public GreenhouseScheduler(DelayQueue<DelayedTask> queue) {
    this.queue = queue;
  }
  public synchronized String getThermostat() {
    return thermostat;
  }
  public synchronized void setThermostat(String value) {
    thermostat = value;
  }

  // ScheduledThreadPoolExecutor scheduler =
  //   new ScheduledThreadPoolExecutor(10);
  // public void schedule(Runnable event, long delay) {
  //   scheduler.schedule(event,delay,TimeUnit.MILLISECONDS);
  // }
  // public void
  // repeat(Runnable event, long initialDelay, long period) {
  //   scheduler.scheduleAtFixedRate(
  //     event, initialDelay, period, TimeUnit.MILLISECONDS);
  // }
  public void schedule(DelayedTask event) {
    queue.put(event);
  }
  public void repeat(DelayedTask event, long period) {
    new Thread() {
      {
        setDaemon(true);
      }
      
      public void run() {
        while (true){
          queue.put(event);
          try {
            TimeUnit.MILLISECONDS.sleep(period);
          } catch(InterruptedException e) {
            // Empty Exception Handler
          }
        }
      }
    }.start();
  }
  class LightOn extends DelayedTask {
    public LightOn(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here to
      // physically turn on the light.
      System.out.println("Turning on lights");
      light = true;
    }
  }
  class LightOff extends DelayedTask {
    public LightOff(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here to
      // physically turn off the light.
      System.out.println("Turning off lights");
      light = false;
    }
  }
  class WaterOn extends DelayedTask {
    public WaterOn(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here.
      System.out.println("Turning greenhouse water on");
      water = true;
    }
  }
  class WaterOff extends DelayedTask {
    public WaterOff(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here.
      System.out.println("Turning greenhouse water off");
      water = false;
    }
  }
  class ThermostatNight extends DelayedTask {
    public ThermostatNight(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here.
      System.out.println("Thermostat to night setting");
      setThermostat("Night");
    }
  }
  class ThermostatDay extends DelayedTask {
    public ThermostatDay(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      // Put hardware control code here.
      System.out.println("Thermostat to day setting");
      setThermostat("Day");
    }
  }
  class Bell extends DelayedTask {
    public Bell(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() { System.out.println("Bing!"); }
  }
  class Terminate extends DelayedTask {
    private ExecutorService exec;
    public Terminate(int delay, ExecutorService e) {
      super(delay);
      exec = e;
    }
    public void run() {
      // for(DelayedTask pt : sequence) {
      //   printnb(pt.summary() + " ");
      // }
      // print();
      print(this + " Calling shutdownNow()");
      exec.shutdownNow();
      new Thread() {
        public void run() {
          for(DataPoint d : data)
            System.out.println(d);
        }
      }.start();
    }
  }
  // New feature: data collection
  static class DataPoint {
    final Calendar time;
    final float temperature;
    final float humidity;
    public DataPoint(Calendar d, float temp, float hum) {
      time = d;
      temperature = temp;
      humidity = hum;
    }
    public String toString() {
      return time.getTime() +
        String.format(
          " temperature: %1$.1f humidity: %2$.2f",
          temperature, humidity);
    }
  }
  private Calendar lastTime = Calendar.getInstance();
  { // Adjust date to the half hour
    lastTime.set(Calendar.MINUTE, 30);
    lastTime.set(Calendar.SECOND, 00);
  }
  private float lastTemp = 65.0f;
  private int tempDirection = +1;
  private float lastHumidity = 50.0f;
  private int humidityDirection = +1;
  private Random rand = new Random(47);
  List<DataPoint> data = Collections.synchronizedList(
    new ArrayList<DataPoint>());
  class CollectData extends DelayedTask {
    public CollectData(int delayInMilliseconds) {
      super(delayInMilliseconds);
    }
    public void run() {
      System.out.println("Collecting data");
      synchronized(GreenhouseScheduler.this) {
        // Pretend the interval is longer than it is:
        lastTime.set(Calendar.MINUTE,
          lastTime.get(Calendar.MINUTE) + 30);
        // One in 5 chances of reversing the direction:
        if(rand.nextInt(5) == 4)
          tempDirection = -tempDirection;
        // Store previous value:
        lastTemp = lastTemp +
          tempDirection * (1.0f + rand.nextFloat());
        if(rand.nextInt(5) == 4)
          humidityDirection = -humidityDirection;
        lastHumidity = lastHumidity +
          humidityDirection * rand.nextFloat();
        // Calendar must be cloned, otherwise all
        // DataPoints hold references to the same lastTime.
        // For a basic object like Calendar, clone() is OK.
        data.add(new DataPoint((Calendar)lastTime.clone(),
          lastTemp, lastHumidity));
      }
    }
  }
  // public static void main(String[] args) {
  //   GreenhouseScheduler gh = new GreenhouseScheduler();
  //   gh.schedule(gh.new Terminate(), 5000);
  //   // Former "Restart" class not necessary:
  //   gh.repeat(gh.new Bell(), 0, 1000);
  //   gh.repeat(gh.new ThermostatNight(), 0, 2000);
  //   gh.repeat(gh.new LightOn(), 0, 200);
  //   gh.repeat(gh.new LightOff(), 0, 400);
  //   gh.repeat(gh.new WaterOn(), 0, 600);
  //   gh.repeat(gh.new WaterOff(), 0, 800);
  //   gh.repeat(gh.new ThermostatDay(), 0, 1400);
  //   gh.repeat(gh.new CollectData(), 500, 500);
  // }
}

class DelayedTaskConsumer implements Runnable {
  private DelayQueue<DelayedTask> q;
  public DelayedTaskConsumer(DelayQueue<DelayedTask> q) {
    this.q = q;
  }
  public void run() {
    try {
      while(!Thread.interrupted())
        q.take().run(); // Run task with the current thread
    } catch(InterruptedException e) {
      // Acceptable way to exit
    }
    print("Finished DelayedTaskConsumer");
  }
}

class DelayedTask implements Runnable, Delayed {
  private static int counter = 0;
  private final int id = counter++;
  private final int delta;
  private final long trigger;
  // protected static List<DelayedTask> sequence =
  //   new ArrayList<DelayedTask>();
  public DelayedTask(int delayInMilliseconds) {
    delta = delayInMilliseconds;
    trigger = System.nanoTime() +
      NANOSECONDS.convert(delta, MILLISECONDS);
    // sequence.add(this);
  }
  public long getDelay(TimeUnit unit) {
    return unit.convert(
      trigger - System.nanoTime(), NANOSECONDS);
  }
  public int compareTo(Delayed arg) {
    DelayedTask that = (DelayedTask)arg;
    if(trigger < that.trigger) return -1;
    if(trigger > that.trigger) return 1;
    return 0;
  }
  public void run() { printnb(this + " "); }
  public String toString() {
    return String.format("[%1$-4d]", delta) +
      " Task " + id;
  }
  public String summary() {
    return "(" + id + ":" + delta + ")";
  }
  public static class EndSentinel extends DelayedTask {
    private ExecutorService exec;
    public EndSentinel(int delay, ExecutorService e) {
      super(delay);
      exec = e;
    }
    public void run() {
      // for(DelayedTask pt : sequence) {
      //   printnb(pt.summary() + " ");
      // }
      // print();
      print(this + " Calling shutdownNow()");
      exec.shutdownNow();
    }
  }
}

class DelayQueueDemo {
  public static void main(String[] args) {
    ExecutorService exec = Executors.newCachedThreadPool();
    DelayQueue<DelayedTask> queue =
      new DelayQueue<DelayedTask>();
    GreenhouseScheduler gh = new GreenhouseScheduler(queue);
    // Former "Restart" class not necessary:
    // gh.schedule(gh.new Bell(0));
    // gh.schedule(gh.new ThermostatNight(0));
    // gh.schedule(gh.new LightOn(0));
    // gh.schedule(gh.new LightOff(0));
    // gh.schedule(gh.new WaterOn(0));
    // gh.schedule(gh.new WaterOff(0));
    // gh.schedule(gh.new ThermostatDay(0));
    // gh.schedule(gh.new CollectData(500));
    gh.repeat(gh.new Bell(0), 1000);
    gh.repeat(gh.new ThermostatNight(0), 2000);
    gh.repeat(gh.new LightOn(0), 200);
    gh.repeat(gh.new LightOff(0), 400);
    gh.repeat(gh.new WaterOn(0), 600);
    gh.repeat(gh.new WaterOff(0), 800);
    gh.repeat(gh.new ThermostatDay(0), 1400);
    gh.repeat(gh.new CollectData(500), 500);
    // Set the stopping point
    gh.schedule(gh.new Terminate(5000, exec));
    exec.execute(new DelayedTaskConsumer(queue));
  }
}

public class ExerciseThirtyThree {
  public static void main(String[] args) {
    DelayQueueDemo.main(args);
  }  
}