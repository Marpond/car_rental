package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    @FXML
    TextField nameTF, phoneTF, addressTF, usernameTF, passwordTF;

    DatabaseHandler databaseHandler;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        databaseHandler = DatabaseHandler.getInstance();

    }

    @FXML
    private void switchToLogin() {
        SceneController.switchTo("login");
    }

    @FXML
    private void createAccount(){
        int phonenumber = 0;
        try{
            phonenumber = Integer.parseInt(phoneTF.getText());
        }catch (Exception e){
            System.out.println("Invalid type");
            return;
        }
        String name = nameTF.getText();
        String address = addressTF.getText();
        String username = usernameTF.getText();
        String password = passwordTF.getText();

        databaseHandler.createNewAccount(phonenumber, name, address, username, password);
        switchToLogin();
    }
}
