import java.nio.*;

public class ExerciseTwentyThree {
  public static boolean isPrintable(char c) {
  	return (c >= '!') && (c <= '~');
  }  

  public static void printCharBuffer(CharBuffer charBuffer) {
  	while (charBuffer.hasRemaining()){
  	  char c = charBuffer.get();
  	  if (isPrintable(c)) {
  	  	System.out.println(c);
  	  }
  	}
  }

  public static void main(String[] args) {
  	ByteBuffer buffer = ByteBuffer.allocate(24);
  	buffer.asCharBuffer().put(new char[]{'a', 'b', 'c'});
  	CharBuffer cb = buffer.asCharBuffer();
  	printCharBuffer(cb);
  }
}