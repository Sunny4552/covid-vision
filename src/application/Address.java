package application;

public class Address {
	private String streetAddr;
	private String city;
	private String state;
	private int zipCode;
	
	/**
	 * Constructs an Address with a given street address, city, state, and zip code.
	 * @param streetAddr The user's street address.
	 * @param city The city the user is living in.
	 * @param state The state the user is living in. 
	 * @param zipCode The zip code the user is living in.
	 */
	public Address (String streetAddr, String city, String state, int zipCode) {
		this.streetAddr = streetAddr.toUpperCase();
		this.city = city.toUpperCase();
		this.state = state.toUpperCase();
		this.zipCode = zipCode;
	}
	
	
	/**
	 * Returns the address as a string
	 */
	@Override
	public String toString() {
		//concatenate all instance fields of address together
		return streetAddr + ", " + city + ", " + state + " " + zipCode;
	}
}
