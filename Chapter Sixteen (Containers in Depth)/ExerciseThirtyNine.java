//: containers/SimpleHashMap.java
// A demonstration hashed Map.
import java.util.*;
import net.mindview.util.*;

class SimpleHashMap<K,V> extends AbstractMap<K,V> {
  // Choose a prime number for the hash table
  // size, to achieve a uniform distribution:
  private static final int SIZE = 997;
  private double loadFactor = 0.0;
  private int size = 0;
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
    loadFactor = (double)(++size) / buckets.length;
    if (loadFactor > 0.75) rehash();
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
  private void rehash() {
    LinkedList<MapEntry<K,V>>[] tempBuckets = buckets;
    buckets = new LinkedList[firstPrimeAfter(2 * buckets.length)];
    size = 0;
    for (LinkedList<MapEntry<K,V>> bucket : tempBuckets) {
      if(bucket == null) continue;
      for(MapEntry<K,V> mpair : bucket)
        put(mpair.getKey(), mpair.getValue());
    }
  }
  private int firstPrimeAfter(int n) {
    for(int i = n + 1; i > n; i++) {
      int j = 2;
      for(; j < Math.sqrt(i); j++) {
        if((i % j) == 0) break;
      }
      if(j >= Math.sqrt(i)) return i;
    }
    return 0;
  }
  public static void main(String[] args) {
    SimpleHashMap<String,String> m =
      new SimpleHashMap<String,String>();
    m.putAll(Countries.capitals(25));
    System.out.println(m);
    System.out.println(m.get("ERITREA"));
    System.out.println(m.entrySet());
  }
}

public class ExerciseThirtyNine {
  public static void main(String[] args){
    SimpleHashMap<Integer, Integer> map = new SimpleHashMap<Integer, Integer>();
    for(int j = 0; j < 996; j++)
      map.put(j, j);
    System.out.println(map.entrySet().size());
  }
}