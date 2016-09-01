class ExerciseTen {
	public static void main(String[] args) {
		if (args.length < 3) {
			System.out.println("Please enter at least three arguments after the class name.");
			return;
		}
		System.out.println("The first three arguments are listed below:");
		System.out.println(args[0]);
		System.out.println(args[1]);
		System.out.println(args[2]);
	}
}