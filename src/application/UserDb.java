package application;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

//NOTE: Database will be in the form
	//FirstName LastName|Street Address City State ZipCode|Age
	//Test Status
	//Exposure Status
	//Interaction#1|Interaction#2-Interaction#3| …… 

public class UserDb {
	File databaseFile;
	List<String> databaseLines;

	/**
	 * Constructs a user database with a given file path
	 * @param filePath
	 */
	public UserDb (String filePath) {
		databaseFile = new File (filePath);
		try {
			//read entire file and parse lines into ArrayList
			databaseLines = Files.readAllLines(Paths.get(filePath));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Searches for the given user
	 * @param user The user to be searched for. 
	 * @return True if the user is found.
	 */
	public boolean userExistsInDb(User user) {
		if (findUser(user) == -1) {
			return false;
		}
		return true;
	}
	
	/**
	 * Add a new user to the database.
	 * @param information The user's name, address, and age.
	 * @param testStatus The user's Covid test status.
	 * @param interactions The user's interactions.
	 */
	public void writeNewUser(User user, String testStatus, String interactions) {
		
		//Append to file
		//Write empty string for exposure status
		String userInfo = user.toString();
		databaseLines.add(userInfo);
		databaseLines.add(testStatus.toUpperCase());
		databaseLines.add("");
		databaseLines.add(interactions.toUpperCase()+"|");
		
		try {
			FileWriter fw = new FileWriter (databaseFile, true);

			fw.write(userInfo+"\n");
			fw.write(testStatus+"\n");
			fw.write("\n");
			fw.write(interactions+"\n");
            fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds new interactions to the user's record.
	 * @param user The user whose interactions will be updated.
	 * @param interactions The names of people to be added to the user's current interactions list.
	 */
	public void writeInteractions(User user, String interactions) {
		int interactionsLineNum = getInteractionsLineNum (user);
		String currentLine = databaseLines.get(interactionsLineNum);
		databaseLines.set(interactionsLineNum, currentLine+interactions.toUpperCase()+"|");
		writeToDatabaseFile();
	}

	/**
	 * Updates the user's Covid test status in the database.
	 * @param user The user whose test status will be updated.
	 * @param status The user's Covid test status. 
	 */
	public void writeTestStatus(User user, String status) {
		int testStatLine = getTestStatLineNum(user);
		databaseLines.set(testStatLine, status.toUpperCase());
		writeToDatabaseFile();
	}
	
	/**
	 * Returns the list of names from the user's interaction list.
	 * @param user The user whose interactions will be returned.
	 * @return A string array of the of names from the user's interaction list.
	 */
	public String[] readInteractions(User user) {
		
		//Retrieve interactions line
		int interactionsLineNum = getInteractionsLineNum (user);
		String interactions = databaseLines.get(interactionsLineNum);
		
		//Parse string into 
		return interactions.split(("\\|"));
	}
	
	/**
	 * Returns the user's test status.
	 * @param user The user whose test status will be returned. 
	 */
	public String readTestStatus(User user) {
		int testStatLine = getTestStatLineNum(user);
		return databaseLines.get(testStatLine);
	}
	
	/**
	 * Searches for the given user
	 * @param user The user to be searched for. 
	 * @return The line number where user record is found or -1 if the user can't be found. 
	 */
	public int findUser (User user) {
		int lineNum = 0;
		while (lineNum < databaseLines.size()) {
			String line = databaseLines.get(lineNum);
			if (line.contains(user.getName()) && line.contains(user.getAddr().toString())){
				return lineNum;
			}
			lineNum++;
		}
		return -1;
	}
	
	/**
	 * Writes entire databaseLines ArrayList to database File
	 */
	public void writeToDatabaseFile() {
		try {
			FileWriter fw = new FileWriter (databaseFile);

			//loop through array and print line onto file
            for (String line: databaseLines) {
                fw.write(line + "\n");
            }
            fw.close();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Gets the line number in database file where the user's test status is written.
	 * @param user The user whose test status line number will be returned.
	 * @return The line number in database file where the user's test status is written.
	 */
	public int getTestStatLineNum (User user) {
		//Retrieve test status line number
		//interactions line is the 2nd line in a user record
		return findUser (user) + 1;
	}
	
	/**
	 * Gets the line number in database file where the user's exposure status is written.
	 * @param user The user whose exposure status line number will be returned.
	 * @return The line number in database file where the user's exposure status is written.
	 */
	public int getExposureStatLineNum (User user) {
		//Retrieve test status line number
		//interactions line is the 2nd line in a user record
		return findUser (user) + 2;
	}
	
	/**
	 * Gets the line number in database file where the user's interactions are written.
	 * @param user The user whose interactions line number will be returned.
	 * @return The line number in database file where the user's interactions are written.
	 */
	public int getInteractionsLineNum (User user) {
		//Retrieve interactions line number
		//interactions line is the 3rd line in a user record
		return findUser (user) + 3;
	}
}
