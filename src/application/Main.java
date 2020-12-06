package application;

import javafx.application.Application;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
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
import javafx.scene.image.ImageView;

public class Main extends Application {

	private ExposureTracker expTracker = new ExposureTracker("FileDatabase.txt");
	private User currentSystemUser = null; // stores user currently logged in

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

		// make fill screen
		Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

		// set scenes
		sceneHome = new Scene(homePane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneRegister1 = new Scene(registerPane1, screenBounds.getWidth(), screenBounds.getHeight());
		sceneRegister2 = new Scene(registerPane2, screenBounds.getWidth(), screenBounds.getHeight());
		sceneLogin = new Scene(loginPane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneLoggedIn = new Scene(loggedInPane, screenBounds.getWidth(), screenBounds.getHeight());
		sceneUpdate = new Scene(updatePane, screenBounds.getWidth(), screenBounds.getHeight());
		//sceneCheck = new Scene(checkPane, screenBounds.getWidth(), screenBounds.getHeight());

		// start out at homepage
		primaryStage.setTitle("20/20 Covid Vision");
		primaryStage.setMaximized(true);
		primaryStage.setScene(sceneHome);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/covid2.png")));
		primaryStage.show();

	}

	public static void main(String[] args) {
		launch(args);
	}

	/* methods that create the scenes */

	// creates the default homepage where user can login/register
	private VBox createHomePane() {

		VBox pane = new VBox();
		pane.setAlignment(Pos.CENTER);
		pane.setSpacing(10);

		//Text title = new Text("20/20 COVID VISION");
		//title.setFont(Font.font(48));

		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.CENTER);

		// image for logo and welcome
		Image welcomeImage = new Image(getClass().getResourceAsStream("/images/welcome2.png"));
		ImageView welcome = new ImageView(welcomeImage);

		// image to use as register button
		Image registerImage = new Image(getClass().getResourceAsStream("/images/register1.png"));
		Image registerHovered = new Image(getClass().getResourceAsStream("/images/register2.png"));
		ImageView register = new ImageView(registerImage);

		register.setFitHeight(100);
		register.setPreserveRatio(true);
		register.setOnMouseClicked(e -> {goRegister();});

		register.setOnMouseEntered(e -> {register.setImage(registerHovered);});
		register.setOnMouseExited(e -> {register.setImage(registerImage);});

		// image to use as login button
		Image loginImage = new Image(getClass().getResourceAsStream("/images/login1.png"));
		Image loginHovered = new Image(getClass().getResourceAsStream("/images/login2.png"));
		ImageView login = new ImageView(loginImage);

		login.setFitHeight(100);
		login.setPreserveRatio(true);
		login.setOnMouseClicked(e -> {goLogin();});

		login.setOnMouseEntered(e -> {login.setImage(loginHovered);});
		login.setOnMouseExited(e -> {login.setImage(loginImage);});

		buttonContainer.getChildren().addAll(register, login);

		pane.getChildren().addAll(welcome, buttonContainer);

		return pane;

	}

	// creates the first register page
	private HBox createRegisterPane1() {

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

		// image to use as back button
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView backButton = new ImageView(backImage);

		backButton.setFitHeight(100);
		backButton.setPreserveRatio(true);
		backButton.setOnMouseClicked(e -> {goMainMenu();});

		backButton.setOnMouseEntered(e -> {backButton.setImage(backHovered);});
		backButton.setOnMouseExited(e -> {backButton.setImage(backImage);});

		Label lblError = new Label("");
		lblError.setFont(Font.font(16));
		GridPane.setHalignment(lblError, HPos.LEFT);
		grid.add(lblError, 1, 5);

		// image to use as next button
		Image nextImage = new Image(getClass().getResourceAsStream("/images/next1.png"));
		Image nextHovered = new Image(getClass().getResourceAsStream("/images/next2.png"));
		ImageView nextButton = new ImageView(nextImage);

		nextButton.setFitHeight(100);
		nextButton.setPreserveRatio(true);
		nextButton.setOnMouseClicked(e -> {
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
	currentSystemUser = new User(valName, valAddress, valCity, valState, value5);
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

		nextButton.setOnMouseEntered(e -> {nextButton.setImage(nextHovered);});
		nextButton.setOnMouseExited(e -> {nextButton.setImage(nextImage);});

		buttonContainer.getChildren().addAll(backButton, nextButton);

		// a vbox to contain grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		pane.getChildren().addAll(register, vBox);

		return pane;

	}

	// creates the next register page that the first goes to next
	private HBox createRegisterPane2() {

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
		GridPane.setValignment(lblInteractions, VPos.TOP);
		grid.add(lblInteractions, 0, 1);

		// choose status menu and interaction text field
		MenuButton chooseStatus = new MenuButton();
		chooseStatus.setFont(Font.font(16));
		chooseStatus.setText("Choose status...");
		chooseStatus.setPrefWidth(200);

		MenuItem notTested = new MenuItem("Not tested");
		MenuItem testPos = new MenuItem("Tested positive");
		MenuItem testNeg = new MenuItem("Tested negative");

		// need to force user to choose a status
		notTested.setOnAction(e -> {chooseStatus.setText("Not tested");});
		testPos.setOnAction(e -> {chooseStatus.setText("Tested positive");});
		testNeg.setOnAction(e -> {chooseStatus.setText("Tested negative");});

		chooseStatus.getItems().addAll(notTested, testNeg, testPos);
		grid.add(chooseStatus, 1, 0);

		// text area to enter interactions
		TextArea tfInteractions = new TextArea();
		tfInteractions.setFont(Font.font(16));
		tfInteractions.setPrefSize(200,200);
		tfInteractions.setWrapText(true);
		tfInteractions.setPromptText("FirstName LastName, FirstName2 LastName2, ...");
		grid.add(tfInteractions, 1, 1);

		// an hbox to contain the buttons
		HBox buttonContainer = new HBox();
		buttonContainer.setAlignment(Pos.TOP_RIGHT);
		//buttonContainer.setSpacing(5);

		// image to use as back button
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView backButton = new ImageView(backImage);

		backButton.setFitHeight(100);
		backButton.setPreserveRatio(true);
		backButton.setOnMouseClicked(e -> {goRegister();});

		backButton.setOnMouseEntered(e -> {backButton.setImage(backHovered);});
		backButton.setOnMouseExited(e -> {backButton.setImage(backImage);});

		// image to use as register button
		Image registerImage = new Image(getClass().getResourceAsStream("/images/register1.png"));
		Image registerHovered = new Image(getClass().getResourceAsStream("/images/register2.png"));
		ImageView registerButton = new ImageView(registerImage);

		registerButton.setFitHeight(100);
		registerButton.setPreserveRatio(true);
		registerButton.setOnMouseClicked(e -> {
		    String value1 = chooseStatus.getText();
		    String value2 = tfInteractions.getText();

		    if(validInteractionList(value2) && validChooseStatus(value1))
		    {
			registerUser(currentSystemUser, value1, value2);
			chooseStatus.setText("Choose status...");
			tfInteractions.clear();
			goLoggedIn();
		    }
		    else
		    {

			tfInteractions.clear();
			tfInteractions.setPromptText("INVALID FORMAT");
		    }
		});

		registerButton.setOnMouseEntered(e -> {registerButton.setImage(registerHovered);});
		registerButton.setOnMouseExited(e -> {registerButton.setImage(registerImage);});

		buttonContainer.getChildren().addAll(backButton, registerButton);

		// a vbox to contain grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		pane.getChildren().addAll(register, vBox);

		return pane;

	}

	// creates the page where user can login
	private HBox createLoginPane() {

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

		// image to use as back button
		Image backImage = new Image(getClass().getResourceAsStream("/images/back1.png"));
		Image backHovered = new Image(getClass().getResourceAsStream("/images/back2.png"));
		ImageView back = new ImageView(backImage);

		back.setFitHeight(100);
		back.setPreserveRatio(true);
		back.setOnMouseClicked(e -> {goMainMenu();});

		back.setOnMouseEntered(e -> {back.setImage(backHovered);});
		back.setOnMouseExited(e -> {back.setImage(backImage);});

		// image to use as login button
		Image loginImage = new Image(getClass().getResourceAsStream("/images/login1.png"));
		Image loginHovered = new Image(getClass().getResourceAsStream("/images/login2.png"));
		ImageView loginButton = new ImageView(loginImage);

		loginButton.setFitHeight(100);
		loginButton.setPreserveRatio(true);

		loginButton.setOnMouseEntered(e -> {loginButton.setImage(loginHovered);});
		loginButton.setOnMouseExited(e -> {loginButton.setImage(loginImage);});

		Label lblError = new Label("");
		lblError.setFont(Font.font(16));
		GridPane.setHalignment(lblError, HPos.LEFT);
		grid.add(lblError, 1, 5);

		loginButton.setOnMouseClicked(e -> {
		    String valName = tfName.getText();
		    String valAddress = tfAddress.getText();
		    String valCity = tfCity.getText();
		    String valState = tfState.getText();
		    
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
			currentSystemUser = new User(valName, valAddress, valCity, valState, value5);
			if(couldLogin(currentSystemUser))
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

		buttonContainer.getChildren().addAll(back, loginButton);

		// a vbox to contain grid and buttonContainer
		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(10);
		vBox.getChildren().addAll(grid, buttonContainer);

		pane.getChildren().addAll(login, vBox);

		return pane;

	}

	// creates the page displayed when a user finishes registering/logging in
	private BorderPane createLoggedInPane() {

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
		Text updateDescription = new Text("Update your COVID-19 Status, and add people who you have interacted " +
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
		Text checkDescription = new Text("View your COVID-19 test status, degree of exposure, and interactions list.");
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

		// image to use as update button
		Image updateImage = new Image(getClass().getResourceAsStream("/images/update1.png"));
		Image updateHovered = new Image(getClass().getResourceAsStream("/images/update2.png"));
		ImageView update = new ImageView(updateImage);

		update.setFitHeight(100);
		update.setPreserveRatio(true);
		update.setOnMouseClicked(e -> {goUpdateStatus();});

		update.setOnMouseEntered(e -> {update.setImage(updateHovered);});
		update.setOnMouseExited(e -> {update.setImage(updateImage);});

		// image to use as check status button
		Image checkImage = new Image(getClass().getResourceAsStream("/images/check2.png"));
		Image checkHovered = new Image(getClass().getResourceAsStream("/images/check1.png"));
		ImageView check = new ImageView(checkImage);

		check.setFitHeight(100);
		check.setPreserveRatio(true);
		check.setOnMouseClicked(e -> {goCheckStatus();});

		check.setOnMouseEntered(e -> {check.setImage(checkHovered);});
		check.setOnMouseExited(e -> {check.setImage(checkImage);});

		hBoxTop.getChildren().addAll(update, check);

		pane.setTop(hBoxTop);

		// put a Return to Main Menu at the bottom of the scene
		StackPane bottomPane = new StackPane();
		bottomPane.setAlignment(Pos.CENTER);

		// image to use as log out button
		Image logOutImage = new Image(getClass().getResourceAsStream("/images/logout1.png"));
		Image logOutHovered = new Image(getClass().getResourceAsStream("/images/logout2.png"));
		ImageView logOut = new ImageView(logOutImage);

		logOut.setFitHeight(100);
		logOut.setPreserveRatio(true);
		logOut.setOnMouseClicked(e -> {goMainMenu();});

		logOut.setOnMouseEntered(e -> {logOut.setImage(logOutHovered);});
		logOut.setOnMouseExited(e -> {logOut.setImage(logOutImage);});

		bottomPane.getChildren().add(logOut);

		pane.setBottom(bottomPane);
		pane.setPadding(new Insets(0, 0, 20, 0));

		// return the completed pane
		return pane;
		
	}

	// creates the page for user to update their status
	private VBox createUpdatePane() {

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
		lblUpdate.setFont(Font.font(16));

		MenuButton chooseStatus = new MenuButton();
		chooseStatus.setFont(Font.font(16));
		chooseStatus.setText("Choose status...");
		chooseStatus.setPrefWidth(200);

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
		lblName.setFont(Font.font(16));

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

		submit.setOnMouseEntered(e -> {submit.setImage(submitHovered);});
		submit.setOnMouseExited(e -> {submit.setImage(submitImage);});

		submit.setOnMouseClicked(e -> {
		    String interaction = tfName.getText();

		    expTracker.addInteractions(currentSystemUser, interaction);
		    //chooseStatus.setText("Choose status...");
		    tfName.clear();
		}); // fill in later -- add name to database

		hBox.getChildren().addAll(lblName, tfName, submit);

		// add button at bottom to return to logged in screen
		StackPane pane = new StackPane();
		pane.setPadding(new Insets(100, 0, 0, 0));
		pane.setAlignment(Pos.BASELINE_CENTER);

		// image to use as done button
		Image doneImage = new Image(getClass().getResourceAsStream("/images/done1.png"));
		Image doneHovered = new Image(getClass().getResourceAsStream("/images/done2.png"));
		ImageView done = new ImageView(doneImage);

		done.setFitHeight(100);
		done.setPreserveRatio(true);

		done.setOnMouseEntered(e -> {done.setImage(doneHovered);});
		done.setOnMouseExited(e -> {done.setImage(doneImage);});
		done.setOnMouseClicked(e -> {
		    if (validChooseStatus(chooseStatus.getText())) {
			updateTestStatus(chooseStatus.getText());
		    }
		    goLoggedIn();});

		pane.getChildren().add(done);

		vBox.getChildren().addAll(updateBox, hBox, pane);

		return vBox;

	}

	// creates the page to display user's exposure status
	private Pane createCheckPane() {

		VBox vBox = new VBox();
		vBox.setAlignment(Pos.CENTER);
		vBox.setSpacing(50);
		vBox.setPadding(new Insets(50, 0, 50, 0));

		GridPane pane = new GridPane();
		pane.setVgap(10);
		pane.setHgap(10);
		pane.setAlignment(Pos.CENTER);

		// labels
		Label lblTestStatus = new Label("Test Status:");
		lblTestStatus.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblTestStatus, HPos.RIGHT);
		pane.add(lblTestStatus, 0, 0);

		Label lblExposureStatus = new Label("Exposure Status:");
		lblExposureStatus.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblExposureStatus, HPos.RIGHT);
		pane.add(lblExposureStatus, 0, 1);

		Label lblInteractionList = new Label("Interactions:");
		lblInteractionList.setFont(Font.font("", FontWeight.BOLD, 16));
		GridPane.setHalignment(lblInteractionList, HPos.RIGHT);
		pane.add(lblInteractionList,0,2);

		// actual values
		Text testStatus = new Text();
		testStatus.setFont(Font.font(16));
		// use testStatus.[method that returns test status value]
		testStatus.setText("I'm FINE.");
		pane.add(testStatus, 1, 0);

		Text exposureStatus = new Text();
		exposureStatus.setFont(Font.font(16));
		//status.setText(checkExposureStatus());
		exposureStatus.setText("I'm FINE.");
		pane.add(exposureStatus, 1, 1);

		Text interactions = new Text();
		interactions.setFont(Font.font(16));
		//interactions.setText(checkPastInteractions());
		interactions.setText("LOL ZERO FRIENDS");
		interactions.setWrappingWidth(400);
		pane.add(interactions, 1, 2);

		// image to use as done button
		Image doneImage = new Image(getClass().getResourceAsStream("/images/done1.png"));
		Image doneHovered = new Image(getClass().getResourceAsStream("/images/done2.png"));
		ImageView done = new ImageView(doneImage);

		done.setFitHeight(100);
		done.setPreserveRatio(true);

		done.setOnMouseEntered(e -> {done.setImage(doneHovered);});
		done.setOnMouseExited(e -> {done.setImage(doneImage);});
		done.setOnMouseClicked(e -> {goLoggedIn();});

		// add button at bottom to return to logged in screen
		StackPane stackPane = new StackPane();
		stackPane.setPadding(new Insets(100, 0, 0, 0));
		stackPane.setAlignment(Pos.BASELINE_CENTER);
		stackPane.getChildren().add(done);

		vBox.getChildren().addAll(pane, stackPane);

		return vBox;
	}

	/* methods for buttons to go to a certain scene */

	// for the button that sends user back to login/register screen
	private void goMainMenu() {

		stage.setScene(sceneHome);
		stage.show();

	}

	// for the button that sends user to first register page
	private void goRegister() {

		stage.setScene(sceneRegister1);
		stage.show();

	}

	// for the button that sends user to next register page
	private void goNext() {

		stage.setScene(sceneRegister2);
		stage.show();

	}

	// for the button that sends user to the login page
	private void goLogin() {

		stage.setScene(sceneLogin);
		stage.show();

	}

	// for the button that logs the user in and sends them to the page to choose
	// update/check status
	private void goLoggedIn() {
		stage.setScene(sceneLoggedIn);
		stage.show();

	}

	// for the button so user can go update status and interactions
	private void goUpdateStatus() {

		stage.setScene(sceneUpdate);
		stage.show();

	}

	// lets user check their status and interactions if they are logged in
	private void goCheckStatus() {

		checkPane = createCheckPane();
		//sceneCheck = new Scene(checkPane, 750, 500);
		//stage.setScene(sceneCheck);
		stage.getScene().setRoot(checkPane);
		stage.show();

	}

	/**
	 * Determines if login information matches a user in the database and if it
	 * does, stores user identification information in loggedInUser.
	 * @param currentUser User to check if could login
	 *
	 */
	private void login(User currentUser) {
		currentSystemUser = currentUser;
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);

	}

	private boolean couldLogin(User currentUser) {
		if (expTracker.loginUser(currentUser)) {
			return true;
		} else {
			return false;
		}

	}

	private void registerUser(User user, String status, String interactions) {
		interactions = interactions.replace("\n", "");
		expTracker.registerNewUser(user, status.toUpperCase(), interactions.toUpperCase());
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);
	}

	private String checkExposureStatus() {
		return expTracker.getExposureStatus(currentSystemUser);
	}

	private String checkPastInteractions() {
		String[] interactionsList = expTracker.getUserInteractions(currentSystemUser);
		String concat = "";

		for (int i = 0; i < interactionsList.length; i++) {
			concat += interactionsList[i];
			if (i != interactionsList.length - 1)
				concat += ", ";
		}
		return concat;
	}

	private String checkTestStatus() {
		return expTracker.getTestStatus(currentSystemUser);
	}

	private void updateTestStatus(String status) {
		checkPane = createCheckPane();
		sceneCheck = new Scene(checkPane, 750, 500);
		expTracker.updateTestStatus(currentSystemUser, status.toUpperCase());
	}

	private void addInteractions(String interactions) {
		expTracker.addInteractions(currentSystemUser, interactions.toUpperCase());
	}

	private boolean validZipCode(String str) {
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

	private boolean validChooseStatus(String status) {
		if (status != "Choose status...") {
			return true;
		} else
			return false;
	}

	private boolean validInteractionList(String interactionList) {
		int count = 0;

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
    
    private boolean validAddress(String address)
    {
    	//checks first case of address formatting
    	if(!address.contains(" "))
    		return false;
    	
    	//splits address to check num string
    	String[] split = address.split(" ");
    	String str = split[0];

    	for (int i = 0; i < str.length(); i++) {
             // Check if character is
             // not digit 
             // then return false
    		
             if (!Character.isDigit(str.charAt(i))) {
            	 System.out.println(str.charAt(i));
                 return false;
             }
    	 }
    	 return true;
    	
    }
    
}
