import java.util.*;

public class ExerciseEight {
	
  public static void printClassHierarchy(Class c) {
  	if (c.getSuperclass() != null)
  	  printClassHierarchy(c.getSuperclass());
  	System.out.println(c.getCanonicalName());
  }

  public static void printClassHierarchy(Object o) {
  	printClassHierarchy(o.getClass());
  }

  public static void main(String[] args){
	printClassHierarchy(new HashSet());
  }	
}