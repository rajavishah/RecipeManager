package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class CategoriesCardController {

	@FXML
    private VBox box;

    @FXML
    private Label recipeChef;

    @FXML
    private Label recipeContents;

    @FXML
    private Label recipeDescription;

    @FXML
    private ImageView recipeImage;

    @FXML
    private Label recipeName;

    @FXML
    private Label recipeTime;

	public void setRecipe(Recipe recipe) {

		try {
    		System.out.println("Before--");
    		System.out.println("recipe.getImage()----"+recipe.getImage());
            Image image = new Image(recipe.getImage());
//            if (image == null || image.equals(null)) {
//                throw new Exception("Image not found");
//            }
          recipeImage.setImage(image);
            System.out.println("after--");

        } catch (Exception e) {
            e.printStackTrace();
        }

    	recipeName.setText(recipe.getName());
    	recipeChef.setText(recipe.getchefName());
    	recipeContents.setText(recipe.getContents());
    	recipeDescription.setText(recipe.getDescription());
    	recipeTime.setText(recipe.getPrepTime());
//    	box.setStyle("-fx-background-color:" + Color.web(colors[(int)(Math.random()*colors.length)]));

    }

	}

