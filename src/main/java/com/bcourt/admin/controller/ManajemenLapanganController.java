
package com.bcourt.admin.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ManajemenLapanganController {

    // ==========================================
    // SIDEBAR NAVIGATION
    // ==========================================

    @FXML
    private javafx.scene.layout.HBox lapanganMenu1;

    @FXML
    private javafx.scene.layout.HBox dashboardMenu11;

    @FXML
    private javafx.scene.layout.HBox peralatanMenu1;

    @FXML
    private javafx.scene.layout.HBox transaksiMenu1;

    @FXML
    private javafx.scene.layout.HBox laporanMenu1;

    @FXML
    private javafx.scene.layout.HBox penggunaMenu1;

    @FXML
    private javafx.scene.layout.HBox profileMenu1;


    // ==========================================
    // BREADCRUMB DAN PENCARIAN
    // ==========================================

    @FXML
    private Hyperlink dashboardBreadcrumb;

    @FXML
    private TextField searchField;


    // ==========================================
    // STATISTIK LAPANGAN
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
    // TABEL MANAJEMEN LAPANGAN
    // ==========================================

    @FXML
    private GridPane courtTable;

    @FXML
    private Button addCourtButton;


    // ==========================================
    // TOMBOL AKSI BARIS 1
    // ==========================================

    @FXML
    private Button detailButton;

    @FXML
    private Button editButton;

    @FXML
    private Button deleteButton;


    // ==========================================
    // TOMBOL AKSI BARIS 2
    // ==========================================

    @FXML
    private Button detailButton2;

    @FXML
    private Button editButton2;

    @FXML
    private Button deleteButton2;


    // ==========================================
    // TOMBOL AKSI BARIS 3
    // ==========================================

    @FXML
    private Button detailButton3;

    @FXML
    private Button editButton3;

    @FXML
    private Button deleteButton3;


    // ==========================================
    // TOMBOL AKSI BARIS 4
    // ==========================================

    @FXML
    private Button detailButton4;

    @FXML
    private Button editButton4;

    @FXML
    private Button deleteButton4;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        // ======================================
        // SIDEBAR NAVIGATION
        // ======================================

        lapanganMenu1.setCursor(Cursor.HAND);
        dashboardMenu11.setCursor(Cursor.HAND);
        peralatanMenu1.setCursor(Cursor.HAND);
        transaksiMenu1.setCursor(Cursor.HAND);
        laporanMenu1.setCursor(Cursor.HAND);
        penggunaMenu1.setCursor(Cursor.HAND);
        profileMenu1.setCursor(Cursor.HAND);

        // Event Dashboard
        lapanganMenu1.setOnMouseClicked(event -> {
            bukaDashboard();
        });

        // Event Manajemen Lapangan
        dashboardMenu11.setOnMouseClicked(event -> {
            // Halaman Manajemen Lapangan sedang aktif.
        });

        // Event Manajemen Peralatan
        peralatanMenu1.setOnMouseClicked(event -> {
            bukaManajemenPeralatan();
        });

        // Event Data Transaksi
        transaksiMenu1.setOnMouseClicked(event -> {
            bukaDataTransaksi();
        });

        // Event Laporan
        laporanMenu1.setOnMouseClicked(event -> {
            bukaLaporan();
        });

        // Event Manajemen Pengguna
        penggunaMenu1.setOnMouseClicked(event -> {
            bukaManajemenPengguna();
        });

        // Event Profil
        profileMenu1.setOnMouseClicked(event -> {
            bukaProfil();
        });


        // ======================================
        // BREADCRUMB
        // ======================================

        dashboardBreadcrumb.setCursor(Cursor.HAND);

        dashboardBreadcrumb.setOnAction(event -> {
            bukaDashboard();
        });


        // ======================================
        // TOMBOL TAMBAH LAPANGAN
        // ======================================

        addCourtButton.setCursor(Cursor.HAND);

        addCourtButton.setOnAction(event -> {
            bukaTambahLapangan();
        });


        // ======================================
        // TOMBOL AKSI BARIS 1
        // ======================================

        detailButton.setCursor(Cursor.HAND);
        editButton.setCursor(Cursor.HAND);
        deleteButton.setCursor(Cursor.HAND);

        detailButton.setOnAction(event -> {
            bukaDetailLapangan(1);
        });

        editButton.setOnAction(event -> {
            bukaEditLapangan(1);
        });

        deleteButton.setOnAction(event -> {
            konfirmasiHapus(1);
        });


        // ======================================
        // TOMBOL AKSI BARIS 2
        // ======================================

        detailButton2.setCursor(Cursor.HAND);
        editButton2.setCursor(Cursor.HAND);
        deleteButton2.setCursor(Cursor.HAND);

        detailButton2.setOnAction(event -> {
            bukaDetailLapangan(2);
        });

        editButton2.setOnAction(event -> {
            bukaEditLapangan(2);
        });

        deleteButton2.setOnAction(event -> {
            konfirmasiHapus(2);
        });


        // ======================================
        // TOMBOL AKSI BARIS 3
        // ======================================

        detailButton3.setCursor(Cursor.HAND);
        editButton3.setCursor(Cursor.HAND);
        deleteButton3.setCursor(Cursor.HAND);

        detailButton3.setOnAction(event -> {
            bukaDetailLapangan(3);
        });

        editButton3.setOnAction(event -> {
            bukaEditLapangan(3);
        });

        deleteButton3.setOnAction(event -> {
            konfirmasiHapus(3);
        });


        // ======================================
        // TOMBOL AKSI BARIS 4
        // ======================================

        detailButton4.setCursor(Cursor.HAND);
        editButton4.setCursor(Cursor.HAND);
        deleteButton4.setCursor(Cursor.HAND);

        detailButton4.setOnAction(event -> {
            bukaDetailLapangan(4);
        });

        editButton4.setOnAction(event -> {
            bukaEditLapangan(4);
        });

        deleteButton4.setOnAction(event -> {
            konfirmasiHapus(4);
        });


        // ======================================
        // FITUR PENCARIAN
        // ======================================

        searchField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                cariLapangan(newValue);
            }
        );


        // ======================================
        // STATISTIK AWAL
        // ======================================

        perbaruiStatistik();
    }


    // ==========================================
    // NAVIGASI SIDEBAR
    // ==========================================

    private void bukaDashboard() {

        pindahHalaman(
            "/com/bcourt/admin/dashboard-admin.fxml"
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
    // NAVIGASI HALAMAN LAPANGAN
    // ==========================================

    private void bukaTambahLapangan() {

        pindahHalaman(
            "/com/bcourt/admin/tambah-lapangan.fxml"
        );
    }


    private void bukaDetailLapangan(int nomorBaris) {

        pindahHalaman(
            "/com/bcourt/admin/detail-lapangan.fxml"
        );
    }


    private void bukaEditLapangan(int nomorBaris) {

        pindahHalaman(
            "/com/bcourt/admin/edit-lapangan.fxml"
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
    // FITUR PENCARIAN LAPANGAN
    // ==========================================

    private void cariLapangan(String keyword) {

        String kataKunci = keyword == null
            ? ""
            : keyword.trim().toLowerCase();

        int jumlahBaris = courtTable.getRowConstraints().size() - 1;

        for (int row = 1; row <= jumlahBaris; row++) {

            String isiBaris = ambilIsiBaris(row);

            boolean cocok = isiBaris.contains(kataKunci);

            aturVisibilitasBaris(row, cocok);
        }
    }


    // ==========================================
    // MENGAMBIL ISI BARIS TABEL
    // ==========================================

    private String ambilIsiBaris(int row) {

        StringBuilder isi = new StringBuilder();

        for (Node node : courtTable.getChildren()) {

            Integer rowIndex = GridPane.getRowIndex(node);

            int currentRow = rowIndex == null
                ? 0
                : rowIndex;

            if (currentRow == row) {

                kumpulkanTeks(node, isi);
            }
        }

        return isi.toString().toLowerCase();
    }


    // ==========================================
    // MENGUMPULKAN TEKS NODE
    // ==========================================

    private void kumpulkanTeks(
        Node node,
        StringBuilder isi
    ) {

        if (node instanceof Label) {

            isi.append(
                ((Label) node).getText()
            ).append(" ");
        }

        if (node instanceof Parent) {

            Parent parent = (Parent) node;

            for (Node child : parent.getChildrenUnmodifiable()) {

                kumpulkanTeks(child, isi);
            }
        }
    }


    // ==========================================
    // MENGATUR VISIBILITAS BARIS
    // ==========================================

    private void aturVisibilitasBaris(
        int row,
        boolean tampil
    ) {

        for (Node node : courtTable.getChildren()) {

            Integer rowIndex = GridPane.getRowIndex(node);

            int currentRow = rowIndex == null
                ? 0
                : rowIndex;

            if (currentRow == row) {

                node.setVisible(tampil);
                node.setManaged(tampil);
            }
        }
    }


    // ==========================================
    // FITUR HAPUS LAPANGAN
    // ==========================================

    private void konfirmasiHapus(int row) {

        String namaLapangan = ambilNamaLapangan(row);

        Alert alert = new Alert(
            Alert.AlertType.CONFIRMATION
        );

        alert.setTitle("Konfirmasi Penghapusan");

        alert.setHeaderText(
            "Hapus " + namaLapangan + "?"
        );

        alert.setContentText(
            "Apakah Anda yakin ingin menghapus data lapangan ini?\n"
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

            hapusBarisTabel(row);

            perbaruiStatistik();

            tampilkanInfo(
                namaLapangan
                + " berhasil dihapus dari tampilan."
            );
        }
    }


    // ==========================================
    // MENGAMBIL NAMA LAPANGAN
    // ==========================================

    private String ambilNamaLapangan(int row) {

        for (Node node : courtTable.getChildren()) {

            Integer rowIndex = GridPane.getRowIndex(node);

            Integer columnIndex = GridPane.getColumnIndex(node);

            int currentRow = rowIndex == null
                ? 0
                : rowIndex;

            int currentColumn = columnIndex == null
                ? 0
                : columnIndex;

            if (
                currentRow == row
                && currentColumn == 1
                && node instanceof Label
            ) {

                return ((Label) node).getText();
            }
        }

        return "Lapangan";
    }


    // ==========================================
    // MENGHAPUS BARIS TABEL
    // ==========================================

    private void hapusBarisTabel(int row) {

        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : courtTable.getChildren()) {

            Integer rowIndex = GridPane.getRowIndex(node);

            int currentRow = rowIndex == null
                ? 0
                : rowIndex;

            if (currentRow == row) {

                nodesToRemove.add(node);

            } else if (currentRow > row) {

                GridPane.setRowIndex(
                    node,
                    currentRow - 1
                );
            }
        }

        courtTable.getChildren().removeAll(nodesToRemove);

        if (row < courtTable.getRowConstraints().size()) {

            courtTable.getRowConstraints().remove(row);
        }

        perbaruiNomorBaris();
    }


    // ==========================================
    // MEMPERBARUI NOMOR BARIS
    // ==========================================

    private void perbaruiNomorBaris() {

        for (Node node : courtTable.getChildren()) {

            if (!(node instanceof Label)) {
                continue;
            }

            Integer rowIndex = GridPane.getRowIndex(node);

            Integer columnIndex = GridPane.getColumnIndex(node);

            int row = rowIndex == null
                ? 0
                : rowIndex;

            int column = columnIndex == null
                ? 0
                : columnIndex;

            if (row > 0 && column == 0) {

                ((Label) node).setText(
                    String.valueOf(row)
                );
            }
        }
    }


    // ==========================================
    // MEMPERBARUI STATISTIK LAPANGAN
    // ==========================================

    private void perbaruiStatistik() {

        int total = 0;
        int tersedia = 0;
        int terpakai = 0;
        int perawatan = 0;

        int jumlahBaris = courtTable.getRowConstraints().size() - 1;

        for (int row = 1; row <= jumlahBaris; row++) {

            String isi = ambilIsiBaris(row);

            if (isi.isBlank()) {
                continue;
            }

            total++;

            if (isi.contains("tersedia")) {

                tersedia++;

            } else if (isi.contains("terpakai")) {

                terpakai++;

            } else if (isi.contains("perawatan")) {

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
