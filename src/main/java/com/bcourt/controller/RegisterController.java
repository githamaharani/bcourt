package com.bcourt.controller;

import java.io.IOException;

import com.bcourt.App;
import com.bcourt.dao.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class RegisterController {

    @FXML private TextField txtFullName;
    @FXML private TextField txtUsername;
    @FXML private TextField txtPhone;

    @FXML private PasswordField pwdPassword;
    @FXML private PasswordField pwdConfirmPassword;

    @FXML private Button btnRegister;
    @FXML private Hyperlink lnkLogin;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        // Persiapan halaman Register
    }

    // Proses pendaftaran akun Kasir
    @FXML
    private void handleRegister() {

        String nama = txtFullName.getText().trim();
        String username = txtUsername.getText().trim();
        String phone = txtPhone.getText().trim();
        String password = pwdPassword.getText();
        String konfirmasi = pwdConfirmPassword.getText();

        // Validasi kolom kosong
        if (nama.isEmpty() || username.isEmpty()
                || phone.isEmpty() || password.isEmpty()
                || konfirmasi.isEmpty()) {

            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Semua kolom wajib diisi."
            );
            return;
        }

        // Validasi username
        if (username.length() < 4) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Username minimal 4 karakter."
            );
            return;
        }

        // Validasi nomor HP
        if (!phone.matches("[0-9]{10,15}")) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Nomor HP harus terdiri dari 10 sampai 15 angka."
            );
            return;
        }

        // Validasi password
        if (password.length() < 8) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Password minimal 8 karakter."
            );
            return;
        }

        // Validasi konfirmasi password
        if (!password.equals(konfirmasi)) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Konfirmasi password tidak cocok."
            );
            return;
        }

        // Periksa username yang sudah terdaftar
        if (userDAO.isUsernameExists(username)) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Username sudah digunakan. Silakan gunakan username lain."
            );
            return;
        }

        // Simpan akun baru dengan role otomatis Kasir
        boolean berhasil = userDAO.registerUser(
                nama,
                username,
                password
        );

        if (berhasil) {

            tampilkanAlert(
                    Alert.AlertType.INFORMATION,
                    "Registrasi berhasil! Akun Kasir sudah tersimpan di database."
            );

            try {
                App.setRoot("login");

            } catch (IOException e) {
                e.printStackTrace();

                tampilkanAlert(
                        Alert.AlertType.ERROR,
                        "Registrasi berhasil, tetapi halaman Login tidak dapat dibuka."
                );
            }

        } else {

            tampilkanAlert(
                    Alert.AlertType.ERROR,
                    "Registrasi gagal. Periksa koneksi database atau username kamu."
            );
        }
    }

    // Kembali ke halaman Login
    @FXML
    private void handleLogin() {

        try {
            App.setRoot("login");

        } catch (IOException e) {
            e.printStackTrace();

            tampilkanAlert(
                    Alert.AlertType.ERROR,
                    "Halaman Login tidak dapat dibuka."
            );
        }
    }

    private void tampilkanAlert(
            Alert.AlertType type,
            String pesan) {

        Alert alert = new Alert(type);
        alert.setTitle("B-Court");
        alert.setHeaderText(null);
        alert.setContentText(pesan);
        alert.showAndWait();
    }
}
