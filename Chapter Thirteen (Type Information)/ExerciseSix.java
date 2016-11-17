import java.util.*;

abstract class Shape {
  boolean highlighted = false;

  void draw() { System.out.println(this + ".draw()"); }
  abstract public String toString();
}

class Circle extends Shape {
  public String toString() { return (highlighted ? "highlighted " : "") + "Circle"; }
}

class Square extends Shape {
  public String toString() { return (highlighted ? "highlighted " : "") + "Square"; }
}

class Triangle extends Shape {
  public String toString() { return (highlighted ? "highlighted " : "") + "Triangle"; }
}	

class Shapes {
  public static void main(String[] args) {
    List<Shape> shapeList = Arrays.asList(
      new Circle(), new Square(), new Triangle()
    );
    for(Shape shape : shapeList) {
      if (shape instanceof Square) {
        shape.highlighted = true;
      }
      shape.draw();
    }
  }
}

public class ExerciseSix {
  public static void main(String[] args){
    Shapes.main(args);    
  }  
}