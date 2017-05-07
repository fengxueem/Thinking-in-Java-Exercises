import enumerated.menu.*;
import java.util.concurrent.*;
import java.util.*;
import static net.mindview.util.Print.*;

// This is given to the waiter, who gives it to the chef:
class Order { // (A data-transfer object)
  private static int counter = 0;
  private final int id = counter++;
  private final Customer customer;
  private final Food food;
  public Order(Customer cust, Food f) {
    customer = cust;
    food = f;
  }
  public Food item() { return food; }
  public Customer getCustomer() { return customer; }
  public String toString() {
    return "Order: " + id + " item: " + food +
      " for: " + customer;
  }
}

class OrderTicket {
  private List<Order> orders;
  private WaitPerson waitPerson;
  public OrderTicket(List<Order> orders, WaitPerson waitPerson) {
    this.orders = orders;
    this.waitPerson = waitPerson;
  }
  public WaitPerson getWaitPerson() {
    return waitPerson;
  }
  public List<Order> getOrders() {
    return orders;
  }
}

// This is what comes back from the chef:
class Plate {
  private final Customer customer;
  private final Food food;
  public Plate(Customer customer, Food food) {
    this.customer = customer;
    this.food = food;
  }
  public Customer getCustomer() { return customer; }
  public Food getFood() { return food; }
  public String toString() { return food.toString(); }
}

class Customer implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private final WaitPerson waitPerson;
  private final Table table;
  // Only one course at a time can be received:
  private SynchronousQueue<Plate> placeSetting =
    new SynchronousQueue<Plate>();
  public Customer(WaitPerson waitPerson, Table table) { 
    this.waitPerson = waitPerson;
    this.table = table; 
  }
  public void
  deliver(Plate p) throws InterruptedException {
    // Only blocks if customer is still
    // eating the previous course:
    placeSetting.put(p);
  }
  public void run() {
    for(Course course : Course.values()) {
      try {
        // Blocks until course has been delivered:
        print(this + "eating " + placeSetting.take());
      } catch(InterruptedException e) {
        print(this + "waiting for " +
          course + " interrupted");
        break;
      }
    }
    print(this + "finished meal");
    // remove from the customer queue in this table
    table.removeCustomer(this);
    // if this is the last customer, notify to clean table
    synchronized(table) {
      if (table.customers.isEmpty()) {
        table.setOccupied(false);
        table.notify();
      }
    }
  }
  public String toString() {
    return "Customer " + id + " ";
  }
}

class Table implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private OrderTicket orderTicket;
  private WaitPerson waitPerson;
  BlockingQueue<Customer> customers =
    new LinkedBlockingQueue<Customer>();
  private volatile boolean occupied = false;
  private ExecutorService exec;
  public Table(ExecutorService exec) {
    this.exec = exec;
  }
  public void setWaitPerson(WaitPerson waitPerson) {
    this.waitPerson = waitPerson;
  }
  public void setCustomers(List<Customer> customers) {
    for (Customer c : customers) {
      this.customers.add(c);
    }
  }
  public synchronized void setOccupied(boolean occupied) {
    this.occupied = occupied;
  }
  public synchronized boolean isOccupied() {
    return occupied;
  }
  public void removeCustomer(Customer c) {
    try {
      customers.remove(c);
    } catch(Exception e) {}
  }
  public void run() {
    try {
      for (Customer customer : customers) {
        exec.execute(customer);
        // customers order foods into an order ticket
        ArrayList<Order> orders = new ArrayList<Order>();
        for(Course course : Course.values()) {
          orders.add(new Order(customer, course.randomSelection()));
        }
        // waitPerson take over the order ticket
        orderTicket = new OrderTicket(orders, waitPerson);
        waitPerson.placeOrderTicket(orderTicket);
      }
      // wait customers to finish their foods
      synchronized(this) {
        while(occupied) {
          this.wait();
        }
      }
      // clean table
      print(this + " is cleaned up");
    } catch(InterruptedException e) {
      print(this + " interrupted");
    }
  }
  public String toString() {
    return "Table " + id + " ";
  }
}

class WaitPerson implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private final Restaurant restaurant;
  BlockingQueue<Plate> filledOrders =
    new LinkedBlockingQueue<Plate>();
  public WaitPerson(Restaurant rest) { restaurant = rest; }
  public void placeOrderTicket(OrderTicket orderTicket) throws InterruptedException {
    restaurant.orderTickets.put(orderTicket);
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // Blocks until a course is ready
        Plate plate = filledOrders.take();
        print(this + "received " + plate +
          " delivering to " +
          plate.getCustomer());
        plate.getCustomer().deliver(plate);
      }
    } catch(InterruptedException e) {
      print(this + " interrupted");
    }
    print(this + " off duty");
  }
  public String toString() {
    return "WaitPerson " + id + " ";
  }
}

class Chef implements Runnable {
  private static int counter = 0;
  private final int id = counter++;
  private final Restaurant restaurant;
  private static Random rand = new Random(47);
  public Chef(Restaurant rest) { restaurant = rest; }
  public void run() {
    // A chef handles a whole list of order ticket
    try {
      while(!Thread.interrupted()) {
        // Blocks until an orderTicket appears:
        OrderTicket orderTicket = restaurant.orderTickets.take();
        WaitPerson waitPerson = orderTicket.getWaitPerson();
        // Start cooking order by order
        for (Order order : orderTicket.getOrders()) {
          Food requestedItem = order.item();
          // Time to prepare order:
          TimeUnit.MILLISECONDS.sleep(rand.nextInt(500));
          Plate plate = new Plate(order.getCustomer(), requestedItem);
          waitPerson.filledOrders.put(plate);
        }
      }
    } catch(InterruptedException e) {
      print(this + " interrupted");
    }
    print(this + " off duty");
  }
  public String toString() { return "Chef " + id + " "; }
}

class Restaurant implements Runnable {
  private List<WaitPerson> waitPersons =
    new ArrayList<WaitPerson>();
  private List<Chef> chefs = new ArrayList<Chef>();
  private List<Table> tables = new ArrayList<Table>();
  private ExecutorService exec;
  private static Random rand = new Random(47);
  BlockingQueue<OrderTicket>
    orderTickets = new LinkedBlockingQueue<OrderTicket>();
  public Restaurant(ExecutorService e, int nWaitPersons,
    int nChefs, int nTables) {
    exec = e;
    for(int i = 0; i < nWaitPersons; i++) {
      WaitPerson waitPerson = new WaitPerson(this);
      waitPersons.add(waitPerson);
      exec.execute(waitPerson);
    }
    for(int i = 0; i < nChefs; i++) {
      Chef chef = new Chef(this);
      chefs.add(chef);
      exec.execute(chef);
    }
    // create tables, but don't run them for now
    for(int i = 0; i < nTables; i++) {
      Table table = new Table(exec);
      tables.add(table);
    }
  }
  public void run() {
    try {
      while(!Thread.interrupted()) {
        // A group of new customers arrive; assign a WaitPerson & an empty table
        WaitPerson wp = waitPersons.get(
            rand.nextInt(waitPersons.size()));
        Table t;
        do {
          t = tables.get(
            rand.nextInt(tables.size()));
        } while (t.isOccupied());
        t.setWaitPerson(wp);
        t.setCustomers(Arrays.asList(new Customer(wp, t), new Customer(wp, t)));
        t.setOccupied(true);
        exec.execute(t);
        TimeUnit.MILLISECONDS.sleep(100);
      }
    } catch(InterruptedException e) {
      print("Restaurant interrupted");
    } catch(RejectedExecutionException r){
      print("Restaurant interrupted");
    }
    print("Restaurant closing");
  }
}

class RestaurantWithQueues {
  public static void main(String[] args) throws Exception {
    ExecutorService exec = Executors.newCachedThreadPool();
    Restaurant restaurant = new Restaurant(exec, 5, 2, 2);
    exec.execute(restaurant);
    if(args.length > 0) // Optional argument
      TimeUnit.SECONDS.sleep(new Integer(args[0]));
    else {
      print("Press 'Enter' to quit");
      System.in.read();
    }
    exec.shutdownNow();
  }
}

public class ExerciseThirtySix {
  public static void main(String[] args) throws Exception {
    RestaurantWithQueues.main(args);
  }  
}