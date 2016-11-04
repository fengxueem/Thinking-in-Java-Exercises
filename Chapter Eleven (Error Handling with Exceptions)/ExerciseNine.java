class OneException extends Exception {}
class TwoException extends Exception {}
class ThreeException extends Exception {}


public class ExerciseNine {
	
	public void throwExceptions(int x) throws OneException, TwoException, ThreeException {
		if(x < 0) throw new ThreeException();
		if(x == 0) throw new TwoException();
		if(x > 0) throw new OneException();
	}

	public static void main(String[] args){
		ExerciseNine e = new ExerciseNine();
		try {
			e.throwExceptions(-1);
			e.throwExceptions(0);
			e.throwExceptions(1);
		} catch(Exception err) {
			err.printStackTrace();
		}
	}	
}