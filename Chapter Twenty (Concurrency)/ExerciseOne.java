class SomeRun implements Runnable {
  private static int counter = 0;
  private final int id = counter++;

  public SomeRun() {
  	System.out.println("Starting SomeRun #:" + id);
  }

  public void run() {
  	for (int i = 0; i < 3; i++) {
  	  System.out.println(i + "th time of #:" + id);
  	  Thread.yield();
  	}
  	System.out.println("closing #:" + id);
  	return;
  }
}

public class ExerciseOne {
  public static void main(String[] args) {
  	for (int i = 0; i < 3; i++) {
  	  new Thread(new SomeRun()).start();
  	}
  }
}