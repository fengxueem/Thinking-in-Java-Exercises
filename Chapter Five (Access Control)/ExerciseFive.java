
class A {

	public String publicString = "public String";
	protected String protectedString = "protected String";
	String defaultString = "default String";
	private String privateString = "private String";

	public String getPublicString() {
		return publicString;
	}

	protected String getProtectedString() {
		return protectedString;
	}

	String getDefaultString() {
		return defaultString;
	}

	private String getPrivateString() {
		return privateString;
	}
}


public class ExerciseFive {

	public static void main(String[] args){
		A a = new A();
		System.out.println("access public field: " + a.publicString);
		System.out.println("access protected field: " + a.protectedString);
		System.out.println("access default field: " + a.defaultString);
		System.out.println("access private field: " + a.privateString);

		System.out.println("access public method: " + a.getPublicString());
		System.out.println("access protected method: " + a.getProtectedString());
		System.out.println("access default method: " + a.getDefaultString());
		System.out.println("access private method: " + a.getPrivateString());
	}	
}