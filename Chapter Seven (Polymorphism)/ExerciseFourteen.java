import java.util.Random;

class Shared {
	private int refcount = 0;
	private static long counter = 0;
	private final long id = counter++;
	public Shared() {
		System.out.println("Creating " + this);
	}
	public Shared(int i) {
		this();
		refcount = i;
	}
	public void addRef() { refcount++; }
	public void showRef() {System.out.println("refcount = " + refcount);}
	protected void dispose() {
		if(--refcount == 0)
			System.out.println("Disposing " + this);
	}
	public String toString() { return "Shared " + id; }

	@Override 
	protected void finalize() {
		if (refcount > 0) {
			System.out.println("Error: Shared " + this + " is in use.");
		}
	}
}

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

	private Shared shared;
	public IntegerHolder i = new IntegerHolder(3);

	public void eat() {}
	public void makeHoles() {}

	public Rodent() {
		System.out.println("Creating Rodent");
	}

	public Rodent(Shared shared) {
		this.shared = shared;
		this.shared.addRef();
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

	public Mouse(Shared shared) {
		super(shared);
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

	public Gerbil(Shared shared) {
		super(shared);
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

	public Hamster(Shared shared) {
		super(shared);
		System.out.println("Creating Hamster");
	}
}

class RandomRodentGenerator {

	private Random rand = new Random(47);
	private Shared shared = new Shared();
	public Rodent next() {
		switch (rand.nextInt(3)) {
			default:
			case 2: return new Mouse(shared);
			case 1: return new Gerbil(shared);
			case 0: return new Hamster(shared);
		}
	}

	public void showSharedRef() {
		shared.showRef();
	}
}

public class ExerciseFourteen {
	
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
		r.showSharedRef();
	}	
}