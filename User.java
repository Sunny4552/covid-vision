package application;

public class User {
	private String name;
	Address addr;
	
	public User (String name, Address addr) {
		this.name = name;
		this.addr = addr;
	}
	
	public String getName() {
		return name;
	}
	
	public Address getAddr() {
		return addr;
	}
	
	
}
