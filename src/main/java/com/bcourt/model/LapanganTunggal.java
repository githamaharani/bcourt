package com.bcourt.model;

public class LapanganTunggal extends Lapangan {

    public LapanganTunggal(int id, String namaLapangan, double hargaPerJam, String status) {
        super(id, namaLapangan, "Tunggal", hargaPerJam, status, 2);
    }

    /** Tunggal: tarif normal x durasi. */
    @Override
    public double hitungHarga() {
        return getHargaPerJam() * getDurasiPenyewaan();
    }
}
