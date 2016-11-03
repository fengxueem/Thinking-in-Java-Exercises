import java.util.*;

public class ExerciseTwentyEight {
	
	public static void main(String[] args){
		Queue<Double> doubleQueue = new PriorityQueue<Double>();	
		Random rand = new Random(13);
		for (int i = 0 ;i < 10 ;i++ ) {
			doubleQueue.offer(rand.nextDouble());
		}
		System.out.println("New queue looks like: " + doubleQueue + " whose size is: " + doubleQueue.size());
		System.out.println("Start polling!");
		// method 1:
		// int size = doubleQueue.size(); 
		// for (int i = 0 ;i < size ;i++ ) {
		// 	System.out.println("i: " + i + " " + doubleQueue.poll());
		// }
		// method 2:
		while (doubleQueue.peek() != null){
			System.out.println(doubleQueue.poll());
		}
		System.out.println("What's left: " + doubleQueue);
	}	
}