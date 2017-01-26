import java.util.*;

// First of all, this set only accepts objects implemented with java.util.Comparable interface
class HomemadeSortedSet<E> extends LinkedList<E> implements SortedSet<E> {
  @SuppressWarnings("unchecked") // to avoid Comparable<Comparable<Comparable<...>>>
  private static final Comparator<Comparable> NATURAL_ORDER = new Comparator<Comparable>() {
    public int compare(Comparable a, Comparable b) {
      return a.compareTo(b);
    }
  };
  Comparator<? super E> comparator;
  @SuppressWarnings("unchecked")
  public HomemadeSortedSet() {
    this.comparator = (Comparator<? super E>) NATURAL_ORDER;
  }
  @SuppressWarnings("unchecked")
  public HomemadeSortedSet(Comparator<? super E> comp) {
  	if (comparator != null) {
      this.comparator = comparator;
    } else {
      this.comparator = (Comparator<? super E>) NATURAL_ORDER;
    }
  }
  public HomemadeSortedSet(List<E> list, Comparator<? super E> comp) {
  	this(comp);
    Iterator<E> it = list.iterator();
    while (it.hasNext()) {
      add(it.next());
    }
  }
  public Comparator<? super E> comparator() {
  	return comparator;
  }
  public E first() {
  	return getFirst();
  }
  public E last() {
  	return getLast();
  }
  public SortedSet<E> subSet(E fromElement, E toElement) {
  	return new HomemadeSortedSet<E>(subList(indexOf(fromElement), indexOf(toElement) + 1), comparator);
  }
  public SortedSet<E> headSet(E toElement) {
   return subSet(first(), toElement);
  }
  public SortedSet<E> tailSet(E fromElement) {
   return subSet(fromElement, last());
  }
  public boolean add(E e) {
  	// only add new element
  	if (!contains(e)) {
  	  Iterator<E> listIt = this.iterator();
	  int index = 0;
	  while(listIt.hasNext()) {
		if(comparator.compare(listIt.next(), e) < 1) 
		  index++;
	  }
	  add(index, e);
	  return true;
  	}
  	return false;
  }
}

public class ExerciseTen {
  public static void main(String[] args) {
  	HomemadeSortedSet<Integer> set = new HomemadeSortedSet<Integer>();
  	set.add(1);
  	set.add(3);
  	set.add(2);
  	System.out.println("Whole set: " + set);
  	System.out.println("First element: " + set.first());
  	System.out.println("Last element: " + set.last());
  	System.out.println("subSet() test: " + set.subSet(2,3));
  	System.out.println("tailSet() test: " + set.tailSet(2));
  	System.out.println("headSet() test: " + set.headSet(2));
  }
}