import java.io.*;
import static net.mindview.util.Print.*;

class Blip1 implements Externalizable {
  public Blip1() {
    print("Blip1 Constructor");
  }
  public void writeExternal(ObjectOutput out)
      throws IOException {
    print("Blip1.writeExternal");
  }
  public void readExternal(ObjectInput in)
     throws IOException, ClassNotFoundException {
    print("Blip1.readExternal");
  }
}

public class ExerciseTwentyEight implements Externalizable {
  // ExerciseTwentyEight() {
  //   print("ExerciseTwentyEight Constructor");
  // }
  public void writeExternal(ObjectOutput out)
      throws IOException {
    print("ExerciseTwentyEight.writeExternal");
  }
  public void readExternal(ObjectInput in)
     throws IOException, ClassNotFoundException {
    print("ExerciseTwentyEight.readExternal");
  }
}

class Blips {
  public static void main(String[] args)
  throws IOException, ClassNotFoundException {
    print("Constructing objects:");
    Blip1 b1 = new Blip1();
    Blip2 b2 = new Blip2();
    ExerciseTwentyEight e = new ExerciseTwentyEight();
    ObjectOutputStream o = new ObjectOutputStream(
      new FileOutputStream("Blips.out"));
    print("Saving objects:");
    o.writeObject(b1);
    o.writeObject(e);
    o.writeObject(b2);
    o.close();
    // Now get them back:
    ObjectInputStream in = new ObjectInputStream(
      new FileInputStream("Blips.out"));
    print("Recovering b1:");
    b1 = (Blip1)in.readObject();
    print("Recovering e:");
    e = (ExerciseTwentyEight)in.readObject();
    // OOPS! Throws an exception:
    // According to JAVA API DOC:
    // When an Externalizable object is reconstructed, an instance is created using the public no-arg constructor,
    // then the readExternal method called. Serializable objects are restored by reading them from an ObjectInputStream.
    // Here Blip2 still gets no public no-arg constructor. Thus, it will face a failure of reconstruction.
    print("Recovering b2:");
    b2 = (Blip2)in.readObject();
  }
}
