import java.util.Random;

class Rodent {
	public void eat() {}
	public void makeHoles() {}
}

class Mouse extends Rodent {

	@Override
	public void eat() {
		System.out.println("Mouse.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Mouse.makeHoles()");
	}
}

class Gerbil extends Rodent {

	@Override
	public void eat() {
		System.out.println("Gerbil.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Gerbil.makeHoles()");
	}
}

class Hamster extends Rodent {

	@Override
	public void eat() {
		System.out.println("Hamster.eat()");
	}
	
	@Override
	public void makeHoles() {
		System.out.println("Hamster.makeHoles()");
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

public class ExerciseNine {
	
	public static void main(String[] args){
		RandomRodentGenerator r = new RandomRodentGenerator();
		Rodent[] rodents = new Rodent[20];
		for ( int i = 0 ; i < rodents.length ; i++ ) {
			rodents[i] = r.next();
		}
		for (Rodent rodent : rodents) {
			rodent.eat();
		}
	}	
}