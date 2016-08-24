class Dog {
		String name;
		String says;
}

class ExerciseFive {

	public static void main(String[] args) {
		Dog d1 = new Dog();
		Dog d2 = new Dog();
		d1.name = "spot";
		d1.says = "Ruff!";
		d2.name = "scruffy";
		d2.says = "Wurf!";
		System.out.println(d1.name + " says: " + d1.says);
		System.out.println(d2.name + " says: " + d2.says);
	}
	
}