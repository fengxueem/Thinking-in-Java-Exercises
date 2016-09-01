class Dog {

	void bark( int t) {
		System.out.println("int barking");
	}

	void bark( String s) {
		System.out.println("String barking");		
	}

	void bark( char c) {
		System.out.println("char barking");
	}

}

public class ExerciseFive {
	public static void main(String[] args) {
		Dog d = new Dog();
		d.bark('c');
		d.bark(1);
		d.bark("c");
	}
}
