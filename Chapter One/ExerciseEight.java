class ExerciseEight {

	String s;
	static int i = 317;

	int storage(String s) {
		return s.length()*2;
	}

	public static void main(String[] args) {
		ExerciseEight e = new ExerciseEight();
		e.s = "apple";
		ExerciseEight e1 = new ExerciseEight();
		e1.s = "pig";
		System.out.println("There are " + e.storage(e.s)/2 + " letters in '" + e.s +"'. And static integer via object " + e.s + " is " + e.i + ".");
		System.out.println("The static integer via object " + e1.s + " is " + e.i + ".");
		System.out.println("Adding one to the static integer via object " + e.s);
		e.i++;
		System.out.println("New results:");
		System.out.println("There are " + e.storage(e.s)/2 + " letters in '" + e.s +"'. And static integer via object " + e.s + " is " + e.i + ".");
		System.out.println("The static integer via object " + e1.s + " is " + e.i + ".");
	}
}