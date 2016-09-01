class ExerciseTen {
	public static void main(String[] args) {
		int num1 = 0xff; // hex
		int num2 = 0370; // octal
		System.out.println("num1 = 0xff = " + Integer.toBinaryString(num1));
		System.out.println("num2 = 0370 = " + Integer.toBinaryString(num2));
		System.out.println("Not num1 = " + Integer.toBinaryString(~num1));
		System.out.println("Not num2 = " + Integer.toBinaryString(~num2));
		System.out.println(Integer.toBinaryString(num1) + " And " + Integer.toBinaryString(num2) + " = " + Integer.toBinaryString(num1 & num2));
		System.out.println(Integer.toBinaryString(num1) + " Or " + Integer.toBinaryString(num2) + " = " + Integer.toBinaryString(num1 | num2));
		System.out.println(Integer.toBinaryString(num1) + " Xor " + Integer.toBinaryString(num2) + " = " + Integer.toBinaryString(num1 ^ num2));
	}
}