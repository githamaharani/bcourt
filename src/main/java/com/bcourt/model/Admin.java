package com.bcourt.model;

/** PEWARISAN: Admin mewarisi atribut User. Method: tampilkanMenu, konfirmasiSewa, tambahLapangan, editInformasiLapangan. */
public class Admin extends User {

    public Admin() {}

    public Admin(int idUser, String nama, String username, String password, String noHp, String alamat) {
        super(idUser, nama, username, password, noHp, alamat);
    }

    @Override
    public String tampilkanMenu() {
        return "Dashboard Admin";   // TODO: sesuaikan dengan nama FXML dashboard admin di App/controller
    }

    /** Admin memverifikasi sewa: status menjadi "Berlangsung" (penyimpanan ke DB dilakukan oleh DAO). */
    public void konfirmasiSewa(Sewa sewa) {
        sewa.setAdmin(this);
        sewa.setStatus("Berlangsung");
    }

    /** Membuat objek lapangan baru (subclass dipilih berdasarkan jenis). Simpan ke DB lewat LapanganDAO. */
    public Lapangan tambahLapangan(String namaLapangan, String jenisLapangan, double hargaPerJam) {
        return Lapangan.buat(0, namaLapangan, jenisLapangan, hargaPerJam, "Tersedia");
    }

    /** Mengubah informasi lapangan melalui method ubahInformasi() milik Lapangan. */
    public void editInformasiLapangan(Lapangan lapangan, String namaLapangan, double hargaPerJam, String status) {
        lapangan.ubahInformasi(namaLapangan, hargaPerJam, status);
    }
}
