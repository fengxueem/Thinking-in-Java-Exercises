public class ExerciseNine {

	public static void generateFibonacci( int num ) {
		if (num <= 0) return;
		System.out.println("The first " + num + " Fibonacco elements are:");
		for ( int i = 0; i < num ; i++) {
			System.out.println(fib(i));
		}
	}

	private static int fib( int num) {
		if (num < 2) {
			return 1;
		}
		return (fib(num-1) + fib(num-2));
	}

	public static void main(String[] args) {
		ExerciseNine.generateFibonacci(5);
	}
}