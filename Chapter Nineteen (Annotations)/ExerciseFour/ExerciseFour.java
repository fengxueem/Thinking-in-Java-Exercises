package exercisefour;
import java.util.*;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class ExerciseFour {
  SYQHashSet<String> testObject = new SYQHashSet<String>();
  @Test boolean _initialization() {
    return testObject.isEmpty();
  }
  @Test boolean _contains() {
    testObject.add("one");
    return testObject.contains("one");
  }
  @Test boolean _remove() {
    testObject.add("one");
    testObject.remove("one");
    return testObject.isEmpty();
  }
  public static void main(String[] args) throws Exception {
    new ExerciseFour();
    OSExecute.command(
      "java net.mindview.atunit.AtUnit ExerciseFour");
  }
}

class SYQHashSet<T> extends HashSet<T> {
  private static long counter = 0l;
  private final long id = counter++;
  public SYQHashSet() {
    System.out.println("This is #" + id + " SYQHashSet. ");
  }
}
