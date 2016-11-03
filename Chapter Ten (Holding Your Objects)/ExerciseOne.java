import java.util.*;

class Gerbil {

	int gerbilNumber;

	public Gerbil(int gerbilNumber) {
		this.gerbilNumber = gerbilNumber;
	}

	public void hop() {
		System.out.println(gerbilNumber);
	}
}

public class ExerciseOne {
	
	public static void main(String[] args){
		List<Gerbil> list = new ArrayList<Gerbil>();
		for (int i = 0; i< 10; i++) 
			list.add(new Gerbil(i));
		for (int i = 0; i< list.size(); i++) 
			list.get(i).hop();
		for (Gerbil g: list) {
			g.hop();				
		}			
	}	
}