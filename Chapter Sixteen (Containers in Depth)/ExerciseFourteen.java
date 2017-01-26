//: containers/Maps.java
// Things you can do with Maps.
import java.util.concurrent.*;
import java.util.*;
import net.mindview.util.*;
import static net.mindview.util.Print.*;

class Maps {
  public static void printKeys(Map<Object, Object> map) {
    printnb("Size = " + map.size() + ", ");
    printnb("Keys: ");
    print(map.keySet()); // Produce a Set of the keys
  }
  public static void test(Map<Object, Object> map) {
    print(map.getClass().getSimpleName());
    map.putAll(new CountingMapData(25));
    // Map has 'Set' behavior for keys:
    map.putAll(new CountingMapData(25));
    printKeys(map);
    // Producing a Collection of the values:
    printnb("Values: ");
    print(map.values());
    print(map);
    print("map.containsKey(11): " + map.containsKey(11));
    print("map.get(11): " + map.get(11));
    print("map.containsValue(\"F0\"): "
      + map.containsValue("F0"));
    Object key = map.keySet().iterator().next();
    print("First key in map: " + key);
    map.remove(key);
    printKeys(map);
    map.clear();
    print("map.isEmpty(): " + map.isEmpty());
    map.putAll(new CountingMapData(25));
    // Operations on the Set change the Map:
    map.keySet().removeAll(map.keySet());
    print("map.isEmpty(): " + map.isEmpty());
  }
  public static void main(String[] args) {
    // Properties is also a map! Because of these:
    // public class Properties extends Hashtable<Object, Object>
    // public class Hashtable<K, V> extends Dictionary<K, V>
    //    implements Map<K, V>, Cloneable, Serializable
    // Note that it maps object to object
    Map<Object, Object> properties = new Properties();
    test(new Properties());
  }
}

public class ExerciseFourteen {
  public static void main(String[] args) {
    Maps.main(args);
  }
}