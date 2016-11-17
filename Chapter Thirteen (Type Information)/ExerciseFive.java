import java.util.*;

abstract class Shape {

  void draw() { System.out.println(this + ".draw()"); }

  void rotate() {
    // if (this.getClass().getSimpleName().equals("Circle")) { return; } also works
    if (this instanceof Circle) {
      return;
    }
    System.out.println(this + " is rotating");
  }

  abstract public String toString();

}

class Circle extends Shape {

  public String toString() { return "Circle"; }

}

class Square extends Shape {

  public String toString() { return "Square"; }

}

class Triangle extends Shape {

  public String toString() { return "Triangle"; }

}	

class Shapes {

  public static void main(String[] args) {
    List<Shape> shapeList = Arrays.asList(
      new Circle(), new Square(), new Triangle()
    );
    for(Shape shape : shapeList)
      shape.rotate();
  }

}


public class ExerciseFive {
  
  public static void main(String[] args){
    Shapes.main(args);
  }  

}