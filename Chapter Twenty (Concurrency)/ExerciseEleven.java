/* In order to dig a little bit deeper into 'synchorinzed'
 * We look into 2 cases here, where class A always holds
 * two data fields that are multithread accessed.
 * 1.
 * The first case is that A is a normal class with two
 * static fields accessable to others.
 * 2.
 * The second case is that A implementing Runnable with
 * two static fields accessable to others.
 * In the comparison of these 2 cases, one very confusing
 * concept floats above the surface, the difference between 'synchronized'
 * and 'synchronized static'.
 * 'synchronized': control all accesses to a class instance to be in order.
 * 'synchronized static': control all accesses to a class to be in order.
 * Thus, as a static field, every instance of this class has this field.
 * If we don't wanna mess up with it, we need to use 'synchronized static'
 * to keep it safe for multithread case.
 */
//CASE 1
//import java.util.concurrent.*;

// class A {
//   private static int i = 0;
//   private static int integer = 0;

//   public synchronized void increase() {
//   	  i++;
//   	  integer++;
//   	  i++;
//   	  integer++;
//   }
//   // Dangerous without "synchronized"
//   // public void increase() {
//   // 	i++;
//   // 	c++;
//   // 	i++;
//   // 	c++;
//   // }
//   public synchronized int getI() {
//   	return i;
//   }
//   public synchronized int getC() {
//   	return integer;
//   }
// }

// class B implements Runnable {
//   private A a;
//   public B(A a) {
//   	this.a = a;
//   }
//   public void run() {
//   	while(a.getI() % 2 == 0) {
//   	  a.increase();
//   	}
//   	System.out.println("Error! i = " + a.getI());
//   }
// }

// public class ExerciseEleven {
//   public static void main(String[] args) {
//   	A a = new A();
//   	ExecutorService service = Executors.newCachedThreadPool();
//   	for (int i = 0; i < 3; i++) {
//   	  service.execute(new B(a));
//   	}
//   	service.shutdown();
//   }
// }

//CASE 2
import java.util.concurrent.*;

class A implements Runnable {
  private static int i = 0;
  private static int integer = 0;

  public synchronized static void increase() {
  	  i++;
  	  integer++;
  	  i++;
  	  integer++;
  }
  // Dangerous without "synchronized static"
  // public void increase() {
  // 	i++;
  // 	integer++;
  // 	i++;
  // 	integer++;
  // }
  public synchronized static int getI() {
  	return i;
  }
  public synchronized static int getC() {
  	return integer;
  }
  public void run() {
  	while(getI() % 2 == 0) {
  	  increase();
  	}
  	System.out.println("Error! i = " + getI());
  }
}

public class ExerciseEleven {
  public static void main(String[] args) {
  	ExecutorService service = Executors.newCachedThreadPool();
  	for (int i = 0; i < 3; i++) {
  	  service.execute(new A());
  	}
  	service.shutdown();
  }
}