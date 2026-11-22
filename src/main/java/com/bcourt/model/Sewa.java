package com.bcourt.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.Map;

public class Sewa {
    private int idSewa;
    private Admin admin;           
    private Pelanggan pelanggan;   
    private Lapangan lapangan;
    private LocalDate tanggalPemesanan;
    private LocalDate tanggalMain;
    private LocalTime jamMulai;
    private LocalTime jamSelesai;
    private String catatan;
    private String status;         
    private double totalBiaya;
    private final Map<Peralatan, Integer> daftarPeralatan = new LinkedHashMap<>();

    public Sewa() {}

    public int hitungDurasiJam() {
        if (jamMulai == null || jamSelesai == null) return 0;
        return (int) Math.max(0, Duration.between(jamMulai, jamSelesai).toHours());
    }

    public void tambahPeralatan(Peralatan peralatan, int jumlah) {
        peralatan.kurangiStok(jumlah);
        daftarPeralatan.merge(peralatan, jumlah, Integer::sum);
    }

    /** Total = biaya lapangan (polimorfik) + subtotal semua peralatan. */
    public double totalBiaya() {
        double total = 0;
        if (lapangan != null) {
            lapangan.setDurasiPenyewaan(hitungDurasiJam());
            total += lapangan.hitungHarga();
        }
        for (Map.Entry<Peralatan, Integer> e : daftarPeralatan.entrySet()) {
            total += e.getKey().subTotalPeralatan(e.getValue());
        }
        return total;
    }

    public String tampilkanDetailSewa() {
        return "Sewa #" + idSewa + " | " + (lapangan != null ? lapangan.getNamaLapangan() : "-")
             + " | " + tanggalMain + " " + jamMulai + "-" + jamSelesai
             + " | Total Rp" + (long) totalBiaya + " | " + status;
    }

    public int getIdSewa() { return idSewa; }
    public void setIdSewa(int idSewa) { this.idSewa = idSewa; }
    public Admin getAdmin() { return admin; }
    public void setAdmin(Admin admin) { this.admin = admin; }
    public Pelanggan getPelanggan() { return pelanggan; }
    public void setPelanggan(Pelanggan pelanggan) { this.pelanggan = pelanggan; }
    public Lapangan getLapangan() { return lapangan; }
    public void setLapangan(Lapangan lapangan) { this.lapangan = lapangan; }
    public LocalDate getTanggalPemesanan() { return tanggalPemesanan; }
    public void setTanggalPemesanan(LocalDate tanggalPemesanan) { this.tanggalPemesanan = tanggalPemesanan; }
    public LocalDate getTanggalMain() { return tanggalMain; }
    public void setTanggalMain(LocalDate tanggalMain) { this.tanggalMain = tanggalMain; }
    public LocalTime getJamMulai() { return jamMulai; }
    public void setJamMulai(LocalTime jamMulai) { this.jamMulai = jamMulai; }
    public LocalTime getJamSelesai() { return jamSelesai; }
    public void setJamSelesai(LocalTime jamSelesai) { this.jamSelesai = jamSelesai; }
    public String getCatatan() { return catatan; }
    public void setCatatan(String catatan) { this.catatan = catatan; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public double getTotalBiaya() { return totalBiaya; }
    public void setTotalBiaya(double totalBiaya) { this.totalBiaya = totalBiaya; }
    public Map<Peralatan, Integer> getDaftarPeralatan() { return daftarPeralatan; }
}
