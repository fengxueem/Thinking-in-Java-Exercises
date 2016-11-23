class BasicCoffee {
  private String recipe = " coffee ";
  public void addRecipe(String ingredient) { recipe += ingredient; }
  public String toString() { return recipe; }
}

class Decorator extends BasicCoffee {
  protected BasicCoffee basicCoffee;
  public Decorator(BasicCoffee basicCoffee) { this.basicCoffee = basicCoffee; }
  public void addRecipe(String ingredient) { basicCoffee.addRecipe(ingredient); }
  public String toString() { return basicCoffee.toString(); }
}

class SteamedMilk extends Decorator {
  public SteamedMilk(BasicCoffee basicCoffee) {
   super(basicCoffee); 
   addRecipe("Steamed Milk ");
  }
}

class Foam extends Decorator {
  public Foam(BasicCoffee basicCoffee) {
   super(basicCoffee); 
   addRecipe("Foam ");
  }
}

class Chocolate extends Decorator {
  public Chocolate(BasicCoffee basicCoffee) {
   super(basicCoffee); 
   addRecipe("Chocolate ");
  }
}

class Caramel extends Decorator {
  public Caramel(BasicCoffee basicCoffee) {
   super(basicCoffee);
   addRecipe("Caramel ");
  }
}

class WhippedCream extends Decorator {
  public WhippedCream(BasicCoffee basicCoffee) {
   super(basicCoffee); 
   addRecipe("Whipped Cream ");
  }
}

public class ExerciseThirtyeight {
  public static void main(String[] args) {
  	BasicCoffee basicCoffee = new BasicCoffee();
  	BasicCoffee coffee = new Foam(new Caramel(basicCoffee));
  	System.out.println(coffee);
  }
}