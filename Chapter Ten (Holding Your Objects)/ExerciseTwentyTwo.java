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
  // Simple test:
  public static void main(String[] args) {
    String file = read("TextFile.java");
    write("test.txt", file);
    TextFile text = new TextFile("test.txt");
    text.write("test2.txt");
    // Break into unique sorted list of words:
    TreeSet<String> words = new TreeSet<String>(
      new TextFile("TextFile.java", "\\W+"));
    // Display the capitalized words:
    System.out.println(words.headSet("a"));
  }
}

class WordCounter {
	private String s;
	private int count;

	public WordCounter(String s, int count) {
		this.s = s;
		this.count = count;
	}

	public WordCounter(String s) {
		this(s, 1);
	}

	public void add(int n) {
		count += n;
	}

	public void add() {
		count++;
	}

	public int getCount() {
		return count;
	}

	public String toString() {
		return (s + ": " + count);
	}
  // in order to use HashSet.contains(), need to override equals() and hashCode()
  @Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
	    final WordCounter other = (WordCounter) obj;
	    if (!this.s.equals(other.s)) {
	        return false;
	    }
	    return true;
	}

  @Override
  public int hashCode() {
    int hash = 3;
    hash = 53 * hash + (this.s != null ? this.s.hashCode() : 0);
    return hash;
  }

}

public class ExerciseTwentyTwo {
  
  public static void main(String[] args){
    List<String> words = new ArrayList<String>(new TextFile("ExerciseTwentyOne.java", "\\W+"));
    Collections.sort(words, String.CASE_INSENSITIVE_ORDER);
    System.out.println(words);
    Set<WordCounter> set = new HashSet<WordCounter>();
    for (String word : words) {
    	WordCounter temp = new WordCounter(word);
    //  // method 1:
    // 	for (Iterator<WordCounter> it = set.iterator(); it.hasNext(); ) {
    //    		WordCounter w = it.next();
    //    		if (w.equals(temp)) {
    //    			temp = w;
    //    			temp.add();
    //    			it.remove();
    //    			break;
    //    		}
	  //  }
   	// 	set.add(temp);
      // method 2:
      if (set.contains(temp)) {
        for (Iterator<WordCounter> it = set.iterator(); it.hasNext(); ) {
          WordCounter w = it.next();
          if (w.equals(temp)) {
            temp = w;
            temp.add();
            it.remove();
            break;
          }
        }
      }
      set.add(temp);
    }
    System.out.println("\n" + set);
  }
}