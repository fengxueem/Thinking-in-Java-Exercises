import java.io.*;

class StoringAndRecoveringData {
  public static void main(String[] args)
  throws IOException {
    DataOutputStream out = new DataOutputStream(
      new BufferedOutputStream(
        new FileOutputStream("Data.txt")));
    //"123".getBytes = {49, 50, 51} from ascii table
    out.write("123".getBytes(), 0, 3);
    out.write(-128);
    out.writeBoolean(true);
    out.writeByte(-128);
    //"257".getBytes = {50, 53, 55} from ascii table
    out.writeBytes("257");
    out.writeChar(0);
    out.writeChars("-128");
    out.writeDouble(3.14159);
    out.writeFloat(3.14159f);
    out.writeInt(-128);
    out.writeLong(-128);
    out.writeShort(-128);
    out.writeUTF("That was pi");
    out.close();
    DataInputStream in = new DataInputStream(
      new BufferedInputStream(
        new FileInputStream("Data.txt")));

    byte[] b = new byte[3];
    int i = in.read(b, 0, 3);
    for (byte temp : b) {
      System.out.println("1: write(\"123\".getBytes(), 0, 3) " + i + "; " + temp);
    }

    // range of one-byte number: -128~127
    b = new byte[1];
    i = in.read(b);
    for (byte temp : b) {
      System.out.println("2: write(-128) " + i + "; " + temp);
    }

    System.out.println("3: writeBoolean(true) " + in.readBoolean());

    // range of one-byte number: -128~127
    System.out.println("4: writeByte(-128) " + in.readByte());

    b = new byte[3];
    i = in.read(b);
    for (byte temp : b) {
      System.out.println("5: writeBytes(\"257\") " + i + "; " + temp);
    }

    System.out.println("6: writeChar(0) " + in.readChar());

    for (int j = 0; j < 4; j++) {
      System.out.println("7: out.writeChars(\"-128\"); " + in.readChar());
    }

    System.out.println("8: writeDouble(3.14159) " + in.readDouble());

    System.out.println("9: writeFloat(3.14159f) " + in.readFloat());

    System.out.println("10: writeInt(-128) " + in.readInt());

    System.out.println("11: writeLong(-128) " + in.readLong());

    System.out.println("12: writeShort(-128) " + in.readShort());

    System.out.println("13: writeUTF(\"That was pi\") " + in.readUTF());
  }
}

public class ExerciseFifteen {
  public static void main(String[] args) throws IOException {
    StoringAndRecoveringData.main(args);
  }  
}