import typeinfo.pets.*;
import java.util.*;

class InterfaceVsIterator {
  public static void display(Iterator<Pet> it) {
    while(it.hasNext()) {
      Pet p = it.next();
      System.out.print(p.id() + ":" + p + " ");
    }
    System.out.println();
  }
  public static void display(Collection<Pet> pets) {
    for(Pet p : pets)
      System.out.print(p.id() + ":" + p + " ");
    System.out.println();
  } 
  public static void main(String[] args) {
    List<Pet> petList = Pets.arrayList(8);
    Set<Pet> petSet = new HashSet<Pet>(petList);
    Map<String,Pet> petMap =
      new LinkedHashMap<String,Pet>();
    String[] names = ("Ralph, Eric, Robin, Lacey, " +
      "Britney, Sam, Spot, Fluffy").split(", ");
    for(int i = 0; i < names.length; i++)
      petMap.put(names[i], petList.get(i));
    display(petList);
    display(petSet);
    display(petList.iterator());
    display(petSet.iterator());
    System.out.println(petMap);
    System.out.println(petMap.keySet());
    display(petMap.values());
    display(petMap.values().iterator());
  } 
}

class CollectionSequence implements Collection<Pet> {
  private Pet[] pets = Pets.createArray(8);
  public int size() { return pets.length; }
  public Iterator<Pet> iterator() {
    return new Iterator<Pet>() {
      private int index = 0;
      public boolean hasNext() {
        return index < pets.length;
      }
      public Pet next() { return pets[index++]; }
      public void remove() { // Not implemented
        throw new UnsupportedOperationException();
      }
    };
  }

  public void clear() {
    if (pets.length > 0) {
      for(Pet p : pets)
        p = null;
    }
  }

  public boolean retainAll(Collection<?> c) { 
    throw new UnsupportedOperationException();
  }

  public boolean removeAll(Collection<?> c) { 
    throw new UnsupportedOperationException();
  }

  public boolean addAll(Collection<? extends Pet> c) { 
    throw new UnsupportedOperationException();
  }

  public boolean contains(Object o) { 
    throw new UnsupportedOperationException();
  }

  public boolean isEmpty() {  
    return (this.size() == 0) ? true : false;
  }

  public boolean containsAll(Collection<?> c) { 
    throw new UnsupportedOperationException();
  }

  public boolean remove(Object o) { 
    throw new UnsupportedOperationException();
  }

  public boolean add(Pet p) { 
    throw new UnsupportedOperationException();
  }

  public Object[] toArray() {
    return pets;
  }

  public <T> T[] toArray(T[] a) {
    throw new UnsupportedOperationException();
  }

  public static void main(String[] args) {
    CollectionSequence c = new CollectionSequence();
    InterfaceVsIterator.display(c);
    InterfaceVsIterator.display(c.iterator());
  }
}

public class ExerciseThirty {
  
  public static void main(String[] args){
    CollectionSequence.main(args);
  }  
}