public class ExerciseNineteen {

	static void f(String... args) {
		if (args == null) {
			return;
		}
		for (String s: args) {
			System.out.println(s);
		}
	}

	public static void main(String[] args) {
		ExerciseNineteen.f("first","a","b");
		ExerciseNineteen.f(new String[] {"second","a","b"});		
	}
}
