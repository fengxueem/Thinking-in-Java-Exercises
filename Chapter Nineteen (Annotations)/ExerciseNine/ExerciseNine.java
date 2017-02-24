package exercisenine;

import java.util.*;
import net.mindview.atunit.*;
import net.mindview.util.*;

public class ExerciseNine {
  HashMap<Integer, String> testObject = new HashMap<>();

  @Test void stringShouldBePut() {
  	String s = "string";
  	testObject.put(1,s);
  	assert testObject.containsKey(1) : "no valid key detected";
  	assert testObject.containsValue("string") : "no valid value detected";
  }

  public static void main(String[] args) {
  	OSExecute.command(
  		"java net.mindview.atunit.AtUnit ExerciseNine");
  }
}