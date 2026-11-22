
package com.bcourt.controller;

import com.bcourt.App;
import com.bcourt.model.Rental;

import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class JadwalKasirController {

    @FXML private Label totalScheduleLabel;
    @FXML private Label pendingScheduleLabel;
    @FXML private Label feedbackLabel;

    @FXML private TableView<Rental> scheduleTable;

    @FXML private TableColumn<Rental, String> codeColumn;
    @FXML private TableColumn<Rental, String> customerColumn;
    @FXML private TableColumn<Rental, String> courtColumn;
    @FXML private TableColumn<Rental, LocalDate> dateColumn;
    @FXML private TableColumn<Rental, String> timeColumn;
    @FXML private TableColumn<Rental, String> statusColumn;

    @FXML
    public void initialize() {

        codeColumn.setCellValueFactory(
            new PropertyValueFactory<>("code"));

        customerColumn.setCellValueFactory(
            new PropertyValueFactory<>("customer"));

        courtColumn.setCellValueFactory(
            new PropertyValueFactory<>("court"));

        dateColumn.setCellValueFactory(
            new PropertyValueFactory<>("date"));

        timeColumn.setCellValueFactory(data ->
            new javafx.beans.property.SimpleStringProperty(
                data.getValue().getStartTime()
                + " - "
                + data.getValue().getEndTime()
            )
        );

        statusColumn.setCellValueFactory(
            new PropertyValueFactory<>("status"));

        scheduleTable.setItems(
            DashboardKasirController.getScheduleData()
        );

        updateScheduleSummary();
    }

    private void updateScheduleSummary() {

        ObservableList<Rental> data =
            DashboardKasirController.getScheduleData();

        totalScheduleLabel.setText(
            data.size() + " Jadwal"
        );

        long pending = data.stream()
            .filter(rental -> "Pending".equals(rental.getStatus()))
            .count();

        pendingScheduleLabel.setText(
            pending + " Jadwal"
        );
    }

    @FXML
    private void backToDashboard() {

        try {
            App.setRoot("dashboard-user");
        } catch (IOException e) {
            showError("Gagal kembali ke Dashboard: " + e.getMessage());
        }
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi kesalahan");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
