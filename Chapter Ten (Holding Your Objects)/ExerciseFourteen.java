import java.util.*;

public class ExerciseFourteen {
	
	private LinkedList<Integer> list = new LinkedList<Integer>();

	public void insertIntInMiddle(Integer integer) {
		ListIterator<Integer> it = list.listIterator(list.size()/2);
		it.add(integer);
		System.out.println(list);
	}

	public static void main(String[] args){
		ExerciseFourteen ex = new ExerciseFourteen();
		for (Integer integer : new Integer[]{1,2,3,4,5}) {
			ex.insertIntInMiddle(integer);
		}
	}	
}