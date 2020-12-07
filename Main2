package application;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Screen;
import javafx.stage.Stage;

/**
 * Regulates the GUI portion of the program.
 * 
 * @author Thi Nguyen, Minh Nguyen, Sunny Mistry, T.K. Bui
 *
 */
public class Main extends Application {

	private ExposureTracker expTracker = new ExposureTracker("FileDatabase.txt");
	private User currentSystemUser = null; // stores user currently logged in

	// get dimensions of the user's screen to make sure the app will appear full screen
	Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

	// stage
	Stage stage;

	// scenes
	Scene sceneHome;
	Scene sceneRegister1;
	Scene sceneRegister2;
	Scene sceneLogin;
	Scene sceneLoggedIn;
	Scene sceneUpdate;
	Scene sceneCheck;

	// pane
	private Pane homePane;
	private Pane registerPane1;
	private Pane registerPane2;
	private Pane loginPane;
	private Pane loggedInPane;
	private Pane updatePane;
	private Pane checkPane;

	@Override
	public void start(Stage primaryStage) throws Exception {

		// set stage
		stage = primaryStage;

		// set panes
		homePane = createHomePane();
		registerPane1 = createRegisterPane1();
		registerPane2 = createRegisterPane2();
		loginPane = createLoginPane();
		loggedInPane = createLoggedInPane();
		updatePane = createUpdatePane();

		// set scenes
		sceneHome = new Scene(homePane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneRegister1 = new Scene(registerPane1, screenBounds.getWidth(), screenBounds.getHeight());
		sceneRegister2 = new Scene(registerPane2, screenBounds.getWidth(), screenBounds.getHeight());
		sceneLogin = new Scene(loginPane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneLoggedIn = new Scene(loggedInPane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneUpdate = new Scene(updatePane, screenBounds.getWidth(), screenBounds.getHeight());

		// start out at home page
		primaryStage.setTitle("20/20 COVID-19 Vision");
		primaryStage.setMaximized(true);
		primaryStage.setScene(sceneHome);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/covid2.png")));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/* methods that create the panes for their respective scenes */

	/**
	 * Creates the default home page where a user can login or register.
	 * 
	 * @return The VBox pane that contains all elements of the home page.
	 */
	public VBox createHomePane() {

		// the overarching VBox container for the home page
		VBox pane = new VBox();

		pane.setStyle("-fx-background-color: white;"); // set pane background color to white
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(10);

		// use an hBox to contain the register and login buttons
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);

		// image for logo and welcome message
		Image welcomeImage = new Image(getClass().getResourceAsStream("/images/welcome2.png"));
		ImageView welcome = new ImageView(welcomeImage);

		// images to use as the register button
		Image registerImage = new Image(getClass().getResourceAsStream("/images/register1.png"));
		Image registerHovered = new Image(getClass().getResourceAsStream("/images/register2.png"));
		ImageView register = new ImageView(registerImage);

		register.setFitHeight(100);
		register.setPreserveRatio(true);
		register.setOnMouseClicked(e -> { // when you click the register button, go to sceneRegister1
			goRegister();
		});

		register.setOnMouseEntered(e -> { // when you hover over the register button, use registerHovered image
			register.setImage(registerHovered);
		});
		register.setOnMouseExited(e -> { // when you stop hovering over the register button, return to original image
			register.setImage(registerImage);
		});

		// image to use as the login button
		Image loginImage = new Image(getClass().getResourceAsStream("/images/login1.png"));
		Image loginHovered = new Image(getClass().getResourceAsStream("/images/login2.png"));
		ImageView login = new ImageView(loginImage);

		login.setFitHeight(100);
		login.setPreserveRatio(true);
		login.setOnMouseClicked(e -> { // when you click the login button, go to sceneLogin
			goLogin();
		});

		login.setOnMouseEntered(e -> { // when you hover over the login button, use loginHovered image
			login.setImage(loginHovered);
		});
		login.setOnMouseExited(e -> { // when you stop hovering over the login button, return to original image
			login.setImage(loginImage);
		});

		// add the register and login buttons to the button container
		buttonContainer.getChildren().addAll(register, login);

		// add the welcome/logo image and button container to the vBox "pane" and return it
		pane.getChildren().addAll(welcome, buttonContainer);

		return pane;
	}

	/**
	 * Creates the first register page.
	 * 
	 * @return The HBox pane that contains all elements of the register pane.
	 */
	public HBox createRegisterPane1() {

		// the overarching HBox container for the first registration page
		HBox pane = new HBox();
		pane.setStyle("-fx-background-color: white;");
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(50);

		// the "title" of this page, displayed to the left of the text fields for user input
		Text register = new Text("REGISTER");
		register.setFont(Font.font(48));
		register.setTextAlignment(TextAlignment.RIGHT);

		// a grid pane to contain labels and the text fields for user input
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		/* labels */

		// the label "Name" for the text field where the user can input a name
		Label lblName = new Label("Name");
		lblName.setFont(Font.font(16));
		GridPane.setHalignment(lblName, HPos.RIGHT);
		grid.add(lblName, 0, 0);

		// the label "Address" for the text field where the user can input their address
		Label lblAddress = new Label("Address");
		lblAddress.setFont(Font.font(16));
		GridPane.setHalignment(lblAddress, HPos.RIGHT);
		grid.add(lblAddress, 0, 1);

		// the label "City" for the text field where the user can input their city
		Label lblCity = new Label("City");
		lblCity.setFont(Font.font(16));
		GridPane.setHalignment(lblCity, HPos.RIGHT);
		grid.add(lblCity, 0, 2);

		// the label "State" for the text field where the user can input their state
		Label lblState = new Label("State");
		lblState.setFont(Font.font(16));
		GridPane.setHalignment(lblState, HPos.RIGHT);
		grid.add(lblState, 0, 3);

		// the label "Zip Code" for the text field where the user can input their zip code
		Label lblZip = new Label("Zip Code");
		lblZip.setFont(Font.font(16));
		GridPane.setHalignment(lblZip, HPos.RIGHT);
		grid.add(lblZip, 0, 4);

		/* text fields for user input */

		// the text field where the user can input their name
		TextField tfName = new TextField();
		tfName.setFont(Font.font(16));
		tfName.setPromptText("Enter first and last name.");
		grid.add(tfName, 1, 0);

		// the text field where the user can input their address
		TextField tfAddress = new TextField();
		tfAddress.setFont(Font.font(16));
		tfAddress.setPromptText("Enter street address.");
		grid.add(tfAddress, 1, 1);

		// the text field where the user can input their city
		TextField tfCity = new TextField();
		tfCity.setFont(Font.font(16));
		tfCity.setPromptText("Enter city.");
		grid.add(tfCity, 1, 2);

		// the text field where the user can input their state
		TextField tfState = new TextField();
		tfState.setFont(Font.font(16));
		tfState.setPromptText("Enter state abbreviation.");
		grid.add(tfState, 1, 3);

		// the text field where the user can input their zip code
		TextField tfZip = new TextField();
		tfZip.setFont(Font.font(16));
		tfZip.setPromptText("Enter zip code.");
		grid.add(tfZip, 1, 4);

		// a label that will appear accordingly to whichever error or empty text field occurs
		Label lblError = new Label("");
		Label focus = new Label("");
		lblError.setFont(Font.font(16));
		GridPane.setHalignment(lblError, HPos.LEFT);
		grid.add(lblError, 1, 5);
		grid.add(focus, 0, 5);

		// an HBox to contain the back and next buttons, displayed below the labels/text fields
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.TOP_RIGHT);
		buttonContainer.setSpacing(5);

		// images to use as the back button (back to home page)
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView backButton = new ImageView(backImage);

		backButton.setFitHeight(100);
		backButton.setPreserveRatio(true);
		backButton.setOnMouseClicked(e -> { // go to home page when back button is clicked
			lblError.setText("");
			tfName.clear();
			tfAddress.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfName.setPromptText("Enter first and last name.");
			tfZip.setPromptText("Enter zip code.");
			goMainMenu();
		});

		backButton.setOnMouseEntered(e -> { // show backHovered when the back button is hovered
			backButton.setImage(backHovered);
		});
		backButton.setOnMouseExited(e -> { // return to original back button image when not hovered
			backButton.setImage(backImage);
		});

		// images to use as the next button (continues registration process)
		Image nextImage = new Image(getClass().getResourceAsStream("/images/next1.png"));
		Image nextHovered = new Image(getClass().getResourceAsStream("/images/next2.png"));
		ImageView nextButton = new ImageView(nextImage);

		nextButton.setFitHeight(100);
		nextButton.setPreserveRatio(true);

		nextButton.setOnMouseEntered(e -> { // show nextHovered when the next button is hovered
			nextButton.setImage(nextHovered);
		});
		nextButton.setOnMouseExited(e -> { // return to original next button image when not hovered
			nextButton.setImage(nextImage);
		});

		/* when clicking "next," check every text field for certain requirements and if they pass,
		* only then let the user move on to the next page of registration (sceneRegister2) */
		nextButton.setOnMouseClicked(e -> {
			focus.requestFocus();
			String valName = tfName.getText();
			String valAddress = tfAddress.getText();
			String valCity = tfCity.getText();
			String valState = tfState.getText();
			String valZip = tfZip.getText();

			// show this lblError if at least one of the text fields is empty
			if (valName.isEmpty() || valAddress.isEmpty() || valCity.isEmpty() || valState.isEmpty()
					|| valZip.isEmpty()) {
				lblError.setText("FIELD(S) ARE EMPTY");
			}

			// show this lblError if the name is in the wrong format (needs both first and last name)
			else if (!User.validName(valName)) {
				tfName.clear();
				tfName.setPromptText("FULL NAME W/ SPACES");
			}

			// show this lblError if the zip code is in the wrong format (only 5 digits allowed)
			else if (!validZipCode(valZip)) {
				tfZip.clear();
				tfZip.setPromptText("ENTER ONLY 5 DIGITS");
			}

			// show this lblError if the state abbreviation is in the wrong format (only 2 letters allowed)
			else if (valState.length() != 2) {
				tfState.clear();
				tfState.setPromptText("ENTER ONLY STATE ABBREVIATIONS");
			}

			// show this lblError if address is in the wrong format (needs a number and a street name)
			else if (!validAddress(valAddress)) {
				tfAddress.clear();
				tfAddress.setPromptText("NUM + STREET");
			}

			// if everything works...
			else {

				// get all of the values of from the text fields and try to register the user
				int value5 = Integer.parseInt(tfZip.getText());
				User attemptRegisterUser = new User(valName, valAddress, valCity, valState, value5);

				if (!couldLogin(attemptRegisterUser)) { // user is not in database, so we can register them

					currentSystemUser = attemptRegisterUser;
					lblError.setText("");
					tfName.clear();
					tfAddress.clear();
					tfCity.clear();
					tfState.clear();
					tfZip.clear();
					tfName.setPromptText("Enter first and last name.");
					tfZip.setPromptText("Enter zip code.");
					goNext();

				} else { // user is already in the database, so make them login instead
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Registration Error");
					alert.setHeaderText("The information entered matches an existing registered user.");
					alert.setContentText("Directing to page where you can login.");
					tfName.clear();
					tfAddress.clear();
					tfCity.clear();
					tfState.clear();
					tfZip.clear();
					alert.showAndWait();
					goLogin();
				}
			}
		});

		// add the back button and next button to the buttonContainer
		buttonContainer.getChildren().addAll(backButton, nextButton);

		// a VBox to contain the grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		// add the "Register" title text and the VBox to the overarching HBox pane and return it
		pane.getChildren().addAll(register, vBox);

		return pane;

	}

	/**
	 * Creates the second register page that the first register page goes to.
	 * 
	 * @return The HBox pane that contains all elements of the second register page.
	 */
	public HBox createRegisterPane2() {

		// the overarching HBox container for the second registration page
		HBox pane = new HBox();
		pane.setStyle("-fx-background-color: white;");
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(50);

		// the "title" of this page
		Text register = new Text("REGISTER");
		register.setFont(Font.font(48));
		register.setTextAlignment(TextAlignment.RIGHT);

		// a grid pane to contain labels and menu/text fields for user input
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		/* labels */

		// the label for the menu where the user can choose their COVID-19 test status
		Label lblStatus = new Label("COVID-19 Status");
		lblStatus.setFont(Font.font(16));
		GridPane.setHalignment(lblStatus, HPos.RIGHT);
		grid.add(lblStatus, 0, 0);

		// the label for the text box where the user can input names of people they've interacted with
		Label lblInteractions = new Label("Interactions");
		lblInteractions.setFont(Font.font(16));
		GridPane.setHalignment(lblInteractions, HPos.RIGHT);
		GridPane.setValignment(lblInteractions, VPos.TOP);
		grid.add(lblInteractions, 0, 1);

		/* user input fields */

		// the menu button where the user can choose their COVID-19 test status
		MenuButton chooseStatus = new MenuButton();
		chooseStatus.setFont(Font.font(16));
		chooseStatus.setText("Choose status...");
		chooseStatus.setPrefWidth(200);

		// the menu has 3 options - not tested, tested positive, and tested negative
		MenuItem notTested = new MenuItem("Not tested");
		MenuItem testPos = new MenuItem("Tested positive");
		MenuItem testNeg = new MenuItem("Tested negative");

		// set the menu text to reflect whichever option they chose
		notTested.setOnAction(e -> {
			chooseStatus.setText("Not tested");
		});
		testPos.setOnAction(e -> {
			chooseStatus.setText("Tested positive");
		});
		testNeg.setOnAction(e -> {
			chooseStatus.setText("Tested negative");
		});

		chooseStatus.getItems().addAll(notTested, testNeg, testPos); // add options to menu
		grid.add(chooseStatus, 1, 0);

		// text area to enter names of those the user has interacted with
		TextArea tfInteractions = new TextArea();
		tfInteractions.setFont(Font.font(16));
		tfInteractions.setPrefSize(200, 200);
		tfInteractions.setWrapText(true);
		tfInteractions.setPromptText("FirstName LastName, FirstName2 LastName2, ...");
		grid.add(tfInteractions, 1, 1);

		// a label that will appear accordingly to whichever error occurs
		Label lblError = new Label("");
		Label focus = new Label("");
		lblError.setFont(Font.font(16));
		grid.add(lblError, 1, 2);
		GridPane.setHalignment(lblError, HPos.LEFT);
		grid.add(focus, 0, 0);

		// an HBox to contain the back and register buttons
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.TOP_RIGHT);

		// image to use as the back button
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView backButton = new ImageView(backImage);

		backButton.setFitHeight(100);
		backButton.setPreserveRatio(true);
		backButton.setOnMouseClicked(e -> { // when back button is clicked, go back to first registration page
			lblError.setText("");
			chooseStatus.setText("Choose status...");
			tfInteractions.clear();
			goRegister();
		});

		backButton.setOnMouseEntered(e -> { // show backHovered when back button is hovered
			backButton.setImage(backHovered);
		});
		backButton.setOnMouseExited(e -> { // return to original back button image when not hovered
			backButton.setImage(backImage);
		});

		// image to use as the register button
		Image registerImage = new Image(getClass().getResourceAsStream("/images/register1.png"));
		Image registerHovered = new Image(getClass().getResourceAsStream("/images/register2.png"));
		ImageView registerButton = new ImageView(registerImage);

		registerButton.setFitHeight(100);
		registerButton.setPreserveRatio(true);

		registerButton.setOnMouseEntered(e -> { // show registerHovered when register button is hovered
			registerButton.setImage(registerHovered);
		});
		registerButton.setOnMouseExited(e -> { // return to original register button image when not hovered
			registerButton.setImage(registerImage);
		});

		// when you click the register button, ...
		registerButton.setOnMouseClicked(e -> {

			// get the test status and interactions to check for validity
			focus.requestFocus();
			String value1 = chooseStatus.getText();
			String value2 = tfInteractions.getText();

			// the user MUST choose a test status to register and log in; having zero interactions is okay
			if (validInteractionList(value2) && validChooseStatus(value1)) { // can register and log in

				registerUser(currentSystemUser, value1, value2);
				chooseStatus.setText("Choose status...");
				tfInteractions.clear();
				goLoggedIn();

			} else { // user did not choose a status, tell them to choose one

				lblError.setText("PLEASE CHOOSE A STATUS");

			}

		});

		// add the back button and register button to the buttonContainer
		buttonContainer.getChildren().addAll(backButton, registerButton);

		// a VBox to contain grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		// add the "Register" page title and the VBox to the overarching HBox pane and return it
		pane.getChildren().addAll(register, vBox);

		return pane;

	}

	/**
	 * Creates the page that allows a user to login.
	 * 
	 * @return The HBox pane that contains all elements of the login page.
	 */
	public HBox createLoginPane() {

		// the overarching HBox container for this page
		HBox pane = new HBox();
		pane.setStyle("-fx-background-color: white;");
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(50);

		// the "title" of this page
		Text login = new Text("LOGIN");
		login.setFont(Font.font(48));
		login.setTextAlignment(TextAlignment.RIGHT);

		// a grid pane to contain labels and text fields
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);

		/* labels */

		// label for the Name text field
		Label lblName = new Label("Name");
		lblName.setFont(Font.font(16));
		GridPane.setHalignment(lblName, HPos.RIGHT);
		grid.add(lblName, 0, 0);

		// label for the Address text field
		Label lblAddress = new Label("Address");
		lblAddress.setFont(Font.font(16));
		GridPane.setHalignment(lblAddress, HPos.RIGHT);
		grid.add(lblAddress, 0, 1);

		// label for the City text field
		Label lblCity = new Label("City");
		lblCity.setFont(Font.font(16));
		GridPane.setHalignment(lblCity, HPos.RIGHT);
		grid.add(lblCity, 0, 2);

		// label for the State text field
		Label lblState = new Label("State");
		lblState.setFont(Font.font(16));
		GridPane.setHalignment(lblState, HPos.RIGHT);
		grid.add(lblState, 0, 3);

		// label for the Zip Code text field
		Label lblZip = new Label("Zip Code");
		lblZip.setFont(Font.font(16));
		GridPane.setHalignment(lblZip, HPos.RIGHT);
		grid.add(lblZip, 0, 4);

		/* text fields */

		// text field where user can input their name
		TextField tfName = new TextField();
		tfName.setFont(Font.font(16));
		tfName.setPromptText("Enter first and last name.");
		grid.add(tfName, 1, 0);

		// text field where user can input their address
		TextField tfAddress = new TextField();
		tfAddress.setFont(Font.font(16));
		tfAddress.setPromptText("Enter street address.");
		grid.add(tfAddress, 1, 1);

		// text field where user can input their city
		TextField tfCity = new TextField();
		tfCity.setFont(Font.font(16));
		tfCity.setPromptText("Enter city.");
		grid.add(tfCity, 1, 2);

		// text field where user can input their state
		TextField tfState = new TextField();
		tfState.setFont(Font.font(16));
		tfState.setPromptText("Enter state abbreviations.");
		grid.add(tfState, 1, 3);

		// text field where user can input their zip code
		TextField tfZip = new TextField();
		tfZip.setFont(Font.font(16));
		tfZip.setPromptText("Enter zip code.");
		grid.add(tfZip, 1, 4);

		// a label for a error that will appear accordingly for an error/empty text field when trying to log in
		Label lblError = new Label("");
		lblError.setFont(Font.font(16));
		GridPane.setHalignment(lblError, HPos.LEFT);
		grid.add(lblError, 1, 5);
		Label focus = new Label("");
		grid.add(focus, 0, 5);

		// an HBox to contain the back and login buttons
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.TOP_RIGHT);
		buttonContainer.setSpacing(5);

		// image to use as the back button
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView back = new ImageView(backImage);

		back.setFitHeight(100);
		back.setPreserveRatio(true);
		back.setOnMouseClicked(e -> { // when back button is clicked, return to home page
			lblError.setText("");
			tfName.clear();
			tfAddress.clear();
			tfCity.clear();
			tfState.clear();
			tfZip.clear();
			tfName.setPromptText("Enter first and last name.");
			tfZip.setPromptText("Enter zip code.");
			goMainMenu();
		});

		back.setOnMouseEntered(e -> { // show backHovered when back button is hovered
			back.setImage(backHovered);
		});
		back.setOnMouseExited(e -> { // return to original back button image when not hovered
			back.setImage(backImage);
		});

		// image to use as the login button
		Image loginImage = new Image(getClass().getResourceAsStream("/images/login1.png"));
		Image loginHovered = new Image(getClass().getResourceAsStream("/images/login2.png"));
		ImageView loginButton = new ImageView(loginImage);

		loginButton.setFitHeight(100);
		loginButton.setPreserveRatio(true);

		loginButton.setOnMouseEntered(e -> { // show loginHovered when login button is hovered
			loginButton.setImage(loginHovered);
		});
		loginButton.setOnMouseExited(e -> { // return to original login button when not hovered
			loginButton.setImage(loginImage);
		});

		// when login button is clicked...
		loginButton.setOnMouseClicked(e -> {

			focus.requestFocus();
			String valName = tfName.getText();
			String valAddress = tfAddress.getText();
			String valCity = tfCity.getText();
			String valState = tfState.getText();

			// check whether the user input is in the correct format

			// check if any fields are empty
			if (valName.isEmpty() || valAddress.isEmpty() || valCity.isEmpty() || valState.isEmpty()
					|| tfZip.getText().isEmpty()) {
				lblError.setText("FIELD(S) ARE EMPTY");
			}

			// check if the user entered their full name
			else if (!User.validName(valName)) {
				tfName.clear();
				tfName.setPromptText("FULL NAME W/ SPACES");
			}

			// check if the zip code is 5 digits
			else if (!validZipCode(tfZip.getText())) {
				tfZip.clear();
				tfZip.setPromptText("ENTER ONLY 5 DIGITS");

			}

			// check if the address is a number with a street name
			else if (!validAddress(valAddress)) {
				tfAddress.clear();
				tfAddress.setPromptText("NUM + STREET");
			}

			else { // all text fields are filled and in the correct formats, so...

				int value5 = Integer.parseInt(tfZip.getText());
				User attemptLoginUser = new User(valName, valAddress, valCity, valState, value5);
				
				// logs user in if user's credentials match an existing one
				if (couldLogin(attemptLoginUser)) {
					login(attemptLoginUser);
					lblError.setText("");
					tfName.clear();
					tfAddress.clear();
					tfCity.clear();
					tfState.clear();
					tfZip.clear();
					tfName.setPromptText("Enter first and last name.");
					tfZip.setPromptText("Enter zip code.");
					goLoggedIn();
				} 
				
				//puts an alert if the user doesn't exist yet and brings user to register page
				else {
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Login Error");
					alert.setHeaderText("The information entered is not a valid user.");
					alert.setContentText("Directing to page where you can register.");
					tfName.clear();
					tfAddress.clear();
					tfCity.clear();
					tfState.clear();
					tfZip.clear();
					alert.showAndWait();
					goRegister();
				}
			}
		});

		// add back and login buttons to buttonContainer
		buttonContainer.getChildren().addAll(back, loginButton);

		// a VBox to contain grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		// add the "Login" title text and vBox to our overarching HBox pane and return it
		pane.getChildren().addAll(login, vBox);

		return pane;

	}

	/**
	 * Creates the page displayed after a user successfully logs in or registers.
	 * 
	 * @return The pane that contains all elements of the page displayed after a user
	 *         logs in or registers.
	 */
	public BorderPane createLoggedInPane() {

		// the overarching border pane for this page
		BorderPane pane = new BorderPane();
		pane.setStyle("-fx-background-color: white;");

		// default message for the center, telling the user what their options are
		VBox defaultMessage = new VBox();
		defaultMessage.setAlignment(Pos.CENTER);
		defaultMessage.setSpacing(20);

		// title of default message
		Text selectTitle = new Text("Select one of the options above!");
		selectTitle.setFont(Font.font("", FontWeight.BOLD, 24));
		selectTitle.setUnderline(true);

		// VBox to hold a description for the Update button in the default message
		VBox updateBox = new VBox();
		updateBox.setAlignment(Pos.CENTER);

		// description about the Update button at top
		Text updateTitle = new Text("Update COVID-19 Status and Interactions");
		updateTitle.setFont(Font.font("", FontWeight.BOLD, 18));
		updateTitle.setTextAlignment(TextAlignment.CENTER);
		Text updateDescription = new Text("Update your COVID-19 test status, and add people who you have interacted "
				+ "with in the last 14 days.");
		updateDescription.setFont(Font.font("", FontPosture.ITALIC, 18));
		updateDescription.setWrappingWidth(400);
		updateDescription.setTextAlignment(TextAlignment.CENTER);
		updateBox.getChildren().addAll(updateTitle, updateDescription);

		// VBox to hold description for Check button in the default message
		VBox checkBox = new VBox();
		checkBox.setAlignment(Pos.CENTER);

		// description about the Check button at top
		Text checkTitle = new Text("Check COVID-19 Exposure Status");
		checkTitle.setFont(Font.font("", FontWeight.BOLD, 18));
		checkTitle.setTextAlignment(TextAlignment.CENTER);
		Text checkDescription = new Text("See if anyone on your list of interactions has been exposed to or "
				+ "interacted with others who have been exposed to COVID-19.");
		checkDescription.setFont(Font.font("", FontPosture.ITALIC, 18));
		checkDescription.setWrappingWidth(400);
		checkDescription.setTextAlignment(TextAlignment.CENTER);
		checkBox.getChildren().addAll(checkTitle, checkDescription);

		// add these descriptions to our default message, which will be the center of our border pane
		defaultMessage.getChildren().addAll(selectTitle, updateBox, checkBox);
		pane.setCenter(defaultMessage);

		// HBox for the update and check buttons in the top strip of the border pane
		HBox hBoxTop = new HBox();
		hBoxTop.setAlignment(Pos.CENTER);
		hBoxTop.setSpacing(5);
		hBoxTop.setPadding(new Insets(20, 0, 0, 0));

		// images to use as the update status button
		Image updateImage = new Image(getClass().getResourceAsStream("/images/update1.png"));
		Image updateHovered = new Image(getClass().getResourceAsStream("/images/update2.png"));
		ImageView update = new ImageView(updateImage);

		update.setFitHeight(100);
		update.setPreserveRatio(true);
		update.setOnMouseClicked(e -> { // when update button is clicked, go to update status page
			goUpdateStatus();
		});

		update.setOnMouseEntered(e -> { // show updateHovered when update button is hovered
			update.setImage(updateHovered);
		});
		update.setOnMouseExited(e -> { // return to original update button image when not hovered
			update.setImage(updateImage);
		});

		// image to use as the check status button
		Image checkImage = new Image(getClass().getResourceAsStream("/images/check2.png"));
		Image checkHovered = new Image(getClass().getResourceAsStream("/images/check1.png"));
		ImageView check = new ImageView(checkImage);

		check.setFitHeight(100);
		check.setPreserveRatio(true);
		check.setOnMouseClicked(e -> { // when check status button is clicked, go to check status page
			goCheckStatus();
		});

		check.setOnMouseEntered(e -> { // show checkHovered when check status button is hovered
			check.setImage(checkHovered);
		});
		check.setOnMouseExited(e -> { // return to original check status button when not hovered
			check.setImage(checkImage);
		});

		// add update and check buttons to the hBox and set it as the top of our border pane
		hBoxTop.getChildren().addAll(update, check);
		pane.setTop(hBoxTop);

		// put a log out button at the bottom of the border pane in a stack pane
		StackPane bottomPane = new StackPane();
		bottomPane.setAlignment(Pos.CENTER);

		// image to use as log out button
		Image logOutImage = new Image(getClass().getResourceAsStream("/images/logout1.png"));
		Image logOutHovered = new Image(getClass().getResourceAsStream("/images/logout2.png"));
		ImageView logOut = new ImageView(logOutImage);

		logOut.setFitHeight(100);
		logOut.setPreserveRatio(true);
		logOut.setOnMouseClicked(e -> { // when log out button is pressed, return to the home page
			goMainMenu();
		});

		logOut.setOnMouseEntered(e -> { // show logOutHovered when log out button is hovered
			logOut.setImage(logOutHovered);
		});
		logOut.setOnMouseExited(e -> { // return to original log out button image when not hovered
			logOut.setImage(logOutImage);
		});

		// add log out button to the stack pane and set it as the bottom of the border pane
		bottomPane.getChildren().add(logOut);
		pane.setBottom(bottomPane);

		pane.setPadding(new Insets(0, 0, 20, 0));

		// return the completed pane
		return pane;

	}

	/**
	 * Creates the page for a user to update their status and add any more interactions.
	 * 
	 * @return The VBox pane that contains all elements of the page for a user to update
	 *         their test status.
	 */
	public VBox createUpdatePane() {

		// the overarching VBox for this page
		VBox vBox = new VBox();
		vBox.setStyle("-fx-background-color: white;");
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(100);
		vBox.setPadding(new Insets(50, 0, 50, 0));

		// vBox for elements related to updating the test status
		VBox updateBox = new VBox();
		updateBox.setSpacing(5);
		updateBox.setAlignment(Pos.CENTER);

		// a label for the menu where user can select a test status
		Label lblUpdate = new Label("Update Status");
		lblUpdate.setFont(Font.font(16));

		// the menu where user can change their test status
		MenuButton chooseStatus = new MenuButton();
		chooseStatus.setFont(Font.font(16));
		chooseStatus.setText("Choose status...");
		chooseStatus.setPrefWidth(200);

		// 3 options for the menu - not tested, tested positive, and tested negative
		MenuItem notTested = new MenuItem("Not tested");
		MenuItem testPos = new MenuItem("Tested positive");
		MenuItem testNeg = new MenuItem("Tested negative");

		// set the menu text to whatever the user selects
		notTested.setOnAction(e -> {
			chooseStatus.setText("Not tested");
		});
		testPos.setOnAction(e -> {
			chooseStatus.setText("Tested positive");
		});
		testNeg.setOnAction(e -> {
			chooseStatus.setText("Tested negative");
		});

		// add menu items to menu, and add the label and menu to the updateBox
		chooseStatus.getItems().addAll(notTested, testNeg, testPos);
		updateBox.getChildren().addAll(lblUpdate, chooseStatus);

		// HBox containing all elements related to submitting new interactions
		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(5);

		// label for text box where user can add interactions
		Label lblName = new Label("Interactions");
		lblName.setFont(Font.font(16));

		// text field where user can add names one at a time
		TextField tfName = new TextField();
		tfName.setPromptText("Enter FirstName LastName.");
		tfName.setPrefWidth(250);
		tfName.setFont(Font.font(16));

		// image to use as submit name button
		Image submitImage = new Image(getClass().getResourceAsStream("/images/submit1.png"));
		Image submitHovered = new Image(getClass().getResourceAsStream("/images/submit2.png"));
		ImageView submit = new ImageView(submitImage);

		submit.setFitHeight(100);
		submit.setPreserveRatio(true);

		submit.setOnMouseEntered(e -> { // show submitHovered when submit button is hovered
			submit.setImage(submitHovered);
		});
		submit.setOnMouseExited(e -> { // return to original submit button image when not hovered
			submit.setImage(submitImage);
		});

		// when submit button is clicked, ...
		submit.setOnMouseClicked(e -> {

			// get the name and split into first and last name
			String interaction = tfName.getText();
			String[] split = interaction.split(" ");

			// make sure that user only submits one interaction at a time, by first and last name
			if (split.length == 2) {
				addInteractions(interaction);
			} else {
				tfName.clear();
				tfName.setPromptText("ENTER FIRST AND LAST NAME");
			}
			tfName.clear();
		});

		// add label, text field, and submit button to hbox for interactions
		hBox.getChildren().addAll(lblName, tfName, submit);

		// add done button at bottom to return to logged in screen
		StackPane pane = new StackPane();
		pane.setPadding(new Insets(100, 0, 0, 0));
		pane.setAlignment(Pos.BASELINE_CENTER);

		// image to use as done button
		Image doneImage = new Image(getClass().getResourceAsStream("/images/done1.png"));
		Image doneHovered = new Image(getClass().getResourceAsStream("/images/done2.png"));
		ImageView done = new ImageView(doneImage);

		done.setFitHeight(100);
		done.setPreserveRatio(true);

		done.setOnMouseEntered(e -> { // show doneHovered when done button is hovered
			done.setImage(doneHovered);
		});

		done.setOnMouseExited(e -> { // show original done button when not hovered
			done.setImage(doneImage);
		});

		// when done button is clicked...
		done.setOnMouseClicked(e -> {

			// if the status has changed, update the test status and return to logged in page
			if (validChooseStatus(chooseStatus.getText())) {
				updateTestStatus(chooseStatus.getText());
			}
			goLoggedIn();
		});

		// add done button to its pane, then add updateBox; hBox, and pane to vBox and return it
		pane.getChildren().add(done);

		vBox.getChildren().addAll(updateBox, hBox, pane);

		return vBox;

	}

	/**
	 * Creates the page to display the user's exposure status.
	 * 
	 * @return The VBox pane that contains all the elements of the page to display the
	 *         user's exposure status.
	 */
	public Pane createCheckPane() {

		// the overarching VBox for this page
		VBox vBox = new VBox();
		vBox.setStyle("-fx-background-color: white;");
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(50);
		vBox.setPadding(new Insets(50, 0, 50, 0));

		// the grid pane to hold the labels and test status, exposure status, and interaction list
		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setAlignment(Pos.CENTER);

		/* labels */

		// label for the test status
		Label lblTestStatus = new Label("Test Status:");
		lblTestStatus.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblTestStatus, HPos.RIGHT);
		pane.add(lblTestStatus, 0, 0);

		// label for the exposure status
		Label lblExposureStatus = new Label("Exposure Status:");
		lblExposureStatus.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblExposureStatus, HPos.RIGHT);
		pane.add(lblExposureStatus, 0, 1);

		// label for the interaction list
		Label lblInteractionList = new Label("Interactions:");
		lblInteractionList.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblInteractionList, HPos.RIGHT);
		pane.add(lblInteractionList, 0, 2);

		/* actual values */

		// the test status of the logged in user
		Text testStatus = new Text();
		testStatus.setFont(Font.font(16));
		testStatus.setText(checkTestStatus());
		pane.add(testStatus, 1, 0);

		// the exposure status of the logged in user
		Text exposureStatus = new Text();
		exposureStatus.setFont(Font.font(16));
		exposureStatus.setText(checkExposureStatus());
		pane.add(exposureStatus, 1, 1);

		// the list of interactions of the logged in user
		Text interactions = new Text();
		interactions.setFont(Font.font(16));
		interactions.setText(checkPastInteractions());
		interactions.setWrappingWidth(400);
		pane.add(interactions, 1, 2);

		// image to use as done button
		Image doneImage = new Image(getClass().getResourceAsStream("/images/done1.png"));
		Image doneHovered = new Image(getClass().getResourceAsStream("/images/done2.png"));
		ImageView done = new ImageView(doneImage);

		done.setFitHeight(100);
		done.setPreserveRatio(true);

		done.setOnMouseEntered(e -> { // show doneHovered when done button is hovered
			done.setImage(doneHovered);
		});

		done.setOnMouseExited(e -> { // show original done button image when not hovered
			done.setImage(doneImage);
		});

		done.setOnMouseClicked(e -> { // return to logged in page when done button is clicked
			goLoggedIn();
		});

		// add done button at bottom to return to logged in screen
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(100, 0, 0, 0));
		stackPane.setAlignment(Pos.BASELINE_CENTER);
		stackPane.getChildren().add(done);

		// add our pane with user info and the done button to VBox and return it
		vBox.getChildren().addAll(pane, stackPane);

		return vBox;
	}

	/* methods for buttons to go to a certain scene */

	/**
	 * Sets scene on stage to home page where user can choose to login or register.
	 */
	public void goMainMenu() {

		stage.setScene(sceneHome);
		stage.show();

	}

	/**
	 * Sets scene on stage to first register page.
	 */
	public void goRegister() {

		stage.setScene(sceneRegister1);
		stage.show();

	}

	/**
	 * Sets scene on stage to the second register page.
	 */
	public void goNext() {

		stage.setScene(sceneRegister2);
		stage.show();

	}

	/**
	 * Sets scene on stage to the login page.
	 */
	public void goLogin() {

		stage.setScene(sceneLogin);
		stage.show();

	}

	/**
	 * Sets scene on stage to the page that allows users to choose to update or
	 * check their status.
	 */
	public void goLoggedIn() {
		stage.setScene(sceneLoggedIn);
		stage.show();

	}

	/**
	 * Sets scene on stage to the page that allows a user to update their test
	 * status and add interactions.
	 */
	public void goUpdateStatus() {

		stage.setScene(sceneUpdate);
		stage.show();

	}

	/**
	 * Sets scene on stage to the page that allows a user to view their status and
	 * interactions.
	 */
	public void goCheckStatus() {

		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, sceneLoggedIn.getWidth(), sceneLoggedIn.getHeight());
		stage.setScene(sceneCheck);
		stage.show();

	}

	/**
	 * Sets currentUser to currentSystemUser and create the scene where the user
	 * 
	 * @param currentUser The user that will be set as the current system user.
	 */
	public void login(User currentUser) {
		currentSystemUser = currentUser;
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);
	}

	/**
	 * Determines if login information matches a user in the database and if it
	 * does, stores user identification information in loggedInUser.
	 * 
	 * @param currentUser The system will check if this user's credentials matches
	 *                    an existing credentials.
	 * @return True if the user successfully logs in, false otherwise.
	 */
	public boolean couldLogin(User currentUser) {
		if (expTracker.loginUser(currentUser)) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * Registers a user into the system.
	 * 
	 * @param user         The user to be registered into the system.
	 * @param status       The user's COVID19 test status.
	 * @param interactions The list of the user's interactions.
	 */
	public void registerUser(User user, String status, String interactions) {
		interactions = interactions.replace("\n", "");
		expTracker.registerNewUser(user, status.toUpperCase(), interactions.toUpperCase());
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);
	}

	/**
	 * Retrieves the user's exposure status.
	 * 
	 * @return The user's exposure status.
	 */
	public String checkExposureStatus() {
		return expTracker.getExposureStatus(currentSystemUser);
	}

	/**
	 * Retrieves the user's interactions list.
	 * 
	 * @return The user's interactions list.
	 */
	public String checkPastInteractions() {
		String[] interactionsList = expTracker.getUserInteractions(currentSystemUser);
		String concat = "";

		for (int i = 0; i < interactionsList.length; i++) {
			concat += interactionsList[i];
			if (i != interactionsList.length - 1)
				concat += ", ";
		}
		return concat;
	}

	/**
	 * Retrieves the user's COVID19 test status.
	 * 
	 * @return The user's COVID19 test status.
	 */
	public String checkTestStatus() {
		return expTracker.getTestStatus(currentSystemUser);
	}

	/**
	 * Updates the user's COVID19 test status.
	 * 
	 * @param status The user's COVID19 test status.
	 */
	public void updateTestStatus(String status) {
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);
		expTracker.updateTestStatus(currentSystemUser, status.toUpperCase());
	}

	/**
	 * Add interactions to the user's interactions list.
	 * 
	 * @param interactions Interactions that will be added to the user's
	 *                     interactions list.
	 */
	public void addInteractions(String interactions) {
		expTracker.addInteractions(currentSystemUser, interactions.toUpperCase());
	}

	/**
	 * Checks whether the zip code is exactly 5 digits.
	 * 
	 * @param str The zipcode string.
	 * @return True if the zip code is in the corrent format, False otherwise.
	 */
	public boolean validZipCode(String str) {
		int length = str.length();
		if (length != 5) {
			return false;
		}
		// Traverse the string from
		// start to end
		for (int i = 0; i < length; i++) {

			// Check if character is
			// not digit from 0-9
			// then return false
			if (str.charAt(i) <= '0' && str.charAt(i) >= '9') {
				return false;
			}

		}
		return true;
	}

	/**
	 * Checks whether the status is a valid status (Tested positive, Tested
	 * negative, Not tested).
	 * 
	 * @param status The status that will be checked.
	 * @return True if the status is in the correct format, False otherwise.
	 */
	public boolean validChooseStatus(String status) {
		if (status != "Choose status...") {
			return true;
		} else
			return false;
	}

	/**
	 * Checks whether interactions list is in the correct format.
	 * 
	 * @param interactionList Interactions string that will be checked.
	 * @return True if the interaction list is in the correct format, False
	 *         otherwise.
	 */
	public boolean validInteractionList(String interactionList) {
		int count = 0;

		// If no Interactions had
		if (interactionList.length() == 0)
			return true;
		// if only first name
		if (interactionList.indexOf(" ", count) == -1)
			return false;

		while (interactionList.indexOf(",", count) != -1) {
			int currentComma = interactionList.indexOf(",", count);
			// checks if there is a space between first/last names between commas
			if (interactionList.indexOf(",", count) <= interactionList.indexOf(" ", count))
				return false;
			// checks spacing
			else if (interactionList.indexOf(" ", currentComma) != currentComma + 1) {
				// corner case where comma is end of string
				if (interactionList.indexOf(" ", currentComma) == -1)
					return true;
				return false;
			} else
				count = currentComma + 2;

		}

		// corner case where only one interaction, no spacing
		if (count < interactionList.length() && interactionList.indexOf(" ", count) == -1)
			return false;

		return true;
	}

	/**
	 * Checks whether the address is a number followed by at least one string.
	 * 
	 * @param address Address string that will be checked.
	 * @return True if the address is in the correct format, False otherwise.
	 */
	public boolean validAddress(String address) {
		// checks first case of address formatting
		if (!address.contains(" "))
			return false;

		// splits address to check num string
		String[] split = address.split(" ");
		if (split.length < 2)
			return false;
		String str = split[0];

		for (int i = 0; i < str.length(); i++) {
			// Check if character is
			// not digit
			// then return false

			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;

	}

}
