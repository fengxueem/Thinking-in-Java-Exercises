class BaseClass {

	public void firstMethod() {
		System.out.println("Base Class -> First Method");
		secondMethod();
	}

	public void secondMethod() {
		System.out.println("Base Class -> Second Method");
	}
}

class DerivedClass extends BaseClass{

	@Override
	public void secondMethod() {
		System.out.println("Derived Class -> Second Method");
	}
}


public class ExerciseTen {
	
	public static void main(String[] args){
		BaseClass b = new DerivedClass();
		b.firstMethod();		
	}	
}