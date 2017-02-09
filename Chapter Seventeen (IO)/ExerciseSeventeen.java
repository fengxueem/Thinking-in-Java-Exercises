import net.mindview.util.*;
import java.util.*;

public class ExerciseSeventeen {
  public static void main(String[] args) {
  	TextFile text = new TextFile("ExerciseSeventeen.java", "\\W+");
  	HashMap<Character, Integer> map = new HashMap<Character, Integer>();
  	for (String word : text) {
  	  char[] charArray = word.toCharArray();
  	  for (char character : charArray) {
  	  	if (!map.containsKey(character)) {
  	  	  map.put(character, 1);
  	  	} else {
  	  	  int i = map.get(character).intValue();
  	  	  map.put(character, ++i);
  	  	}
  	  }
  	}
  	System.out.println(map);
  }
}