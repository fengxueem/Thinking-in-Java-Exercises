import java.util.*;

class ModifiedSequence {
  private Object[] items;
  private int next = 0;
  public ModifiedSequence(int size) { items = new Object[size]; }
  public void add(Object x) {
    if(next < items.length)
      items[next++] = x;
  }
  private class SequenceIterator implements Iterator<Object> {
    private int i = 0;
    public boolean hasNext() { return i != items.length; }
    public Object next() {
      if(i < items.length)
        return items[i++];
      else throw new NoSuchElementException();
    }
    public void remove() {
      List<Object> temp = new LinkedList(Arrays.asList(items));
      temp.remove(items[--i]);
      items = temp.toArray();
    }
  }
  public Iterator iterator() {
    return new SequenceIterator();
  }	
  public static void main(String[] args) {
    ModifiedSequence sequence = new ModifiedSequence(15);
    for(int i = 0; i < 15; i++)
      sequence.add(Integer.toString(i));
    Iterator iterator = sequence.iterator();
    while(iterator.hasNext()) {
      System.out.print(iterator.next() + " ");
      iterator.remove();
    }
    System.out.println("\nAfter remove");
    iterator = sequence.iterator();
    while(iterator.hasNext()) {
      System.out.print(iterator.next() + " ");
    }
  }
}

public class ExerciseNine {
  
  public static void main(String[] args){
    ModifiedSequence.main(args);  
  }  
}