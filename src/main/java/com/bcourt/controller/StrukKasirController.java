package com.bcourt.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.bcourt.App;
import com.bcourt.dao.PembayaranDAO;
import com.bcourt.model.Rental;

import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StrukKasirController {

    @FXML private VBox receiptBox;

    @FXML private Label codeLabel;
    @FXML private Label customerLabel;
    @FXML private Label phoneLabel;
    @FXML private Label courtLabel;
    @FXML private Label dateLabel;
    @FXML private Label timeLabel;
    @FXML private Label methodLabel;
    @FXML private Label statusLabel;

    @FXML private Label courtCostLabel;
    @FXML private Label equipmentCostLabel;
    @FXML private Label totalCostLabel;

    private static Rental selectedRental;
    private static String selectedMethod;

    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();

    private final NumberFormat rupiah =
            NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void setTransactionData(Rental rental, String method) {
        selectedRental = rental;
        selectedMethod = method;
    }

    @FXML
    public void initialize() {

        if (selectedRental == null) {
            showError("Data transaksi tidak ditemukan.");
            return;
        }

        codeLabel.setText(selectedRental.getCode());
        customerLabel.setText(selectedRental.getCustomer());
        phoneLabel.setText(selectedRental.getPhone());
        courtLabel.setText(selectedRental.getCourt());

        dateLabel.setText(
                selectedRental.getDate().toString()
        );

        timeLabel.setText(
                selectedRental.getStartTime() + " - "
                        + selectedRental.getEndTime()
        );

        methodLabel.setText(
                selectedMethod != null ? selectedMethod : "-"
        );

        String statusDatabase = getStatusDatabase();

        if (statusDatabase != null) {
            statusLabel.setText(statusDatabase);
            selectedRental.setStatus(statusDatabase);
        } else {
            statusLabel.setText(selectedRental.getStatus());
        }

        courtCostLabel.setText(
                formatRupiah(selectedRental.getBiayaLapangan())
        );

        equipmentCostLabel.setText(
                formatRupiah(selectedRental.getBiayaPeralatan())
        );

        totalCostLabel.setText(
                formatRupiah(selectedRental.getTotalBiaya())
        );
    }

    private String getStatusDatabase() {

        String code = selectedRental.getCode();

        if (code == null || !code.startsWith("TRX-")) {
            return null;
        }

        try {
            int idSewa = Integer.parseInt(code.substring(4));

            return pembayaranDAO.getStatusPembayaran(idSewa);

        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String formatRupiah(int amount) {
        return rupiah.format(amount).replace(",00", "");
    }

    @FXML
    private void printReceipt() {

        if (receiptBox == null || receiptBox.getScene() == null) {
            showError("Struk belum siap untuk dicetak.");
            return;
        }

        PrinterJob job = PrinterJob.createPrinterJob();

        if (job == null) {
            showError("Printer tidak tersedia pada perangkat ini.");
            return;
        }

        boolean proceed = job.showPrintDialog(
                receiptBox.getScene().getWindow()
        );

        if (proceed) {

            boolean printed = job.printPage(receiptBox);

            if (printed) {

                boolean completed = job.endJob();

                if (completed) {
                    showInfo("Struk berhasil dikirim ke printer.");
                } else {
                    showError("Proses pencetakan gagal diselesaikan.");
                }

            } else {
                job.cancelJob();
                showError("Struk gagal dicetak.");
            }

        } else {
            job.cancelJob();
        }
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
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private void showInfo(String message) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Informasi");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}
