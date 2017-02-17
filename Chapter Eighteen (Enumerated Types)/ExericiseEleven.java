import java.util.*;
import net.mindview.util.*;

interface Input { 
  public int amount(); 
}

enum MoneyAndInsturction implements Input {
  NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100),
  // Enum Input does not categorize vented items.
  // Items are read from a txt file named 'venteditems.txt'.
  // TOOTHPASTE(200), CHIPS(75), SODA(100), SOAP(50),
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
  MoneyAndInsturction(int value) { this.value = value; }
  MoneyAndInsturction() {}
  public int amount() { return value; }; // In cents
  static Random rand = new Random(47);
  public static Input randomSelection() {
    // Don't include STOP:
    return values()[rand.nextInt(values().length - 1)];
  }
}

class VendedItem implements Input {
  private String name;
  private int value;
  VendedItem(String name, int value) {
    this.name = name;
    this.value = value;
  }
  public String name() { return name; }
  public int amount() { return value; }
} 

enum Category {
  MONEY(MoneyAndInsturction.NICKEL, MoneyAndInsturction.DIME, MoneyAndInsturction.QUARTER, MoneyAndInsturction.DOLLAR),
  ITEM_SELECTION,
  QUIT_TRANSACTION(MoneyAndInsturction.ABORT_TRANSACTION),
  SHUT_DOWN(MoneyAndInsturction.STOP);
  private Input[] values;
  Category(Input... types) { values = types; }  
  Category() {
    // empty constructor for ITEM_SELECTION
  }  
  private static HashMap<Input,Category> categories =
      new HashMap<Input,Category>();
  static {
    for(Category c : Category.class.getEnumConstants()) {
      // don't put ITEM_SELECTION in the map, it's handled by FileInputGenerator.next()
      if (c.values == null) {
        continue;
      }
      for(Input type : c.values) 
        categories.put(type, c);
    }
  }
  public static Category categorize(Input input) {
    Category temp = categories.get(input);
    return temp == null ? ITEM_SELECTION : temp;
  }
} 

class VendingMachine {
  private static State state = State.RESTING;
  private static int amount = 0;
  private static Input selection = null;
  static ArrayList<VendedItem> itemList = new ArrayList();
  static {
    // initialize the itemList
    ArrayList<String> venteditems = new TextFile("venteditems.txt", ",");
    for (String s : venteditems) {
      String[] nameAndAmount = s.split("\\(|\\)");
      itemList.add(new VendedItem(nameAndAmount[0], Integer.parseInt(nameAndAmount[1])));
    }
  }
  enum StateDuration { TRANSIENT } // Tagging enum
  enum State {
    RESTING {
      void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            state = ADDING_MONEY;
            break;
          case SHUT_DOWN:
            state = TERMINAL;
          default:
        }
      }
    },  
    ADDING_MONEY {
      void next(Input input) {
        switch(Category.categorize(input)) {
          case MONEY:
            amount += input.amount();
            break;
          case ITEM_SELECTION:
            selection = input;
            if(amount < selection.amount())
              System.out.println("Insufficient money for " + selection);
            else state = DISPENSING;
            break;
          case QUIT_TRANSACTION:
            state = GIVING_CHANGE;
            break;
          case SHUT_DOWN:
            state = TERMINAL;
          default:
        }
      }
    },  
    DISPENSING(StateDuration.TRANSIENT) {
      void next() {
        System.out.println("here is your " + ((VendedItem)selection).name());
        amount -= selection.amount();
        state = GIVING_CHANGE;
      }
    },
    GIVING_CHANGE(StateDuration.TRANSIENT) {
      void next() {
        if(amount > 0) {
          System.out.println("Your change: " + amount);
          amount = 0;
        }
        state = RESTING;
      }
    },  
    TERMINAL { void output() { System.out.println("Halted"); } };
    private boolean isTransient = false;
    State() {}
    State(StateDuration trans) { isTransient = true; }
    void next(Input input) {
      throw new RuntimeException("Only call " +
        "next(Input input) for non-transient states");
    }
    void next() {
      throw new RuntimeException("Only call next() for " +
        "StateDuration.TRANSIENT states");
    }
    void output() { System.out.println(amount); }
  } 
  static void run(Generator<Input> gen) {
    while(state != State.TERMINAL) {
      state.next(gen.next());
      while(state.isTransient)
        state.next();
      state.output();
    }
  }
  public static void main(String[] args) {
    Generator<Input> gen = new FileInputGenerator("ExerciseElevenInputFile.txt");
    run(gen);
  }
  // Test method for VendingMachine static initialization of itemList
  // static void printList() {
  //   for (VendedItem v : itemList) {
  //     System.out.println(v.name() + " costs " + v.amount());
  //   }
  // }
} 

// Create Inputs from a file of ';'-separated strings:
class FileInputGenerator implements Generator<Input> {
  private Iterator<String> input;
  public FileInputGenerator(String fileName) {
    input = new TextFile(fileName, ";").iterator();
  }
  public Input next() throws RuntimeException {
    if(!input.hasNext())
      return null;
    String toBeParsedString = input.next().trim();
    Input temp = null;
    // First, try to parse as MoneyAndInsturction
    try {
      temp = Enum.valueOf(MoneyAndInsturction.class, toBeParsedString);
    } catch (IllegalArgumentException e) {
      // this error means toBeParsedString is not part of MoneyAndInsturction
    }
    // Second, try to parse as VentedItem
    if (temp == null) {
      for (VendedItem v : VendingMachine.itemList) {
        if (v.name().equals(toBeParsedString)) {
          return v;
        }
      }
    } else {
      return temp;
    }
    // finally(it's neither MoneyAndInsturction nor VentedItem), throw an error.
    throw new RuntimeException(toBeParsedString + " is not a valid input");
  }
}

public class ExericiseEleven {
  public static void main(String[] args){
    // Category c = Category.categorize(new VendedItem("apple", 15));
    // System.out.println(c + "");
    // Generator<Input> gen = new FileInputGenerator("ExerciseElevenInputFile.txt");
    // VendingMachine.printList();
    // System.out.println(gen.next().getClass());
    // System.out.println(gen.next().getClass());
    // System.out.println(gen.next().getClass());
    // System.out.println(gen.next().getClass());
    VendingMachine.main(args);
  }  
}