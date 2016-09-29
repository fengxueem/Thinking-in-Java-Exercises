public class ExerciseNineteen {
	
	final int i;
	// final int i = 2; if i is assigned in the definition, then i cannot be assigned again in the consturctor

	ExerciseNineteen() {
		i = 1;
	}

	public static void main(String[] args){
		ExerciseNineteen e = new ExerciseNineteen();
		System.out.println(e.i);
		// Errors: cannot assign values 
		// e.i++;
	}	
}