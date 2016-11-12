import java.io.*;
import java.util.regex.*;
import net.mindview.util.*;

class JGrep {

  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java ExerciseSixteen (file or directory) regex");
      System.exit(0);
    }
	  findPattern(args[0], args[1]);  
  }

  private static void findPattern(String path, String regex) {
  	File f = new File(path);
    if (f.isFile()) {
      System.out.println("Searching in: " + path);
  	  Pattern p = Pattern.compile(regex);
      // Iterate through the lines of the input file:
      int index = 0;
      Matcher m = p.matcher("");
      for(String line : new TextFile(path)) {
        m.reset(line);
        while(m.find())
          System.out.println((index++) + ": " + m.group() + ": " + m.start());
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

public class ExerciseSixteen {
	
  public static void main(String[] args) throws Exception {
	  JGrep.main(args);		
  }	

}