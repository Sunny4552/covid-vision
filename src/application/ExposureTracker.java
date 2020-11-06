package application;

import java.util.ArrayList;

public class ExposureTracker {

	private UserDb database;

	/**
	 * Constructs an exposure tracker with a given database file path
	 * 
	 * @param databaseFilePath The file path to the database text file.
	 */
	public ExposureTracker(String databaseFilePath) {
		database = new UserDb(databaseFilePath);
	}

	public boolean loginUser(User user) {
		return userAlrRegistered(user);
	}

	/**
	 * Add all of the user's information to the database.
	 * 
	 * @param information The user's information.
	 */
	public void createNewUser(User user, String testStatus, String interactions) {
		// calls writeNewUser() from UserDb

		database.writeNewUser(user, testStatus, interactions);
	}

	/**
	 * 
	 * 
	 * @param information The user's information.
	 */
	public void registerNewUser(User user, String testStatus, String interactions) {
		ArrayList<Integer> unregisteredUserRecords = database.findUnregisteredUser(user);
		
		
		if (database.nameExistsInDb(user) && !database.userFullyRegistered(user))
		{
			ArrayList<String[]> listOfUserInteractions = database.readInteractions(database.findUnregisteredUser(user));
			for (String[] currentUserIteractions: listOfUserInteractions)
			{
				if (interactions.contains(currentUserIteractions[0]))
						{
							//EDIT CURRENT USER
							return;
						}
			}
		}
		
		createNewUser(user, testStatus, interactions);
		
	}

	/**
	 * Gets the user's Covid test status .
	 * 
	 * @param user The user that the system will be checking Covid test status for.
	 * @return The user's Covid test status.
	 */
	public String getTestStatus(User user) {
		return database.readTestStatus(user);
	}

	/**
	 * Gets the user's exposure status.
	 * 
	 * @param user The user that the system will be checking Covid test status for.
	 * @return The user's Covid test status.
	 */
	public String getExposureStatus(User user) {
		return database.readTestStatus(user);
	}

	public String[] getUserInteractions(User user) {
		return database.readInteractions(user);
	}

	/**
	 * Checks if a user with the same name and address already exists in the
	 * database.
	 * 
	 * @param infoEntered Name and address that the user just entered.
	 * @return True if a user with the same name and address already exists in the
	 *         database.
	 */
	public boolean userAlrRegistered(User user) {
		return database.nameExistsInDb(user) && database.userFullyRegistered(user);
	}

	/**
	 * Adds interactions to the user's current list of interactions in the database.
	 * 
	 * @param interactions The list of people who the user has interacted with.
	 */
	public void addInteractions(User user, String interactions) {
		database.writeInteractions(user, interactions);
	}

	/**
	 * Sets the user's Covid test status in the database.
	 * 
	 * @param user   The user whose Covid test status will be updated.
	 * @param status The user's Covid test status.
	 */
	public void updateTestStatus(User user, String status) {
		// The status can either be negative, positive, or not tested.
		database.writeTestStatus(user, status);
	}

	/**
	 * Update the exposure status of all the people on the interactions list.
	 * 
	 * @param user The user whose interactions' exposure status will be updated.
	 */
	public void updateInteractionsExposure(User user) {
		// Gets list of all user's interactions

		// Goes through and marks what degree of exposure user has
	}

}
