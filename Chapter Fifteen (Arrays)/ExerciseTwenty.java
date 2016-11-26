import java.util.*;

public class ExerciseTwenty {
  public static void main(String[] args) {
  	Double[][] m1 = ExerciseThree.createDoubleMatrix(3, 3, 10.0, 10.0);
  	System.out.println("Matrix 1:");
  	ExerciseThree.printMatrix(m1);
  	Double[][] m2 = ExerciseThree.createDoubleMatrix(3, 3, 10.0, 10.0);
  	System.out.println("Matrix 2:");
  	ExerciseThree.printMatrix(m2);
  	Double[][] m3 = ExerciseThree.createDoubleMatrix(2, 2, 10.0, 10.0);
  	System.out.println("Matrix 3:");
  	ExerciseThree.printMatrix(m3);
  	System.out.println("m1 comparing with m2: " + Arrays.deepEquals(m1, m2));
  	System.out.println("m1 comparing with m3: " + Arrays.deepEquals(m1, m3));
  }
}