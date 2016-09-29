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

class Composing {
	private Shared shared;
	private static long counter = 0;
	private final long id = counter++;
	public Composing(Shared shared) {
		System.out.println("Creating " + this);
		this.shared = shared;
		this.shared.addRef();
	}
	protected void dispose() {
		System.out.println("Disposing " + this);
		shared.dispose();
	}
	public String toString() { return "Composing " + id; }
}

public class ExerciseThirteen {
	public static void main(String[] args) {
		Shared shared = new Shared();
		Composing[] composing = { new Composing(shared),
			new Composing(shared), new Composing(shared),
			new Composing(shared), new Composing(shared)			
		}; // aggregate initialization
		for(Composing c : composing)
			c.dispose();
		// Test finilize() by creating a unreferenced Shared object which doesn't meet the termination condition
		new Shared(1);
		System.gc();
	}
}