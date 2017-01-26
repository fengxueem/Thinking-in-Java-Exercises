import java.util.*;

interface Testable {
  public void prepare(String... parameters);
  public void test(String... parameters);
}

class SortArrayListTest implements Testable {
  private Random random = new Random();
  private ArrayList<Integer> list= new ArrayList<Integer>();

  public void setLength(int length) {
  	list.clear();
  	for (int i = 0; i < length; i++) {
  	  list.add(random.nextInt());
  	}
  }

  public void prepare(String... parameters) {
  	setLength(Integer.decode(parameters[0]));
  }

  public void test(String... parameters) {
  	Collections.sort(list);
  }
}

class SortLinkedListTest implements Testable {
  private Random random = new Random();
  private ArrayList<Integer> list= new ArrayList<Integer>();

  public void setLength(int length) {
  	list.clear();
  	for (int i = 0; i < length; i++) {
  	  list.add(random.nextInt());
  	}
  }

  public void prepare(String... parameters) {
  	setLength(Integer.decode(parameters[0]));
  }

  public void test(String... parameters) {
  	Collections.sort(list);
  }
}


public class ExerciseThirty {
  private static int reps = 100;
  
  private static void test(String head, int reps, Testable tesTarget) {
  	System.out.println("---" + head + "---");
  	for (int length = 100; length <= 10000000; length = length * 10) {
  	  System.out.print("length " + length);
  	  long time = 0l;
  	  tesTarget.prepare(intToString(length));
  	  for (int i = 0; i < reps; i++) {
  	  	long start = System.nanoTime();
  	  	tesTarget.test();
  	  	long duration = System.nanoTime() - start;
  	  	time += (duration / reps);
  	  }
  	  System.out.println(": " + time + " nanosec");
  	}
  }

  private static String intToString(int i) {
  	StringBuilder sb = new StringBuilder();
	  sb.append("");
	  sb.append(i);
	  return sb.toString();
  }

  public static void main(String[] args) {
  	SortArrayListTest arrayTest = new SortArrayListTest();
  	SortLinkedListTest linkedTest = new SortLinkedListTest();
  	test("Test for sorting ArrayList", reps, arrayTest);
  	test("Test for sorting LinkedList", reps, linkedTest);
  }	
}