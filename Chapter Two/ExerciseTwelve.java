class ExerciseTwelve {
	public static void main(String[] args) {
		int i = 0xf;
		System.out.println("i = " + Integer.toBinaryString(i));
		int length = Integer.toBinaryString(i).length();
		System.out.println("The length of i is " + length);
		i <<= 1;
		length = Integer.toBinaryString(i).length();
		System.out.println("Left shifted i = " + Integer.toBinaryString(i));
		System.out.println("The new length of i is " + length);
		for (int j = 0; j < length - 1; j++) {
			i = i >>> 1;
			System.out.println("Right shifted i = " + Integer.toBinaryString(i));
		}
	}
}