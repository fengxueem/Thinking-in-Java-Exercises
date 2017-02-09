import java.io.*;
import java.util.*;
import java.util.regex.*;

public class ExerciseTen {
  public static LinkedList<String> read(String filename) throws IOException {
    // Reading input by lines:
    BufferedReader in = new BufferedReader(
        new FileReader(filename));
    String s;
    LinkedList<String> list = new LinkedList<String>();
    while((s = in.readLine())!= null)
      list.add(s.toUpperCase()); // transfer to upper case
    in.close();
    return list;
  }
  public static void main(String[] args) throws IOException {
    if (args.length != 2) {
      System.out.println("Please input the file name you want to access, then the words you want to find");
      return;
    }
    LinkedList<String> list = read(args[0]);
    ListIterator<String> it = list.listIterator();
    Pattern pattern = Pattern.compile(args[1].toUpperCase());
    while (it.hasNext()){
      String temp = it.next();
      if (pattern.matcher(temp).find()) {
        System.out.println(temp);
      }
    }
  }  
}