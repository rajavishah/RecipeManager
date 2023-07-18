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
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class MyDisplayCardController extends MyDisplayCard implements Initializable{

	 DatabaseConnection connectNow = new DatabaseConnection();
     Connection connectDB = connectNow.getConnection();

	 @FXML
	    private Button deleteButton;

	    @FXML
	    private Label ingredients;

	    @FXML
	    private Label stepsToCook;

	    @FXML
	    private Label recipeName;

	    String Name;

	    String Ingredients;

	    String recipeType;

	    String StepsToCook;

	    void displayRecipe() throws SQLException {

		   System.out.println("Displaying recipe inside my displaycard ---- ");
	       String selectQuery = "SELECT * FROM addmyrecipe WHERE idUserAccount = ?";
	       PreparedStatement selectStatement = connectDB.prepareStatement(selectQuery);
	       ArrayList<Integer> userIdList = UserAccount.getUserIdList();
	       int userId = UserAccount.idUserAccount;
	       selectStatement.setInt(1, userId);
	       ResultSet resultSet = selectStatement.executeQuery();
	       Recipe recipe = new Recipe();
	       if (resultSet.next()) {
	            this.Name = resultSet.getString("recipeName");
	            this.Ingredients = resultSet.getString("ingredients");
	            this.StepsToCook = resultSet.getString("stepsToCook");
	            this.recipeType = resultSet.getString("recipeType");




	           // Set the retrieved data to the Recipe object
	           recipe.setName(Name);
	           recipe.setDescription(StepsToCook);
	           recipe.setContents(Ingredients);


	           // Set the data to the UI elements
	           recipeName.setText(recipe.getName());
	           stepsToCook.setText(recipe.getDescription());
	           ingredients.setText(Ingredients);

       }



   }

	@Override
	void handleViewClick(ActionEvent event) {


	 	// Get the stage (i.e., window) associated with the current event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current window
        stage.close();

        // Go back to the previous page
        Parent root;
		try {
			// Get the stage (i.e., window) associated with the current event

				root = FXMLLoader.load(getClass().getResource("ViewRecipe.fxml"));
				Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();



		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	void handleUpdateClick(ActionEvent event){
	    try {
	        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyRecipeForm.fxml"));
	        Parent root = loader.load();
	        MyRecipeFormController controller = loader.getController();

	        // Pass the display recipe object to MyRecipeFormController
	        Recipe recipe = new Recipe();
	        recipe.setName(recipeName.getText());
	        recipe.setDescription(stepsToCook.getText());
	        recipe.setContents(ingredients.getText());
	        controller.setRecipe(recipe);

	        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        Scene scene = new Scene(root);
	        stage.setScene(scene);
	        stage.show();
	    } catch(Exception e) {
	        e.printStackTrace();
	    }
	}


    @Override
	@FXML
    void handleDeleteClick(ActionEvent event){
        String deleteQuery = "DELETE FROM addmyrecipe WHERE idUserAccount = ? AND recipeName = ?";
        PreparedStatement deleteStatement = null;
        try {
            deleteStatement = connectDB.prepareStatement(deleteQuery);
            deleteStatement.setInt(1, UserAccount.idUserAccount);
            deleteStatement.setString(2, recipeName.getText());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int affectedRows;
        try {
            affectedRows = deleteStatement.executeUpdate();
            if (affectedRows > 0) {
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Delete Recipe");
                alert.setHeaderText("Recipe Name: " + recipeName);
                alert.setContentText("Recipe deleted successfully!");

                alert.showAndWait();

                // Get the current scene and stage
                Scene scene = ((Node) event.getSource()).getScene();
                Stage stage = (Stage) scene.getWindow();

                // Load the FXML file you want to refresh the page with
                Parent root = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));

                // Set the loaded FXML file as the new root of the scene
                scene.setRoot(root);

                // Show the stage containing the refreshed scene
                stage.show();
            } else {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Unable to delete recipe. Please try again.");
                alert.showAndWait();
            }
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		try {
			displayRecipe();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setRecipe(Recipe recipe) {
		// TODO Auto-generated method stub
		 // Set the retrieved data to the Recipe object
		System.out.println("Inside My display controller - set Recipe ----------------------");

        // Set the data to the UI elements

        recipeName.setText(recipe.getName());
        stepsToCook.setText(recipe.getDescription());
        ingredients.setText(recipe.getContents());

	}

}
