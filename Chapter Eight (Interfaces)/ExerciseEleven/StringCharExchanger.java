public class StringCharExchanger {
	public String exchange(String s) {
		char[] c = new char[s.length()];
		for (int i = 0;i < s.length() ;i++ ) {
			c[i] = s.charAt(s.length()-i-1);
		}
		return new String(c);
	}
}