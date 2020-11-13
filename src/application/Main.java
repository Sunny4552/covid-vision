package application;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
import javafx.stage.Stage;

//TODO Replace with T.K's GUI code

		// when login button, create User(info) with name, street, city state, and
		// zipcode, and then login(user)
		// when registerx new user, create User(info) with name, street, city state, and
		// zipcode, and then call login(user))
		// If login(info) fails (is false), continue to get covid status,
		// interactions(exact input from user), and call registerUser(user, status,
		// interactions)

public class Main extends Application {

	private ExposureTracker expTrcker = new ExposureTracker("FileDatabase.txt");
	private User loggedInUser = null; // stores user currently logged in

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
    private Pane checkPane; // can update pane type when we figure out what pane we want

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
        //checkPane = createCheckPane();

        // set scenes
        sceneHome = new Scene(homePane, 750, 500);
        sceneRegister1 = new Scene(registerPane1, 750, 500);
        sceneRegister2 = new Scene(registerPane2, 750, 500);
        sceneLogin = new Scene(loginPane, 750, 500);
        sceneLoggedIn = new Scene(loggedInPane, 750, 500);
        sceneUpdate = new Scene(updatePane, 750, 500);
        //sceneCheck = new Scene(checkPane, 750, 500);

        // start out at homepage
        primaryStage.setTitle("20/20 Covid Vision");
        primaryStage.setScene(sceneHome);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

    /* methods that create the scenes */

    // creates the default homepage where user can login/register
    public VBox createHomePane() {

        VBox pane = new VBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(10);

        Text title = new Text("20/20 COVID VISION");
        title.setFont(Font.font(48));

        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setSpacing(5);

        Button btnRegister = new Button("Register");
        btnRegister.setFont(Font.font(18));
        btnRegister.setOnMouseClicked(e -> {goRegister();});

        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font(18));
        btnLogin.setOnMouseClicked(e -> {goLogin();});

        buttonContainer.getChildren().addAll(btnRegister, btnLogin);

        pane.getChildren().addAll(title, buttonContainer);

        return pane;

    }

    // creates the first register page
    public HBox createRegisterPane1() {

        // the overarching hbox container for this page
        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);

        // the "title" of this page
        Text register = new Text("REGISTER");
        register.setFont(Font.font(48));
        register.setTextAlignment(TextAlignment.RIGHT);

        // a grid pane to contain labels and text fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        Label lblName = new Label("Name");
        lblName.setFont(Font.font(16));
        GridPane.setHalignment(lblName, HPos.RIGHT);
        grid.add(lblName, 0, 0);

        Label lblAddress = new Label("Address");
        lblAddress.setFont(Font.font(16));
        GridPane.setHalignment(lblAddress, HPos.RIGHT);
        grid.add(lblAddress, 0, 1);

        Label lblCity = new Label("City");
        lblCity.setFont(Font.font(16));
        GridPane.setHalignment(lblCity, HPos.RIGHT);
        grid.add(lblCity, 0, 2);

        Label lblState = new Label("State");
        lblState.setFont(Font.font(16));
        GridPane.setHalignment(lblState, HPos.RIGHT);
        grid.add(lblState, 0, 3);

        Label lblZip = new Label("Zip Code");
        lblZip.setFont(Font.font(16));
        GridPane.setHalignment(lblZip, HPos.RIGHT);
        grid.add(lblZip, 0, 4);

        TextField tfName = new TextField();
        tfName.setFont(Font.font(16));
        tfName.setPromptText("Enter first and last name.");
        grid.add(tfName, 1, 0);
//        String value1 = tfName.getText();
//        System.out.println(value1);

        TextField tfAddress = new TextField();
        tfAddress.setFont(Font.font(16));
        tfAddress.setPromptText("Enter street address.");
        grid.add(tfAddress, 1, 1);

        TextField tfCity = new TextField();
        tfCity.setFont(Font.font(16));
        tfCity.setPromptText("Enter city.");
        grid.add(tfCity, 1, 2);

        TextField tfState = new TextField();
        tfState.setFont(Font.font(16));
        tfState.setPromptText("Enter state abbreviation.");
        grid.add(tfState, 1, 3);

        TextField tfZip = new TextField();
        tfZip.setFont(Font.font(16));
        tfZip.setPromptText("Enter zip code.");
        grid.add(tfZip, 1, 4);

        // an hbox to contain the buttons
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.setSpacing(5);

        Button btnReturnToMain = new Button("Return to Main Menu");
        btnReturnToMain.setFont(Font.font(16));
        btnReturnToMain.setOnMouseClicked(e -> {goMainMenu();});

        Label lblError = new Label("");
        lblError.setFont(Font.font(16));
        GridPane.setHalignment(lblError, HPos.LEFT);
        grid.add(lblError, 1, 5);
        
        Button btnNext = new Button("Next");
        btnNext.setFont(Font.font(16));
        btnNext.setOnMouseClicked(e -> {
        	String valName = tfName.getText();
        	String valAddress = tfAddress.getText();
        	String valCity = tfCity.getText();
        	String valState = tfState.getText();
        	String valZip = tfZip.getText();
        	User testName = new User(valName);
        	
        	//if one of the fields are empty
        	if(valName.isEmpty() || valAddress.isEmpty() || valCity.isEmpty() || 
        			valState.isEmpty() || valZip.isEmpty())
        	{
                lblError.setText("FIELD(S) ARE EMPTY");
        	}
        	
        	//if name is in wrong format
        	else if(!User.validName(valName))
        	{
        		tfName.clear();
        		tfName.setPromptText("FULL NAME W/ SPACES");
        	}
        	
        	//if zip code is in wrong format
        	else if(!validZipCode(valZip))
        	{
        		tfZip.clear();
        		tfZip.setPromptText("ENTER ONLY 5 DIGITS");
        	}
        	
        	//if state abbreviation is in wrong format
        	else if (valState.length() !=2) {
        		tfState.clear();
        		tfZip.setPromptText("ENTER ONLY STATE ABBREVIATIONS");
        	}
        	
        	//if everything works
        	else
        	{
	        	int value5 = Integer.parseInt(valZip);
	        	loggedInUser = new User(valName, valAddress, valCity, valState, value5);
	        	lblError.setText("");
	        	tfName.clear();
	        	tfAddress.clear();
	        	tfCity.clear();
	        	tfState.clear();
	        	tfZip.clear();
	        	tfName.setPromptText("Enter first and last name.");
	        	tfZip.setPromptText("Enter zip code.");
	        	goNext();
        	}
            //System.out.println(loggedInUser.toString());
        	});

        buttonContainer.getChildren().addAll(btnReturnToMain, btnNext);

        // a vbox to contain grid and buttonContainer
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(grid, buttonContainer);

        pane.getChildren().addAll(register, vBox);

        return pane;

    }

    // creates the next register page that the first goes to next
    public HBox createRegisterPane2() {

        // the overarching hbox container for this page
        HBox pane = new HBox();
        pane.setAlignment(Pos.CENTER);
        pane.setSpacing(50);

        // the "title" of this page
        Text register = new Text("REGISTER");
        register.setFont(Font.font(48));
        register.setTextAlignment(TextAlignment.RIGHT);

        // a grid pane to contain labels and text fields
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);

        // labels
        Label lblStatus = new Label("COVID-19 Status");
        lblStatus.setFont(Font.font(16));
        GridPane.setHalignment(lblStatus, HPos.RIGHT);
        grid.add(lblStatus, 0, 0);

        Label lblInteractions = new Label("Interactions");
        lblInteractions.setFont(Font.font(16));
        GridPane.setHalignment(lblInteractions, HPos.RIGHT);
        grid.add(lblInteractions, 0, 1);

        // choose status menu and interaction text field
        MenuButton chooseStatus = new MenuButton();
        chooseStatus.setFont(Font.font(16));
        chooseStatus.setText("Choose status...");

        MenuItem notTested = new MenuItem("Not tested");
        MenuItem testPos = new MenuItem("Tested positive");
        MenuItem testNeg = new MenuItem("Tested negative");

        // update the actions to update in DB too
        notTested.setOnAction(e -> {chooseStatus.setText("Not tested");});
        testPos.setOnAction(e -> {chooseStatus.setText("Tested positive");});
        testNeg.setOnAction(e -> {chooseStatus.setText("Tested negative");});

        chooseStatus.getItems().addAll(notTested, testNeg, testPos);
        grid.add(chooseStatus, 1, 0);

        TextField tfInteractions = new TextField();
        tfInteractions.setFont(Font.font(16));
        tfInteractions.setPromptText("Name1, Name2, ...");
        grid.add(tfInteractions, 1, 1);

        // an hbox to contain the buttons
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.setSpacing(5);

        Button btnBack = new Button("Back");
        btnBack.setFont(Font.font(16));
        btnBack.setOnMouseClicked(e -> {goRegister();});

        Button btnSubmit = new Button("Submit");
        btnSubmit.setFont(Font.font(16));
        btnSubmit.setOnMouseClicked(e -> {
        	String value1 = chooseStatus.getText();
        	String value2 = tfInteractions.getText();
        	
        	registerUser(loggedInUser, value1, value2);
        	chooseStatus.setText("Choose status...");
        	tfInteractions.clear();
        	goLoggedIn();
        	});

        buttonContainer.getChildren().addAll(btnBack, btnSubmit);

        // a vbox to contain grid and buttonContainer
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(grid, buttonContainer);

        pane.getChildren().addAll(register, vBox);

        return pane;

    }

    // creates the page where user can login
    public HBox createLoginPane() {

        // the overarching hbox container for this page
        HBox pane = new HBox();
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

        Label lblName = new Label("Name");
        lblName.setFont(Font.font(16));
        GridPane.setHalignment(lblName, HPos.RIGHT);
        grid.add(lblName, 0, 0);

        Label lblAddress = new Label("Address");
        lblAddress.setFont(Font.font(16));
        GridPane.setHalignment(lblAddress, HPos.RIGHT);
        grid.add(lblAddress, 0, 1);

        Label lblCity = new Label("City");
        lblCity.setFont(Font.font(16));
        GridPane.setHalignment(lblCity, HPos.RIGHT);
        grid.add(lblCity, 0, 2);

        Label lblState = new Label("State");
        lblState.setFont(Font.font(16));
        GridPane.setHalignment(lblState, HPos.RIGHT);
        grid.add(lblState, 0, 3);

        Label lblZip = new Label("Zip Code");
        lblZip.setFont(Font.font(16));
        GridPane.setHalignment(lblZip, HPos.RIGHT);
        grid.add(lblZip, 0, 4);

        TextField tfName = new TextField();
        tfName.setFont(Font.font(16));
        tfName.setPromptText("Enter first and last name.");
        grid.add(tfName, 1, 0);

        TextField tfAddress = new TextField();
        tfAddress.setFont(Font.font(16));
        tfAddress.setPromptText("Enter street address.");
        grid.add(tfAddress, 1, 1);

        TextField tfCity = new TextField();
        tfCity.setFont(Font.font(16));
        tfCity.setPromptText("Enter city.");
        grid.add(tfCity, 1, 2);

        TextField tfState = new TextField();
        tfState.setFont(Font.font(16));
        tfState.setPromptText("Enter state abbreviations.");
        grid.add(tfState, 1, 3);

        TextField tfZip = new TextField();
        tfZip.setFont(Font.font(16));
        tfZip.setPromptText("Enter zip code.");
        grid.add(tfZip, 1, 4);

        // an hbox to contain the buttons
        HBox buttonContainer = new HBox();
        buttonContainer.setAlignment(Pos.TOP_RIGHT);
        buttonContainer.setSpacing(5);

        Button btnReturnToMain = new Button("Return to Main Menu");
        btnReturnToMain.setFont(Font.font(16));
        btnReturnToMain.setOnMouseClicked(e -> {goMainMenu();});
        
        Label lblError = new Label("");
        lblError.setFont(Font.font(16));
        GridPane.setHalignment(lblError, HPos.LEFT);
        grid.add(lblError, 1, 5);

        Button btnLogin = new Button("Login");
        btnLogin.setFont(Font.font(16));
        btnLogin.setOnMouseClicked(e -> {
        	String valName = tfName.getText();
        	String valAddress = tfAddress.getText();
        	String valCity = tfCity.getText();
        	String valState = tfState.getText();
        	User testName = new User(valName);
        	if(valName.isEmpty() || valAddress.isEmpty() || valCity.isEmpty() || 
        			valState.isEmpty() || tfZip.getText().isEmpty())
        	{
                lblError.setText("FIELD(S) ARE EMPTY");
        	}
        	else if(!User.validName(valName))
        	{
        		tfName.clear();
        		tfName.setPromptText("FULL NAME W/ SPACES");
        	}
        	else if(!validZipCode(tfZip.getText()))
        	{
        		tfZip.clear();
        		tfZip.setPromptText("ENTER ONLY 5 DIGITS");
        	}
        	else
        	{
	        	
	        	int value5 = Integer.parseInt(tfZip.getText());
	        	loggedInUser = new User(valName, valAddress, valCity, valState, value5);
	        	if(login(loggedInUser))
	        	{
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
	        	else
	        	{
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

        buttonContainer.getChildren().addAll(btnReturnToMain, btnLogin);

        // a vbox to contain grid and buttonContainer
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(10);
        vBox.getChildren().addAll(grid, buttonContainer);

        pane.getChildren().addAll(login, vBox);

        return pane;

    }

    // creates the page displayed when a user finishes registering/logging in
    public BorderPane createLoggedInPane() {

        BorderPane pane = new BorderPane();

        // default message for the center
        VBox defaultMessage = new VBox();
        defaultMessage.setAlignment(Pos.CENTER);
        defaultMessage.setSpacing(20);

        Text selectTitle = new Text("Select one of the options above to display here!");
        selectTitle.setFont(Font.font("", FontWeight.BOLD, 24));
        selectTitle.setUnderline(true);

        // box to hold description for the Update button at top
        VBox updateBox = new VBox();
        updateBox.setAlignment(Pos.CENTER);

        // description about the Update button at top
        Text updateTitle = new Text("Update COVID-19 Status/Interactions");
        updateTitle.setFont(Font.font("", FontWeight.BOLD, 18));
        updateTitle.setTextAlignment(TextAlignment.CENTER);
        Text updateDescription = new Text("Update your COVID-19 Status and add people who you've interacted " +
                "with in the last 14 days.");
        updateDescription.setFont(Font.font("", FontPosture.ITALIC, 18));
        updateDescription.setWrappingWidth(400);
        updateDescription.setTextAlignment(TextAlignment.CENTER);
        updateBox.getChildren().addAll(updateTitle, updateDescription);

        // box to hold description for Check button at top
        VBox checkBox = new VBox();
        checkBox.setAlignment(Pos.CENTER);

        // description about the Check button at top
        Text checkTitle = new Text("Check COVID-19 Exposure Status");
        checkTitle.setFont(Font.font("", FontWeight.BOLD, 18));
        checkTitle.setTextAlignment(TextAlignment.CENTER);
        Text checkDescription = new Text("See if anyone on your list of interactions has been exposed to or " +
                "interacted with others who have been exposed to COVID-19.");
        checkDescription.setFont(Font.font("", FontPosture.ITALIC, 18));
        checkDescription.setWrappingWidth(400);
        checkDescription.setTextAlignment(TextAlignment.CENTER);
        checkBox.getChildren().addAll(checkTitle, checkDescription);

        // set the default of the center pane to be these messages
        defaultMessage.getChildren().addAll(selectTitle, updateBox, checkBox);
        pane.setCenter(defaultMessage);

        // hbox for top buttons
        HBox hBoxTop = new HBox();
        hBoxTop.setAlignment(Pos.CENTER);
        hBoxTop.setSpacing(5);
        hBoxTop.setPadding(new Insets(20, 0, 0, 0));

        Button btnUpdateStatus = new Button("Update COVID-19 Status/Interactions");
        btnUpdateStatus.setFont(Font.font(16));
        btnUpdateStatus.setOnMouseClicked(e -> {goUpdateStatus();});

        Button btnCheckStatus = new Button("Check COVID-19 Exposure Status");
        btnCheckStatus.setFont(Font.font(16));
        btnCheckStatus.setOnMouseClicked(e -> {goCheckStatus();});

        hBoxTop.getChildren().addAll(btnUpdateStatus, btnCheckStatus);

        pane.setTop(hBoxTop);

        // put a Return to Main Menu at the bottom of the scene
        StackPane bottomPane = new StackPane();
        bottomPane.setAlignment(Pos.CENTER);
        Button btnReturnMain = new Button("Return to Main Menu");
        btnReturnMain.setFont(Font.font(16));
        btnReturnMain.setOnMouseClicked(e -> {goMainMenu();});

        bottomPane.getChildren().add(btnReturnMain);

        pane.setBottom(bottomPane);
        pane.setPadding(new Insets(0, 0, 20, 0));

        // return the completed pane
        return pane;

    }

    // creates the page for user to update their status
    public VBox createUpdatePane() {

        // holds section for update status and add interactions
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setSpacing(100);
        vBox.setPadding(new Insets(50, 0, 50, 0));

        // vBox for update status
        VBox updateBox = new VBox();
        updateBox.setSpacing(5);
        updateBox.setAlignment(Pos.CENTER);

        Label lblUpdate = new Label("Update Status");
        lblUpdate.setFont(Font.font(18));

        MenuButton chooseStatus = new MenuButton();
        chooseStatus.setFont(Font.font(16));
        chooseStatus.setText("Choose status...");

        MenuItem notTested = new MenuItem("Not tested");
        MenuItem testPos = new MenuItem("Tested positive");
        MenuItem testNeg = new MenuItem("Tested negative");

        // update the actions to update in DB too
        notTested.setOnAction(e -> {chooseStatus.setText("Not tested");});
        testPos.setOnAction(e -> {chooseStatus.setText("Tested positive");});
        testNeg.setOnAction(e -> {chooseStatus.setText("Tested negative");});

        chooseStatus.getItems().addAll(notTested, testNeg, testPos);

        updateBox.getChildren().addAll(lblUpdate, chooseStatus);

        // hbox for submitting new interactions
        HBox hBox = new HBox();
        hBox.setAlignment(Pos.CENTER);
        hBox.setSpacing(5);

        Label lblName = new Label("Interactions");
        lblName.setFont(Font.font(18));

        TextField tfName = new TextField();
        tfName.setPromptText("Enter name.");
        tfName.setFont(Font.font(18));

        Button btSubmit = new Button("Submit Name");
        btSubmit.setFont(Font.font(18));
        btSubmit.setOnMouseClicked(e -> {}); // fill in later -- add name to database

        hBox.getChildren().addAll(lblName, tfName, btSubmit);

        // add button at bottom to return to logged in screen
        StackPane pane = new StackPane();
        pane.setPadding(new Insets(100, 0, 0, 0));
        pane.setAlignment(Pos.BASELINE_CENTER);

        Button btReturn = new Button("Done");
        btReturn.setFont(Font.font(18));
        btReturn.setAlignment(Pos.CENTER);
        btReturn.setOnMouseClicked(e -> {goLoggedIn();});

        pane.getChildren().add(btReturn);

        vBox.getChildren().addAll(updateBox, hBox, pane);

        return vBox;

    }

    // TODO: creates the page to display user's exposure status -- use DB to get info for this?
    public Pane createCheckPane() {
        return null;
    }

    /* methods for buttons to go to a certain scene */

    // for the button that sends user back to login/register screen
    public void goMainMenu() {

        stage.setScene(sceneHome);
        stage.show();

    }

    // for the button that sends user to first register page
    public void goRegister() {
    	
        stage.setScene(sceneRegister1);
        stage.show();

    }

    // for the button that sends user to next register page
    public void goNext() {

        stage.setScene(sceneRegister2);
        stage.show();

    }

    // for the button that sends user to the login page
    public void goLogin() {

        stage.setScene(sceneLogin);
        stage.show();

    }

    // for the button that logs the user in and sends them to the page to choose update/check status
    public void goLoggedIn() {

        stage.setScene(sceneLoggedIn);
        stage.show();

    }

    // for the button so user can go update status and interactions
    public void goUpdateStatus() {

        stage.setScene(sceneUpdate);
        stage.show();

    }

    // TODO for button so that user can check exposure status - finish createCheckPane() to work
    public void goCheckStatus() {

        stage.setScene(sceneCheck);
        stage.show();

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
	
	public boolean validZipCode(String str) 
    { 
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
            if (str.charAt(i) <= '0'
                && str.charAt(i) >= '9') { 
                return false; 
            } 
  
        } 
        return true; 
    } 
	
}
