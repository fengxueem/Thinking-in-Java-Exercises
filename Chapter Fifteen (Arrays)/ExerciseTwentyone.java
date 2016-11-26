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

class BerylliumSphereComparator implements Comparator<BerylliumSphere> {
  public int compare(BerylliumSphere b1, BerylliumSphere b2) {
  	return (b1.getID() > b2.getID() ? -1 : (b1.getID() == b2.getID() ? 0 : 1));
  }
}

public class ExerciseTwentyone {
  public static void main(String[] args) {
  	BerylliumSphere[] b1 = {new BerylliumSphere(), new BerylliumSphere()};
  	System.out.println("Here is an array (b1) before comparison: " + Arrays.toString(b1));
  	Arrays.sort(b1, new BerylliumSphereComparator());
  	System.out.println("Here is an array (b1) after comparison: " + Arrays.toString(b1));
  }
}