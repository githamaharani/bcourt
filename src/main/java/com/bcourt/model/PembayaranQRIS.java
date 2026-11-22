package com.bcourt.model;

import java.time.LocalDateTime;

public class PembayaranQRIS extends Pembayaran {
    private String kodeQR;

    public PembayaranQRIS(int idPembayaran, double totalBayar, String kodeQR) {
        super(idPembayaran, totalBayar);
        this.kodeQR = kodeQR;
    }

    /** QRIS: pembayaran dicatat dan menunggu verifikasi admin. */
    @Override
    public boolean prosesPembayaran() {
        if (kodeQR == null || kodeQR.isBlank()) return false;
        setTanggalBayar(LocalDateTime.now());
        setStatusPembayaran("Pending");
        return true;
    }

    public String getKodeQR() { return kodeQR; }
    public void setKodeQR(String kodeQR) { this.kodeQR = kodeQR; }
}
