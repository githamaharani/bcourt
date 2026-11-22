package com.bcourt.dao;

import com.bcourt.util.Koneksi;
import com.bcourt.util.PasswordUtil;
import com.bcourt.util.UserSession;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean isUsernameExists(String username) {

        String sql = "SELECT id_pengguna FROM pengguna WHERE username = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                return rs.next();
            }

        } catch (SQLException e) {
            System.err.println("Gagal memeriksa username: " + e.getMessage());
            return false;
        }
    }

    public boolean registerUser(
            String nama,
            String username,
            String password) {

        String sql = "INSERT INTO pengguna "
                + "(nama, username, password, role) "
                + "VALUES (?, ?, ?, ?)";

        String hashedPassword = PasswordUtil.hashPassword(password);

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, username);
            stmt.setString(3, hashedPassword);
            stmt.setString(4, "Kasir");

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal mendaftarkan pengguna: " + e.getMessage());
            return false;
        }
    }

    public String loginUser(String username, String password) {

        String sql = "SELECT password, role FROM pengguna WHERE username = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String storedPassword = rs.getString("password");
                    String role = rs.getString("role");

                    if (PasswordUtil.verifyPassword(password, storedPassword)) {
                        return role;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal login: " + e.getMessage());
        }

        return null;
    }

    public boolean loginAndSetSession(String username, String password) {

        String sql = "SELECT id_pengguna, nama, username, password, role "
                + "FROM pengguna WHERE username = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {

                if (rs.next()) {

                    String storedPassword = rs.getString("password");

                    if (PasswordUtil.verifyPassword(password, storedPassword)) {

                        UserSession.setUser(
                                rs.getInt("id_pengguna"),
                                rs.getString("nama"),
                                rs.getString("username"),
                                rs.getString("role")
                        );

                        return true;
                    }
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal login dan mengambil session: "
                    + e.getMessage());
        }

        return false;
    }
}
