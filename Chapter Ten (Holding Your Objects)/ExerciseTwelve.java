import java.util.*;


public class ExerciseTwelve {
	
	public static void main(String[] args){
		List<Integer> listOne = Arrays.asList(1,2,3,4);		
		List<Integer> listTwo = new ArrayList<Integer>(listOne);
		ListIterator<Integer> it = listOne.listIterator();
		while (it.hasNext()){
			listTwo.set(listTwo.size() - it.nextIndex() - 1, it.next());
		}
		System.out.println(listTwo);
	}	
}