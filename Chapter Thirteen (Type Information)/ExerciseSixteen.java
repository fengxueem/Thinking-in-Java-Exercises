import typeinfo.factory.*;
import java.util.*;
import net.mindview.util.*;

class Coffee {
  private static long counter = 0;
  private final long id = counter++;
  public String toString() {
    return getClass().getSimpleName() + " " + id;
  }
}

class Latte extends Coffee {
	public static class Factory implements typeinfo.factory.Factory<Latte> {
		public Latte create() { return new Latte(); }
	}
}

class Mocha extends Coffee {
	public static class Factory implements typeinfo.factory.Factory<Mocha> {
		public Mocha create() { return new Mocha(); }
	}
}

class Cappuccino extends Coffee {
	public static class Factory implements typeinfo.factory.Factory<Cappuccino> {
		public Cappuccino create() { return new Cappuccino(); }
	}
}

class Americano extends Coffee {
	public static class Factory implements typeinfo.factory.Factory<Americano> {
		public Americano create() { return new Americano(); }
	}
}

class Breve extends Coffee {
	public static class Factory implements typeinfo.factory.Factory<Breve> {
		public Breve create() { return new Breve(); }
	}
}

class CoffeeGenerator
implements Generator<Coffee>, Iterable<Coffee> {
  private static List<Factory<? extends Coffee>> coffeeFactories =
		new ArrayList<Factory<? extends Coffee>>();
  private static Random rand = new Random(47);
  // For iteration:
  private int size = 0;
  static {
	  coffeeFactories.add(new Latte.Factory());
    coffeeFactories.add(new Mocha.Factory());
    coffeeFactories.add(new Cappuccino.Factory());
    coffeeFactories.add(new Americano.Factory());
    coffeeFactories.add(new Breve.Factory());
  }
  public CoffeeGenerator() {}
  public CoffeeGenerator(int sz) { size = sz; }	
  public Coffee next() {
	  int n = rand.nextInt(coffeeFactories.size());   
	  return coffeeFactories.get(n).create();
  }
  class CoffeeIterator implements Iterator<Coffee> {
    int count = size;
    public boolean hasNext() { return count > 0; }
    public Coffee next() {
      count--;
      return CoffeeGenerator.this.next();
    }
    public void remove() { // Not implemented
      throw new UnsupportedOperationException();
    }
  };	
  public Iterator<Coffee> iterator() {
    return new CoffeeIterator();
  }
  public static void main(String[] args) {
    CoffeeGenerator gen = new CoffeeGenerator();
    for(int i = 0; i < 5; i++)
      System.out.println(gen.next());
    for(Coffee c : new CoffeeGenerator(5))
      System.out.println(c);
  }
}


public class ExerciseSixteen {
  public static void main(String[] args){
	CoffeeGenerator.main(args);			
  }	
}