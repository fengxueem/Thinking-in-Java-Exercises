import java.util.*;

public class ExerciseThree {
  static Double[][] createDoubleMatrix(int m, int n, Double min, Double max) {
  	if ((m <= 0) || (n <= 0)) {
  	  throw new IllegalArgumentException("matrix size must be positive.");
  	}
  	Double[][] result = new Double[m][n];
  	Random random = new Random();
  	for (int i = 0; i < result.length; i++) {
  	  for (int j = 0; j < result[i].length; j++) {
  	  	result[i][j] = min + random.nextDouble() * (max - min);
  	  }
  	}
  	return result;
  }
  static void printMatrix(Object[][] matrix) {
  	Formatter f = new Formatter(System.out);
  	for (int i = 0; i < matrix.length; i++) {
  	  // print "[" and "]" at two ends of a line
  	  System.out.print("[ ");
  	  // start print each element in line number i;
  	  for (int j = 0; j < matrix[i].length; j++) {
  	  	f.format("%10.8f ", matrix[i][j]);
  	  }
  	  System.out.print("]");
  	  // finish this line with a CLRF
  	  System.out.println();
  	}
  }
  public static void main(String[] args) {
  	Double[][] doubleMatrix1 = createDoubleMatrix(3, 3, 10.0, 15.0);
  	printMatrix(doubleMatrix1);
  	Double[][] doubleMatrix2 = createDoubleMatrix(4, 4, -1.0, -2.0);
  	printMatrix(doubleMatrix2);
  	Double[][] doubleMatrix3 = createDoubleMatrix(5, 5, 0.0, 1.0);
  	printMatrix(doubleMatrix3);
  }
}