class OneException extends Exception {}
class TwoException extends Exception {}


public class ExerciseTen {
	
	public void g() throws OneException {
		throw new OneException();
	}

	public void f() throws TwoException {
		try {
			g();
		} catch(Exception e) {
			TwoException t = new TwoException();
			t.initCause(e);
			throw t;
		}
	}

	public static void main(String[] args){
		ExerciseTen e = new ExerciseTen();
		try {
			e.f();
		} catch(Exception err) {
			// err.fillInStackTrace().printStackTrace();
			err.printStackTrace(System.out);
		}
	}	
}