package com.bcourt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bcourt.util.Koneksi;

public class LapanganDAO {

    public void tampilkanSemuaLapangan() {
        String sql = "SELECT id_lapangan, nama, jenis_lapangan, harga_per_jam, status FROM lapangan";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("=== DATA LAPANGAN ===");
            boolean adaData = false;

            while (rs.next()) {
                adaData = true;
                int id = rs.getInt("id_lapangan");
                String nama = rs.getString("nama");
                String jenis = rs.getString("jenis_lapangan");
                double harga = rs.getDouble("harga_per_jam");
                String status = rs.getString("status");

                System.out.printf("ID: %d | Nama: %s | Jenis: %s | Harga/Jam: %.2f | Status: %s%n",
                        id, nama, jenis, harga, status);
            }

            if (!adaData) {
                System.out.println("Belum ada data di tabel lapangan.");
            }

        } catch (SQLException e) {
            System.out.println("Gagal mengambil data lapangan: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        LapanganDAO dao = new LapanganDAO();
        dao.tampilkanSemuaLapangan();
    }
}