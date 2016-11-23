class A {
  Integer i = 21;
  String s = "twentyone";
  public A() {}
  public A(Integer i, String s) {
  	this.i = i;
  	this.s = s;
  }
  public String toString() { return "" + s + i; }
}

public class ExerciseTwentytwo {
  static <T> T createNew(Class<T> kind, Object... args) throws Exception {
  	Class[] parameterTypes = new Class[args.length];
  	int index = 0;
  	for (Object arg : args) {
  	  parameterTypes[index++] = arg.getClass();
  	}
  	return kind.getConstructor(parameterTypes).newInstance(args);
  } 
  public static void main(String[] args) throws Exception {
  	System.out.println(createNew(A.class, new Integer(13), "thirteen").toString());
  	System.out.println(createNew(A.class).toString());
  	// produce an NoSuchMethodException indicating 
  	// the constructor we call here does not exist.
  	System.out.println(createNew(A.class, new Integer(12)).toString());
  }
}