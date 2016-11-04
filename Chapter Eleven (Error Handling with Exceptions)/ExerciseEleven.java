class OneException extends Exception {}

public class ExerciseEleven {
	
	public void g() throws OneException {
		throw new OneException();
	}

	// even you explicitly note this method throws RuntimeException, the compiler
	// won't tell you to try to catch the RuntimeException when you call this method.
	public void f() throws RuntimeException { 
		try {
			g();
		} catch(Exception e) {
			// TwoException t = new TwoException();
			// t.initCause(e);
			// throw t;
			// We can pass a cause into the RuntimeException constructor
			// Only Error, Exception & RuntimeException can do this.
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args){
		ExerciseEleven e = new ExerciseEleven();
		e.f();
	}	
}