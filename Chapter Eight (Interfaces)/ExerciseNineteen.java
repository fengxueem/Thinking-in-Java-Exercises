import java.util.Random;

interface Tosser {
	public void tossing();
}

interface TosserFactory {
	public Tosser getTosser();
}

class CoinTosser implements Tosser {

	public CoinTosser() {}

	public void tossing() {
		Random r = new Random();
		if ((r.nextInt(2) % 2) == 0) {
			System.out.println("Heads");
		} else {
			System.out.println("Tails");
		}
	}

}

class CoinTosserFactory implements TosserFactory {
	public Tosser getTosser() {
		return new CoinTosser();
	}
}

class DiceTosser implements Tosser {

	public DiceTosser() {}

	public void tossing() {
		Random r = new Random();
		System.out.println(r.nextInt(6)+1);
	}

}

class DiceTosserFactory implements TosserFactory {
	public Tosser getTosser() {
		return new DiceTosser();
	}
}

public class ExerciseNineteen {
	
	public void tryTossing(TosserFactory f) {
		f.getTosser().tossing();
	}

	public static void main(String[] args){
		ExerciseNineteen e = new ExerciseNineteen();
		e.tryTossing(new DiceTosserFactory());
		e.tryTossing(new CoinTosserFactory());
	}	
}