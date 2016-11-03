import java.util.*;

class SomeClass {
	private String id;

	public SomeClass(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return id;
	}
}

public class ExerciseSeven {
	
	public static void main(String[] args){
		SomeClass[] s = new SomeClass[]{
			new SomeClass("wedder"), new SomeClass("zabbe"),
			new SomeClass("arschkirzler"), new SomeClass("Smelly Girl")
		};
		List<SomeClass> list = new ArrayList(Arrays.asList(s));
		System.out.println("1: whole list " + list);
		List<SomeClass> sub = list.subList(1,3);
		System.out.println("2: sub list " + sub);
		list.removeAll(sub);
		System.out.println("3: after remove " + list);
	}	
}