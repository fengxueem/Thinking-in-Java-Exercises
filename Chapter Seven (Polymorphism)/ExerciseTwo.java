import java.util.Random;

class Shape {
	public void draw() {}
	public void erase() {}
}

class Circle extends Shape {

	@Override
	public void draw() {
		System.out.println("Circle.draw()");
	}
	
	@Override
	public void erase() {
		System.out.println("Circle.erase()");
	}
}

class Square extends Shape {

	@Override
	public void draw() {
		System.out.println("Square.draw()");
	}
	
	@Override
	public void erase() {
		System.out.println("Square.erase()");
	}
}

class Triangle extends Shape {

	@Override
	public void draw() {
		System.out.println("Triangle.draw()");
	}
	
	@Override
	public void erase() {
		System.out.println("Triangle.erase()");
	}
}

class RandomShapeGenerator {

	private Random rand = new Random(47);

	public Shape next() {
		switch (rand.nextInt(3)) {
			default:
			case 2: return new Circle();
			case 1: return new Square();
			case 0: return new Triangle();
		}
	} 
}

public class ExerciseTwo {
	
	public static void main(String[] args){
		RandomShapeGenerator r = new RandomShapeGenerator();
		Shape[] s = new Shape[20];
		for ( int i = 0 ; i < s.length ; i++ ) {
			s[i] = r.next();
		}
		for (Shape shape : s) {
			shape.draw();
		}
	}	
}