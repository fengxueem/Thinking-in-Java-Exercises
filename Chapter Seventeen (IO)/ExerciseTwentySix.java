import java.util.regex.*;
import net.mindview.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.io.*;

class JGrep {
  public static void main(String[] args) throws Exception {
    if(args.length < 2) {
      System.out.println("Usage: java ExerciseTwentySix file regex");
      System.exit(0);
    }
    Pattern p = Pattern.compile(args[1]);
    // Iterate through the lines of the input file:
    int index = 0;
    Matcher m = p.matcher("");
    MappedByteBuffer buffer = new FileInputStream(args[0]).getChannel()
        .map(FileChannel.MapMode.READ_ONLY, 0, new File(args[0]).length());
    // follow the example in page 951 to read String from ByteBuffer
    String[] lines = Charset.forName(System.getProperty("file.encoding"))
        .decode(buffer).toString().split("\n");
    for(String line : lines) {
      m.reset(line);
      while(m.find())
        System.out.println(index++ + ": " +
          m.group() + ": " + m.start());
    }
  }
}

public class ExerciseTwentySix {
  public static void main(String[] args) throws Exception {
    JGrep.main(args);    
  }  
}