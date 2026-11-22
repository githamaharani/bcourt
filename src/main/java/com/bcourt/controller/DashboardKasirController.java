
package com.bcourt.controller;

import com.bcourt.model.Rental;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class DashboardKasirController {

    @FXML private Label totalRentalLabel;
    @FXML private Label revenueLabel;
    @FXML private Label pendingPaymentLabel;
    @FXML private Label availableCourtLabel;
    @FXML private Label feedbackLabel;

    @FXML private TableView<RentalRow> recentRentalTable;
    @FXML private TableColumn<RentalRow, String> codeColumn;
    @FXML private TableColumn<RentalRow, String> customerColumn;
    @FXML private TableColumn<RentalRow, String> courtColumn;
    @FXML private TableColumn<RentalRow, String> timeColumn;
    @FXML private TableColumn<RentalRow, String> amountColumn;
    @FXML private TableColumn<RentalRow, String> statusColumn;

    // Data untuk tabel Dashboard
    private static final ObservableList<RentalRow> rentalData =
            FXCollections.observableArrayList();

    // Data transaksi asli untuk halaman Jadwal Lapangan
    private static final ObservableList<Rental> rentalObjects =
            FXCollections.observableArrayList();

    @FXML
    public void initialize() {

        codeColumn.setCellValueFactory(
            new PropertyValueFactory<>("code"));

        customerColumn.setCellValueFactory(
            new PropertyValueFactory<>("customer"));

        courtColumn.setCellValueFactory(
            new PropertyValueFactory<>("court"));

        timeColumn.setCellValueFactory(
            new PropertyValueFactory<>("time"));

        amountColumn.setCellValueFactory(
            new PropertyValueFactory<>("amount"));

        statusColumn.setCellValueFactory(
            new PropertyValueFactory<>("status"));

        // Data contoh hanya dimasukkan sekali
        if (rentalData.isEmpty()) {

            rentalData.addAll(
                new RentalRow("TRX-001", "Andi Pratama",
                    "Lapangan 1", "10.00 - 12.00",
                    "Rp270.000", "Lunas"),

                new RentalRow("TRX-002", "Budi Santoso",
                    "Lapangan 2", "13.00 - 15.00",
                    "Rp220.000", "Pending"),

                new RentalRow("TRX-003", "Made Wirawan",
                    "Lapangan 3", "15.00 - 17.00",
                    "Rp180.000", "Lunas")
            );
        }

        recentRentalTable.setItems(rentalData);

        updateDashboard();
    }

    // Menambahkan transaksi baru ke Dashboard dan Jadwal
    public static void addRental(Rental rental) {

        String time = rental.getStartTime()
                + " - " + rental.getEndTime();

        // Masukkan ke tabel Dashboard
        rentalData.add(0, new RentalRow(
            rental.getCode(),
            rental.getCustomer(),
            rental.getCourt(),
            time,
            "Belum dihitung",
            rental.getStatus()
        ));

        // Masukkan objek Rental ke daftar Jadwal
        rentalObjects.add(0, rental);
    }

    // Mengambil data transaksi untuk halaman Jadwal
    public static ObservableList<Rental> getScheduleData() {
        return rentalObjects;
    }

    // Memperbarui ringkasan Dashboard
    private void updateDashboard() {

        totalRentalLabel.setText(
            rentalData.size() + " Transaksi"
        );

        long pendingCount = rentalData.stream()
            .filter(item -> "Pending".equals(item.getStatus()))
            .count();

        pendingPaymentLabel.setText(
            pendingCount + " Transaksi"
        );

        revenueLabel.setText("Rp450.000");
        availableCourtLabel.setText("2 Lapangan");
    }

    @FXML
    private void showDashboard() {
        notify("Kamu berada di Dashboard Kasir.");
    }

    @FXML
    private void openTransactions() {

        try {
            com.bcourt.App.setRoot("transaksi-penyewaan");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            notify("Gagal membuka halaman Transaksi Penyewaan.");
        }
    }

    @FXML
    private void openSchedule() {

        try {
            com.bcourt.App.setRoot("jadwal-lapangan");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            notify("Gagal membuka halaman Jadwal Lapangan.");
        }
    }

    @FXML
    private void openPayments() {
        try {
            com.bcourt.App.setRoot("pembayaran-kasir");
        } catch (java.io.IOException e) {
            e.printStackTrace();
            notify("Gagal membuka halaman Pembayaran.");
        }
    }

    @FXML
    private void openHistory() {
        notify("Menu Riwayat Transaksi dipilih.");
    }

    @FXML
    private void openProfile() {
        notify("Menu Profil Saya dipilih.");
    }

    @FXML
    private void createRental() {
        openTransactions();
    }

    @FXML
    private void logout() {
        notify("Tombol Logout dipilih.");
    }

    private void notify(String message) {
        feedbackLabel.setText(message);
    }

    // Model khusus untuk baris tabel Dashboard
    public static class RentalRow {

        private final String code;
        private final String customer;
        private final String court;
        private final String time;
        private final String amount;
        private final String status;

        public RentalRow(String code, String customer,
                         String court, String time,
                         String amount, String status) {

            this.code = code;
            this.customer = customer;
            this.court = court;
            this.time = time;
            this.amount = amount;
            this.status = status;
        }

        public String getCode() { return code; }
        public String getCustomer() { return customer; }
        public String getCourt() { return court; }
        public String getTime() { return time; }
        public String getAmount() { return amount; }
        public String getStatus() { return status; }
    }
}
