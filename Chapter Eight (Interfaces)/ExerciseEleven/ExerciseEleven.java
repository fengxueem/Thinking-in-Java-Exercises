import interfaces.interfaceprocessor.*;

class StringCharExchangerAdapter implements Processor {
	private StringCharExchanger stringExchanger = new StringCharExchanger();

	public String name() {
		return stringExchanger.getClass().getName();
	}

	public Object process(Object input) {
		return stringExchanger.exchange((String) input);
	}
}

public class ExerciseEleven {
	public static void main(String[] args){
		Apply.process(new StringCharExchangerAdapter(), "haha");
	}	
}
