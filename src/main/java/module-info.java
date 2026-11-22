module com.bcourt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.bcourt to javafx.fxml;
    opens com.bcourt.admin.controller to javafx.fxml;
    opens com.bcourt.controller to javafx.fxml;
    opens com.bcourt.model to javafx.base;

    exports com.bcourt;
    exports com.bcourt.controller;
    exports com.bcourt.model;
    exports com.bcourt.dao;
    exports com.bcourt.util;
}
