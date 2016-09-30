interface A {
	public int i = 10;
}

public class ExerciseSeventeen implements A{

	public static void main(String[] args){
		System.out.println(ExerciseSeventeen.i);		
		ExerciseSeventeen e = new ExerciseSeventeen();
		// e.i = 11; Error: cannot assign a value to a final variable.
	}	
}