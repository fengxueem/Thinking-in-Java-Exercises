import java.util.*;

class NoClassFoundInDatabaseException extends Exception {
  NoClassFoundInDatabaseException(String msg) {
  	super(msg);
  }
}
class Building {}
class House extends Building {}

class ClassTypeCapture<T> {
  Class<T> kind;
  Map<String,Class<?>> kindMap = new HashMap<String,Class<?>>();
  public ClassTypeCapture() {}
  public ClassTypeCapture(Class<T> kind) {
  	this.kind = kind;
  	kindMap.put(kind.getSimpleName(), kind);
  }
  public boolean f(Object arg) {
  	return kind.isInstance(arg);
  }
  public void addType(String typeName, Class<?> kind) {
  	kindMap.put(typeName, kind);
  }
  public Object createNew(String typeName) throws NoClassFoundInDatabaseException {
  	if (kindMap.containsKey(typeName)) {
  	  try {
  	  	return kindMap.get(typeName).newInstance();
  	  } catch (IllegalAccessException err) {
  	  	System.out.println("the class" + typeName + 
  	  		"or its nullary constructor is not accessible.");
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

  	ctt1.addType("House", House.class);
  	System.out.println(ctt1.createNew("House").getClass().getName());
  	// produce a NoClassFoundInDatabaseException error
  	ctt1.createNew("house");
  }
}

public class ExerciseTwentyone {
  public static void main(String[] args) throws Exception {
  	ClassTypeCapture.main(args);
  }
}