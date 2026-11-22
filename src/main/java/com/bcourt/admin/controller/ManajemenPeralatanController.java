
package com.bcourt.admin.controller;

import java.io.IOException;
import java.util.Optional;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

public class ManajemenPeralatanController {

    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    @FXML
    private HBox lapanganMenu1;

    @FXML
    private HBox peralatanMenu1;

    @FXML
    private HBox dashboardMenu111;

    @FXML
    private HBox transaksiMenu1;

    @FXML
    private HBox laporanMenu1;

    @FXML
    private HBox penggunaMenu1;

    @FXML
    private HBox profileMenu1;


    // ==========================================
    // BREADCRUMB DAN PENCARIAN
    // ==========================================

    @FXML
    private Hyperlink dashboardBreadcrumb;

    @FXML
    private TextField searchEquipmentField;


    // ==========================================
    // STATISTIK PERALATAN
    // ==========================================

    @FXML
    private Label totalCourtValue1;

    @FXML
    private Label availableCourtValue1;

    @FXML
    private Label occupiedCourtValue1;

    @FXML
    private Label maintenanceCourtValue1;


    // ==========================================
    // TOMBOL TAMBAH PERALATAN
    // ==========================================

    @FXML
    private Button addEquipmentButton;


    // ==========================================
    // DATA TABEL PERALATAN
    // ==========================================

    @FXML
    private GridPane equipmentRow1;

    @FXML
    private GridPane equipmentRow2;

    @FXML
    private GridPane equipmentRow3;

    @FXML
    private GridPane equipmentRow4;

    @FXML
    private GridPane equipmentRow5;

    @FXML
    private GridPane equipmentRow6;


    // ==========================================
    // DATA BARIS 1
    // ==========================================

    @FXML
    private Label row1No;

    @FXML
    private Label row1Name;

    @FXML
    private Label row1Type;

    @FXML
    private Label row1Quantity;

    @FXML
    private Label row1Condition;

    @FXML
    private Label row1Status;


    // ==========================================
    // DATA BARIS 2
    // ==========================================

    @FXML
    private Label row2No;

    @FXML
    private Label row2Name;

    @FXML
    private Label row2Type;

    @FXML
    private Label row2Quantity;

    @FXML
    private Label row2Condition;

    @FXML
    private Label row2Status;


    // ==========================================
    // DATA BARIS 3
    // ==========================================

    @FXML
    private Label row3No;

    @FXML
    private Label row3Name;

    @FXML
    private Label row3Type;

    @FXML
    private Label row3Quantity;

    @FXML
    private Label row3Condition;

    @FXML
    private Label row3Status;


    // ==========================================
    // DATA BARIS 4
    // ==========================================

    @FXML
    private Label row4No;

    @FXML
    private Label row4Name;

    @FXML
    private Label row4Type;

    @FXML
    private Label row4Quantity;

    @FXML
    private Label row4Condition;

    @FXML
    private Label row4Status;


    // ==========================================
    // DATA BARIS 5
    // ==========================================

    @FXML
    private Label row5No;

    @FXML
    private Label row5Name;

    @FXML
    private Label row5Type;

    @FXML
    private Label row5Quantity;

    @FXML
    private Label row5Condition;

    @FXML
    private Label row5Status;


    // ==========================================
    // DATA BARIS 6
    // ==========================================

    @FXML
    private Label row6No;

    @FXML
    private Label row6Name;

    @FXML
    private Label row6Type;

    @FXML
    private Label row6Quantity;

    @FXML
    private Label row6Condition;

    @FXML
    private Label row6Status;


    // ==========================================
    // TOMBOL AKSI BARIS 1
    // ==========================================

    @FXML
    private Button detailButton21;

    @FXML
    private Button editButton21;

    @FXML
    private Button deleteButton21;


    // ==========================================
    // TOMBOL AKSI BARIS 2
    // ==========================================

    @FXML
    private Button detailButton211;

    @FXML
    private Button editButton211;

    @FXML
    private Button deleteButton211;


    // ==========================================
    // TOMBOL AKSI BARIS 3
    // ==========================================

    @FXML
    private Button detailButton2111;

    @FXML
    private Button editButton2111;

    @FXML
    private Button deleteButton2111;


    // ==========================================
    // TOMBOL AKSI BARIS 4
    // ==========================================

    @FXML
    private Button detailButton2112;

    @FXML
    private Button editButton2112;

    @FXML
    private Button deleteButton2112;


    // ==========================================
    // TOMBOL AKSI BARIS 5
    // ==========================================

    @FXML
    private Button detailButton2113;

    @FXML
    private Button editButton2113;

    @FXML
    private Button deleteButton2113;


    // ==========================================
    // TOMBOL AKSI BARIS 6
    // ==========================================

    @FXML
    private Button detailButton2114;

    @FXML
    private Button editButton2114;

    @FXML
    private Button deleteButton2114;


    // ==========================================
    // ARRAY DATA TABEL
    // ==========================================

    private GridPane[] daftarBaris;

    private Label[] daftarNama;

    private Label[] daftarJenis;

    private Label[] daftarKondisi;

    private Label[] daftarStatus;

    private boolean[] dataDihapus = new boolean[6];


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        inisialisasiDaftarBaris();


        // ======================================
        // SIDEBAR NAVIGATION
        // ======================================

        lapanganMenu1.setCursor(Cursor.HAND);
        peralatanMenu1.setCursor(Cursor.HAND);
        dashboardMenu111.setCursor(Cursor.HAND);
        transaksiMenu1.setCursor(Cursor.HAND);
        laporanMenu1.setCursor(Cursor.HAND);
        penggunaMenu1.setCursor(Cursor.HAND);
        profileMenu1.setCursor(Cursor.HAND);

        // Event Dashboard
        lapanganMenu1.setOnMouseClicked(event -> {
            bukaDashboard();
        });

        // Event Manajemen Lapangan
        peralatanMenu1.setOnMouseClicked(event -> {
            bukaManajemenLapangan();
        });

        // Event Manajemen Peralatan
        dashboardMenu111.setOnMouseClicked(event -> {
            // Halaman Manajemen Peralatan sedang aktif.
        });

        // Event Data Transaksi
        transaksiMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Data Transaksi belum dihubungkan."
            );
        });

        // Event Laporan
        laporanMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Laporan belum dihubungkan."
            );
        });

        // Event Manajemen Pengguna
        penggunaMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Halaman Manajemen Pengguna belum dihubungkan."
            );
        });

        // Event Profil
        profileMenu1.setOnMouseClicked(event -> {
            tampilkanInfo(
                "Fitur Profil Admin belum dihubungkan."
            );
        });


        // ======================================
        // BREADCRUMB
        // ======================================

        dashboardBreadcrumb.setCursor(Cursor.HAND);

        dashboardBreadcrumb.setOnAction(event -> {
            bukaDashboard();
        });


        // ======================================
        // TOMBOL TAMBAH PERALATAN
        // ======================================

        addEquipmentButton.setCursor(Cursor.HAND);

        addEquipmentButton.setOnAction(event -> {
            bukaTambahPeralatan();
        });


        // ======================================
        // TOMBOL AKSI BARIS 1
        // ======================================

        detailButton21.setCursor(Cursor.HAND);
        editButton21.setCursor(Cursor.HAND);
        deleteButton21.setCursor(Cursor.HAND);

        detailButton21.setOnAction(event -> {
            bukaDetailPeralatan(0);
        });

        editButton21.setOnAction(event -> {
            bukaEditPeralatan(0);
        });

        deleteButton21.setOnAction(event -> {
            konfirmasiHapus(0);
        });


        // ======================================
        // TOMBOL AKSI BARIS 2
        // ======================================

        detailButton211.setCursor(Cursor.HAND);
        editButton211.setCursor(Cursor.HAND);
        deleteButton211.setCursor(Cursor.HAND);

        detailButton211.setOnAction(event -> {
            bukaDetailPeralatan(1);
        });

        editButton211.setOnAction(event -> {
            bukaEditPeralatan(1);
        });

        deleteButton211.setOnAction(event -> {
            konfirmasiHapus(1);
        });


        // ======================================
        // TOMBOL AKSI BARIS 3
        // ======================================

        detailButton2111.setCursor(Cursor.HAND);
        editButton2111.setCursor(Cursor.HAND);
        deleteButton2111.setCursor(Cursor.HAND);

        detailButton2111.setOnAction(event -> {
            bukaDetailPeralatan(2);
        });

        editButton2111.setOnAction(event -> {
            bukaEditPeralatan(2);
        });

        deleteButton2111.setOnAction(event -> {
            konfirmasiHapus(2);
        });


        // ======================================
        // TOMBOL AKSI BARIS 4
        // ======================================

        detailButton2112.setCursor(Cursor.HAND);
        editButton2112.setCursor(Cursor.HAND);
        deleteButton2112.setCursor(Cursor.HAND);

        detailButton2112.setOnAction(event -> {
            bukaDetailPeralatan(3);
        });

        editButton2112.setOnAction(event -> {
            bukaEditPeralatan(3);
        });

        deleteButton2112.setOnAction(event -> {
            konfirmasiHapus(3);
        });


        // ======================================
        // TOMBOL AKSI BARIS 5
        // ======================================

        detailButton2113.setCursor(Cursor.HAND);
        editButton2113.setCursor(Cursor.HAND);
        deleteButton2113.setCursor(Cursor.HAND);

        detailButton2113.setOnAction(event -> {
            bukaDetailPeralatan(4);
        });

        editButton2113.setOnAction(event -> {
            bukaEditPeralatan(4);
        });

        deleteButton2113.setOnAction(event -> {
            konfirmasiHapus(4);
        });


        // ======================================
        // TOMBOL AKSI BARIS 6
        // ======================================

        detailButton2114.setCursor(Cursor.HAND);
        editButton2114.setCursor(Cursor.HAND);
        deleteButton2114.setCursor(Cursor.HAND);

        detailButton2114.setOnAction(event -> {
            bukaDetailPeralatan(5);
        });

        editButton2114.setOnAction(event -> {
            bukaEditPeralatan(5);
        });

        deleteButton2114.setOnAction(event -> {
            konfirmasiHapus(5);
        });


        // ======================================
        // FITUR PENCARIAN
        // ======================================

        searchEquipmentField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                cariPeralatan(newValue);
            }
        );


        // ======================================
        // STATISTIK AWAL
        // ======================================

        perbaruiStatistik();
    }


    // ==========================================
    // INISIALISASI DATA TABEL
    // ==========================================

    private void inisialisasiDaftarBaris() {

        daftarBaris = new GridPane[] {
            equipmentRow1,
            equipmentRow2,
            equipmentRow3,
            equipmentRow4,
            equipmentRow5,
            equipmentRow6
        };

        daftarNama = new Label[] {
            row1Name,
            row2Name,
            row3Name,
            row4Name,
            row5Name,
            row6Name
        };

        daftarJenis = new Label[] {
            row1Type,
            row2Type,
            row3Type,
            row4Type,
            row5Type,
            row6Type
        };

        daftarKondisi = new Label[] {
            row1Condition,
            row2Condition,
            row3Condition,
            row4Condition,
            row5Condition,
            row6Condition
        };

        daftarStatus = new Label[] {
            row1Status,
            row2Status,
            row3Status,
            row4Status,
            row5Status,
            row6Status
        };
    }


    // ==========================================
    // NAVIGASI SIDEBAR
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


    // ==========================================
    // NAVIGASI HALAMAN PERALATAN
    // ==========================================

    private void bukaTambahPeralatan() {

        pindahHalaman(
            "/com/bcourt/admin/tambah-peralatan.fxml"
        );
    }


    private void bukaDetailPeralatan(int nomorBaris) {

        pindahHalaman(
            "/com/bcourt/admin/detail-peralatan.fxml"
        );
    }


    private void bukaEditPeralatan(int nomorBaris) {

        pindahHalaman(
            "/com/bcourt/admin/edit-peralatan.fxml"
        );
    }


    // ==========================================
    // NAVIGASI FXML
    // ==========================================

    private void pindahHalaman(String lokasiFXML) {

        try {

            App.setRoot(lokasiFXML);

        } catch (IOException e) {

            e.printStackTrace();

            tampilkanError(
                "Gagal membuka halaman.",
                "Periksa lokasi file FXML berikut:\n"
                + lokasiFXML
            );
        }
    }


    // ==========================================
    // FITUR PENCARIAN PERALATAN
    // ==========================================

    private void cariPeralatan(String keyword) {

        String kataKunci = keyword == null
            ? ""
            : keyword.trim().toLowerCase();

        for (int i = 0; i < daftarBaris.length; i++) {

            if (dataDihapus[i]) {
                continue;
            }

            String isiBaris = (
                daftarNama[i].getText() + " "
                + daftarJenis[i].getText() + " "
                + daftarKondisi[i].getText() + " "
                + daftarStatus[i].getText()
            ).toLowerCase();

            boolean cocok = isiBaris.contains(kataKunci);

            daftarBaris[i].setVisible(cocok);
            daftarBaris[i].setManaged(cocok);
        }
    }


    // ==========================================
    // FITUR HAPUS PERALATAN
    // ==========================================

    private void konfirmasiHapus(int index) {

        if (dataDihapus[index]) {
            return;
        }

        String namaPeralatan = ambilNamaPeralatan(index);

        Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION
        );

        alert.setTitle("Konfirmasi Penghapusan");

        alert.setHeaderText(
            "Hapus " + namaPeralatan + "?"
        );

        alert.setContentText(
            "Apakah Anda yakin ingin menghapus data peralatan ini?\n"
            + "Data yang telah dihapus tidak dapat dikembalikan."
        );

        ButtonType tombolHapus = new ButtonType("Ya, Hapus");

        ButtonType tombolBatal = new ButtonType("Tidak");

        alert.getButtonTypes().setAll(
            tombolHapus,
            tombolBatal
        );

        Optional<ButtonType> hasil = alert.showAndWait();

        if (hasil.isPresent() && hasil.get() == tombolHapus) {

            hapusBarisPeralatan(index);

            perbaruiStatistik();

            tampilkanInfo(
                namaPeralatan
                + " berhasil dihapus dari tampilan."
            );
        }
    }


    // ==========================================
    // MENGAMBIL NAMA PERALATAN
    // ==========================================

    private String ambilNamaPeralatan(int index) {

        return daftarNama[index].getText();
    }


    // ==========================================
    // MENGHAPUS BARIS PERALATAN
    // ==========================================

    private void hapusBarisPeralatan(int index) {

        dataDihapus[index] = true;

        daftarBaris[index].setVisible(false);
        daftarBaris[index].setManaged(false);
    }


    // ==========================================
    // MEMPERBARUI STATISTIK PERALATAN
    // ==========================================

    private void perbaruiStatistik() {

        int total = 0;
        int tersedia = 0;
        int terpakai = 0;
        int perawatan = 0;

        for (int i = 0; i < daftarStatus.length; i++) {

            if (dataDihapus[i]) {
                continue;
            }

            total++;

            String status = daftarStatus[i].getText();

            if (status.equalsIgnoreCase("Tersedia")) {

                tersedia++;

            } else if (status.equalsIgnoreCase("Terpakai")) {

                terpakai++;

            } else if (status.equalsIgnoreCase("Perawatan")) {

                perawatan++;
            }
        }

        totalCourtValue1.setText(
            String.valueOf(total)
        );

        availableCourtValue1.setText(
            String.valueOf(tersedia)
        );

        occupiedCourtValue1.setText(
            String.valueOf(terpakai)
        );

        maintenanceCourtValue1.setText(
            String.valueOf(perawatan)
        );
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
