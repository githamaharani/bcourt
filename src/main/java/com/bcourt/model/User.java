package com.bcourt.model;

/**
 * Superclass pengguna (TK1: id_user, nama, username, password, no_hp, alamat).
 * ABSTRAKSI + ENKAPSULASI: atribut private, diakses lewat getter/setter.
 */
public abstract class User {
    private int idUser;
    private String nama;
    private String username;
    private String password;   // simpan hash (SHA-256), bukan teks asli
    private String noHp;
    private String alamat;

    public User() {}

    public User(int idUser, String nama, String username, String password, String noHp, String alamat) {
        this.idUser = idUser;
        this.nama = nama;
        this.username = username;
        this.password = password;
        this.noHp = noHp;
        this.alamat = alamat;
    }

    /** POLIMORFISME: tiap subclass menampilkan menu yang berbeda. Mengembalikan nama halaman dashboard. */
    public abstract String tampilkanMenu();

    public int getIdUser() { return idUser; }
    public void setIdUser(int idUser) { this.idUser = idUser; }
    public String getNama() { return nama; }
    public void setNama(String nama) { this.nama = nama; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }
    public String getAlamat() { return alamat; }
    public void setAlamat(String alamat) { this.alamat = alamat; }
}
