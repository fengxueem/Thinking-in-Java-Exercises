abstract class BaseClass {}


class DerivedClass extends BaseClass {
	public void haha() {
		System.out.println("haha");
	}
}

abstract class BaseClassWithMethod {
	abstract void haha();
}

class DerivedClassWithMethod extends BaseClassWithMethod {
	@Override
	public void haha() {
		System.out.println("haha");
	}
}

public class ExerciseFour {
	
	public static void callHaha(BaseClass b) {
		((DerivedClass)b).haha();
	}

	public static void callHahaWithMethod(BaseClassWithMethod b) {
		b.haha();
	}

	public static void main(String[] args){
		DerivedClass d = new DerivedClass();
		callHaha(d);
		DerivedClassWithMethod dd = new DerivedClassWithMethod();
		callHahaWithMethod(dd);
	}	
}