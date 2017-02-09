import net.mindview.util.*;
import java.util.*;
import java.io.*;

public class ExerciseTwenty {
  public static void main(String[] args) throws IOException {
  	Directory.TreeInfo treeInfo = Directory.walk(".", ".*\\.class");
  	Iterator<File> it = treeInfo.iterator();
  	while (it.hasNext()){
  	  File f = it.next();
  	  System.out.println("Checking out " + f);
  	  byte[] bytes = BinaryFile.read(f);
  	  // Class files are identified by the following 4 byte header (in hexadecimal): CA FE BA BE
  	  for (int i =0; i < 4; i++) {
  	  	System.out.println(Integer.toHexString(bytes[i] & 0xff).toUpperCase());
  	  }
  	}
  }
}