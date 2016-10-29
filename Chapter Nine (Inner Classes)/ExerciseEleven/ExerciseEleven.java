public class ExerciseEleven {
	
	private class thisName implements someName {
		private int i = 0;
		public void f() {System.out.println("Private inner class founction f()");}
	}

	public someName getInnerClassInstance() {
		return new thisName();
	}

	public static void main(String[] args){
		ExerciseEleven e = new ExerciseEleven();
		System.out.println("Stay upcasted~");
		e.getInnerClassInstance().f();
		// System.out.println("Try to downcast the interface-typed object to inner class~");
		// Error: cannot be downcasted back to innner class!
		// System.out.print((thisName)(e.getInnerClassInstance()).i);
	}	
}