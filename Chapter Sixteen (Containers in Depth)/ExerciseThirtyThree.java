import java.util.*;
import net.mindview.util.*;

class FastTraversalLinkedList<E> {
  private ArrayList<E> aList = new ArrayList<E>();
  private LinkedList<E> lList = new LinkedList<E>();

  private void synchronizeArray() {
    aList.clear();
    aList.addAll(lList);
  }

  private void synchronizeLinked() {
    lList.clear();
    lList.addAll(aList);
  }

  public void clear() {
    aList.clear();
    lList.clear();
  }

  public void add(int i, E e) {
    lList.add(i, e);
    synchronizeArray();
  }

  public boolean addAll(Collection<? extends E> c) {
    boolean temp = lList.addAll(c);
    synchronizeArray(); 
    return temp;
  }

  public E remove(int i) {
    E temp = lList.remove(i);
    synchronizeArray();
    return temp;
  }

  public E get(int i) {
    return aList.get(i);
  }

  public int size() {
    return aList.size();
  }
}

class ListPerformance {
  static Random rand = new Random();
  static int reps = 1000;
  static List<Test<FastTraversalLinkedList<Integer>>> tests =
    new ArrayList<Test<FastTraversalLinkedList<Integer>>>();
  static { 
    tests.add(new Test<FastTraversalLinkedList<Integer>>("remove") {
      int test(FastTraversalLinkedList<Integer> list, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          list.clear();
          list.addAll(new CountingIntegerList(size));
          while(list.size() > 5)
            list.remove(5); // Minimize random-access cost
        } 
        return loops * size;    
      }
    });   
    tests.add(new Test<FastTraversalLinkedList<Integer>>("get") {
      int test(FastTraversalLinkedList<Integer> list, TestParam tp) {
        int loops = tp.loops * reps;
        int listSize = list.size();
        for(int i = 0; i < loops; i++)
          list.get(rand.nextInt(listSize));
        return loops;
      } 
    });   
    tests.add(new Test<FastTraversalLinkedList<Integer>>("insert") {
      int test(FastTraversalLinkedList<Integer> list, TestParam tp) {
        int loops = tp.loops;
        for(int i = 0; i < loops; i++)
          list.add(5, 47); // Minimize random-access cost
        return loops;
      }
    });   
  }
  static class ListTester extends Tester<FastTraversalLinkedList<Integer>> {
    public ListTester(FastTraversalLinkedList<Integer> container, 
    List<Test<FastTraversalLinkedList<Integer>>> tests) {
      super(container, tests);
    }
    public static void run(FastTraversalLinkedList<Integer> list, 
    List<Test<FastTraversalLinkedList<Integer>>> tests) {
      new ListTester(list, tests).timedTest();
    }   
  } 
  public static void main(String[] args) {
    Tester.defaultParams = TestParam.array(
      10, 5000, 100, 5000, 1000, 1000, 10000, 200);
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    ListTester.run(new FastTraversalLinkedList<Integer>(), tests);       
  }
}

public class ExerciseThirtyThree {
  public static void main(String[] args){
    ListPerformance.main(args);
  }  
}

abstract class Test<C> {
  String name;
  public Test(String name) { this.name = name; }
  // Override this method for different tests.
  // Returns actual number of repetitions of test.
  abstract int test(C container, TestParam tp);
}

class TestParam {
  public final int size;
  public final int loops;
  public TestParam(int size, int loops) {
    this.size = size;
    this.loops = loops;
  }
  // Create an array of TestParam from a varargs sequence:
  public static TestParam[] array(int... values) {
    int size = values.length/2;
    TestParam[] result = new TestParam[size];
    int n = 0;
    for(int i = 0; i < size; i++)
      result[i] = new TestParam(values[n++], values[n++]);
    return result;
  }
  // Convert a String array to a TestParam array:
  public static TestParam[] array(String[] values) {
    int[] vals = new int[values.length];
    for(int i = 0; i < vals.length; i++)
      vals[i] = Integer.decode(values[i]);
    return array(vals);
  }
}

class Tester<C> {
  public static int fieldWidth = 8;
  public static TestParam[] defaultParams= TestParam.array(
    10, 5000, 100, 5000, 1000, 5000, 10000, 500);
  // Override this to modify pre-test initialization:
  protected C initialize(int size) { return container; }
  protected C container;
  private String headline = "";
  private List<Test<C>> tests;
  private static String stringField() {
    return "%" + fieldWidth + "s";
  }
  private static String numberField() {
    return "%" + fieldWidth + "d";
  }
  private static int sizeWidth = 5;
  private static String sizeField = "%" + sizeWidth + "s";
  private TestParam[] paramList = defaultParams;
  public Tester(C container, List<Test<C>> tests) {
    this.container = container;
    this.tests = tests;
    if(container != null)
      headline = container.getClass().getSimpleName();
  }
  public Tester(C container, List<Test<C>> tests,
      TestParam[] paramList) {
    this(container, tests);
    this.paramList = paramList;
  }
  public void setHeadline(String newHeadline) {
    headline = newHeadline;
  }
  // Generic methods for convenience :
  public static <C> void run(C cntnr, List<Test<C>> tests){
    new Tester<C>(cntnr, tests).timedTest();
  }
  public static <C> void run(C cntnr,
      List<Test<C>> tests, TestParam[] paramList) {
    new Tester<C>(cntnr, tests, paramList).timedTest();
  }
  private void displayHeader() {
    // Calculate width and pad with '-':
    int width = fieldWidth * tests.size() + sizeWidth;
    int dashLength = width - headline.length() - 1;
    StringBuilder head = new StringBuilder(width);
    for(int i = 0; i < dashLength/2; i++)
      head.append('-');
    head.append(' ');
    head.append(headline);
    head.append(' ');
    for(int i = 0; i < dashLength/2; i++)
      head.append('-');
    System.out.println(head);
    // Print column headers:
    System.out.format(sizeField, "size");
    for(Test test : tests)
      System.out.format(stringField(), test.name);
    System.out.println();
  }
  // Run the tests for this container:
  public void timedTest() {
    displayHeader();
    for(TestParam param : paramList) {
      System.out.format(sizeField, param.size);
      for(Test<C> test : tests) {
        C kontainer = initialize(param.size);
        long start = System.nanoTime();
        // Call the overriden method:
        int reps = test.test(kontainer, param);
        long duration = System.nanoTime() - start;
        long timePerRep = duration / reps; // Nanoseconds
        System.out.format(numberField(), timePerRep);
      }
      System.out.println();
    }
  }
}