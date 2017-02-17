import java.util.*;
import net.mindview.util.*;

enum Input {
  NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
  TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
  ABORT_TRANSACTION {
    public int amount() { // Disallow
      throw new RuntimeException("ABORT.amount()");
    }
  },
  STOP { // This must be the last instance.
    public int amount() { // Disallow
      throw new RuntimeException("SHUT_DOWN.amount()");
    }
  };	
  int value; // In cents
  Input(int value) { this.value = value; }
  Input() {}
  int amount() { return value; }; // In cents
  static Random rand = new Random(47);
  public static Input randomSelection() {
    // Don't include STOP:
    return values()[rand.nextInt(values().length - 1)];
  }
}

enum Category {
  MONEY(Input.NICKEL, Input.DIME, Input.QUARTER, Input.DOLLAR),
  ITEM_SELECTION(Input.TOOTHPASTE, Input.CHIPS, Input.SODA, Input.SOAP),
  QUIT_TRANSACTION(Input.ABORT_TRANSACTION),
  SHUT_DOWN(Input.STOP);
  private Input[] values;
  Category(Input... types) { values = types; }  
  private static EnumMap<Input,Category> categories =
      new EnumMap<Input,Category>(Input.class);
  static {
    for(Category c : Category.class.getEnumConstants())
      for(Input type : c.values)
        categories.put(type, c);
  }
  public static Category categorize(Input input) {
    return categories.get(input);
  }
}

enum State {RESTING, ADDING_MONEY, DISPENSING, GIVING_CHANGE, TERMINAL}

interface Handler {
  public void next(Input input);
  public void next();
  public void output();
}

class VendingMachine {
  private State state = State.RESTING;
  private int amount = 0;
  private Input selection = null;
  private boolean isTransient = false;
  private EnumMap<State, Handler> map = new EnumMap<State, Handler>(State.class);

  {
    map.put(State.RESTING, new Handler() {
      public void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            state = State.ADDING_MONEY;
            break;
          case SHUT_DOWN:
            state = State.TERMINAL;
          default:
        }
      }
      public void next() {
        isTransient = false;
      }
      public void output() {
        System.out.println("" + state + amount);
      }
    });
    map.put(State.ADDING_MONEY, new Handler() {
      public void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            break;
          case ITEM_SELECTION:
            selection = input;
            if(amount < selection.amount())
              System.out.println("Insufficient money for " + selection);
            else state = State.DISPENSING;
            break;
          case QUIT_TRANSACTION:
            state = State.GIVING_CHANGE;
            break;
          case SHUT_DOWN:
            state = State.TERMINAL;
          default:
        }
      }
      public void next() {
        isTransient = false;
      }
      public void output() {
        System.out.println("" + state + amount);
      }
    });
    map.put(State.DISPENSING, new Handler() {
      public void next(Input input) {
        next();
      }
      public void next() {
        isTransient = true;
        System.out.println("here is your " + selection);
        amount -= selection.amount();
        state = State.GIVING_CHANGE;
      }
      public void output() {
        System.out.println("" + state + amount);
      }
    });
    map.put(State.GIVING_CHANGE, new Handler() {
      public void next(Input input) {
        next();
      }
      public void next() {
        isTransient = true;
        if(amount >= 0) {
          System.out.println("Your change: " + amount);
          amount = 0;
        }
        state = State.RESTING;
      }
      public void output() {
        System.out.println("" + state + amount);
      }
    });
    map.put(State.TERMINAL, new Handler() {
      public void next(Input input) {
        next();
      }
      public void next() {
        isTransient = false;
        System.out.println("Thanks for purchasing:)");
      }
      public void output() {
        System.out.println("" + state + amount);
      }
    });
  }

  public static void main(String[] args) {
    Generator<Input> gen = new FileInputGenerator("ExerciseTenInputFile.txt");
    VendingMachine vm = new VendingMachine();
    while(vm.state != State.TERMINAL) {
      if (vm.isTransient) {
        vm.map.get(vm.state).next();
      } else {
        vm.map.get(vm.state).next(gen.next());
      }
      vm.map.get(vm.state).output();
    }
    // create the second vending machine example
    gen = new FileInputGenerator("ExerciseTenInputFile.txt");
    vm = new VendingMachine();
    while(vm.state != State.TERMINAL) {
      if (vm.isTransient) {
        vm.map.get(vm.state).next();
      } else {
        vm.map.get(vm.state).next(gen.next());
      }
      vm.map.get(vm.state).output();
    }
  }
} 

// For a basic sanity check:
class RandomInputGenerator implements Generator<Input> {
  public Input next() { return Input.randomSelection(); }
}

// Create Inputs from a file of ';'-separated strings:
class FileInputGenerator implements Generator<Input> {
  private Iterator<String> input;
  public FileInputGenerator(String fileName) {
    input = new TextFile(fileName, ";").iterator();
  }
  public Input next() {
    if(!input.hasNext()) {
      return null;
    } else {
      return Enum.valueOf(Input.class, input.next().trim());
    }
  }
}    

public class ExericiseTen {
  public static void main(String[] args){
    VendingMachine.main(args);
  }  
}