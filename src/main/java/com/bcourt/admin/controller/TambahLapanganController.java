
package com.bcourt.admin.controller;

import java.io.File;
import java.io.IOException;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TambahLapanganController {

    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    @FXML private HBox lapanganMenu1;
    @FXML private HBox dashboardMenu11;
    @FXML private HBox peralatanMenu1;
    @FXML private HBox transaksiMenu1;
    @FXML private HBox laporanMenu1;
    @FXML private HBox penggunaMenu1;
    @FXML private HBox profileMenu1;


    // ==========================================
    // BREADCRUMB
    // ==========================================

    @FXML private Hyperlink dashboardBreadcrumb;
    @FXML private Hyperlink courtBreadcrumb;


    // ==========================================
    // FORM LAPANGAN
    // ==========================================

    @FXML private TextField courtNameField;
    @FXML private TextField courtNumberField;
    @FXML private RadioButton singleCourtRadio;
    @FXML private RadioButton doubleCourtRadio;
    @FXML private TextField courtPriceField;
    @FXML private ComboBox<String> courtStatusComboBox;
    @FXML private TextArea courtDescriptionField;


    // ==========================================
    // FASILITAS LAPANGAN
    // ==========================================

    @FXML private CheckBox netCheckBox;
    @FXML private CheckBox lampuCheckBox;
    @FXML private CheckBox kursiCheckBox;
    @FXML private CheckBox lockerCheckBox;
    @FXML private CheckBox dispenserCheckBox;
    @FXML private CheckBox areaIstirahatCheckBox;
    @FXML private CheckBox peminjamanPeralatanCheckBox;


    // ==========================================
    // FOTO LAPANGAN
    // ==========================================

    @FXML private VBox photoDropZone;
    @FXML private javafx.scene.control.Label photoDropDescription;

    private File fotoTerpilih;


    // ==========================================
    // TOMBOL
    // ==========================================

    @FXML private Button cancelButton;
    @FXML private Button saveCourtButton;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        aturSidebar();
        aturBreadcrumb();
        aturPilihanStatus();
        aturTombol();
        aturPemilihanFoto();
    }


    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    private void aturSidebar() {

        lapanganMenu1.setCursor(Cursor.HAND);
        dashboardMenu11.setCursor(Cursor.HAND);
        peralatanMenu1.setCursor(Cursor.HAND);
        transaksiMenu1.setCursor(Cursor.HAND);
        laporanMenu1.setCursor(Cursor.HAND);
        penggunaMenu1.setCursor(Cursor.HAND);
        profileMenu1.setCursor(Cursor.HAND);

        // Dashboard
        lapanganMenu1.setOnMouseClicked(event -> {
            bukaDashboard();
        });

        // Manajemen Lapangan
        dashboardMenu11.setOnMouseClicked(event -> {
            bukaManajemenLapangan();
        });

        // Manajemen Peralatan
        peralatanMenu1.setOnMouseClicked(event -> {
            bukaManajemenPeralatan();
        });

        // Data Transaksi
        transaksiMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Data Transaksi belum dihubungkan."
            );
        });

        // Laporan
        laporanMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Laporan belum dihubungkan."
            );
        });

        // Manajemen Pengguna
        penggunaMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Manajemen Pengguna belum dihubungkan."
            );
        });

        // Profil
        profileMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Fitur Profil Admin belum dihubungkan."
            );
        });
    }


    // ==========================================
    // BREADCRUMB
    // ==========================================

    private void aturBreadcrumb() {

        dashboardBreadcrumb.setCursor(Cursor.HAND);
        courtBreadcrumb.setCursor(Cursor.HAND);

        dashboardBreadcrumb.setOnAction(event -> {
            bukaDashboard();
        });

        courtBreadcrumb.setOnAction(event -> {
            bukaManajemenLapangan();
        });
    }


    // ==========================================
    // PILIHAN STATUS
    // ==========================================

    private void aturPilihanStatus() {

        courtStatusComboBox.getItems().setAll(
            "Tersedia",
            "Perawatan"
        );

        courtStatusComboBox.setValue("Tersedia");
    }


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    private void aturTombol() {

        cancelButton.setCursor(Cursor.HAND);
        saveCourtButton.setCursor(Cursor.HAND);

        cancelButton.setOnAction(event -> {
            bukaManajemenLapangan();
        });

        saveCourtButton.setOnAction(event -> {
            simpanLapangan();
        });
    }


    // ==========================================
    // PEMILIHAN FOTO
    // ==========================================

    private void aturPemilihanFoto() {

        photoDropZone.setCursor(Cursor.HAND);

        photoDropZone.setOnMouseClicked(event -> {
            pilihFoto();
        });
    }


    private void pilihFoto() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Pilih Foto Lapangan");

        fileChooser.getExtensionFilters().add(
            new FileChooser.ExtensionFilter(
                "File Gambar",
                "*.png",
                "*.jpg",
                "*.jpeg"
            )
        );

        Stage stage = (Stage) photoDropZone
            .getScene()
            .getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            fotoTerpilih = file;

            photoDropDescription.setText(
                "Foto dipilih: " + file.getName()
            );
        }
    }


    // ==========================================
    // VALIDASI FORM
    // ==========================================

    private boolean validasiForm() {

        String nama = courtNameField.getText().trim();
        String nomor = courtNumberField.getText().trim();
        String harga = courtPriceField.getText().trim();

        // Validasi nama
        if (nama.isEmpty()) {

            tampilkanPeringatan(
                "Nama lapangan wajib diisi."
            );

            courtNameField.requestFocus();
            return false;
        }

        // Validasi nomor
        if (nomor.isEmpty()) {

            tampilkanPeringatan(
                "Nomor lapangan wajib diisi."
            );

            courtNumberField.requestFocus();
            return false;
        }

        if (!nomor.matches("\\d+")) {

            tampilkanPeringatan(
                "Nomor lapangan harus berupa angka."
            );

            courtNumberField.requestFocus();
            return false;
        }

        // Validasi jenis
        if (
            !singleCourtRadio.isSelected()
            && !doubleCourtRadio.isSelected()
        ) {

            tampilkanPeringatan(
                "Silakan pilih jenis lapangan."
            );

            return false;
        }

        // Validasi harga
        if (harga.isEmpty()) {

            tampilkanPeringatan(
                "Harga sewa wajib diisi."
            );

            courtPriceField.requestFocus();
            return false;
        }

        try {

            int nilaiHarga = Integer.parseInt(harga);

            if (nilaiHarga <= 0) {

                tampilkanPeringatan(
                    "Harga sewa harus lebih dari 0."
                );

                courtPriceField.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {

            tampilkanPeringatan(
                "Harga sewa harus berupa angka."
            );

            courtPriceField.requestFocus();
            return false;
        }

        // Validasi status
        if (courtStatusComboBox.getValue() == null) {

            tampilkanPeringatan(
                "Silakan pilih status lapangan."
            );

            return false;
        }

        return true;
    }


    // ==========================================
    // SIMPAN LAPANGAN
    // ==========================================

    private void simpanLapangan() {

        if (!validasiForm()) {
            return;
        }

        /*
         * Data sudah lolos validasi.
         *
         * Penyimpanan permanen akan dihubungkan
         * ke LapanganDAO ketika integrasi database
         * mulai dikerjakan.
         */

        tampilkanInfo(
            "Form lapangan berhasil divalidasi.\n\n"
            + "Nama: " + courtNameField.getText().trim()
            + "\nNomor: " + courtNumberField.getText().trim()
            + "\nJenis: "
            + (singleCourtRadio.isSelected() ? "Tunggal" : "Ganda")
            + "\nHarga: Rp " + courtPriceField.getText().trim()
            + "\nStatus: " + courtStatusComboBox.getValue()
            + "\n\nData belum disimpan permanen karena "
            + "integrasi database belum dilakukan."
        );
    }


    // ==========================================
    // NAVIGASI HALAMAN
    // ==========================================

    private void bukaDashboard() {

        pindahHalaman(
            "/com/bcourt/admin/dashboard-admin.fxml"
        );
    }


    private void bukaManajemenLapangan() {

        pindahHalaman(
            "/com/bcourt/admin/manajemen-lapangan.fxml"
        );
    }


    private void bukaManajemenPeralatan() {

        pindahHalaman(
            "/com/bcourt/admin/manajemen-peralatan.fxml"
        );
    }


    // ==========================================
    // PINDAH HALAMAN
    // ==========================================

    private void pindahHalaman(String lokasiFXML) {

        try {

            App.setRoot(lokasiFXML);

        } catch (IOException e) {

            e.printStackTrace();

            tampilkanError(
                "Gagal membuka halaman.",
                "Periksa lokasi file FXML:\n" + lokasiFXML
            );
        }
    }


    // ==========================================
    // ALERT INFORMASI
    // ==========================================

    private void tampilkanInfo(String pesan) {

        Alert alert = new Alert(
            Alert.AlertType.INFORMATION
        );

        alert.setTitle("Informasi B-Court");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        alert.showAndWait();
    }


    // ==========================================
    // ALERT PERINGATAN
    // ==========================================

    private void tampilkanPeringatan(String pesan) {

        Alert alert = new Alert(
            Alert.AlertType.WARNING
        );

        alert.setTitle("Periksa Form");
        alert.setHeaderText(null);
        alert.setContentText(pesan);

        alert.showAndWait();
    }


    // ==========================================
    // ALERT ERROR
    // ==========================================

    private void tampilkanError(
        String judul,
        String pesan
    ) {

        Alert alert = new Alert(
            Alert.AlertType.ERROR
        );

        alert.setTitle("Terjadi Kesalahan");
        alert.setHeaderText(judul);
        alert.setContentText(pesan);

        alert.showAndWait();
    }
}
