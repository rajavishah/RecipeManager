package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ViewRecipeController implements Initializable{




	DatabaseConnection connectNow = new DatabaseConnection();
     Connection connectDB = connectNow.getConnection();

    @FXML
    private Button backButton;

    @FXML
    private Label ingredients;

    @FXML
    private Label recipeName;

    @FXML
    private Label stepsToCook;

    @FXML
    void handleBackButton(ActionEvent event) {
    	// Get the stage (i.e., window) associated with the current event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current window
        stage.close();

        // Go back to the previous page
        Parent root;
		try {
			// Get the stage (i.e., window) associated with the current event

				root = FXMLLoader.load(getClass().getResource("Myprofile.fxml"));
				Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


    }

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		displayRecipe();
	}

	private void displayRecipe() {
		// TODO Auto-generated method stub
		 System.out.println("Displaying recipe inside ViewRecipe ---- ");
	       String selectQuery = "SELECT recipeName, ingredients, stepsToCook FROM addmyrecipe WHERE idUserAccount = ? AND recipeName = ?";
	       PreparedStatement selectStatement;
		try {
			selectStatement = connectDB.prepareStatement(selectQuery);
		    ArrayList<Integer> userIdList = UserAccount.getUserIdList();
		       int userId = UserAccount.idUserAccount;
		       selectStatement.setInt(1, userId);
		       String recipeNameString = recipeName.getText();
		       selectStatement.setString(2, recipeNameString);
		       System.out.println("Coming here? " + recipeNameString);
		       ResultSet resultSet = selectStatement.executeQuery();
		       System.out.println("==========");
		       System.out.println("Result Set " + resultSet.next());
		       if (resultSet.next()) {
		           String Name = resultSet.getString("recipeName");
		           String Ingredients = resultSet.getString("ingredients");
		           String StepsToCook = resultSet.getString("stepsToCook");
		           System.out.println("Coming here? - 2 ");
		           System.out.println("Recipe Name "+ Name);
		           System.out.println("Recipe Ingredients "+ Ingredients);
		           System.out.println("Steps to cook "+ StepsToCook);
		           Recipe recipe = new Recipe();
		           // Set the retrieved data to the Recipe object
		           recipe.setName(Name);
		           recipe.setDescription(StepsToCook);
		           recipe.setContents(Ingredients);


		           // Set the data to the UI elements
		           recipeName.setText(recipe.getName());
		           ingredients.setText(recipe.getContents());
		           stepsToCook.setText(recipe.getDescription());
		       }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}



		}

}
