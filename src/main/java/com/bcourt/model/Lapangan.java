package com.bcourt.model;

/**
 * ABSTRAKSI: Lapangan hanya menjelaskan hal umum; perhitungan harga diserahkan ke subclass.
 * TK1: id_lapangan, nama_lapangan, jenis_lapangan, harga_per_jam, status, durasi_penyewaan, kapasitas.
 */
public abstract class Lapangan {
    private int idLapangan;
    private String namaLapangan;
    private String jenisLapangan;   // "Tunggal" / "Ganda"
    private double hargaPerJam;
    private String status;          // "Tersedia" / "Perawatan"
    private int durasiPenyewaan;    // jam, diisi saat transaksi (tidak disimpan di tabel Lapangan)
    private int kapasitas;          // jumlah pemain

    protected Lapangan(int idLapangan, String namaLapangan, String jenisLapangan,
                       double hargaPerJam, String status, int kapasitas) {
        this.idLapangan = idLapangan;
        this.namaLapangan = namaLapangan;
        this.jenisLapangan = jenisLapangan;
        this.hargaPerJam = hargaPerJam;
        this.status = status;
        this.kapasitas = kapasitas;
    }

    /** Factory: memilih subclass berdasarkan kolom jenis_lapangan di database. */
    public static Lapangan buat(int id, String nama, String jenis, double harga, String status) {
        return "Ganda".equalsIgnoreCase(jenis)
            ? new LapanganGanda(id, nama, harga, status)
            : new LapanganTunggal(id, nama, harga, status);
    }

    /** POLIMORFISME: dioverride di LapanganTunggal dan LapanganGanda. */
    public abstract double hitungHarga();

    public boolean cekKetersediaan() {
        return "Tersedia".equalsIgnoreCase(status);
    }

    public void ubahInformasi(String namaLapangan, double hargaPerJam, String status) {
        this.namaLapangan = namaLapangan;
        this.hargaPerJam = hargaPerJam;
        this.status = status;
    }

    public String tampilkanDetailLapangan() {
        return namaLapangan + " (" + jenisLapangan + ") | Rp" + (long) hargaPerJam
             + "/jam | Kapasitas " + kapasitas + " orang | " + status;
    }

    public int getIdLapangan() { return idLapangan; }
    public void setIdLapangan(int idLapangan) { this.idLapangan = idLapangan; }
    public String getNamaLapangan() { return namaLapangan; }
    public void setNamaLapangan(String namaLapangan) { this.namaLapangan = namaLapangan; }
    public String getJenisLapangan() { return jenisLapangan; }
    public void setJenisLapangan(String jenisLapangan) { this.jenisLapangan = jenisLapangan; }
    public double getHargaPerJam() { return hargaPerJam; }
    public void setHargaPerJam(double hargaPerJam) { this.hargaPerJam = hargaPerJam; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public int getDurasiPenyewaan() { return durasiPenyewaan; }
    public void setDurasiPenyewaan(int durasiPenyewaan) { this.durasiPenyewaan = durasiPenyewaan; }
    public int getKapasitas() { return kapasitas; }
    public void setKapasitas(int kapasitas) { this.kapasitas = kapasitas; }
}
