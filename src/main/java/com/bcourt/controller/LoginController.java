package com.bcourt.controller;

import java.io.IOException;

import com.bcourt.App;
import com.bcourt.dao.UserDAO;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField pwdPassword;
    @FXML private TextField txtPasswordVisible;
    @FXML private Button btnTogglePassword;
    @FXML private CheckBox chkRememberMe;
    @FXML private Hyperlink lnkForgotPassword;
    @FXML private Button btnSignIn;
    @FXML private Hyperlink lnkRegister;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void initialize() {
        if (txtPasswordVisible != null) {
            txtPasswordVisible.setManaged(false);
            txtPasswordVisible.setVisible(false);
        }
    }

    // Tampilkan atau sembunyikan password
    @FXML
    private void togglePassword() {

        if (txtPasswordVisible.isVisible()) {

            pwdPassword.setText(txtPasswordVisible.getText());

            txtPasswordVisible.setVisible(false);
            txtPasswordVisible.setManaged(false);

            pwdPassword.setVisible(true);
            pwdPassword.setManaged(true);

            btnTogglePassword.setText("Lihat sandi");

        } else {

            txtPasswordVisible.setText(pwdPassword.getText());

            pwdPassword.setVisible(false);
            pwdPassword.setManaged(false);

            txtPasswordVisible.setVisible(true);
            txtPasswordVisible.setManaged(true);

            btnTogglePassword.setText("Sembunyikan");
        }
    }

    // Proses login menggunakan database
    @FXML
    private void handleLogin() {

        String username = txtUsername.getText().trim();

        String password = pwdPassword.isVisible()
                ? pwdPassword.getText()
                : txtPasswordVisible.getText();

        if (username.isEmpty()) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Username harus diisi."
            );
            return;
        }

        if (password.isEmpty()) {
            tampilkanAlert(
                    Alert.AlertType.WARNING,
                    "Password harus diisi."
            );
            return;
        }

        try {
            String role = userDAO.loginUser(username, password);

            if (role == null) {
                tampilkanAlert(
                        Alert.AlertType.ERROR,
                        "Username atau password salah, atau koneksi database bermasalah."
                );
                return;
            }

            if (role.equalsIgnoreCase("Admin")) {

                tampilkanAlert(
                        Alert.AlertType.INFORMATION,
                        "Login Admin berhasil."
                );

                App.setRoot("dashboard-admin");

            } else if (role.equalsIgnoreCase("Kasir")) {

                tampilkanAlert(
                        Alert.AlertType.INFORMATION,
                        "Login Kasir berhasil."
                );

                App.setRoot("dashboard-user");

            } else {

                tampilkanAlert(
                        Alert.AlertType.ERROR,
                        "Role pengguna tidak dikenali: " + role
                );
            }

        } catch (IOException e) {
            e.printStackTrace();

            tampilkanAlert(
                    Alert.AlertType.ERROR,
                    "Dashboard tidak dapat dibuka. Periksa lokasi file FXML."
            );
        }
    }

    // Buka halaman Register
    @FXML
    private void handleRegister() {

        try {
            App.setRoot("register");

        } catch (IOException e) {
            e.printStackTrace();

            tampilkanAlert(
                    Alert.AlertType.ERROR,
                    "Halaman Register tidak dapat dibuka."
            );
        }
    }

    @FXML
    private void handleForgotPassword() {

        tampilkanAlert(
                Alert.AlertType.INFORMATION,
                "Silakan hubungi administrator untuk bantuan reset password."
        );
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
