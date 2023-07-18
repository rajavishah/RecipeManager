package application;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public abstract class Register {
	@FXML protected TextField nameRegister;
    @FXML protected TextField passwordRegister;
    @FXML protected TextField emailRegister;
    @FXML protected TextField phoneRegister;
    @FXML protected TextField confirmPasswordRegister;


    protected abstract void register();

    protected void ValidateDetails() {
    	if (nameRegister.getText().isEmpty() || passwordRegister.getText().isEmpty() || confirmPasswordRegister.getText().isEmpty()|| emailRegister.getText().isEmpty()) {
            System.out.println("Please fill the fields");
            return;
        }

    }

    protected void clearDetails() {
    	 nameRegister.clear();
         passwordRegister.clear();
         confirmPasswordRegister.clear();
         phoneRegister.clear();
         emailRegister.clear();
    }
}
