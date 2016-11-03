import java.util.*;

interface Generater<T> {
	public T next();
}

class CharacterGenerater implements Generater<String> {

	private static final List<String> characterList = new ArrayList<String>(
		Arrays.asList(
			"Tom Cruise","Bruce Lee","Jackie Chan",
			"Patrick J. Adams","Gabriel Macht","Meghan Markle"
			));
	private static int index = 0;

	public String next() {
		if (index == characterList.size()-1) {
			index = 0;
			return characterList.get(characterList.size()-1);
		}
		return characterList.get(index++);
	}
}


public class ExerciseFour {
	
	private static CharacterGenerater characterGenerater = new CharacterGenerater();

	public static Collection fillCollection(Collection c, Generater<String> gen, int num) {
		for(int i = 0; i < num; i++) 
			c.add(gen.next());
		return c;
	}

	public static String[] fillStringArray(String[] s, Generater<String> gen) {
		for (int i = 0;i < s.length ;i++ ) {
			s[i] = gen.next();
		}
		return s;
	}

	public static void main(String[] args){
		System.out.println(fillStringArray(new String[10],characterGenerater));
		System.out.println(fillCollection(new ArrayList(),characterGenerater,5));
		System.out.println(fillCollection(new LinkedList(),characterGenerater,5));
		System.out.println(fillCollection(new HashSet(),characterGenerater,5));
		System.out.println(fillCollection(new LinkedHashSet(),characterGenerater,5));
		System.out.println(fillCollection(new TreeSet(),characterGenerater,5));
	}	
}