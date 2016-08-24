import java.util.*;

class ExerciseSeven {
	public static void main(String[] args) {
		System.out.println("Please type any key to start simulation once. Each simulation will present only one result.");
		Random r = new Random();
		Scanner sc = new Scanner(System.in); 
		while (true) {
			String temp = sc.nextLine();
			if (r.nextInt(2) == 1) {
				System.out.println("Coin => Head");			
			} else
				System.out.println("Coin => Tail");			
		}
	}
}