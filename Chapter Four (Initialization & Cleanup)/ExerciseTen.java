class Dog {

	protected void finalize() {
		System.out.println( this + " is being destroied.");
	}

}

public class ExerciseTen {
	public static void main(String[] args) {
		new Dog();
		new Dog();
		System.gc(); // no guarantee that this unreferenced object will be destroied.
	}
}
