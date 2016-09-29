import reusing.exercisefifteenhelper.*;

public class ExerciseFifteen {
	
	static class Test extends ExerciseFifteenHelper {
		public String append(String a) {
			return super.append(a);
		}
	}

	public static void main(String[] args){
		ExerciseFifteenHelper e = new ExerciseFifteenHelper();
		// error: append(String) has protcted access in ExerciseFifteenHelper 
		// e.append("xixi"); 
		Test t = new Test();
		System.out.println( t.append("xixi") );
	}
}