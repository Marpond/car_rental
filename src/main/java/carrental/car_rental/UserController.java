package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
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
        Main.startWeek = Main.formatDateToWeekNumber(listViewCamperDates.getSelectionModel().getSelectedItem());
        buttonSelectEndWeek.setDisable(false);
        buttonSelectStartWeek.setDisable(true);
        textStartWeek.setText(String.valueOf(Main.startWeek));
        setTextDeadlineValues();
    }

    /**
     * Sets the end week of the rental period.
     * Also sets the total rent period.
     */
    @FXML
    private void buttonSelectEndClick() {
        Main.endWeek = Main.formatDateToWeekNumber(listViewCamperDates.getSelectionModel().getSelectedItem());
        textEndWeek.setText(String.valueOf(Main.endWeek));
        Main.totalWeek = Main.endWeek - Main.startWeek + 1;
        textTotalRentPeriod.setText("%s weeks".formatted(Main.totalWeek));
    }

    @FXML
    private void buttonResetCamperListViewClick() {
        Main.startWeek = 0;
        Main.endWeek = 0;
        textStartWeek.setText("");
        textEndWeek.setText("");
        textTotalRentPeriod.setText("");
        textTenPercentDeadline.setText("");
        textNinetyPercentDeadline.setText("");
        buttonSelectStartWeek.setDisable(true);
        buttonSelectEndWeek.setDisable(true);
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
        // +12 because the full price must be paid before 8 weeks of the rental start, just to make sure there are no conflicts
        for (int i = Main.currentWeekNumber + 12; i <= 52*4; i++) {
            listViewCamperDates.getItems().add(Main.formatWeekNumberToDate(i));
        }
    }

    /**
     * Disables or enables the buttons depending on the selected item in the combo box.
     */
    private void setListViewCamperDatesListener() {
        listViewCamperDates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                buttonSelectStartWeek.setDisable(Main.formatDateToWeekNumber(newValue) <= Main.startWeek);
                if (Main.startWeek != 0) {
                    buttonSelectEndWeek.setDisable(Main.formatDateToWeekNumber(newValue) < Main.startWeek);
                }
            }
        });
    }

    private void fillTextAreaCamperDetails() {
        List<String> camperDetails = db.getCamperCategoryDetails(comboBoxCampers.getValue());
        textAreaCamperDetails.setText(String.join("\n", camperDetails));
    }

    private void setComboBoxCampersListener() {
        comboBoxCampers.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                fillListViewCamperDates();
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
                    Main.priceInsurance = 0.0;
                }
            }
        });
    }
    private void setTextDeadlineValues() {
            // Calculate the day 2 weeks after today
            LocalDate tenDeadline = LocalDate.now().plusDays(14);
            textTenPercentDeadline.setText(tenDeadline.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")));
            // Calculate the day 8 weeks before the start of the rental
            int ninetyDeadline = Main.startWeek - 7;
            String formattedDate = Main.formatWeekNumberToDate(ninetyDeadline);
            textNinetyPercentDeadline.setText(formattedDate);
    }
}
