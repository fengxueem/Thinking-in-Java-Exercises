
public class ExerciseTen {
	
  public static void main(String[] args){
	char[] c = new char[] {'a','b','c','d','d','a','b','c','d','d'};
	// c is an Object:		
	System.out.println("Superclass of char[] c: " + 
		c.getClass().getSuperclass());
	System.out.println("char[] c instanceof Object: " + 
		(c instanceof Object));		
  }	

}