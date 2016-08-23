class ExerciseNine {

	// forward autoboxing
	Boolean b = true;
	Character c = 'c';
	Byte by = 1;
	Short s = 1;
	Integer i = 1;
	Long l = 1L;
	Float f = 1.0f;
	Double d = 1.0d;

	// backward autoboxing
	boolean bo = b;
	char ch = c;
	byte byt = by;
	short sh = s;
	int in = i;
	long lo = l;
	float fl = f;
	double dou = d;

	public static void main(String[] args) {
		ExerciseNine e = new ExerciseNine();
		System.out.println("Boolean:" + e.b + "; boolean:" + e.bo);
		System.out.println("Character:" + e.c + "; char:" + e.ch);
		System.out.println("Byte:" + e.by + "; byte:" + e.byt);
		System.out.println("Short:" + e.s + "; short:" + e.sh);
		System.out.println("Integer:" + e.i + "; int:" + e.in);
		System.out.println("Long:" + e.l + "; long:" + e.lo);
		System.out.println("Float:" + e.f + "; float:" + e.fl);
		System.out.println("Double:" + e.d + "; double:" + e.dou);
	}

}