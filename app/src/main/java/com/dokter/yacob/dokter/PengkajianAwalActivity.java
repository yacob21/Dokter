package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

public class PengkajianAwalActivity extends AppCompatActivity {
    Button btnLanjut;
    EditText etKeluhanUtama,etRiwayatKeluhanUtama,etDiagnosisMedis,etPekerjaan,etAktivitasWaktuLuang,etPerasaanBosan,
    etKeterbatasanKarenaKondisi,etSiang,etMalam,etInsomia,etLainKebiasaanTidur,etLainResponsAktivitas,
    etMassa,etPostur,etRentangGerak,etKekuatan1,etKekuatan2,etKekuatan3,etKekuatan4,etLokasiDeformitas,
    etEkstremitasSejakKapan,etTekananDarah,etFrekuensiNadi;

    CheckBox cbInsomia,cbSesakNapas,cbKelelahan,cbPalpitasi,cbLainResponsAktivitas,
    cbHipertensi,cbMasalahJantung,cbDemamRematik,cbEdemaTungkai,cbFlebitis,cbKlaudikasi,
    cbBatuk,cbPenyembuhanLambat,cbPerubahanFrekuensi,cbKesemutan,cbKebas,
    cbThrill,cbDorongan,cbS3,cbS4,cbGallop,cbMurmur,cbSianosis,cbPucat,cbTabuh,cbClubbingFinger
    ,cbKonjungtivaAnemis,cbSkleraIkterik;

    int insomia =0,sesaknapas=0,kelelahan=0,palpitasi=0,lainresponsaktivitas=0,hipertensi=0,masalahjantung=0,demamrematik=0,edematungkai=0,flebitis=0,klaudikasi=0,
    batuk=0,penyembuhanlambat=0,perubahanfrekuensi=0,kesemutan=0,kebas=0,thrill=0,dorongan=0,s3=0,s4=0,gallop=0,murmur=0,sianosis=0,pucat=0,tabuh=0,clubbing=0,konjungtiva=0,sklera=0;

    RadioButton rbSegarYa,rbSegarTidak,rbTremorYa,rbTremorTidak,rbDeformitasYa,
            rbDeformitasTidak,rbS1Normal,rbS1Tidak,rbS2Normal,rbS2Tidak,
            rbDistensiYa,rbDistensiTidak,rbHangat,rbDingin,rbKurangDetik,
            rbLebihDetik,rbHomanNegatif,rbHomanPositif,rbBibirNormal,rbBibirPucat;

    int segar = 0,tremor = 0,deformitas =0,s1=0,s2=0,distensi=0,suhu=0,kapiler=0,homan=0,bibir=0;

    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    SessionLogin sessionLogin;
    Context context;
    public String INSERT_PENGKAJIAN_AWAL_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertPengkajianAwal.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengkajian_awal);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pengkajian Awal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        sessionLogin = new SessionLogin(getApplicationContext());

        queue = Volley.newRequestQueue(this);

        btnLanjut = findViewById(R.id.btnLanjut);

        etKeluhanUtama= findViewById(R.id.etKeluhanUtama);
        etRiwayatKeluhanUtama = findViewById(R.id.etRiwayatKeluhanUtama);
        etDiagnosisMedis= findViewById(R.id.etDiagnosisMedis);
        etPekerjaan= findViewById(R.id.etPekerjaan);
        etAktivitasWaktuLuang = findViewById(R.id.etAktivitasWaktuLuang);
        etPerasaanBosan= findViewById(R.id.etPerasaanBosan);
        etKeterbatasanKarenaKondisi= findViewById(R.id.etKeterbatasanKarenaKondisi);
        etSiang= findViewById(R.id.etSiang);
        etMalam= findViewById(R.id.etMalam);
        etInsomia= findViewById(R.id.etInsomia);
        etLainKebiasaanTidur= findViewById(R.id.etLainKebiasaanTidur);
        etLainResponsAktivitas= findViewById(R.id.etLainResponsAktivitas);
        etMassa= findViewById(R.id.etMassa);
        etPostur= findViewById(R.id.etPostur);
        etRentangGerak= findViewById(R.id.etRentangGerak);
        etKekuatan1= findViewById(R.id.etKekuatan1);
        etKekuatan2= findViewById(R.id.etKekuatan2);
        etKekuatan3= findViewById(R.id.etKekuatan3);
        etKekuatan4= findViewById(R.id.etKekuatan4);
        etLokasiDeformitas= findViewById(R.id.etLokasiDeformitas);
        etEkstremitasSejakKapan= findViewById(R.id.etEkstremitasSejakKapan);
        etTekananDarah= findViewById(R.id.etTekananDarah);
        etFrekuensiNadi= findViewById(R.id.etFrekuensiNadi);

        cbInsomia= findViewById(R.id.cbInsomia);
        cbSesakNapas= findViewById(R.id.cbSesakNapas);
        cbKelelahan= findViewById(R.id.cbKelelahan);
        cbPalpitasi= findViewById(R.id.cbPalpitasi);
        cbLainResponsAktivitas= findViewById(R.id.cbLainResponsAktivitas);
        cbHipertensi= findViewById(R.id.cbHipertensi);
        cbMasalahJantung= findViewById(R.id.cbMasalahJantung);
        cbDemamRematik= findViewById(R.id.cbDemamRematik);
        cbEdemaTungkai= findViewById(R.id.cbEdemaTungkai);
        cbFlebitis= findViewById(R.id.cbFlebitis);
        cbKlaudikasi= findViewById(R.id.cbKlaudikasi);
        cbBatuk= findViewById(R.id.cbBatuk);
        cbPenyembuhanLambat= findViewById(R.id.cbPenyembuhanLambat);
        cbPerubahanFrekuensi= findViewById(R.id.cbPerubahanFrekuensi);
        cbKesemutan= findViewById(R.id.cbKesemutan);
        cbKebas= findViewById(R.id.cbKebas);
        cbThrill= findViewById(R.id.cbThrill);
        cbDorongan= findViewById(R.id.cbDorongan);
        cbS3= findViewById(R.id.cbS3);
        cbS4= findViewById(R.id.cbS4);
        cbGallop= findViewById(R.id.cbGallop);
        cbMurmur= findViewById(R.id.cbMurmur);
        cbSianosis= findViewById(R.id.cbSianosis);
        cbPucat= findViewById(R.id.cbPucat);
        cbTabuh= findViewById(R.id.cbTabuh);
        cbClubbingFinger= findViewById(R.id.cbClubbingFinger);
        cbKonjungtivaAnemis= findViewById(R.id.cbKonjungtivaAnemis);
        cbSkleraIkterik= findViewById(R.id.cbSkleraIkterik);

        cbInsomia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    insomia=1;
                }
                else{
                    insomia=0;
                }
            }
        });

        cbSesakNapas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                   sesaknapas=1;
                }
                else{
                    sesaknapas=0;
                }
            }
        });

        cbKelelahan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kelelahan=1;
                }
                else{
                     kelelahan=0;
                }
            }
        });

        cbPalpitasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    palpitasi=1;
                }
                else{
                    palpitasi=0;
                }
            }
        });

        cbLainResponsAktivitas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lainresponsaktivitas=1;
                }
                else{
                    lainresponsaktivitas=0;
                }
            }
        });

        cbHipertensi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hipertensi=1;
                }
                else{
                    hipertensi=0;
                }
            }
        });
        cbMasalahJantung.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    masalahjantung=1;
                }
                else{
                    masalahjantung=0;
                }
            }
        });

        cbDemamRematik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    demamrematik=1;
                }
                else{
                    demamrematik=0;
                }
            }
        });

        cbEdemaTungkai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    edematungkai=1;
                }
                else{
                    edematungkai=0;
                }
            }
        });

        cbFlebitis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    flebitis=1;
                }
                else{
                    flebitis=0;
                }
            }
        });

        cbKlaudikasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    klaudikasi=1;
                }
                else{
                    klaudikasi=0;
                }
            }
        });

        cbBatuk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    batuk=1;
                }
                else{
                    batuk=0;
                }
            }
        });

        cbPenyembuhanLambat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    penyembuhanlambat=1;
                }
                else{
                    penyembuhanlambat=0;
                }
            }
        });
        cbPerubahanFrekuensi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    perubahanfrekuensi=1;
                }
                else{
                    perubahanfrekuensi=0;
                }
            }
        });

        cbKesemutan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kesemutan=1;
                }
                else{
                    kesemutan=0;
                }
            }
        });

        cbKebas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kebas=1;
                }
                else{
                    kebas=0;
                }
            }
        });
        cbThrill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    thrill=1;
                }
                else{
                    thrill=0;
                }
            }
        });

        cbDorongan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dorongan=1;
                }
                else{
                    dorongan=0;
                }
            }
        });

        cbS3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s3=1;
                }
                else{
                    s3=0;
                }
            }
        });

        cbS4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s4=1;
                }
                else{
                    s4=0;
                }
            }
        });

        cbGallop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    gallop=1;
                }
                else{
                    gallop=0;
                }
            }
        });

        cbMurmur.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    murmur=1;
                }
                else{
                    murmur=0;
                }
            }
        });

        cbSianosis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sianosis=1;
                }
                else{
                    sianosis=0;
                }
            }
        });

        cbPucat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pucat=1;
                }
                else{
                    pucat=0;
                }
            }
        });

        cbTabuh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tabuh=1;
                }
                else{
                    tabuh=0;
                }
            }
        });
        cbClubbingFinger.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    clubbing=1;
                }
                else{
                    clubbing=0;
                }
            }
        });
        cbKonjungtivaAnemis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    konjungtiva=1;
                }
                else{
                    konjungtiva=0;
                }
            }
        });

        cbSkleraIkterik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sklera=1;
                }
                else{
                    sklera=0;
                }
            }
        });

        rbSegarYa= findViewById(R.id.rbSegarYa);
        rbSegarTidak= findViewById(R.id.rbSegarTidak);
        rbTremorYa= findViewById(R.id.rbTremorYa);
        rbTremorTidak= findViewById(R.id.rbTremorTidak);
        rbDeformitasYa= findViewById(R.id.rbDeformitasYa);
        rbDeformitasTidak= findViewById(R.id.rbDeformitasTidak);
        rbS1Normal= findViewById(R.id.rbS1Normal);
        rbS1Tidak= findViewById(R.id.rbS1Tidak);
        rbS2Normal= findViewById(R.id.rbS2Normal);
        rbS2Tidak= findViewById(R.id.rbS2Tidak);
        rbDistensiYa= findViewById(R.id.rbDistensiYa);
        rbDistensiTidak= findViewById(R.id.rbDistensiTidak);
        rbHangat= findViewById(R.id.rbHangat);
        rbDingin= findViewById(R.id.rbDingin);
        rbKurangDetik= findViewById(R.id.rbKurangDetik);
        rbLebihDetik= findViewById(R.id.rbLebihDetik);
        rbHomanNegatif= findViewById(R.id.rbHomanNegatif);
        rbHomanPositif= findViewById(R.id.rbHomanPositif);
        rbBibirNormal= findViewById(R.id.rbBibirNormal);
        rbBibirPucat= findViewById(R.id.rbBibirPucat);


        rbSegarYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    segar=1;
                }
            }
        });

        rbSegarTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    segar=0;
                }
            }
        });
        rbTremorYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tremor=1;
                }
            }
        });
        rbTremorTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tremor=0;
                }
            }
        });
        rbDeformitasYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    deformitas=1;
                }
            }
        });
        rbDeformitasTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    deformitas=0;
                }
            }
        });
        rbS1Normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s1=1;
                }
            }
        });
        rbS1Tidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s1=0;
                }
            }
        });
        rbS2Normal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s2=1;
                }
            }
        });
        rbS2Tidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    s2=0;
                }
            }
        });
        rbDistensiYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    distensi=1;
                }
            }
        });
        rbDistensiTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    distensi=0;
                }
            }
        });
        rbHangat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    suhu=1;
                }
            }
        });
        rbDingin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    suhu=0;
                }
            }
        });
        rbKurangDetik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kapiler=1;
                }
            }
        });
        rbLebihDetik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kapiler=0;
                }
            }
        });
        rbHomanNegatif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    homan=1;
                }
            }
        });
        rbHomanPositif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    homan=0;
                }
            }
        });
        rbBibirNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    bibir=1;
                }
            }
        });
        rbBibirPucat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    bibir=0;
                }
            }
        });



        getPengkajianAwal(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(PengkajianAwalActivity.this,PengkajianAwalActivity2.class);
                    startActivity(i);
            }
        });
    }



    public  void getPengkajianAwal(String id){
        cbInsomia.setClickable(false);
        cbSesakNapas.setClickable(false);
        cbKelelahan.setClickable(false);
        cbPalpitasi.setClickable(false);
        cbLainResponsAktivitas.setClickable(false);
        cbHipertensi.setClickable(false);
        cbMasalahJantung.setClickable(false);
        cbDemamRematik.setClickable(false);
        cbEdemaTungkai.setClickable(false);
        cbFlebitis.setClickable(false);
        cbKlaudikasi.setClickable(false);
        cbBatuk.setClickable(false);
        cbPenyembuhanLambat.setClickable(false);
        cbPerubahanFrekuensi.setClickable(false);
        cbKesemutan.setClickable(false);
        cbKebas.setClickable(false);
        cbThrill.setClickable(false);
        cbDorongan.setClickable(false);
        cbS3.setClickable(false);
        cbS4.setClickable(false);
        cbGallop.setClickable(false);
        cbMurmur.setClickable(false);
        cbSianosis.setClickable(false);
        cbPucat.setClickable(false);
        cbTabuh.setClickable(false);
        cbClubbingFinger.setClickable(false);
        cbKonjungtivaAnemis.setClickable(false);
        cbSkleraIkterik.setClickable(false);

        rbSegarYa.setClickable(false);
        rbSegarTidak.setClickable(false);
        rbTremorYa.setClickable(false);
        rbTremorTidak.setClickable(false);
        rbDeformitasYa.setClickable(false);
        rbDeformitasTidak.setClickable(false);
        rbS1Normal.setClickable(false);
        rbS1Tidak.setClickable(false);
        rbS2Normal.setClickable(false);
        rbS2Tidak.setClickable(false);
        rbDistensiYa.setClickable(false);
        rbDistensiTidak.setClickable(false);
        rbHangat.setClickable(false);
        rbDingin.setClickable(false);
        rbKurangDetik.setClickable(false);
        rbLebihDetik.setClickable(false);
        rbHomanNegatif.setClickable(false);
        rbHomanPositif.setClickable(false);
        rbBibirNormal.setClickable(false);
        rbBibirPucat.setClickable(false);

        etKeluhanUtama.setKeyListener(null);
        etRiwayatKeluhanUtama.setKeyListener(null);
        etDiagnosisMedis.setKeyListener(null);
        etPekerjaan.setKeyListener(null);
        etAktivitasWaktuLuang.setKeyListener(null);
        etPerasaanBosan.setKeyListener(null);
        etKeterbatasanKarenaKondisi.setKeyListener(null);
        etSiang.setKeyListener(null);
        etMalam.setKeyListener(null);
        etInsomia.setKeyListener(null);
        etLainKebiasaanTidur.setKeyListener(null);
        etLainResponsAktivitas.setKeyListener(null);
        etMassa.setKeyListener(null);
        etPostur.setKeyListener(null);
        etRentangGerak.setKeyListener(null);
        etKekuatan1.setKeyListener(null);
        etKekuatan2.setKeyListener(null);
        etKekuatan3.setKeyListener(null);
        etKekuatan4.setKeyListener(null);
        etLokasiDeformitas.setKeyListener(null);
        etEkstremitasSejakKapan.setKeyListener(null);
        etTekananDarah.setKeyListener(null);
        etFrekuensiNadi.setKeyListener(null);
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPengkajianAwal.php?id_pasien="+id;
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
                                etKeluhanUtama.setText(obj.getString("Keluhan_Utama"));
                                etRiwayatKeluhanUtama.setText(obj.getString("Riwayat_Keluhan_Utama"));
                                etDiagnosisMedis.setText(obj.getString("Diagnosis_Medis"));
                                etPekerjaan.setText(obj.getString("Pekerjaan"));
                                etAktivitasWaktuLuang.setText(obj.getString("Aktivitas_Luang"));
                                etPerasaanBosan.setText(obj.getString("Bosan"));
                                etKeterbatasanKarenaKondisi.setText(obj.getString("Keterbatasan_Kondisi"));
                                etSiang.setText(obj.getString("Tidur_Siang")+" jam");
                                etMalam.setText(obj.getString("Tidur_Malam")+" jam");
                                if(obj.getInt("Insomnia") == 1){
                                    cbInsomia.setChecked(true);
                                }
                                etInsomia.setText(obj.getString("Hub_Insomnia"));
                                if(obj.getInt("Bangun_Segar") == 1){
                                    rbSegarYa.setChecked(true);
                                }
                                if(obj.getInt("Bangun_Segar")== 0){
                                    rbSegarTidak.setChecked(true);
                                }
                                etLainKebiasaanTidur.setText(obj.getString("Kebiasaan_Tidur_Lainnya"));
                                if(obj.getInt("Respons_Aktivitas_Sesak")==1){
                                    cbSesakNapas.setChecked(true);
                                }
                                if(obj.getInt("Respons_Aktivitas_Kelelahan")==1){
                                    cbKelelahan.setChecked(true);
                                }
                                if(obj.getInt("Respons_Aktivitas_Palpitasi")==1){
                                    cbPalpitasi.setChecked(true);
                                }
                                if(obj.getInt("Respons_Aktivitas_Lainnya")==1){
                                    cbLainResponsAktivitas.setChecked(true);
                                }
                                etLainResponsAktivitas.setText(obj.getString("Respons_Aktivitas_Isi_Lainnya"));
                                etMassa.setText(obj.getString("Massa_Otot"));
                                etPostur.setText(obj.getString("Postur"));
                                if(obj.getInt("Tremor")==1){
                                    rbTremorYa.setChecked(true);
                                }
                                if(obj.getInt("Tremor")==0){
                                    rbTremorTidak.setChecked(true);
                                }
                                etRentangGerak.setText(obj.getString("Rentang_Gerak"));
                                etKekuatan1.setText(obj.getString("Kekuatan_1"));
                                etKekuatan2.setText(obj.getString("Kekuatan_2"));
                                etKekuatan3.setText(obj.getString("Kekuatan_3"));
                                etKekuatan4.setText(obj.getString("Kekuatan_4"));
                                if(obj.getInt("Deformitas")==1){
                                    rbDeformitasYa.setChecked(true);
                                }
                                if(obj.getInt("Deformitas")==0){
                                    rbDeformitasTidak.setChecked(true);
                                }
                                etLokasiDeformitas.setText(obj.getString("Lokasi_Deformitas"));
                                if(obj.getInt("Hipertensi")==1){
                                    cbHipertensi.setChecked(true);
                                }
                                if(obj.getInt("Masalah_Jantung")==1){
                                    cbMasalahJantung.setChecked(true);
                                }
                                if(obj.getInt("Demam_Rematik")==1){
                                    cbDemamRematik.setChecked(true);
                                }
                                if(obj.getInt("Edema_Tungkai_Sirkulasi")==1){
                                    cbEdemaTungkai.setChecked(true);
                                }
                                if(obj.getInt("Flebitis")==1){
                                    cbFlebitis.setChecked(true);
                                }
                                if(obj.getInt("Klaudikasi")==1){
                                    cbKlaudikasi.setChecked(true);
                                }
                                if(obj.getInt("Hemoptisis")==1){
                                    cbBatuk.setChecked(true);
                                }
                                if(obj.getInt("Penyembuhan_Lambat")==1){
                                    cbPenyembuhanLambat.setChecked(true);
                                }
                                if(obj.getInt("Perubahan_Urine")==1){
                                    cbPerubahanFrekuensi.setChecked(true);
                                }
                                if(obj.getInt("Ekstremitas_Kesemutan")==1){
                                    cbKesemutan.setChecked(true);
                                }
                                if(obj.getInt("Ekstremitas_Kebas")==1){
                                    cbKebas.setChecked(true);
                                }
                                etEkstremitasSejakKapan.setText(obj.getString("Kapan_Masalah_Sirkulasi"));
                                etTekananDarah.setText(obj.getString("Tekanan_Darah")+" MmHg");
                                etFrekuensiNadi.setText(obj.getString("Frek_Nadi")+" kali/menit");
                                if(obj.getInt("Palpasi_Thrill")==1){
                                    cbThrill.setChecked(true);
                                }
                                if(obj.getInt("Palpasi_Dorongan")==1){
                                    cbDorongan.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_S1")==1){
                                    rbS1Normal.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_S1")==0){
                                    rbS1Tidak.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_S2")==1){
                                    rbS2Normal.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_S2")==0){
                                    rbS2Tidak.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_Tambahan_S3")==1){
                                    cbS3.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_Tambahan_S4")==1){
                                    cbS4.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_Tambahan_Gallop")==1){
                                    cbGallop.setChecked(true);
                                }
                                if(obj.getInt("Bunyi_Tambahan_Murmur")==1){
                                    cbMurmur.setChecked(true);
                                }
                                if(obj.getInt("Distensi_Vena_Jugularis")==1){
                                    rbDistensiYa.setChecked(true);
                                }
                                if(obj.getInt("Distensi_Vena_Jugularis")==0){
                                    rbDistensiTidak.setChecked(true);
                                }
                                if(obj.getInt("Ekstremitas_Sianosis")==1){
                                    cbSianosis.setChecked(true);
                                }
                                if(obj.getInt("Ekstremitas_Pucat")==1){
                                    cbPucat.setChecked(true);
                                }
                                if(obj.getInt("Suhu")==1){
                                    rbHangat.setChecked(true);
                                }
                                if(obj.getInt("Suhu")==0){
                                    rbDingin.setChecked(true);
                                }
                                if(obj.getInt("Pengisian_Kapiler")==1){
                                    rbKurangDetik.setChecked(true);
                                }
                                if(obj.getInt("Pengisian_Kapiler")==0){
                                    rbLebihDetik.setChecked(true);
                                }
                                if(obj.getInt("Kuku_Tabuh")==1){
                                    cbTabuh.setChecked(true);
                                }
                                if(obj.getInt("Kuku_Clubbing")==1){
                                    cbClubbingFinger.setChecked(true);
                                }
                                if(obj.getInt("Tanda_Homan")==1){
                                    rbHomanNegatif.setChecked(true);
                                }
                                if(obj.getInt("Tanda_Homan")==0){
                                    rbHomanPositif.setChecked(true);
                                }
                                if(obj.getInt("Warna_Bibir")==1){
                                    rbBibirNormal.setChecked(true);
                                }
                                if(obj.getInt("Warna_Bibir")==0){
                                    rbBibirPucat.setChecked(true);
                                }
                                if(obj.getInt("Mata_Anemis")==1){
                                    cbKonjungtivaAnemis.setChecked(true);
                                }
                                if(obj.getInt("Mata_Ikterik")==1){
                                    cbSkleraIkterik.setChecked(true);
                                }


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
    }


    public void insertPengkajianAwal(int id_pasien
    ,String nik_perawat,String keluhan_utama,String riwayat_keluhan_utama,
    String diagnosis_medis,String pekerjaan,String aktivitas_luang, String bosan,String
    keterbatasan_kondisi,String tidur_siang,String tidur_malam,int insomia,
    String hub_insomia,int bangun_segar,String kebiasaan_tidur_lainnya,
    int respons_aktivitas_sesak, int respons_aktivitas_kelelahan,
    int respons_aktivitas_palpitasi, int respons_aktivitas_lainnya,
    String respons_aktivitas_isi_lainnya, String massa_otot, String postur,
    int tremor,String rentang_gerak,String kekuatan_1,String kekuatan_2,
    String kekuatan_3,String kekuatan_4,int deformitas,String lokasi_deformitas,
    int hipertensi,int masalah_jantung,int demam_rematik,int edema_tungkai_sirkulasi,
    int flebitis, int klaudikasi, int hemoptisis, int penyembuhan_lambat,
    int perubahan_urine, int ekstremitas_kesemutan,int ekstremitas_kebas,
    String kapan_masalah_sirkulasi,String tekanan_darah,String frek_nadi,
    int palpasi_thrill,int palpasi_dorongan,int bunyi_s1,int bunyi_s2,
    int bunyi_tambahan_s3, int bunyi_tambahan_s4,int bunyi_tambahan_gallop,
    int bunyi_tambahan_murmur, int distensi_vena_jugularis, int ekstremitas_sianosis,
    int ekstremitas_pucat, int suhu,int pengisian_kapiler, int kuku_tabuh,
    int kuku_clubbing, int tanda_homan,int warna_bibir,int mata_anemis,
    int mata_ikterik){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("id_pasien",id_pasien);
            objDetail.put("nik_perawat",nik_perawat);
            objDetail.put("keluhan_utama",keluhan_utama);
            objDetail.put("riwayat_keluhan_utama",riwayat_keluhan_utama);
            objDetail.put("diagnosis_medis",diagnosis_medis);
            objDetail.put("pekerjaan",pekerjaan);
            objDetail.put("aktivitas_luang",aktivitas_luang);
            objDetail.put("bosan",bosan);
            objDetail.put("keterbatasan_kondisi",keterbatasan_kondisi);
            objDetail.put("tidur_siang",tidur_siang);
            objDetail.put("tidur_malam",tidur_malam);
            objDetail.put("insomia",insomia);
            objDetail.put("hub_insomia",hub_insomia);
            objDetail.put("bangun_segar",bangun_segar);
            objDetail.put("kebiasaan_tidur_lainnya",kebiasaan_tidur_lainnya);
            objDetail.put("respons_aktivitas_sesak",respons_aktivitas_sesak);
            objDetail.put("respons_aktivitas_kelelahan",respons_aktivitas_kelelahan);
            objDetail.put("respons_aktivitas_palpitasi",respons_aktivitas_palpitasi);
            objDetail.put("respons_aktivitas_lainnya",respons_aktivitas_lainnya);
            objDetail.put("respons_aktivitas_isi_lainnya",respons_aktivitas_isi_lainnya);
            objDetail.put("massa_otot",massa_otot);
            objDetail.put("postur",postur);
            objDetail.put("tremor",tremor);
            objDetail.put("rentang_gerak",rentang_gerak);
            objDetail.put("kekuatan_1",kekuatan_1);
            objDetail.put("kekuatan_2",kekuatan_2);
            objDetail.put("kekuatan_3",kekuatan_3);
            objDetail.put("kekuatan_4",kekuatan_4);
            objDetail.put("deformitas",deformitas);
            objDetail.put("lokasi_deformitas",lokasi_deformitas);
            objDetail.put("hipertensi",hipertensi);
            objDetail.put("masalah_jantung",masalah_jantung);
            objDetail.put("demam_rematik",demam_rematik);
            objDetail.put("edema_tungkai_sirkulasi",edema_tungkai_sirkulasi);
            objDetail.put("flebitis",flebitis);
            objDetail.put("klaudikasi",klaudikasi);
            objDetail.put("hemoptisis",hemoptisis);
            objDetail.put("penyembuhan_lambat",penyembuhan_lambat);
            objDetail.put("perubahan_urine",perubahan_urine);
            objDetail.put("ekstremitas_kesemutan",ekstremitas_kesemutan);
            objDetail.put("ekstremitas_kebas",ekstremitas_kebas);
            objDetail.put("kapan_masalah_sirkulasi",kapan_masalah_sirkulasi);
            objDetail.put("tekanan_darah",tekanan_darah);
            objDetail.put("frek_nadi",frek_nadi);
            objDetail.put("palpasi_thrill",palpasi_thrill);
            objDetail.put("palpasi_dorongan",palpasi_dorongan);
            objDetail.put("bunyi_s1",bunyi_s1);
            objDetail.put("bunyi_s2",bunyi_s2);
            objDetail.put("bunyi_tambahan_s3",bunyi_tambahan_s3);
            objDetail.put("bunyi_tambahan_s4",bunyi_tambahan_s4);
            objDetail.put("bunyi_tambahan_gallop",bunyi_tambahan_gallop);
            objDetail.put("bunyi_tambahan_murmur",bunyi_tambahan_murmur);
            objDetail.put("distensi_vena_jugularis",distensi_vena_jugularis);
            objDetail.put("ekstremitas_sianosis",ekstremitas_sianosis);
            objDetail.put("ekstremitas_pucat",ekstremitas_pucat);
            objDetail.put("suhu",suhu);
            objDetail.put("pengisian_kapiler",pengisian_kapiler);
            objDetail.put("kuku_tabuh",kuku_tabuh);
            objDetail.put("kuku_clubbing",kuku_clubbing);
            objDetail.put("tanda_homan",tanda_homan);
            objDetail.put("warna_bibir",warna_bibir);
            objDetail.put("mata_anemis",mata_anemis);
            objDetail.put("mata_ikterik",mata_ikterik);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, INSERT_PENGKAJIAN_AWAL_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Intent i = new Intent(PengkajianAwalActivity.this,PengkajianAwalActivity2.class);
                                i.putExtra("panduan","insert");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(PengkajianAwalActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(PengkajianAwalActivity.this);
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
