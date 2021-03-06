package carrental.car_rental;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.net.URL;
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
    Button buttonProceed;
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
        fillComboBoxCampers();
        fillComboBoxInsurance();
        setComboBoxInsuranceListener();
        setComboBoxCampersListener();
        setListViewCamperDatesListener();
        setButtonProceedEnablerTimeline();
        buttonSelectStartWeek.setDisable(true);
        buttonSelectEndWeek.setDisable(true);
        buttonProceed.setDisable(true);
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
        checkSelectedWeekError();
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
        checkSelectedWeekError();
    }

    /**
     * Sets a timeline that will check every 1/3 of a second whether the user has selected a valid start and end week,
     * and has chosen an insurance option.
     */
    private void setButtonProceedEnablerTimeline() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(333), event -> buttonProceed.setDisable(isWeekOrderIncorrect() || textAreaInsurance.getText().equals("") || Main.endWeekNumber == 0)));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
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
    private void checkSelectedWeekError() {
        if (isWeekOrderIncorrect() && Main.endWeekNumber != 0) {
            textDateError.setText("ERROR: Start week is larger than end week");
        } else {
            textDateError.setText("");
        }
    }

    private boolean isWeekOrderIncorrect() {
        return Main.startWeekNumber > Main.endWeekNumber;
    }

    /**
     * Sets the campers in the combo box.
     */
    private void fillComboBoxCampers() {
        List<String> campers = db.getCamperLicensePlates();
        comboBoxCampers.getItems().addAll(campers);
    }

    /**
     * Sets the insurance numbers in the combo box.
     */
    private void fillComboBoxInsurance() {
        comboBoxInsurance.getItems().add("None");
        for (int i = 1; i <= insuranceDetails.size(); i++) {
            comboBoxInsurance.getItems().add(String.valueOf(i));
        }
    }

    /**
     * Fills the listViewCamperDates with the available dates for the selected camper
     */
    private void fillListViewCamperDates() {
        listViewCamperDates.getItems().clear();
        // Get the current week number
        // +12 because the full price must be paid before 8 weeks of the rental start, just to make sure there are no conflicts
        int yearCount = 4;
        int weekCountInYear = 52;
        for (int i = Main.currentWeekNumber + 12; i <= weekCountInYear * yearCount; i++) {
            listViewCamperDates.getItems().add(Main.formatWeekNumberToDate(i));
        }
    }

    /**
     * Enables the select start week button.
     */
    private void setListViewCamperDatesListener() {
        listViewCamperDates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> buttonSelectStartWeek.setDisable(false));
    }

    private void fillTextAreaCamperDetails() {
        List<String> camperDetails = db.getCamperCategoryDetails(comboBoxCampers.getValue());
        textAreaCamperDetails.setText(String.join("\n", camperDetails));
    }

    /**
     * Listener for comboBoxCampers.
     *
     * @method fillListViewCamperDates() fills the listViewCamperDates with the available dates for the selected camper
     * @method fillTextAreaCamperDetails() fills the textAreaCamperDetails with the camper details
     */
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
