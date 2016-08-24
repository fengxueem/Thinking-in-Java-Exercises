class Dog {
		String name = ""; // String is no primitive type! It has no default value.
		String says = "";

		public boolean equals( Dog d ) {
			if (d.name.equals(this.name) && d.says.equals(this.says)) {
				return true;
			}
			return false;
		}
}

class ExerciseSix {

	public static void main(String[] args) {
		Dog d1 = new Dog();
		Dog d2 = new Dog();
		d1.name = "spot";
		d1.says = "Ruff!";
		d2.name = "scruffy";
		d2.says = "Wurf!";
		System.out.println(d1.name + " says: " + d1.says);
		System.out.println(d2.name + " says: " + d2.says);
		Dog d3 = new Dog();
		d1 = d3;
		if (d1 == d2) {
			System.out.println("d1 == d2");
		}
		if (d1.equals(d2)) {
			System.out.println("d1 equals d2");
		}
		if (d1 == d3) {
			System.out.println("d1 == d3");
		}
		if (d1.equals(d3)) {
			System.out.println("d1 equals d3");
		}
		if (d2 == d3) {
			System.out.println("d2 == d3");
		}
		if (d2.equals(d3)) {
			System.out.println("d2 equals d3");
		}
	}
	
}