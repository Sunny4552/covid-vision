package application;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.fxml.FXMLLoader;

public class Main extends Application {

	private ExposureTracker expTrcker = new ExposureTracker("FileDatabase.txt");
	private User loggedInUser = null; // stores user currently logged in

	@Override
	public void start(Stage primaryStage) {
		// TODO Replace with T.K's GUI code

		// when login button, create User(info) with name, street, city state, and
		// zipcode, and then login(user)
		// when register new user, create User(info) with name, street, city state, and
		// zipcode, and then call login(user))
		// If login(info) fails (is false), continue to get covid status,
		// interactions(exact input from user), and call registerUser(user, status,
		// interactions)

		try {
			VBox root = (VBox) FXMLLoader.load(getClass().getResource("UserGUI.fxml"));
			Scene scene = new Scene(root, 400, 400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Determines if login information matches a user in the database and if it
	 * does, stores user identification information in loggedInUser.
	 * 
	 * @param user User to check if could login
	 * @return True if user is registered successfully logged in, False if user is
	 *         not registered and failed to log in
	 */
	public boolean login(User currentUser) {
		if (expTrcker.loginUser(currentUser)) {
			loggedInUser = currentUser;
			return true;
		} else {
			return false;
		}

	}

	public void registerUser(User user, String status, String interactions) {
		expTrcker.registerNewUser(user, status, interactions);
	}

	public String checkExposureStatus() {
		return expTrcker.getExposureStatus(loggedInUser);
	}

	public String checkPastInteractions() {
		String[] interactionsList = expTrcker.getUserInteractions(loggedInUser);
		String concat = "";

		for (int i = 0; i < interactionsList.length; i++) {
			concat += interactionsList[i];
			if (i != interactionsList.length - 1)
				concat += ", ";
		}
		return concat;
	}

	public void updateTestStatus(String status) {
		expTrcker.updateTestStatus(loggedInUser, status);
	}

	public void addInteractions(String interactions) {
		expTrcker.addInteractions(loggedInUser, interactions);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
