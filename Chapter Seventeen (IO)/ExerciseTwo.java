import java.util.regex.*;
import java.io.*;
import java.util.*;
import net.mindview.util.TextFile;

class DirList {
  public static void main(String[] args) {
    File path = new File(".");
    File[] list;
    if(args.length == 0)
      // list only files not directories
      list = path.listFiles(new FilenameFilter() {
          public boolean accept(File dir, String name) {
            if (new File(name).isDirectory()) {
              return false;
            } else {
              return true;
            }
          }
      });
    else
      list = path.listFiles(new FilenameFilter() {
          private Pattern pattern = Pattern.compile(args[0]);
          public boolean accept(File dir, String name) {
            if (new File(name).isDirectory()) {
              return false;
            } else {
              return pattern.matcher(TextFile.read(name)).find();
            }
          }
      });
    Arrays.sort(list);
    long size = 0L;
    for(File dirItem : list) {
      System.out.println(dirItem.getName() + " size: " + dirItem.length());
      size += dirItem.length();
    }
    System.out.println("Total size of selected files is " + size + " bytes.");
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

public class ExerciseTwo {
  public static void main(String[] args){
    DirList.main(args); 
  }  
}