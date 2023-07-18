package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class RegisterController extends Register{


	@FXML Button RegisterBtn;

	private Stage stage;
    private Scene scene;

	public void handleRegister(ActionEvent event) throws IOException {



		// perform validation of input fields
		ValidateDetails();

		System.out.println("printing name register-------"+this.nameRegister);
        register();

        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();

       // clear the input fields
       clearDetails();
    }

	@Override
	protected void register() {
		// perform registration logic here
		 String username = nameRegister.getText();
		    String password = passwordRegister.getText();
		    String confirmpassword = confirmPasswordRegister.getText();
		    String email = emailRegister.getText();
		    String phoneno = phoneRegister.getText();

		    if (!password.equals(confirmpassword)) {
		        System.out.println("Passwords do not match");
		        passwordRegister.clear();
		        confirmPasswordRegister.clear();
		    }

		    DatabaseConnection connectNow = new DatabaseConnection();
		    Connection connectDB = connectNow.getConnection();
		    String checkUserExists = "SELECT * from UserAccount where userName = '" + nameRegister.getText() + "'";
		    try {
		        Statement statement = connectDB.createStatement();
		        ResultSet queryResult = statement.executeQuery(checkUserExists);

		        if (queryResult.isBeforeFirst()) {
		            System.out.println("Username already exists");
		            Alert alert = new Alert(Alert.AlertType.ERROR);
		            alert.setContentText("Cannot use this username");
		            alert.show();
		        } else {
		            PreparedStatement Insertps = connectDB.prepareStatement("Insert into UserAccount (Username, Password, Email, Phoneno) values (?, ?, ?, ?)");
		            Insertps.setString(1, username);
		            Insertps.setString(2, password);
		            Insertps.setString(3, email);
		            Insertps.setString(4, phoneno);
		            Insertps.executeUpdate();


		        }
		    }
		    catch(Exception e) {
		        e.printStackTrace();
		    }
   }
	public void BackBtn(ActionEvent event) throws IOException {

    		Parent root = FXMLLoader.load(getClass().getResource("OnboardingPage.fxml"));
	    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
	    	scene = new Scene(root);
	    	stage.setScene(scene);
	    	stage.show();

    }


	}




