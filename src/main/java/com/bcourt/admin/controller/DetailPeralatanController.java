
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

public class DetailPeralatanController {

    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    @FXML private HBox lapanganMenu1;
    @FXML private HBox peralatanMenu1;
    @FXML private HBox dashboardMenu111;
    @FXML private HBox transaksiMenu1;
    @FXML private HBox laporanMenu1;
    @FXML private HBox penggunaMenu1;
    @FXML private HBox profileMenu1;


    // ==========================================
    // BREADCRUMB
    // ==========================================

    @FXML private Hyperlink dashboardBreadcrumb;
    @FXML private Hyperlink equipmentBreadcrumb;


    // ==========================================
    // INFORMASI PERALATAN
    // ==========================================

    @FXML private Label equipmentNameLabel;
    @FXML private Label equipmentStatusBadge;

    @FXML private Label infoTypeValue;
    @FXML private Label infoQuantityValue;
    @FXML private Label infoConditionValue;
    @FXML private Label infoStatusValue;
    @FXML private Label infoDescriptionValue;

    @FXML private Label rentalPriceValue;
    @FXML private Label addedDateValue;


    // ==========================================
    // TOMBOL
    // ==========================================

    @FXML private Button backButton;
    @FXML private Button editEquipmentButton;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        aturSidebar();
        aturBreadcrumb();
        aturTombol();
        aturTampilanStatus();
    }


    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    private void aturSidebar() {

        aturKlikSidebar(lapanganMenu1, this::bukaDashboard);

        aturKlikSidebar(peralatanMenu1, this::bukaManajemenLapangan);

        aturKlikSidebar(dashboardMenu111, this::bukaManajemenPeralatan);

        aturKlikSidebar(transaksiMenu1, () ->
            tampilkanInfo("Halaman Data Transaksi belum dihubungkan.")
        );

        aturKlikSidebar(laporanMenu1, () ->
            tampilkanInfo("Halaman Laporan belum dihubungkan.")
        );

        aturKlikSidebar(penggunaMenu1, () ->
            tampilkanInfo("Halaman Manajemen Pengguna belum dihubungkan.")
        );

        aturKlikSidebar(profileMenu1, () ->
            tampilkanInfo("Fitur Profil Admin belum dihubungkan.")
        );
    }


    private void aturKlikSidebar(HBox menu, Runnable aksi) {

        menu.setCursor(Cursor.HAND);
        menu.setPickOnBounds(true);

        menu.setOnMouseClicked(event -> {
            aksi.run();
            event.consume();
        });
    }


    // ==========================================
    // BREADCRUMB
    // ==========================================

    private void aturBreadcrumb() {

        dashboardBreadcrumb.setCursor(Cursor.HAND);
        equipmentBreadcrumb.setCursor(Cursor.HAND);

        dashboardBreadcrumb.setOnAction(event -> {
            bukaDashboard();
        });

        equipmentBreadcrumb.setOnAction(event -> {
            bukaManajemenPeralatan();
        });
    }


    // ==========================================
    // TOMBOL DETAIL
    // ==========================================

    private void aturTombol() {

        backButton.setCursor(Cursor.HAND);
        editEquipmentButton.setCursor(Cursor.HAND);

        backButton.setOnAction(event -> {
            bukaManajemenPeralatan();
        });

        editEquipmentButton.setOnAction(event -> {
            bukaEditPeralatan();
        });
    }


    // ==========================================
    // TAMPILAN STATUS
    // ==========================================

    private void aturTampilanStatus() {

        String status = equipmentStatusBadge.getText();

        if (status == null) {
            return;
        }

        String warnaLatar;
        String warnaTeks;

        if (status.equalsIgnoreCase("Tersedia")) {

            warnaLatar = "#E3F6EA";
            warnaTeks = "#2E7D4F";

        } else if (status.equalsIgnoreCase("Terpakai")) {

            warnaLatar = "#FDE3E5";
            warnaTeks = "#C0262D";

        } else if (status.equalsIgnoreCase("Perawatan")) {

            warnaLatar = "#FDE3D1";
            warnaTeks = "#B8480A";

        } else {
            return;
        }

        String style =
            "-fx-background-color: " + warnaLatar + ";"
            + "-fx-background-radius: 14;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: " + warnaTeks + ";"
            + "-fx-padding: 3 12 3 12;";

        equipmentStatusBadge.setStyle(style);
        infoStatusValue.setStyle(style);
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


    private void bukaEditPeralatan() {

        pindahHalaman(
            "/com/bcourt/admin/edit-peralatan.fxml"
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
                e.getMessage()
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
