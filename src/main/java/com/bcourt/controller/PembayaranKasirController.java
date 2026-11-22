package com.bcourt.controller;

import java.io.IOException;
import java.util.Optional;

import com.bcourt.App;
import com.bcourt.model.Rental;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;


public class PembayaranKasirController {

    @FXML private Label totalPaymentLabel;
    @FXML private Label unpaidPaymentLabel;
    @FXML private Label feedbackLabel;

    @FXML private TableView<Rental> paymentTable;

    @FXML private TableColumn<Rental, String> codeColumn;
    @FXML private TableColumn<Rental, String> customerColumn;
    @FXML private TableColumn<Rental, String> courtColumn;
    @FXML private TableColumn<Rental, java.time.LocalDate> dateColumn;
    @FXML private TableColumn<Rental, String> statusColumn;

    @FXML private TextField codeField;
    @FXML private TextField customerField;
    @FXML private TextField courtField;

    @FXML private ComboBox<String> methodCombo;

    private Rental selectedRental;

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

        statusColumn.setCellValueFactory(
            new PropertyValueFactory<>("status"));

        methodCombo.getItems().setAll(
            "Tunai",
            "QRIS"
        );

        ObservableList<Rental> rentalData =
            DashboardKasirController.getScheduleData();

        paymentTable.setItems(rentalData);

        paymentTable.getSelectionModel()
            .selectedItemProperty()
            .addListener((observable, oldValue, newValue) -> {

                selectedRental = newValue;

                if (newValue != null) {
                    codeField.setText(newValue.getCode());
                    customerField.setText(newValue.getCustomer());
                    courtField.setText(newValue.getCourt());

                    methodCombo.getSelectionModel().clearSelection();

                    if ("Pending".equals(newValue.getStatus())) {
                        feedbackLabel.setText(
                            "Transaksi " + newValue.getCode() +
                            " siap dicatat pembayarannya."
                        );
                    } else {
                        feedbackLabel.setText(
                            "Status transaksi: " + newValue.getStatus()
                        );
                    }

                } else {
                    clearDetail();
                }
            });

        updatePaymentSummary();
    }

    private void updatePaymentSummary() {

        ObservableList<Rental> data =
            DashboardKasirController.getScheduleData();

        totalPaymentLabel.setText(
            data.size() + " Transaksi"
        );

        long unpaidCount = data.stream()
            .filter(rental -> "Pending".equals(rental.getStatus()))
            .count();

        unpaidPaymentLabel.setText(
            unpaidCount + " Transaksi"
        );

        paymentTable.refresh();
    }

    @FXML
    private void confirmPayment() {

        if (selectedRental == null) {
            showWarning("Silakan pilih transaksi terlebih dahulu.");
            return;
        }

        if (!"Pending".equals(selectedRental.getStatus())) {
            showWarning(
                "Transaksi ini sudah dicatat atau tidak lagi menunggu pembayaran."
            );
            return;
        }

        String method = methodCombo.getValue();

        if (method == null) {
            showWarning("Silakan pilih metode pembayaran.");
            return;
        }

        Alert confirmation = new Alert(Alert.AlertType.CONFIRMATION);
        confirmation.setTitle("Pencatatan Pembayaran");
        confirmation.setHeaderText("Periksa pembayaran pelanggan");

        confirmation.setContentText(
            "Kode Transaksi: " + selectedRental.getCode() +
            "\nPelanggan: " + selectedRental.getCustomer() +
            "\nLapangan: " + selectedRental.getCourt() +
            "\nMetode Pembayaran: " + method +
            "\n\nPastikan pembayaran sudah benar-benar diterima."
        );

        Optional<ButtonType> result = confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            // Kasir tidak mengubah status menjadi Lunas.
            // Status menunggu verifikasi Admin.
            selectedRental.setStatus("Menunggu Verifikasi");

            paymentTable.refresh();
            updatePaymentSummary();

            feedbackLabel.setText(
                "Pembayaran " + selectedRental.getCode() +
                " dicatat menggunakan " + method +
                ". Menunggu verifikasi Admin."
            );

            Alert success = new Alert(Alert.AlertType.INFORMATION);
            success.setTitle("Pembayaran Berhasil Dicatat");
            success.setHeaderText("Pencatatan pembayaran berhasil");

            success.setContentText(
                "Kode Transaksi: " + selectedRental.getCode() +
                "\nPelanggan: " + selectedRental.getCustomer() +
                "\nMetode: " + method +
                "\nStatus: Menunggu Verifikasi Admin" +
                "\n\nKasir telah mencatat pembayaran." +
                "\nAdmin perlu melakukan verifikasi akhir." +
                "\n\nData masih tersimpan sementara di aplikasi."
            );

            success.showAndWait();

            StrukKasirController.setTransactionData(
                selectedRental,
                method
            );

            try {
                App.setRoot("struk-pembayaran");
            } catch (IOException e) {
                showError("Gagal membuka struk: " + e.getMessage());
            }
                    }
                }

    @FXML
    private void resetSelection() {

        paymentTable.getSelectionModel().clearSelection();
        selectedRental = null;
        clearDetail();

        feedbackLabel.setText(
            "Pilihan transaksi berhasil direset."
        );
    }

    private void clearDetail() {

        codeField.clear();
        customerField.clear();
        courtField.clear();

        methodCombo.getSelectionModel().clearSelection();
    }

    @FXML
    private void backToDashboard() {

        try {
            App.setRoot("dashboard-user");
        } catch (IOException e) {
            showError(
                "Gagal kembali ke Dashboard: " + e.getMessage()
            );
        }
    }

    private void showWarning(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Peringatan");
        alert.setHeaderText("Pembayaran belum dapat diproses");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi kesalahan");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
