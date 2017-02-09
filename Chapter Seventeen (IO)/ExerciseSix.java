import net.mindview.util.*;
import java.text.*;
import java.io.*;
import java.util.*;

public class ExerciseSix {
  public static void main(String[] args){
  	SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
  	String[] array = new String[]{args[0]};
    new ProcessFiles(new ProcessFiles.Strategy() {
      public void process(File file) {
      	Date date = new Date(file.lastModified());
      	try {
      	  if (date.after(format.parse(args[1]))) {
	        System.out.println(file);
      	  }
      	} catch(Exception e) {
      	  e.printStackTrace();
      	}
      }
    }, "java").start(array);
  }  
}