class ExerciseFourteen {

	public static void booleanStringTest( String st1, String st2) {
		System.out.println("Comparison between: " + st1 + " & " + st2);
		// System.out.println("st1 > st2:" + (st1 > st2) ); // not valid
		System.out.println("st1 == st2:" + (st1 == st2) );
		System.out.println("st1 != st2:" + (st1 != st2) );
		System.out.println("st1.equals(st2):" + (st1.equals(st2)) );
		// System.out.println("st1 <= st2:" + (st1 <= st2) ); // not valid
		// System.out.println("st1 && st2:" + (st1 && st2) ); // not valid
		// System.out.println("st1 || st2:" + (st1 || st2) ); // not valid 
	}

	public static void main(String[] args) {
		ExerciseFourteen.booleanStringTest("asd","dsa");
		ExerciseFourteen.booleanStringTest("apple","dog");
		ExerciseFourteen.booleanStringTest("Washington","manipulate");
		ExerciseFourteen.booleanStringTest("Chicago","Illinois");
		ExerciseFourteen.booleanStringTest("Win","Win");
	}
}