import java.io.*;
import java.util.*;

public class ExerciseTwelve {
  public static LinkedList<String> read(String filename) throws IOException {
    // Reading input by lines:
    BufferedReader in = new BufferedReader(
        new FileReader(filename));
    String s;
    LinkedList<String> list = new LinkedList<String>();
    while((s = in.readLine())!= null)
      list.add(s);
    in.close();
    return list;
  }
  public static void main(String[] args) throws IOException {
    if (args.length != 1) {
      System.out.println("Please input the file name you want to access.");
      return;
    }
    LinkedList<String> list = read(args[0]);
    ListIterator<String> it = list.listIterator(list.size());
    int lineCounter = list.size();
    PrintWriter out = new PrintWriter("OutputFromExerciseTwelve.txt");
    while (it.hasPrevious()){
      out.println(lineCounter-- + ": " + it.previous());
    }
    out.close();
  }  
}