import java.util.*;
import net.mindview.util.*;

class MapEntry<K,V> implements Map.Entry<K,V>,  Comparable<MapEntry<K,V>>{
  private K key;
  private V value;
  public MapEntry(K key, V value) {
    this.key = key;
    this.value = value;
  }
  public K getKey() { return key; }
  public V getValue() { return value; }
  public V setValue(V v) {
    V result = value;
    value = v;
    return result;
  }
  public int hashCode() {
    return (key==null ? 0 : key.hashCode()) ^
      (value==null ? 0 : value.hashCode());
  }
  public boolean equals(Object o) {
    if(!(o instanceof MapEntry)) return false;
    MapEntry me = (MapEntry)o;
    return
      (key == null ?
       me.getKey() == null : key.equals(me.getKey())) &&
      (value == null ?
       me.getValue()== null : value.equals(me.getValue()));
  }
  public String toString() { return key + "=" + value; }
  public int compareTo(MapEntry<K,V> o) {
    return (getKey().hashCode() < o.getKey().hashCode()) ? -1 : 
        ((getKey().hashCode() > o.getKey().hashCode()) ? 1 : 0);
  }
}

class SlowMap<K,V> extends AbstractMap<K,V> {
  private List<MapEntry<K,V>> entries = new ArrayList<MapEntry<K,V>>();

  public V put(K key, V value) {
    // // unchanged version
    // V oldValue = get(key); // The old value or null
    // Iterator<MapEntry<K,V>> it = entries.iterator();
    // while(it.hasNext()) {
    //   MapEntry<K,V> entry = it.next();
    //   if (entry.getKey().equals(key)) {
    //     entry.setValue(value);
    //     return oldValue;
    //   }
    // }
    // entries.add(new MapEntry<K,V>(key, value));
    // return oldValue;

    // changed version
    MapEntry<K,V> keyEntry = new MapEntry<K,V>(((K)key), null);
    int i = Collections.binarySearch(entries, keyEntry);
    V oldValue = null;
    if (i >= 0) {
      oldValue = entries.get(i).getValue();
      entries.get(i).setValue(value);
    } else {
      entries.add(new MapEntry<K,V>(key, value));
      Collections.sort(entries);
    }
    return oldValue;
  }

  public V get(Object key) { // key is type Object, not K
    // // unchanged version
    // Iterator<MapEntry<K,V>> it = entries.iterator();
    // while(it.hasNext()) {
    //   MapEntry<K,V> entry = it.next();
    //   if (entry.getKey().equals(key)) {
    //     return entry.getValue();
    //   }
    // }
    // return null;

    // changed version
    MapEntry<K,V> keyEntry = new MapEntry<K,V>(((K)key), null);
    int i = Collections.binarySearch(entries, keyEntry);
    if (i >= 0) {
      return entries.get(i).getValue();
    } else {
      return null;
    }
  }

  public Set<Map.Entry<K,V>> entrySet() {
    Set<Map.Entry<K,V>> set= new HashSet<Map.Entry<K,V>>();
    set.addAll(entries);
    return set;
  }
}

class MapPerformance {
  static List<Test<Map<Integer,Integer>>> tests =
    new ArrayList<Test<Map<Integer,Integer>>>();
  static {
    tests.add(new Test<Map<Integer,Integer>>("put") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int size = tp.size;
        for(int i = 0; i < loops; i++) {
          map.clear();
          for(int j = 0; j < size; j++)
            map.put(j, j);
        }
        return loops * size;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("get") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops;
        int span = tp.size * 2;
        for(int i = 0; i < loops; i++)
          for(int j = 0; j < span; j++)
            map.get(j);
        return loops * span;
      }
    });
    tests.add(new Test<Map<Integer,Integer>>("iterate") {
      int test(Map<Integer,Integer> map, TestParam tp) {
        int loops = tp.loops * 10;
        for(int i = 0; i < loops; i ++) {
          Iterator it = map.entrySet().iterator();
          while(it.hasNext())
            it.next();
        }
        return loops * map.size();
      }
    });
  }
  public static void main(String[] args) {
    if(args.length > 0)
      Tester.defaultParams = TestParam.array(args);
    Tester.run(new SlowMap<Integer,Integer>(), tests);
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

public class ExerciseThirtySix {
  public static void main(String[] args){
    MapPerformance.main(args);    
  }  
}
// old version
// ---------- SlowMap ----------
//  size     put     get iterate
//    10    1279     183     184
//   100    2339     214    1450
// new version
// ---------- SlowMap ----------
//  size     put     get iterate
//    10     996     191     137
//   100    1487      41    1248