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
    public static int startWeek;
    public static int endWeek;
    public static int totalWeek;
    public static final int currentWeekNumber = LocalDate.now().get(WeekFields.ISO.weekOfWeekBasedYear());
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");

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

    public static void resetValues() {
        priceInsurance = 0.0;
        priceBooking = 0.0;
        priceTotal = 0.0;
        startWeek = 0;
        endWeek = 0;
        totalWeek = 0;
    }

    public static String formatWeekNumberToDate(int weekNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        Date ninetyDeadlineDate = calendar.getTime();
        return sdf.format(ninetyDeadlineDate);
    }

    public static int formatDateToWeekNumber(String date) {
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(sdf.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}