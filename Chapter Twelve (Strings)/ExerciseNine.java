public class ExerciseNine {
	
	public static void main(String[] args){
		String k =
    		"Then, when you have found the shrubbery, you must " +
   			"cut down the mightiest tree in the forest... " +
   			"with... a herring!";
		System.out.println(k.replaceAll("[aeiouAEIOU]", "_"));
	}	
}