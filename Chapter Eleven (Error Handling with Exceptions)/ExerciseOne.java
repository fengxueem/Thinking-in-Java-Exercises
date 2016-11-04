
public class ExerciseOne {
	
	public static void main(String[] args){
		try {
			throw new Exception("I am a MESSAGE.");
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			System.out.println("It comes to here! In finally!");
		}
	}	
}