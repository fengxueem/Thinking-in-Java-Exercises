// Just modify TwoTuple and ThreeTuple for examples.
import java.util.*;

class TwoTuple<A,B> implements Comparable {
  public final A first;
  public final B second;
  public TwoTuple(A a, B b) { first = a; second = b; }
  public String toString() {
    return "(" + first + ", " + second + ")";
  }
  @Override
  public int hashCode() {
    int result = 317;
    result = result * 317 + first.hashCode();
    result = result * 317 + second.hashCode();
    return result;
  }
  @Override
  public boolean equals(Object o) {
    return o.getClass().equals(getClass()) && 
      first.equals(((TwoTuple)o).first) && 
      second.equals(((TwoTuple)o).second);
  }
  public int compareTo(Object o) throws ClassCastException {
    if (o.getClass().equals(getClass())) {
      return (hashCode() < o.hashCode()) ? -1 : 
      ((hashCode() > o.hashCode()) ? 1 : 0);
    } else {
      throw new ClassCastException();
    }
  }
}

class ThreeTuple<A,B,C> extends TwoTuple<A,B> {
  public final C third;
  public ThreeTuple(A a, B b, C c) {
    super(a, b);
    third = c;
  }
  public String toString() {
    return "(" + first + ", " + second + ", " + third +")";
  }
  @Override
  public int hashCode() {
    int result = 317;
    result = result * 317 + super.hashCode();
    result = result * 317 + third.hashCode();
    return result;
  }
  @Override
  public boolean equals(Object o) {
    return o.getClass().equals(getClass()) && 
        first.equals(((ThreeTuple)o).first) && 
        second.equals(((ThreeTuple)o).second) &&
        third.equals(((ThreeTuple)o).third);
  }
  // public int compareTo(Object o) {
  //   if (o.getClass().equals(getClass())) {
  //     return (hashCode() < o.hashCode()) ? -1 : 
  //     ((hashCode() > o.hashCode()) ? 1 : 0);
  //   } else {
  //     throw new ClassCastException();
  //   }
  // }
}

class Tuple {
  public static <A,B> TwoTuple<A,B> tuple(A a, B b) {
    return new TwoTuple<A,B>(a, b);
  }
  public static <A,B,C> ThreeTuple<A,B,C> tuple(A a, B b, C c) {
    return new ThreeTuple<A,B,C>(a, b, c);
  }
}

public class ExerciseTwentyEight {
  public static void main(String[] args){
    TwoTuple<String, String> a = Tuple.tuple("hi", "there");
    TwoTuple<String, String> b = Tuple.tuple("hi", "here");
    Set<TwoTuple> set = new TreeSet<TwoTuple>();
    set.add(a);
    set.add(b);
    System.out.println(set);
    ThreeTuple<String, String, String> c = Tuple.tuple("hi", "there", "love");
    System.out.println("a equals to c: " + a.equals(c));
    try {
      System.out.println("a compares to c: " + a.compareTo(c));
    } catch(ClassCastException err) {
      System.out.println("a compares to c: c is not class of a");
    }
    try {
      System.out.println("c compares to a: " + c.compareTo(a));
    } catch(ClassCastException err) {
      System.out.println("c compares to a: c is not class of a");
    }
  }  
}