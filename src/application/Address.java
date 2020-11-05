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
	 * Constructs an Address with a given string parsed into street address, city, state, and zip code.
	 * @param addressString
	 */
	public Address (String addressString) {
		String[] addressComponents = addressString.split(", "); //first element is street, second element is city, third element is state concatenated with zip code
		String[] stateAndZipcode = addressComponents[2].split(" "); //first element is state, second element is zip code
		
		this.streetAddr = addressComponents[0].toUpperCase();
		this.city = addressComponents[1].toUpperCase();
		this.state = stateAndZipcode[0].toUpperCase();
		this.zipCode = Integer.parseInt(stateAndZipcode[1]);
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
