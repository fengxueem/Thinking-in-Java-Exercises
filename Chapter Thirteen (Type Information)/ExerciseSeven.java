import static net.mindview.util.Print.*;

class Candy {
  static { print("Loading Candy"); }
}

class Gum {
  static { print("Loading Gum"); }
}

class Cookie {
  static { print("Loading Cookie"); }
}

public class ExerciseSeven {
  public static void main(String[] args) {	
    if (args.length != 1) {
      print("java ExerciseSeven type");
      System.exit(0);
    }
    try {
      Class c = Class.forName(args[0]);
    } catch(ClassNotFoundException e) {
      print("cannot find class " + args[0]);
    }
  }
}