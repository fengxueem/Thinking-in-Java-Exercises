import java.util.*;

class Stack<T> {
	private LinkedList<T> storage = new LinkedList<T>();
	public void push(T t) { storage.addFirst(t); }
	public T peek() { return storage.getFirst(); }
	public T pop() { return storage.removeFirst(); }
	public boolean isEmpty() { return storage.isEmpty(); }
	public String toString() { return storage.toString(); }
}


public class ExerciseFifteen {
	
	public static void main(String[] args){
		String commands = "+U+n+c---+e+r+t---+a-+i-+n+t+y---+ -+r+u--+l+e+s---";
		char[] commandsInChar = commands.toCharArray();
		Stack<Character> charStack = new Stack<Character>();
		for (int i = 0;i < commandsInChar.length ;i++ ) {
			if (commandsInChar[i] == '+') {
				charStack.push(commandsInChar[++i]);
			}
			if (commandsInChar[i] == '-') {
				System.out.println(charStack.pop());
			}
		}
	}	
}