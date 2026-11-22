package com.bcourt.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Kasir extends User {

    public Kasir() {}

    public Kasir(int idUser, String nama, String username, String password, String noHp, String alamat) {
        super(idUser, nama, username, password, noHp, alamat);
    }

    @Override
    public String tampilkanMenu() {
        return "dashborad-admin";   // TODO: samakan dengan nama FXML dashboard kasir di App/controller
    }

    public Sewa buatSewa(Pelanggan pelanggan, Lapangan lapangan, LocalDate tanggalMain,
                         LocalTime jamMulai, LocalTime jamSelesai, String catatan) {
        Sewa sewa = new Sewa();
        sewa.setKasir(this);
        sewa.setPelanggan(pelanggan);
        sewa.setLapangan(lapangan);
        sewa.setTanggalPemesanan(LocalDate.now());
        sewa.setTanggalMain(tanggalMain);
        sewa.setJamMulai(jamMulai);
        sewa.setJamSelesai(jamSelesai);
        sewa.setCatatan(catatan);
        sewa.setStatus("Dipesan");
        sewa.setTotalBiaya(sewa.totalBiaya());
        return sewa;
    }

    /** POLIMORFISME: pembayaran bisa berupa QRIS atau Tunai. */
    public boolean buatPembayaran(Pembayaran pembayaran) {
        return pembayaran.prosesPembayaran();
    }

    public void batalkanSewa(Sewa sewa) {
        sewa.setStatus("Dibatalkan");
    }
}