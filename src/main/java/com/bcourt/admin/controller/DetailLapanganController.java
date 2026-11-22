
package com.bcourt.admin.controller;

import java.io.IOException;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DetailLapanganController {

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
    // TOMBOL AKSI
    // ==========================================

    @FXML private Button backButton;
    @FXML private Button editCourtButton;


    // ==========================================
    // INFORMASI LAPANGAN
    // ==========================================

    @FXML private javafx.scene.image.ImageView courtMainImage;

    @FXML private Label courtNameLabel;
    @FXML private Label courtNumberLabel;

    @FXML private HBox courtStatusBadge;
    @FXML private Label courtStatusLabel;

    @FXML private Label courtTypeValue;
    @FXML private Label courtPriceValue;
    @FXML private Label courtDescriptionLabel;


    // ==========================================
    // FASILITAS LAPANGAN
    // ==========================================

    @FXML private Label facility1;
    @FXML private Label facility2;
    @FXML private Label facility3;
    @FXML private Label facility4;
    @FXML private Label facility5;
    @FXML private Label facility6;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        aturSidebar();
        aturBreadcrumb();
        aturTombol();
        aturStatusBadge();
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
    // TOMBOL AKSI
    // ==========================================

    private void aturTombol() {

        backButton.setCursor(Cursor.HAND);
        editCourtButton.setCursor(Cursor.HAND);

        // Kembali ke daftar lapangan
        backButton.setOnAction(event -> {
            bukaManajemenLapangan();
        });

        // Membuka halaman edit
        editCourtButton.setOnAction(event -> {
            bukaEditLapangan();
        });
    }


    // ==========================================
    // STATUS LAPANGAN
    // ==========================================

    private void aturStatusBadge() {

        String status = courtStatusLabel.getText()
            .trim()
            .toLowerCase();

        if (status.equals("tersedia")) {

            courtStatusBadge.setStyle(
                "-fx-background-color: #E3F6EA;"
                + "-fx-background-radius: 15.81;"
                + "-fx-padding: 6.32 14.23 6.32 14.23;"
            );

            courtStatusLabel.setStyle(
                "-fx-font-size: 11.86px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #2E7D4F;"
            );

        } else if (status.equals("terpakai")) {

            courtStatusBadge.setStyle(
                "-fx-background-color: #FDE6E8;"
                + "-fx-background-radius: 15.81;"
                + "-fx-padding: 6.32 14.23 6.32 14.23;"
            );

            courtStatusLabel.setStyle(
                "-fx-font-size: 11.86px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #A51F26;"
            );

        } else if (status.equals("perawatan")) {

            courtStatusBadge.setStyle(
                "-fx-background-color: #FFF2D6;"
                + "-fx-background-radius: 15.81;"
                + "-fx-padding: 6.32 14.23 6.32 14.23;"
            );

            courtStatusLabel.setStyle(
                "-fx-font-size: 11.86px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #B7791F;"
            );
        }
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


    private void bukaEditLapangan() {

        pindahHalaman(
            "/com/bcourt/admin/edit-lapangan.fxml"
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
