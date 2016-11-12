import java.util.regex.*;
import java.util.*;

class Groups {
  static public final String POEM =
    "Twas brillig, and the slithy toves\n" +
    "Did gyre and gimble in the wabe.\n" +
    "All mimsy were the borogoves,\n" +
    "And the mome raths outgrabe.\n\n" +
    "Beware the Jabberwock, my son,\n" +
    "The jaws that bite, the claws that catch.\n" +
    "Beware the Jubjub bird, and shun\n" +
    "The frumious Bandersnatch.";
  public static void main(String[] args) {
    Matcher m =
      Pattern.compile("(?m)(^[a-z]|\\s+[a-z])\\w*")
        .matcher(POEM);
    TreeSet<String> words = new TreeSet<String>();
    while(m.find()) {
    //   for(int j = 0; j <= m.groupCount(); j++)
    //     System.out.print("[" + m.group(j) + "]");
    //   System.out.println();
    // }
      words.add(m.group());
    }
    System.out.println(words);
  }
}


public class ExerciseTwelve {
  
  public static void main(String[] args){
    Groups.main(args);    
  }  
}