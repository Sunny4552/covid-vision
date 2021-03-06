package application;

/**
 * Simulates a user that has a name and address.
 * 
 * @author Thi Nguyen, Minh Nguyen, Sunny Mistry, T.K. Bui
 *
 */
public class User {
	private String name;
	Address addr;

	/**
	 * Constructs a user object with a given name and address.
	 * 
	 * @param name The user's name.
	 * @param addr The user's address.
	 */
	public User(String name, Address addr) {
		this.name = name.toUpperCase();
		this.addr = addr;
	}

	/**
	 * Constructs a user object with a given name.
	 * 
	 * @param name The user's name.
	 */
	public User(String name) {
		this.name = name;
		addr = new Address();
	}

	/**
	 * Constructs a user object with a given name, street, city, state, and zip
	 * code.
	 * 
	 * @param name
	 * @param streetAddr
	 * @param city
	 * @param state
	 * @param zipCode
	 */
	public User(String name, String streetAddr, String city, String state, int zipCode) {
		this(name, new Address(streetAddr, city, state, zipCode));
	}

	/**
	 * Gets the user's name.
	 * 
	 * @return The user's name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gets the user's address.
	 * 
	 * @return the user's address object.
	 */
	public Address getAddr() {
		return addr;
	}

	/**
	 * Checks that the name entered is in the correct format.
	 * 
	 * @param name The name the user entered.
	 * @return True if the name is in the correct format.
	 */
	public static boolean validName(String name) {
		// Makes sure user has entered a first name and a last name and are alphabet
		// characters
		String[] fullName = name.split(" ");

		if (fullName.length < 2)
			return false;

		return fullName.length == 2;
	}

	/**
	 * Returns the User as a string
	 */
	@Override
	public String toString() {
		// concatenate all instance fields of user together
		// return name + "|" + addr + "|" + age;
		return name + "|" + addr;
	}

}
