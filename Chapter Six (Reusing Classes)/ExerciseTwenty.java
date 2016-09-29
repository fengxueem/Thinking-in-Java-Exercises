/**
* Run this code, read the compile error message and think about why is looks like that.
*/

class WithFinals {
	// Identical to private alone:
	private final void f() { System.out.println("WithFinals.f()"); }
	// Also automatically "final":
	private void g() { System.out.println("WithFinals.g()"); }
} 

class OverridingPrivate extends WithFinals {

	@Override
	private final void f() {
		System.out.println("OverridingPrivate.f()");
	}

	@Override
	private void g() {
		System.out.println("OverridingPrivate.g()");
	}

}

class OverridingPrivate2 extends OverridingPrivate {

	@Override
	public final void f() {
		System.out.println("OverridingPrivate2.f()");
	}

	@Override
	public void g() {
		System.out.println("OverridingPrivate2.g()");
	}

}

public class ExerciseTwenty {
	
	public static void main(String[] args){
		OverridingPrivate2 op2 = new OverridingPrivate2();
		op2.f();
		op2.g();
		// You can upcast:
		OverridingPrivate op = op2;
		// But you can't call the methods:
		//! op.f();
		//! op.f();
		// Same here:
		WithFinals wf = op2;
		//! wf.f();
		//! wf.g();
	}	
}