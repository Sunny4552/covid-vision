package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

//NOTE: Database will be in the form
//FirstName LastName|Street Address, City, State ZipCode
//Test Status
//Exposure Status
//Interaction#1|Interaction#2-Interaction#3| …… 
//InteractionLineNum#1|InteractionLineNum#2|InteractionLineNum#3 ....

/**
 * Simulates a database that stores user's names, test status, exposure status,
 * and interactions.
 * 
 * @author Thi Nguyen, Minh Nguyen, Sunny Mistry, T.K. Bui
 *
 */
public class UserDb {
	File databaseFile;
	List<String> databaseLines; // all the lines of the database
	int numRecords; // number of records in database

	/**
	 * Constructs a user database with a given file path.
	 * 
	 * @param filePath Path to file.
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
	 * Searches for the given name.
	 * 
	 * @param user The user whose name is to be searched for.
	 * @return True if the name is found.
	 */
	public boolean nameExistsInDb(User user) {
		// if the user can't be found in the database
		if (findUser(user) == -1) {
			return false;
		}

		// if the user can be found
		return true;
	}

	/**
	 * Determines if the user is fully registered or if the user is just created
	 * from a list of interactions.
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
	 * Adds a new user to the database.
	 * 
	 * @param user         The User object that contains the user's name and
	 *                     address.
	 * @param testStatus   The user's Covid test status.
	 * @param interactions The user's interactions.
	 * @return The line where the new user record is written.
	 */
	public int writeNewUser(User user, String testStatus, String interactions) {
		// since new user is being added to the bottom of the file, the line where the
		// new user record will be written is equal to the size databaseLines
		int newUserRecNum = databaseLines.size();

		// retrieve the user's name and address
		String userInfo = user.toString();

		// add the user's name and address to database ArrayList
		databaseLines.add(userInfo);

		// add test status to database
		testStatus = testStatus.toUpperCase();
		databaseLines.add(testStatus);

		// since interactions passed in is in the format firstName lastName, firstName
		// lastName, firstName lastName, etc.
		// need to convert interactions to format firstName lastName|firstName lastName|
		// etc.
		interactions = interactions.toUpperCase();
		interactions = interactions.replace(" ,", "|");
		interactions = interactions.replace(", ", "|");
		interactions = interactions.replace(",", "|");

		// add empty string for exposure status
		databaseLines.add("");

		// add interactions
		databaseLines.add(interactions + "|");

		// add empty string for interaction nums since there are none when creating a
		// new user
		databaseLines.add("");

		// add empty line between records
		databaseLines.add("");

		try {
			// Append to file
			FileWriter fw = new FileWriter(databaseFile, true);

			fw.write(userInfo + "\n");
			fw.write(testStatus + "\n");
			fw.write("\n"); // have empty line for exposure status
			fw.write(interactions + "\n");
			fw.write("\n"); // have empty line for interaction record numbers
			fw.write("\n"); // have empty line between records
			fw.close();

			numRecords++;
		} catch (IOException e) {
			e.printStackTrace();
		}

		// return
		return newUserRecNum;
	}

	/**
	 * Given a line number, rewrites the entire user record in the database.
	 * 
	 * @param user          The user whose record will be rewritten.
	 * @param testStatus    The user's Covid test status.
	 * @param exposureLevel The user's Covid exposure level.
	 * @param interactions  The user's interactions.
	 * @param recordLineNum The user's record line number.
	 */
	public void writeEntireUserInfo(User user, String testStatus, String exposureLevel, String interactions,
			int recordLineNum) {

		// ewwrite user info (name and address)
		String userInfo = user.toString();
		databaseLines.set(recordLineNum, userInfo); // overwrite user info.

		// rewrite test status
		testStatus = testStatus.toUpperCase();
		databaseLines.set(recordLineNum + 1, testStatus);

		// rewrite exposure status
		exposureLevel = exposureLevel.toUpperCase();
		databaseLines.set(recordLineNum + 2, exposureLevel);

		// append interactions
		interactions = interactions.replace(" ,", "|");
		interactions = interactions.replace(", ", "|");
		interactions = interactions.replace(",", "|");

		// retrieve the current interactions line
		String currentInteractions = databaseLines.get(getInteractionsLineNum(user));

		// change all interactions to uppercase
		interactions = interactions.toUpperCase();

		// if the interaction line is not empty, concatenate interactions with current
		// interactions and write to database
		if (!interactions.equals("")) {
			databaseLines.set(recordLineNum + 3, currentInteractions + interactions + "|");
		}

		// write changes to database
		writeToDatabaseFile();
	}

	/**
	 * Adds new interactions to the user's record.
	 * 
	 * @param user         The user whose interactions will be updated.
	 * @param interactions The names of interactions, separated by |, to be added to
	 *                     the user's current interactions list.
	 */
	public void writeInteractions(User user, String interactions) {

		// retrieve the user's record number
		int recordLineNum = findRegisteredUser(user);

		// write the new interactions into the user's record
		writeInteractions(recordLineNum, interactions);
	}

	/**
	 * Adds new interactions to the user's record.
	 * 
	 * @param recordLineNum The line number of the record of the user whose
	 *                      interactions will be updated.
	 * @param interactions  The names of interactions, separated by |, to be added
	 *                      to the user's current interactions list.
	 */
	public void writeInteractions(int recordLineNum, String interactions) {

		// retrieve the user's record number
		int interactionRecNum = getInteractionsLineNum(recordLineNum);

		// get the current interactions
		String currentLine = databaseLines.get(interactionRecNum);

		// concatenate the interactions with the user's current interactions
		databaseLines.set(interactionRecNum, currentLine + interactions.toUpperCase() + "|");

		// rewrite the database file
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's Covid test status in the database.
	 * 
	 * @param user   The user whose test status will be updated.
	 * @param status The user's Covid test status.
	 */
	public void writeTestStatus(User user, String status) {

		// get line number where user's test status is written
		int testStatLine = getTestStatLineNum(user);

		// change the status to the status passed into writeTestStatus
		databaseLines.set(testStatLine, status.toUpperCase());

		// rewrite database file
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's exposure status in the database.
	 * 
	 * @param user          The user whose exposure status will be updated.
	 * @param exposureLevel The user's exposure level.
	 */
	public void writeExposureStatus(User user, int exposureLevel) {

		// get exposure status line number
		int exposureStatLine = getExposureStatLineNum(user);

		// retrieve current exposure status
		String currentLine = databaseLines.get(exposureStatLine);

		// if nothing is written on the line
		if (!currentLine.equals("")) {
			int currentExpStat = Integer.parseInt(currentLine);

			// if the user's current exposure status is more severe than the exposureLevel,
			// skip rest of method
			if (currentExpStat <= exposureLevel) {
				return;
			}
		}

		// if the user's exposureLevel is 0, erase entire line
		if (exposureLevel == 0)
			databaseLines.set(exposureStatLine, "");

		// if the exposureLevel is more severe than the current one the user has, update
		// it to exposureLevel
		else {
			databaseLines.set(exposureStatLine, new Integer(exposureLevel).toString());
		}

		// rewrite database file
		writeToDatabaseFile();
	}

	/**
	 * Writes the exposure status of the user into the given the user's record line
	 * number.
	 * 
	 * @param userLineNum   The line number where the user's record start in the
	 *                      database file.
	 * @param exposureLevel The user's exposure level.
	 */
	public void writeExposureStatus(int userLineNum, int exposureLevel) {

		// exposure stat is 2 lines away from user record line number
		int exposureStatLine = userLineNum + 2;

		// retrieve current line in the database
		String currentLine = databaseLines.get(exposureStatLine);

		// if nothing is written on the line
		if (!currentLine.equals("")) {
			int currentExpStat = Integer.parseInt(currentLine);

			// if the user's current exposure status is more severe than the exposureLevel,
			// skip rest of method
			if (currentExpStat <= exposureLevel) {
				return;
			}
		}

		// if the user's exposureLevel is 0, erase entire line
		if (exposureLevel == 0)
			databaseLines.set(exposureStatLine, "");

		// if the exposureLevel is more severe than the current one the user has, update
		// it to exposureLevel
		else {
			databaseLines.set(exposureStatLine, new Integer(exposureLevel).toString());
		}

		// rewrite database file
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's interactions record line numbers in the database.
	 * 
	 * @param user                The user whose interactions record line numbers
	 *                            will be updated.
	 * @param interactionsLineNum Interaction record line numbers, separated by a |,
	 *                            that will be added to the user's record.
	 */
	public void writeInteractionsRecLineNum(User user, String interactionsLineNum) {

		// retrieve the interactions record line number
		int interactRecordsLineNum = getInteractionsRecLineNum(user);

		// retrieve the line that includes all of the user's interaction record line
		// numbers
		String currentLine = databaseLines.get(interactRecordsLineNum);

		// concatenate interactionsLineNum to the existing interaction record line
		// numbers
		databaseLines.set(interactRecordsLineNum, currentLine + interactionsLineNum.toUpperCase() + "|");

		// rewrite the database file
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's interactions record line numbers in the database.
	 * 
	 * @param lineNum             The record line number of the user whose
	 *                            interactions record line numbers will be updated.
	 * @param interactionsLineNum Interaction record line numbers, separated by a |,
	 *                            that will be added to the user's record.
	 */
	public void writeInteractionsRecLineNum(int lineNum, String interactionsLineNum) {
		// retrieve the interactions record line number
		int interactRecordsLineNum = getInteractionsRecLineNum(lineNum);

		// retrieve the line that includes all of the user's interaction record line
		// numbers
		String currentLine = databaseLines.get(interactRecordsLineNum);

		// concatenate interactionsLineNum to the existing interaction record line
		// numbers
		databaseLines.set(interactRecordsLineNum, currentLine + interactionsLineNum.toUpperCase() + "|");

		// rewrite the database file
		writeToDatabaseFile();
	}

	/**
	 * Returns a String array of the names from the user's interaction list.
	 * 
	 * @param user The user whose interactions will be returned.
	 * @return A string array of the names from the user's interaction list.
	 */
	public String[] readInteractions(User user) {

		// Retrieve interactions line
		int interactionsLineNum = getInteractionsLineNum(user);
		String interactions = databaseLines.get(interactionsLineNum);

		// Parse string into a String array, split where there is a |
		return interactions.split(("\\|"));
	}

	/**
	 * Returns a String array of the names from the user's interaction list.
	 * 
	 * @param lineNum The line number of where the user's record begins.
	 * @return A string array of the names from the user's interaction list.
	 */
	public String[] readInteractions(int lineNum) {

		// Retrieve interactions line
		int interactionsLineNum = getInteractionsLineNum(lineNum);
		String interactions = databaseLines.get(interactionsLineNum);

		// Parse string into a String array, split where there is a |
		return interactions.split(("\\|"));
	}

	/**
	 * Returns a String array of the names from the user's interaction list.
	 * 
	 * @param lineNumList A list of record line numbers of users whose interactions
	 *                    will be returned.
	 * @return An ArrrayList of String arrays containing the names from the user's
	 *         interaction list.
	 */
	public ArrayList<String[]> readInteractions(ArrayList<Integer> lineNumList) {
		ArrayList<String[]> listInteractions = new ArrayList<>();

		// for every interaction record line number
		for (Integer lineNum : lineNumList) {

			// retrieve the interactions
			String interactions = databaseLines.get(lineNum);

			// Parse string into a String array, split where there is a |
			// Add into ArrayList
			listInteractions.add(interactions.split(("\\|")));
		}

		return listInteractions;
	}

	/**
	 * Read the user's exposure status.
	 * 
	 * @param user The user whose exposure status will be checked.
	 * @return The user's exposure status.
	 */
	public String readExposureStat(User user) {
		// retrieve the user's exposure status and return it
		return databaseLines.get(getExposureStatLineNum(user));
	}

	/**
	 * Read the user's exposure status.
	 * 
	 * @param recordLineNum The record line number of the user's whose exposure
	 *                      status will be checked.
	 * @return The user's exposure status.
	 */
	public String readExposureStat(int recordLineNum) {
		return databaseLines.get(recordLineNum + 2);
	}

	/**
	 * Read the user's interactions' record line numbers.
	 * 
	 * @param userRecLineNum the user's record line number.
	 * @return String array containing the users' interactions' record line numbers.
	 */
	public String[] readInteractionsRecLineNum(int userRecLineNum) {

		// retrieves the user's interactions' record line numbers
		String interactionsRecLineNum = databaseLines.get((userRecLineNum + 4));

		// Parse string into a String array, split where there is a |
		return interactionsRecLineNum.split(("\\|"));
	}

	/**
	 * Returns the user's test status.
	 * 
	 * @param user The user whose test status will be returned.
	 * @return The user's test status.
	 */
	public String readTestStatus(User user) {

		// retrieve the line number where the user's test status is located
		int testStatLine = getTestStatLineNum(user);

		// retrieve and return the user's test status
		return databaseLines.get(testStatLine);
	}

	/**
	 * Returns the user's test status.
	 * 
	 * @param lineNum The line number of the user's record whose test status will be
	 *                returned.
	 * @return The user's test status.
	 */
	public String readTestStatus(int lineNum) {

		// retrieve the line number where the user's test status is located
		int testStatLine = getTestStatLineNum(lineNum);

		// retrieve and return the user's test status
		return databaseLines.get(testStatLine);
	}

	/**
	 * Searches for the given user with matching name.
	 * 
	 * @param user The user to be searched for.
	 * @return The line number where user record is found or -1 if the user can't be
	 *         found.
	 */
	private int findUser(User user) {
		int lineNum = 0;

		// search through the database
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);

			// if the user contains the user's name
			if (line.contains(user.getName())) {
				return lineNum;
			}
			lineNum++;
		}

		// if the user can't be found
		return -1;
	}

	/**
	 * Creates User object from given record line number.
	 * 
	 * @param lineNum The line number of record to read from.
	 * @return The User object of record at lineNum if record exists, null if no
	 *         record at lineNum
	 */
	public User retrieveUser(int lineNum) {

		// retrieve the user info from the database
		String line = databaseLines.get(lineNum);

		// if the line is empty
		if (line.equals(""))
			return null;

		// split the line retrieved
		String[] nameAddress = line.split("\\|");

		// if the line doesn't have an address
		if (noAddress(line)) {

			// return a User object with an empty address
			return new User(nameAddress[0]);
		}

		// if user has an address, create an new Address object
		Address userAddress = new Address(nameAddress[1]);

		// return a new user object with name and address
		return new User(nameAddress[0], userAddress);
	}

	/**
	 * Gets the list of User records from database.
	 * 
	 * @return ArrayList of User records in database
	 */
	public ArrayList<User> userRecords() {

		int currentLineNum = 0;
		ArrayList<User> records = new ArrayList<>();

		// go through the database
		while (currentLineNum < databaseLines.size()) {

			// retrieve user
			User retrieved = retrieveUser(currentLineNum);

			// if user exists
			if (retrieved != null)
				records.add(retrieved);
			currentLineNum += 6;
		}
		return records;
	}

	/**
	 * Searches for the user with matching name and address
	 * 
	 * @param user The user to be searched for.
	 * @return The line number where user record is found or -1 if the user can't be
	 *         found.
	 */
	public int findRegisteredUser(User user) {
		int lineNum = 0;

		// go through each record in database
		while (lineNum < databaseLines.size()) {

			// retrieve the line at lineNum
			String line = databaseLines.get(lineNum);

			// if the line contains the user and the address of user Object
			if (line.contains(user.getName()) && line.contains(user.getAddr().toString())) {
				return lineNum;
			}

			// go to next record
			lineNum += 6;
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

		// Retrieve all line nums that store records that has interacted with the user
		ArrayList<Integer> recordLines = new ArrayList<>();

		// go through records in database
		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);

			// if the user contains a user with no address
			if (line.contains(user.getName()) && (noAddress(line))) {

				// add the record line number to recordLines
				recordLines.add(lineNum);
			}

			// go to the next record
			lineNum += 6;
		}
		return recordLines;
	}

	/**
	 * Determines if given line has an address or not.
	 * 
	 * @param line The first line of a record to check/
	 * @return True if the line does not contain an address, False if it does.
	 */
	public boolean noAddress(String line) {
		// checks if there is a | without any address after
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

		// read exposure status of both records
		String expStat1 = readExposureStat(rec1LineNum);
		String expStat2 = readExposureStat(rec2LineNum);

		// if the second record's exposure status is not empty
		if (!expStat2.equals("")) {
			// and the first exposure status is empty, write the exposure status of the
			// second record to the first one. Or, if the second and first record's exposure
			// status are both not empty and the second record's exposure status is less
			// than than the first one, write the second exposure status to the first record
			if (expStat1.equals("") || Integer.parseInt(expStat2) < Integer.parseInt(expStat1)) {
				writeExposureStatus(rec1LineNum, Integer.parseInt(expStat2));
			}
		}

		// write interaction from rec2 into rec1
		String[] rec2Interactions = readInteractions(rec2LineNum); // get rec2 interactions
		writeInteractions(rec1LineNum, rec2Interactions[0]); // write rec2 interactions to rec1

		// write interactionLineNum from rec2 into rec1
		String[] rec2InteractionsLineNum = readInteractionsRecLineNum(rec2LineNum);
		writeInteractionsRecLineNum(rec1LineNum, rec2InteractionsLineNum[0]);

		// get the record line number of the only interaction of the second empty record
		int interactRecNum2 = Integer.parseInt(rec2InteractionsLineNum[0]);

		// read the record line numbers of the interactions of the original record (who
		// created the second empty record)
		String interactRecNum = databaseLines.get(interactRecNum2 + 4);

		// replace the empty record's line number with the merged record line number
		interactRecNum = interactRecNum.replace("" + rec2LineNum, "" + rec1LineNum);
		databaseLines.set(interactRecNum2 + 4, interactRecNum);

		// clear second record
		clearRecord(rec2LineNum);
	}

	/**
	 * Clears the record.
	 * 
	 * @param recLineNum The record line number of record that will be cleared.
	 */
	public void clearRecord(int recLineNum) {
		// set all portions of the record to empty strings
		databaseLines.set(recLineNum, "");
		databaseLines.set(recLineNum + 1, "");
		databaseLines.set(recLineNum + 2, "");
		databaseLines.set(recLineNum + 3, "");
		databaseLines.set(recLineNum + 4, "");

		// rewrite database file
		writeToDatabaseFile();
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
		// test status line is 2nd line in a user record (1 line away from first line of
		// user record)
		return findRegisteredUser(user) + 1;
	}

	/**
	 * Gets the line number in database file where the user's test status is
	 * written.
	 * 
	 * @param lineNum The line number of the user's record whose test status line
	 *                number will be returned.
	 * @return The line number in database file where the user's test status is
	 *         written.
	 */
	public int getTestStatLineNum(int lineNum) {
		// Retrieve test status line number
		// test status line is the 2nd line in a user record (1 line away from first
		// line of user record)
		return lineNum + 1;
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
		// exposures line is the 3rd line in a user record (2 lines away from first line
		// of user record)
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
		// interactions line is the 4th line in a user record (3 lines away from first
		// line of user record)
		return findRegisteredUser(user) + 3;
	}

	/**
	 * Gets the line number in database file where the user's interactions are
	 * written.
	 * 
	 * @param lineNum The line number of the user record whose interactions line
	 *                number will be returned.
	 * @return The line number in database file where the user's interactions are
	 *         written.
	 */
	public int getInteractionsLineNum(int lineNum) {
		// Retrieve interactions line number
		// interactions line is the 4th line in a user record (3 lines away from first
		// line of user record)
		return lineNum + 3;
	}

	/**
	 * Gets the line number in database file where the user's interactions' record
	 * line numbers are stored.
	 * 
	 * @param user The user whose interactions' record line numbers are stored.
	 * @return The line number in database file where the user's interactions'
	 *         record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(User user) {
		// interaction record line numbers is the 5th line in a user record (4 lines
		// away from first line of user record)
		return findRegisteredUser(user) + 4;
	}

	/**
	 * Gets the line number in database file where the user's interactions' record
	 * line numbers are stored.
	 * 
	 * @param lineNum The line number of the user's record whose interactions'
	 *                record line numbers are stored.
	 * @return The line number in database file where the user's interactions'
	 *         record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(int lineNum) {
		// interaction record line numbers is the 5th line in a user record (4 lines
		// away from first line of user record)
		return lineNum + 4;
	}
}
