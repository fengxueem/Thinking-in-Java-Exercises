import java.util.prefs.*;
import java.io.*;

public class ExerciseThirtyThree {
  public static void main(String[] args) throws Exception {
  	Preferences preferences = Preferences.userNodeForPackage(ExerciseThirtyThree.class);
  	preferences.put("base directory", "a");
  	System.out.println("Current value for \"base directory\" is " + preferences.get("base directory", "null"));
  	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
  	String newValue;
    while((newValue = reader.readLine()) != null && newValue.length()!= 0) {
  	  preferences.put("base directory", newValue);
  	  System.out.println("Current value for \"base directory\" is " + preferences.get("base directory", "null"));
    }
  }
}