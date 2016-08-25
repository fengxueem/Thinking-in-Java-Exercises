public class ExerciseFour {
	public static void main(String[] args) {
		for ( int i = 2; i < 10000; i++) {
			int factor;
			for ( factor = 2; factor <= Math.round(Math.sqrt(i)) ; factor++) {
				if (i % factor == 0) {
					break;
				}
			}
			if (factor > Math.round(Math.sqrt(i))) {
				System.out.println(i + " is a prime number.");
			}
		}
	}
}