class TwoTuple<A,B> {
  final A a;
  final B b;
  TwoTuple(A a, B b) {
  	this.a = a;
  	this.b = b;
  }
  public String toString() {
  	return "(" + a + "+" + b + ")";
  }
}

class ThreeTuple<A,B,C> extends TwoTuple<A,B> {
  final C c;
  ThreeTuple(A a, B b, C c) {
	super(a, b);
	this.c = c;
  }
  public String toString() {
  	return "(" + a + "+" + b + "+" + c + ")";
  }
}

class FourTuple<A,B,C,D> extends ThreeTuple<A,B,C> {
  final D d;
  FourTuple(A a, B b, C c, D d) {
	super(a, b, c);
	this.d = d;
  }
  public String toString() {
  	return "(" + a + "+" + b + "+" + c + "+" + d + ")";
  }
}

class FiveTuple<A,B,C,D,E> extends FourTuple<A,B,C,D> {
  final E e;
  FiveTuple(A a, B b, C c, D d, E e) {
	super(a, b, c, d);
	this.e = e;
  }
  public String toString() {
  	return "(" + a + "+" + b + "+" + c + "+" + d + "+" + e + ")";
  }
}

class SixTuple<A,B,C,D,E,F> extends FiveTuple<A,B,C,D,E> {
  final F f;
  SixTuple(A a, B b, C c, D d, E e, F f) {
	super(a, b, c, d, e);
	this.f = f;
  }
  public String toString() {
  	return "(" + a + "+" + b + "+" + c + "+" + d + "+" + e + "+" + f +")";
  }
}

class TupleTest2 {
  static TwoTuple<String,Integer> f() {
    return new TwoTuple("hi", 47);
  }
  static TwoTuple f2() { return new TwoTuple("hi", 47); }
  static ThreeTuple<String,String,Integer> g() {
    return new ThreeTuple(new String("Three"), "hi", 47);
  }
  static
  FourTuple<String,String,String,Integer> h() {
    return new FourTuple(new String("Four"), new String("Four"), "hi", 47);
  }
  static
  FiveTuple<String,String,String,Integer,Double> k() {
    return new FiveTuple(new String("Five"), new String("Five"),
      "hi", 47, 11.1);
  }
  static
  SixTuple<String,String,String,Integer,Double,Integer> l() {
    return new SixTuple(new String("Six"), new String("Six"),
      "hi", 47, 11.1, 13);
  }
  public static void main(String[] args) {
    TwoTuple<String,Integer> ttsi = f();
    System.out.println(ttsi);
    System.out.println(f2());
    System.out.println(g());
    System.out.println(h());
    System.out.println(k());
    System.out.println(l());
  }
}

public class ExerciseSixteen {
  public static void main(String[] args) {
  	TupleTest2.main(args);
  }
}