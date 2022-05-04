package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    ListView<String> listViewCamperDates;
    @FXML
    ComboBox<String> comboBoxCampers;
    @FXML
    ComboBox<String> comboBoxInsurance;
    @FXML
    TextArea textAreaInsurance;
    @FXML
    TextArea textAreaCamperDetails;
    @FXML
    Button buttonSelectStartWeek;
    @FXML
    Button buttonSelectEndWeek;
    @FXML
    Text textStartWeek;
    @FXML
    Text textEndWeek;
    @FXML
    Text textTotalRentPeriod;
    @FXML
    Text textDateError;
    @FXML
    Text textTenPercentDeadline;
    @FXML
    Text textNinetyPercentDeadline;
    DatabaseHandler db = DatabaseHandler.getInstance();
    List<List<String>> insuranceDetails = db.getInsuranceDetails();

    /**
     * Initializes the controller class.
     *
     * @param url            The URL of the controller class.
     * @param resourceBundle The resource bundle of the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setComboBoxCampers();
        setComboBoxInsurance();
        setComboBoxInsuranceListener();
        setComboBoxCampersListener();
        setListViewCamperDatesListener();
        buttonSelectStartWeek.setDisable(true);
        buttonSelectEndWeek.setDisable(true);
        Main.resetValues();
    }

    @FXML
    private void switchToLogin() {
        SceneController.switchTo("login");
    }

    /**
     * Sets the beginning week of the rental period.
     * Also sets the deadline for the 10% and 90% of the rent
     */
    @FXML
    private void buttonSelectStartClick() {
        Main.startWeekNumber = Main.formatDateToWeekNumber(listViewCamperDates.getSelectionModel().getSelectedItem());
        Main.startWeekString = listViewCamperDates.getSelectionModel().getSelectedItem();
        textStartWeek.setText(Main.startWeekString);
        setTotalWeek();
        setTextDeadlineValues();
        checkWeekError();
        buttonSelectEndWeek.setDisable(false);
    }

    /**
     * Sets the end week of the rental period.
     * Also sets the total rent period.
     */
    @FXML
    private void buttonSelectEndClick() {
        Main.endWeekNumber = Main.formatDateToWeekNumber(listViewCamperDates.getSelectionModel().getSelectedItem());
        Main.endWeekString = listViewCamperDates.getSelectionModel().getSelectedItem();
        textEndWeek.setText(Main.endWeekString);
        setTotalWeek();
        checkWeekError();
    }

    private void setTotalWeek() {
        Main.totalWeek = Main.endWeekNumber - Main.startWeekNumber + 1;
        if (Main.totalWeek > 0) {
        textTotalRentPeriod.setText("%s weeks".formatted(Main.totalWeek));
        } else {
            textTotalRentPeriod.setText("? weeks");
        }
    }

    /**
     * Checks whether the start week value is larger than the end week
     */
    private void checkWeekError() {
        if (Main.startWeekNumber > Main.endWeekNumber && Main.endWeekNumber != 0) {
            textDateError.setText("ERROR: Start week is larger than end week");
            // TODO: turn off proceed button here
        } else {
            textDateError.setText("");
        }
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

    /**
     * Fills the listViewCamperDates with the available dates for the selected camper
     * @param yearCount The number of years to show
     */
    private void fillListViewCamperDates(int yearCount) {
        listViewCamperDates.getItems().clear();
        // Get the current week number
        // +12 because the full price must be paid before 8 weeks of the rental start, just to make sure there are no conflicts
        for (int i = Main.currentWeekNumber + 12; i <= 52*yearCount; i++) {
            listViewCamperDates.getItems().add(Main.formatWeekNumberToDate(i));
        }
    }

    /**
     * Enables the select start week button.
     */
    private void setListViewCamperDatesListener() {
        listViewCamperDates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) ->
                buttonSelectStartWeek.setDisable(false));
    }

    private void fillTextAreaCamperDetails() {
        List<String> camperDetails = db.getCamperCategoryDetails(comboBoxCampers.getValue());
        textAreaCamperDetails.setText(String.join("\n", camperDetails));
    }

    /**
     * Listener for comboBoxCampers.
     * @method fillListViewCamperDates() fills the listViewCamperDates with the available dates for the selected camper
     * @method fillTextAreaCamperDetails() fills the textAreaCamperDetails with the camper details
     */
    private void setComboBoxCampersListener() {
        comboBoxCampers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillListViewCamperDates(4);
                fillTextAreaCamperDetails();
            }
        });
    }

    /**
     * Sets the insurance details in the text area.
     */
    private void setComboBoxInsuranceListener() {
        comboBoxInsurance.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    textAreaInsurance.setText(String.join("\n", insuranceDetails.get(Integer.parseInt(newValue) - 1)));
                    Main.priceInsurance = Double.parseDouble(insuranceDetails.get(Integer.parseInt(newValue) - 1).get(0));
                } catch (Exception e) {
                    textAreaInsurance.setText("No insurance selected");
                    Main.priceInsurance = 0;
                }
            }
        });
    }

    /**
     * Sets the text of the 10% and 90% deadline text fields with yyyy/MM/dd format.
     */
    private void setTextDeadlineValues() {
            // Calculate the day 2 weeks after today
            LocalDate tenDeadline = LocalDate.now().plusDays(14);
            textTenPercentDeadline.setText(tenDeadline.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            // Calculate the day 8 weeks before the start of the rental
            int ninetyDeadline = Main.startWeekNumber - 7;
            String formattedDate = Main.formatWeekNumberToDate(ninetyDeadline);
            textNinetyPercentDeadline.setText(formattedDate);
    }
}
