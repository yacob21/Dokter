package com.dokter.yacob.dokter;

/**
 * Created by yacob on 12/11/2017.
 */

public class Resep {
    String Tanggal_Dibuat;
    String Jumlah;
    String Id_Resep;
    String Nama_Obat;
    String Dosis;
    String Sehari;
    String Sekali;
    String Total_Hari;
    String Instruksi;
    String Tambahan;

    public Resep(String nama_Obat,String dosis,String sehari,String sekali,String total_Hari,String instruksi,String tambahan){
        this.Nama_Obat=nama_Obat;
        this.Dosis= dosis;
        this.Sehari=sehari;
        this.Sekali = sekali;
        this.Total_Hari = total_Hari;
        this.Instruksi = instruksi;
        this.Tambahan = tambahan;
    }

    public Resep(String tanggal_Dibuat,String jumlah,String id_Resep){
        this.Tanggal_Dibuat = tanggal_Dibuat;
        this.Jumlah = jumlah;
        this.Id_Resep = id_Resep;
    }

    public String getId_Resep() {
        return Id_Resep;
    }

    public void setId_Resep(String id_Resep) {
        Id_Resep = id_Resep;
    }

    public String getTanggal_Dibuat() {
        return Tanggal_Dibuat;
    }

    public void setTanggal_Dibuat(String tanggal_Dibuat) {
        Tanggal_Dibuat = tanggal_Dibuat;
    }

    public String getJumlah() {
        return Jumlah;
    }

    public void setJumlah(String jumlah) {
        Jumlah = jumlah;
    }
}
