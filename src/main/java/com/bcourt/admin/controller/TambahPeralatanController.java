
package com.bcourt.admin.controller;

import java.io.File;
import java.io.IOException;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class TambahPeralatanController {

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
    // FORM PERALATAN
    // ==========================================

    @FXML private TextField equipmentNameField;
    @FXML private ComboBox<String> equipmentTypeComboBox;
    @FXML private TextField equipmentQuantityField;
    @FXML private ComboBox<String> equipmentConditionComboBox;
    @FXML private ComboBox<String> equipmentStatusComboBox;
    @FXML private TextField equipmentRentalPriceField;
    @FXML private TextArea equipmentDescriptionField;


    // ==========================================
    // FOTO PERALATAN
    // ==========================================

    @FXML private StackPane photoDropZone;
    @FXML private Label photoDropDescription;

    private File fotoTerpilih;


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    @FXML private Button cancelButton;
    @FXML private Button saveEquipmentButton;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        aturSidebar();
        aturBreadcrumb();
        aturPilihanForm();
        aturTombol();
        aturPemilihanFoto();
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
    // PILIHAN FORM
    // ==========================================

    private void aturPilihanForm() {

        equipmentTypeComboBox.getItems().setAll(
            "Raket",
            "Shuttlecock",
            "Net",
            "Aksesori"
        );

        equipmentConditionComboBox.getItems().setAll(
            "Baik",
            "Cukup",
            "Rusak"
        );

        equipmentStatusComboBox.getItems().setAll(
            "Tersedia",
            "Terpakai",
            "Perawatan"
        );
    }


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    private void aturTombol() {

        cancelButton.setCursor(Cursor.HAND);
        saveEquipmentButton.setCursor(Cursor.HAND);

        cancelButton.setOnAction(event -> {
            bukaManajemenPeralatan();
        });

        saveEquipmentButton.setOnAction(event -> {
            simpanPeralatan();
        });
    }


    // ==========================================
    // PEMILIHAN FOTO
    // ==========================================

    private void aturPemilihanFoto() {

        photoDropZone.setCursor(Cursor.HAND);
        photoDropZone.setPickOnBounds(true);

        photoDropZone.setOnMouseClicked(event -> {
            pilihFoto();
        });
    }


    private void pilihFoto() {

        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Pilih Foto Peralatan");

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

        if (file == null) {
            return;
        }

        if (file.length() > 5 * 1024 * 1024) {

            tampilkanPeringatan(
                "Ukuran foto maksimal 5 MB."
            );

            return;
        }

        fotoTerpilih = file;

        photoDropDescription.setText(
            "Foto dipilih: " + file.getName()
        );
    }


    // ==========================================
    // VALIDASI FORM
    // ==========================================

    private boolean validasiForm() {

        String nama = equipmentNameField.getText().trim();
        String jumlah = equipmentQuantityField.getText().trim();
        String harga = equipmentRentalPriceField.getText().trim();

        // Validasi nama
        if (nama.isEmpty()) {

            tampilkanPeringatan(
                "Nama peralatan wajib diisi."
            );

            equipmentNameField.requestFocus();
            return false;
        }

        // Validasi jenis
        if (equipmentTypeComboBox.getValue() == null) {

            tampilkanPeringatan(
                "Silakan pilih jenis peralatan."
            );

            equipmentTypeComboBox.requestFocus();
            return false;
        }

        // Validasi jumlah
        if (jumlah.isEmpty()) {

            tampilkanPeringatan(
                "Jumlah peralatan wajib diisi."
            );

            equipmentQuantityField.requestFocus();
            return false;
        }

        try {

            int nilaiJumlah = Integer.parseInt(jumlah);

            if (nilaiJumlah <= 0) {

                tampilkanPeringatan(
                    "Jumlah peralatan harus lebih dari 0."
                );

                equipmentQuantityField.requestFocus();
                return false;
            }

        } catch (NumberFormatException e) {

            tampilkanPeringatan(
                "Jumlah peralatan harus berupa angka bulat."
            );

            equipmentQuantityField.requestFocus();
            return false;
        }

        // Validasi kondisi
        if (equipmentConditionComboBox.getValue() == null) {

            tampilkanPeringatan(
                "Silakan pilih kondisi peralatan."
            );

            equipmentConditionComboBox.requestFocus();
            return false;
        }

        // Validasi status
        if (equipmentStatusComboBox.getValue() == null) {

            tampilkanPeringatan(
                "Silakan pilih status peralatan."
            );

            equipmentStatusComboBox.requestFocus();
            return false;
        }

        // Harga sewa bersifat opsional
        if (!harga.isEmpty()) {

            try {

                int nilaiHarga = Integer.parseInt(harga);

                if (nilaiHarga < 0) {

                    tampilkanPeringatan(
                        "Harga sewa tidak boleh negatif."
                    );

                    equipmentRentalPriceField.requestFocus();
                    return false;
                }

            } catch (NumberFormatException e) {

                tampilkanPeringatan(
                    "Harga sewa harus berupa angka."
                );

                equipmentRentalPriceField.requestFocus();
                return false;
            }
        }

        return true;
    }


    // ==========================================
    // SIMPAN PERALATAN
    // ==========================================

    private void simpanPeralatan() {

        if (!validasiForm()) {
            return;
        }

        String deskripsi = equipmentDescriptionField
            .getText()
            .trim();

        if (deskripsi.isEmpty()) {
            deskripsi = "-";
        }

        String harga = equipmentRentalPriceField
            .getText()
            .trim();

        if (harga.isEmpty()) {
            harga = "Tidak ditentukan";
        }

        tampilkanInfo(
            "Form peralatan berhasil divalidasi.\n\n"
            + "Nama: " + equipmentNameField.getText().trim()
            + "\nJenis: " + equipmentTypeComboBox.getValue()
            + "\nJumlah: " + equipmentQuantityField.getText().trim()
            + "\nKondisi: " + equipmentConditionComboBox.getValue()
            + "\nStatus: " + equipmentStatusComboBox.getValue()
            + "\nHarga Sewa: " + harga
            + "\nDeskripsi: " + deskripsi
            + "\nFoto: "
            + (fotoTerpilih == null
                ? "Belum dipilih"
                : fotoTerpilih.getName())
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
