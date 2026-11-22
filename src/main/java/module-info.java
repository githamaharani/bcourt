module com.bcourt {

    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    // Package utama
    opens com.bcourt to javafx.fxml;

    // Controller Admin
    opens com.bcourt.admin.controller to javafx.fxml;

    // Controller Kasir
    opens com.bcourt.controller to javafx.fxml;

    // Model untuk JavaFX
    opens com.bcourt.model to javafx.base;

    // Export package
    exports com.bcourt;
    exports com.bcourt.controller;
    exports com.bcourt.model;
    exports com.bcourt.dao;
}