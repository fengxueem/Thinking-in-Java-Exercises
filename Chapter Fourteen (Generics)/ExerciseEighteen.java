import java.util.*;
import net.mindview.util.*;

class BigFish {
  private static long counter = 1;
  private final long id = counter++;
  private BigFish() {}
  public String toString() { return "BigFish " + id; }
  // A method to produce Generator objects:
  public static Generator<BigFish> generator() {
    return new Generator<BigFish>() {
      public BigFish next() { return new BigFish(); }
    };
  }
}	

class LittleFish {
  private static long counter = 1;
  private final long id = counter++;
  private LittleFish() {}
  public String toString() { return "LittleFish " + id; }
  // A single Generator object:
  public static Generator<LittleFish> generator =
    new Generator<LittleFish>() {
      public LittleFish next() { return new LittleFish(); }
    };
}	

class Generators {
  public static <T> Collection<T>
  fill(Collection<T> coll, Generator<T> gen, int n) {
    for(int i = 0; i < n; i++)
      coll.add(gen.next());
    return coll;
  }
}

class Ocean {
  public static void eat(LittleFish t, BigFish c) {
    System.out.println(t + " eats " + c);
  }
  public static void main(String[] args) {
    Random rand = new Random(47);
    Queue<BigFish> line = new LinkedList<BigFish>();
    Generators.fill(line, BigFish.generator(), 15);
    List<LittleFish> LittleFishs = new ArrayList<LittleFish>();
    Generators.fill(LittleFishs, LittleFish.generator, 4);
    for(BigFish c : line)
      eat(LittleFishs.get(rand.nextInt(LittleFishs.size())), c);
  }	
}

public class ExerciseEighteen {
  public static void main(String[] args) {
  	Ocean.main(args);
  }
}