package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML
    TextField nameTF, phoneTF, addressTF, usernameTF, passwordTF;
    DatabaseHandler databaseHandler = DatabaseHandler.getInstance();

    @FXML
    private void switchToLogin() {
        SceneController.switchTo("login");
    }

    @FXML
    private void createAccount() {
        int phoneNumber;
        try {
            phoneNumber = Integer.parseInt(phoneTF.getText());
        } catch (Exception e) {
            System.out.println("Invalid type");
            return;
        }
        String name = nameTF.getText();
        String address = addressTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        databaseHandler.createNewAccount(phoneNumber, name, address, username, password);
        switchToLogin();
    }
}
