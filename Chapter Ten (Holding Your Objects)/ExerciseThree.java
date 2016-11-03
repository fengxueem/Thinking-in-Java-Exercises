import java.util.*;

interface Selector {
	boolean end();
	Object current();
	void next();
}

// class Sequence {
// 	private Object[] items;
// 	private int next = 0;
// 	public Sequence(int size) {items = new Object[size];}
// 	public void add(Object x) {
// 		if (next < items.length) 			
// 		items[next++] = x;
// 	}
// 	private class SequenceSelector implements Selector {
// 		private int i = 0;
// 		public boolean end() {return i == items.length;}
// 		public Object current() {return items[i];}
// 		public void next() {if (i < items.length) i++;}	
// 	}
// 	public Selector selector() {
// 		return new SequenceSelector();
// 	}
// 	public static void main(String[] args) {
// 		Sequence sequence = new Sequence(10);
// 		for(int i = 0; i < 10; i++)
// 			sequence.add(Integer.toString(i));
// 		Selector selector = sequence.selector();
// 		while(!selector.end()) {
// 			System.out.print(selector.current() + " ");
// 			selector.next();
// 		}
// 	}
// }

class ModifiedSequence {
	private List<Object> items;
	private int next = 0;
	public ModifiedSequence() {items = new ArrayList<Object>();}
	public void add(Object x) {
		items.add(x);
	}
	private class SequenceSelector implements Selector {
		private int i = 0;
		public boolean end() {return i == items.size();}
		public Object current() {return items.get(i);}
		public void next() {i++;}	
	}
	public Selector selector() {
		return new SequenceSelector();
	}
	public static void main(String[] args) {
		ModifiedSequence modifiedSequence = new ModifiedSequence();
		for(int i = 0; i < 10; i++)
			modifiedSequence.add(Integer.toString(i));
		Selector selector = modifiedSequence.selector();
		while(!selector.end()) {
			System.out.print(selector.current() + " ");
			selector.next();
		}
	}
}

public class ExerciseThree {
	
	public static void main(String[] args){
		ModifiedSequence.main(args);		
	}	
}