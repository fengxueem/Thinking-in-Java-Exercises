import exercisefive.*;


public class ExerciseFive implements Color{
	
	public void dilute() {
		System.out.println("Dilute");
	}

	public void mix() {}
	public void erase() {}

	public static void main(String[] args){
		ExerciseFive e = new ExerciseFive();
		e.dilute();
	}	
}