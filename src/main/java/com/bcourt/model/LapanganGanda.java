package com.bcourt.model;

public class LapanganGanda extends Lapangan {

    public LapanganGanda(int id, String namaLapangan, double hargaPerJam, String status) {
        super(id, namaLapangan, "Ganda", hargaPerJam, status, 4);
    }

    /** Ganda: aturan berbeda dari Tunggal, diskon 10% jika durasi 3 jam atau lebih. */
    @Override
    public double hitungHarga() {
        double total = getHargaPerJam() * getDurasiPenyewaan();
        return getDurasiPenyewaan() >= 3 ? total * 0.9 : total;
    }
}
