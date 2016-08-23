class ExerciseSix {

	String s = "apple";

	int storage(String s) {
		return s.length()*2;
	}

	public static void main(String[] args) {
		ExerciseSix e = new ExerciseSix();
		System.out.println("There are "+ e.storage(e.s)/2 + " letters in 'apple'.");
	}
}