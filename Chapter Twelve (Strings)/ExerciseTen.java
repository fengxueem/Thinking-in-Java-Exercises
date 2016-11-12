import java.util.*;
import java.util.regex.*;

public class ExerciseTen {
	
	public static void main(String[] args){
		String s = "Java now has regular expressions";
		List<String> regexs = new ArrayList<String>(Arrays.asList(
			"^Java","\\Breg.*","n.w\\s+h(a|i)s","s?","s*","s{4}","s{1}.","s{0,3}")); 
		for(String regex : regexs) {
			System.out.println("Regular expression: \"" + regex + "\"");
			Pattern p = Pattern.compile(regex);
			Matcher m = p.matcher(s);
			if(!m.find()) 
			  	System.out.println("No match found for " + "\"" + regex + "\"");
			m.reset();
			while(m.find()) {
				System.out.println("Match \"" + m.group() + "\" at position " +
					m.start() + ((m.end() - m.start() < 2) ? "" :  ("-" + (m.end() - 1))));
			}
		}		
	}	
}