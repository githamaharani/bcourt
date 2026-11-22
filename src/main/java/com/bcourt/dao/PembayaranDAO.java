package com.bcourt.dao;

import com.bcourt.util.Koneksi;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PembayaranDAO {

    public boolean konfirmasiPembayaran(int idSewa) {

        String sql = "UPDATE pembayaran "
                   + "SET status_pembayaran = 'Lunas', "
                   + "tanggal_bayar = CURRENT_TIMESTAMP "
                   + "WHERE id_sewa = ? AND status_pembayaran = 'Pending'";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSewa);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Gagal mengonfirmasi pembayaran: " + e.getMessage()
            );
            return false;
        }
    }

    public String getStatusPembayaran(int idSewa) {

        String sql = "SELECT status_pembayaran FROM pembayaran "
                   + "WHERE id_sewa = ?";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSewa);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("status_pembayaran");
                }
            }

        } catch (SQLException e) {
            System.err.println(
                    "Gagal mengambil status pembayaran: " + e.getMessage()
            );
        }

        return null;
    }

    public boolean batalkanPembayaran(int idSewa) {

        String sql = "UPDATE pembayaran "
                   + "SET status_pembayaran = 'Gagal' "
                   + "WHERE id_sewa = ? AND status_pembayaran = 'Pending'";

        try (Connection conn = Koneksi.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, idSewa);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.err.println(
                    "Gagal membatalkan pembayaran: " + e.getMessage()
            );
            return false;
        }
    }
}
