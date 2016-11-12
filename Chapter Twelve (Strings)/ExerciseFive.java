import java.util.*;

public class ExerciseFive {
	
	public static void main(String[] args){
		Formatter f = new Formatter(System.out);
		char c = 'a';
		System.out.println("Conversions for char: 'a'");
		f.format("%1$-5b %1$-5B %1$-5h %1$-5H %1$-5s %1$-5S %1$-5c %1$-5C ",c);

		int i = 97;
		System.out.println("\nConversions for int: 97");
		f.format("%1$-5b %1$-5B %1$-5h %1$-5H %1$-5s %1$-5S %1$-5c %1$-5C %1$-5d %1$-5o %1$-5x %1$-5X",i);

		double d = 97.07;
		System.out.println("\nConversions for floating number: 97.07");
		f.format("%1$-5b %1$-5B %1$-5h %1$-5H %1$-5s %1$-5S %1$-5e %1$-5E %1$-5g %1$-5G %1$-5a %1$-5A",d);
	}	
}