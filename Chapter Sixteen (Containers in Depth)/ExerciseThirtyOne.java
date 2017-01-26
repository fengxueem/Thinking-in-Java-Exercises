import java.util.*;

class StringContainer {
  private int size = 9999;
  private String[] array = new String[size];
  private int index = 0;

  public void add(String s) {
    if (index < size) {
      array[index++] = s;
    } else {
      // resize the array
      String[] temp = new String[size * 2];
      for (int i = 0; i < size; i++) {
        temp[i] = array[i];
      }
      temp[index++] = s;
      array = temp;
      size = size * 2;
    }
  }

  public String get(int i) {
    if (i >= 0 && i <= index) {
      return array[i];
    } else {
      throw new IndexOutOfBoundsException();
    }
  }
}

interface Testable {
  public void prepare(String... parameters);
  public void test(String... parameters);
}

class StringContainerTest implements Testable {
  private Random random = new Random();
  private StringContainer container = new StringContainer();

  public void addTest(int reps) {
    for (int i = 0; i < reps; i++) {
      container.add(new Integer(random.nextInt()).toString());
    }
  }

  public void getTest(int reps) {
    for (int i = 0; i < reps; i++) {
      try {
        container.get(random.nextInt());
      } catch(Exception e) {
        continue;
      }
    }
  }

  public void prepare(String... parameters) {}

  public void test(String... parameters) {
    switch(parameters[0]) {
      case "add": addTest(Integer.decode(parameters[1])); break;
      case "get": getTest(Integer.decode(parameters[1])); break;
    }
  }
}

class ArrayListTest implements Testable {
  private Random random = new Random();
  private ArrayList<String> container = new ArrayList<String>();

  public void addTest(int reps) {
    for (int i = 0; i < reps; i++) {
      container.add(new Integer(random.nextInt()).toString());
    }
  }

  public void getTest(int reps) {
    for (int i = 0; i < reps; i++) {
      try {
        container.get(random.nextInt());
      } catch(Exception e) {
        continue;
      }
    }
  }

  public void prepare(String... parameters) {}

  public void test(String... parameters) {
    switch(parameters[0]) {
      case "add": addTest(Integer.decode(parameters[1])); break;
      case "get": getTest(Integer.decode(parameters[1])); break;
    }
  }
}

public class ExerciseThirtyOne {
  
  private static int reps = 100;
  
  private static void test(String head, int reps, Testable tesTarget, String method) {
    System.out.println("---" + head + "---(" + method + " " + reps + " words )");
    long start = System.nanoTime();
    tesTarget.test(method, intToString(reps));
    long duration = System.nanoTime() - start;
    System.out.println(duration + " ns");
  }

  private static String intToString(int i) {
    StringBuilder sb = new StringBuilder();
    sb.append("");
    sb.append(i);
    return sb.toString();
  }

  public static void main(String[] args) {
    ArrayListTest arrayTest = new ArrayListTest();
    StringContainerTest stringContainerTest = new StringContainerTest();
    test("Test of ArrayList", reps, arrayTest, "add");
    test("Test of StringContainer", reps, stringContainerTest, "add");
    test("Test of ArrayList", reps, arrayTest, "get");
    test("Test of StringContainer", reps, stringContainerTest, "get");
  } 
}