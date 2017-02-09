import java.util.regex.*;
import java.io.*;
import java.util.*;
import net.mindview.util.TextFile;

class DirList {
  public static void main(String[] args) {
    File path = new File(".");
    String[] list;
    if(args.length == 0)
      list = path.list();
    else
      list = path.list(new DirFilter(args[0]));
    Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
    for(String dirItem : list)
      System.out.println(dirItem);
  }
}

class DirFilter implements FilenameFilter {
  private Pattern pattern;
  public DirFilter(String regex) {
    pattern = Pattern.compile(regex);
  }
  public boolean accept(File dir, String name) {
    if (new File(name).isDirectory()) {
      return false;
    } else {
      return pattern.matcher(TextFile.read(name)).find();
    }
  }
}

public class ExerciseOne {
  public static void main(String[] args){
    DirList.main(args); 
  }  
}