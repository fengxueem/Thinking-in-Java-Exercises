public class ExerciseFive {

	public static String toBinaryString( int num) {
		if(num == 0) return "0";
		else {
			String outputString = "";
			int nlz = Integer.numberOfLeadingZeros(num); 
			num <<= nlz;
			for(int p = 0; p < 32 - nlz; p++) {
				String temp = (Integer.numberOfLeadingZeros(num) == 0) ? "1" : "0";
				outputString = outputString + temp;
				num <<= 1;
			}
			return outputString;		
		}
	}

	public static void main(String[] args) {
		System.out.println("1 = " + ExerciseFive.toBinaryString(-1));
		int num1 = 0xff; // hex
		int num2 = 0370; // octal
		System.out.println("num1 = 0xff = " + ExerciseFive.toBinaryString(num1));
		System.out.println("num2 = 0370 = " + ExerciseFive.toBinaryString(num2));
		System.out.println("Not num1 = " + ExerciseFive.toBinaryString(~num1));
		System.out.println("Not num2 = " + ExerciseFive.toBinaryString(~num2));
		System.out.println(ExerciseFive.toBinaryString(num1) + " And " + ExerciseFive.toBinaryString(num2) + " = " + ExerciseFive.toBinaryString(num1 & num2));
		System.out.println(ExerciseFive.toBinaryString(num1) + " Or " + ExerciseFive.toBinaryString(num2) + " = " + ExerciseFive.toBinaryString(num1 | num2));
		System.out.println(ExerciseFive.toBinaryString(num1) + " Xor " + ExerciseFive.toBinaryString(num2) + " = " + ExerciseFive.toBinaryString(num1 ^ num2));
	}
}