package application;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class DetailCardController implements Initializable{

	 DatabaseConnection connectNow = new DatabaseConnection();
	 Connection connectDB = connectNow.getConnection();
	@FXML
	private Button DetailSaveBtn;

	@FXML
	private ImageView DetailrecipeImage;

	@FXML
	private Label chefNameDetail;

	@FXML
	private Label recipeDescriptionDetail;

	@FXML
	private Label recipeContentsDetail;

	@FXML
	private ImageView recipeRatingDetail;

	@FXML
	private Button backbtn;

	@FXML
	private Label recipeNameDetailTo;

	@FXML
	private Label Mymsg;

	@FXML
	private Button commentsbtn;

	@FXML
    private TextArea addComments;

	@FXML
    private Label displayComments;


	@FXML
    private TextField ratingEdit;



	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {


	}

	public void initData(Recipe recipe) {

	    try {


	        recipeNameDetailTo.setText(recipe.getName());
	        chefNameDetail.setText(recipe.getchefName());
	        recipeDescriptionDetail.setText(recipe.getDescription());
	        recipeContentsDetail.setText(recipe.getContents());



	        System.out.println("-------Printing the ratings image------- ");


	        switch((int) Math.floor(recipe.getRating())) {
	        case 5:
	        	recipeRatingDetail.setImage(new Image("File:assets/Four_star.png"));
	            break;
	        case 4:
	            recipeRatingDetail.setImage(new Image("File:assets/Four_star.png"));
	            break;
	        case 3:
	            recipeRatingDetail.setImage(new Image("File:assets/Three_star.jpeg"));
	            break;
	        case 2:
	            recipeRatingDetail.setImage(new Image("File:assets/Two_star.png"));
	            break;
	        default:
	            recipeRatingDetail.setImage(new Image("File:assets/One_star.jpeg"));
	    }


	    } catch (Exception e) {
	        e.printStackTrace();
	    }

	}

	public void setImage(Image img) {

		 DetailrecipeImage.setImage(img);
	}

	@FXML
	void backButtonClicked(ActionEvent event) throws IOException {
		Parent root = FXMLLoader.load(getClass().getResource("HomePage.fxml"));
    	Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
    	Scene scene = new Scene(root);
    	stage.setScene(scene);
    	stage.show();
	}

	public void handleButtonClick(Recipe recipe) throws SQLException {
		String MoreBtnRecipeName = recipe.getName();
    	System.out.println("------getting recipe name----" + MoreBtnRecipeName);
    	String selectQuery = "SELECT * FROM RecipesInfo WHERE recipeName = ?";
        PreparedStatement selectStatement = connectDB.prepareStatement(selectQuery);
        selectStatement.setString(1, MoreBtnRecipeName);
        ResultSet resultSet = selectStatement.executeQuery();
        System.out.println(resultSet);

        if (resultSet.next()) {
            String Name = resultSet.getString("recipeName");
            String Description = resultSet.getString("recipeDescription");
            String Chef = resultSet.getString("recipeChef");
            double Rating = resultSet.getDouble("recipeRating");
            String Contents = resultSet.getString("recipeContents");
            byte[] recipeImg = resultSet.getBytes("recipesImg");
            String comments = resultSet.getString("Comments");

            Image image = new Image(new ByteArrayInputStream(recipeImg));


            // Set the retrieved data to the Recipe object
            recipe.setName(Name);
            recipe.setDescription(Description);
            recipe.setchefName(Chef);
            recipe.setRating(Rating);
            recipe.setContents(Contents);
            recipe.setImageDetail(image);
            recipe.setComments(comments);
            recipe.setByteImage(recipeImg);
            System.out.println("----image----" + recipeImg);
            System.out.println("Inside handle button action");

//            DetailCardController dcl = new DetailCardController();
//        	dcl.initData(recipe);


            try {
        		System.out.println("Before--");

               System.out.println("Inside handle button action");
                System.out.println("recipe.getImage()----"+recipe.getImageDetail(image));
//                if (image == null || image.equals(null)) {
//                    throw new Exception("Image not found");
//                }
                DetailrecipeImage.setImage(recipe.getImageDetail(image));
                System.out.print(DetailrecipeImage);
                System.out.println("after--");

            } catch (Exception e) {
                e.printStackTrace();
            }
        	System.out.println("    recipeNameDetailTo -------"+recipeNameDetailTo);
            recipeNameDetailTo.setText(recipe.getName());
    		chefNameDetail.setText(recipe.getchefName());
    		recipeDescriptionDetail.setText(recipe.getDescription());
    		recipeContentsDetail.setText(recipe.getContents());
    		displayComments.setText(recipe.getComments());



    	        handleDetailSaveBtn(recipe);



    		 switch((int) Math.floor(recipe.getRating())) {
 	        case 5:
 	        	recipeRatingDetail.setImage(new Image("File:assets/Four_star.png"));
 	            break;
 	        case 4:
 	            recipeRatingDetail.setImage(new Image("File:assets/Four_star.png"));
 	            break;
 	        case 3:
 	            recipeRatingDetail.setImage(new Image("File:assets/Three_star.jpeg"));
 	            break;
 	        case 2:
 	            recipeRatingDetail.setImage(new Image("File:assets/Two_star.png"));
 	            break;
 	        default:
 	            recipeRatingDetail.setImage(new Image("File:assets/One_star.jpeg"));
 	    }


	}else {
        System.out.println("Recipe not found in the database");
    }
        System.out.println("Received object: " );






	}

	@FXML
	void handleAddComments(ActionEvent event) throws SQLException {
		String comments = addComments.getText();

		PreparedStatement updateps = connectDB.prepareStatement("UPDATE recipesInfo SET Comments = ? WHERE recipeName = ?");
		updateps.setString(1, comments);
		updateps.setString(2, recipeNameDetailTo.getText());
		updateps.executeUpdate();



        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Comments added");
        alert.setHeaderText(null);
        alert.setContentText("Comments added");
        alert.showAndWait();

        displayComments.setText(comments);


	}



	void handleDetailSaveBtn(Recipe recipe) {
		DetailSaveBtn.setOnAction(event -> {
	   		System.out.println("Entering Save Button Details Page");
	   			// Create a new SavedRecipesController instance
	   			FXMLLoader loader = new FXMLLoader(getClass().getResource("SavedRecipes.fxml"));
	   			try {
	        		Parent root = loader.load();
					SavedRecipesController controller = loader.getController();
					if(SavedRecipesController.recipes.contains(recipe)) {
		        		Alert alert = new Alert(AlertType.INFORMATION);
		                alert.setTitle("Recipe Saved");
		                alert.setHeaderText(null);
		                alert.setContentText("The recipe is already saved.");
		                alert.showAndWait();
		        	}
		        	else {
		        		controller.handleSaveButtonClick(recipe);
			        	System.out.println("Object recieved at cardController" + recipe);
			        	SavedRecipesController.recipes.add(recipe);

		        	}

	        	} catch (IOException e) {
					e.printStackTrace();
	        	}

	   		});
	}

	@FXML
	void handleEditRating(ActionEvent event) throws SQLException {
		String rating = ratingEdit.getText();

		PreparedStatement updateps = connectDB.prepareStatement("UPDATE recipesInfo SET recipeRating = ? WHERE recipeName = ?");
		updateps.setString(1, rating);
		updateps.setString(2, recipeNameDetailTo.getText());
		updateps.executeUpdate();




        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ratings added");
        alert.setHeaderText(null);
        alert.setContentText("Ratings added");
        alert.showAndWait();


	}


}

