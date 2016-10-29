public class ExerciseNineteen {
	
	static String s0 = "level-0";

	class Inner {
		String s1 = "level-1";

		class InnerTwo {
			String s2 = "level-2";
		} 
	}

	static class NestedClass {
		String s1 = "level-1";
		String s10 = s0; 

		static class NestedClassTwo {
			String s2 = "level-2";
		}
	}

	public static void main(String[] args){
		ExerciseNineteen e = new ExerciseNineteen();
		System.out.println(e.s0);

		ExerciseNineteen.Inner eInner = e.new Inner();
		System.out.println(eInner.s1);

		ExerciseNineteen.Inner.InnerTwo eInnerInnerTwo = eInner.new InnerTwo();
		System.out.println(eInnerInnerTwo.s2); 

		NestedClass n = new NestedClass();
		System.out.println(n.s1 + "+" + n.s10);
		
		NestedClass.NestedClassTwo nTwo = new NestedClass.NestedClassTwo();
		System.out.println(nTwo.s2);
	}	
}