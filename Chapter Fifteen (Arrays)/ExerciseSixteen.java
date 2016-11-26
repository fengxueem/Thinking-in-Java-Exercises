import net.mindview.util.*;
import static net.mindview.util.Print.*;
import java.util.*;

interface SkipGenerator<T> extends Generator<T>{
  T next(int n);
}

class SkipGenerated {
  public static class Generated {
    static <T> T[] array(Class<?> type, SkipGenerator<T> gen, int size) {
      T[] a = (T[])java.lang.reflect.Array.newInstance(type, size);
      for(int i = 0; i < a.length; i++) {
        a[i] = gen.next();
      }
      return a;
    }
    static <T> T[] array(Class<?> type, SkipGenerator<T> gen, int size, int skip) {
      T[] a = (T[])java.lang.reflect.Array.newInstance(type, size);
      for(int i = 0; i < a.length; i++) {
        a[i] = gen.next(skip);
      }
      return a;
    }
  }
  public static class
  Boolean implements SkipGenerator<java.lang.Boolean> {
    private boolean value = false;
    public java.lang.Boolean next() {
      value = !value; // Just flips back and forth
      return value;
    }
    public java.lang.Boolean next(int n) {
      return (n % 2 == 0) ? value : !value;
    }
  }
  public static class
  Byte implements SkipGenerator<java.lang.Byte> {
    private byte value = 0;
    public java.lang.Byte next() { return value++; }
    public java.lang.Byte next(int n) { return value += n; }
  }
  static char[] chars = ("abcdefghijklmnopqrstuvwxyz" +
    "ABCDEFGHIJKLMNOPQRSTUVWXYZ").toCharArray();
  public static class
  Character implements SkipGenerator<java.lang.Character> {
    int index = -1;
    public java.lang.Character next() {
      index = (index + 1) % chars.length;
      return chars[index];
    }
    public java.lang.Character next(int n) {
      index = (index + 1 + n) % chars.length;
      return chars[index];
    }
  }
  public static class
  String implements SkipGenerator<java.lang.String> {
    private int length = 7;
    SkipGenerator<java.lang.Character> cg = new Character();
    public String() {}
    public String(int length) { this.length = length; }
    public java.lang.String next() {
      char[] buf = new char[length];
      for(int i = 0; i < length; i++)
        buf[i] = cg.next();
      return new java.lang.String(buf);
    }
    public java.lang.String next(int n) {
      char[] buf = new char[length];
      for(int i = 0; i < length; i++)
        buf[i] = cg.next(n);
      return new java.lang.String(buf);
    }
  }
  public static class
  Short implements SkipGenerator<java.lang.Short> {
    private short value = 0;
    public java.lang.Short next() { return value++; }
    public java.lang.Short next(int n) { return value += n; }
  }
  public static class
  Integer implements SkipGenerator<java.lang.Integer> {
    private int value = 0;
    public java.lang.Integer next() { return value++; }
    public java.lang.Integer next(int n) { return value += n; }
  }
  public static class
  Long implements SkipGenerator<java.lang.Long> {
    private long value = 0;
    public java.lang.Long next() { return value++; }
    public java.lang.Long next(int n) { return value += n; }
  }
  public static class
  Float implements SkipGenerator<java.lang.Float> {
    private float value = 0;
    public java.lang.Float next() {
      return value += 1.0;
    }
    public java.lang.Float next(int n) {
      return value += (1.0 * n);
    }
  }
  public static class
  Double implements SkipGenerator<java.lang.Double> {
    private double value = 0.0;
    public java.lang.Double next() {
      return value += 1.0;
    }
    public java.lang.Double next(int n) {
      return value += (1.0 * n);
    }
  }
}

public class ExerciseSixteen {
  public static void main(String[] args) {
    // without skip
    int size = 6;
    boolean[] a1 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Boolean.class, new SkipGenerated.Boolean(), size));
    print("a1 = " + Arrays.toString(a1));
    byte[] a2 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Byte.class, new SkipGenerated.Byte(), size));
    print("a2 = " + Arrays.toString(a2));
    char[] a3 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Character.class, new SkipGenerated.Character(), size));
    print("a3 = " + Arrays.toString(a3));
    short[] a4 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Short.class, new SkipGenerated.Short(), size));
    print("a4 = " + Arrays.toString(a4));
    int[] a5 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Integer.class, new SkipGenerated.Integer(), size));
    print("a5 = " + Arrays.toString(a5));
    long[] a6 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Long.class, new SkipGenerated.Long(), size));
    print("a6 = " + Arrays.toString(a6));
    float[] a7 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Float.class, new SkipGenerated.Float(), size));
    print("a7 = " + Arrays.toString(a7));
    double[] a8 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Double.class, new SkipGenerated.Double(), size));
    print("a8 = " + Arrays.toString(a8));
    // with skip
    int skip = 317;
    boolean[] a9 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Boolean.class, new SkipGenerated.Boolean(), size, skip));
    print("a9 = " + Arrays.toString(a9));
    byte[] a10 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Byte.class, new SkipGenerated.Byte(), size, skip));
    print("a10 = " + Arrays.toString(a10));
    char[] a11 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Character.class, new SkipGenerated.Character(), size, skip));
    print("a11 = " + Arrays.toString(a11));
    short[] a12 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Short.class, new SkipGenerated.Short(), size, skip));
    print("a12 = " + Arrays.toString(a12));
    int[] a13 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Integer.class, new SkipGenerated.Integer(), size, skip));
    print("a13 = " + Arrays.toString(a13));
    long[] a14 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Long.class, new SkipGenerated.Long(), size, skip));
    print("a14 = " + Arrays.toString(a14));
    float[] a15 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Float.class, new SkipGenerated.Float(), size, skip));
    print("a15 = " + Arrays.toString(a15));
    double[] a16 = ConvertTo.primitive(SkipGenerated.Generated.array(
      java.lang.Double.class, new SkipGenerated.Double(), size, skip));
    print("a16 = " + Arrays.toString(a16));
  }  
}