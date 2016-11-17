import net.mindview.util.*;
import generics.coffee.*;


public class ExerciseTwelve {
  
  public static void main(String[] args){
    // counter dealing with every type derived from Coffee
    TypeCounter typeCounter = new TypeCounter(Coffee.class);  
    // generate 10 different types and count
    for (Coffee coffee : new CoffeeGenerator(10)) {
      System.out.println("counting: " + coffee.getClass().getSimpleName());
      typeCounter.count(coffee);
    }
    // show the results
    System.out.println("Counted Types:");
    System.out.println(typeCounter);
  }  

}