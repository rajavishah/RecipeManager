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

public class LunchController implements Initializable{

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







	public void handleSearchButtonAction(ActionEvent event) {
	    String name = searchField.getText();
	    List<Recipe> lunchRecipes = addLunchRecipes();
	    boolean matchFound = false;

	    for (Recipe recipe : lunchRecipes) {
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



	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addData();

	}

	private void addData() {
		List<Recipe> lunchRecipes = new ArrayList<>(addLunchRecipes());
		for (Recipe recipe : lunchRecipes) {

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

	private List<Recipe> addLunchRecipes() {
		List<Recipe> lunchRecipes = new ArrayList<>();

	    Recipe recipe1 = new Recipe();
	    recipe1.setName("Chicken Caesar Salad");
	    recipe1.setImage("File:assets/caesar_salad.jpeg");
	    recipe1.setchefName("John Smith");
	    recipe1.setDescription("Grill a chicken breast and slice it into strips.\n"
	            + "Wash and chop some romaine lettuce and place it in a bowl.\n"
	            + "Add the chicken, croutons, and shaved Parmesan cheese to the bowl.\n"
	            + "Drizzle Caesar dressing over the top and toss to combine.");
	    recipe1.setContents("1 chicken breast\n"
	            + "2 cups chopped romaine lettuce\n"
	            + "1/4 cup croutons\n"
	            + "1/4 cup shaved Parmesan cheese\n"
	            + "2 tbsp Caesar dressing");
	    recipe1.setRating(4.5);
	    recipe1.setPrepTime("25 mins");
	    recipe1.setCategory("Lunch");

	    lunchRecipes.add(recipe1);

	    Recipe recipe2 = new Recipe();
	    recipe2.setName("Veggie Wrap");
	    recipe2.setImage("File:assets/veggie_wrap.jpeg");
	    recipe2.setchefName("Emma Johnson");
	    recipe2.setDescription("Spread hummus or cream cheese on a whole wheat wrap.\n"
	            + "Add sliced avocado, cucumber, bell pepper, and lettuce to the wrap.\n"
	            + "Wrap it up tightly and slice it in half.");
	    recipe2.setContents("1 whole wheat wrap\n"
	            + "2 tbsp hummus or cream cheese\n"
	            + "1/4 avocado, sliced\n"
	            + "1/4 cucumber, sliced\n"
	            + "1/4 bell pepper, sliced\n"
	            + "1/2 cup lettuce");
	    recipe2.setRating(4.3);
	    recipe2.setPrepTime("15 mins");
	    recipe2.setCategory("Lunch");

	    lunchRecipes.add(recipe2);

	    Recipe recipe3 = new Recipe();
	    recipe3.setName("Tomato Soup");
	    recipe3.setImage("File:assets/tomato_soup.jpeg");
	    recipe3.setchefName("Sophia Lee");
	    recipe3.setDescription("Heat some olive oil in a pot over medium heat.\n"
	            + "Add chopped onions and garlic and cook until softened.\n"
	            + "Add canned tomatoes, vegetable broth, and some Italian seasoning.\n"
	            + "Bring to a boil, then reduce heat and simmer for 20-25 minutes.\n"
	            + "Use an immersion blender to puree the soup until smooth.\n"
	            + "Optional: stir in some heavy cream or milk for a creamier texture.");
	    recipe3.setContents("2 tbsp olive oil\n"
	            + "1 onion, chopped\n"
	            + "2 garlic cloves, minced\n"
	            + "28 oz canned tomatoes\n"
	            + "4 cups vegetable broth\n"
	            + "1 tsp Italian seasoning\n"
	            + "Optional: 1/4 cup heavy cream or milk");
	    recipe3.setRating(4.7);
	    recipe3.setPrepTime("35 mins");
	    recipe3.setCategory("Lunch");

	    lunchRecipes.add(recipe3);

	    return lunchRecipes;
	}

}
