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
		int userLineNum = database.writeNewUser(user, testStatus, interactions);
		createEmptyRecordsForInteractions(user, userLineNum, testStatus, interactions);
		

	}

	public void createEmptyRecordsForInteractions(User user, int userLineNum, String testStatus, String interactions) {
//		for (String s: database.databaseLines)
//			System.out.println(s);
		if (interactions.equals(""))
			return;
		String[] interactionNames = interactions.toUpperCase().split(", ");
		for (String name : interactionNames) {
			User existingInteractionRec = findExistingInteractionRecord(user, name);
//			System.out.println(existingInteractionRec);
			if (existingInteractionRec != null) {
				database.writeInteractions(existingInteractionRec, name);
				database.writeInteractionsRecordLineNum(existingInteractionRec, "" + userLineNum);
				continue;
			}
			int lineNumInteractionRecords = database.writeNewUser(new User(name), "", user.getName());
			database.writeInteractionsRecLineNum(lineNumInteractionRecords, "" + userLineNum);
			database.writeInteractionsRecordLineNum(user, "" + lineNumInteractionRecords);
			System.out.println("TEST STATUS: "+testStatus);
			if (testStatus.toUpperCase().equals("TESTED POSITIVE"))
				updateInteractionsExposure(lineNumInteractionRecords, 1);
			String exposureStat = database.readExposureStat(user);
			if (!exposureStat.equals(""))
				updateInteractionsExposure(lineNumInteractionRecords, Integer.parseInt(exposureStat) + 1);
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
			boolean foundFirstMatchingRecord = false;
			int firstMatchingRecord = -1;
			String newInteractionsRecToMake = interactions.toUpperCase();
			for (Integer userRecordLineNum : unregisteredUserRecords) {
				String originalInteraction = database.readInteractions(userRecordLineNum)[0];
				if (interactions.toUpperCase().contains(originalInteraction)) {
					if (!foundFirstMatchingRecord) {
						newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction + ", "), "");
						newInteractionsRecToMake = newInteractionsRecToMake.replace((", " + originalInteraction), "");
						newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction), "");
						foundFirstMatchingRecord = true;
						firstMatchingRecord = userRecordLineNum;
					} else {
						System.out.println(
								"firstMatchingRec: " + firstMatchingRecord + "toMergeRec: " + userRecordLineNum);
						newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction + ", "), "");
						newInteractionsRecToMake = newInteractionsRecToMake.replace((", " + originalInteraction), "");
						newInteractionsRecToMake = newInteractionsRecToMake.replace((originalInteraction), "");
						database.mergeRecords(firstMatchingRecord, userRecordLineNum);
					}
				}
			}
			if (foundFirstMatchingRecord) {
				database.writeEntireUserInfo(user, testStatus, database.readExposureStat(user), newInteractionsRecToMake, firstMatchingRecord);
				createEmptyRecordsForInteractions(user, firstMatchingRecord, testStatus, newInteractionsRecToMake);
				System.out.println("TEST STATUS: "+testStatus);
				if (testStatus.toUpperCase().equals("TESTED POSITIVE"))
					updateInteractionsExposure(firstMatchingRecord, 0);
				return;
			}
		}

		createNewUser(user, testStatus, interactions);

	}

	/**
	 * Determines if two users have interacted with each other.
	 * 
	 * @param user1 first user
	 * @param user2 second user
	 * @return True if both users interacted with each other, false if they have
	 *         not.
	 */
	public boolean bidirectionalInteraction(User user1, User user2) {
		String[] user1Interactions = database.readInteractions(user1);
////		System.out.println("\nUser1: " + user1 + "interaction user1: ");
//		for (String u: user1Interactions)
//			System.out.print(u+"\t");

		String[] user2Interactions = database.readInteractions(user2);
//		System.out.println("\nUser2: " + user2 +"interaction user2: ");
//		for (String u: user2Interactions)
//			System.out.print(u+"\t");

		boolean user1InteractedUser2 = false;
		for (String name : user1Interactions) {
			if (name.equals(user2.getName()))
				user1InteractedUser2 = true;
		}

		if (!user1InteractedUser2)
			return false;

		boolean user2InteractedUser1 = false;
		for (String name : user2Interactions) {
			if (name.equals(user1.getName()))
				user2InteractedUser1 = true;
		}

		if (!user2InteractedUser1)
			return false;

		return true;
	}

	/**
	 * Finds existing record of given interaction name of a user.
	 * 
	 * @param user            User whose interaction's record is being searched for
	 * @param interactionName name of interaction whose record is being search for
	 * @return User object of given interaction if exists, null if it does not exist
	 */
	public User findExistingInteractionRecord(User user, String interactionName) {
		ArrayList<User> records = database.userRecords();
//		System.out.print("User: " + user + "\t\tInteractionName: " + interactionName);

//		System.out.println("All User Records");
//		for (User u: records)
//			System.out.print(u + "\t\t");
		interactionName = interactionName.toUpperCase();

		for (User currentRec : records) {
			if (currentRec.getName().equals(interactionName) && bidirectionalInteraction(user, currentRec))
				return currentRec;
		}

		return null;
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
		String exp = database.readExposureStat(user);
		
		String testStat = database.readTestStatus(user);
		if (testStat.equals("TEST NEGATIVE") || testStat.equals("NOT TESTED")) {
			if (!exp.equals("")) {
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
				}
			}
		}
		
		return exp;
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

		createEmptyRecordsForInteractions(user, database.findRegisteredUser(user), database.readTestStatus(user),
				interactions);
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
		System.out.println("status: "+status);
		if (status.equals("TESTED POSITIVE")) {
			System.out.println("IN");
			updateInteractionsExposure(database.findUser(user), 0);
		}
	}

	/**
	 * Update the exposure status of all the people on the interactions list.
	 * 
	 * @param userLineNum Line number of the user's record whose interactions'
	 *                    exposure status will be updated.
	 * @param status      Status of current user to update interaction's status
	 */
	public void updateInteractionsExposure(int userLineNum, int exposureLevel) {

		System.out.println("testing for: "+userLineNum+"\t\t with exposure level "+exposureLevel);
		if (!database.readTestStatus(userLineNum).equals("TESTED POSITIVE"))
			database.writeExposureStatus(userLineNum, exposureLevel);

		// Gets list of all user's interactions
		String[] interactionsRecLineNum = database.readInteractionsRecLineNum(userLineNum);
		for (String lineNum : interactionsRecLineNum) {
			int intLineNum = Integer.parseInt(lineNum);
			if (exposureLevel < 3)
				updateInteractionsExposure(intLineNum, exposureLevel + 1);
		}
		// Goes through and marks what degree of exposure user has
	}

}
