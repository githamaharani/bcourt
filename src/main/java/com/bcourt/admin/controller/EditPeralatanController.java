
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditPeralatanController {

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
    @FXML private TextArea equipmentDescriptionField;


    // ==========================================
    // FOTO PERALATAN
    // ==========================================

    @FXML private ImageView currentEquipmentImage;
    @FXML private VBox replacePhotoContainer;
    @FXML private Label replacePhotoDescription;

    private File fotoTerpilih;


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    @FXML private Button cancelButton;
    @FXML private Button saveChangesButton;


    // ==========================================
    // PREVIEW PERALATAN
    // ==========================================

    @FXML private ImageView equipmentPreviewImage;
    @FXML private Label previewEquipmentName;
    @FXML private Label previewEquipmentStatus;
    @FXML private Label previewTypeValue;
    @FXML private Label previewQuantityValue;
    @FXML private Label previewConditionValue;
    @FXML private Label previewDescriptionLabel;


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
        aturPreview();
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

        equipmentTypeComboBox.setValue("Raket");
        equipmentConditionComboBox.setValue("Baik");
        equipmentStatusComboBox.setValue("Tersedia");
    }


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    private void aturTombol() {

        cancelButton.setCursor(Cursor.HAND);
        saveChangesButton.setCursor(Cursor.HAND);

        cancelButton.setOnAction(event -> {
            bukaDetailPeralatan();
        });

        saveChangesButton.setOnAction(event -> {
            simpanPerubahan();
        });
    }


    // ==========================================
    // PEMILIHAN FOTO
    // ==========================================

    private void aturPemilihanFoto() {

        replacePhotoContainer.setCursor(Cursor.HAND);
        replacePhotoContainer.setPickOnBounds(true);

        replacePhotoContainer.setOnMouseClicked(event -> {
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

        Stage stage = (Stage) replacePhotoContainer
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

        try {

            Image gambar = new Image(
                file.toURI().toString()
            );

            if (gambar.isError()) {
                throw new IllegalArgumentException(
                    "File gambar tidak valid."
                );
            }

            fotoTerpilih = file;

            currentEquipmentImage.setImage(gambar);
            equipmentPreviewImage.setImage(gambar);

            replacePhotoDescription.setText(
                "Foto dipilih: " + file.getName()
            );

        } catch (Exception e) {

            tampilkanError(
                "Gagal Memuat Foto",
                "File gambar tidak dapat dibuka."
            );
        }
    }


    // ==========================================
    // PREVIEW PERALATAN
    // ==========================================

    private void aturPreview() {

        equipmentNameField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        equipmentQuantityField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        equipmentDescriptionField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        equipmentTypeComboBox.valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        equipmentConditionComboBox.valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        equipmentStatusComboBox.valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        perbaruiPreview();
    }


    private void perbaruiPreview() {

        // Nama peralatan
        String nama = equipmentNameField.getText().trim();

        previewEquipmentName.setText(
            nama.isEmpty() ? "Nama Peralatan" : nama
        );

        // Jenis peralatan
        String jenis = equipmentTypeComboBox.getValue();

        previewTypeValue.setText(
            jenis == null ? "-" : jenis
        );

        // Jumlah peralatan
        String jumlah = equipmentQuantityField.getText().trim();

        previewQuantityValue.setText(
            jumlah.isEmpty() ? "0 Unit" : jumlah + " Unit"
        );

        // Kondisi peralatan
        String kondisi = equipmentConditionComboBox.getValue();

        previewConditionValue.setText(
            kondisi == null ? "-" : kondisi
        );

        // Deskripsi peralatan
        String deskripsi = equipmentDescriptionField
            .getText()
            .trim();

        previewDescriptionLabel.setText(
            deskripsi.isEmpty() ? "-" : deskripsi
        );

        // Status peralatan
        String status = equipmentStatusComboBox.getValue();

        previewEquipmentStatus.setText(
            status == null ? "-" : status
        );

        aturWarnaPreviewStatus(status);
    }


    // ==========================================
    // WARNA STATUS PREVIEW
    // ==========================================

    private void aturWarnaPreviewStatus(String status) {

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

        } else {

            warnaLatar = "#FDE3D1";
            warnaTeks = "#B8480A";
        }

        previewEquipmentStatus.setStyle(
            "-fx-background-color: " + warnaLatar + ";"
            + "-fx-background-radius: 14;"
            + "-fx-font-weight: bold;"
            + "-fx-text-fill: " + warnaTeks + ";"
            + "-fx-padding: 3 12 3 12;"
        );
    }


    // ==========================================
    // VALIDASI FORM
    // ==========================================

    private boolean validasiForm() {

        String nama = equipmentNameField.getText().trim();
        String jumlah = equipmentQuantityField.getText().trim();

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

        return true;
    }


    // ==========================================
    // SIMPAN PERUBAHAN
    // ==========================================

    private void simpanPerubahan() {

        if (!validasiForm()) {
            return;
        }

        String deskripsi = equipmentDescriptionField
            .getText()
            .trim();

        if (deskripsi.isEmpty()) {
            deskripsi = "-";
        }

        tampilkanInfo(
            "Perubahan peralatan berhasil divalidasi.\n\n"
            + "Nama: " + equipmentNameField.getText().trim()
            + "\nJenis: " + equipmentTypeComboBox.getValue()
            + "\nJumlah: " + equipmentQuantityField.getText().trim()
            + "\nKondisi: " + equipmentConditionComboBox.getValue()
            + "\nStatus: " + equipmentStatusComboBox.getValue()
            + "\nDeskripsi: " + deskripsi
            + "\nFoto: "
            + (fotoTerpilih == null
                ? "Menggunakan foto sebelumnya"
                : fotoTerpilih.getName())
            + "\n\nPerubahan belum disimpan permanen karena "
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


    private void bukaDetailPeralatan() {

        pindahHalaman(
            "/com/bcourt/admin/detail-peralatan.fxml"
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
