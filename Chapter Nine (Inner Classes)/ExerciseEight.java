
public class ExerciseEight {
	
	class Inner {
		private int i = 0;
	}

	public int readInnerClassPrivateField() {
		return new Inner().i;
	}

	public static void main(String[] args){
		ExerciseEight e = new ExerciseEight();
		System.out.println(e.readInnerClassPrivateField());
	}	
}