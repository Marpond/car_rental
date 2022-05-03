package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    TextField textFieldUsername;
    @FXML
    TextField textFieldPassword;
    @FXML
    Button buttonLogin;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        buttonLogin.setDisable(true);

        DatabaseHandler db = DatabaseHandler.getInstance();

        db.testInsert();

        setTextFieldListeners();
    }

    private void setTextFieldListeners() {
        textFieldUsername.textProperty().addListener((observable, oldValue, newValue) ->
            buttonLogin.setDisable(newValue.length() <= 0));
        textFieldPassword.textProperty().addListener((observable, oldValue, newValue) ->
            buttonLogin.setDisable(newValue.length() <= 0));
    }
}