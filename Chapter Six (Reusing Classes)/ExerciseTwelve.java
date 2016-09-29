class Component1 {
	Component1() {System.out.println("Component1");}
	void dispose() {
		System.out.println("Component1 dispose");
	}
}

class Component2 {
	Component2() {System.out.println("Component2");}
	void dispose() {
		System.out.println("Component2 dispose");
	}
}

class Component3 {
	Component3() {System.out.println("Component3");}
	void dispose() {
		System.out.println("Component3 dispose");
	}
}

class Root {
	Component1 c1 = new Component1();
	Component2 c2 = new Component2();
	Component3 c3 = new Component3();
	Root() {System.out.println("Root");}
	void dispose(){
		System.out.println("Root dispose");
		c1.dispose();
		c2.dispose();
		c3.dispose();
	}
}

class Stem extends Root {
	Component1 c1Stem = new Component1();
	Component2 c2Stem = new Component2();
	Component3 c3Stem = new Component3();
	Stem() {System.out.println("Stem");}
	void dispose() {
		System.out.println("Stem dispose");
		c1Stem.dispose();
		c2Stem.dispose();
		c3Stem.dispose();
		super.dispose();
	}
}

public class ExerciseTwelve {
	
	public static void main(String[] args){
		Stem s = new Stem();
		s.dispose();
	}	
}