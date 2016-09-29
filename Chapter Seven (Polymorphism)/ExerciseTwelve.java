import java.util.Random;

class IntegerHolder {
	private int i;

	public IntegerHolder( int i ) {
		this.i = i;
		System.out.println(i);
	}

	public String toString() {
		return Integer.toString(i);
	}
}

class Rodent {

	public IntegerHolder i = new IntegerHolder(3);

	public void eat() {}
	public void makeHoles() {}
	public Rodent() {
		System.out.println("Creating Rodent");
	}
}

class Mouse extends Rodent {

	public IntegerHolder i = new IntegerHolder(2);

	@Override
	public void eat() {
		System.out.println("Mouse.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Mouse.makeHoles()");
	}

	public Mouse() {
		System.out.println("Creating Mouse");
	}
}

class Gerbil extends Rodent {

	public IntegerHolder i = new IntegerHolder(1);

	@Override
	public void eat() {
		System.out.println("Gerbil.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Gerbil.makeHoles()");
	}

	public Gerbil() {
		System.out.println("Creating Gerbil");
	}
}

class Hamster extends Rodent {

	public IntegerHolder i = new IntegerHolder(0);

	@Override
	public void eat() {
		System.out.println("Hamster.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Hamster.makeHoles()");
	}

	public Hamster() {
		System.out.println("Creating Hamster");
	}
}

class RandomRodentGenerator {

	private Random rand = new Random(47);

	public Rodent next() {
		switch (rand.nextInt(3)) {
			default:
			case 2: return new Mouse();
			case 1: return new Gerbil();
			case 0: return new Hamster();
		}
	} 
}

public class ExerciseTwelve {
	
	public static void main(String[] args){
		RandomRodentGenerator r = new RandomRodentGenerator();
		Rodent[] rodents = new Rodent[3];
		for ( int i = 0 ; i < rodents.length ; i++ ) {
			rodents[i] = r.next();
		}
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		Mouse m = new Mouse();
		System.out.println(m.i);
		System.out.println(((Rodent)m).i);
		System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~");
		Rodent rm = new Mouse();
		System.out.println(rm.i);
		System.out.println(((Mouse)rm).i);
	}	
}