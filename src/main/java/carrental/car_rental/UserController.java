package carrental.car_rental;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;
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
     */
    @FXML
    private void buttonSelectStartClick() {
        Main.startWeek = listViewCamperDates.getSelectionModel().getSelectedItem();
        buttonSelectEndWeek.setDisable(false);
        buttonSelectStartWeek.setDisable(true);
        textStartWeek.setText(String.valueOf(Main.startWeek));
    }

    /**
     * Sets the end week of the rental period.
     * Also sets the total rent period and the deadline for the 10% and 90% of the rent.
     */
    @FXML
    private void buttonSelectEndClick() {
        Main.endWeek = listViewCamperDates.getSelectionModel().getSelectedItem();
        textEndWeek.setText(String.valueOf(Main.endWeek));
        Main.totalWeek = Main.endWeek - Main.startWeek + 1;
        textTotalRentPeriod.setText(String.valueOf(Main.totalWeek));
        setTextDeadlineValues();
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
        int currentWeek = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());
        // +9 because the full price must be paid before 8 weeks of the rental starts
        for (int i = currentWeek + 9; i <= 52; i++) {
            listViewCamperDates.getItems().add(i);
        }
    }

    /**
     * Disables or enables the buttons depending on the selected item in the combo box.
     */
    private void setListViewCamperDatesListener() {
        listViewCamperDates.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                buttonSelectStartWeek.setDisable(newValue < Main.startWeek);
                if (Main.startWeek != 0) {
                    buttonSelectEndWeek.setDisable(newValue < Main.startWeek);
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
        if (Main.startWeek != 0) {
            // Calculate the day 2 weeks after today
            LocalDate tenDeadline = LocalDate.now().plusDays(14);
            textTenPercentDeadline.setText(tenDeadline.toString());
            // Calculate the day 8 weeks before the start of the rental
            int ninetyDeadline = Main.startWeek - 8;
            String formattedDate = formatWeekNumberToDate(ninetyDeadline);
            textNinetyPercentDeadline.setText(formattedDate);
        }
    }

    private String formatWeekNumberToDate(int weekNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date ninetyDeadlineDate = calendar.getTime();
        return new SimpleDateFormat("yyyy-MM-dd").format(ninetyDeadlineDate);
    }

    private int formatDateToWeekNumber(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }


}
