package com.bcourt.model;

/**
 * Data pelanggan yang dilayani kasir. Pelanggan tidak login, jadi tidak lagi
 * mewarisi User (aktor sistem hanya Admin dan Kasir).
 */
public class Pelanggan {
    private int idPelanggan;
    private String nama;
    private String noHp;

    public Pelanggan() {}

    public Pelanggan(int idPelanggan, String nama, String noHp) {
        this.idPelanggan = idPelanggan;
        this.nama = nama;
        this.noHp = noHp;
    }

    public int getIdPelanggan() { return idPelanggan; }
    public void setIdPelanggan(int idPelanggan) { this.idPelanggan = idPelanggan; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    @Override
    public String toString() { return nama + " (" + noHp + ")"; }
}