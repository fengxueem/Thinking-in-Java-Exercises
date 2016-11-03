import java.util.*;

public class ExerciseTwentyThree {
  private static int getBestInt20(int n) {
    Random rand = new Random();
    Map<Integer, Integer> m =
      new TreeMap<Integer, Integer>();
    for(int i = 0; i < n; i++) {
      // Produce a number between 0 and 20:
      int r = rand.nextInt(20);
      Integer freq = m.get(r);
      m.put(r, freq == null ? 1 : freq + 1);
    }
    int max = 0;
    int maxKey = 0;
    for(int i = 0; i < m.keySet().size(); i++) {
      maxKey = max < m.get(i) ? i : maxKey;
      max = max < m.get(i) ? m.get(i) : max;
    }
    return maxKey;      
  }

  public static void main(String[] args) {
    Map<Integer,Integer> m20 =    
      new TreeMap<Integer,Integer>();
    for(int i = 0; i < 200000; i++) {
      int x = getBestInt20(10000);
      Integer freq = m20.get(x);
      m20.put(x, freq == null ? 1 : freq + 1);
    }
    System.out.println("Most often picked ints, 0 - 19, in 200000 tests of 10,000 random picks: " + m20);
  }
}