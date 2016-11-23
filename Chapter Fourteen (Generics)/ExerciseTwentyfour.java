import java.util.*;

interface Factory<T> {
  T create();
}
class NoClassFoundInDatabaseException extends Exception {
  NoClassFoundInDatabaseException(String msg) {
  	super(msg);
  }
}
class Building {
  public static class ConstructionFactory implements Factory<Building> {
  	public Building create() {
  	  return new Building();
  	}
  }
}
class House extends Building {
  public static class ConstructionFactory implements Factory<House> {
  	public House create() {
  	  return new House();
  	}
  }
}

class ClassTypeCapture<T> {
  Class<T> kind;
  Map<String,Factory> kindMap = new HashMap<String,Factory>();
  public ClassTypeCapture() {}
  public ClassTypeCapture(Class<T> kind) {
  	this.kind = kind;
  	// kindMap.put(kind.getSimpleName(), kind);
  }
  public boolean f(Object arg) {
  	return kind.isInstance(arg);
  }
  public void addType(String typeName, Factory factory) {
  	kindMap.put(typeName, factory);
  }
  public Object createNew(String typeName) throws NoClassFoundInDatabaseException {
  	if (kindMap.containsKey(typeName)) {
  	  try {
  	  	return kindMap.get(typeName).create();
  	  } catch (Exception err) {
		err.printStackTrace();
  	  }
  	}
  	throw new NoClassFoundInDatabaseException("the class " + typeName + 
  		" is not found in database.");
  }
  public static void main(String[] args) throws Exception {
  	ClassTypeCapture<Building> ctt1 = new ClassTypeCapture<Building>(Building.class);
  	System.out.println(ctt1.f(new Building()));
  	System.out.println(ctt1.f(new House()));

  	ClassTypeCapture<House> ctt2 = new ClassTypeCapture<House>(House.class);
  	System.out.println(ctt2.f(new Building()));
  	System.out.println(ctt2.f(new House()));

  	ctt1.addType("House", new House.ConstructionFactory());
  	System.out.println(ctt1.createNew("House").getClass().getName());
  	// produce a NoClassFoundInDatabaseException error
  	ctt1.createNew("house");
  }
}

public class ExerciseTwentyfour {
  public static void main(String[] args) throws Exception {
  	ClassTypeCapture.main(args);
  }
}