module com.bcourt {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Membuka package utama untuk JavaFX
    opens com.bcourt to javafx.fxml;

    // Membuka package Controller Admin
    opens com.bcourt.admin.controller to javafx.fxml;

    // Export package utama
    exports com.bcourt;
}
