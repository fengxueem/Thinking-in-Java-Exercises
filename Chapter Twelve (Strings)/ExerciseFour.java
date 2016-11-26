import java.util.*;

class Receipt {
  private static final int NAMEWIDTH = 20;
  private static final int QTYWIDTH = 7;
  private static final int PRIWIDTH = 13;
  private static final String TITLE = "%-" + NAMEWIDTH+ "s %" + QTYWIDTH + "s %" + PRIWIDTH + "s\n";
  private static final String TOTAL = "%-" + NAMEWIDTH+ "s %" + QTYWIDTH + "s %" + PRIWIDTH + ".2f\n";
  private static final String ENTRY = "%-" + NAMEWIDTH+ "." + NAMEWIDTH +"s %" + QTYWIDTH + "d %" + PRIWIDTH + ".2f\n";
  private double total = 0;
  private Formatter f = new Formatter(System.out);
  public void printTitle() {
    f.format(TITLE, "Item", "Qty", "Price");
    f.format(TITLE, "----", "---", "-----");
  }
  public void print(String name, int qty, double price) {
    f.format(ENTRY, name, qty, price);
    total += price;
  }
  public void printTotal() {
    f.format(TOTAL, "Tax", "", total*0.06);
    f.format(TITLE, "", "", "-----");
    f.format(TOTAL, "Total", "", total * 1.06);
  }
  public static void main(String[] args) {
    Receipt receipt = new Receipt();
    receipt.printTitle();
    receipt.print("Jack's Magic Beans", 4, 4.25);
    receipt.print("Princess Peas", 3, 5.1);
    receipt.print("Three Bears Porridge", 1, 14.29);
    receipt.printTotal();
  }
}


public class ExerciseFour {
  public static void main(String[] args){
    Receipt.main(args);    
  }  
}