import static net.mindview.util.Print.*;
import java.util.Random;
import java.util.ArrayList;

interface Factory<T> { T create(); }

interface Generator<T> { T next(); }

class Instrument {
  public static class MusicFactory implements Factory<Instrument> {
    public Instrument create() { return new Instrument();}
  }
  void play() { print("Instrument.play() "); }
  String what() { return "Instrument"; }
  void adjust() { print("Adjusting Instrument"); }
}

class Wind extends Instrument {
  public static class MusicFactory implements Factory<Wind> {
    public Wind create() { return new Wind();}
  }
  void play() { print("Wind.play() "); }
  String what() { return "Wind"; }
  void adjust() { print("Adjusting Wind"); }
  void clearSpitValue() { print(this.getClass().getName() + " clearing spit"); }
}	

class Percussion extends Instrument {
  public static class MusicFactory implements Factory<Percussion> {
    public Percussion create() { return new Percussion();}
  }
  void play() { print("Percussion.play() "); }
  String what() { return "Percussion"; }
  void adjust() { print("Adjusting Percussion"); }
}

class Stringed extends Instrument {
  public static class MusicFactory implements Factory<Stringed> {
    public Stringed create() { return new Stringed();}
  }
  void play() { print("Stringed.play() "); }
  String what() { return "Stringed"; }
  void adjust() { print("Adjusting Stringed"); }
}

class Brass extends Wind {
  public static class MusicFactory implements Factory<Brass> {
    public Brass create() { return new Brass();}
  }
  void play() { print("Brass.play() "); }
  void adjust() { print("Adjusting Brass"); }
}

class Woodwind extends Wind {
  public static class MusicFactory implements Factory<Woodwind> {
    public Woodwind create() { return new Woodwind();}
  }
  void play() { print("Woodwind.play() "); }
  String what() { return "Woodwind"; }
}

class InstrumentGenerator implements Generator<Instrument> {
  private static ArrayList<Factory<? extends Instrument>> instrumentFactories = 
      new ArrayList<Factory<? extends Instrument>>();
  static {
    instrumentFactories.add(new Instrument.MusicFactory());
    instrumentFactories.add(new Wind.MusicFactory());
    instrumentFactories.add(new Percussion.MusicFactory());
    instrumentFactories.add(new Stringed.MusicFactory());
    instrumentFactories.add(new Brass.MusicFactory());
    instrumentFactories.add(new Woodwind.MusicFactory());
  }
  public Instrument next() {
    Random r = new Random();
    return instrumentFactories.get(r.nextInt(instrumentFactories.size())).create();
  }
}

public class ExerciseTwentysix {
  public static void main(String[] args){
    InstrumentGenerator gen = new InstrumentGenerator();
    Instrument[] array = new Instrument[20];
    for (int i =0; i < array.length; i++) {
      array[i] = gen.next();
      if (array[i] instanceof Wind) {
        ((Wind)array[i]).clearSpitValue();
      } else {
        array[i].play();
      }
    }
  }  
}