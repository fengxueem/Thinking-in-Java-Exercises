import java.util.*;
import net.mindview.util.*;

class SList<T> {
  private static class Link<T> {
	  Link<T> next = null;
	  T object = null;
  	Link(T object) {
  	  this.object = object;
  	}
  }
  // singly linked list always ends with an element holding nothing
  private Link<T> head = new Link<T>(null);
  public SList(Collection<T> c) {
  	if(!c.isEmpty()) {
  	  Iterator<T> it = c.iterator();
  	  head.object = it.next();
  	  Link<T> pervious = head;
  	  while(it.hasNext()) {
  	  	Link<T> newOne = new Link<T>(it.next());
  	  	pervious.next = newOne;
  	  	pervious = newOne;
  	  }
  	  // again, last element always holding nothing
  	  pervious.next = new Link<T>(null);
  	}
  }
  public SList() {}
  public static class SListIterator<T> {
  	private Link<T> current;
  	public SListIterator(Link<T> head) {current = head;}
  	public boolean hasNext() {
  	  return current.next != null;
  	}
  	public T next() {
  	  if(!hasNext())
  	  	throw new NoSuchElementException();
  	  T temp = current.object;
  	  current = current.next;
  	  return temp;
  	}
  	public void remove() {
  	  if(current.next != null) {
  	    current.next = current.next.next;
  	  }
  	}
  	public void insert(T t) {
  	  // Can and only can insert new element after
  	  // current element, since it is a singly list
  	  // with only one direction
  	  Link<T> a = new Link<T>(t);
  	  a.next = current.next;
  	  current.next = a;
  	} 
  }
  public String toString() {
  	if(head.next == null) return "[empty]";
  	StringBuilder builder = new StringBuilder("[");
  	SListIterator it = iterator();
  	while(it.hasNext()) {
  	  builder.append(it.next().toString() + (it.hasNext() ? "; " : ""));
  	}
  	builder.append("]");
  	return builder.toString();
  }
  public SListIterator<T> iterator() {
  	return new SListIterator<T>(head);
  }
}

public class ExerciseEight {
  public static void main(String[] args) {
  	// Test 1: print all elements
  	System.out.println(Countries.names(3));
  	SList<String> slist = new SList<String>(Countries.names(3));
  	System.out.println(slist);
  	// Test 2: inset new element
  	SList.SListIterator<String> it = slist.iterator();
  	it.insert("China");
  	System.out.println(it.next());
  	System.out.println(it.next());
  	it.insert("Chinaa");
  	System.out.println(slist);
  	// Test 3: remove element
  	System.out.println("Before remove: \n" + slist);
  	it = slist.iterator();
  	while(it.hasNext()) {
	  it.remove();
	  System.out.println(slist);
  	}
  }
}