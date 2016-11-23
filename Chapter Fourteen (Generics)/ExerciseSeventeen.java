import java.util.*;

enum Day {MON, TUE, WED, THU, FRI, SAT, SUN}
enum Weekday {}

class Sets {
  public static <T> Set<T> union(Set<T> a, Set<T> b) {
    Set<T> result;
    if (a instanceof EnumSet) {
      System.out.println("found EnumSet");
      result = ((EnumSet)a).clone();
    } else {
      result = new HashSet<T>(a);
    }
    result.addAll(b);
    return result;
  }
  public static <T>
  Set<T> intersection(Set<T> a, Set<T> b) {
    Set<T> result;
    if (a instanceof EnumSet) {
      System.out.println("found EnumSet");
      result = ((EnumSet)a).clone();
    } else {
      result = new HashSet<T>(a);
    }
    result.retainAll(b);
    return result;
  } 
  // Subtract subset from superset:
  public static <T> Set<T>
  difference(Set<T> superset, Set<T> subset) {
    Set<T> result;
    if (superset instanceof EnumSet) {
      System.out.println("found EnumSet");
      result = ((EnumSet)superset).clone();
    } else {
      result = new HashSet<T>(superset);
    }
    result.removeAll(subset);
    return result;
  }
  // Reflexive--everything not in the intersection:
  public static <T> Set<T> complement(Set<T> a, Set<T> b) {
    return difference(union(a, b), intersection(a, b));
  }
} ///:~

public class ExerciseSeventeen {
  static void f(Set<Day> a, Set<Day> b) {
    Set<Day> union = Sets.union(a, b);
    Set<Day> intersect = Sets.intersection(a, b);
    Set<Day> diff = Sets.difference(a, b);
    Set<Day> complement = Sets.complement(a, b);
    System.out.println("union:" + union);
    System.out.println("intersection:" + intersect);
    System.out.println("difference:" + diff);
    System.out.println("complement:" + complement);
  }
  public static void main(String[] args){
    Set<Day> weekdays = EnumSet.range(Day.MON, Day.FRI);     
    Set<Day> weekends = EnumSet.range(Day.SAT, Day.SUN);     
    f(weekdays, weekends);
    System.out.println();
    Set<Day> hashWeekdays = new HashSet<Day>(weekdays);
    Set<Day> hashWeekends = new HashSet<Day>(weekends);
    f(hashWeekdays, hashWeekends);
  }  
}