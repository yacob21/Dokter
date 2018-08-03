package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PengkajianAwalActivity4 extends AppCompatActivity {
    Button btnLanjut;
    EditText etReaksiAlergi,etPerubahanSistem,etPenyebab,etPerilakuRisiko,etJumlahTransfusi,
            etKapanTransfusi,etGambaranTransfusi,etSuhuTubuh,etLukaBakar;

    RadioButton rbAlergiYa,rbAlergiTidak,rbRingan,rbSedang,rbBerat,rbAmbulasiYa,rbAmbulasiTidak,
    rbDiaforesisYa,rbDiaforesisTidak;

    int alergi =0,ambulasi=0,diaforesis=0;

    String cedera="";

    CheckBox cbFraktur,cbArthritis,cbMasalah,cbJaringanParut,cbKemerahan,cbLaserasi,
    cbUlserasi,cbEkimosis,cbLepuh,cbLukaBakar;

    int fraktur=0,arthritis=0,masalah=0,parut=0,kemerahan=0,laserasi=0,ulserasi=0,ekimosis=0,lepuh=0,luka=0;

    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    Context context;
    String INSERT_PENGKAJIAN_AWAL_4_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertPengkajianAwal4.php";

    AlertDialog dialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengkajian_awal4);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pengkajian Awal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        sessionIdPasien = new SessionIdPasien(getApplicationContext());

        queue = Volley.newRequestQueue(this);
        builder = new AlertDialog.Builder(this);

        etReaksiAlergi = findViewById(R.id.etReaksiAlergi);
        etPerubahanSistem = findViewById(R.id.etPerubahanSistem);
        etPenyebab = findViewById(R.id.etPenyebab);
        etPerilakuRisiko = findViewById(R.id.etPerilakuRisiko);
        etJumlahTransfusi = findViewById(R.id.etJumlahTransfusi);
        etKapanTransfusi = findViewById(R.id.etKapanTransfusi);
        etGambaranTransfusi = findViewById(R.id.etGambaranTransfusi);
        etSuhuTubuh = findViewById(R.id.etSuhuTubuh);
        etLukaBakar = findViewById(R.id.etLukaBakar);

        rbAlergiYa = findViewById(R.id.rbAlergiYa);
        rbAlergiTidak = findViewById(R.id.rbAlergiTidak);
        rbRingan = findViewById(R.id.rbRingan);
        rbSedang = findViewById(R.id.rbSedang);
        rbBerat = findViewById(R.id.rbBerat);
        rbAmbulasiYa = findViewById(R.id.rbAmbulasiYa);
        rbAmbulasiTidak = findViewById(R.id.rbAmbulasiTidak);
        rbDiaforesisYa = findViewById(R.id.rbDiaforesisYa);
        rbDiaforesisTidak = findViewById(R.id.rbDiaforesisTidak);

        rbAlergiYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    alergi=1;
                }
            }
        });
        rbAlergiTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    alergi=0;
                }
            }
        });
        rbRingan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cedera="Ringan";
                }
            }
        });
        rbSedang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cedera="Sedang";
                }
            }
        });
        rbBerat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cedera="Berat";
                }
            }
        });
        rbAmbulasiYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ambulasi=1;
                }
            }
        });
        rbAmbulasiTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ambulasi=0;
                }
            }
        });
        rbDiaforesisYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    diaforesis=1;
                }
            }
        });
        rbDiaforesisTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    diaforesis=0;
                }
            }
        });


        cbFraktur = findViewById(R.id.cbFraktur);
        cbArthritis  = findViewById(R.id.cbArthritis);
        cbMasalah  = findViewById(R.id.cbMasalah);
        cbJaringanParut = findViewById(R.id.cbJaringanParut);
        cbKemerahan  = findViewById(R.id.cbKemerahan);
        cbLaserasi = findViewById(R.id.cbLaserasi);
        cbUlserasi = findViewById(R.id.cbUlserasi);
        cbEkimosis = findViewById(R.id.cbEkimosis);
        cbLepuh = findViewById(R.id.cbLepuh);
        cbLukaBakar = findViewById(R.id.cbLukaBakar);

        cbFraktur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fraktur=1;
                }
                else{
                    fraktur=0;
                }
            }
        });
        cbArthritis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    arthritis=1;
                }
                else{
                    arthritis=0;
                }
            }
        });
        cbMasalah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    masalah=1;
                }
                else{
                    masalah=0;
                }
            }
        });
        cbJaringanParut.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    parut=1;
                }
                else{
                    parut=0;
                }
            }
        });
        cbKemerahan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kemerahan=1;
                }
                else{
                    kemerahan=0;
                }
            }
        });

        cbLaserasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    laserasi=1;
                }
                else{
                    laserasi=0;
                }
            }
        });
        cbUlserasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ulserasi=1;
                }
                else{
                    ulserasi=0;
                }
            }
        });

        cbEkimosis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ekimosis=1;
                }
                else{
                    ekimosis=0;
                }
            }
        });

        cbLepuh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lepuh=1;
                }
                else{
                    lepuh=0;
                }
            }
        });
        cbLukaBakar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    luka=1;
                }
                else{
                    luka=0;
                }
            }
        });






        getPengkajianAwal(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));

        btnLanjut = findViewById(R.id.btnLanjut);
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    if(btnLanjut.getText().toString().equals("Kembali")){
                        Intent i = new Intent(PengkajianAwalActivity4.this,DetailPasienActivity.class);
                        startActivity(i);
                    }
                    else{
                        Intent i = new Intent(PengkajianAwalActivity4.this,PengkajianAwalActivity5.class);
                        startActivity(i);
                    }
            }
        });

    }



    public  void getPengkajianAwal(String id){

        cbFraktur.setClickable(false);cbArthritis.setClickable(false);cbMasalah.setClickable(false);cbJaringanParut.setClickable(false);
        cbKemerahan.setClickable(false);cbLaserasi.setClickable(false);cbUlserasi.setClickable(false);cbEkimosis.setClickable(false);cbLepuh.setClickable(false);
        cbLukaBakar.setClickable(false);

        rbAlergiYa.setClickable(false);rbAlergiTidak.setClickable(false);rbRingan.setClickable(false);rbSedang.setClickable(false);rbBerat.setClickable(false);
        rbAmbulasiYa.setClickable(false);rbAmbulasiTidak.setClickable(false);rbDiaforesisYa.setClickable(false);rbDiaforesisTidak.setClickable(false);

        etReaksiAlergi.setKeyListener(null);etPerubahanSistem.setKeyListener(null);etPenyebab.setKeyListener(null);etPerilakuRisiko.setKeyListener(null);etJumlahTransfusi.setKeyListener(null);
        etKapanTransfusi.setKeyListener(null);etGambaranTransfusi.setKeyListener(null);etSuhuTubuh.setKeyListener(null);etLukaBakar.setKeyListener(null);

        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPengkajianAwal4.php?id_pasien="+id;
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray users = null;
                        try {
                            users = response.getJSONArray("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < users.length(); i++) {

                            try {
                                JSONObject obj = users.getJSONObject(i);
                                if(obj.getInt("Alergi")==1){
                                    rbAlergiYa.setChecked(true);
                                }
                                if(obj.getInt("Alergi")==0){
                                    rbAlergiTidak.setChecked(true);
                                }
                                etReaksiAlergi.setText(obj.getString("Reaksi_Alergi"));
                                etPerubahanSistem.setText(obj.getString("Perubahan_Sistem_Imun"));
                                etPenyebab.setText(obj.getString("Penyebab_Perubahan_Imun"));
                                etPerilakuRisiko.setText(obj.getString("Perilaku_Risiko_Tinggi"));
                                etJumlahTransfusi.setText(obj.getString("Jumlah_Transfusi"));
                                etKapanTransfusi.setText(obj.getString("Kapan_Transfusi"));
                                etGambaranTransfusi.setText(obj.getString("Gambar_Reaksi_Transfusi"));
                                if(obj.getString("Risiko_Cedera_Jatuh").equals("Ringan")){
                                    rbRingan.setChecked(true);
                                }
                                if(obj.getString("Risiko_Cedera_Jatuh").equals("Sedang")){
                                    rbSedang.setChecked(true);
                                }
                                if(obj.getString("Risiko_Cedera_Jatuh").equals("Berat")){
                                    rbBerat.setChecked(true);
                                }
                                if(obj.getInt("Fraktur") == 1){
                                    cbFraktur.setChecked(true);
                                }
                                if(obj.getInt("Arthritis")==1){
                                    cbArthritis.setChecked(true);
                                }
                                if(obj.getInt("Masalah_Punggung")==1){
                                    cbMasalah.setChecked(true);
                                }
                                if(obj.getInt("Alat_Ambulasi")==1){
                                    rbAmbulasiYa.setChecked(true);
                                }if(obj.getInt("Alat_Ambulasi")==0){
                                    rbAmbulasiTidak.setChecked(true);
                                }
                                etSuhuTubuh.setText(obj.getString("Suhu_Tubuh")+"\u00b0C");
                                if(obj.getInt("Diaforesis")==1){
                                    rbDiaforesisYa.setChecked(true);
                                }if(obj.getInt("Diaforesis")==0){
                                    rbDiaforesisTidak.setChecked(true);
                                }
                                if(obj.getInt("Jaringan_Parut")==1){
                                    cbJaringanParut.setChecked(true);
                                }
                                if(obj.getInt("Kemerahan")==1){
                                    cbKemerahan.setChecked(true);
                                }
                                if(obj.getInt("Laserasi")==1){
                                    cbLaserasi.setChecked(true);
                                }
                                if(obj.getInt("Ulserasi")==1){
                                    cbUlserasi.setChecked(true);
                                }
                                if(obj.getInt("Ekimosis")==1){
                                    cbEkimosis.setChecked(true);
                                }
                                if(obj.getInt("Lepuh")==1){
                                    cbLepuh.setChecked(true);
                                }
                                if(obj.getInt("Luka_Bakar")==1){
                                    cbLukaBakar.setChecked(true);
                                }
                                else{
                                    btnLanjut.setText("Kembali");
                                }
                                etLukaBakar.setText(obj.getString("Derajat_Luka_Bakar")+" %");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(req);
//        if(!cbLukaBakar.isChecked()){
//
//        }
    }




    public void insertPengkajianAwal(int Alergi,String Reaksi_Alergi,String Perubahan_Sistem_Imun
            ,String Penyebab_Perubahan_Imun,String Perilaku_Risiko_Tinggi,String Jumlah_Transfusi,String Kapan_Transfusi,String Gambar_Reaksi_Transfusi,
                                     String Risiko_Cedera_Jatuh,int Fraktur
            ,int Arthritis,int Masalah_Punggung,int Alat_Ambulasi,String Suhu_Tubuh,int Diaforesis,int Jaringan_Parut,int Kemerahan,int Laserasi,int Ulserasi,
                                     int Ekimosis,int Lepuh,int Luka_Bakar,String Derajat_Luka_Bakar,int Id_Pasien){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("Alergi",Alergi);
            objDetail.put("Reaksi_Alergi",Reaksi_Alergi);
            objDetail.put("Perubahan_Sistem_Imun",Perubahan_Sistem_Imun);
            objDetail.put("Penyebab_Perubahan_Imun",Penyebab_Perubahan_Imun);
            objDetail.put("Perilaku_Risiko_Tinggi",Perilaku_Risiko_Tinggi);
            objDetail.put("Jumlah_Transfusi",Jumlah_Transfusi);
            objDetail.put("Kapan_Transfusi",Kapan_Transfusi);
            objDetail.put("Gambar_Reaksi_Transfusi",Gambar_Reaksi_Transfusi);
            objDetail.put("Risiko_Cedera_Jatuh",Risiko_Cedera_Jatuh);
            objDetail.put("Fraktur",Fraktur);
            objDetail.put("Arthritis",Arthritis);
            objDetail.put("Masalah_Punggung",Masalah_Punggung);
            objDetail.put("Alat_Ambulasi",Alat_Ambulasi);
            objDetail.put("Suhu_Tubuh",Suhu_Tubuh);
            objDetail.put("Diaforesis",Diaforesis);
            objDetail.put("Jaringan_Parut",Jaringan_Parut);
            objDetail.put("Kemerahan",Kemerahan);
            objDetail.put("Laserasi",Laserasi);
            objDetail.put("Ulserasi",Ulserasi);
            objDetail.put("Ekimosis",Ekimosis);
            objDetail.put("Lepuh",Lepuh);
            objDetail.put("Luka_Bakar",Luka_Bakar);
            objDetail.put("Derajat_Luka_Bakar",Derajat_Luka_Bakar);
            objDetail.put("Id_Pasien",Id_Pasien);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, INSERT_PENGKAJIAN_AWAL_4_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                if(luka==1) {
                                    Intent i = new Intent(PengkajianAwalActivity4.this, PengkajianAwalActivity5.class);
                                    i.putExtra("panduan","insert");
                                    startActivity(i);
                                }
                                else{
                                    Intent i = new Intent(PengkajianAwalActivity4.this, DetailPasienActivity.class);
                                    i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(i);
                                }
                            }
                            else{
                                Toast.makeText(PengkajianAwalActivity4.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(PengkajianAwalActivity4.this);
        requestQueue.add(stringRequest);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
