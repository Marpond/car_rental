module carrental.car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens carrental.car_rental to javafx.fxml;
    exports carrental.car_rental;
}