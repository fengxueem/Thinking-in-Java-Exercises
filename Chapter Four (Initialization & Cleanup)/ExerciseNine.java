class Dog {
	Dog( int i ) {
		System.out.println("int constructor");
	}

	Dog( boolean b ) {
		this(1); // using 'this' keyword to call the int constructor
		System.out.println("boolean constructor");
	}

}

public class ExerciseNine {
	public static void main(String[] args) {
		Dog d = new Dog(1);
		Dog d1 = new Dog(true);
	}
}
