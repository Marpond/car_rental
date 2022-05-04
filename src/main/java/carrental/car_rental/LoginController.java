package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldPassword;
    @FXML
    Text textErrorPrompt;
    @FXML
    Button buttonLogin;
    DatabaseHandler db = DatabaseHandler.getInstance();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.setDisable(true);
        setTextFieldListeners();
    }

    @FXML
    private void login() {
        String username = textFieldUsername.getText();
        String password = textFieldPassword.getText();
        HashMap<String, String> accounts = db.getAccounts();;
        // If the accounts hashmap contains the username and password,
        // then the user is logged in.
        if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
            // Check if the user is an admin.
            if (username.equals("admin")) {
                SceneController.switchTo("admin");
            } else {
                SceneController.switchTo("user");
            }
        } else {
            // If the username or password is incorrect, display an error message.
            textErrorPrompt.setText("Incorrect username or password.");
        }
    }

    private void setTextFieldListeners() {
        textFieldUsername.textProperty().addListener((observable, oldValue, newValue) ->
            buttonLogin.setDisable(newValue.length() <= 0 || textFieldPassword.getText().length() <= 0));
        textFieldPassword.textProperty().addListener((observable, oldValue, newValue) ->
            buttonLogin.setDisable(newValue.length() <= 0 || textFieldUsername.getText().length() <= 0));
    }
}