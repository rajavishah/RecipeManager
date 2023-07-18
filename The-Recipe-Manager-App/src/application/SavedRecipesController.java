package application;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class SavedRecipesController implements Initializable {

	static List<Recipe> recipes = new ArrayList<>();


	@FXML
	private VBox saveCardLayout;

	private static SavedRecipesController instance;

	@FXML
	private Button backBtn;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		displaySavedRecipes();
		System.out.println("***Initialize in Saved Recipe Contoller****");
		System.out.println(recipes);

	}

	 // private constructor to prevent multiple instances from being created

	// static method to return the singleton instance of the controller
	public static SavedRecipesController getInstance() {
		if (instance == null) {
			instance = new SavedRecipesController();
		}
		return instance;
	}

	void displaySavedRecipes() {
		System.out.println("Inside Display Recipes");
        // Clear the existing recipe cards from the layout
        saveCardLayout.getChildren().clear();
        // Display the saved recipes in the UI
        for (int i = 0;i<recipes.size();i++) {
            try {

                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SaveCard.fxml"));
                HBox cardBox = fxmlLoader.load();
                SaveCardController cardController = fxmlLoader.getController();
                cardController.setData(recipes.get(i));
                saveCardLayout.getChildren().add(cardBox);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	@FXML
	public void handleBackBtn(ActionEvent event) throws IOException {
		// Load the home page v	iew and set it as the current scene
		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
		Scene scene = new Scene(root);
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setScene(scene); stage.show();
	}


	void addData(List<Recipe> recipeList) {
		for(Recipe rc: recipeList) {
			Recipe recipe = new Recipe();
			recipe.setName(rc.getName());
			recipe.setContents(rc.getContents());
			recipe.setchefName(rc.getchefName());

		}

	}
	public void handleSaveButtonClick(Recipe recipe) {
		/*
		 * recipes.add(recipe); List<Recipe> recipeList = new ArrayList<>();
		 * recipeList.add(recipe); addData(recipeList); displaySavedRecipes();
		 */
        //call a method to save all the objects

        // Show a confirmation message



    		 String SaveBtnRecipeName = recipe.getName();
    	        String Description = recipe.getDescription();
    	        String Chef = recipe.getchefName();
    	        double Rating = recipe.getRating();
    	        String Contents = recipe.getContents();

    	        try {
    	            byte[] imageData = recipe.getByteImage();

    	        	if(recipe.getImage() == null) {
    	        		Image image1 = new Image(new ByteArrayInputStream(imageData));
    	                recipe.setImageDetail(image1);

    	        	}
    	        	else {
    	                Image image = new Image(recipe.getImage());
    	            	recipe.setImageDetail(image);

    	        	}

    	        }catch(Exception e){
    	        	e.printStackTrace();
    	        }
    	    	System.out.println("------getting recipe name----" + SaveBtnRecipeName);
    		recipe.setName(SaveBtnRecipeName);
            recipe.setDescription(Description);
            recipe.setchefName(Chef);
            recipe.setRating(Rating);
            recipe.setContents(Contents);


           Alert alert = new Alert(AlertType.INFORMATION);
           alert.setTitle("Recipe Saved");
           alert.setHeaderText(null);
           alert.setContentText("The recipe has been saved successfully.");
           alert.showAndWait();


    }


}