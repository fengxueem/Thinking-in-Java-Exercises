import java.util.*;

class BerylliumSphere {
  private static long counter = 0;
  private final long id = counter++;
  public long getID() { return id; }
  @Override
  public String toString() {
  	return "BerylliumSphere: " + id;
  }
}

public class ExerciseEighteen {
  public static void main(String[] args) {
  	BerylliumSphere[] b1 = {new BerylliumSphere(), new BerylliumSphere()};
  	System.out.println("Here is an array (b1): " + Arrays.toString(b1));

  	System.out.println("Start copying from b1 to b2...");
  	BerylliumSphere[] b2 = new BerylliumSphere[2];
  	
  	System.arraycopy(b1, 0, b2, 0, b1.length);
  	System.out.println("Finish copying! Here is b2: " + Arrays.toString(b2));
  }
}