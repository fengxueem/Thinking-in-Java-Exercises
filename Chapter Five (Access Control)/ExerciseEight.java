class Connection {
	private Connection() {}

	public static Connection getInstance() {
		return new Connection();
	}
}

class ConnectionManager {
	private static Connection[] connectionArray = new Connection[20];
	private static int rentedConnectionIndex = 0; // this index indicates how many connections are rented.
	static {
		for (int i = 0; i < connectionArray.length; i++) {
			connectionArray[i] = Connection.getInstance();
		}
	}

	private ConnectionManager() {}

	public static int getRentedConnectionIndex() {
		return rentedConnectionIndex;
	}

	/**
	 * Rent a connection starting from the beginning of the connectionArray.
	 * When it runs out of objects, return null.	
	 */
	public static Connection rentConnection() {
		if (rentedConnectionIndex == connectionArray.length) {
			System.out.println("Sorry, no more connections to rent.");
			return null;
		}
		return connectionArray[rentedConnectionIndex++];
	}

	/**
	 * Return a connection.
	 * When it's full of objects, return an error message.	
	 */
	public static void returnConnection( Connection c ) {
		if (rentedConnectionIndex == 0) {
			System.out.println("Error: Connection Manager has full of connections now. No more connections are accepted.");
			return;
		}
		if (c == null) {
			System.out.println("Error: This connection is an empty object. Please return a real connection object.");
			return;
		}
		System.out.println("Thanks for using our connection:'" + c + "'. Waiting for your next rent :)");
		connectionArray[--rentedConnectionIndex] = c;
	}
}


public class ExerciseEight {

	public static void main(String[] args){
		// ConnectionManager cm = new ConnectionManager();
		Connection[] c = new Connection[50];
		for (int i = 0; i < c.length ; i++) {
			c[i] = ConnectionManager.rentConnection();
		}
		System.out.println(ConnectionManager.getRentedConnectionIndex());
		ConnectionManager.returnConnection(c[21]);
		System.out.println(ConnectionManager.getRentedConnectionIndex());
		ConnectionManager.returnConnection(c[0]);
		System.out.println(ConnectionManager.getRentedConnectionIndex());
	}	
}