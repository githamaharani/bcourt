
package com.bcourt.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Locale;
import java.util.regex.Pattern;

import com.bcourt.App;
import com.bcourt.model.Rental;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class TransaksiKasirController {

    // Tahapan halaman
    @FXML private VBox step1Box;
    @FXML private VBox step2Box;
    @FXML private VBox step3Box;

    @FXML private Label step1Label;
    @FXML private Label step2Label;
    @FXML private Label step3Label;

    // Data pelanggan
    @FXML private TextField customerField;
    @FXML private TextField phoneField;

    // Data lapangan
    @FXML private ComboBox<String> courtCombo;
    @FXML private ComboBox<String> gameTypeCombo;
    @FXML private DatePicker datePicker;
    @FXML private ComboBox<String> startTimeCombo;
    @FXML private ComboBox<String> endTimeCombo;
    @FXML private Label courtPriceLabel;

    @FXML private TextArea noteField;
    @FXML private Label feedbackLabel;

    // Peralatan
    @FXML private CheckBox racketCheck;
    @FXML private CheckBox shuttleCheck;
    @FXML private TextField racketQtyField;
    @FXML private TextField shuttleQtyField;

    // Ringkasan
    @FXML private Label summaryCustomerLabel;
    @FXML private Label summaryPhoneLabel;
    @FXML private Label summaryCourtLabel;
    @FXML private Label summaryGameLabel;
    @FXML private Label summaryDateLabel;
    @FXML private Label summaryTimeLabel;
    @FXML private Label summaryDurationLabel;

    @FXML private Label courtCostLabel;
    @FXML private Label equipmentCostLabel;
    @FXML private Label totalCostLabel;

    // Pembayaran
    @FXML private RadioButton cashRadio;
    @FXML private RadioButton qrisRadio;

    private final ToggleGroup paymentGroup = new ToggleGroup();

    private static final int HARGA_TUNGGAL = 30000;
    private static final int HARGA_GANDA = 45000;
    private static final int HARGA_RAKET = 15000;
    private static final int HARGA_SHUTTLECOCK = 25000;

    private int durasi;
    private int biayaLapangan;
    private int biayaPeralatan;
    private int totalBiaya;

    private int jumlahRaket;
    private int jumlahShuttlecock;

    private String metodePembayaran = "";

    private final NumberFormat rupiah =
        NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    @FXML
    public void initialize() {

        courtCombo.setItems(FXCollections.observableArrayList(
            "Lapangan 1",
            "Lapangan 2",
            "Lapangan 3"
        ));

        gameTypeCombo.setItems(FXCollections.observableArrayList(
            "Tunggal",
            "Ganda"
        ));

        startTimeCombo.setItems(FXCollections.observableArrayList(
            "08:00", "09:00", "10:00",
            "11:00", "12:00", "13:00",
            "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00",
            "20:00", "21:00"
        ));

        endTimeCombo.setItems(FXCollections.observableArrayList(
            "09:00", "10:00", "11:00",
            "12:00", "13:00", "14:00",
            "15:00", "16:00", "17:00",
            "18:00", "19:00", "20:00",
            "21:00", "22:00"
        ));

        datePicker.setValue(LocalDate.now());

        datePicker.setDayCellFactory(picker ->
            new DateCell() {
                @Override
                public void updateItem(LocalDate date, boolean empty) {
                    super.updateItem(date, empty);

                    if (date.isBefore(LocalDate.now())) {
                        setDisable(true);
                    }
                }
            }
        );

        cashRadio.setToggleGroup(paymentGroup);
        qrisRadio.setToggleGroup(paymentGroup);

        gameTypeCombo.setOnAction(event -> updateCourtPrice());

        updateCourtPrice();
        showStep(1);
    }

    private String formatRupiah(int amount) {
        return rupiah.format(amount).replace(",00", "");
    }

    private void updateCourtPrice() {

        String gameType = gameTypeCombo.getValue();

        if ("Tunggal".equals(gameType)) {
            courtPriceLabel.setText(
                formatRupiah(HARGA_TUNGGAL) + " / jam"
            );

        } else if ("Ganda".equals(gameType)) {
            courtPriceLabel.setText(
                formatRupiah(HARGA_GANDA) + " / jam"
            );

        } else {
            courtPriceLabel.setText("Pilih jenis permainan");
        }
    }

    private void showStep(int step) {

        step1Box.setVisible(step == 1);
        step1Box.setManaged(step == 1);

        step2Box.setVisible(step == 2);
        step2Box.setManaged(step == 2);

        step3Box.setVisible(step == 3);
        step3Box.setManaged(step == 3);

        step1Label.setStyle(step == 1
            ? "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #F15A00;"
            : "-fx-font-size: 13px; -fx-text-fill: #A58B7D;");

        step2Label.setStyle(step == 2
            ? "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #F15A00;"
            : "-fx-font-size: 13px; -fx-text-fill: #A58B7D;");

        step3Label.setStyle(step == 3
            ? "-fx-font-size: 13px; -fx-font-weight: bold; -fx-text-fill: #F15A00;"
            : "-fx-font-size: 13px; -fx-text-fill: #A58B7D;");
    }

    @FXML
    private void nextToEquipment() {

        if (!validateCustomerAndCourt()) {
            return;
        }

        showStep(2);
        feedbackLabel.setText(
            "Silakan pilih peralatan tambahan atau lewati tahap ini."
        );
    }

    @FXML
    private void backToCourt() {
        showStep(1);
    }

    @FXML
    private void skipEquipment() {

        racketCheck.setSelected(false);
        shuttleCheck.setSelected(false);

        prepareSummary();
    }

    @FXML
    private void nextToSummary() {

        if (!validateEquipment()) {
            return;
        }

        prepareSummary();
    }

    @FXML
    private void backToEquipment() {
        showStep(2);
    }

    private boolean validateCustomerAndCourt() {

        String customer = customerField.getText().trim();
        String phone = phoneField.getText().trim();

        if (customer.isEmpty()) {
            showWarning("Nama pelanggan wajib diisi.");
            customerField.requestFocus();
            return false;
        }

        if (!Pattern.matches("[0-9]{10,15}", phone)) {
            showWarning(
                "Nomor WhatsApp harus berisi 10 sampai 15 angka."
            );
            phoneField.requestFocus();
            return false;
        }

        if (courtCombo.getValue() == null) {
            showWarning("Silakan pilih lapangan.");
            courtCombo.requestFocus();
            return false;
        }

        if (gameTypeCombo.getValue() == null) {
            showWarning(
                "Silakan pilih jenis permainan Tunggal atau Ganda."
            );
            gameTypeCombo.requestFocus();
            return false;
        }

        if (datePicker.getValue() == null ||
            datePicker.getValue().isBefore(LocalDate.now())) {

            showWarning("Tanggal penyewaan tidak valid.");
            return false;
        }

        if (startTimeCombo.getValue() == null ||
            endTimeCombo.getValue() == null) {

            showWarning(
                "Silakan pilih jam mulai dan jam selesai."
            );
            return false;
        }

        LocalTime start = LocalTime.parse(startTimeCombo.getValue());
        LocalTime end = LocalTime.parse(endTimeCombo.getValue());

        if (!end.isAfter(start)) {
            showWarning(
                "Jam selesai harus lebih besar dari jam mulai."
            );
            return false;
        }

        long hours = Duration.between(start, end).toHours();

        if (hours <= 0) {
            showWarning(
                "Durasi penyewaan minimal satu jam."
            );
            return false;
        }

        return true;
    }

    private boolean validateEquipment() {

        jumlahRaket = 0;
        jumlahShuttlecock = 0;

        if (racketCheck.isSelected()) {

            try {
                jumlahRaket = Integer.parseInt(
                    racketQtyField.getText().trim()
                );

                if (jumlahRaket <= 0) {
                    showWarning("Jumlah raket minimal 1.");
                    return false;
                }

            } catch (NumberFormatException e) {
                showWarning("Jumlah raket harus berupa angka.");
                return false;
            }
        }

        if (shuttleCheck.isSelected()) {

            try {
                jumlahShuttlecock = Integer.parseInt(
                    shuttleQtyField.getText().trim()
                );

                if (jumlahShuttlecock <= 0) {
                    showWarning("Jumlah shuttlecock minimal 1.");
                    return false;
                }

            } catch (NumberFormatException e) {
                showWarning(
                    "Jumlah shuttlecock harus berupa angka."
                );
                return false;
            }
        }

        return true;
    }

    private void prepareSummary() {

        LocalTime start = LocalTime.parse(startTimeCombo.getValue());
        LocalTime end = LocalTime.parse(endTimeCombo.getValue());

        durasi = (int) Duration.between(start, end).toHours();

        if ("Tunggal".equals(gameTypeCombo.getValue())) {
            biayaLapangan = HARGA_TUNGGAL * durasi;
        } else {
            biayaLapangan = HARGA_GANDA * durasi;
        }

        jumlahRaket = racketCheck.isSelected()
            ? Integer.parseInt(racketQtyField.getText().trim()) : 0;

        jumlahShuttlecock = shuttleCheck.isSelected()
            ? Integer.parseInt(shuttleQtyField.getText().trim()) : 0;

        biayaPeralatan =
            (jumlahRaket * HARGA_RAKET) +
            (jumlahShuttlecock * HARGA_SHUTTLECOCK);

        totalBiaya = biayaLapangan + biayaPeralatan;

        summaryCustomerLabel.setText(
            customerField.getText().trim()
        );

        summaryPhoneLabel.setText(
            phoneField.getText().trim()
        );

        summaryCourtLabel.setText(courtCombo.getValue());
        summaryGameLabel.setText(gameTypeCombo.getValue());

        summaryDateLabel.setText(
            datePicker.getValue().toString()
        );

        summaryTimeLabel.setText(
            startTimeCombo.getValue() + " - " +
            endTimeCombo.getValue()
        );

        summaryDurationLabel.setText(durasi + " jam");

        courtCostLabel.setText(
            "Sewa lapangan (" + durasi + " jam): " +
            formatRupiah(biayaLapangan)
        );

        String equipmentDetails = "";

        if (jumlahRaket > 0) {
            equipmentDetails +=
                "Raket " + jumlahRaket + " buah";
        }

        if (jumlahShuttlecock > 0) {

            if (!equipmentDetails.isEmpty()) {
                equipmentDetails += ", ";
            }

            equipmentDetails +=
                "Shuttlecock " + jumlahShuttlecock + " tabung";
        }

        if (equipmentDetails.isEmpty()) {
            equipmentDetails =
                "Tidak ada peralatan tambahan";
        }

        equipmentCostLabel.setText(
            equipmentDetails + " : " +
            formatRupiah(biayaPeralatan)
        );

        totalCostLabel.setText(formatRupiah(totalBiaya));

        showStep(3);

        feedbackLabel.setText(
            "Periksa kembali pesanan sebelum menyimpan transaksi."
        );
    }

    // ==========================================
    // SIMPAN TRANSAKSI DAN ARAHKAN PEMBAYARAN
    // ==========================================

    @FXML
    private void confirmRental() {

        if (paymentGroup.getSelectedToggle() == null) {
            showWarning("Silakan pilih metode pembayaran.");
            return;
        }

        metodePembayaran =
            cashRadio.isSelected() ? "Tunai" : "QRIS";

        String customer = customerField.getText().trim();
        String phone = phoneField.getText().trim();

        String court = courtCombo.getValue() +
            " - " + gameTypeCombo.getValue();

        String startTime = startTimeCombo.getValue();
        String endTime = endTimeCombo.getValue();

        String equipmentNote =
            "Peralatan: Raket " + jumlahRaket +
            " buah, Shuttlecock " + jumlahShuttlecock +
            " tabung.";

        String paymentNote =
            "Metode pembayaran: " + metodePembayaran +
            ". Total biaya: " + formatRupiah(totalBiaya) +
            ". " + equipmentNote;

        String originalNote = noteField.getText().trim();

        if (!originalNote.isEmpty()) {
            paymentNote += " Catatan: " + originalNote;
        }

        // Kode transaksi dibuat satu kali
        String transactionCode =
            "TRX-" + System.currentTimeMillis();

        // Membuat data Rental
        Rental rental = new Rental(
            transactionCode,
            customer,
            phone,
            court,
            datePicker.getValue(),
            startTime,
            endTime,
            paymentNote,
            "Pending",
            biayaLapangan,
            biayaPeralatan,
            totalBiaya
        );

        // Menyimpan transaksi ke daftar sementara Dashboard
        DashboardKasirController.addRental(rental);

        // ======================================
        // JIKA METODE PEMBAYARAN QRIS
        // ======================================

        if ("QRIS".equals(metodePembayaran)) {

            try {

                QrisKasirController.setTransactionData(rental);

                App.setRoot("qris-pembayaran");

            } catch (Exception e) {

                showError(
                    "Gagal membuka halaman QRIS: " +
                    e.getMessage()
                );

                e.printStackTrace();
            }

            return;
        }

        // ======================================
        // JIKA METODE PEMBAYARAN TUNAI
        // ======================================

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Transaksi Berhasil");
        alert.setHeaderText("Transaksi Berhasil Dicatat");

        alert.setContentText(
            "Kode Transaksi: " + transactionCode +
            "\nPelanggan: " + customer +
            "\nLapangan: " + court +
            "\nTanggal: " + datePicker.getValue() +
            "\nWaktu: " + startTime + " - " + endTime +
            "\nDurasi: " + durasi + " jam" +
            "\nBiaya Lapangan: " + formatRupiah(biayaLapangan) +
            "\nBiaya Peralatan: " + formatRupiah(biayaPeralatan) +
            "\nTotal: " + formatRupiah(totalBiaya) +
            "\nMetode Pembayaran: Tunai" +
            "\nStatus: Pending" +
            "\n\nData masih tersimpan sementara di aplikasi, belum ke MySQL."
        );

        alert.showAndWait();

        try {

            App.setRoot("dashboard-user");

        } catch (IOException e) {

            showError(
                "Transaksi tersimpan, tetapi gagal kembali ke Dashboard: " +
                e.getMessage()
            );
        }
    }

    @FXML
    private void backToDashboard() {

        try {

            App.setRoot("dashboard-user");

        } catch (IOException e) {

            showError(
                "Gagal kembali ke Dashboard: " +
                e.getMessage()
            );
        }
    }

    private void showWarning(String message) {

        Alert alert = new Alert(Alert.AlertType.WARNING);

        alert.setTitle("Peringatan");
        alert.setHeaderText("Periksa kembali data");
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
