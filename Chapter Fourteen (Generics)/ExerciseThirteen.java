import generics.coffee.*;
import java.util.*;
import net.mindview.util.*;

class Fibonacci implements Generator<Integer> {
  private int count = 0;
  public Integer next() { return fib(count++); }
  private int fib(int n) {
    if(n < 2) return 1;
    return fib(n-2) + fib(n-1);
  }
}

class Generators {
  public static <T> Collection<T>
  fill(Collection<T> coll, Generator<T> gen, int n) {
    System.out.println("\nCollection fill");
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
  public static <T> List<T>
  fill(List<T> coll, Generator<T> gen, int n) {
    System.out.println("\nList fill");
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
  public static <T> LinkedList<T>
  fill(LinkedList<T> coll, Generator<T> gen, int n) {
    System.out.println("\nLinkedList fill");
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
  public static <T> Set<T>
  fill(Set<T> coll, Generator<T> gen, int n) {
    System.out.println("\nSet fill");
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
  public static <T> Queue<T>
  fill(Queue<T> coll, Generator<T> gen, int n) {
    System.out.println("\nQueue fill");
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
  public static void main(String[] args) {
    Collection<Coffee> coffee = fill(
      new ArrayList<Coffee>(), new CoffeeGenerator(), 4);
    for(Coffee c : coffee)
      System.out.println(c);
    List<Integer> fnumbers = fill(
      new ArrayList<Integer>(), new Fibonacci(), 12);
    for(int i : fnumbers)
      System.out.print(i + ", ");
    LinkedList<Integer> fnumbersLinked = fill(
      new LinkedList<Integer>(), new Fibonacci(), 12);
    for(int i : fnumbersLinked)
      System.out.print(i + ", ");
    Set<Integer> fnumbersSet = fill(
      new HashSet<Integer>(), new Fibonacci(), 12);
    for(int i : fnumbersSet)
      System.out.print(i + ", ");
    Queue<Integer> fnumbersQueue = fill(
      new PriorityQueue<Integer>(), new Fibonacci(), 12);
    for(int i : fnumbersQueue)
      System.out.print(i + ", ");
  }
}

public class ExerciseThirteen {
  public static void main(String[] args){
    Generators.main(args);    
  }  
}