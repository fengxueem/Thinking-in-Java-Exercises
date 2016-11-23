import java.util.*;

// Different types of function objects:
interface Combiner<T> { T combine(T x, T y); }
interface UnaryFunction<R,T> { R function(T x); }
interface Collector<T> extends UnaryFunction<T,T> {
  T result(); // Extract result of collecting parameter
}
interface UnaryPredicate<T> { boolean test(T x); }

class ClassA {
  String name;
  ClassA(String name) { this.name = name; }
  String getName() { return name; }
  String reverse() { return new StringBuilder(name).reverse().toString(); }
}

class ClassB {
  private static long counter = 0;
  private final long id = counter++;
  long getId() { return id; }
  long truncate() {return id % 1000; }
}

class Functional {
  // Calls the Combiner object on each element to combine
  // it with a running result, which is finally returned:
  public static <T> T
  reduce(Iterable<T> seq, Combiner<T> combiner) {
    Iterator<T> it = seq.iterator();
    if(it.hasNext()) {
      T result = it.next();
      while(it.hasNext())
        result = combiner.combine(result, it.next());
      return result;
    }
    // If seq is the empty list:
    return null; // Or throw exception
  }
  // Take a function object and call it on each object in
  // the list, ignoring the return value. The function
  // object may act as a collecting parameter, so it is
  // returned at the end.
  public static <T> Collector<T>
  forEach(Iterable<T> seq, Collector<T> func) {
    for(T t : seq)
      func.function(t);
    return func;
  }
  // To use the above generic methods, we need to create
  // function objects to adapt to our particular needs:
  static class ClassACombiner implements Combiner<ClassA> {
    public ClassA combine(ClassA x, ClassA y) {
      return new ClassA(x.getName() + ", " + y.getName());
    }
  }
  static class ClassBCollector implements Collector<ClassB> {
    private ClassB val;
    public ClassB function(ClassB x) {
      val = new ClassB();
      return val;
    }
    public ClassB result() { return val; }
  }
  public static void main(String[] args) {
  	List<ClassA> listA = new ArrayList(Arrays.asList(
  		new ClassA("xixi"), new ClassA("haha"), new ClassA("hoho")));
  	ClassA greeting = reduce(listA, new ClassACombiner());
  	System.out.println(greeting.getName());

  	List<ClassB> listB = new ArrayList(Arrays.asList(
  		new ClassB(), new ClassB(), new ClassB()));
  	Collector<ClassB> collector = forEach(listB, new ClassBCollector());
  	System.out.println(collector.result().getId());
  }
}

public class ExerciseFourtytwo {
  public static void main(String[] args) {
  	Functional.main(args);
  }
}