class Cleanser {
	private String s = this.getClass().getName(); // reflection
	public void append(String a) { s += a; }
	public void dilute() { append(" dilute()"); }
	public void apply() { append(" apply()"); }
	public void scrub() { append(" scrub()"); }
	public String toString() {return s;}
	public static void main(String[] args) {
		Cleanser c = new Cleanser();
		c.dilute();c.apply();c.scrub();
		System.out.println(c);	
	}
}

class Detergent {
	private Cleanser c = new Cleanser();
	public void append(String a) { c.append(a); }
	public void dilute() { c.append(" dilute()"); }
	public void apply() { c.append(" apply()"); }
	public void scrub() { 
		append(" Detergent.scrub()"); 
		c.scrub();
	}
	public void foam() {
		append(" foam()");
	}
	public String toString() {return c.toString();}
}

public class ExerciseEleven {
	
	public static void main(String[] args){
		Detergent d = new Detergent();
		d.dilute();
		d.apply();
		d.scrub();
		d.foam();
		System.out.println(d);
		System.out.println("Testing base class...");
		Cleanser.main(args);
	}	
}