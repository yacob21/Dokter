package com.dokter.yacob.dokter;

/**
 * Created by yacob on 3/16/2018.
 */

public class Timeline {
    String Tanggal;
    String Aktivitas;
    String NIK_Perawat;
    String Nama_Perawat;
    String NIK_Dokter;
    String Nama_Dokter;
    String Date;
    String Gambar;
    String Id_Resep;

    public Timeline(){

    }

    public Timeline(String tanggal, String aktivitas, String nIK_Perawat, String nama_Perawat, String nik_Dokter , String nama_Dokter, String date, String gambar,String id_Resep){
        this.Tanggal = tanggal;
        this.Aktivitas=aktivitas;
        this.NIK_Perawat = nIK_Perawat;
        this.Nama_Perawat=nama_Perawat;
        this.NIK_Dokter=nik_Dokter;
        this.Nama_Dokter=nama_Dokter;
        this.Date=date;
        this.Gambar=gambar;
        this.Id_Resep = id_Resep;
    }


    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getNama_Dokter() {
        return Nama_Dokter;
    }

    public void setNama_Dokter(String nama_Dokter) {
        Nama_Dokter = nama_Dokter;
    }

    public String getTanggal() {
        return Tanggal;
    }

    public void setTanggal(String tanggal) {
        Tanggal = tanggal;
    }

    public String getAktivitas() {
        return Aktivitas;
    }

    public void setAktivitas(String aktivitas) {
        Aktivitas = aktivitas;
    }

    public String getNIK_Perawat() {
        return NIK_Perawat;
    }

    public void setNIK_Perawat(String NIK_Perawat) {
        this.NIK_Perawat = NIK_Perawat;
    }

    public String getNama_Perawat() {
        return Nama_Perawat;
    }

    public void setNama_Perawat(String nama_Perawat) {
        Nama_Perawat = nama_Perawat;
    }

    public String getNIK_Dokter() {
        return NIK_Dokter;
    }

    public void setNIK_Dokter(String NIK_Dokter) {
        this.NIK_Dokter = NIK_Dokter;
    }
}
