package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MyProfileController implements Initializable{

	@FXML
    private Button addRecipe;

    @FXML
    private RadioButton eggetarian;

    @FXML
    private HBox foodBox;

    @FXML
    private ToggleGroup foodChoice;

    @FXML
    private Text foodText;

    @FXML
    private RadioButton glutenFree;

    @FXML
    private RadioButton high;

    @FXML
    private RadioButton ketoDiet;

    @FXML
    private RadioButton lowCarb;

    @FXML
    private HBox mealBox;

    @FXML
    private ToggleGroup mealChoice;

    @FXML
    private Text mealText;

    @FXML
    private RadioButton medium;

    @FXML
    private RadioButton mild;

    @FXML
    private Hyperlink myProfileBack;

    @FXML
    private VBox myRecipeBox;

    @FXML
    private RadioButton nonVegetarian;

    @FXML
    private Hyperlink profileLogout;

    @FXML
    private Hyperlink profileMealPlan;

    @FXML
    private VBox profilePreference;

    @FXML
    private Hyperlink profileSavedRecipe;

    @FXML
    private HBox spiceBox;

    @FXML
    private ToggleGroup spiceChoice;

    @FXML
    private Text spiceText;

    @FXML
    private RadioButton vegetarian;


    @FXML
    void addRecipeClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MyRecipeForm.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    @FXML
    void myProfileClick(ActionEvent event) {

    }

    @FXML
    void profilePreferenceClick(MouseEvent event) {

    }


    public void vegetarianClick(ActionEvent event) {
    	System.out.println(event);
        foodText.setText("Hello! You are on your way to a healthier lifestyle with a vegetarian diet");
        foodBox.setVisible(true);
    }

    public void eggetarianClick(ActionEvent event) {
    	System.out.println(event);
        foodText.setText("You've chosen an eggetarian diet that is both nutritious and delicious.");
        foodBox.setVisible(true);
    }

    public void nonVegetarianClick(ActionEvent event) {
    	System.out.println(event);
        foodText.setText("Welcome to the world of non-vegetarian cuisine! Get ready to explore!");
        foodBox.setVisible(true);
    }

    @FXML
    public void glutenFreeClick(ActionEvent event) {
    	System.out.println(event);
        mealText.setText("Great choice! A gluten-free diet can help improve your overall health");
        mealBox.setVisible(true);
    }

    @FXML
    public void lowCarbClick(ActionEvent event) {
    	System.out.println(event);
        mealText.setText("Low-carb diets can help you reduce your risk of certain health conditions.");
        mealBox.setVisible(true);
    }

    @FXML
    public void ketoDietClick(ActionEvent event) {
    	System.out.println(event);
        mealText.setText("You've opted for a keto diet! That helps you lose weight..");
        mealBox.setVisible(true);
    }

    @FXML
    public void mildClick(ActionEvent event) {
    	System.out.println(event);
        spiceText.setText("Mild spice level, eh? You prefer to savor the subtle flavors in your food. Excellent choice!");
        spiceBox.setVisible(true);
    }

    @FXML
    public void mediumClick(ActionEvent event) {
    	System.out.println(event);
        spiceText.setText("Medium spice level it is! You like a little kick in your food, but nothing too overwhelming. Enjoy!");
        spiceBox.setVisible(true);
    }

    @FXML
    public void highClick(ActionEvent event) {
    	System.out.println(event);
        spiceText.setText("High spice level, huh? You're a fan of the heat and the bold flavors that come with it.");
        spiceBox.setVisible(true);
    }

    @FXML
    private void handleAddRecipe(ActionEvent event) {

    		try {
    			System.out.println("Add preference");
    			Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
//    			Parent root = FXMLLoader.load(getClass().getResource("Myprofile.fxml"));
    		    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		    Scene scene = new Scene(root);
    		    stage.setScene(scene);
    		    stage.show();


    		} catch(Exception e) {
    			e.printStackTrace();
    		}
//        HBox newRecipeHBox = new HBox();
//        newRecipeHBox.setPrefSize(869, 50);
//        // add any necessary child nodes to the new HBox
//
//        myRecipeBox.getChildren().add(newRecipeHBox);
    }


    private void profilePreferenceClick(ActionEvent event) {
    	System.out.println("profile preference");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            // Create a new ToggleGroup for the food radio buttons
            ToggleGroup foodChoice = new ToggleGroup();

            // Add the radio buttons to the ToggleGroup
            vegetarian.setToggleGroup(foodChoice);
            eggetarian.setToggleGroup(foodChoice);
            nonVegetarian.setToggleGroup(foodChoice);

            // Create a new ToggleGroup for the food radio buttons
            ToggleGroup mealChoice = new ToggleGroup();

            // Add the radio buttons to the ToggleGroup
            ketoDiet.setToggleGroup(mealChoice);
            lowCarb.setToggleGroup(mealChoice);
            glutenFree.setToggleGroup(mealChoice);

            // Create a new ToggleGroup for the food radio buttons
            ToggleGroup spiceChoice = new ToggleGroup();

            // Add the radio buttons to the ToggleGroup
            mild.setToggleGroup(spiceChoice);
            medium.setToggleGroup(spiceChoice);
            high.setToggleGroup(spiceChoice);



            List<Recipe> recipes = getAll();
            for (Recipe recipe : recipes) {
                // create a new instance of FXMLLoader and set the location of the FXML file
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyDisplayCard.fxml"));

                // load the FXML file and retrieve its root element
                VBox cardBox = fxmlLoader.load();
                // retrieve the controller associated with the FXML file
                MyDisplayCardController controller = fxmlLoader.getController();
                // set the Recipe data to the controller
                controller.setRecipe(recipe);
                // add the root element to the container
                myRecipeBox.getChildren().add(cardBox);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Recipe> getAll() throws SQLException {
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();
        List<Recipe> recipes = new ArrayList<>();
        String selectQuery = "SELECT * FROM addmyrecipe WHERE idUserAccount = ?";
        PreparedStatement selectStatement = connectDB.prepareStatement(selectQuery);
        int userId = UserAccount.idUserAccount;
        selectStatement.setInt(1, userId);
        ResultSet resultSet = selectStatement.executeQuery();
        while (resultSet.next()) {
            String name = resultSet.getString("recipeName");
            String ingredients = resultSet.getString("ingredients");
            String stepsToCook = resultSet.getString("stepsToCook");
            String recipeType = resultSet.getString("recipeType");

            // create a new Recipe object
            Recipe recipe = new Recipe();
            recipe.setName(name);
            recipe.setDescription(stepsToCook);
            recipe.setContents(ingredients);
//            recipe.setType(recipeType);

            // add the Recipe object to the list
            recipes.add(recipe);
        }
        return recipes;
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
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}
    @FXML
	public void handleBackClick(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}


	@FXML
	public void handleLogoutClick(ActionEvent event) throws IOException {

		Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
		Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//    	try {
//			getAll();
//		} catch (SQLException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
//        // create a new instance of FXMLLoader and set the location of the FXML file
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("MyDisplayCard.fxml"));
//        try {
//            // load the FXML file and retrieve its root element
//            HBox cardBox = fxmlLoader.load();
//            // retrieve the controller associated with the FXML file
//            MyDisplayCardController controller = fxmlLoader.getController();
//            // add the root element to the container
//            myRecipeBox.getChildren().add(cardBox);
//
//        } catch (IOException e) {
//            // handle any exceptions that might occur during the loading of the FXML file
//            e.printStackTrace();
//        }
//    }
//
//	private void getAll() throws SQLException {
//		DatabaseConnection connectNow = new DatabaseConnection();
//	     Connection connectDB = connectNow.getConnection();
//
//		// TODO Auto-generated method stub
//		String selectQuery = "SELECT * FROM addmyrecipe WHERE idUserAccount = ?";
//	       PreparedStatement selectStatement = connectDB.prepareStatement(selectQuery);
//	       ArrayList<Integer> userIdList = UserAccount.getUserIdList();
////	       int userId = userIdList.get(0);
//	       int userId = UserAccount.idUserAccount;
//	       selectStatement.setInt(1, userId);
//	       ResultSet resultSet = selectStatement.executeQuery();
//	       Recipe recipe = new Recipe();
//	       if (resultSet.next()) {
//	           String Name = resultSet.getString("recipeName");
//	           String Ingredients = resultSet.getString("ingredients");
//	           String StepsToCook = resultSet.getString("stepsToCook");
//	           String recipeType = resultSet.getString("recipeType");
//
//
//
//
//	           // Set the retrieved data to the Recipe object
//	           recipe.setName(Name);
//	           recipe.setDescription(StepsToCook);
//	           recipe.setContents(Ingredients);
//	       }
//	}
//
//
}
