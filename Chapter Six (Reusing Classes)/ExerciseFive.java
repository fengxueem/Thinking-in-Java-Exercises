class A {
	A() {
		System.out.println("I am A");
	}
}

class B {
	B() {
		System.out.println("I am B");
	}
}

class C extends A {
	B b = new B();
}

public class ExerciseFive {
	
	public static void main(String[] args){
		C c = new C(); // the default constructor of C runs first, then instantiate the fields of C.
	}	
}