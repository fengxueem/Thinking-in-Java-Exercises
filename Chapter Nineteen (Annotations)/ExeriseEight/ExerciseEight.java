package exerciseeight;

import net.mindview.atunit.*;
import net.mindview.util.*;

class Some {
  private void haveSome() {
  	System.out.println(" have some ");
  }

  @TestProperty 
  public void haveSomeTests() {
  	haveSome();
  }

}

public class ExerciseEight extends Some {
  @Test
  public boolean shoudPrintHaveSome() {
  	haveSomeTests();
  	return true;
  }
  
  public static void main(String[] args) {
  	OSExecute.command(
  		"java net.mindview.atunit.AtUnit ExerciseEight");
  }
}