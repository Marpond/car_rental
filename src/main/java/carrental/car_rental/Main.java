package carrental.car_rental;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public static Stage stage;

    public static double priceInsurance;
    public static double priceBooking;
    public static double priceTotal;
    public static int startWeek;
    public static int endWeek;
    public static int totalWeek;

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
}