package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.net.URL;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    ListView<Integer> listViewCamperDates;
    @FXML
    ComboBox<String> comboBoxCampers;
    @FXML
    ComboBox<String> comboBoxInsurance;
    @FXML
    TextArea textAreaInsurance;
    @FXML
    TextArea textAreaCamperDetails;
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

    @FXML
    private void switchToLogin() {
        SceneController.switchTo("login");
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
        comboBoxInsurance.getItems().add("None");
        for (int i = 1; i <= insuranceDetails.size(); i++) {
            comboBoxInsurance.getItems().add(String.valueOf(i));
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

    private void fillTextAreaCamperDetails() {
        List<String> camperDetails = db.getCamperCategoryDetails(comboBoxCampers.getValue());
        textAreaCamperDetails.setText(String.join("\n", camperDetails));
    }

    private void setComboBoxCampersListener() {
        comboBoxCampers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            assert newValue != null;
            fillListViewCamperDates();
            fillTextAreaCamperDetails();
        });
    }

    /**
     * Sets the insurance details in the text area.
     */
    private void setComboBoxInsuranceListener() {
        comboBoxInsurance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            assert newValue != null;
            try {
                textAreaInsurance.setText(String.join("\n", insuranceDetails.get(Integer.parseInt(newValue) - 1)));
                Main.priceInsurance = Double.parseDouble(insuranceDetails.get(Integer.parseInt(newValue) - 1).get(0));
            } catch (Exception e) {
                textAreaInsurance.setText("No insurance selected");
                Main.priceInsurance = 0.0;
            }
        });
    }


}
