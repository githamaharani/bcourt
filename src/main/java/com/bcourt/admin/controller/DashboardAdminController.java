
package com.bcourt.admin.controller;

import java.io.IOException;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class DashboardAdminController {

    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    @FXML
    private HBox dashboardMenu;

    @FXML
    private HBox lapanganMenu;

    @FXML
    private HBox peralatanMenu;

    @FXML
    private HBox transaksiMenu;

    @FXML
    private HBox laporanMenu;

    @FXML
    private HBox penggunaMenu;

    @FXML
    private HBox profileMenu;


    // ==========================================
    // BOOKING CHART FILTER
    // ==========================================

    @FXML
    private Label bookingWeeklyLabel;

    @FXML
    private Label bookingMonthlyLabel;

    @FXML
    private Label bookingYearlyLabel;


    // ==========================================
    // REVENUE CHART FILTER
    // ==========================================

    @FXML
    private Label revenueWeeklyLabel;

    @FXML
    private Label revenueMonthlyLabel;

    @FXML
    private Label revenueYearlyLabel;


    // ==========================================
    // TRANSACTION
    // ==========================================

    @FXML
    private Hyperlink viewAllLink;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        // Navigasi Sidebar
        dashboardMenu.setCursor(Cursor.HAND);
        lapanganMenu.setCursor(Cursor.HAND);
        peralatanMenu.setCursor(Cursor.HAND);
        transaksiMenu.setCursor(Cursor.HAND);
        laporanMenu.setCursor(Cursor.HAND);
        penggunaMenu.setCursor(Cursor.HAND);
        profileMenu.setCursor(Cursor.HAND);

        // Event Dashboard
        dashboardMenu.setOnMouseClicked(event -> {
            bukaDashboard();
        });

        // Event Manajemen Lapangan
        lapanganMenu.setOnMouseClicked(event -> {
            bukaManajemenLapangan();
        });

        // Event Manajemen Peralatan
        peralatanMenu.setOnMouseClicked(event -> {
            bukaManajemenPeralatan();
        });

        // Event Data Transaksi
        transaksiMenu.setOnMouseClicked(event -> {
            bukaDataTransaksi();
        });

        // Event Laporan
        laporanMenu.setOnMouseClicked(event -> {
            bukaLaporan();
        });

        // Event Manajemen Pengguna
        penggunaMenu.setOnMouseClicked(event -> {
            bukaManajemenPengguna();
        });

        // Event Profil
        profileMenu.setOnMouseClicked(event -> {
            bukaProfil();
        });


        // Cursor Filter Grafik
        bookingWeeklyLabel.setCursor(Cursor.HAND);
        bookingMonthlyLabel.setCursor(Cursor.HAND);
        bookingYearlyLabel.setCursor(Cursor.HAND);

        revenueWeeklyLabel.setCursor(Cursor.HAND);
        revenueMonthlyLabel.setCursor(Cursor.HAND);
        revenueYearlyLabel.setCursor(Cursor.HAND);


        // Event Filter Booking
        bookingWeeklyLabel.setOnMouseClicked(event -> {
            pilihBookingMingguan();
        });

        bookingMonthlyLabel.setOnMouseClicked(event -> {
            pilihBookingBulanan();
        });

        bookingYearlyLabel.setOnMouseClicked(event -> {
            pilihBookingTahunan();
        });


        // Event Filter Revenue
        revenueWeeklyLabel.setOnMouseClicked(event -> {
            pilihRevenueMingguan();
        });

        revenueMonthlyLabel.setOnMouseClicked(event -> {
            pilihRevenueBulanan();
        });

        revenueYearlyLabel.setOnMouseClicked(event -> {
            pilihRevenueTahunan();
        });


        // Event Lihat Semua Transaksi
        viewAllLink.setOnAction(event -> {
            lihatSemuaTransaksi();
        });
    }


    // ==========================================
    // NAVIGASI HALAMAN
    // ==========================================

    private void bukaDashboard() {
        // Dashboard merupakan halaman yang sedang aktif.
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


    private void bukaDataTransaksi() {

        tampilkanInfo(
            "Halaman Data Transaksi belum dihubungkan."
        );
    }


    private void bukaLaporan() {

        tampilkanInfo(
            "Halaman Laporan belum dihubungkan."
        );
    }


    private void bukaManajemenPengguna() {

        tampilkanInfo(
            "Halaman Manajemen Pengguna belum dihubungkan."
        );
    }


    private void bukaProfil() {

        tampilkanInfo(
            "Fitur Profil Admin belum dihubungkan."
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
                e.getMessage()
            );
        }
    }


    // ==========================================
    // BOOKING CHART FILTER
    // ==========================================

    private void pilihBookingMingguan() {

        aktifkanFilter(
            bookingWeeklyLabel,
            bookingMonthlyLabel,
            bookingYearlyLabel
        );
    }


    private void pilihBookingBulanan() {

        aktifkanFilter(
            bookingMonthlyLabel,
            bookingWeeklyLabel,
            bookingYearlyLabel
        );
    }


    private void pilihBookingTahunan() {

        aktifkanFilter(
            bookingYearlyLabel,
            bookingWeeklyLabel,
            bookingMonthlyLabel
        );
    }


    // ==========================================
    // REVENUE CHART FILTER
    // ==========================================

    private void pilihRevenueMingguan() {

        aktifkanFilter(
            revenueWeeklyLabel,
            revenueMonthlyLabel,
            revenueYearlyLabel
        );
    }


    private void pilihRevenueBulanan() {

        aktifkanFilter(
            revenueMonthlyLabel,
            revenueWeeklyLabel,
            revenueYearlyLabel
        );
    }


    private void pilihRevenueTahunan() {

        aktifkanFilter(
            revenueYearlyLabel,
            revenueWeeklyLabel,
            revenueMonthlyLabel
        );
    }


    // ==========================================
    // MENGUBAH TAMPILAN FILTER
    // ==========================================

    private void aktifkanFilter(
        Label aktif,
        Label... tidakAktif
    ) {

        aktif.setStyle(
            "-fx-font-size: 9.50px;" +
            "-fx-font-weight: bold;" +
            "-fx-text-fill: #FFFFFF;" +
            "-fx-background-color: #FF7721;" +
            "-fx-background-radius: 9.75;" +
            "-fx-padding: 3.68 11.89 3.68 11.89;" +
            "-fx-cursor: hand;"
        );

        for (Label label : tidakAktif) {

            label.setStyle(
                "-fx-font-size: 9.50px;" +
                "-fx-text-fill: #9B7967;" +
                "-fx-padding: 3.68 8.50 3.68 8.50;" +
                "-fx-cursor: hand;"
            );
        }
    }


    // ==========================================
    // LIHAT SEMUA TRANSAKSI
    // ==========================================

    private void lihatSemuaTransaksi() {

        tampilkanInfo(
            "Halaman seluruh transaksi belum tersedia."
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
