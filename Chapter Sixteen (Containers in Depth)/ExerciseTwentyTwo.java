import java.util.*;
import net.mindview.util.*;

class SimpleHashMap<K,V> extends AbstractMap<K,V> {
  // Choose a prime number for the hash table
  // size, to achieve a uniform distribution:
  static final int SIZE = 997;
  // You can't have a physical array of generics,
  // but you can upcast to one:
  @SuppressWarnings("unchecked")
  LinkedList<MapEntry<K,V>>[] buckets =
    new LinkedList[SIZE];
  public V put(K key, V value) {
    V oldValue = null;
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null)
      buckets[index] = new LinkedList<MapEntry<K,V>>();
    else System.out.println("Collision Detected");
    LinkedList<MapEntry<K,V>> bucket = buckets[index];
    MapEntry<K,V> pair = new MapEntry<K,V>(key, value);
    boolean found = false;
    ListIterator<MapEntry<K,V>> it = bucket.listIterator();
    while(it.hasNext()) {
      MapEntry<K,V> iPair = it.next();
      if(iPair.getKey().equals(key)) {
        oldValue = iPair.getValue();
        it.set(pair); // Replace old with new
        found = true;
        break;
      }
    }
    if(!found)
      buckets[index].add(pair);
    return oldValue;
  }
  public V get(Object key) {
    int index = Math.abs(key.hashCode()) % SIZE;
    if(buckets[index] == null) return null;
    for(MapEntry<K,V> iPair : buckets[index])
      if(iPair.getKey().equals(key))
        return iPair.getValue();
    return null;
  }
  public Set<Map.Entry<K,V>> entrySet() {
    Set<Map.Entry<K,V>> set= new HashSet<Map.Entry<K,V>>();
    for(LinkedList<MapEntry<K,V>> bucket : buckets) {
      if(bucket == null) continue;
      for(MapEntry<K,V> mpair : bucket)
        set.add(mpair);
    }
    return set;
  }
  @Override
  public void clear() {
    buckets = new LinkedList[SIZE];
  }
  @Override
  public V remove(Object key) {
    if (key != null) {
      int index = Math.abs(key.hashCode()) % SIZE;
      if(buckets[index] == null) return null;
      for (int i = 0; i < buckets[index].size(); i++) {
        MapEntry<K,V> entry = buckets[index].get(i);
        if(entry.getKey().equals(key)) {
          V oldValue = entry.getValue();
          buckets[index].remove(i);
          return oldValue;
        }
      }
    }
    return null;
  }
  public static void main(String[] args) {
    SimpleHashMap<String,String> m =
      new SimpleHashMap<String,String>();
    m.putAll(Countries.capitals(25));
    System.out.println(m);
    m.put("BENIN", "Porto-Novo");
    System.out.println(m.remove("BENIN"));
    System.out.println("After remove(BENIN):" + m);
    m.clear();
    System.out.println("After purge: " + m);
  }
}

public class ExerciseTwentyTwo {
  public static void main(String[] args){
    SimpleHashMap.main(args);    
  }  
}