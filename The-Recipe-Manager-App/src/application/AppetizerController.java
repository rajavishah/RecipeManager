package application;

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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AppetizerController implements Initializable {

	@FXML
    private Button backBtn;

    @FXML
    private VBox displayLayout;

    @FXML
    private TextField searchField;



    @FXML
    void backBtnAction(ActionEvent event) throws IOException {
    	Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
    }



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		addData();

	}


	private void addData() {
		List<Recipe> AppetizerRecipes = new ArrayList<>(addAppetizerRecipes());
		for (Recipe recipe : AppetizerRecipes) {

            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("Categories.fxml"));

                VBox card = fxmlLoader.load();

                CategoriesCardController categoriesCardController = fxmlLoader.getController();
                if (categoriesCardController == null) {
                	categoriesCardController = new CategoriesCardController();
                    fxmlLoader.setController(categoriesCardController);
                }
                categoriesCardController.setRecipe(recipe);

                displayLayout.getChildren().add(card);
            } catch (Exception e) {
                System.out.println("Error caught");
                e.printStackTrace();
            }
        }

	}



	public void handleSearchButtonAction(ActionEvent event) {
	    String name = searchField.getText();
	    List<Recipe> appetizerRecipes = addAppetizerRecipes();
	    boolean matchFound = false;

	    for (Recipe recipe : appetizerRecipes) {
	        if (recipe.getName().equalsIgnoreCase(name) && !name.isEmpty()) {
	            displayRecipe(recipe);
	            matchFound = true;
	            break;
	        }



	    }



	    if (!matchFound) {
	        // Show an alert box indicating that no recipes were found
	        Alert alert = new Alert(Alert.AlertType.INFORMATION);
	        alert.setTitle("No Recipes Found");
	        alert.setHeaderText(null);
	        alert.setContentText("Sorry, no recipes were found for your search query.");
	        alert.showAndWait();
	        displayLayout.getChildren().clear();
	        addData();
	    }


	}

	public void displayRecipe(Recipe recipe) {
	    try {
	        FXMLLoader fxmlLoader = new FXMLLoader();
	        fxmlLoader.setLocation(getClass().getResource("Categories.fxml"));

	        VBox card = fxmlLoader.load();

	        CategoriesCardController categoriesCardController = fxmlLoader.getController();
	        if (categoriesCardController == null) {
	            categoriesCardController = new CategoriesCardController();
	            fxmlLoader.setController(categoriesCardController);
	        }
	        categoriesCardController.setRecipe(recipe);

	        displayLayout.getChildren().clear();
	        displayLayout.getChildren().add(card);
	    } catch (Exception e) {
	        System.out.println("Error caught");
	        e.printStackTrace();
	    }
	}


private List<Recipe> addAppetizerRecipes() {
    List<Recipe> appetizerRecipes = new ArrayList<>();

    Recipe recipe1 = new Recipe();
    recipe1.setName("Bruschetta");
    recipe1.setImage("File:assets/bruschetta.jpeg");
    recipe1.setchefName("Maria Rodriguez");
    recipe1.setDescription("Toast slices of bread until crispy and golden brown.\n"
            + "Rub a clove of garlic on each slice.\n"
            + "Top with diced tomatoes, chopped fresh basil, and a drizzle of olive oil.\n"
            + "Optional: add a sprinkle of salt and balsamic vinegar for extra flavor.");
    recipe1.setContents("4 slices of bread\n"
            + "2 tomatoes, diced\n"
            + "2 tbsp fresh basil, chopped\n"
            + "2 cloves of garlic, peeled\n"
            + "2 tbsp olive oil\n"
            + "Optional: salt and balsamic vinegar");
    recipe1.setRating(4.7);
    recipe1.setPrepTime("15 mins");
    recipe1.setCategory("Appetizer");

    appetizerRecipes.add(recipe1);

    Recipe recipe2 = new Recipe();
    recipe2.setName("Caprese Salad");
    recipe2.setImage("File:assets/caprese_salad.jpeg");
    recipe2.setchefName("John Smith");
    recipe2.setDescription("Slice fresh mozzarella and tomatoes.\n"
            + "Arrange on a plate, alternating between the two.\n"
            + "Top with fresh basil leaves and a drizzle of balsamic glaze.\n"
            + "Optional: sprinkle with salt and black pepper for extra flavor.");
    recipe2.setContents("1 ball fresh mozzarella\n"
            + "2 large tomatoes\n"
            + "1/4 cup fresh basil leaves\n"
            + "2 tbsp balsamic glaze\n"
            + "Optional: salt and black pepper");
    recipe2.setRating(4.5);
    recipe2.setPrepTime("10 mins");
    recipe2.setCategory("Appetizer");

    appetizerRecipes.add(recipe2);

    Recipe recipe3 = new Recipe();
    recipe3.setName("Spinach and Artichoke Dip");
    recipe3.setImage("File:assets/spinach_artichoke_dip.jpeg");
    recipe3.setchefName("Emily Brown");
    recipe3.setDescription("Mix cream cheese, sour cream, mayonnaise, Parmesan cheese, spinach, and artichoke hearts in a bowl.\n"
            + "Transfer to a baking dish and bake until hot and bubbly.\n"
            + "Serve with toasted bread, pita chips, or tortilla chips.");
    recipe3.setContents("8 oz cream cheese, softened\n"
            + "1/2 cup sour cream\n"
            + "1/2 cup mayonnaise\n"
            + "1/2 cup grated Parmesan cheese\n"
            + "1 cup chopped spinach, thawed and drained\n"
            + "1 can artichoke hearts, drained and chopped\n"
            + "Toasted bread, pita chips, or tortilla chips for serving");
    recipe3.setRating(4.9);
    recipe3.setPrepTime("30 mins");
    recipe3.setCategory("Appetizer");

    appetizerRecipes.add(recipe3);

    return appetizerRecipes;
}




}
