package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;




public class RecipeCardController implements Initializable{

	   @FXML
	    private ImageView DetailrecipeImage;

	    @FXML
	    private Button backbtn;

	    @FXML
	    private Label chefNameDetail;

	    @FXML
	    private Label recipeContentsDetail;

	    @FXML
	    private Label recipeDescriptionDetail;

	    @FXML
	    private Label recipeNameDetailTo;

	    @FXML
	    private ImageView recipeRatingDetail;

	    @FXML
	    void backButtonClicked(ActionEvent event) throws IOException {
	    	// Close the current window or dialog
	        Stage stage = (Stage) backbtn.getScene().getWindow();
	        stage.close();
	    }

	public void setRecipe(Recipe recipe) {
		 try {
		        // Get the image data as a byte array
		        byte[] imageData = recipe.getByteImage();
		        if (imageData == null) {
		            throw new Exception("Image not found");
		        }

		        // Create an Image object from the byte array
		        Image image = new Image(new ByteArrayInputStream(imageData));
		        DetailrecipeImage.setImage(image);
		     } catch (Exception e) {
		         e.printStackTrace();
		     }
     	System.out.println("recipeNameDetailTo -------"+recipeNameDetailTo);
         recipeNameDetailTo.setText(recipe.getName());
 		chefNameDetail.setText(recipe.getchefName());
 		recipeDescriptionDetail.setText(recipe.getDescription());
 		recipeContentsDetail.setText(recipe.getContents());

//     	box.setStyle("-fx-background-color:" + Color.web(colors[(int)(Math.random()*colors.length)]));


	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub

	}

}
