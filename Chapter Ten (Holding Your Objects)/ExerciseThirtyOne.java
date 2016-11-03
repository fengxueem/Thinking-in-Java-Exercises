import polymorphism.shape.*;
import java.util.*;

class RandomShapeGenerator implements Iterable<Shape>{
  private Random rand = new Random(47);
  private int num;

  public Shape next() {
    switch(rand.nextInt(3)) {
      default:
      case 0: return new Circle();
      case 1: return new Square();
      case 2: return new Triangle();
    }
  }

  public RandomShapeGenerator() {}

  public RandomShapeGenerator(int num) {
    this.num = num;
  }

  @Override
  public Iterator<Shape> iterator() {
    return new Iterator<Shape>() {
      private int index = 0;
      public boolean hasNext() {
        return index < num;
      }
      public Shape next() {
        index++;
        return RandomShapeGenerator.this.next();
      }
      public void remove() {
        throw new UnsupportedOperationException();
      }
    };
  }

}


public class ExerciseThirtyOne {
  
  public static void main(String[] args){
    List<Shape> shapeList = new ArrayList<Shape>();
    RandomShapeGenerator gen = new RandomShapeGenerator(20);
    Iterator<Shape> it = gen.iterator();
    while (it.hasNext()){
      shapeList.add(it.next());
    }
    System.out.println(shapeList);
  }  
}