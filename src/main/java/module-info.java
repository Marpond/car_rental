module carrental.car_rental {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.microsoft.sqlserver.jdbc;
    requires junit;


    opens carrental.car_rental to javafx.fxml;
    exports carrental.car_rental;
}