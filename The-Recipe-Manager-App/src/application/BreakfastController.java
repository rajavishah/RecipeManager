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

public class BreakfastController implements Initializable{

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
		List<Recipe> BreakfastRecipes = new ArrayList<>(addBreakfastRecipes());
		for (Recipe recipe : BreakfastRecipes) {

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
	    List<Recipe> addBreakfastRecipes = addBreakfastRecipes();
	    boolean matchFound = false;

	    for (Recipe recipe : addBreakfastRecipes) {
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


private List<Recipe> addBreakfastRecipes() {
		    List<Recipe> breakfastRecipes = new ArrayList<>();

		    Recipe recipe1 = new Recipe();
		    recipe1.setName("Avocado Toast");
		    recipe1.setImage("File:assets/avocado_toast.jpeg");
		    recipe1.setchefName("Jessica Lee");
		    recipe1.setDescription("Toast a slice of bread until it's crispy and golden brown.\n"
		            + "Mash an avocado in a bowl and spread it over the toast.\n"
		            + "Drizzle some olive oil and sprinkle some salt and black pepper on top.\n"
		            + "Optional: add sliced tomatoes or a poached egg on top for extra flavor.");
		    recipe1.setContents("1 slice of bread\n"
		            + "1 ripe avocado\n"
		            + "1 tsp olive oil\n"
		            + "1/4 tsp salt\n"
		            + "1/4 tsp black pepper powder\n"
		            + "Optional: sliced tomatoes or poached egg");
		    recipe1.setRating(4.6);
		    recipe1.setPrepTime("15 mins");
		    recipe1.setCategory("Breakfast");

		    breakfastRecipes.add(recipe1);

		    Recipe recipe2 = new Recipe();
		    recipe2.setName("Oatmeal");
		    recipe2.setImage("File:assets/oatmeal.jpeg");
		    recipe2.setchefName("David Nguyen");
		    recipe2.setDescription("Bring 1 cup of water or milk to a boil in a small saucepan.\n"
		            + "Add 1/2 cup of oats and reduce heat to low.\n"
		            + "Simmer for 5-7 minutes, stirring occasionally, until the oatmeal is creamy and thick.\n"
		            + "Add your choice of toppings, such as fresh fruit, nuts, or honey.");
		    recipe2.setContents("1 cup water or milk\n"
		            + "1/2 cup oats\n"
		            + "Toppings of your choice (e.g. fresh fruit, nuts, honey)");
		    recipe2.setRating(4.4);
		    recipe2.setPrepTime("10 mins");
		    recipe2.setCategory("Breakfast");

		    breakfastRecipes.add(recipe2);

		    Recipe recipe3 = new Recipe();
		    recipe3.setName("Greek Yogurt Parfait");
		    recipe3.setImage("File:assets/yogurt_parfait.jpeg");
		    recipe3.setchefName("Megan Lee");
		    recipe3.setDescription("Layer Greek yogurt, granola, and fresh fruit in a glass or jar.\n"
		            + "Repeat the layers until the glass is full.\n"
		            + "Optional: drizzle some honey or maple syrup on top for sweetness.");
		    recipe3.setContents("1 cup Greek yogurt\n"
		            + "1/2 cup granola\n"
		            + "1 cup fresh fruit (e.g. berries, chopped bananas, chopped apples)\n"
		            + "Optional: honey or maple syrup");
		    recipe3.setRating(4.8);
		    recipe3.setPrepTime("5 mins");
		    recipe3.setCategory("Breakfast");

		    breakfastRecipes.add(recipe3);

		    return breakfastRecipes;
		}


}
