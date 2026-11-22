package com.bcourt.controller;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

import com.bcourt.App;
import com.bcourt.dao.PembayaranDAO;
import com.bcourt.model.Rental;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.paint.Color;

public class QrisKasirController {

    @FXML private Label totalLabel;
    @FXML private Label codeLabel;
    @FXML private Label customerLabel;
    @FXML private Label statusLabel;
    @FXML private ImageView qrImageView;

    private static Rental selectedRental;

    private final PembayaranDAO pembayaranDAO = new PembayaranDAO();

    private final NumberFormat rupiah =
            NumberFormat.getCurrencyInstance(new Locale("id", "ID"));

    public static void setTransactionData(Rental rental) {
        selectedRental = rental;
    }

    @FXML
    public void initialize() {

        if (selectedRental == null) {
            showError("Data transaksi tidak ditemukan.");
            return;
        }

        totalLabel.setText(
                formatRupiah(selectedRental.getTotalBiaya())
        );

        codeLabel.setText(selectedRental.getCode());
        customerLabel.setText(selectedRental.getCustomer());

        statusLabel.setText("Menunggu pembayaran");

        generateQRCode();
    }

    private String formatRupiah(int amount) {
        return rupiah.format(amount).replace(",00", "");
    }

    private int getIdSewa() {

        if (selectedRental == null) {
            return -1;
        }

        String kode = selectedRental.getCode();

        if (kode == null || !kode.startsWith("TRX-")) {
            return -1;
        }

        try {
            return Integer.parseInt(kode.substring(4));
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    private void generateQRCode() {

        try {

            String qrData =
                    "BCOURT-SIMULASI"
                    + "|KODE=" + selectedRental.getCode()
                    + "|PELANGGAN=" + selectedRental.getCustomer()
                    + "|TOTAL=" + selectedRental.getTotalBiaya();

            QRCodeWriter qrWriter = new QRCodeWriter();

            BitMatrix matrix = qrWriter.encode(
                    qrData,
                    BarcodeFormat.QR_CODE,
                    300,
                    300
            );

            WritableImage image = new WritableImage(300, 300);
            PixelWriter pixelWriter = image.getPixelWriter();

            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {

                    pixelWriter.setColor(
                            x,
                            y,
                            matrix.get(x, y) ? Color.BLACK : Color.WHITE
                    );
                }
            }

            qrImageView.setImage(image);

        } catch (WriterException e) {
            showError("Gagal membuat QR Code: " + e.getMessage());
        }
    }

    @FXML
    private void confirmPayment() {

        if (selectedRental == null) {
            showError("Data transaksi tidak ditemukan.");
            return;
        }

        int idSewa = getIdSewa();

        if (idSewa <= 0) {
            showError("Kode transaksi tidak valid.");
            return;
        }

        boolean berhasil = pembayaranDAO.konfirmasiPembayaran(idSewa);

        if (!berhasil) {
            showError(
                    "Pembayaran gagal diperbarui di database. "
                    + "Pastikan status pembayaran masih Pending."
            );
            return;
        }

        selectedRental.setStatus("Lunas");

        statusLabel.setText("Pembayaran berhasil dikonfirmasi.");

        Alert alert = new Alert(Alert.AlertType.INFORMATION);

        alert.setTitle("Pembayaran QRIS");
        alert.setHeaderText("Simulasi Pembayaran Berhasil");

        alert.setContentText(
                "Kode Transaksi: " + selectedRental.getCode()
                + "\nTotal Pembayaran: "
                + formatRupiah(selectedRental.getTotalBiaya())
                + "\nStatus: Lunas"
        );

        alert.showAndWait();

        StrukKasirController.setTransactionData(
                selectedRental,
                "QRIS"
        );

        try {
            App.setRoot("struk-pembayaran");

        } catch (IOException e) {
            showError("Gagal membuka struk: " + e.getMessage());
        }
    }

    @FXML
    private void backToDashboard() {

        try {
            App.setRoot("dashboard-user");

        } catch (IOException e) {
            showError("Gagal kembali ke Dashboard: " + e.getMessage());
        }
    }

    private void showError(String message) {

        Alert alert = new Alert(Alert.AlertType.ERROR);

        alert.setTitle("Kesalahan");
        alert.setHeaderText("Terjadi Kesalahan");
        alert.setContentText(message);

        alert.showAndWait();
    }
}
