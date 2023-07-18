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

public class DinnerController implements Initializable{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		addData();
	}


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


    private void addData() {
        List<Recipe> DinnerRecipes = new ArrayList<>(addDinnerRecipes());
        for (Recipe recipe : DinnerRecipes) {
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
        List<Recipe> addDinnerRecipes = addDinnerRecipes();
        boolean matchFound = false;

        for (Recipe recipe : addDinnerRecipes) {
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

	private List<Recipe> addDinnerRecipes() {
		List<Recipe> dinnerRecipes = new ArrayList<>();
	    Recipe recipe1 = new Recipe();
	    recipe1.setName("Grilled Salmon");
	    recipe1.setImage("File:assets/grilled_salmon.jpeg");
	    recipe1.setchefName("Michael Smith");
	    recipe1.setDescription("Preheat the grill to medium-high heat.\n"
	            + "Season the salmon fillets with salt and pepper.\n"
	            + "Grill the salmon for about 4-5 minutes per side, or until cooked through.\n"
	            + "Optional: serve with a side of grilled vegetables or a mixed green salad.");
	    recipe1.setContents("4 salmon fillets\n"
	            + "1 tsp salt\n"
	            + "1/2 tsp black pepper powder\n"
	            + "Optional: grilled vegetables or mixed green salad");
	    recipe1.setRating(4.9);
	    recipe1.setPrepTime("20 mins");
	    recipe1.setCategory("Dinner");

	    dinnerRecipes.add(recipe1);

	    Recipe recipe2 = new Recipe();
	    recipe2.setName("Spaghetti Bolognese");
	    recipe2.setImage("File:assets/spaghetti_bolognese.jpeg");
	    recipe2.setchefName("Sophie Taylor");
	    recipe2.setDescription("Cook spaghetti according to package instructions.\n"
	            + "In a large saucepan, cook ground beef and onion over medium heat until browned.\n"
	            + "Add garlic, diced tomatoes, tomato sauce, salt, and pepper, and let simmer for 10-15 minutes.\n"
	            + "Serve the Bolognese sauce over the cooked spaghetti and top with grated Parmesan cheese.");
	    recipe2.setContents("1 lb spaghetti\n"
	            + "1 lb ground beef\n"
	            + "1 onion, diced\n"
	            + "2 cloves garlic, minced\n"
	            + "1 can diced tomatoes\n"
	            + "1 can tomato sauce\n"
	            + "1 tsp salt\n"
	            + "1/2 tsp black pepper powder\n"
	            + "Grated Parmesan cheese for topping");
	    recipe2.setRating(4.7);
	    recipe2.setPrepTime("40 mins");
	    recipe2.setCategory("Dinner");

	    dinnerRecipes.add(recipe2);

	    Recipe recipe3 = new Recipe();
	    recipe3.setName("Chicken Stir Fry");
	    recipe3.setImage("File:assets/chicken_stir_fry.jpeg");
	    recipe3.setchefName("Katie Lee");
	    recipe3.setDescription("Heat oil in a wok or large skillet over high heat.\n"
	            + "Add sliced chicken and stir-fry for about 5-7 minutes, or until cooked through.\n"
	            + "Add sliced vegetables, such as bell peppers, broccoli, and carrots, and stir-fry for an additional 3-4 minutes.\n"
	            + "Season with soy sauce, hoisin sauce, and sesame oil.\n"
	            + "Serve over rice or noodles.");
	    recipe3.setContents("1 lb boneless, skinless chicken breasts, sliced\n"
	            + "1 bell pepper, sliced\n"
	            + "1 broccoli florets\n"
	            + "2 carrots, sliced\n"
	            + "2 tbsp oil\n"
	            + "2 tbsp soy sauce\n"
	            + "1 tbsp hoisin sauce\n"
	            + "1 tsp sesame oil\n"
	            + "Cooked rice or noodles for serving");
	    recipe3.setRating(4.5);
	    recipe3.setPrepTime("30 mins");
	    recipe3.setCategory("Dinner");

	    dinnerRecipes.add(recipe3);

	    return dinnerRecipes;
	}



}
