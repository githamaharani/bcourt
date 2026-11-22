package com.bcourt.model;

/** TK1: id_peralatan, nama_peralatan, harga_sewa, stok, stok_minimum, durasi_penyewaan. */
public class Peralatan {
    private int idPeralatan;
    private String namaPeralatan;
    private double hargaSewa;
    private int stok;
    private int stokMinimum;
    private int durasiPenyewaan;   // diisi saat transaksi

    public Peralatan() {}

    public Peralatan(int idPeralatan, String namaPeralatan, double hargaSewa, int stok, int stokMinimum) {
        this.idPeralatan = idPeralatan;
        this.namaPeralatan = namaPeralatan;
        this.hargaSewa = hargaSewa;
        this.stok = stok;
        this.stokMinimum = stokMinimum;
    }

    public String tampilkanPeralatan() {
        return namaPeralatan + " | Rp" + (long) hargaSewa + " | stok " + stok;
    }

    public boolean cekStok(int jumlah) {
        return jumlah > 0 && stok >= jumlah;
    }

    /** Mengurangi stok; melempar exception jika stok tidak cukup. */
    public void kurangiStok(int jumlah) {
        if (!cekStok(jumlah)) {
            throw new IllegalStateException("Stok " + namaPeralatan + " tidak cukup (tersisa " + stok + ")");
        }
        stok -= jumlah;
    }

    public double subTotalPeralatan(int jumlah) {
        return hargaSewa * jumlah;
    }

    /** Dipakai admin untuk peringatan stok menipis. */
    public boolean isStokMenipis() {
        return stok < stokMinimum;
    }

    public int getIdPeralatan() { return idPeralatan; }
    public void setIdPeralatan(int idPeralatan) { this.idPeralatan = idPeralatan; }
    public String getNamaPeralatan() { return namaPeralatan; }
    public void setNamaPeralatan(String namaPeralatan) { this.namaPeralatan = namaPeralatan; }
    public double getHargaSewa() { return hargaSewa; }
    public void setHargaSewa(double hargaSewa) { this.hargaSewa = hargaSewa; }
    public int getStok() { return stok; }
    public void setStok(int stok) { this.stok = stok; }
    public int getStokMinimum() { return stokMinimum; }
    public void setStokMinimum(int stokMinimum) { this.stokMinimum = stokMinimum; }
    public int getDurasiPenyewaan() { return durasiPenyewaan; }
    public void setDurasiPenyewaan(int durasiPenyewaan) { this.durasiPenyewaan = durasiPenyewaan; }
}
