package com.bcourt.dao;

import com.bcourt.model.Peralatan;
import com.bcourt.util.Koneksi;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeralatanDAO {

    public List<Peralatan> getAllPeralatan() {

        List<Peralatan> daftar = new ArrayList<>();

        String sql = "SELECT id_peralatan, nama, harga, stok, stok_minimum "
                   + "FROM peralatan ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                Peralatan peralatan = new Peralatan(
                        rs.getInt("id_peralatan"),
                        rs.getString("nama"),
                        rs.getDouble("harga"),
                        rs.getInt("stok"),
                        rs.getInt("stok_minimum")
                );

                daftar.add(peralatan);
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil peralatan: " + e.getMessage());
        }

        return daftar;
    }

    public List<Peralatan> getPeralatanTersedia() {

        List<Peralatan> daftar = new ArrayList<>();

        String sql = "SELECT id_peralatan, nama, harga, stok, stok_minimum "
                   + "FROM peralatan WHERE stok > 0 ORDER BY nama";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {

                daftar.add(
                        new Peralatan(
                                rs.getInt("id_peralatan"),
                                rs.getString("nama"),
                                rs.getDouble("harga"),
                                rs.getInt("stok"),
                                rs.getInt("stok_minimum")
                        )
                );
            }

        } catch (SQLException e) {
            System.err.println("Gagal mengambil peralatan tersedia: "
                    + e.getMessage());
        }

        return daftar;
    }

    public boolean updateStok(int idPeralatan, int jumlah) {

        String sql = "UPDATE peralatan SET stok = stok - ? "
                   + "WHERE id_peralatan = ? AND stok >= ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, jumlah);
            stmt.setInt(2, idPeralatan);
            stmt.setInt(3, jumlah);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println("Gagal memperbarui stok: " + e.getMessage());
            return false;
        }
    }
}
