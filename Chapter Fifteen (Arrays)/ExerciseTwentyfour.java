import java.util.*;
import static net.mindview.util.Print.*;

class A {
  protected int x;
  A(int x) { this.x = x; }
  public boolean equals(Object b) {
  	return (b.getClass().getSimpleName() == "A" 
		&& this.x == ((A)b).x) ? true : false;
  }
  public String toString() {
  	return "A(" + x + ")";
  }
}

class AComparator implements Comparator<A> {
  public int compare(A a1, A a2) {
  	return a1.x < a2.x ? -1 : (a1.x == a2.x ? 0 : 1); 
  }
}

public class ExerciseTwentyfour {
  public static void main(String[] args) {
    A[] a1 = { new A(1), new A(2), new A(3) };
    A[] a2 = { new A(1), new A(2), new A(3) };
    print(Arrays.toString(a1));
    print(Arrays.toString(a2));
    print(Arrays.equals(a1, a2));
    Arrays.sort(a1, new AComparator());
    int index = Arrays.binarySearch(a1, new A(2), new AComparator());
    print("Index: " + index + "\n" + a1[index]);
  }
}