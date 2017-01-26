import java.util.*;
import net.mindview.util.*;

class TwoString implements Comparable<TwoString> {
  private String first;
  private String second;

  public TwoString(String first, String second) {
  	this.first = first;
  	this.second = second;
  }

  public int compareTo(TwoString other) {
  	return first.compareTo(other.first);
  }

  public String toString() {
  	return "(" + first + "," + second + ")";
  }

  public static class TwoStringComparator implements Comparator<TwoString> {
  	public int compare(TwoString one, TwoString two) {
  	  return one.second.compareTo(two.second);
  	}
  }
}



public class ExerciseForty {

  public static void printArray(Object[] array) {
  	System.out.print("[");
  	for (int i = 0; i < array.length -1; i++) {
  	  System.out.print(array[i] + ", ");
  	}
  	System.out.println(array[array.length -1] + "]");
  }

  public static void main(String[] args) {
  	RandomGenerator.String random = new RandomGenerator.String(4);
	TwoString[] array = new TwoString[10];
	ArrayList<TwoString> list = new ArrayList<TwoString>();
	for (int i = 0; i < array.length; i++) {
	  String a = random.next();
	  String b = random.next();
	  array[i] = new TwoString(a, b);
	  list.add(new TwoString(a, b));
	}
	System.out.println("------natural ordering -----");
	System.out.println("Before Sorting:(ArrayList) " + list);
	Collections.sort(list);
	System.out.println("After Sorting:(ArrayList) " + list);
	System.out.print("Before Sorting:(array) ");
	printArray(array);
	Arrays.sort(array);
	System.out.print("After Sorting:(array) ");
	printArray(array);
	System.out.println("------Outside comparator-----");
	System.out.println("Before Sorting:(ArrayList) " + list);
	Collections.sort(list, new TwoString.TwoStringComparator());
	System.out.println("After Sorting:(ArrayList) " + list);
	System.out.print("Before Sorting:(array) ");
	printArray(array);
	Arrays.sort(array, new TwoString.TwoStringComparator());
	System.out.print("After Sorting:(array) ");
	printArray(array);
	System.out.println("------Binary search-----");
	int i = Collections.binarySearch(list, list.get(1), new TwoString.TwoStringComparator());
	System.out.println("[--Target---vs---Origin--]");
	System.out.println(list.get(i) + " vs " + list.get(1));
  }	
}