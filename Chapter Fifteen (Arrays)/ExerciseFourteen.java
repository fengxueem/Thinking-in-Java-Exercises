import net.mindview.util.*;
import java.lang.reflect.*;
import java.util.*;

public class ExerciseFourteen {
  // Method 1:
  // this generic method can only work with int, long, double.
  // Because, java.util.stream.Stream<T> only contains mapToInt, mapToLong, mapToDouble
  @SuppressWarnings("unchecked")
  static <T> T[] fillPrimitiveArray(int size, Class<T> classToken, Generator<T> gen) {
  	T[] result = (T[])Array.newInstance(classToken, size);
  	for (int i =0; i < size; i++) {
  		result[i] = gen.next();
  	}
  	return result;
  }
  // Method 2:
  static char[] createCharArray(int size, Generator<Character> gen) {
    char[] result = new char[size];
    for (int i = 0; i < size; i++) {
      result[i] = gen.next();
    }
    return result;
  }

  static byte[] createByteArray(int size, Generator<Byte> gen) {
    byte[] result = new byte[size];
    for (int i = 0; i < size; i++) {
      result[i] = gen.next();
    }
    return result;
  }

  static float[] createFloatArray(int size, Generator<Float> gen) {
    float[] result = new float[size];
    for (int i = 0; i < size; i++) {
      result[i] = gen.next();
    }
    return result;
  }

  public static void main(String[] args) {
    // Method 1:
    // use Arrays.stream(), new feature in Java8.
    // convert Integer[] to int[]
    //         Double[] to double[]
    //         Long[] to long[]
    int[] intArray = Arrays.stream(
      fillPrimitiveArray(10, Integer.class, new CountingGenerator.Integer())
      ).mapToInt(Integer::intValue).toArray();
    double[] doubleArray = Arrays.stream(
      fillPrimitiveArray(10, Double.class, new CountingGenerator.Double())
      ).mapToDouble(Double::doubleValue).toArray();
    long[] longArray = Arrays.stream(
      fillPrimitiveArray(10, Long.class, new CountingGenerator.Long())
      ).mapToLong(Long::longValue).toArray();
    System.out.println("Method 1: ");
    System.out.println("int[]: " + Arrays.toString(intArray));
    System.out.println("double[]: " + Arrays.toString(doubleArray));
    System.out.println("long[]: " + Arrays.toString(longArray));
    // Method 2:
    char[] charArray2 = createCharArray(10, new CountingGenerator.Character());
    byte[] byteArray2 = createByteArray(10, new CountingGenerator.Byte());
    float[] floatArray2 = createFloatArray(10, new CountingGenerator.Float());
    System.out.println("Method 2: ");
    System.out.println("char[]: " + Arrays.toString(charArray2));
    System.out.println("byte[]: " + Arrays.toString(byteArray2));
    System.out.println("float[]: " + Arrays.toString(floatArray2));
    // Method 3:
    char[] charArray3 = ConvertTo.primitive(
        fillPrimitiveArray(10, Character.class, new CountingGenerator.Character()));
    System.out.println("Method 3: ");
    System.out.println("char[]: " + Arrays.toString(charArray3));
  }
}