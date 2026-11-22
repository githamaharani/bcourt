package com.bcourt.dao;

import com.bcourt.model.Admin;
import com.bcourt.model.Kasir;
import com.bcourt.model.User;
import com.bcourt.util.Koneksi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/** Tabel pengguna <-> User (Admin / Kasir). Password disimpan sebagai hash SHA-256. */
public class PenggunaDAO {

    public static String hash(String plain) {
        try {
            byte[] d = MessageDigest.getInstance("SHA-256").digest(plain.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String role(User u) {
        return u instanceof Admin ? "Admin" : "Kasir";
    }

    private static User map(ResultSet rs) throws SQLException {
        User u = "Admin".equals(rs.getString("role")) ? new Admin() : new Kasir();
        u.setIdUser(rs.getInt("id_pengguna"));
        u.setNama(rs.getString("nama"));
        u.setUsername(rs.getString("username"));
        u.setPassword(rs.getString("password"));
        return u;
    }

    /** Password pada objek User dianggap teks asli dan di-hash di sini. */
    public void insert(User u) throws SQLException {
        String sql = "INSERT INTO pengguna (nama, username, password, role) VALUES (?,?,?,?)";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, u.getNama());
            ps.setString(2, u.getUsername());
            ps.setString(3, hash(u.getPassword()));
            ps.setString(4, role(u));
            ps.executeUpdate();
            try (ResultSet k = ps.getGeneratedKeys()) {
                if (k.next()) u.setIdUser(k.getInt(1));
            }
        }
    }

    /** Tidak mengubah password; pakai gantiPassword() untuk itu. */
    public boolean update(User u) throws SQLException {
        String sql = "UPDATE pengguna SET nama=?, username=?, role=? WHERE id_pengguna=?";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, u.getNama());
            ps.setString(2, u.getUsername());
            ps.setString(3, role(u));
            ps.setInt(4, u.getIdUser());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean gantiPassword(int idPengguna, String passwordBaru) throws SQLException {
        String sql = "UPDATE pengguna SET password=? WHERE id_pengguna=?";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, hash(passwordBaru));
            ps.setInt(2, idPengguna);
            return ps.executeUpdate() > 0;
        }
    }

    public boolean delete(int idPengguna) throws SQLException {
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement("DELETE FROM pengguna WHERE id_pengguna=?")) {
            ps.setInt(1, idPengguna);
            return ps.executeUpdate() > 0;
        }
    }

    public User getById(int idPengguna) throws SQLException {
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement("SELECT * FROM pengguna WHERE id_pengguna=?")) {
            ps.setInt(1, idPengguna);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }

    public List<User> getAll() throws SQLException {
        List<User> list = new ArrayList<>();
        try (Connection c = Koneksi.getConnection();
             Statement st = c.createStatement();
             ResultSet rs = st.executeQuery("SELECT * FROM pengguna ORDER BY nama")) {
            while (rs.next()) list.add(map(rs));
        }
        return list;
    }

    /** Mengembalikan Admin/Kasir jika cocok, atau null jika username/password salah. */
    public User login(String username, String passwordPlain) throws SQLException {
        String sql = "SELECT * FROM pengguna WHERE username=? AND password=?";
        try (Connection c = Koneksi.getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, username);
            ps.setString(2, hash(passwordPlain));
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? map(rs) : null;
            }
        }
    }
}