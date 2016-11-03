import java.util.*;

public class ExerciseEighteen {
	
	public static void main(String[] args){
		Map<String,String> map = new HashMap<String,String>();
		map.put("Mark", "A");
		map.put("Fu", "B");
		map.put("Smelly", "C");
		map.put("Girl", "D");
		System.out.println("Origin map: " + map);
		Set<String> sortedKeys = new TreeSet<String>(map.keySet());
		Map<String,String> sortedMap = new LinkedHashMap<String,String>();
		for(String integer : sortedKeys) {
			System.out.print("Adding " + integer + ", ");
			sortedMap.put(integer, map.get(integer));			
		}
		System.out.println("\nSorted map: " + sortedMap);
	}	
}