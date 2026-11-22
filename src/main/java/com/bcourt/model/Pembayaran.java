package com.bcourt.model;

import java.time.LocalDateTime;

/** ABSTRAKSI: Pembayaran = kerangka umum; cara bayar ditentukan subclass (QRIS / Tunai). */
public abstract class Pembayaran {
    private int idPembayaran;
    private double totalBayar;
    private LocalDateTime tanggalBayar;
    private String statusPembayaran = "Pending";   // Lunas / Pending / Gagal

    protected Pembayaran() {}

    protected Pembayaran(int idPembayaran, double totalBayar) {
        this.idPembayaran = idPembayaran;
        this.totalBayar = totalBayar;
    }

    /** POLIMORFISME: dioverride oleh PembayaranQRIS dan PembayaranTunai. */
    public abstract boolean prosesPembayaran();

    public String tampilkanDetailPembayaran() {
        return "Pembayaran #" + idPembayaran + " | Rp" + (long) totalBayar + " | " + statusPembayaran;
    }

    public int getIdPembayaran() { return idPembayaran; }
    public void setIdPembayaran(int idPembayaran) { this.idPembayaran = idPembayaran; }
    public double getTotalBayar() { return totalBayar; }
    public void setTotalBayar(double totalBayar) { this.totalBayar = totalBayar; }
    public LocalDateTime getTanggalBayar() { return tanggalBayar; }
    public void setTanggalBayar(LocalDateTime tanggalBayar) { this.tanggalBayar = tanggalBayar; }
    public String getStatusPembayaran() { return statusPembayaran; }
    public void setStatusPembayaran(String statusPembayaran) { this.statusPembayaran = statusPembayaran; }
}
