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
		int userLineNum = database.findRegisteredUser (user);
		createEmptyRecordsForInteractions(user, userLineNum, testStatus, interactions);

	}

	public void createEmptyRecordsForInteractions(User user, int userLineNum, String testStatus, String interactions) {
		String[] interactionNames = database.readInteractions(user);
		for (String name : interactionNames) {
			int lineNumInteractionRecords = database.writeNewUser(new User(name), "", user.getName());
			database.writeInteractionsRecLineNum(lineNumInteractionRecords, userLineNum);
			database.addInteractionsRecordLineNum(user, lineNumInteractionRecords);
		}
	}

	/**
	 * Registers a user. If user record is created as an interaction, edit that
	 * record. If user is not found, create a new record.
	 * 
	 * @param user         user to be registered
	 * @param testStatus   user COVID-19 test status
	 * @param interactions list of user interactions
	 */
	public void registerNewUser(User user, String testStatus, String interactions) {
		ArrayList<Integer> unregisteredUserRecords = database.findUnregisteredUser(user);

		if (database.nameExistsInDb(user) && !database.userFullyRegistered(user)) {
			for (Integer userRecordLineNum : unregisteredUserRecords) {
				if (interactions.contains(database.readInteractions(userRecordLineNum)[0])) {
					database.writeEntireUserInfo(user, testStatus, "", interactions, userRecordLineNum);
					createEmptyRecordsForInteractions(user, userRecordLineNum, testStatus, interactions);
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
	 * Sets the user's Covid test status in the database and updates user's
	 * interaction's exposure status.
	 * 
	 * @param user   The user whose Covid test status will be updated.
	 * @param status The user's Covid test status.
	 */
	public void updateTestStatus(User user, String status) {
		// The status can either be negative, positive, or not tested.
		database.writeTestStatus(user, status);
		if (status.equals("POSITIVE"))
			updateInteractionsExposure(database.findUser(user), 1);
	}

	/**
	 * Update the exposure status of all the people on the interactions list.
	 * 
	 * @param userLineNum   Line number of the user's record whose interactions' exposure status will be updated.
	 * @param status Status of current user to update interaction's status
	 */
	public void updateInteractionsExposure(int userLineNum, int exposureLevel) {

		// Gets list of all user's interactions
		String[] interactionsRecLineNum = database.readInteractionsRecLineNum(userLineNum);
		for (String lineNum: interactionsRecLineNum)
		{
			int intLineNum = Integer.parseInt(lineNum);
			database.writeExposureStatus(intLineNum, exposureLevel);
			if (exposureLevel < 3)
				updateInteractionsExposure(intLineNum, exposureLevel + 1);
		}
		// Goes through and marks what degree of exposure user has
	}

}
