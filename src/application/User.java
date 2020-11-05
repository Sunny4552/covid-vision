package application;

public class User {
	private String name;
	Address addr;
	int age;
	
	/**
	 * Constructs a user object with a given name, address, and age.
	 * @param name
	 * @param addr
	 * @param age
	 */
	public User (String name, Address addr, int age) {
		this.name = name.toUpperCase();
		this.addr = addr;
		this.age = age;
	}
	
	/**
	 * Gets the user's name.
	 * @return The user's name
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Gets the user's address.
	 * @return the user's address object.
	 */
	public Address getAddr() {
		return addr;
	}
	
	/**
	 * Gets the user's age.
	 * @return the user's age.
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Returns the User as a string
	 */
	@Override
	public String toString() {
		//concatenate all instance fields of user together
		return name+"|"+addr+"|"+age;
	}
	
	
}
