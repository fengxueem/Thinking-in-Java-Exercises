import java.util.*;

class FixedSizeStack<T> {
  private int index = 0;
  private int size;
  private List<T> storage;
  public FixedSizeStack(int size) {
    storage = new ArrayList<T>();
    this.size = size;
  }
  public void push(T item) {
    if (index < size) {
      storage.add(item);
      index++;
    }
  }
  @SuppressWarnings("unchecked")
  public T pop() {
    if (index > 0) {
      T tmp = storage.get(--index);
      return tmp; 
    } else {
      return null;
    }
  }
}	

class GenericCast {
  public static final int SIZE = 10;
  public static void main(String[] args) {
    FixedSizeStack<String> strings =
      new FixedSizeStack<String>(SIZE);
    for(String s : "A B C D E F G H I J".split(" "))
      strings.push(s);
    for(int i = 0; i < SIZE; i++) {
      String s = strings.pop();
      System.out.print(s + " ");
    }
  }
}

public class ExerciseThirtythree {
  public static void main(String[] args) {
    GenericCast.main(args);
  }  
}