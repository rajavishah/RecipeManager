package application;

import java.io.ByteArrayInputStream;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class SaveCardController implements Initializable {

    @FXML
    private Label SaveRecipeName;

    @FXML
    private Label recipeContents;

    @FXML
    private Label recipeDescription;

    @FXML
    private ImageView saveRatingImage;

    @FXML
    private ImageView saveRecipeImage;

    @FXML
    private Label savechefName;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        // Initialize method
    }

    public void setData(Recipe recipe) {
        try {
            System.out.println("Inside Save Card Controller --");
                byte[] imageData = recipe.getByteImage();

                if(recipe.getImage() == null) {
                	Image image1 = new Image(new ByteArrayInputStream(imageData));
                    recipe.setImageDetail(image1);
                    saveRecipeImage.setImage(image1);

                }else {
                    Image image = new Image(recipe.getImage());
                    recipe.setImageDetail(image);
                    saveRecipeImage.setImage(image);

                }
        } catch (Exception e) {
            e.printStackTrace();
        }

        SaveRecipeName.setText(recipe.getName());
        savechefName.setText(recipe.getchefName());
        System.out.println("recipe.setChefName()----" + recipe.getchefName());
        recipeDescription.setText(recipe.getDescription());
        recipeContents.setText(recipe.getContents());
    }

    public void initData(Recipe recipe) {
        try {
            System.out.println("----------Inside INIT DATA----------------");
            System.out.println("Get name" + recipe.getName());
            System.out.println("Get chef name" + recipe.getchefName());
            System.out.println("Get recipe name detail" + recipe.getDescription());
            System.out.println("Get recipe contents" + recipe.getContents());
            System.out.println("**************************" + SaveRecipeName);

            SaveRecipeName.setText(recipe.getName());
            savechefName.setText(recipe.getchefName());
            recipeDescription.setText(recipe.getDescription());
            recipeContents.setText(recipe.getContents());

            if (recipe.getRating() >= 4.0) {
                saveRatingImage.setImage(new Image("File:assets/Four_star.png"));
            } else {
                saveRatingImage.setImage(new Image("File:assets/Three_star.jpeg"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setImage(Image img) {
        saveRecipeImage.setImage(img);
    }
}
