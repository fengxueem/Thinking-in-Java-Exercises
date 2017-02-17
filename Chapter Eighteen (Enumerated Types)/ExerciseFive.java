import java.util.*;

enum VowelsAndConsonants {
  VOWEL('a', 'e', 'i', 'o', 'u'),
  SOMETIMES_A_VOWEL('y', 'w'),
  CONSONANT('b', 'c', 'd', 'f', 'g', 'h', 'j', 'k', 'l',
      'm', 'n', 'p', 'q', 'r', 's', 't', 'v', 'x', 'z');

  private char[] letters;

  private VowelsAndConsonants(char... letters) {
    this.letters = letters;
  }

  private static boolean contains(char[] array, char letter) {
    for (char temp : array) {
      if (temp == letter) {
        return true;
      }
    }
    return false;
  }

  public static void main(String[] args) {
    Random rand = new Random(47);
    for(int i = 0; i < 100; i++) {
      int c = rand.nextInt(26) + 'a';
      System.out.print((char)c + ", " + c + ": ");
      if (contains(VOWEL.letters, (char)c)) {
        System.out.println("vowel");
      } else if (contains(SOMETIMES_A_VOWEL.letters, (char)c)) {
        System.out.println("sometimes a vowel");
      } else {
        System.out.println("consonant");
      }
    }
  }
}

public class ExerciseFive {
  public static void main(String[] args){
    VowelsAndConsonants.main(args);    
  }  
}