package application;

import java.util.ArrayList;

/**
 * Simulates a 
 * 
 * @author Thi Nguyen, Minh Nguyen, Sunny Mistry, T.K. Bui
 *
 */
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

	/**
	 * Checks if a user could login using provided information.
	 * 
	 * @param user The user with provided information trying to login.
	 * @return True if the user exists in the database and could login, false
	 *         otherwise.
	 */
	public boolean loginUser(User user) {
		return userAlrRegistered(user);
	}

	/**
	 * Adds all of the user's information to the database.
	 * 
	 * @param user         The user with provided information trying to register.
	 * @param testStatus   The test status of the user being registered.
	 * @param interactions A list of interaction's names of the user being
	 *                     registered, separated by ", ".
	 */
	public void createNewUser(User user, String testStatus, String interactions) {
		// calls writeNewUser() from UserDb
		int userLineNum = database.writeNewUser(user, testStatus, interactions);

		// create empty records for the people who interacted with the user
		createEmptyRecordsForInteractions(user, userLineNum, testStatus, interactions);

	}

	/**
	 * Creates empty records for given user's interactions and updates their
	 * exposure status accordingly to the user's current test and exposure status.
	 * 
	 * @param user         The user whose interactions will be created records for.
	 * @param userLineNum  The line number where the user's record starts on in the
	 *                     file database.
	 * @param testStatus   The test status of user.
	 * @param interactions The list of interactions of user who will be created
	 *                     empty records for.
	 */
	public void createEmptyRecordsForInteractions(User user, int userLineNum, String testStatus, String interactions) {

		// if there are no interactions, return and don't create any empty records.
		if (interactions.equals(""))
			return;

		// otherwise, create empty records.

		// Split interactions into separate names and store into array.
		String[] interactionNames = interactions.toUpperCase().split(", ");

		// for every interaction name
		for (String name : interactionNames) {

			// create a new empty record with the name of the interaction and the only
			// interaction of that empty record
			// is the user. Store the line number of the new empty record in
			// lineNumInteractionRecords
			int lineNumInteractionRecords = database.writeNewUser(new User(name), "", user.getName());

			// store the line number of the user's record into the empty record for future
			// access to empty record
			database.writeInteractionsRecLineNum(lineNumInteractionRecords, "" + userLineNum);

			// store the line number of the empty record into the user's record for future
			// access to the empty records for the interactions
			database.writeInteractionsRecLineNum(user, "" + lineNumInteractionRecords);

			// if the user's test status is positive
			if (testStatus.toUpperCase().equals("TESTED POSITIVE")) {
				// update the exposure status of the empty record to 1
				updateInteractionsExposure(lineNumInteractionRecords, 1);
			}

			// read the exposure status of the user
			String exposureStat = database.readExposureStat(user);

			// if the exposure status of the user is not empty
			if (!exposureStat.equals("")) {
				// update the exposure status of the empty record to the user's exposure status
				// + 1
				updateInteractionsExposure(lineNumInteractionRecords, Integer.parseInt(exposureStat) + 1);
			}
		}
	}

	/**
	 * Registers a user, if user record has an empty record already edit that empty
	 * record. If an empty record for the user is not found, create a new record.
	 * 
	 * @param user         The user to be registered.
	 * @param testStatus   the test status of the new user to be registered.
	 * @param interactions The list of names of the user interactions.
	 */
	public void registerNewUser(User user, String testStatus, String interactions) {

		// if a record with the name of the user exists in the database and that record
		// is an empty record, edit that record.
		if (database.nameExistsInDb(user) && !database.userFullyRegistered(user)) {

			// find all the empty records in the database and store their record line number
			// in registeredUserRecords.
			ArrayList<Integer> unregisteredUserRecords = database.findUnregisteredUser(user);

			// flag indicating if an empty record matching the user trying to register has
			// been found.
			boolean foundFirstMatchingRecord = false;

			// line number of the first matching empty record
			int firstMatchingRecordLineNum = -1;

			// list of interactions of the user trying to register converted to uppercase
			String newInteractionsRecToMake = interactions.toUpperCase();

			// for every empty record
			for (Integer userRecordLineNum : unregisteredUserRecords) {

				// read the only interaction of the empty record
				String originalInteraction = database.readInteractions(userRecordLineNum)[0];

				// if the empty record's only interaction matches one of the interactions of the
				// user trying to register, this confirms that the empty record belongs to the
				// user trying to register
				if (newInteractionsRecToMake.contains(originalInteraction)) {

					// remove that one interaction that the empty record already has from the string
					// containing new interactions to be added when registering the user
					newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction + ", "), "");
					newInteractionsRecToMake = newInteractionsRecToMake.replace((", " + originalInteraction), "");
					newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction), "");

					// if this is the first empty record found matching the user trying to register
					if (!foundFirstMatchingRecord) {
						// make flag true
						foundFirstMatchingRecord = true;

						// store the line number of the first empty record matching the user trying to
						// register
						firstMatchingRecordLineNum = userRecordLineNum;

						// if this is not the first empty record found matching the user trying to
						// register and another matching record ahs been found
					} else {
						// merge this empty matching record to the first empty record
						database.mergeRecords(firstMatchingRecordLineNum, userRecordLineNum);
					}
				}
			}

			// if an empty record was found matching the user trying to register
			if (foundFirstMatchingRecord) {

				// read the current exposure status of the empty record and store in "exposure"
				String exposure = database.readExposureStat(firstMatchingRecordLineNum);

				// if the test status of the user trying to register is positive, write the
				// exposure status empty
				if (testStatus.equals("TESTED POSITIVE"))
					exposure = "";

				// edit the first empty record and add the user's information, exposure status,
				// and interactions
				database.writeEntireUserInfo(user, testStatus, exposure, newInteractionsRecToMake,
						firstMatchingRecordLineNum);

				// create empty records for the user's interactions
				createEmptyRecordsForInteractions(user, firstMatchingRecordLineNum, testStatus,
						newInteractionsRecToMake);

				// if the test status of the user trying to register is positive, call recursive
				// method that will update all of the user's interactions records to exposure
				// status 1 (user's level is 0 + 1)
				if (testStatus.toUpperCase().equals("TESTED POSITIVE"))
					updateInteractionsExposure(firstMatchingRecordLineNum, 0);

				// completed registering user
				return;
			}
		}

		// if an empty record for the user does not exist yet, create a new record for
		// the user in the database.
		createNewUser(user, testStatus, interactions);

	}

	/**
	 * Gets the user's COVID-19 test status .
	 * 
	 * @param user The user that the system will be checking the test status for.
	 * @return The user's COVID test status.
	 */
	public String getTestStatus(User user) {
		return database.readTestStatus(user);
	}

	/**
	 * Gets the user's exposure status.
	 * 
	 * @param user The user that the system will be checking the exposure status
	 *             for.
	 * @return The user's exposure status.
	 */
	public String getExposureStatus(User user) {

		// read exposure status of user from database
		String exp = database.readExposureStat(user);

		// read test status of user from database
		String testStat = database.readTestStatus(user);

		if (!exp.equals((""))){
			// convert exposure status to word form
			int exposure = Integer.parseInt(exp);
			switch (exposure) {
			case 1:
				exp = "FIRST-DEGREE";
				break;
			case 2:
				exp = "SECOND-DEGREE";
				break;
			case 3:
				exp = "THIRD-DEGREE";
				break;
			default:
				exp = "";
			}

		}

		return exp;

	}

	/**
	 * Retrieves an array of the names of the user's interactions.
	 * 
	 * @param user The user's whose interaction is being requested.
	 * @return An array of the user's interactions.
	 */
	public String[] getUserInteractions(User user) {
		return database.readInteractions(user);
	}

	/**
	 * Checks if a user with the same name and address already exists in the
	 * database.
	 * 
	 * @param user The user that the database will check for.
	 * 
	 * @return True if a user with the same name and address already exists in the
	 *         database, false if the user does not exist.
	 */
	public boolean userAlrRegistered(User user) {
		// check if the name exists in the database and if the record has an address
		// (fully registered).
		return database.nameExistsInDb(user) && database.userFullyRegistered(user);
	}

	/**
	 * Adds an interaction to the user's current list of interactions in the
	 * database.
	 * 
	 * @param user        The user who is adding an interaction.
	 * @param interaction The name of the person whom the user has interacted with.
	 */
	public void addInteractions(User user, String interaction) {

		// retrieve list of all empty records that matches the user
		ArrayList<Integer> unregisteredUserRecords = database.findUnregisteredUser(user);

		// if empty records matching the user exist, the another user must have added
		// the current user as an interaction and therefore an empty record for the
		// current user was created.

		// for every empty record matching the user
		for (Integer userRecordLineNum : unregisteredUserRecords) {

			// read the only interaction of the empty record
			String originalInteraction = database.readInteractions(userRecordLineNum)[0];

			// if the empty record's only interaction matches one of the interactions of the
			// user, this confirms that the empty record belongs to the
			// user trying to add interactions
			if (interaction.toUpperCase().contains(originalInteraction)) {

				// store the record line number of the record who originally created the empty
				// record (this is the record of an interaction the user is trying to add)
				String[] toMergeOriginalInteractionRecLineNum = database.readInteractionsRecLineNum(userRecordLineNum);

				// parse record line number into integer
				int interactionOriginalRecLineNum = Integer.parseInt(toMergeOriginalInteractionRecLineNum[0]);

				// merge empty record to record of user
				database.mergeRecords(database.findRegisteredUser(user), userRecordLineNum);

				// read exposure status of the user adding interactions
				String exposureStat = database.readExposureStat(user);

				// if the user's exposure status is not empty, update the user's new interaction
				// record to user's exposure status + 1.
				if (!exposureStat.equals(""))
					updateInteractionsExposure(interactionOriginalRecLineNum, Integer.parseInt(exposureStat) + 1);

				// completed adding interaction
				return;
			}
		}

		// if no empty records for the user has been found (meaning the interaction to
		// be added does not have an existing record), add new interaction to the user's
		// record
		database.writeInteractions(user, interaction);

		// create empty record for the interaction
		createEmptyRecordsForInteractions(user, database.findRegisteredUser(user), database.readTestStatus(user),
				interaction);
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
		if (status.equals("TESTED POSITIVE")) {
			updateInteractionsExposure(database.findRegisteredUser(user), 0);
			database.writeExposureStatus(user, 0);
		}
	}

	/**
	 * Updates the exposure status of the current user to exposureLevel and all of
	 * the people on the user's interactions list to epxosureLevel+1.
	 * 
	 * @param userLineNum   The line number of the user's record.
	 * @param exposureLevel The exposure level of the user.
	 */
	public void updateInteractionsExposure(int userLineNum, int exposureLevel) {

		// if the user's test status is not positive, update the exposureLevel in the
		// user's record
		if (!database.readTestStatus(userLineNum).equals("TESTED POSITIVE"))
			database.writeExposureStatus(userLineNum, exposureLevel);

		// Gets an array of all record line numbers of the user's interactions
		String[] interactionsRecLineNum = database.readInteractionsRecLineNum(userLineNum);

		// if the exposure status of the user is less than 3 (third degree)
		if (exposureLevel < 3) {
			// For every record line number
			for (String lineNum : interactionsRecLineNum) {

				// parse into an integer
				int intLineNum = Integer.parseInt(lineNum);

				// update the exposure status of the user's interaction to the eposure status of
				// the current user+1.
				updateInteractionsExposure(intLineNum, exposureLevel + 1);
			}
		}
	}

}
