import java.io.*;
import java.util.regex.*;
import net.mindview.util.*;

public class ExerciseEighteen {
  
  public static void main(String[] args) throws Exception {
    if (args.length != 1) {
      System.out.println("java ExerciseSeventeen filepath.java");
      System.exit(0);   
    }
    Matcher m = Pattern.compile("(\\W|^)[A-Z][a-zA-Z0-9]*.java\\b").matcher(args[0]);
    if (m.find()) {
      findPattern(args[0], "\".*\"");
    } else {
      System.out.println("Please input a valid java source file ending with \".java\"");
    }
  }

  private static void findPattern(String path, String regex) {
    File f = new File(path);
    if (f.isFile()) {
      System.out.println("Searching in: " + path);
      Pattern p = Pattern.compile(regex);
      // Iterate through the lines of the input file:
      int index = 0;
      int lineIndex = 1;
      Matcher m = p.matcher("");
      for(String line : new TextFile(path)) {
        m.reset(line);
        while(m.find())
          System.out.println((index++) + ": at line " + lineIndex + m.group());
        lineIndex++;
      }
    } else if (f.isDirectory()) {
      System.out.println("Searching in: " + path);
      File[] files = f.listFiles();
      for (File file : files) {
        findPattern(file.getPath(), regex);
      }
    }
  }

}