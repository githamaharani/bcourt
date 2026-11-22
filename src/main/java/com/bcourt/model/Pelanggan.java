package com.bcourt.model;

import java.time.LocalDate;
import java.time.LocalTime;

/** PEWARISAN: Pelanggan mewarisi User. Method: tampilkanMenu, buatSewa, buatPembayaran, batalkanSewa. */
public class Pelanggan extends User {

    public Pelanggan() {}

    public Pelanggan(int idUser, String nama, String username, String password, String noHp, String alamat) {
        super(idUser, nama, username, password, noHp, alamat);
    }

    @Override
    public String tampilkanMenu() {
        return "Dashboard Pelanggan";   // TODO: sesuaikan dengan nama FXML dashboard pelanggan
    }

    public Sewa buatSewa(Lapangan lapangan, LocalDate tanggalMain, LocalTime jamMulai, LocalTime jamSelesai, String catatan) {
        Sewa sewa = new Sewa();
        sewa.setPelanggan(this);
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
