package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public abstract class MyRecipeForm {

    @FXML
	public TextField recipeName;
    @FXML protected TextArea ingredients;
    @FXML protected TextArea stepsToCook;
    @FXML protected RadioButton vegetarianDietType;
    @FXML protected RadioButton eggetarianDietType;
    @FXML protected RadioButton nonvegetarianDietType;
    @FXML protected Button cancelButton;
    @FXML protected Button submitButton;

    // Handle the Cancel button click event
    @FXML void handleCancelButton(ActionEvent event) {
        // Add your implementation for the cancel button click event
    }

    // Handle the Submit button click event
    @FXML
    void handleSubmitButton(ActionEvent event) {
        // Add your implementation for the submit button click event
    }

    @FXML
    void handleDietType(ActionEvent event) {
        // Add your implementation for the vegetarian radio button click event
    }

}
