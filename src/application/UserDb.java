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

public class UserDb {
	File databaseFile;
	List<String> databaseLines;

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
		databaseLines.add(testStatus.toUpperCase());
		databaseLines.add(" , ");
		interactions.replace(" ,", "|");
		interactions.replace(", ", "|");
		interactions.replace(",", "|");
		databaseLines.add(interactions.toUpperCase() + "|");
		

		try {
			// Append to file
			FileWriter fw = new FileWriter(databaseFile, true);

			fw.write(userInfo + "\n");
			fw.write(testStatus + "\n");
			fw.write("\n"); // Write empty string for exposure status
			fw.write(interactions + "\n");
			fw.write("\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		return newUserRecNum;
	}

	/**
	 * Given a line number, rewrite the entire user record in the database.
	 * @param user User whose record will be rewritten.
	 * @param testStatus The user's Covid test status.
	 * @param interactions The user's interactions.
	 * @param recordLineNum The user's record line number.
	 */
	public void writeEntireUserInfo(User user, String testStatus, String exposureLevel, String interactions, int recordLineNum) {
		String userInfo = user.toString();
		databaseLines.add(userInfo);
		databaseLines.add(testStatus.toUpperCase());
		databaseLines.add(" , ");
		interactions.replace(" ,", "|");
		interactions.replace(", ", "|");
		interactions.replace(",", "|");
		databaseLines.add(interactions.toUpperCase() + "|");

		try {
			// Append to file
			FileWriter fw = new FileWriter(databaseFile);

			fw.write(userInfo + "\n");
			fw.write(testStatus + "\n");
			fw.write(exposureLevel); 
			fw.write(interactions + "\n");
			fw.write("\n");//Write empty string for interactions record line number
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds new interactions to the user's record.
	 * 
	 * @param user         The user whose interactions will be updated.
	 * @param interactions The names of people, separated by |, to be added to the user's current
	 *                     interactions list.
	 */
	public void writeInteractions(User user, String interactions) {
		int interactionsLineNum = getInteractionsLineNum(user);
		String currentLine = databaseLines.get(interactionsLineNum);
		databaseLines.set(interactionsLineNum, currentLine + interactions.toUpperCase() + "|");
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's Covid test status in the database.
	 * 
	 * @param user The user whose test status will be updated.
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
	 * @param user The user whose exposure status will be updated.
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
	
	public void writeExposureStatus(int userLineNum, int exposureLevel){
		//exposure stat is 2 lines away from user record line number
		int exposureStatLine = userLineNum+2;
		int currentLine = Integer.parseInt(databaseLines.get(exposureStatLine));
		if (currentLine >= exposureLevel) {
			return;
		}
		databaseLines.set(userLineNum + 2,new Integer(exposureLevel).toString());
		writeToDatabaseFile(); 
	}
	
	/**
	 * Updates the user's interactions record line numbers in the database.
	 * @param user The user whose interactions record line numbers will be updated.
	 * @param interactionsLineNum interaction record line numbers, separated by a |, that will be added
	 */
	public void writeInteractionsRecordLineNum (User user, String interactionsLineNum) {
		int interactRecordsLineNum = getInteractionsRecLineNum (user);
		String currentLine = databaseLines.get(interactRecordsLineNum);
		databaseLines.set(interactRecordsLineNum, currentLine + interactionsLineNum.toUpperCase() + "|");
		writeToDatabaseFile();
	}
	
	/**
	 * Updates the user's interactions record line numbers in the database.
	 * @param user The user whose interactions record line numbers will be updated.
	 * @param interactionsLineNum interaction record line numbers, separated by a |, that will be added
	 */
	public void writeInteractionsRecLineNum (int lineNum, String interactionsLineNum) {
		int interactRecordsLineNum = getInteractionsRecLineNum (lineNum);
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

		String interactions = databaseLines.get(lineNum);
		
		// Parse string into
		return interactions.split(("\\|"));
	}

	/**
	 * Returns a list of all the list of names from the user's interaction list given a list of line number
	 * of user record
	 * 
	 * @param lineNumList A list of line numbers of users whose interactions will be returned.
	 * @return A list of string array of the of names from the users' interaction list.
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
		ArrayList<Integer> recordLines = new ArrayList<>();

		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);
			if (line.contains(user.getName()) && (line.substring(line.indexOf("|")).length() == 1)) {
				recordLines.add(lineNum);
			}
			lineNum++;
		}
		return recordLines;
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
	
	/**
	 * Gets the line number in database file where the user's interactions's record line numbers are stored.
	 * @param user The user whose interaction record line numbers are stored.
	 * @return The line number in database file where the user's interactions's record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(User user) {
		return findRegisteredUser(user) + 4;
	}
	
	/**
	 * Gets the line number in database file where the user's interactions's record line numbers are stored.
	 * @param lineNum Line number of the user's record whose interaction record line numbers are stored.
	 * @return The line number in database file where the user's interactions's record line numbers are stored.
	 */
	public int getInteractionsRecLineNum(int lineNum) {
		return lineNum + 4;
	}
}
