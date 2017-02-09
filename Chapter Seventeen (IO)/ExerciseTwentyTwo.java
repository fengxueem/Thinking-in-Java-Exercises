import java.io.*;
import net.mindview.util.*;
import java.util.*;

class OSExecute {
  public static TwoTuple<ArrayList<String>,ArrayList<String>> command(String command) {
    // boolean err = false;
    try {
      Process process =
        new ProcessBuilder(command.split(" ")).start();
      BufferedReader results = new BufferedReader(
        new InputStreamReader(process.getInputStream()));
      String s;
      ArrayList<String> resultList = new ArrayList<>();
      while((s = results.readLine())!= null)
        resultList.add(s);
      BufferedReader errors = new BufferedReader(
        new InputStreamReader(process.getErrorStream()));
      // Report errors and return nonzero value
      // to calling process if there are problems:
      ArrayList<String> errList = new ArrayList<>();
      while((s = errors.readLine())!= null) {
        errList.add(s);
        // err = true;
      }
      return new TwoTuple(resultList, errList);
    } catch(Exception e) {
      // Compensate for Windows 2000, which throws an
      // exception for the default command line:
      if(!command.startsWith("CMD /C"))
        command("CMD /C " + command);
      else
        throw new RuntimeException(e);
    }
    // if(err)
    //   throw new OSExecuteException("Errors executing " +
    //     command);
    return null;
  }
}

public class ExerciseTwentyTwo {
  public static void main(String[] args){
    TwoTuple<ArrayList<String>,ArrayList<String>> tuple = OSExecute.command("javap ExerciseOne");
    ArrayList<String> resultList = tuple.first;
    ArrayList<String> errList = tuple.second;
    for (String temp : resultList) {
      System.out.println(temp);
    }
    for (String temp : errList) {
      System.out.println(temp);
    }
  }  
}