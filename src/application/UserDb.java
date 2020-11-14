package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//NOTE: Database will be in the form
//FirstName LastName|Street Address, City, State ZipCode
//Test Status
//Exposure Status
//Interaction#1|Interaction#2-Interaction#3| …… 
//InteractionLineNum#1|InteractionLineNum#2|InteractionLineNum#3 ....

public class UserDb {
	File databaseFile;
	List<String> databaseLines;
	int numRecords;

	/**
	 * Constructs a user database with a given file path
	 * 
	 * @param filePath
	 */
	public UserDb(String filePath) {
		databaseFile = new File(filePath);
		try {
			// read entire file and parse lines into ArrayList
			databaseLines = Files.readAllLines(Paths.get(filePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Searches for the given name
	 * 
	 * @param user The use whose name is to be searched for.
	 * @return True if the name is found.
	 */
	public boolean nameExistsInDb(User user) {
		if (findUser(user) == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Determine if user is fully registered or just created from a list of
	 * interactions.
	 * 
	 * @param user The user to be searched for.
	 * @return True if the user is fully registered, False if user is just an
	 *         interaction.
	 */
	public boolean userFullyRegistered(User user) {
		if (findRegisteredUser(user) == -1) {
			return false;
		}
		return true;
	}

	/**
	 * Add a new user to the database.
	 * 
	 * @param user         The user's name and address.
	 * @param testStatus   The user's Covid test status.
	 * @param interactions The user's interactions.
	 */
	public int writeNewUser(User user, String testStatus, String interactions) {
		int newUserRecNum = databaseLines.size();
		String userInfo = user.toString();
		databaseLines.add(userInfo);
		testStatus = testStatus.toUpperCase();
		databaseLines.add(testStatus);
		interactions = interactions.toUpperCase();
		interactions = interactions.replace(" ,", "|");
		interactions = interactions.replace(", ", "|");
		interactions = interactions.replace(",", "|");
		databaseLines.add(""); // empty string for exposure status
		databaseLines.add(interactions + "|");
		databaseLines.add(""); // empty string for interaction nums
		databaseLines.add(""); // empty line between records

		try {
			// Append to file
			FileWriter fw = new FileWriter(databaseFile, true);

			fw.write(userInfo + "\n");
			fw.write(testStatus + "\n");
			fw.write("\n"); // Write empty string for exposure status
			fw.write(interactions + "\n");
			fw.write("\n");
			fw.write("\n");
			fw.close();
			numRecords++;
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(databaseLines.size());
		return newUserRecNum;
	}

	/**
	 * Given a line number, rewrite the entire user record in the database.
	 * 
	 * @param user          User whose record will be rewritten.
	 * @param testStatus    The user's Covid test status.
	 * @param interactions  The user's interactions.
	 * @param recordLineNum The user's record line number.
	 */
	public void writeEntireUserInfo(User user, String testStatus, String exposureLevel, String interactions,
			int recordLineNum) {

		// write user info
		String userInfo = user.toString();
		databaseLines.set(recordLineNum, userInfo); // overwrite user info.

		// rewrite test status
		testStatus = testStatus.toUpperCase();
		databaseLines.set(recordLineNum + 1, testStatus);

		// rewrite exposure
		exposureLevel = exposureLevel.toUpperCase();
		databaseLines.set(recordLineNum + 2, exposureLevel);

		// append interactions
		interactions.replace(" ,", "|");
		interactions.replace(", ", "|");
		interactions.replace(",", "|");
		String currentInteractions = databaseLines.get(getInteractionsLineNum(user));
		interactions = interactions.toUpperCase();
		databaseLines.set(recordLineNum + 3, currentInteractions + interactions + "|");

		try {
			FileWriter fw = new FileWriter(databaseFile);

			fw.write(userInfo + "\n");
			fw.write(testStatus + "\n");
			fw.write(exposureLevel);
			fw.write(interactions + "\n");
			fw.write("\n");// Write empty string for interactions record line number
			fw.write("\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds new interactions to the user's record.
	 * 
	 * @param user         The user whose interactions will be updated.
	 * @param interactions The names of people, separated by |, to be added to the
	 *                     user's current interactions list.
	 */
	public void writeInteractions(User user, String interactions) {
		int recordLineNum = findRegisteredUser(user);
		writeInteractions(recordLineNum, interactions);
	}

	/**
	 * Adds new interactions to the user's record.
	 * 
	 * @param recordLineNum         The line number of the record of the user whose interactions will be updated.
	 * @param interactions The names of people, separated by |, to be added to the
	 *                     user's current interactions list.
	 */
	public void writeInteractions(int recordLineNum, String interactions) {
		// check if interaction already exits, do not write
		String[] existingInteractions = readInteractions(recordLineNum);
		String[] newInteractions = interactions.split("|");

		for (String newInteraction : newInteractions) {
			for (String existingInteraction : existingInteractions) {
				if (newInteraction.toUpperCase().equals(existingInteraction))
					interactions.replace("newInteraction", "");
			}
		}

		int interactionRecNum = recordLineNum + 3;
		String currentLine = databaseLines.get(interactionRecNum);
		databaseLines.set(interactionRecNum, currentLine + interactions.toUpperCase() + "|");
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's Covid test status in the database.
	 * 
	 * @param user   The user whose test status will be updated.
	 * @param status The user's Covid test status.
	 */
	public void writeTestStatus(User user, String status) {
		int testStatLine = getTestStatLineNum(user);
		databaseLines.set(testStatLine, status.toUpperCase());
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's exposure status in the database.
	 * 
	 * @param user          The user whose exposure status will be updated.
	 * @param exposureLevel The user's exposure level.
	 */
	public void writeExposureStatus(User user, int exposureLevel) {
		int exposureStatLine = getExposureStatLineNum(user);
		int currentLine = Integer.parseInt(databaseLines.get(exposureStatLine));
		if (currentLine >= exposureLevel) {
			return;
		}
		databaseLines.set(exposureStatLine, new Integer(exposureLevel).toString());
		writeToDatabaseFile();
	}

	public void writeExposureStatus(int userLineNum, int exposureLevel) {
		// exposure stat is 2 lines away from user record line number
		int exposureStatLine = userLineNum + 2;
		int currentLine = Integer.parseInt(databaseLines.get(exposureStatLine));
		if (currentLine >= exposureLevel) {
			return;
		}
		databaseLines.set(userLineNum + 2, new Integer(exposureLevel).toString());
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's interactions record line numbers in the database.
	 * 
	 * @param user                The user whose interactions record line numbers
	 *                            will be updated.
	 * @param interactionsLineNum interaction record line numbers, separated by a |,
	 *                            that will be added
	 */
	public void writeInteractionsRecordLineNum(User user, String interactionsLineNum) {
		String[] existingInteractionsRecLineNum = readInteractionsRecLineNum(findRegisteredUser(user));
		String[] newInteractionsRecLineNum  = interactionsLineNum.split("|");

		for (String newInteractionRecLineNum : newInteractionsRecLineNum) {
			for (String existingInteractionRecLineNum : existingInteractionsRecLineNum) {
				if (newInteractionRecLineNum.toUpperCase().equals(existingInteractionRecLineNum))
					interactionsLineNum.replace(newInteractionRecLineNum, "");
			}
		}
		
		int interactRecordsLineNum = getInteractionsRecLineNum(user);
		String currentLine = databaseLines.get(interactRecordsLineNum);
		databaseLines.set(interactRecordsLineNum, currentLine + interactionsLineNum.toUpperCase() + "|");
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's interactions record line numbers in the database.
	 * 
	 * @param user                The user whose interactions record line numbers
	 *                            will be updated.
	 * @param interactionsLineNum interaction record line numbers, separated by a |,
	 *                            that will be added
	 */
	public void writeInteractionsRecLineNum(int lineNum, String interactionsLineNum) {
		int interactRecordsLineNum = getInteractionsRecLineNum(lineNum);
		String currentLine = databaseLines.get(interactRecordsLineNum);
		databaseLines.set(interactRecordsLineNum, currentLine + interactionsLineNum.toUpperCase() + "|");
		writeToDatabaseFile();
	}

	/**
	 * Returns the list of names from the user's interaction list given user.
	 * 
	 * @param user The user whose interactions will be returned.
	 * @return A string array of the of names from the user's interaction list.
	 */
	public String[] readInteractions(User user) {

		// Retrieve interactions line
		int interactionsLineNum = getInteractionsLineNum(user);
		String interactions = databaseLines.get(interactionsLineNum);

		// Parse string into
		return interactions.split(("\\|"));
	}

	/**
	 * Returns the list of names from the user's interaction list given line number
	 * of user record
	 * 
	 * @param user The user whose interactions will be returned.
	 * @return A string array of the of names from the user's interaction list.
	 */
	public String[] readInteractions(int lineNum) {

		// Retrieve interactions line
		int interactionsLineNum = getInteractionsLineNum(lineNum);
		String interactions = databaseLines.get(interactionsLineNum);

		// Parse string
		return interactions.split(("\\|"));
	}

	/**
	 * Returns a list of all the list of names from the user's interaction list
	 * given a list of line number of user record
	 * 
	 * @param lineNumList A list of line numbers of users whose interactions will be
	 *                    returned.
	 * @return A list of string array of the of names from the users' interaction
	 *         list.
	 */
	public ArrayList<String[]> readInteractions(ArrayList<Integer> lineNumList) {
		ArrayList<String[]> listInteractions = new ArrayList<>();
		for (Integer lineNum : lineNumList) {
			String interactions = databaseLines.get(lineNum);

			// Parse string into
			listInteractions.add(interactions.split(("\\|")));
		}

		return listInteractions;
	}
	
	public String readExposureStat(User user) {
		return databaseLines.get(getExposureStatLineNum(user));
	}

	public String[] readInteractionsRecLineNum(int userRecLineNum) {
		String interactionsRecLineNum = databaseLines.get((userRecLineNum + 4));
		// Parse string
		return interactionsRecLineNum.split(("\\|"));
	}

	/**
	 * Returns the user's test status given user.
	 * 
	 * @param user The user whose test status will be returned.
	 */
	public String readTestStatus(User user) {
		int testStatLine = getTestStatLineNum(user);
		return databaseLines.get(testStatLine);
	}

	/**
	 * Searches for the given user with matching name
	 * 
	 * @param user The user to be searched for.
	 * @return The line number where user record is found or -1 if the user can't be
	 *         found.
	 */
	public int findUser(User user) {
		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);
			if (line.contains(user.getName())) {
				return lineNum;
			}
			lineNum++;
		}
		return -1;
	}

	/**
	 * Creates User object from given record line number.
	 * 
	 * @param lineNum line number of record to read from
	 * @return User object of record at lineNum
	 */
	public User retrieveUser(int lineNum) {
		String line =  databaseLines.get(lineNum);
		String[] nameAddress = line.split("\\|");
		if (noAddress(line)) {
			return new User(nameAddress[0]);
		}
		Address userAddress = new Address(nameAddress[1]);
		return new User(nameAddress[0], userAddress);
	}

	/**
	 * Gets list of User records in database
	 * 
	 * @return ArrayList of User records in database
	 */
	public ArrayList<User> userRecords() {

		int currentLineNum = 0;
		ArrayList<User> records = new ArrayList<>();

		while (currentLineNum < databaseLines.size()) {
			records.add(retrieveUser(currentLineNum));
			currentLineNum += 6;
		}
		return records;
	}

	/**
	 * Searches for the user with matching name and address
	 * 
	 * @param user The user to be searched for.
	 * @return The line number where user record that is found or -1 if the user
	 *         can't be found.
	 */
	public int findRegisteredUser(User user) {
		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);
			if (line.contains(user.getName()) && line.contains(user.getAddr().toString())) {
				return lineNum;
			}
			lineNum++;
		}
		return -1;
	}

	/**
	 * Searches for not fully registered users (users made from interactions) with
	 * matching name
	 * 
	 * @param user The user with name to be searched for.
	 * @return Array of line numbers where unregistered user record with matching
	 *         name is found.
	 */
	public ArrayList<Integer> findUnregisteredUser(User user) {
		ArrayList<Integer> recordLines = new ArrayList<>(); // retreive all line nums that store recs that has
															// interacted with user

		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);
			//
			if (line.contains(user.getName()) && (noAddress(line))) {
				recordLines.add(lineNum);
			}
			lineNum++;
		}
		return recordLines;
	}
	
	/**
	 * Determine if given line has an address or not.
	 * @param line first line of a record to check
	 * @return true if line does not contain an address, false if it does
	 */
	public boolean noAddress(String line)
	{
		return (line.substring(line.indexOf("|")).length() == 1);
	}

	/**
	 * Writes entire databaseLines ArrayList to database File
	 */
	public void writeToDatabaseFile() {
		try {
			FileWriter fw = new FileWriter(databaseFile);

			// loop through array and print line onto file
			for (String line : databaseLines) {
				fw.write(line + "\n");
			}
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Merges two records together given the record line number of both records.
	 * 
	 * @param rec1LineNum The record line number of the record where the second
	 *                    record will merge to.
	 * @param rec2LineNum The record line number of the record that will be merged
	 *                    and cleared.
	 */
	public void mergeRecords(int rec1LineNum, int rec2LineNum) {
		// combine interactions
		String[] rec2Interactions = readInteractions(rec2LineNum); // get rec2 interactions
		writeInteractions(rec1LineNum, rec2Interactions[0]); // write rec2 interactions to rec1

		// combine the interaction line num
		String[] rec2InteractionsLineNum = readInteractionsRecLineNum(rec2LineNum);
		writeInteractionsRecLineNum(rec1LineNum, rec2InteractionsLineNum[0]);

		// go to record2 interactions and update their interact record line num with new
		// number
		writeInteractions(Integer.parseInt(rec2InteractionsLineNum[0]), rec2Interactions[0]);
		writeInteractionsRecLineNum(Integer.parseInt(rec2InteractionsLineNum[0]), "" + rec1LineNum);

		// clear second record
	}

	public void clearRecord(int recLineNum) {
		databaseLines.set(recLineNum, "");
		databaseLines.set(recLineNum + 1, "");
		databaseLines.set(recLineNum + 2, "");
		databaseLines.set(recLineNum + 3, "");
		databaseLines.set(recLineNum + 4, "");
	}

	/**
	 * Gets the line number in database file where the user's test status is
	 * written.
	 * 
	 * @param user The user whose test status line number will be returned.
	 * @return The line number in database file where the user's test status is
	 *         written.
	 */
	public int getTestStatLineNum(User user) {
		// Retrieve test status line number
		// interactions line is the 2nd line in a user record
		return findRegisteredUser(user) + 1;
	}

	/**
	 * Gets the line number in database file where the user's exposure status is
	 * written.
	 * 
	 * @param user The user whose exposure status line number will be returned.
	 * @return The line number in database file where the user's exposure status is
	 *         written.
	 */
	public int getExposureStatLineNum(User user) {
		// Retrieve test status line number
		// interactions line is the 2nd line in a user record
		return findRegisteredUser(user) + 2;
	}

	/**
	 * Gets the line number in database file where the user's interactions are
	 * written.
	 * 
	 * @param user The user whose interactions line number will be returned.
	 * @return The line number in database file where the user's interactions are
	 *         written.
	 */
	public int getInteractionsLineNum(User user) {
		// Retrieve interactions line number
		// interactions line is the 3rd line in a user record
		return findRegisteredUser(user) + 3;
	}

	public int getInteractionsLineNum(int lineNum) {
		// Retrieve interactions line number
		// interactions line is the 3rd line in a user record
		return lineNum + 3;
	}

	/**
	 * Gets the line number in database file where the user's interactions's record
	 * line numbers are stored.
	 * 
	 * @param user The user whose interaction record line numbers are stored.
	 * @return The line number in database file where the user's interactions's
	 *         record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(User user) {
		return findRegisteredUser(user) + 4;
	}

	/**
	 * Gets the line number in database file where the user's interactions's record
	 * line numbers are stored.
	 * 
	 * @param lineNum Line number of the user's record whose interaction record line
	 *                numbers are stored.
	 * @return The line number in database file where the user's interactions's
	 *         record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(int lineNum) {
		return lineNum + 4;
	}
}
