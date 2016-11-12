
public class ExerciseSeven {
	
	public static void main(String[] args){
		String regex = "[A-Z].*[\\.]";
		System.out.println("Ad sa.".matches(regex));
		System.out.println("ad sa.".matches(regex));
		System.out.println("Ad sa?".matches(regex));
	}	
}