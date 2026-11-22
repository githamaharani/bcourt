package com.bcourt.model;

import java.time.LocalDateTime;

public class PembayaranTunai extends Pembayaran {
    private double jumlahUang;
    private double kembalian;

    public PembayaranTunai(int idPembayaran, double totalBayar, double jumlahUang) {
        super(idPembayaran, totalBayar);
        this.jumlahUang = jumlahUang;
    }

    /** Tunai: hitung kembalian; ditolak jika uang kurang dari total. */
    @Override
    public boolean prosesPembayaran() {
        if (jumlahUang < getTotalBayar()) {
            setStatusPembayaran("Gagal");
            return false;
        }
        kembalian = jumlahUang - getTotalBayar();
        setTanggalBayar(LocalDateTime.now());
        setStatusPembayaran("Pending");   // admin yang menandai Lunas saat verifikasi
        return true;
    }

    public double getJumlahUang() { return jumlahUang; }
    public void setJumlahUang(double jumlahUang) { this.jumlahUang = jumlahUang; }
    public double getKembalian() { return kembalian; }
}
