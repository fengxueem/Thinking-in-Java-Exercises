import java.lang.reflect.*;

class Toy {
  // Comment out the following default constructor
  // to see NoSuchMethodError from (*1*)
  Toy() {}
  Toy(int i) {}
}

class ToyTest {
  static void printInfo(Class cc) {
    System.out.println("Class name: " + cc.getName() +
      " is interface? [" + cc.isInterface() + "]");
    System.out.println("Simple name: " + cc.getSimpleName());
    System.out.println("Canonical name : " + cc.getCanonicalName());
  }
  public static void main(String[] args) {
    try {
	  Toy t = Toy.class.getDeclaredConstructor(int.class).newInstance(1);
	  printInfo(t.getClass());
	  // catch four exceptions:
	} catch(NoSuchMethodException e) {
	    System.out.println("No such method: " + e);
	} catch(InstantiationException e) {
		System.out.println("Unable make Toy: " + e);
	} catch(IllegalAccessException e) {
		System.out.println("Unable access: " + e);
	} catch(InvocationTargetException e) {
		System.out.println("Target invocation problem: " + e);
	}
  }
}

public class ExerciseNineteen {
  public static void main(String[] args){
  	ToyTest.main(args);
  }	
}