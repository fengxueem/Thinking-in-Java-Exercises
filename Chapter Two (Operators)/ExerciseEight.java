class ExerciseEight {
	public static void main(String[] args) {
		long l = 0xffl; // hex
		long lng = 0377l; // octal
		System.out.println("l = 0xffl = " + Long.toBinaryString(l));
		System.out.println("lng = 0377 = " + Long.toBinaryString(lng));
	}
}