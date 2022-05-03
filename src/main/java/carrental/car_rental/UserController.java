package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    ListView<String> listViewCamperDates;
    @FXML
    ComboBox<String> comboBoxCampers;

    DatabaseHandler db = DatabaseHandler.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    private void setComboBoxCampers() {
        List<String> campers = db.getCamperLicensePlates();
        comboBoxCampers.getItems().addAll(campers);
    }
}
