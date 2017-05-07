import java.util.concurrent.*;
import static net.mindview.util.Print.*;

class Meal {
  private final int orderNum;
  public Meal(int orderNum) { this.orderNum = orderNum; }
  public String toString() { return "Meal " + orderNum; }
}

class WaitPerson implements Runnable {
  private Restaurant restaurant;
  public WaitPerson(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          while(restaurant.meal == null)
            wait(); // ... for the chef to produce a meal
        }
        print("Waitperson got " + restaurant.meal);
        synchronized(restaurant.chef) {
          restaurant.meal = null;
          restaurant.chef.notifyAll(); // Ready for another
        }
        synchronized(restaurant.busBoy) {
          restaurant.busBoy.dishToClean = true;
          restaurant.busBoy.notify(); // Ready for another
        }
      }
    } catch(InterruptedException e) {
      print("WaitPerson interrupted");
    }
  }
}

class Chef implements Runnable {
  private Restaurant restaurant;
  private int count = 0;
  public Chef(Restaurant r) { restaurant = r; }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          while(restaurant.meal != null)
            wait(); // ... for the meal to be taken
        }
        if(++count == 10) {
          print("Out of food, closing");
          restaurant.exec.shutdownNow();
        }
        printnb("Order up! ");
        synchronized(restaurant.waitPerson) {
          restaurant.meal = new Meal(count);
          restaurant.waitPerson.notifyAll();
        }
        TimeUnit.MILLISECONDS.sleep(100);
      }
    } catch(InterruptedException e) {
      print("Chef interrupted");
    }
  }
}

class BusBoy implements Runnable {
  private Restaurant restaurant;
  public BusBoy(Restaurant r) { restaurant = r; }
  protected boolean dishToClean = false;
  public void run() {
    try {
      while(!Thread.interrupted()) {
        synchronized(this) {
          while (!dishToClean){
            wait();
          }
        }
        print("BusBoy comes to clean.");
        dishToClean = false;
      }
    } catch(InterruptedException e) {
      print("Chef interrupted");
    }
  }
}

class Restaurant {
  Meal meal;
  ExecutorService exec = Executors.newCachedThreadPool();
  WaitPerson waitPerson = new WaitPerson(this);
  Chef chef = new Chef(this);
  BusBoy busBoy = new BusBoy(this);
  public Restaurant() {
    exec.execute(chef);
    exec.execute(waitPerson);
    exec.execute(busBoy);
  }
  public static void main(String[] args) {
    new Restaurant();
  }
}

public class ExerciseTwentySix {
  public static void main(String[] args){
    Restaurant.main(args);    
  }  
}