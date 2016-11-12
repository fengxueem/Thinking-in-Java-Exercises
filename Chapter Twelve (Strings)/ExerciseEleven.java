import java.util.regex.*;

public class ExerciseEleven {
	
	public static void main(String[] args){
		String s = "Arline ate eight apples and one orange while Anita hadn't any";
		// (?i) ==  Pattern.compile("((^[aeiou])|(\\s+[aeiou]))\\w+[aeiou]\\b", Pattern.CASE_INSENSITIVE);
		Pattern p = Pattern.compile("(?i)((^[aeiou])|(\\s+[aeiou]))\\w+[aeiou]\\b");
		Matcher m = p.matcher(s);
		while (m.find()){
			System.out.println("Match \"" + m.group() + "\" at " + m.start());
		}
	}	
}