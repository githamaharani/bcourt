package com.bcourt.dao;

import com.bcourt.model.Lapangan;
import com.bcourt.util.Koneksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LapanganDAO {

    public List<Lapangan> getAllLapangan() {

        List<Lapangan> daftar = new ArrayList<>();

        String sql = "SELECT id_lapangan, nama, jenis_lapangan, "
                   + "harga_per_jam, status FROM lapangan "
                   + "ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Lapangan lapangan = Lapangan.buat(
                        rs.getInt("id_lapangan"),
                        rs.getString("nama"),
                        rs.getString("jenis_lapangan"),
                        rs.getDouble("harga_per_jam"),
                        rs.getString("status")
                );

                daftar.add(lapangan);
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil lapangan: " + e.getMessage());
        }

        return daftar;
    }

    public List<Lapangan> getLapanganTersedia() {

        List<Lapangan> daftar = new ArrayList<>();

        String sql = "SELECT id_lapangan, nama, jenis_lapangan, "
                   + "harga_per_jam, status FROM lapangan "
                   + "WHERE status = 'Tersedia' ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                daftar.add(
                        Lapangan.buat(
                                rs.getInt("id_lapangan"),
                                rs.getString("nama"),
                                rs.getString("jenis_lapangan"),
                                rs.getDouble("harga_per_jam"),
                                rs.getString("status")
                        )
                );
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil lapangan tersedia: "
                    + e.getMessage());
        }

        return daftar;
    }
}
