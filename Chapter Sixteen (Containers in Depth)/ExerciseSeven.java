import java.util.*;
import net.mindview.util.*;

public class ExerciseSeven {
  public static void printListByIterator(List list) {
  	Iterator iter = list.iterator();
  	int i = 0;
  	System.out.println("[-----Begin-----]");
  	while(iter.hasNext()) {
  	  System.out.println((++i) + ": " + iter.next());
  	}
  	System.out.println("[------End------]");
  }
  // generic can give you compile time check for the correctness of the
  // elements list a and b are holding
  public static <T> List<T> insertAtEveryOther(List<T> a, List<T> b) {
  	List<T> result = new LinkedList<T>(a);
  	ListIterator<T> it = result.listIterator();
  	Iterator<T> bIterator = b.iterator();
  	while(bIterator.hasNext()) {
  	  it.add(bIterator.next());
  	  it.next();
  	}
  	return result;
  }
  // use listIterator to reverse the list
  public static List reverseList(List a) {
  	List result = new ArrayList();
  	ListIterator it = a.listIterator(a.size());
  	while(it.hasPrevious()) {
  	  result.add(it.previous());
  	}
  	return result;
  }
  public static void main(String[] args) {
  	List<String> arrayList = new ArrayList<String>(Countries.names(5));
  	printListByIterator(arrayList);
  	List<String> linkedList = new LinkedList<String>(Countries.names(5));
  	printListByIterator(linkedList);
  	printListByIterator(
  		insertAtEveryOther(reverseList(arrayList), linkedList));
  }
}