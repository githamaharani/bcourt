
package com.bcourt.dao;

import com.bcourt.util.Koneksi;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Types;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

public class SewaDAO {
    public int simpanTransaksi(
            int idPelanggan,
            int idPengguna,
            int idLapangan,
            LocalDate tanggalMain,
            LocalTime jamMulai,
            LocalTime jamSelesai,
            String catatan,
            Map<Integer, Integer> daftarPeralatan,
            String metodePembayaran) {

        if (idPelanggan <= 0 || idPengguna <= 0 || idLapangan <= 0) {
            System.err.println("ID pelanggan, pengguna, atau lapangan tidak valid.");
            return -1;
        }

        if (tanggalMain == null || jamMulai == null || jamSelesai == null) {
            System.err.println("Tanggal dan jam penyewaan harus diisi.");
            return -1;
        }

        long durasiMenit = Duration.between(jamMulai, jamSelesai).toMinutes();

        if (durasiMenit <= 0 || durasiMenit % 60 != 0) {
            System.err.println("Durasi penyewaan harus kelipatan satu jam.");
            return -1;
        }

        if (!"QRIS".equals(metodePembayaran)
                && !"Tunai".equals(metodePembayaran)) {
            System.err.println("Metode pembayaran tidak valid.");
            return -1;
        }

        if (daftarPeralatan == null) {
            System.err.println("Daftar peralatan tidak boleh null.");
            return -1;
        }

        int durasiJam = (int) (durasiMenit / 60);

        String sqlLapangan =
                "SELECT harga_per_jam, status FROM lapangan "
                + "WHERE id_lapangan = ? FOR UPDATE";

        String sqlPelanggan =
                "SELECT id_pelanggan FROM pelanggan WHERE id_pelanggan = ?";

        String sqlPengguna =
                "SELECT id_pengguna FROM pengguna WHERE id_pengguna = ?";

        String sqlSewa =
                "INSERT INTO sewa "
                + "(tanggal_pemesanan, tanggal_main, catatan, status, "
                + "total_biaya, id_pelanggan, id_pengguna) "
                + "VALUES (CURDATE(), ?, ?, 'Dipesan', ?, ?, ?)";

        String sqlDetailLapangan =
                "INSERT INTO detail_lapangan "
                + "(jam_mulai, jam_selesai, harga_per_jam, subtotal, "
                + "id_sewa, id_lapangan) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        String sqlPeralatan =
                "SELECT harga, stok FROM peralatan "
                + "WHERE id_peralatan = ? FOR UPDATE";

        String sqlDetailPeralatan =
                "INSERT INTO detail_peralatan "
                + "(jumlah, harga_satuan, subtotal, id_peralatan, id_sewa) "
                + "VALUES (?, ?, ?, ?, ?)";

        String sqlUpdateStok =
                "UPDATE peralatan SET stok = stok - ? "
                + "WHERE id_peralatan = ? AND stok >= ?";

        String sqlPembayaran =
                "INSERT INTO pembayaran "
                + "(metode_pembayaran, total_bayar, tanggal_bayar, "
                + "status_pembayaran, id_sewa) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = Koneksi.getConnection()) {

            conn.setAutoCommit(false);

            try {
                cekId(conn, sqlPelanggan, idPelanggan, "Pelanggan");
                cekId(conn, sqlPengguna, idPengguna, "Pengguna");

                BigDecimal hargaPerJam;

                try (PreparedStatement stmt =
                             conn.prepareStatement(sqlLapangan)) {

                    stmt.setInt(1, idLapangan);

                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new Exception("Lapangan tidak ditemukan.");
                        }

                        if (!"Tersedia".equals(rs.getString("status"))) {
                            throw new Exception("Lapangan sedang tidak tersedia.");
                        }

                        hargaPerJam = rs.getBigDecimal("harga_per_jam");
                    }
                }

                BigDecimal subtotalLapangan =
                        hargaPerJam.multiply(BigDecimal.valueOf(durasiJam));

                BigDecimal totalBiaya = subtotalLapangan;

                for (Map.Entry<Integer, Integer> item
                        : daftarPeralatan.entrySet()) {

                    int idPeralatan = item.getKey();
                    int jumlah = item.getValue();

                    if (idPeralatan <= 0 || jumlah <= 0) {
                        throw new Exception("Data jumlah peralatan tidak valid.");
                    }

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(sqlPeralatan)) {

                        stmt.setInt(1, idPeralatan);

                        try (ResultSet rs = stmt.executeQuery()) {
                            if (!rs.next()) {
                                throw new Exception(
                                        "Peralatan ID " + idPeralatan
                                        + " tidak ditemukan."
                                );
                            }

                            int stok = rs.getInt("stok");

                            if (stok < jumlah) {
                                throw new Exception(
                                        "Stok peralatan ID " + idPeralatan
                                        + " tidak mencukupi."
                                );
                            }

                            BigDecimal harga = rs.getBigDecimal("harga");

                            totalBiaya = totalBiaya.add(
                                    harga.multiply(BigDecimal.valueOf(jumlah))
                            );
                        }
                    }
                }

                int idSewa;

                try (PreparedStatement stmt =
                             conn.prepareStatement(
                                     sqlSewa,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    stmt.setDate(1, java.sql.Date.valueOf(tanggalMain));
                    stmt.setString(2, catatan);
                    stmt.setBigDecimal(3, totalBiaya);
                    stmt.setInt(4, idPelanggan);
                    stmt.setInt(5, idPengguna);

                    stmt.executeUpdate();

                    try (ResultSet keys = stmt.getGeneratedKeys()) {
                        if (!keys.next()) {
                            throw new Exception("ID penyewaan gagal dibuat.");
                        }

                        idSewa = keys.getInt(1);
                    }
                }

                try (PreparedStatement stmt =
                             conn.prepareStatement(sqlDetailLapangan)) {

                    stmt.setTime(1, java.sql.Time.valueOf(jamMulai));
                    stmt.setTime(2, java.sql.Time.valueOf(jamSelesai));
                    stmt.setBigDecimal(3, hargaPerJam);
                    stmt.setBigDecimal(4, subtotalLapangan);
                    stmt.setInt(5, idSewa);
                    stmt.setInt(6, idLapangan);

                    stmt.executeUpdate();
                }

                for (Map.Entry<Integer, Integer> item
                        : daftarPeralatan.entrySet()) {

                    int idPeralatan = item.getKey();
                    int jumlah = item.getValue();
                    BigDecimal harga;

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(sqlPeralatan)) {

                        stmt.setInt(1, idPeralatan);

                        try (ResultSet rs = stmt.executeQuery()) {
                            if (!rs.next()) {
                                throw new Exception("Peralatan tidak ditemukan.");
                            }

                            harga = rs.getBigDecimal("harga");
                        }
                    }

                    BigDecimal subtotal =
                            harga.multiply(BigDecimal.valueOf(jumlah));

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(sqlDetailPeralatan)) {

                        stmt.setInt(1, jumlah);
                        stmt.setBigDecimal(2, harga);
                        stmt.setBigDecimal(3, subtotal);
                        stmt.setInt(4, idPeralatan);
                        stmt.setInt(5, idSewa);

                        stmt.executeUpdate();
                    }

                    try (PreparedStatement stmt =
                                 conn.prepareStatement(sqlUpdateStok)) {

                        stmt.setInt(1, jumlah);
                        stmt.setInt(2, idPeralatan);
                        stmt.setInt(3, jumlah);

                        if (stmt.executeUpdate() == 0) {
                            throw new Exception(
                                    "Stok peralatan gagal diperbarui."
                            );
                        }
                    }
                }

                String statusPembayaran =
                        "Tunai".equals(metodePembayaran) ? "Lunas" : "Pending";

                try (PreparedStatement stmt =
                             conn.prepareStatement(sqlPembayaran)) {

                    stmt.setString(1, metodePembayaran);
                    stmt.setBigDecimal(2, totalBiaya);

                    if ("Tunai".equals(metodePembayaran)) {
                        stmt.setTimestamp(
                                3,
                                new java.sql.Timestamp(System.currentTimeMillis())
                        );
                    } else {
                        stmt.setNull(3, Types.TIMESTAMP);
                    }

                    stmt.setString(4, statusPembayaran);
                    stmt.setInt(5, idSewa);

                    stmt.executeUpdate();
                }

                conn.commit();

                return idSewa;

            } catch (Exception e) {

                conn.rollback();

                System.err.println(
                        "Transaksi dibatalkan: " + e.getMessage()
                );

                return -1;

            } finally {
                conn.setAutoCommit(true);
            }

        } catch (Exception e) {
            System.err.println(
                    "Gagal menyimpan transaksi: " + e.getMessage()
            );
            return -1;
        }
    }

    private void cekId(
            Connection conn,
            String sql,
            int id,
            String namaData) throws Exception {

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (!rs.next()) {
                    throw new Exception(namaData + " tidak ditemukan.");
                }
            }
        }
    }
}
