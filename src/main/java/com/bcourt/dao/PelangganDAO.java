package com.bcourt.dao;

import com.bcourt.model.Pelanggan;
import com.bcourt.util.Koneksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PelangganDAO {

    public List<Pelanggan> getAllPelanggan() {

        List<Pelanggan> daftar = new ArrayList<>();

        String sql = "SELECT id_pelanggan, nama, no_hp "
                   + "FROM pelanggan ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Pelanggan pelanggan = new Pelanggan(
                        rs.getInt("id_pelanggan"),
                        rs.getString("nama"),
                        rs.getString("no_hp")
                );

                daftar.add(pelanggan);
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil pelanggan: " + e.getMessage());
        }

        return daftar;
    }

    public boolean tambahPelanggan(String nama, String noHp) {

        String sql = "INSERT INTO pelanggan (nama, no_hp) VALUES (?, ?)";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nama);
            stmt.setString(2, noHp);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal menambahkan pelanggan: " + e.getMessage());
            return false;
        }
    }

    public Pelanggan cariPelanggan(String noHp) {

        String sql = "SELECT id_pelanggan, nama, no_hp "
                   + "FROM pelanggan WHERE no_hp = ? LIMIT 1";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, noHp);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Pelanggan(
                            rs.getInt("id_pelanggan"),
                            rs.getString("nama"),
                            rs.getString("no_hp")
                    );
                }
            }

        } catch (SQLException e) {
            System.err.println("Gagal mencari pelanggan: " + e.getMessage());
        }

        return null;
    }
}
