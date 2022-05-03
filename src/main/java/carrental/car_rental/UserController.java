package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    ListView<Integer> listViewCamperDates;
    @FXML
    ComboBox<String> comboBoxCampers;
    @FXML
    ComboBox<Integer> comboBoxInsurance;
    @FXML
    TextArea textAreaInsurance;
    DatabaseHandler db = DatabaseHandler.getInstance();
    List<List<String>> insuranceDetails = db.getInsuranceDetails();

    /**
     * Initializes the controller class.
     * @param url The URL of the controller class.
     * @param resourceBundle The resource bundle of the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBoxCampers();
        setComboBoxInsurance();
        setComboBoxInsuranceListener();
        setComboBoxCampersListener();
    }

    /**
     * Sets the campers in the combo box.
     */
    private void setComboBoxCampers() {
        List<String> campers = db.getCamperLicensePlates();
        comboBoxCampers.getItems().addAll(campers);
    }

    /**
     * Sets the insurance numbers in the combo box.
     */
    private void setComboBoxInsurance() {
        for (int i = 1; i <= insuranceDetails.size(); i++) {
            comboBoxInsurance.getItems().add(i);
        }
    }

    private void fillListViewCamperDates() {
        listViewCamperDates.getItems().clear();
        // Get the current week number
        int currentWeek = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());
        for (int i = currentWeek; i <= 52; i++) {
            listViewCamperDates.getItems().add(i);
        }
    }

    private void setComboBoxCampersListener() {
        comboBoxCampers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            assert newValue != null;
            fillListViewCamperDates();
        });
    }

    /**
     * Sets the insurance details in the text area.
     */
    private void setComboBoxInsuranceListener() {
        comboBoxInsurance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            assert newValue != null;
            textAreaInsurance.setText(String.join("\n",insuranceDetails.get(newValue - 1)));
            // Set the price of the insurance
            Main.priceInsurance = Double.parseDouble(insuranceDetails.get(newValue - 1).get(0));
        });
    }


}
