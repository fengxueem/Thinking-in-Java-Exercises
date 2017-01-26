import java.util.*;
import net.mindview.util.*;

class SlowSet<E> extends AbstractSet<E> {
  private List<E> values = new ArrayList<E>();
  @Override
  public boolean add(E value) {
    if(!values.contains(value)) {
      values.add(value);
      return true;
    } else
      return false;
  }
  @Override
  public int size() {
    return values.size();
  }
  @Override
  public Iterator<E> iterator() {
    return values.iterator();
  }
  public static void main(String[] args) {
    SlowSet<String> m= new SlowSet<String>();
    m.addAll(Arrays.asList("A","B","C"));
    System.out.println(m);
    m.remove("A");
    System.out.println(m);
    m.removeAll(Arrays.asList("B","C"));
    System.out.println(m + "" + m.isEmpty());
  }
}

public class ExerciseEighteen {
  public static void main(String[] args){
    SlowSet.main(args);    
  }  
}