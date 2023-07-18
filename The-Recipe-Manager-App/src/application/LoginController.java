package application;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class LoginController extends Login{

	DatabaseConnection connectNow = new DatabaseConnection();
    Connection connectDB = connectNow.getConnection();

    @FXML
    private Button loginButton;

    @FXML
    private Button forgotPasswordLogin;

    private Stage stage;
    private Scene scene;

    public void LoginPageBtAction(ActionEvent event) throws IOException {
    	if (!usernameLogin.getText().isBlank() && !passwordLogin.getText().isBlank()) {
    		LoginController lc = new LoginController();
    		if(lc.validateLogin(usernameLogin.getText(),passwordLogin.getText() )) {
    		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
	    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();
    		}

    	}else {
    		LoginMessageLabel.setText("Please Enter Username and Password");
    	}

    }
    @FXML
    public void BackBtnAction(ActionEvent event) throws IOException {
    		Parent root = FXMLLoader.load(getClass().getResource("OnboardingPage.fxml"));
	    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();
    }
    @FXML
    public void forgotPasswordLogin(ActionEvent event) throws SQLException, AddressException, MessagingException {

    	 System.out.println("Clicked on forgot password");

    	// Generate a random password

    	 Random random = new Random();
         int randomNumber = random.nextInt(100);

    	// Generate a random user ID
    	String user = "user_";

    	String userId = user + randomNumber;

    	String password = "pass@" + randomNumber;

    	String email = user + "@gmail.com";


    	// Insert the user ID and password into the UserAccount table
    	PreparedStatement insertps = connectDB.prepareStatement("INSERT INTO UserAccount (UserName, Password) VALUES (?, ?)");
    	insertps.setString(1, userId);
    	insertps.setString(2, password);

    	insertps.executeUpdate();

    	Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("New Login has set up");
        alert.setHeaderText("Information");
        alert.setContentText("username is "+ userId + " & password is " + password);
        alert.showAndWait();


    }
}

