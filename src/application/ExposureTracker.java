package application;

import java.util.ArrayList;

public class ExposureTracker {

	private UserDb database;
	
	/**
	 * Constructs an exposure tracker with a given database file path
	 * @param databaseFilePath The file path to the database text file.
	 */
	public ExposureTracker(String databaseFilePath) {
		database = new UserDb (databaseFilePath);
	}
	
	/**
	 * Checks that the street address is in the correct format.
	 * @param streetAddr The street address the user entered.
	 * @return True if the street address is in the correct format.
	 */
	public boolean validStreetAddr (String streetAddr) {
		//makes sure the string is in the format
		
		//tokenize address
		String[] address = streetAddr.split(" ");
		
		//makes sure first part contains only number
		if (! (address[0].matches("[0-9+"))) {
			return false;
		}
		
		//makes sure the rest are strings
		for (int i = 1; i < address.length; i++) {
			
			//if strings doesn't contain only alphabet letters
			if (! (address[0].matches("[a-zA-Z]+"))) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * Checks that the name entered is in the correct format.
	 * @param name The name the user entered.
	 * @return True if the name is in the correct format.
	 */
	public boolean validName (String name) {
		//Makes sure user has entered a first name and a last name
		String[] fullName = name.split (" ");
		return fullName.length == 2;
	}
	
	/**
	 * Checks that the age entered is in the correct format.
	 * @param age The age the user entered.
	 * @return True if the age is in the correct format.
	 */
	public boolean validAge (int age) {
		//assume that valid age is: 0 <= age <= 150
		return age >= 0 && age <= 150;
	}
	
	/**
	 * Add all of the user's information to the database.
	 * @param information The user's information.
	 */
	public void createNewUser(String information, String testStatus, String interactions) {
		//calls writeUser() from UserDb
		
		database.writeUser(information, testStatus, interactions);
	}
	
	/**
	 * Gets the user's Covid status .
	 * @param user The user that the system will be checking Covid status for.
	 * @return The user's Covid status.
	 */
	public String getCovidStatus(User user) {
		return database.readCovidStatus(user);
	}
	
	public ArrayList<String> getUserInteraction(User user) {
		return database.readInteractions(user);
	}
	
	/**
	 * Checks if a user with the same name and address already exists in the database.
	 * @param infoEntered Name and address that the user just entered.
	 * @return True if a user with the same name and address already exists in the database.
	 */
	public boolean userAlrExists (User user) {
		return database.userExistsInDb(user);
	}
	
	/**
	 * Adds interactions to the user's list of interactions to the database.
	 * @param interactions The list of people who the user has interacted with.
	 */
	public void addInteractions (User user, String interactions) {
		
	}
	
	/**
	 * Sets the user's Covid status in the database.
	 * @param user The user whose Covid status will be updated.
	 * @param status The user's Covid status.
	 */
	public void setCovidStatus(User user, String status) {
		//The status can either be negative, positive, or not tested.
		database.writeStatus (user, status);
	}
	

	/**
	 * 
	 * @param user
	 */
	public void updateInteractionsExposure(User user) {
		//Gets list of all user's interactions
		
		//Goes through and marks what degree of exposure user has
	}
	
}