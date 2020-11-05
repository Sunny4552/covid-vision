package application;

public class User {
	private String name;
	Address addr;
	int age;
	
	public User (String name, Address addr, int age) {
		this.name = name.toUpperCase();
		this.addr = addr;
		this.age = age;
	}
	
	public String getName() {
		return name;
	}
	
	public Address getAddr() {
		return addr;
	}
	
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
