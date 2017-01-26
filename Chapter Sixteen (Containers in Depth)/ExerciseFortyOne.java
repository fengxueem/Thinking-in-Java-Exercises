import java.util.*;
import net.mindview.util.*;

class TwoStrings implements Comparable<TwoStrings> {
  private String first;
  private String second;

  public TwoStrings(String first, String second) {
  	this.first = first;
  	this.second = second;
  }

  public int compareTo(TwoStrings other) {
  	return first.compareTo(other.first);
  }

  @Override
  public String toString() {
  	return "(" + first + "," + second + ")";
  }

  // In order to work with HashSet, override hashCode() and equals()
  @Override
  public int hashCode() {
  	return first.hashCode() * 37 + second.hashCode();
  }

  @Override
  public boolean equals(Object other) {
  	if (getClass().equals(other.getClass())) {
  	  return ((TwoStrings)other).first.equals(first) &&
  	     ((TwoStrings)other).second.equals(second);
  	} else {
  	  throw new ClassCastException();
  	}
  }

  public static class TwoStringsComparator implements Comparator<TwoStrings> {
  	public int compare(TwoStrings one, TwoStrings two) {
  	  return one.second.compareTo(two.second);
  	}
  }
}

public class ExerciseFortyOne {

  public static void printArray(Object[] array) {
  	System.out.print("[");
  	for (int i = 0; i < array.length -1; i++) {
  	  System.out.print(array[i] + ", ");
  	}
  	System.out.println(array[array.length -1] + "]");
  }

  public static void main(String[] args) {
  	RandomGenerator.String random = new RandomGenerator.String(4);
	HashSet<TwoStrings> set = new HashSet<TwoStrings>();
	HashMap<TwoStrings, Integer> map = new HashMap<TwoStrings, Integer>();
	for (int i = 0; i < 10; i++) {
	  String a = random.next();
	  String b = random.next();
	  set.add(new TwoStrings(a, b));
	  map.put(new TwoStrings(a, b), i);
	}
	System.out.println("Set: " + set);
	System.out.println("Map: " + map);
	System.out.println("Map.keySet: " + map.keySet());
	TwoStrings temp = set.iterator().next();
	boolean b = map.keySet().contains(temp);
	System.out.println("Map contains key " + temp + "? " + b);
  }	
}