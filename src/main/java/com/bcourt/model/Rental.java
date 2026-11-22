package com.bcourt.model;

import java.time.LocalDate;

public class Rental {

    private String code;
    private String customer;
    private String phone;
    private String court;
    private LocalDate date;
    private String startTime;
    private String endTime;
    private String note;
    private String status;

    // Rincian biaya transaksi
    private int biayaLapangan;
    private int biayaPeralatan;
    private int totalBiaya;

    // Constructor lama tetap dipertahankan
    public Rental(String code, String customer, String phone,
                  String court, LocalDate date,
                  String startTime, String endTime,
                  String note, String status) {

        this(code, customer, phone, court, date,
             startTime, endTime, note, status, 0, 0, 0);
    }

    // Constructor baru dengan rincian biaya
    public Rental(String code, String customer, String phone,
                  String court, LocalDate date,
                  String startTime, String endTime,
                  String note, String status,
                  int biayaLapangan, int biayaPeralatan,
                  int totalBiaya) {

        this.code = code;
        this.customer = customer;
        this.phone = phone;
        this.court = court;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.note = note;
        this.status = status;
        this.biayaLapangan = biayaLapangan;
        this.biayaPeralatan = biayaPeralatan;
        this.totalBiaya = totalBiaya;
    }

    public String getCode() { return code; }
    public String getCustomer() { return customer; }
    public String getPhone() { return phone; }
    public String getCourt() { return court; }
    public LocalDate getDate() { return date; }
    public String getStartTime() { return startTime; }
    public String getEndTime() { return endTime; }
    public String getNote() { return note; }
    public String getStatus() { return status; }

    public int getBiayaLapangan() { return biayaLapangan; }
    public int getBiayaPeralatan() { return biayaPeralatan; }
    public int getTotalBiaya() { return totalBiaya; }

    public void setStatus(String status) {
        this.status = status;
    }
}
