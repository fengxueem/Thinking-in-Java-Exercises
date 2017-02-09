import net.mindview.util.*;
import java.util.*;
import java.io.*;

// Procedure of redirection in Linux as simple as follows:
// java ExerciseTwentyOne < ExerciseTwentyOne.java
public class ExerciseTwentyOne {
  public static void main(String[] args) throws IOException {
	BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));  	
	String temp;
	while ((temp = stdin.readLine()) != null && temp.length() != 0){
	  System.out.println(temp.toUpperCase());
	}
  }
}