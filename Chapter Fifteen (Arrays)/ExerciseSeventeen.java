import net.mindview.util.*;
import java.util.*;
import java.math.*;

class BigDecimalGenerator implements Generator<BigDecimal> {
  private BigDecimal b = new BigDecimal(0.0); 
  public BigDecimal next() {
  	return b = b.add(new BigDecimal(1.0));
  }
}

public class ExerciseSeventeen {
  public static void main(String[] args) {
  	// fill an exsiting array
  	BigDecimal[] b1 = new BigDecimal[20];
  	Generated.array(b1, new BigDecimalGenerator());
  	System.out.println(Arrays.toString(b1));
  	// create an array
  	BigDecimal[] b2 = Generated.array(BigDecimal.class, new BigDecimalGenerator(), 20);
  	System.out.println(Arrays.toString(b2));
  }
}