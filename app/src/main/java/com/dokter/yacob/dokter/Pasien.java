package com.dokter.yacob.dokter;

/**
 * Created by user on 27/07/2017.
 */


public class Pasien {
    protected String Id_Pasien;
    protected String Nama_Pasien;
    protected String Jenis_Kelamin;
    protected String Tanggal_Lahir;
    protected String Tanggal_Masuk;
    protected String Jenis_Kamar;
    protected String Nomor_Kamar;
    protected String Alergi;
    protected String Golongan_Darah;
    protected String Tinggi;
    protected String Berat;
    protected String Diagnosa_1;
    protected String Diagnosa_2;
    protected String Diagnosa_3;



    public Pasien() {

    }


    public Pasien(String id_pasien,String nama_Pasien,String tanggal_Masuk,String jenis_Kamar,String nomor_Kamar) {
        this.Id_Pasien=id_pasien;
        this.Nama_Pasien=nama_Pasien;
        this.Tanggal_Masuk=tanggal_Masuk;
        this.Jenis_Kamar=jenis_Kamar;
        this.Nomor_Kamar=nomor_Kamar;
    }


    public String getId_Pasien() {
        return Id_Pasien;
    }

    public void setId_Pasien(String id_Pasien) {
        Id_Pasien = id_Pasien;
    }

    public String getNama_Pasien() {
        return Nama_Pasien;
    }

    public void setNama_Pasien(String nama_Pasien) {
        Nama_Pasien = nama_Pasien;
    }

    public String getJenis_Kelamin() {
        return Jenis_Kelamin;
    }

    public void setJenis_Kelamin(String jenis_Kelamin) {
        Jenis_Kelamin = jenis_Kelamin;
    }

    public String getTanggal_Lahir() {
        return Tanggal_Lahir;
    }

    public void setTanggal_Lahir(String tanggal_Lahir) {
        Tanggal_Lahir = tanggal_Lahir;
    }

    public String getTanggal_Masuk() {
        return Tanggal_Masuk;
    }

    public void setTanggal_Masuk(String tanggal_Masuk) {
        Tanggal_Masuk = tanggal_Masuk;
    }

    public String getJenis_Kamar() {
        return Jenis_Kamar;
    }

    public void setJenis_Kamar(String jenis_Kamar) {
        Jenis_Kamar = jenis_Kamar;
    }

    public String getNomor_Kamar() {
        return Nomor_Kamar;
    }

    public void setNomor_Kamar(String nomor_Kamar) {
        Nomor_Kamar = nomor_Kamar;
    }

    public String getAlergi() {
        return Alergi;
    }

    public void setAlergi(String alergi) {
        Alergi = alergi;
    }

    public String getGolongan_Darah() {
        return Golongan_Darah;
    }

    public void setGolongan_Darah(String golongan_Darah) {
        Golongan_Darah = golongan_Darah;
    }

    public String getTinggi() {
        return Tinggi;
    }

    public void setTinggi(String tinggi) {
        Tinggi = tinggi;
    }

    public String getBerat() {
        return Berat;
    }

    public void setBerat(String berat) {
        Berat = berat;
    }

    public String getDiagnosa_1() {
        return Diagnosa_1;
    }

    public void setDiagnosa_1(String diagnosa_1) {
        Diagnosa_1 = diagnosa_1;
    }

    public String getDiagnosa_2() {
        return Diagnosa_2;
    }

    public void setDiagnosa_2(String diagnosa_2) {
        Diagnosa_2 = diagnosa_2;
    }

    public String getDiagnosa_3() {
        return Diagnosa_3;
    }

    public void setDiagnosa_3(String diagnosa_3) {
        Diagnosa_3 = diagnosa_3;
    }
}

