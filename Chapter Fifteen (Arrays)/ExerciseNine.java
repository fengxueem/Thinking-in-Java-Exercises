import java.util.*;

interface Peelable {
  void peel();
}

class Banana implements Peelable {
  private static long counter;
  private final long id = counter++;
  public String toString() { return "Banana " + id; }	
  public void peel() { System.out.println(this + " is peeled."); }
}

class Peel<T extends Peelable> {
  private T t;
  private static long counter;
  private final long id = counter++;
  public Peel(T t) { this.t = t; } 
  public void peel() { t.peel(); }
  public String toString() { return "Peel " + id + " " + t.toString(); }
}

public class ExerciseNine {
  // ignore the warning created by line 29
  @SuppressWarnings("unchecked")
  public static void main(String[] args) {
  	// error: generic array creation
  	// Peel<Banana>[] peel = new Peel<Banana>[10];
  	// "Non-generic instantiation with cast" version
  	Peel<Banana>[] peel = (Peel<Banana>[])new Peel[10];
  	for(int i = 0; i < peel.length; i++) {
  	  peel[i] = new Peel<Banana>(new Banana());
  	}
  	for(int i = 0; i < peel.length; i++) {
  	  peel[i].peel();
  	}
  	// "ArrayList version"
  	List<Peel<Banana>> peelList = new ArrayList<Peel<Banana>>();
  	for(int i = 0; i < peel.length; i++) {
  	  peelList.add(new Peel<Banana>(new Banana()));
  	}
  	for (Peel<Banana> peelBanana : peelList) {
  	  peelBanana.peel();
  	}
  }
}