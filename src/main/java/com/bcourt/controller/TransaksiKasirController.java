package com.bcourt.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Pattern;

import com.bcourt.App;
import com.bcourt.dao.LapanganDAO;
import com.bcourt.dao.PelangganDAO;
import com.bcourt.dao.PeralatanDAO;
import com.bcourt.dao.SewaDAO;
import com.bcourt.model.Lapangan;
import com.bcourt.model.Pelanggan;
import com.bcourt.model.Peralatan;
import com.bcourt.model.Rental;
import com.bcourt.util.UserSession;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

public class TransaksiKasirController {

    @FXML private VBox step1Box;
    @FXML private VBox step2Box;
    @FXML private VBox step3Box;

    @FXML private Label step1Label;
    @FXML private Label step2Label;
    @FXML private Label step3Label;

    @FXML private TextField customerField;
    @FXML private TextField phoneField;

    @FXML private ComboBox<String> courtCombo;
    @FXML private ComboBox<String> gameTypeCombo;
    @FXML private javafx.scene.control.DatePicker datePicker;
    @FXML private ComboBox<String> startTimeCombo;
    @FXML private ComboBox<String> endTimeCombo;
    @FXML private Label courtPriceLabel;

    @FXML private TextArea noteField;
    @FXML private Label feedbackLabel;

    @FXML private CheckBox racketCheck;
    @FXML private CheckBox shuttleCheck;
    @FXML private TextField racketQtyField;
    @FXML private TextField shuttleQtyField;

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

    @FXML private RadioButton cashRadio;
    @FXML private RadioButton qrisRadio;

    private final PelangganDAO pelangganDAO = new PelangganDAO();
    private final LapanganDAO lapanganDAO = new LapanganDAO();
    private final PeralatanDAO peralatanDAO = new PeralatanDAO();
    private final SewaDAO sewaDAO = new SewaDAO();

    private List<Lapangan> daftarLapangan = new ArrayList<>();
    private List<Peralatan> daftarPeralatan = new ArrayList<>();

    private Lapangan lapanganTerpilih;
    private Peralatan raketTerpilih;
    private Peralatan shuttlecockTerpilih;

    private final ToggleGroup paymentGroup = new ToggleGroup();

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

        cashRadio.setToggleGroup(paymentGroup);
        qrisRadio.setToggleGroup(paymentGroup);

        datePicker.setValue(LocalDate.now());

        datePicker.setDayCellFactory(picker ->
                new DateCell() {
                    @Override
                    public void updateItem(LocalDate date, boolean empty) {
                        super.updateItem(date, empty);

                        if (!empty && date.isBefore(LocalDate.now())) {
                            setDisable(true);
                        }
                    }
                }
        );

        isiPilihanWaktu();

        gameTypeCombo.setItems(
                FXCollections.observableArrayList("Tunggal", "Ganda")
        );

        gameTypeCombo.setOnAction(event -> {
            isiPilihanLapangan();
            updateCourtPrice();
        });

        courtCombo.setOnAction(event -> updateCourtPrice());

        racketCheck.setOnAction(event -> {
            racketQtyField.setDisable(!racketCheck.isSelected());

            if (!racketCheck.isSelected()) {
                racketQtyField.setText("");
            }
        });

        shuttleCheck.setOnAction(event -> {
            shuttleQtyField.setDisable(!shuttleCheck.isSelected());

            if (!shuttleCheck.isSelected()) {
                shuttleQtyField.setText("");
            }
        });

        racketQtyField.setDisable(true);
        shuttleQtyField.setDisable(true);

        loadDataDatabase();

        showStep(1);
    }

    private void loadDataDatabase() {

        daftarLapangan = lapanganDAO.getLapanganTersedia();
        daftarPeralatan = peralatanDAO.getPeralatanTersedia();

        raketTerpilih = cariPeralatan("raket");
        shuttlecockTerpilih = cariPeralatan("shuttle");

        isiPilihanLapangan();

        if (daftarLapangan.isEmpty()) {
            feedbackLabel.setText(
                    "Belum ada lapangan tersedia di database."
            );
        }

        if (daftarPeralatan.isEmpty()) {
            feedbackLabel.setText(
                    "Data peralatan belum tersedia di database."
            );
        }
    }

    private void isiPilihanLapangan() {

        String jenis = gameTypeCombo.getValue();

        List<String> namaLapangan = new ArrayList<>();

        for (Lapangan lapangan : daftarLapangan) {

            if (jenis == null ||
                    jenis.equalsIgnoreCase(lapangan.getJenisLapangan())) {

                namaLapangan.add(lapangan.getNamaLapangan());
            }
        }

        courtCombo.setItems(
                FXCollections.observableArrayList(namaLapangan)
        );

        courtCombo.getSelectionModel().clearSelection();
        lapanganTerpilih = null;
    }

    private Peralatan cariPeralatan(String kataKunci) {

        for (Peralatan peralatan : daftarPeralatan) {

            String nama = peralatan.getNamaPeralatan();

            if (nama != null &&
                    nama.toLowerCase(Locale.ROOT)
                            .contains(kataKunci.toLowerCase(Locale.ROOT))) {

                return peralatan;
            }
        }

        return null;
    }

    private Lapangan cariLapangan(String nama) {

        for (Lapangan lapangan : daftarLapangan) {

            if (lapangan.getNamaLapangan().equals(nama)) {
                return lapangan;
            }
        }

        return null;
    }

    private void isiPilihanWaktu() {

        List<String> jamMulai = new ArrayList<>();
        List<String> jamSelesai = new ArrayList<>();

        for (int jam = 8; jam <= 21; jam++) {
            jamMulai.add(String.format("%02d:00", jam));
        }

        for (int jam = 9; jam <= 22; jam++) {
            jamSelesai.add(String.format("%02d:00", jam));
        }

        startTimeCombo.setItems(
                FXCollections.observableArrayList(jamMulai)
        );

        endTimeCombo.setItems(
                FXCollections.observableArrayList(jamSelesai)
        );
    }

    private String formatRupiah(int amount) {
        return rupiah.format(amount).replace(",00", "");
    }

    private void updateCourtPrice() {

        String nama = courtCombo.getValue();

        if (nama == null) {
            courtPriceLabel.setText("Pilih lapangan");
            lapanganTerpilih = null;
            return;
        }

        lapanganTerpilih = cariLapangan(nama);

        if (lapanganTerpilih == null) {
            courtPriceLabel.setText("Lapangan tidak ditemukan");
            return;
        }

        int harga = (int) lapanganTerpilih.getHargaPerJam();

        courtPriceLabel.setText(
                formatRupiah(harga) + " / jam"
        );
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

        racketQtyField.clear();
        shuttleQtyField.clear();

        racketQtyField.setDisable(true);
        shuttleQtyField.setDisable(true);

        jumlahRaket = 0;
        jumlahShuttlecock = 0;

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

        if (gameTypeCombo.getValue() == null) {
            showWarning("Silakan pilih jenis permainan.");
            return false;
        }

        if (courtCombo.getValue() == null || lapanganTerpilih == null) {
            showWarning("Silakan pilih lapangan.");
            return false;
        }

        if (datePicker.getValue() == null ||
                datePicker.getValue().isBefore(LocalDate.now())) {

            showWarning("Tanggal penyewaan tidak valid.");
            return false;
        }

        if (startTimeCombo.getValue() == null ||
                endTimeCombo.getValue() == null) {

            showWarning("Silakan pilih jam mulai dan jam selesai.");
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
            showWarning("Durasi minimal satu jam.");
            return false;
        }

        if (hours > 24) {
            showWarning("Durasi penyewaan tidak valid.");
            return false;
        }

        return true;
    }

    private boolean validateEquipment() {

        jumlahRaket = 0;
        jumlahShuttlecock = 0;

        if (racketCheck.isSelected()) {

            if (raketTerpilih == null) {
                showWarning(
                        "Data peralatan raket tidak ditemukan di database."
                );
                return false;
            }

            try {
                jumlahRaket = Integer.parseInt(
                        racketQtyField.getText().trim()
                );

                if (jumlahRaket <= 0) {
                    showWarning("Jumlah raket minimal 1.");
                    return false;
                }

                if (jumlahRaket > raketTerpilih.getStok()) {
                    showWarning("Stok raket tidak mencukupi.");
                    return false;
                }

            } catch (NumberFormatException e) {
                showWarning("Jumlah raket harus berupa angka.");
                return false;
            }
        }

        if (shuttleCheck.isSelected()) {

            if (shuttlecockTerpilih == null) {
                showWarning(
                        "Data peralatan shuttlecock tidak ditemukan di database."
                );
                return false;
            }

            try {
                jumlahShuttlecock = Integer.parseInt(
                        shuttleQtyField.getText().trim()
                );

                if (jumlahShuttlecock <= 0) {
                    showWarning("Jumlah shuttlecock minimal 1.");
                    return false;
                }

                if (jumlahShuttlecock > shuttlecockTerpilih.getStok()) {
                    showWarning("Stok shuttlecock tidak mencukupi.");
                    return false;
                }

            } catch (NumberFormatException e) {
                showWarning("Jumlah shuttlecock harus berupa angka.");
                return false;
            }
        }

        return true;
    }

    private void prepareSummary() {

        LocalTime start = LocalTime.parse(startTimeCombo.getValue());
        LocalTime end = LocalTime.parse(endTimeCombo.getValue());

        durasi = (int) Duration.between(start, end).toHours();

        biayaLapangan = (int) (
                lapanganTerpilih.getHargaPerJam() * durasi
        );

        jumlahRaket = racketCheck.isSelected()
                ? Integer.parseInt(racketQtyField.getText().trim()) : 0;

        jumlahShuttlecock = shuttleCheck.isSelected()
                ? Integer.parseInt(shuttleQtyField.getText().trim()) : 0;

        biayaPeralatan = 0;

        if (jumlahRaket > 0) {
            biayaPeralatan += (int) (
                    raketTerpilih.getHargaSewa() * jumlahRaket
            );
        }

        if (jumlahShuttlecock > 0) {
            biayaPeralatan += (int) (
                    shuttlecockTerpilih.getHargaSewa()
                            * jumlahShuttlecock
            );
        }

        totalBiaya = biayaLapangan + biayaPeralatan;

        summaryCustomerLabel.setText(customerField.getText().trim());
        summaryPhoneLabel.setText(phoneField.getText().trim());

        summaryCourtLabel.setText(lapanganTerpilih.getNamaLapangan());
        summaryGameLabel.setText(lapanganTerpilih.getJenisLapangan());

        summaryDateLabel.setText(datePicker.getValue().toString());

        summaryTimeLabel.setText(
                startTimeCombo.getValue() + " - "
                        + endTimeCombo.getValue()
        );

        summaryDurationLabel.setText(durasi + " jam");

        courtCostLabel.setText(
                "Sewa lapangan (" + durasi + " jam): "
                        + formatRupiah(biayaLapangan)
        );

        String equipmentDetails = "";

        if (jumlahRaket > 0) {
            equipmentDetails += "Raket " + jumlahRaket + " buah";
        }

        if (jumlahShuttlecock > 0) {

            if (!equipmentDetails.isEmpty()) {
                equipmentDetails += ", ";
            }

            equipmentDetails +=
                    "Shuttlecock " + jumlahShuttlecock + " tabung";
        }

        if (equipmentDetails.isEmpty()) {
            equipmentDetails = "Tidak ada peralatan tambahan";
        }

        equipmentCostLabel.setText(
                equipmentDetails + " : "
                        + formatRupiah(biayaPeralatan)
        );

        totalCostLabel.setText(formatRupiah(totalBiaya));

        showStep(3);

        feedbackLabel.setText(
                "Periksa kembali pesanan sebelum menyimpan transaksi."
        );
    }

    @FXML
    private void confirmRental() {

        if (paymentGroup.getSelectedToggle() == null) {
            showWarning("Silakan pilih metode pembayaran.");
            return;
        }

        if (!UserSession.isLoggedIn()) {
            showError(
                    "Sesi login tidak ditemukan. Silakan login kembali."
            );
            return;
        }

        metodePembayaran = cashRadio.isSelected() ? "Tunai" : "QRIS";

        String nama = customerField.getText().trim();
        String phone = phoneField.getText().trim();

        Pelanggan pelanggan = pelangganDAO.cariPelanggan(phone);

        if (pelanggan == null) {

            boolean berhasil = pelangganDAO.tambahPelanggan(
                    nama,
                    phone
            );

            if (!berhasil) {
                showError("Gagal menyimpan data pelanggan.");
                return;
            }

            pelanggan = pelangganDAO.cariPelanggan(phone);

            if (pelanggan == null) {
                showError(
                        "Pelanggan berhasil ditambahkan, tetapi gagal "
                                + "mengambil ID pelanggan."
                );
                return;
            }
        }

        Map<Integer, Integer> peralatanDipilih = new HashMap<>();

        if (jumlahRaket > 0) {
            peralatanDipilih.put(
                    raketTerpilih.getIdPeralatan(),
                    jumlahRaket
            );
        }

        if (jumlahShuttlecock > 0) {
            peralatanDipilih.put(
                    shuttlecockTerpilih.getIdPeralatan(),
                    jumlahShuttlecock
            );
        }

        String catatan = noteField.getText() == null
                ? ""
                : noteField.getText().trim();

        int idSewa = sewaDAO.simpanTransaksi(
                pelanggan.getIdPelanggan(),
                UserSession.getIdPengguna(),
                lapanganTerpilih.getIdLapangan(),
                datePicker.getValue(),
                LocalTime.parse(startTimeCombo.getValue()),
                LocalTime.parse(endTimeCombo.getValue()),
                catatan,
                peralatanDipilih,
                metodePembayaran
        );

        if (idSewa <= 0) {
            showError(
                    "Transaksi gagal disimpan ke database. "
                            + "Periksa pesan error di Terminal."
            );
            return;
        }

        String transactionCode = "TRX-" + idSewa;

        String court = lapanganTerpilih.getNamaLapangan()
                + " - " + lapanganTerpilih.getJenisLapangan();

        String paymentNote = "Metode pembayaran: "
                + metodePembayaran
                + ". Total biaya: "
                + formatRupiah(totalBiaya);

        Rental rental = new Rental(
                transactionCode,
                nama,
                phone,
                court,
                datePicker.getValue(),
                startTimeCombo.getValue(),
                endTimeCombo.getValue(),
                paymentNote,
                "Pending",
                biayaLapangan,
                biayaPeralatan,
                totalBiaya
        );

        DashboardKasirController.addRental(rental);

        if ("QRIS".equals(metodePembayaran)) {

            try {
                QrisKasirController.setTransactionData(rental);
                App.setRoot("qris-pembayaran");

            } catch (Exception e) {
                showError(
                        "Transaksi sudah tersimpan, tetapi "
                                + "halaman QRIS gagal dibuka: "
                                + e.getMessage()
                );
                e.printStackTrace();
            }

            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Transaksi Berhasil");
        alert.setHeaderText("Transaksi Berhasil Disimpan");

        alert.setContentText(
                "ID Penyewaan: " + idSewa
                        + "\nKode Transaksi: " + transactionCode
                        + "\nPelanggan: " + nama
                        + "\nNomor HP: " + phone
                        + "\nLapangan: " + court
                        + "\nTanggal: " + datePicker.getValue()
                        + "\nWaktu: " + startTimeCombo.getValue()
                        + " - " + endTimeCombo.getValue()
                        + "\nDurasi: " + durasi + " jam"
                        + "\nBiaya Lapangan: " + formatRupiah(biayaLapangan)
                        + "\nBiaya Peralatan: " + formatRupiah(biayaPeralatan)
                        + "\nTotal: " + formatRupiah(totalBiaya)
                        + "\nMetode Pembayaran: Tunai"
                        + "\nStatus Pembayaran: Lunas"
                        + "\n\nTransaksi berhasil disimpan ke MySQL."
        );

        alert.showAndWait();

        try {
            App.setRoot("dashboard-user");

        } catch (IOException e) {
            showError(
                    "Transaksi tersimpan, tetapi gagal kembali "
                            + "ke Dashboard: " + e.getMessage()
            );
        }
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
