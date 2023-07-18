package application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class CreatePlanMeal {
	@FXML
    private TextArea IngredientsRecipe;

    @FXML
    private TextArea InstructionsRecipe;

    @FXML
    private Button backbtn;

    @FXML
    private ImageView recipeImage;

    @FXML
    private TextField prepTime;

    @FXML
    private Button submit;

    @FXML
    private Button uploadPicture;

    @FXML
    private TextField recipeName;

    static File selectedFile;


    @FXML
    public void uploadPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {

            Image image = new Image(selectedFile.toURI().toString());
            recipeImage .setImage(image);
            // do something with the imageView...
        }
    }

    @FXML
    public void submitBtnAction(ActionEvent event) {
        String name = recipeName.getText();
        String ingredients = IngredientsRecipe.getText();
        String instructions = InstructionsRecipe.getText();
        String time = prepTime.getText();

        if (selectedFile == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please select an image for the recipe.");
            alert.showAndWait();
            return;
        }

        byte[] imageBytes = null;
        try {
            imageBytes = Files.readAllBytes(selectedFile.toPath());
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while reading the image file.");
            alert.showAndWait();
            e.printStackTrace();
            return;
        }

        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        int userId = UserAccount.idUserAccount;

        try {
            PreparedStatement Insertps = connectDB.prepareStatement("INSERT INTO CustomMealPlan (idUserAccount, recipeName, recipeContents, recipeDescription, recipeImage, prepTime) VALUES (?, ?, ?, ?, ?, ?)");
            Insertps.setInt(1, userId);
            Insertps.setString(2, name);
            Insertps.setString(3, ingredients);
            Insertps.setString(4, instructions);
            Insertps.setBytes(5, imageBytes);
            Insertps.setString(6, time);
            Insertps.executeUpdate();

            connectDB.close();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Create new meal plan");
            alert.setHeaderText(null);
            alert.setContentText("Added recipe to custom meal plan");
            alert.showAndWait();

        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("An error occurred while inserting the recipe into the database.");
            alert.showAndWait();
            e.printStackTrace();
        }

        addToTable();
    }

    private void addToTable() {
    	DatabaseConnection connectNow = new DatabaseConnection();
	    Connection connectDB = connectNow.getConnection();
	    int userId = UserAccount.idUserAccount;


	    try {
	        // Retrieve all custom meal plans from the database
	        PreparedStatement getallcustom = connectDB.prepareStatement("SELECT * FROM CustomMealPlan where idUserAccount = ?");
	        getallcustom.setInt(1, userId);
	        ResultSet customMealPlans = getallcustom.executeQuery();


	        // Iterate over the custom meal plans
	        while (customMealPlans.next()) {
	            // Create a new Recipe object and set its properties based on the custom meal plan
	        	Recipe customRecipe = new Recipe();
	            customRecipe.setName(customMealPlans.getString("recipeName"));
	            customRecipe.setContents(customMealPlans.getString("recipeContents"));
	            customRecipe.setDescription(customMealPlans.getString("recipeDescription"));
	            customRecipe.setPrepTime(customMealPlans.getString("prepTime"));

	            Blob blob = customMealPlans.getBlob("recipeImage");

	            if (blob != null) {
	                // Convert the blob data to a byte array
	                byte[] recipeImg = blob.getBytes(1, (int)blob.length());

	                // Create an image from the byte array
	                Image image = new Image(new ByteArrayInputStream(recipeImg));

	                // Use the image as needed
	                customRecipe.setImageDetail(image);
	               // recipeImgView.setImage(image);
	            }

	            MealPlan.addRecipe("Custom Meal Plan", customRecipe);
	            System.out.println("----UserId---" + userId);
	            MealPlan.addRecipeCustom("Custom Meal Plan", customRecipe, userId);
	        } // Close the database connection
	        connectDB.close();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }


	}

	@FXML
    public void backBtnAction(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MealPlan.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}



}
