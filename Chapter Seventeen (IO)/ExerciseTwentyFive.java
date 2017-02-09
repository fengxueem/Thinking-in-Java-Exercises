import java.nio.*;
import java.io.*;
import java.nio.channels.*;
import static net.mindview.util.Print.*;

class UsingBuffers {
  public static void symmetricScramble(CharBuffer buffer){
    while(buffer.hasRemaining()) {
      buffer.mark();
      char c1 = buffer.get();
      char c2 = buffer.get();
      buffer.reset();
      buffer.put(c2).put(c1);
    }
  }
}

class MappedIO {
  private abstract static class Tester {
    private String name;
    public Tester(String name) { this.name = name; }
    public void runTest() {
      System.out.print(name + ": ");
      try {
        long start = System.nanoTime();
        test();
        long duration = (System.nanoTime() - start);
        System.out.print(duration + "us \n");
      } catch(IOException e) {
        throw new RuntimeException(e);
      }
    }
    public abstract void test() throws IOException;
  }
  private static Tester[] tests = {
    new Tester("UsingBuffers->allocate") {
      public void test() throws IOException {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocate(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        cb.rewind();
        UsingBuffers.symmetricScramble(cb);
        cb.rewind();
        UsingBuffers.symmetricScramble(cb);
        cb.rewind();
      }
    },
    new Tester("UsingBuffers->allocateDirect") {
      public void test() throws IOException {
        char[] data = "UsingBuffers".toCharArray();
        ByteBuffer bb = ByteBuffer.allocateDirect(data.length * 2);
        CharBuffer cb = bb.asCharBuffer();
        cb.put(data);
        cb.rewind();
        UsingBuffers.symmetricScramble(cb);
        cb.rewind();
        UsingBuffers.symmetricScramble(cb);
        cb.rewind();
      }
    },
    new Tester("GetChannel->allocate") {
      public void test() throws IOException {
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        fc.close();
        // Add to the end of the file:
        fc = new RandomAccessFile("data.txt", "rw").getChannel();
        fc.position(fc.size()); // Move to the end
        fc.write(ByteBuffer.wrap("Some more".getBytes()));
        fc.close();
        // Read the file:
        fc = new FileInputStream("data.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocate(1024);
        fc.read(buff);
        buff.flip();
        while(buff.hasRemaining())
          System.out.print((char)buff.get());
      }
    },
    new Tester("GetChannel->allocateDirect") {
      public void test() throws IOException {
        FileChannel fc = new FileOutputStream("data.txt").getChannel();
        fc.write(ByteBuffer.wrap("Some text ".getBytes()));
        fc.close();
        // Add to the end of the file:
        fc = new RandomAccessFile("data.txt", "rw").getChannel();
        fc.position(fc.size()); // Move to the end
        fc.write(ByteBuffer.wrap("Some more".getBytes()));
        fc.close();
        // Read the file:
        fc = new FileInputStream("data.txt").getChannel();
        ByteBuffer buff = ByteBuffer.allocateDirect(1024);
        fc.read(buff);
        buff.flip();
        while(buff.hasRemaining())
          System.out.print((char)buff.get());
      }
    }
  };
  public static void main(String[] args) {
    for(Tester test : tests)
      test.runTest();
  }
}

public class ExerciseTwentyFive {
  public static void main(String[] args){
    MappedIO.main(args);
  }  
}