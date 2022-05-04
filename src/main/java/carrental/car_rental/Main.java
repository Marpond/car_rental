package carrental.car_rental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.Calendar;
import java.util.Date;

public class Main extends Application {
    public static Stage stage;

    public static double priceInsurance;
    public static double priceBooking;
    public static double priceTotal;
    public static int startWeekNumber;
    public static int endWeekNumber;
    public static int totalWeek;
    public static String startWeekString;
    public static String endWeekString;
    public static final int currentWeekNumber = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());
    public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

    @Override
    public void start(Stage primaryStage) throws IOException {
        DatabaseHandler.DBConstructor();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage = primaryStage;
        stage.setTitle("login");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        stage.setOnCloseRequest(e -> DatabaseHandler.closeConnection());
    }

    public static void main(String[] args) {
        launch();
    }

    /**
     * Resets the values of the public static variables in case the user refreshes the scene in a single instance
     */
    public static void resetValues() {
        priceInsurance = 0;
        priceBooking = 0;
        priceTotal = 0;
        startWeekNumber = 0;
        endWeekNumber = 0;
        totalWeek = 0;
        startWeekString = "";
        endWeekString = "";
    }

    /**
     * Converts a week number to a date with the format yyyy/MM/dd
     *
     * @param weekNumber the week number to be converted
     * @return the date of the week number
     */
    public static String formatWeekNumberToDate(int weekNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date ninetyDeadlineDate = calendar.getTime();
        return sdf.format(ninetyDeadlineDate);
    }

    /**
     * Converts a date with the format yyyy/MM/dd to a week number
     * Dates that are after the current year will return a number that is 52 values higher than the actual week number
     * Dates that are before the current year would return a negative value,
     * as you cannot book something in the past and the system does not provide a way to do so
     * <p>
     * For example, the current year is 2022:
     * If the date is 2022/01/01, the week number will be 1
     * If the date is 2023/01/01, the week number will be 53
     *
     * @param date the date to be converted
     * @return the week number of the date
     */
    public static int formatDateToWeekNumber(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int currentYear = LocalDate.now().getYear();
        int calendarYear = calendar.get(Calendar.YEAR);
        int yearDifference = calendarYear - currentYear;

        return calendar.get(Calendar.WEEK_OF_YEAR) + yearDifference * 52;
    }
}