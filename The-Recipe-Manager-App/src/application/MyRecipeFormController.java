package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class MyRecipeFormController extends MyRecipeForm {
	 private int userId;

	 @FXML
	  private TextArea ingredients;

	 @FXML
	    private TextField recipeName;

	    @FXML
	    private TextArea stepsToCook;


    @Override
    void handleCancelButton(ActionEvent event) {
    	// Get the stage (i.e., window) associated with the current event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current window
        stage.close();

        // Go back to the previous page
        Parent root;
		try {
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
    void handleSubmitButton(ActionEvent event){
        // Add your implementation for the submit button click event

  	  String recipeNameText = recipeName.getText();
        String ingredientsText = ingredients.getText();
        String stepsToCookText = stepsToCook.getText();

        String dietType = "";

        if (vegetarianDietType.isSelected()) {
            dietType = vegetarianDietType.getText();
        } else if (eggetarianDietType.isSelected()) {
            dietType = eggetarianDietType.getText();
        } else if (nonvegetarianDietType.isSelected()) {
            dietType = nonvegetarianDietType.getText();
        }



        System.out.println("Recipe Name: " + recipeNameText);
        System.out.println("Ingredients: " + ingredientsText);
        System.out.println("Steps to Cook: " + stepsToCookText);
        System.out.println("Diet Type: " + dietType);



        // Get the stage (i.e., window) associated with the current event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // Close the current window
        stage.close();

        // Go back to the previous page
        Parent root;
		try {
			// Get the stage (i.e., window) associated with the current event
	        insert();

			root = FXMLLoader.load(getClass().getResource("Myprofile.fxml"));
			Scene scene = new Scene(root);
		    stage.setScene(scene);
		    stage.show();




		} catch (IOException e) {
			e.printStackTrace();
		}


    }

    public void insert() {
        // Get the recipe name and user ID
        String recipeName = this.recipeName.getText();
        int userId = UserAccount.idUserAccount;

        // Make sure the recipe name is not null or empty
        if (recipeName == null || recipeName.isEmpty()) {
            System.out.println("Recipe name is required!");
            return;
        }

        // Get a connection to the database
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        try {
            // Check if the recipe already exists in the database
            String selectRecipe = "SELECT COUNT(*) FROM addmyrecipe WHERE recipeName = ? AND idUserAccount = ?";
            PreparedStatement selectStatement = connectDB.prepareStatement(selectRecipe);
            selectStatement.setString(1, recipeName);
            selectStatement.setInt(2, userId);
            ResultSet selectResult = selectStatement.executeQuery();

            if (selectResult.next() && selectResult.getInt(1) > 0) {
                // Recipe already exists, update it instead of inserting a new one
                String updateRecipe = "UPDATE addmyrecipe SET ingredients = ?, stepsToCook = ? WHERE recipeName = ? AND idUserAccount = ?";
                PreparedStatement updateStatement = connectDB.prepareStatement(updateRecipe);
                updateStatement.setString(1, ingredients.getText());
                updateStatement.setString(2, stepsToCook.getText());
                updateStatement.setString(3, recipeName);
                updateStatement.setInt(4, userId);
                int rowsUpdated = updateStatement.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Recipe updated successfully!");
                } else {
                    System.out.println("Recipe update failed!");
                }
            } else {
                // Recipe does not exist, insert a new one
                String insertRecipe = "INSERT INTO addmyrecipe (recipeName, ingredients, stepsToCook, idUserAccount) VALUES (?,?,?,?)";
                PreparedStatement insertStatement = connectDB.prepareStatement(insertRecipe);
                insertStatement.setString(1, recipeName);
                insertStatement.setString(2, ingredients.getText());
                insertStatement.setString(3, stepsToCook.getText());
                insertStatement.setInt(4, userId);
                int rowsInserted = insertStatement.executeUpdate();

                if (rowsInserted > 0) {
                    System.out.println("Recipe inserted successfully!");
                } else {
                    System.out.println("Recipe insert failed!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error inserting/updating recipe: " + e.getMessage());
        } finally {
            // Close the database connection
            try {
                connectDB.close();
            } catch (SQLException e) {
                System.out.println("Error closing database connection: " + e.getMessage());
            }
        }
    }


//    public void insert() {
//		// TODO Auto-generated method stub
//    	// Get a connection to the database
//    	ArrayList<Integer> userIdList = UserAccount.getUserIdList();
//    	int userId = UserAccount.idUserAccount;
//
//    	System.out.println("Entering Method insert--------------------------");
//        DatabaseConnection connectNow = new DatabaseConnection();
//        Connection connectDB = connectNow.getConnection();
//
//        String Name = recipeName.getText();
//
//        if(Name == null) {
//        // SQL query to insert a new recipe with the logged-in user's userId
//        String insertRecipe = "INSERT INTO addmyrecipe (recipeName, ingredients, stepsToCook, idUserAccount) VALUES (?,?,?,?)";
//
//        try {
//            // Create a prepared statement for the SQL query
//            PreparedStatement preparedStatement = connectDB.prepareStatement(insertRecipe);
//            System.out.println("UserID " + userId);
//
//            // Set the parameters of the prepared statement to the recipe data
//            preparedStatement.setString(1, recipeName.getText());
//            preparedStatement.setString(2, ingredients.getText());
//            preparedStatement.setString(3, stepsToCook.getText());
//            preparedStatement.setInt(4, userId);
//
//            // Execute the prepared statement to insert the new recipe
//            preparedStatement.executeUpdate();
//
//            // Print a message to confirm that the recipe was inserted successfully
//            System.out.println("Recipe inserted successfully!");
//        } catch (SQLException e) {
//            // Print an error message if the recipe could not be inserted
//            System.out.println("Error inserting recipe: " + e.getMessage());
//        } finally {
//            // Close the database connection
//            try {
//                connectDB.close();
//            } catch (SQLException e) {
//                System.out.println("Error closing database connection: " + e.getMessage());
//            }
//        }
//        }else {
//        	String update = "Update INTO addmyrecipe (recipeName, ingredients, stepsToCook, idUserAccount) VALUES (?,?,?,?)";
//        }
//
//	}

	@Override
    void handleDietType(ActionEvent event) {
        // Add your implementation for the vegetarian radio button click event
        System.out.println("DietType radio button clicked");
    }

	public void setRecipe(Recipe recipe) {
		// TODO Auto-generated method stub

		recipeName.setText(recipe.getName());
		ingredients.setText(recipe.getContents());
		stepsToCook.setText(recipe.getDescription());

	}

}
