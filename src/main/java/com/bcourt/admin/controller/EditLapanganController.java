
package com.bcourt.admin.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.bcourt.App;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class EditLapanganController {

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

    @FXML private CheckBox ledCheckBox;
    @FXML private CheckBox floorCheckBox;
    @FXML private CheckBox acCheckBox;
    @FXML private CheckBox scoreboardCheckBox;
    @FXML private CheckBox seatCheckBox;


    // ==========================================
    // FOTO LAPANGAN
    // ==========================================

    @FXML private ImageView currentCourtImage;
    @FXML private ImageView courtPreviewImage;

    @FXML private Button removePhotoButton;
    @FXML private VBox replacePhotoContainer;
    @FXML private Label replacePhotoDescription;

    private File fotoTerpilih;
    private boolean fotoDihapus = false;


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    @FXML private Button cancelButton;
    @FXML private Button saveChangesButton;


    // ==========================================
    // PREVIEW LAPANGAN
    // ==========================================

    @FXML private Label previewCourtName;
    @FXML private Label previewCourtStatus;

    @FXML private Label previewNumberValue;
    @FXML private Label previewTypeValue;
    @FXML private Label previewPriceValue;

    @FXML private Label previewFacility1;
    @FXML private Label previewFacility2;
    @FXML private Label previewFacility3;

    @FXML private Label previewDescriptionLabel;


    // ==========================================
    // INITIALIZE
    // ==========================================

    @FXML
    private void initialize() {

        aturSidebar();
        aturBreadcrumb();
        aturPilihanStatus();
        aturTombol();
        aturFoto();
        pasangEventPreview();

        // Preview awal
        perbaruiPreview();
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
            "Terpakai",
            "Perawatan"
        );

        courtStatusComboBox.setValue("Tersedia");
    }


    // ==========================================
    // TOMBOL FORM
    // ==========================================

    private void aturTombol() {

        cancelButton.setCursor(Cursor.HAND);
        saveChangesButton.setCursor(Cursor.HAND);
        removePhotoButton.setCursor(Cursor.HAND);

        // Batal
        cancelButton.setOnAction(event -> {
            bukaDetailLapangan();
        });

        // Simpan perubahan
        saveChangesButton.setOnAction(event -> {
            simpanPerubahan();
        });

        // Hapus foto
        removePhotoButton.setOnAction(event -> {
            hapusFoto();
        });
    }


    // ==========================================
    // FOTO LAPANGAN
    // ==========================================

    private void aturFoto() {

        replacePhotoContainer.setCursor(Cursor.HAND);

        replacePhotoContainer.setOnMouseClicked(event -> {
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

        Stage stage = (Stage) replacePhotoContainer
            .getScene()
            .getWindow();

        File file = fileChooser.showOpenDialog(stage);

        if (file != null) {

            fotoTerpilih = file;
            fotoDihapus = false;

            Image gambar = new Image(
                file.toURI().toString()
            );

            currentCourtImage.setImage(gambar);
            courtPreviewImage.setImage(gambar);

            replacePhotoDescription.setText(
                file.getName()
            );
        }
    }


    // ==========================================
    // HAPUS FOTO
    // ==========================================

    private void hapusFoto() {

        fotoTerpilih = null;
        fotoDihapus = true;

        currentCourtImage.setImage(null);
        courtPreviewImage.setImage(null);

        replacePhotoDescription.setText(
            "Pilih foto pengganti (PNG, JPG)"
        );
    }


    // ==========================================
    // EVENT PREVIEW
    // ==========================================

    private void pasangEventPreview() {

        courtNameField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        courtNumberField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        courtPriceField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        courtDescriptionField.textProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        courtStatusComboBox.valueProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        singleCourtRadio.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        doubleCourtRadio.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        ledCheckBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        floorCheckBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        acCheckBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        scoreboardCheckBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );

        seatCheckBox.selectedProperty().addListener(
            (observable, oldValue, newValue) -> {
                perbaruiPreview();
            }
        );
    }


    // ==========================================
    // MEMPERBARUI PREVIEW
    // ==========================================

    private void perbaruiPreview() {

        // Nama lapangan
        String nama = courtNameField.getText().trim();

        previewCourtName.setText(
            nama.isEmpty() ? "Nama Lapangan" : nama
        );

        // Nomor lapangan
        String nomor = courtNumberField.getText().trim();

        previewNumberValue.setText(
            nomor.isEmpty() ? "-" : nomor
        );

        // Jenis lapangan
        String jenis = singleCourtRadio.isSelected()
            ? "Tunggal"
            : "Ganda";

        previewTypeValue.setText(jenis);

        // Harga lapangan
        String harga = courtPriceField.getText()
            .replaceAll("[^0-9]", "");

        if (harga.isEmpty()) {

            previewPriceValue.setText("Rp 0");

        } else {

            try {

                previewPriceValue.setText(
                    "Rp " + formatHarga(
                        Integer.parseInt(harga)
                    )
                );

            } catch (NumberFormatException e) {

                previewPriceValue.setText("Rp 0");
            }
        }

        // Status lapangan
        String status = courtStatusComboBox.getValue();

        previewCourtStatus.setText(
            status == null ? "Belum dipilih" : status
        );

        aturWarnaStatus(status);

        // Deskripsi
        String deskripsi = courtDescriptionField
            .getText()
            .trim();

        previewDescriptionLabel.setText(
            deskripsi.isEmpty()
                ? "Belum ada deskripsi."
                : deskripsi
        );

        // Fasilitas
        tampilkanPreviewFasilitas();
    }


    // ==========================================
    // FORMAT HARGA
    // ==========================================

    private String formatHarga(int harga) {

        return String.format(
            Locale.forLanguageTag("id-ID"),
            "%,d",
            harga
        ).replace(',', '.');
    }


    // ==========================================
    // WARNA STATUS PREVIEW
    // ==========================================

    private void aturWarnaStatus(String status) {

        if ("Tersedia".equalsIgnoreCase(status)) {

            previewCourtStatus.setStyle(
                "-fx-background-color: #E3F6EA;"
                + "-fx-background-radius: 15.81;"
                + "-fx-font-size: 11.07px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #2E7D4F;"
                + "-fx-padding: 4.74 12.65 4.74 12.65;"
            );

        } else if ("Terpakai".equalsIgnoreCase(status)) {

            previewCourtStatus.setStyle(
                "-fx-background-color: #FDE6E8;"
                + "-fx-background-radius: 15.81;"
                + "-fx-font-size: 11.07px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #A51F26;"
                + "-fx-padding: 4.74 12.65 4.74 12.65;"
            );

        } else {

            previewCourtStatus.setStyle(
                "-fx-background-color: #FFF2D6;"
                + "-fx-background-radius: 15.81;"
                + "-fx-font-size: 11.07px;"
                + "-fx-font-weight: bold;"
                + "-fx-text-fill: #B7791F;"
                + "-fx-padding: 4.74 12.65 4.74 12.65;"
            );
        }
    }


    // ==========================================
    // PREVIEW FASILITAS
    // ==========================================

    private void tampilkanPreviewFasilitas() {

        List<String> fasilitas = ambilFasilitas();

        Label[] labelFasilitas = {
            previewFacility1,
            previewFacility2,
            previewFacility3
        };

        for (int i = 0; i < labelFasilitas.length; i++) {

            if (i < fasilitas.size()) {

                labelFasilitas[i].setText(
                    fasilitas.get(i)
                );

                labelFasilitas[i].setVisible(true);
                labelFasilitas[i].setManaged(true);

            } else {

                labelFasilitas[i].setVisible(false);
                labelFasilitas[i].setManaged(false);
            }
        }
    }


    // ==========================================
    // MENGAMBIL FASILITAS
    // ==========================================

    private List<String> ambilFasilitas() {

        List<String> fasilitas = new ArrayList<>();

        if (ledCheckBox.isSelected()) {
            fasilitas.add("Penerangan (LED)");
        }

        if (floorCheckBox.isSelected()) {
            fasilitas.add("Karpet/Lantai Standar");
        }

        if (acCheckBox.isSelected()) {
            fasilitas.add("AC");
        }

        if (scoreboardCheckBox.isSelected()) {
            fasilitas.add("Papan Skor");
        }

        if (seatCheckBox.isSelected()) {
            fasilitas.add("Tempat Duduk");
        }

        return fasilitas;
    }


    // ==========================================
    // VALIDASI FORM
    // ==========================================

    private boolean validasiForm() {

        String nama = courtNameField.getText().trim();
        String nomor = courtNumberField.getText().trim();

        String harga = courtPriceField.getText()
            .replaceAll("[^0-9]", "");

        // Nama
        if (nama.isEmpty()) {

            tampilkanPeringatan(
                "Nama lapangan wajib diisi."
            );

            courtNameField.requestFocus();
            return false;
        }

        // Nomor
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

        // Jenis
        if (
            !singleCourtRadio.isSelected()
            && !doubleCourtRadio.isSelected()
        ) {

            tampilkanPeringatan(
                "Silakan pilih jenis lapangan."
            );

            return false;
        }

        // Harga
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
                "Format harga sewa tidak valid."
            );

            courtPriceField.requestFocus();
            return false;
        }

        // Status
        if (courtStatusComboBox.getValue() == null) {

            tampilkanPeringatan(
                "Silakan pilih status lapangan."
            );

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

        /*
         * Form sudah lolos validasi.
         *
         * Perubahan belum disimpan permanen.
         * Nantinya proses UPDATE akan dilakukan
         * melalui LapanganDAO.
         */

        tampilkanInfo(
            "Form perubahan lapangan berhasil divalidasi.\n\n"
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


    private void bukaDetailLapangan() {

        pindahHalaman(
            "/com/bcourt/admin/detail-lapangan.fxml"
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
