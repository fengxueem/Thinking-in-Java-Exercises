class BaseClass {
	public BaseClass() {
		System.out.println("Inside Base Class Constructor");
	}
}

class DerivedClass extends BaseClass{
	public DerivedClass() {
		System.out.println("Inside Derived Class Constructor");
	}
}

public class ExerciseFour {

	public static void main(String[] args){
		System.out.println("Creating an object of DerivedClass..");
		DerivedClass d = new DerivedClass();
		System.out.println("Creating an object of BaseClass..");
		BaseClass b = new BaseClass();
	}	
}