package application;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class UserDb {
	File databaseFile;
	//NOTE: Database will be in the form
	//FirstName LastName|Street Address City State ZipCode|Age
	//Test Status
	//Exposure Status
	//Interaction#1|Interaction#2|Interaction#3| …… 

	
	/**
	 * Constructs a user database with a given file path
	 * @param filePath
	 */
	public UserDb (String filePath) {
		databaseFile = new File ("filePath");
	}
	
	/**
	 * Searches for the given user
	 * @param user The user to be searched for. 
	 * @return True if the user is found.
	 */
	public boolean userExistsInDb(User user) {
		boolean found = false;
		String line;
		try {
			FileReader fr = new FileReader (databaseFile);
			BufferedReader br = new BufferedReader (fr);
			
			//look through file until found or EOF 
			while (!found && (line = br.readLine()) != null){
				
				String[] info = line.split(("|"));
				
				//if this is line with name and address
				if (info.length == 3) {
					//if the name and the address matches
					if (info[0].equals(user.getName()) && info [1].equals (user.getAddr().toString())){
						return true;
					}
				}
			}
		}
		catch (IOException e){
			e.printStackTrace();
		}
		return false;
	}
	
	
	/**
	 * Add a new user to the database.
	 * @param information The user's name, address, and age.
	 * @param testStatus The user's Covid status.
	 * @param interactions The user's interactions.
	 */
	public void writeUser(String information, String testStatus, String interactions) {
		
		//Append to file
		//Write empty string for exposure status
		try {
			FileWriter fw = new FileWriter (databaseFile, true);
			fw.write (information + "\n" + testStatus + "" + "\n" + interactions + "\n");
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Adds new interactions to the user's record.
	 * @param interactions The names of people to be added.
	 */
	public void writeInteractions(User user, String interactions) {
		
	}
	
	/**
	 * Updates the user's Covid status in the database.
	 * @param status the user's Covid status
	 */
	public void writeStatus(User user, String status) {
		
	}
	
	/**
	 * Returns the list of names from the user's interaction list.
	 * @param user The user
	 * @return The list of names from the user's interaction list
	 */
	public ArrayList<String> readInteractions(User user) {
		
	}
	
	/**
	 * Returns the user's Covid status.
	 * @param user The user whose Covid status will be returned. 
	 */
	public String readCovidStatus(User user) {
		
	}
}
