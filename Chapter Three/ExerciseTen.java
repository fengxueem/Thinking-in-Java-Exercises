public class ExerciseTen {

	static int findKilobit (int num) {
		return num / 1000;
	}

	static int findHundreds (int num) {
		return num / 100 % 10;
	}

	static int findDecade (int num) {
		return num / 10 % 10;
	}

	static int findUnit (int num) {
		return num % 10;
	}

	static int buildDecade (int decade, int unit) {
		return 10*decade+unit;
	}

	public static void main(String[] args) {
		for (int num = 1000 ; num < 10000; num++ ) {
			// kilobit + hundreds
			if (buildDecade(findKilobit(num) , findHundreds(num)) * buildDecade(findDecade(num) , findUnit(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findHundreds(num)) + "*" + buildDecade(findDecade(num) , findUnit(num)));
				continue;
			}
			if (buildDecade(findKilobit(num) , findHundreds(num)) * buildDecade(findUnit(num) , findDecade(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findHundreds(num)) + "*" + buildDecade(findUnit(num) , findDecade(num)));
				continue;
			}
			if (buildDecade(findHundreds(num) , findKilobit(num)) * buildDecade(findUnit(num) , findDecade(num)) == num) {
				System.out.println(num + " = " + buildDecade(findHundreds(num) , findKilobit(num)) + "*" + buildDecade(findUnit(num) , findDecade(num)));
				continue;
			}
			if (buildDecade(findHundreds(num) , findKilobit(num)) * buildDecade(findDecade(num) , findUnit(num)) == num) {
				System.out.println(num + " = " + buildDecade(findHundreds(num) , findKilobit(num)) + "*" + buildDecade(findDecade(num) , findUnit(num)));
				continue;
			}
			// kilobit + decade
			if (buildDecade(findKilobit(num) , findDecade(num)) * buildDecade(findHundreds(num) , findUnit(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findDecade(num)) + "*" + buildDecade(findHundreds(num) , findUnit(num)));
				continue;
			}
			if (buildDecade(findKilobit(num) , findDecade(num)) * buildDecade(findUnit(num) , findHundreds(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findDecade(num)) + "*" + buildDecade(findUnit(num) , findHundreds(num)));
				continue;
			}
			if (buildDecade(findDecade(num) , findKilobit(num)) * buildDecade(findUnit(num) , findHundreds(num)) == num) {
				System.out.println(num + " = " + buildDecade(findDecade(num) , findKilobit(num)) + "*" + buildDecade(findUnit(num) , findHundreds(num)));
				continue;
			}
			if (buildDecade(findDecade(num) , findKilobit(num)) * buildDecade(findHundreds(num) , findUnit(num)) == num) {
				System.out.println(num + " = " + buildDecade(findDecade(num) , findKilobit(num)) + "*" + buildDecade(findHundreds(num) , findUnit(num)));
				continue;
			}
			// kilobit + unit
			if (buildDecade(findKilobit(num) , findUnit(num)) * buildDecade(findDecade(num) , findHundreds(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findUnit(num)) + "*" + buildDecade(findDecade(num) , findHundreds(num)));
				continue;
			}
			if (buildDecade(findKilobit(num) , findUnit(num)) * buildDecade(findHundreds(num) , findDecade(num)) == num) {
				System.out.println(num + " = " + buildDecade(findKilobit(num) , findUnit(num)) + "*" + buildDecade(findHundreds(num) , findDecade(num)));
				continue;
			}
			if (buildDecade(findUnit(num) , findKilobit(num)) * buildDecade(findHundreds(num) , findDecade(num)) == num) {
				System.out.println(num + " = " + buildDecade(findUnit(num) , findKilobit(num)) + "*" + buildDecade(findHundreds(num) , findDecade(num)));
				continue;
			}
			if (buildDecade(findUnit(num) , findKilobit(num)) * buildDecade(findDecade(num) , findHundreds(num)) == num) {
				System.out.println(num + " = " + buildDecade(findUnit(num) , findKilobit(num)) + "*" + buildDecade(findDecade(num) , findHundreds(num)));
				continue;
			}
		}
	}
}