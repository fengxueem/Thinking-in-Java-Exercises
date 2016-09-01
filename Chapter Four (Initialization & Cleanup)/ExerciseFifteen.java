class Dog {
	static int i;
	{
		i = 1;
		System.out.println(i + " instance initialization");
	}
	static {
		System.out.println(i + " static initialization");
	}

	Dog() {
		System.out.println("constructor");
	}
}

public class ExerciseFifteen {
	public static void main(String[] args) {
		Dog d = new Dog();
	}
}
