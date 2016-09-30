/**
The initialization with inheritance procedure is explained on the page of 273~274.
1.load derived class and base classes
2.static initialization of base class
3.set the memory in the object to binary zero(non-static fields are set to default value)
4.constructors of base class
5.constructor of the derived class
*/
abstract class BaseClass {
	abstract void print();

	BaseClass() {
		print();
	}
}

class DerivedClass extends BaseClass {
	private int i = 1;

	@Override
	void print() {
		System.out.println(i);
	}
}

public class ExerciseThree {
	public static void main(String[] args){
		DerivedClass d = new DerivedClass();
		d.print();
	}	
}