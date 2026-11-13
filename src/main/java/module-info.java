module com.bcourt {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.bcourt to javafx.fxml;
    exports com.bcourt;
}
