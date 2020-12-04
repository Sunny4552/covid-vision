package application;

/**
 * Simulates an address that has a street address, city, state, and zip code.
 * 
 * @author Thi Nguyen, Minh Nguyen, Sunny Mistry, T.K. Bui
 *
 */
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
	 * Constructs an empty Address.
	 */
	public Address () {
		this.streetAddr = "";
		this.city = "";
		this.state = "";
		this.zipCode = 0;
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
	 * Checks that the street address is in the correct format.
	 * 
	 * @param streetAddr The street address the user entered.
	 * @return True if the street address is in the correct format.
	 */
	public boolean validStreetAddr(String streetAddr) {
		// makes sure the string is in the format

		// tokenize address
		String[] address = streetAddr.split(" ");

		// makes sure street has has two strings(name and street type)
		if (address.length < 3) { //minimum length is 3 because street number, street name, and street type
			return false;
		}

		// makes sure first part contains only number
		if (!(address[0].matches("[0-9+"))) {
			return false;
		}

		// makes sure the rest are strings
		for (int i = 1; i < address.length; i++) {

			// if strings doesn't contain only alphabet letters
			if (!(address[0].matches("[a-zA-Z]+"))) {
				return false;
			}
		}

		return true;
	}
	
	/**
	 * Returns the address as a string
	 */
	@Override
	public String toString() {
		//if empty address
		if (streetAddr.equals("") && city.equals("") && state.equals("") && zipCode == 0)
			return "";
		
		//concatenate all instance fields of address together
		return streetAddr + ", " + city + ", " + state + " " + zipCode;
	}
}
