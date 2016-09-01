class Dog {

	protected void finalize() {
		System.out.println( this + " is being destroied.");
	}

}

public class ExerciseEleven {
	public static void main(String[] args) {
		new Dog();
		new Dog();
		//System.gc(); // no guarantee that this unreferenced object will be destroied.
		/*Deprecated. This method is inherently unsafe. It may result in finalizers 
		*being called on live objects while other threads are concurrently manipulating 
		*those objects, resulting in erratic behavior or deadlock.*/
		System.runFinalizersOnExit(true);
	}
}
