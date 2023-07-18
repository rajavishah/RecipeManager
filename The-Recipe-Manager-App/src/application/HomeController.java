package application;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;




public class HomeController implements Initializable {

	 DatabaseConnection connectNow = new DatabaseConnection();
	 Connection connectDB = connectNow.getConnection();

	@FXML
	private HBox cardLayout;

	@FXML
	private TextField searchField;

	@FXML
	private HBox cardAreaLayout;

	@FXML
	private Hyperlink mealPlan;

	private List<Recipe> discoverRecipes;
	private List<Recipe> addRecipesFromList;


    private Stage stage;
    private Scene scene;

	@FXML
	private Hyperlink Logout;
	@FXML
    private Hyperlink savedRecipe;
	@FXML
    private Hyperlink myProfile;


	    @FXML
	    private Button breakfastBtn;

	    @FXML
	    private Button dinnerBtn;

	    @FXML
	    private Button lunchBtn;

	    @FXML
	    private Button starterBtn;
	@Override
	public void initialize(URL location, ResourceBundle arg1) {
		System.out.println("Entering home intializable");
		discoverRecipes = new ArrayList<>(discoverRecipes());
		try {
			for(int i =0; i < discoverRecipes.size(); i++)
			{
				FXMLLoader fxmlLoader = new FXMLLoader();
				fxmlLoader.setLocation(getClass().getResource("card.fxml"));


				HBox cardBox = fxmlLoader.load();
				CardController cardController = fxmlLoader.getController();
				cardController.setData(discoverRecipes.get(i));
				System.out.print("Send Object method");
				//cardController.sendObject(rc);
				System.out.println("**Seeing object from HomeController ********" + discoverRecipes.get(i));
				cardController.sendObject(discoverRecipes.get(i));
				System.out.print("\nEnd of Send Object method");
				cardLayout.getChildren().add(cardBox);

			}
			RecipeStorage rs = new RecipeStorage();
			addRecipesFromList = new ArrayList<>(addRecipes());
			rs.addRecipesToDb(addRecipesFromList);

			ArrayList<String> RecipeNames = new ArrayList<>();

			RecipeNames.add("Paneer Tikka");

			RecipeNames.add("Veg Sandwich");
			RecipeNames.add("Salad Bowl");

		Iterator<String> iterator = RecipeNames.iterator();
		while (iterator.hasNext()) {
		    String recipeName = iterator.next();
		    Recipe rc = new Recipe();
		    FXMLLoader fxmlLoader = new FXMLLoader();
		    fxmlLoader.setLocation(getClass().getResource("Area.fxml"));
		    HBox cardBox = fxmlLoader.load();
		    CardController cardController = fxmlLoader.getController();
		    rc.setName(recipeName);
		    cardController.setDataFromDb(rc);
		    cardController.sendObj(rc);
		    cardAreaLayout.getChildren().add(cardBox);
		}


		}
		catch(Exception e) {
			e.printStackTrace();
		}


//		mealPlan.setOnAction(e ->  {
//		    try {
//		        handleMealPlanClick(e);
//		    } catch (IOException ex) {
//		        ex.printStackTrace();
//		    }
//		});

	}


@FXML
private void handleSearchButtonAction(ActionEvent event) throws IOException, SQLException {
    String query = searchField.getText();
    List<Recipe> recipes = searchDatabase(query);
    if (!recipes.isEmpty()) {
        // Create a new FXML card to display the recipe information
        FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeCard.fxml"));
        Parent root = loader.load();
        RecipeCardController controller = loader.getController();
        controller.setRecipe(recipes.get(0)); // Display the first recipe in the list

        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    } else {
        // Show an alert box indicating that no recipes were found
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("No Recipes Found");
        alert.setHeaderText(null);
        alert.setContentText("Sorry, no recipes were found for your search query.");
        alert.showAndWait();
    }
}


	private List<Recipe> searchDatabase(String query) throws SQLException {
		List<Recipe> ls = new ArrayList<>();
		Recipe recipe = new Recipe();
		String selectQuery = "SELECT * FROM RecipesInfo WHERE recipeName = ?";
        PreparedStatement selectStatement = connectDB.prepareStatement(selectQuery);
        selectStatement.setString(1, searchField.getText());
        ResultSet resultSet = selectStatement.executeQuery();
        System.out.println(resultSet);

        if (resultSet.next()) {
            String Name = resultSet.getString("recipeName");
            String Description = resultSet.getString("recipeDescription");
            String Chef = resultSet.getString("recipeChef");
            double Rating = resultSet.getDouble("recipeRating");
            String Contents = resultSet.getString("recipeContents");
            byte[] recipeImg = resultSet.getBytes("recipesImg");

            Image image = new Image(new ByteArrayInputStream(recipeImg));


            // Set the retrieved data to the Recipe object
            recipe.setName(Name);
            recipe.setDescription(Description);
            recipe.setchefName(Chef);
            recipe.setRating(Rating);
            recipe.setContents(Contents);
            recipe.setImageDetail(image);
            recipe.setByteImage(recipeImg);


            System.out.println("Inside search database function");


            ls.add(recipe);


	}else {
        System.out.println("Recipe not found in the database");
    }
		return ls;
	}





	private List<Recipe> addRecipes() {
		List<Recipe> dbls = new ArrayList<>();
		Recipe recipe1 = new Recipe();
		recipe1.setName("Paneer Tikka");
		recipe1.setImage("assets/Paneer_tikka.jpeg");
		recipe1.setchefName("Pannaga Veeramohan");
		recipe1.setDescription("In a bowl, mix together ginger paste, garlic paste, red chili powder, coriander powder, cumin powder, turmeric powder, garam masala powder, and salt.\n"
				+ "Add the paneer cubes and vegetables to the bowl and mix well, making sure that the marinade coats everything evenly. Let it marinate for at least 30 minutes.\n"
				+ "Preheat the oven to 200°C (400°F). Skewer the paneer and vegetables.\n"
				+ "Brush some oil on the skewers and place them on a baking sheet.\n"
				+ "Bake for 10-12 minutes, turning them once halfway through cooking, until they are golden brown and slightly charred.\n");
		recipe1.setContents("250g paneer, cut into cubes\n"
				+ "1 bell pepper, cut into cubes\n"
				+ "1 onion, cut into cubes\n"
				+ "1 tomato, cut into cubes\n"
				+ "1 tsp ginger paste\n"
				+ "1 tsp garlic paste\n"
				+ "2 tsp red chili powder\n"
				+ "2 tsp coriander powder\n"
				+ "1 tsp cumin powder\n"
				+ "1/2 tsp turmeric powder\n"
				+ "1 tsp garam masala powder\n"
				+ "Salt to taste\n"
				+ "2 tbsp oil");
		recipe1.setRating(4.8);

		dbls.add(recipe1);


		Recipe recipe2 = new Recipe();
		recipe2.setName("Veg Sandwich");
		recipe2.setImage("assets/veg_sandwich.jpeg");
		recipe2.setchefName("John Smith");
		recipe2.setDescription("Take two bread slices and spread butter or mayonnaise on both the slices.\n"
		+ "Add sliced cucumber, tomato, onion and boiled potato on one of the bread slice.\n"
		+ "Sprinkle salt, black pepper powder and chaat masala.\n"
		+ "Cover the sandwich with the other bread slice.\n"
		+ "Heat a sandwich maker and place the sandwich in it.\n"
		+ "Toast the sandwich until it turns golden brown.\n");
		recipe2.setContents("4 bread slices\n"
		+ "1 small cucumber, sliced\n"
		+ "1 small tomato, sliced\n"
		+ "1 small onion, sliced\n"
		+ "1 boiled potato, sliced\n"
		+ "1/2 tsp salt\n"
		+ "1/4 tsp black pepper powder\n"
		+ "1/2 tsp chaat masala\n"
		+ "2 tbsp butter or mayonnaise");
		recipe2.setRating(4.5);

		dbls.add(recipe2);

		Recipe recipe3 = new Recipe();
		recipe3.setName("Salad Bowl");
		recipe3.setImage("assets/salad_bowl.jpeg");
		recipe3.setchefName("Mary Jones");
		recipe3.setDescription("In a large bowl, mix together chopped lettuce, spinach, carrots, cucumber, tomatoes and red onions.\n"
		+ "Add boiled chickpeas or any other beans of your choice.\n"
		+ "Add roasted sunflower seeds and pumpkin seeds for crunch.\n"
		+ "In a separate small bowl, whisk together olive oil, lemon juice, salt and black pepper.\n"
		+ "Pour the dressing over the salad and mix well.\n"
		+ "Garnish with some feta cheese or grated parmesan cheese and serve.");
		recipe3.setContents("2 cups chopped lettuce\n"
		+ "2 cups chopped spinach\n"
		+ "1 carrot, grated\n"
		+ "1 cucumber, chopped\n"
		+ "2 tomatoes, chopped\n"
		+ "1/2 red onion, sliced\n"
		+ "1 cup boiled chickpeas or any other beans\n"
		+ "2 tbsp roasted sunflower seeds\n"
		+ "2 tbsp roasted pumpkin seeds\n"
		+ "2 tbsp olive oil\n"
		+ "1 tbsp lemon juice\n"
		+ "1/2 tsp salt\n"
		+ "1/4 tsp black pepper powder\n"
		+ "2 tbsp crumbled feta cheese or grated parmesan cheese");
		recipe3.setRating(4.2);

		dbls.add(recipe3);

		return dbls;
	}

	private List<Recipe> discoverRecipes(){
		List<Recipe> ls = new ArrayList<>();
		Recipe recipe = new Recipe();
		recipe.setName("Avocado\nToast");
		recipe.setImage("file:assets/avocado_toast.jpg");
		recipe.setchefName("By Gordan Ramsay");

		recipe.setDescription("Mash the avocado with a fork in a shallow bowl until chunky. Season with fine salt and black pepper.\n" +
				"Toast the bread until browned and crisp. Lightly rub 1 side of each slice with the cut side of the garlic until fragrant; discard the garlic. \n" +
				"Lightly brush the toasts with oil, and season with fine salt and pepper. Divide the mashed avocado evenly among the toasts, and top with more flaky sea salt, more black pepper and red pepper flakes if using.");
		recipe.setContents("One 8-ounce ripe avocado, halved, pitted and peeled\n" +"Fine salt and freshly ground black pepper\n" +
				"4 slices whole grain or whole wheat bread\n" +
				"1 clove garlic, peeled and halved\n" +
				"2 tablespoons extra-virgin olive oil or unsalted butter, softened\n" +
				"Flaky sea salt, for serving \n" +
				"Crushed red pepper flakes, optional");
		recipe.setRating(4.5);

		ls.add(recipe);

		recipe = new Recipe();
		recipe.setName("Tofu\nCurry");
		recipe.setImage("file:assets/TofuCurry.jpeg");
		recipe.setchefName("By Jamie Oliver");

		recipe.setDescription("Place all the ingredients, except the vegetable oil, tofu, butter, cilantro and salt, into a blender and blitz into a very smooth paste.\r\n"
				+ "Heat the oil in a wide saucepan or wok. Add the tomato-onion paste and cook it, covered, over a medium-high flame, stirring frequently, until the moisture is mostly gone, the paste looks darker, and shimmers with oil droplets. The paste should look quite sticky at this point and should not taste raw. This step should take you anywhere from 10-15 minutes.\r\n"
				+ "Add 2-3 cups of water or vegetable stock, depending on the thickness you want for your curry, along with the tofu cubes. Add salt, stir to mix everything, bring back to a boil and cover and cook another five minutes. Stir in the vegan butter.\r\n"
				+ "Serve hot or warm, garnished with cilantro.");
		recipe.setContents("1 medium onion \r\n"
				+ "3 medium tomatoes \r\n"
				+ "1 inch knob ginger\r\n"
				+ "4 cloves garlic\r\n"
				+ "1 tablespoon coriander powder\r\n"
				+ "1 tablespoon garam masala\r\n"
				+ "2 teaspoons paprika\r\n"
				+ "2 tablespoons raw cashews \r\n"
				+ "1 teaspoon sugar\r\n"
				+ "1 tablespoon kasoori methi \r\n"
				+ "1 tablespoon vegetable oil\r\n"
				+ "14 oz extra firm tofu \r\n"
				+ "Salt to taste\r\n"
				+ "2 tablespoon vegan butter\r\n"
				+ "2 tablespoon cilantro");
		recipe.setRating(4.0);

		ls.add(recipe);

		recipe = new Recipe();
		recipe.setName("Pink Sauce\nPasta");
		recipe.setImage("file:assets/PinkSausePasta.jpeg");
		recipe.setchefName("By Gordan Ramsay");
		recipe.setDescription("Bring a large pot of salted water to a boil and cook the penne pasta to al dente, according to the package instructions. \r\n"
				+ "Heat a large non-stick skillet over medium heat, melt the butter and then add in the onions. Cook for about 5 minutes, or until they are translucent. Add the garlic and cook for another 1 - 2 minutes, or until aromatic. (Stir frequently to avoid burning  your garlic and onions.)\r\n"
				+ "Now add the tomato sauce, chicken broth, dried basil, dried oregano, salt and stir. Bring to a simmer for 2 - 3 minutes, stirring occasionally.\r\n"
				+ "Add in the milk and cream cheese and stir until melted, then mix in the shredded mozzarella cheese and let simmer for 7 - 8 minutes (it’s important to not let the sauce boil, but just simmer instead).\r\n"
				+ "Mix the pasta in with the sauce. Garnish with the chopped parsley and a little extra mozzarella if desired then serve!");
		recipe.setContents("3 cups dry penne pasta (300 grams)\r\n"
				+ "1 Tablespoon butter\r\n"
				+ "2 medium cloves garlic (minced)\r\n"
				+ "1 large yellow onion (diced)\r\n"
				+ "1 (8 ounce) can tomato sauce\r\n"
				+ "1 teaspoon dried oregano\r\n"
				+ "1/2 teaspoon fine sea salt\r\n"
				+ "1/2 teaspoon dried basil\r\n"
				+ "1/2 cup chicken broth\r\n"
				+ "1/2 cup milk*\r\n"
				+ "4 ounces full fat cream cheese (half a brick)\r\n"
				+ "1 cup freshly shredded pizza mozzarella**\r\n");
		recipe.setRating(3.2);


		ls.add(recipe);



		recipe = new Recipe();
		recipe.setName("Brocolli\nSoup");
		recipe.setImage("file:assets/brocolli_soup.jpeg");
		recipe.setchefName("Pannaga Veeramohan");
    recipe.setDescription("Bring a large pot of salted water to a boil and cook the penne pasta to al dente, according to the package instructions. \r\n"
				+ "Heat a large non-stick skillet over medium heat, melt the butter and then add in the onions. Cook for about 5 minutes, or until they are translucent. Add the garlic and cook for another 1 - 2 minutes, or until aromatic. (Stir frequently to avoid burning  your garlic and onions.)\r\n"
				+ "Now add the tomato sauce, chicken broth, dried basil, dried oregano, salt and stir. Bring to a simmer for 2 - 3 minutes, stirring occasionally.\r\n"
				+ "Add in the milk and cream cheese and stir until melted, then mix in the shredded mozzarella cheese and let simmer for 7 - 8 minutes (it’s important to not let the sauce boil, but just simmer instead).\r\n"
				+ "Mix the pasta in with the sauce. Garnish with the chopped parsley and a little extra mozzarella if desired then serve!");
		recipe.setContents("3 cups dry penne pasta (300 grams)\r\n"
				+ "1 Tablespoon butter\r\n"
				+ "2 medium cloves garlic (minced)\r\n"
				+ "1 large yellow onion (diced)\r\n"
				+ "1 (8 ounce) can tomato sauce\r\n"
				+ "1 teaspoon dried oregano\r\n"
				+ "1/2 teaspoon fine sea salt\r\n"
				+ "1/2 teaspoon dried basil\r\n"
				+ "1/2 cup chicken broth\r\n"
				+ "1/2 cup milk*\r\n"
				+ "4 ounces full fat cream cheese (half a brick)\r\n"
				+ "1 cup freshly shredded pizza mozzarella**\r\n");
		recipe.setRating(4.5);

		ls.add(recipe);


		recipe = new Recipe();

		recipe.setName("Masala \nDosa");
		recipe.setImage("file:assets/MasalaDosa.jpeg");
		recipe.setchefName("By Visa Jain");
		recipe.setDescription("Wash both the rice two to three times and soak in six cups of water for at least four hours. Wash and soak split black gram with fenugreek seeds in three cups of water also for a similar time.\r\n"
				+ "Drain and grind the rice and split black gram separately to a smooth texture and dropping consistency. Add salt and mix both the batters with hand thoroughly in a whipping motion.\r\n"
				+ "Keep the batter in a large vessel, close tightly and rest the batter overnight or for about four to six hours at room temperature to ferment.\r\n"
				+ "To make the potato bhaji, heat the oil in a kadai. Add the mustard seeds and when they splutter add the asafoetida and split Bengal gram and sauté till lightly browned.\r\n"
				+ "Add the green chillies, curry leaves and onion and sauté till onion is lightly browned. Add the potatoes, turmeric powder and salt. Mix well. Sprinkle a tablespoon of water and cook till the potatoes are heated through.\r\n"
				+ "Add the coriander leaves and lemon juice and mix well. Mix the batter well, adjust to pouring consistency. Heat a flat tawa (preferably non-stick), grease with a little oil. Pour a ladle full of batter and spread to as thin a pancake as possible.\r\n"
				+ "Couple of dosas may go wrong but once the tawa gets seasoned the rest of the dosas will come out well. Pour the oil/ghee around the dosa and let it cook till it becomes crisp on the edges and turns golden brown.");
		recipe.setContents("Parboiled rice(ukda chawal) 2 3/4 cups.\r\n"
				+ "Split black gram skinless 1/4 cup.\r\n"
				+ "Split black gram skinless (dhuli urad dal) 1 cup.\r\n"
				+ "Fenugreek seeds (methi dana) 1 teaspoon.\r\n"
				+ "Oil as required.\r\n"
				+ "salt as required\r\n"
				+ "potatoes boiled n peeled 3 large\r\n"
				+ "Oil 1 tablespoon.\r\n"
				+ "Split Bengal gram (chana dal) 1 teaspoon.\r\n"
				+ "Asafoetida 1/4 teaspoon.\r\n"
				+ "Mustard seeds 1/2 teaspoon.\r\n"
				+ "Green chillies chopped 2.\r\n"
				+ "onions chopped 2 large\r\n"
				+ "Curry leaves 6-8.\r\n"
				+ "turmeric powder 1/2 tbsp\r\n"
				+ "fresh coriander for topping\r\n"
				+ "lemon juice for topping");
		recipe.setRating(4.5);


		ls.add(recipe);

		return ls;

	}

	@FXML
	public void handleMealPlanClick(ActionEvent event) throws IOException {
	    Parent root = FXMLLoader.load(getClass().getResource("MealPlan.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();

	}


  @FXML
	public void handleSavedRecipesClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("SavedRecipes.fxml"));
    	stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}

	@FXML
	public void handleAppetizerClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("AppetizerPage.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	public void handleBreakfastClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("BreakfastPage.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	public void handleLunchClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("LunchPage.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	public void handleDinnerClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("DinnerPage.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}

	@FXML
	public void handlemyProfileClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("MyProfile.fxml"));
	    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    Scene scene = new Scene(root);
	    stage.setScene(scene);
	    stage.show();
	}



 	@FXML
	public void handleLogoutClick(ActionEvent event) throws IOException {

 		UserAccount.idUserAccount = -1;
		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}




}
