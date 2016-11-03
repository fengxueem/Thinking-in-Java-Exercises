import java.util.*;
import java.io.*;

class TextFile extends ArrayList<String> {
  // Read a file as a single string:
  public static String read(String fileName) {
    StringBuilder sb = new StringBuilder();
    try {
      BufferedReader in= new BufferedReader(new FileReader(
        new File(fileName).getAbsoluteFile()));
      try {
        String s;
        while((s = in.readLine()) != null) {
          sb.append(s);
          sb.append("\n");
        }
      } finally {
        in.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
    return sb.toString();
  }
  // Write a single file in one method call:
  public static void write(String fileName, String text) {
    try {
      PrintWriter out = new PrintWriter(
        new File(fileName).getAbsoluteFile());
      try {
        out.print(text);
      } finally {
        out.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }
  // Read a file, split by any regular expression:
  public TextFile(String fileName, String splitter) {
    super(Arrays.asList(read(fileName).split(splitter)));
    // Regular expression split() often leaves an empty
    // String at the first position:
    if(get(0).equals("")) remove(0);
  }
  // Normally read by lines:
  public TextFile(String fileName) {
    this(fileName, "\n");
  }
  public void write(String fileName) {
    try {
      PrintWriter out = new PrintWriter(
        new File(fileName).getAbsoluteFile());
      try {
        for(String item : this)
          out.println(item);
      } finally {
        out.close();
      }
    } catch(IOException e) {
      throw new RuntimeException(e);
    }
  }

}


public class ExerciseTwentySix {
  
  public static void main(String[] args){
    List<String> words = new ArrayList<String>(new TextFile("ExerciseTwentyFive.java", "\\W+"));
    Map<String,ArrayList<Integer>> wordLocation = new LinkedHashMap<String,ArrayList<Integer>>();
    for (int i = 0; i < words.size(); i++) {
      if (wordLocation.containsKey(words.get(i))) 
        wordLocation.get(words.get(i)).add(i);
      else
        wordLocation.put(words.get(i), new ArrayList<Integer>(Arrays.asList(i)));
    }
    Map<Integer,String> orderedWords = new HashMap<Integer,String>();
    for (Map.Entry<String,ArrayList<Integer>> entry : wordLocation.entrySet()) {
      for (Integer i : entry.getValue()) {
        orderedWords.put(i, entry.getKey());
      }
    }
    System.out.println(orderedWords);
  }  
}