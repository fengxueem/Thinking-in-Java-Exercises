class ExerciseEleven {
	public static void main(String[] args) {
		int i = 0x100;
		System.out.println("i = " + Integer.toBinaryString(i));
		int length = Integer.toBinaryString(i).length();
		System.out.println("" + length);
		for (int j = 0; j < length; j++) {
			i = i >> 1;
			System.out.println("i = " + Integer.toBinaryString(i));
		}
	}
}