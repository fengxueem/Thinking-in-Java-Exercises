import java.util.*;
import java.lang.reflect.*;

public class ExerciseNine {
	
  public static void printClassHierarchy(Class c) {
  	if (c.getSuperclass() != null)
  	  printClassHierarchy(c.getSuperclass());
  	System.out.println(c.getCanonicalName());
  	for (Field f : c.getDeclaredFields()) {
  	  System.out.println(" " + f.getName());
  	}
  }

  public static void printClassHierarchy(Object o) {
  	printClassHierarchy(o.getClass());
  }

  public static void main(String[] args){
	  printClassHierarchy(new HashSet());
  }	
}