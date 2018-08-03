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

public class PengkajianAwalActivity2 extends AppCompatActivity {
    Button btnLanjut;


    EditText etFrekuensiBab,etKarakteristikFeses,etFrekuensiBak,
    etKarakteristikUrin,etBisingUsus,etNyeriTekan,etTerabaMassa,
    etLingkarAbdomen,etJumlahUrin,etTanggalKateter,etTipeStoma,etLainEliminasi,
    etKebiasaanDiet,etFrekuensiMakanan,etSarapan,etMakanSiang,etMakanMalam,
    etCemilan,etMakananTerakhir,etMakananSuka,etDisebabkan,etDisembuhkan,
    etJenisAlergi,etBeratBadanBiasa,etBeratBadanSekarang,etTinggiBadan,etIMT
            ,etKategoriIMT,etDerajat,etKondisiGigi,etPenampilanLidah,
            etGlukosaDarah,etDibantuOleh,etAlatBantu,etPenampilanUmum,
            etCaraBerpakian,etKebiasaanPribadi,etKondisiKulitKepala;

    CheckBox cbKonstipasi,cbDiare,cbInkontinensia,cbUrgensi,cbFrekuensi
            ,cbRetensi,cbSpasmeBladder,cbRasaTerbakar,cbLunak,cbKeras,cbNyeriTekan,
            cbTerabaMassa,cbKehilanganSeleraMakan,cbMual,cbMuntah,cbNyeriUluHati,
            cbAlergiMakanan,cbIntoleransiMakanan,cbUmum,cbTungkai,cbPeriorbital,cbAsites
            ,cbPembesaranKelenjarTiroid,cbHernia,cbBauBadan,cbAdanyaKutu;

    int konstipasi =0,diare =0, inkontinensia =0,urgensi =0, frekuensi =0, retensi =0, spasme =0, terbakar =0, lunak =0,
        keras =0, nyeritekan=0, teraba=0,seleramakan =0, mual =0,muntah =0, uluhati =0,alergi =0,intoleransi=0, umum=0,
        tungkai =0, periorbital =0,asites=0,tiroid=0,hernia=0, bau=0,kutu=0;


    RadioButton rbLaksatifYa,rbLaksatifTidak,rbEnemaYa,rbEnemaTidak,
            rbPendarahanYa,rbPendarahanTidak,rbRiwayatGinjalYa,rbRiwayatGinalTidak,
            rbDiuretikYa,rbDiuretikTidak,rbHerbalYa,rbHerbalTidak,rbHemoroidYa,
            rbHemoroidTidak,rbFistulaYa,rbFistulaTidak,rbKateterYa,rbKatetertidak
            ,rbStomaYa,rbStomaTidak,rbKesulitanMengunyahYa,rbKesulitanMengunyahTidak,
            rbPerubahanBeratBadanYa,rbPerubahanBeratBadanTidak,rbTurgorNormal,
            rbTurgorMenurun,rbMukosaLembab,rbMukosaKering,rbMobilitasMandiri,rbMobilitasDibantu,
    rbMakanMandiri,rbMakanDibantu,rbHygieneMandiri,rbHygieneDibantu,rbBerpakaianMandiri,
    rbBerpakaianDibantu,rbTolietingMandiri,rbToiletingDibantu;

    int laksatif=0,enema=0,pendarahan=0,ginjal=0,diuretik=0,herbal=0,hemoroid=0,fistula=0,kateter=0,stoma=0,mengunyah=0,
    perubahanberat=0,turgor=0,mukosa=0,mobilitas=0,makan=0,hygiene=0,berpakaian=0,toileting=0;

    SessionIdPasien sessionIdPasien;
    SessionLogin sessionLogin;
    RequestQueue queue;
    Context context;
    public String UPDATE_PENGKAJIAN_AWAL_2_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updatePengkajian2.php";

    String tglkateter;

    AlertDialog dialog;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengkajian_awal2);
        context = this;
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pengkajian Awal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());

        queue = Volley.newRequestQueue(this);

        btnLanjut = findViewById(R.id.btnLanjut);
        builder = new AlertDialog.Builder(this);

        etFrekuensiBab = findViewById(R.id.etFrekuensiBab);
        etKarakteristikFeses= findViewById(R.id.etKarakteristikFeses);
        etFrekuensiBak= findViewById(R.id.etFrekuensiBak);
        etKarakteristikUrin= findViewById(R.id.etKarakteristikUrin);
        etBisingUsus= findViewById(R.id.etBisingUsus);
        etNyeriTekan= findViewById(R.id.etNyeriTekan);
        etTerabaMassa= findViewById(R.id.etTerabaMassa);
        etLingkarAbdomen= findViewById(R.id.etLingkarAbdomen);
        etJumlahUrin= findViewById(R.id.etJumlahUrin);
        etTanggalKateter= findViewById(R.id.etTanggalKateter);
        etTipeStoma= findViewById(R.id.etTipeStoma);
        etLainEliminasi= findViewById(R.id.etLainEliminasi);
        etKebiasaanDiet= findViewById(R.id.etKebiasaanDiet);
        etFrekuensiMakanan= findViewById(R.id.etFrekuensiMakanan);
        etSarapan= findViewById(R.id.etSarapan);
        etMakanSiang= findViewById(R.id.etMakanSiang);
        etMakanMalam= findViewById(R.id.etMakanMalam);
        etCemilan= findViewById(R.id.etCemilan);
        etMakananTerakhir= findViewById(R.id.etMakananTerakhir);
        etMakananSuka= findViewById(R.id.etMakananSuka);
        etDisebabkan= findViewById(R.id.etDisebabkan);
        etDisembuhkan= findViewById(R.id.etDisembuhkan);
        etJenisAlergi= findViewById(R.id.etJenisAlergi);
        etBeratBadanBiasa= findViewById(R.id.etBeratBadanBiasa);
        etBeratBadanSekarang= findViewById(R.id.etBeratBadanSekarang);
        etTinggiBadan= findViewById(R.id.etTinggiBadan);
        etIMT= findViewById(R.id.etIMT);
        etKategoriIMT= findViewById(R.id.etKategoriIMT);
        etDerajat= findViewById(R.id.etDerajat);
        etKondisiGigi= findViewById(R.id.etKondisiGigi);
        etPenampilanLidah= findViewById(R.id.etPenampilanLidah);
        etGlukosaDarah= findViewById(R.id.etGlukosaDarah);
        etDibantuOleh= findViewById(R.id.etDibantuOleh);
        etAlatBantu= findViewById(R.id.etAlatBantu);
        etPenampilanUmum= findViewById(R.id.etPenampilanUmum);
        etCaraBerpakian= findViewById(R.id.etCaraBerpakaian);
        etKebiasaanPribadi= findViewById(R.id.etKebiasaanPribadi);
        etKondisiKulitKepala= findViewById(R.id.etKondisiKulitKepala);


        cbKonstipasi= findViewById(R.id.cbKonstipasi);
        cbDiare= findViewById(R.id.cbDiare);
        cbInkontinensia= findViewById(R.id.cbInkontinensia);
        cbUrgensi= findViewById(R.id.cbUrgensi);
        cbFrekuensi= findViewById(R.id.cbFrekuensi);
        cbRetensi= findViewById(R.id.cbRetensi);
        cbSpasmeBladder= findViewById(R.id.cbSpasmeBladder);
        cbRasaTerbakar= findViewById(R.id.cbRasaTerbakar);
        cbLunak= findViewById(R.id.cbLunak);
        cbKeras= findViewById(R.id.cbKeras);
        cbNyeriTekan= findViewById(R.id.cbNyeriTekan);
        cbTerabaMassa= findViewById(R.id.cbTerabaMassa);
        cbKehilanganSeleraMakan= findViewById(R.id.cbKehilanganSeleraMakan);
        cbMual= findViewById(R.id.cbMual);
        cbMuntah= findViewById(R.id.cbMuntah);
        cbNyeriUluHati= findViewById(R.id.cbNyeriUluHati);
        cbAlergiMakanan= findViewById(R.id.cbAlergiMakanan);
        cbIntoleransiMakanan= findViewById(R.id.cbIntoleransiMakanan);
        cbUmum= findViewById(R.id.cbUmum);
        cbTungkai= findViewById(R.id.cbTungkai);
        cbPeriorbital= findViewById(R.id.cbPeriorbital);
        cbAsites= findViewById(R.id.cbAsites);
        cbPembesaranKelenjarTiroid= findViewById(R.id.cbPembesaranKelenjarTiroid);
        cbHernia= findViewById(R.id.cbHernia);
        cbBauBadan= findViewById(R.id.cbBauBadan);
        cbAdanyaKutu= findViewById(R.id.cbAdanyaKutu);

        cbKonstipasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    konstipasi=1;
                }
                else{
                    konstipasi=0;
                }
            }
        });

        cbDiare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    diare=1;
                }
                else{
                    diare=0;
                }
            }
        });
        cbInkontinensia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    inkontinensia=1;
                }
                else{
                    inkontinensia=0;
                }
            }
        });
        cbUrgensi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    urgensi=1;
                }
                else{
                    urgensi=0;
                }
            }
        });
        cbFrekuensi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    frekuensi=1;
                }
                else{
                    frekuensi=0;
                }
            }
        });
        cbRetensi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    retensi=1;
                }
                else{
                    retensi=0;
                }
            }
        });
        cbMuntah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    muntah=1;
                }
                else{
                    muntah=0;
                }
            }
        });
        cbSpasmeBladder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    spasme=1;
                }
                else{
                    spasme=0;
                }
            }
        });
        cbRasaTerbakar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    terbakar=1;
                }
                else{
                    terbakar=0;
                }
            }
        });
        cbLunak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    lunak=1;
                }
                else{
                    lunak=0;
                }
            }
        });
        cbKeras.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    keras=1;
                }
                else{
                    keras=0;
                }
            }
        });
        cbNyeriTekan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    nyeritekan=1;
                }
                else{
                    nyeritekan=0;
                }
            }
        });
        cbTerabaMassa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    teraba=1;
                }
                else{
                    teraba=0;
                }
            }
        });
        cbKehilanganSeleraMakan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    seleramakan=1;
                }
                else{
                    seleramakan=0;
                }
            }
        });
        cbMual.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    mual=1;
                }
                else{
                    mual=0;
                }
            }
        });
        cbNyeriUluHati.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    uluhati=1;
                }
                else{
                    uluhati=0;
                }
            }
        });
        cbAlergiMakanan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    alergi=1;
                }
                else{
                    alergi=0;
                }
            }
        });
        cbIntoleransiMakanan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    intoleransi=1;
                }
                else{
                    intoleransi=0;
                }
            }
        });
        cbUmum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    umum=1;
                }
                else{
                    umum=0;
                }
            }
        });
        cbTungkai.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tungkai=1;
                }
                else{
                    tungkai=0;
                }
            }
        });
        cbPeriorbital.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    periorbital=1;
                }
                else{
                    periorbital=0;
                }
            }
        });
        cbAsites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    asites=1;
                }
                else{
                    asites=0;
                }
            }
        });
        cbPembesaranKelenjarTiroid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    tiroid=1;
                }
                else{
                    tiroid=0;
                }
            }
        });
        cbHernia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    hernia=1;
                }
                else{
                    hernia=0;
                }
            }
        });
        cbBauBadan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    bau=1;
                }
                else{
                    bau=0;
                }
            }
        });
        cbAdanyaKutu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                    kutu=1;
                }
                else{
                    kutu=0;
                }
            }
        });

        rbLaksatifYa = findViewById(R.id.rbLaksatifYa);
        rbLaksatifTidak = findViewById(R.id.rbLaksatifTidak);
        rbEnemaYa = findViewById(R.id.rbEnemaYa);
        rbEnemaTidak = findViewById(R.id.rbEnemaTidak);
        rbPendarahanYa = findViewById(R.id.rbPendarahanYa);
        rbPendarahanTidak = findViewById(R.id.rbPendarahanTidak);
        rbRiwayatGinjalYa = findViewById(R.id.rbRiwayatGinjalYa);
        rbRiwayatGinalTidak = findViewById(R.id.rbRiwayatGinjalTidak);
        rbDiuretikYa = findViewById(R.id.rbDiuretikYa);
        rbDiuretikTidak = findViewById(R.id.rbDiuretikTidak);
        rbHerbalYa = findViewById(R.id.rbHerbalYa);
        rbHerbalTidak = findViewById(R.id.rbHerbalTidak);
        rbHemoroidYa = findViewById(R.id.rbHemoroidYa);
        rbHemoroidTidak = findViewById(R.id.rbHemoroidTidak);
        rbFistulaYa = findViewById(R.id.rbFistulaYa);
        rbFistulaTidak = findViewById(R.id.rbFistulaTidak);
        rbKateterYa = findViewById(R.id.rbKateterYa);
        rbKatetertidak = findViewById(R.id.rbKateterTidak);
        rbStomaYa = findViewById(R.id.rbStomaYa);
        rbStomaTidak = findViewById(R.id.rbStomaTidak);
        rbKesulitanMengunyahYa = findViewById(R.id.rbKesulitanMengunyahYa);
        rbKesulitanMengunyahTidak = findViewById(R.id.rbKesulitanMengunyahTidak);
        rbPerubahanBeratBadanYa = findViewById(R.id.rbPerubahanBeratBadanYa);
        rbPerubahanBeratBadanTidak = findViewById(R.id.rbPerubahanBeratBadanTidak);
        rbTurgorNormal = findViewById(R.id.rbTurgorNormal);
        rbTurgorMenurun = findViewById(R.id.rbTurgorMenurun);
        rbMukosaLembab = findViewById(R.id.rbMukosaLembab);
        rbMukosaKering = findViewById(R.id.rbMukosaKering);
        rbMobilitasMandiri = findViewById(R.id.rbMobilitasMandiri);
        rbMobilitasDibantu = findViewById(R.id.rbMobilitasDibantu);
        rbMakanMandiri = findViewById(R.id.rbMakanMandiri);
        rbMakanDibantu = findViewById(R.id.rbMakanDibantu);
        rbHygieneMandiri = findViewById(R.id.rbHygieneMandiri);
        rbHygieneDibantu = findViewById(R.id.rbHygieneDibantu);
        rbBerpakaianMandiri = findViewById(R.id.rbBerpakaianMandiri);
        rbBerpakaianDibantu = findViewById(R.id.rbBerpakaianDibantu);
        rbTolietingMandiri = findViewById(R.id.rbToiletingMandiri);
        rbToiletingDibantu = findViewById(R.id.rbToiletingDibantu);



        rbLaksatifYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    laksatif=1;
                }
            }
        });
        rbLaksatifTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    laksatif=0;
                }
            }
        });
        rbEnemaYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    enema=1;
                }
            }
        });
        rbEnemaTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    enema=0;
                }
            }
        });
        rbPendarahanYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pendarahan=1;
                }
            }
        });
        rbPendarahanTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pendarahan=0;
                }
            }
        });
        rbRiwayatGinjalYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ginjal=1;
                }
            }
        });
        rbRiwayatGinalTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ginjal=0;
                }
            }
        });
        rbDiuretikYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    diuretik=1;
                }
            }
        });
        rbDiuretikTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    diuretik=0;
                }
            }
        });
        rbHerbalYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    herbal=1;
                }
            }
        });
        rbHerbalTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    herbal=0;
                }
            }
        });
        rbHemoroidYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hemoroid=1;
                }
            }
        });
        rbHemoroidTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hemoroid=0;
                }
            }
        });
        rbFistulaYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fistula=1;
                }
            }
        });
        rbFistulaTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fistula=0;
                }
            }
        });
        rbKateterYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kateter=1;
                }
            }
        });
        rbKatetertidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kateter=0;
                }
            }
        });
        rbStomaYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    stoma=1;
                }
            }
        });
        rbStomaTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    stoma=0;
                }
            }
        });
        rbKesulitanMengunyahYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mengunyah=1;
                }
            }
        });
        rbKesulitanMengunyahTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mengunyah=0;
                }
            }
        });
        rbPerubahanBeratBadanYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    perubahanberat=1;
                }
            }
        });
        rbPerubahanBeratBadanTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    perubahanberat=0;
                }
            }
        });
        rbTurgorNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    turgor=1;
                }
            }
        });
        rbTurgorMenurun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    turgor=0;
                }
            }
        });
        rbMukosaLembab.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mukosa=1;
                }
            }
        });
        rbMukosaKering.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mukosa=0;
                }
            }
        });
        rbMobilitasMandiri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mobilitas=1;
                }
            }
        });
        rbMobilitasDibantu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mobilitas=0;
                }
            }
        });
        rbMakanMandiri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    makan=1;
                }
            }
        });
        rbMakanDibantu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    makan=0;
                }
            }
        });
        rbHygieneMandiri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hygiene=1;
                }
            }
        });
        rbHygieneDibantu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    hygiene=0;
                }
            }
        });
        rbBerpakaianMandiri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    berpakaian=1;
                }
            }
        });
        rbBerpakaianDibantu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    berpakaian=0;
                }
            }
        });
        rbTolietingMandiri.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    toileting=1;
                }
            }
        });
        rbToiletingDibantu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    toileting=0;
                }
            }
        });


        getPengkajianAwal(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(PengkajianAwalActivity2.this,PengkajianAwalActivity3.class);
                    startActivity(i);

            }
        });
    }




    public  void getPengkajianAwal(String id){
                cbKonstipasi.setClickable(false);cbDiare.setClickable(false);cbInkontinensia.setClickable(false);cbUrgensi.setClickable(false);cbFrekuensi.setClickable(false);
                cbRetensi.setClickable(false);cbSpasmeBladder.setClickable(false);cbRasaTerbakar.setClickable(false);cbLunak.setClickable(false);cbKeras.setClickable(false);
                cbNyeriTekan.setClickable(false);cbTerabaMassa.setClickable(false);cbKehilanganSeleraMakan.setClickable(false);cbMual.setClickable(false);cbMuntah.setClickable(false);
                cbNyeriUluHati.setClickable(false);cbAlergiMakanan.setClickable(false);cbIntoleransiMakanan.setClickable(false);cbUmum.setClickable(false);cbTungkai.setClickable(false);
                cbPeriorbital.setClickable(false);cbAsites.setClickable(false);cbPembesaranKelenjarTiroid.setClickable(false);cbHernia.setClickable(false);cbBauBadan.setClickable(false);
                cbAdanyaKutu.setClickable(false);


        rbLaksatifYa.setClickable(false);rbLaksatifTidak.setClickable(false);rbEnemaYa.setClickable(false);rbEnemaTidak.setClickable(false);
                rbPendarahanYa.setClickable(false);rbPendarahanTidak.setClickable(false);rbRiwayatGinjalYa.setClickable(false);rbRiwayatGinalTidak.setClickable(false);
                rbDiuretikYa.setClickable(false);rbDiuretikTidak.setClickable(false);rbHerbalYa.setClickable(false);rbHerbalTidak.setClickable(false);rbHemoroidYa.setClickable(false);
                rbHemoroidTidak.setClickable(false);rbFistulaYa.setClickable(false);rbFistulaTidak.setClickable(false);rbKateterYa.setClickable(false);rbKatetertidak.setClickable(false);
                rbStomaYa.setClickable(false);rbStomaTidak.setClickable(false);rbKesulitanMengunyahYa.setClickable(false);rbKesulitanMengunyahTidak.setClickable(false);
                rbPerubahanBeratBadanYa.setClickable(false);rbPerubahanBeratBadanTidak.setClickable(false);rbTurgorNormal.setClickable(false);
                rbTurgorMenurun.setClickable(false);rbMukosaLembab.setClickable(false);rbMukosaKering.setClickable(false);rbMobilitasMandiri.setClickable(false);rbMobilitasDibantu.setClickable(false);
                rbMakanMandiri.setClickable(false);rbMakanDibantu.setClickable(false);rbHygieneMandiri.setClickable(false);rbHygieneDibantu.setClickable(false);rbBerpakaianMandiri.setClickable(false);
                rbBerpakaianDibantu.setClickable(false);rbTolietingMandiri.setClickable(false);rbToiletingDibantu.setClickable(false);



                etFrekuensiBab.setKeyListener(null);etKarakteristikFeses.setKeyListener(null);etFrekuensiBak.setKeyListener(null);
                etKarakteristikUrin.setKeyListener(null);etBisingUsus.setKeyListener(null);etNyeriTekan.setKeyListener(null);etTerabaMassa.setKeyListener(null);
                etLingkarAbdomen.setKeyListener(null);etJumlahUrin.setKeyListener(null);etTanggalKateter.setKeyListener(null);etTipeStoma.setKeyListener(null);etLainEliminasi.setKeyListener(null);
                etKebiasaanDiet.setKeyListener(null);etFrekuensiMakanan.setKeyListener(null);etSarapan.setKeyListener(null);etMakanSiang.setKeyListener(null);etMakanMalam.setKeyListener(null);
                etCemilan.setKeyListener(null);etMakananTerakhir.setKeyListener(null);etMakananSuka.setKeyListener(null);etDisebabkan.setKeyListener(null);etDisembuhkan.setKeyListener(null);
                etJenisAlergi.setKeyListener(null);etBeratBadanBiasa.setKeyListener(null);etBeratBadanSekarang.setKeyListener(null);etTinggiBadan.setKeyListener(null);etIMT.setKeyListener(null);
                etKategoriIMT.setKeyListener(null);etDerajat.setKeyListener(null);etKondisiGigi.setKeyListener(null);etPenampilanLidah.setKeyListener(null);
                etGlukosaDarah.setKeyListener(null);etDibantuOleh.setKeyListener(null);etAlatBantu.setKeyListener(null);etPenampilanUmum.setKeyListener(null);
                etCaraBerpakian.setKeyListener(null);etKebiasaanPribadi.setKeyListener(null);etKondisiKulitKepala.setKeyListener(null);


        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPengkajianAwal2.php?id_pasien="+id;
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
                                etFrekuensiBab.setText(obj.getString("Frekuensi_BAB"));
                                if(obj.getInt("BAB_Konstipasi")==1){
                                    cbKonstipasi.setChecked(true);
                                }
                                if(obj.getInt("BAB_Inkontinensia")==1){
                                    cbInkontinensia.setChecked(true);
                                }
                                if(obj.getInt("BAB_Diare")==1){
                                    cbDiare.setChecked(true);
                                }
                                etKarakteristikFeses.setText(obj.getString("Karakteristik_Feses"));
                                if(obj.getInt("Laksatif")==1){
                                    rbLaksatifYa.setChecked(true);
                                }
                                if(obj.getInt("Laksatif")==0){
                                    rbLaksatifTidak.setChecked(true);
                                }
                                if(obj.getInt("Enema")==1){
                                    rbEnemaYa.setChecked(true);
                                }
                                if(obj.getInt("Enema")==0){
                                    rbEnemaTidak.setChecked(true);
                                }
                                if(obj.getInt("Riwayat_Pendarahan")==1){
                                    rbPendarahanYa.setChecked(true);
                                }
                                if(obj.getInt("Riwayat_Pendarahan")==0){
                                    rbPendarahanTidak.setChecked(true);
                                }
                                etFrekuensiBak.setText(obj.getString("Frekuensi_BAK"));

                                if(obj.getInt("BAK_Urgensi")==1){
                                    cbUrgensi.setChecked(true);
                                }
                                if(obj.getInt("BAK_Retensi")==1){
                                    cbRetensi.setChecked(true);
                                }
                                if(obj.getInt("BAK_Rasa")==1){
                                    cbRasaTerbakar.setChecked(true);
                                }
                                if(obj.getInt("BAK_Frekuensi")==1){
                                    cbFrekuensi.setChecked(true);
                                }
                                if(obj.getInt("BAK_Spasme")==1){
                                    cbSpasmeBladder.setChecked(true);
                                }
                                etKarakteristikUrin.setText(obj.getString("Karakteristik_Urine"));
                                if(obj.getInt("Riwayat_Penyakit_Ginjal")==1){
                                    rbRiwayatGinjalYa.setChecked(true);
                                }
                                if(obj.getInt("Riwayat_Penyakit_Ginjal")==0){
                                    rbRiwayatGinalTidak.setChecked(true);
                                }
                                if(obj.getInt("Diuretik")==1){
                                    rbDiuretikYa.setChecked(true);
                                }
                                if(obj.getInt("Diuretik")==0){
                                    rbDiuretikTidak.setChecked(true);
                                }
                                if(obj.getInt("Herbal")==1){
                                    rbHerbalYa.setChecked(true);
                                }
                                if(obj.getInt("Herbal")==0){
                                    rbHerbalTidak.setChecked(true);
                                }
                                etBisingUsus.setText(obj.getString("Bising_Usus")+" kali/menit");
                                if(obj.getInt("Lunak")==1){
                                    cbLunak.setChecked(true);
                                }
                                if(obj.getInt("Keras")==1){
                                    cbKeras.setChecked(true);
                                }
                                if(obj.getInt("Nyeri_Tekan")==1){
                                    cbNyeriTekan.setChecked(true);
                                }
                                etNyeriTekan.setText(obj.getString("Lokasi_Nyeri_Tekan"));
                                if(obj.getInt("Teraba_Massa")==1){
                                    cbTerabaMassa.setChecked(true);
                                }
                                etTerabaMassa.setText(obj.getString("Lokasi_Teraba_Massa"));
                                etLingkarAbdomen.setText(obj.getString("Lingkar_Abdomen")+" cm");
                                if(obj.getInt("Hemoroid")==1){
                                    rbHemoroidYa.setChecked(true);
                                }
                                if(obj.getInt("Hemoroid")==0){
                                    rbHemoroidTidak.setChecked(true);
                                }
                                if(obj.getInt("Fistula")==1){
                                    rbFistulaYa.setChecked(true);
                                }
                                if(obj.getInt("Fistula")==0){
                                    rbFistulaTidak.setChecked(true);
                                }
                                if(obj.getInt("Kateter_Urine")==1){
                                    rbKateterYa.setChecked(true);
                                }
                                if(obj.getInt("Kateter_Urine")==0){
                                    rbKatetertidak.setChecked(true);
                                }
                                etTanggalKateter.setText(obj.getString("Tgl_Pasang_Kateter"));
                                etJumlahUrin.setText(obj.getString("Jumlah_Urine"));
                                if(obj.getInt("Stoma")==1){
                                    rbStomaYa.setChecked(true);
                                }
                                if(obj.getInt("Stoma")==0){
                                    rbStomaTidak.setChecked(true);
                                }
                                etTipeStoma.setText(obj.getString("Tipe_Stoma"));
                                etLainEliminasi.setText(obj.getString("Masalah_Eliminasi_Lainnya"));
                                etKebiasaanDiet.setText(obj.getString("Kebiasaan_Diet"));
                                etFrekuensiMakanan.setText(obj.getString("Frek_Makan")+" kali/hari");
                                etSarapan.setText(obj.getString("Pola_Diet_Sarapan"));
                                etMakanSiang.setText(obj.getString("Pola_Diet_Siang"));
                                etMakanMalam.setText(obj.getString("Pola_Diet_Malam"));
                                etCemilan.setText(obj.getString("Pola_Diet_Camilan"));
                                etMakananTerakhir.setText(obj.getString("Makanan_Terakhir"));
                                etMakananSuka.setText(obj.getString("Makanan_Disukai"));
                                if(obj.getInt("Hilang_Selera_Makan")==1){
                                    cbKehilanganSeleraMakan.setChecked(true);
                                }
                                if(obj.getInt("Muntah")==1){
                                    cbMuntah.setChecked(true);
                                }
                                if(obj.getInt("Mual")==1){
                                    cbMual.setChecked(true);
                                }
                                if(obj.getInt("Nyeri_Ulu_Hati")==1){
                                    cbNyeriUluHati.setChecked(true);
                                }
                                etDisebabkan.setText(obj.getString("Disebabkan_Oleh"));
                                etDisembuhkan.setText(obj.getString("Disembuhkn_Oleh"));
                                if(obj.getInt("Alergi_Makanan")==1){
                                    cbAlergiMakanan.setChecked(true);
                                }
                                if(obj.getInt("Intoleransi_Makanan")==1){
                                    cbIntoleransiMakanan.setChecked(true);
                                }
                                if(obj.getInt("Teraba_Massa")==1){
                                    cbTerabaMassa.setChecked(true);
                                }
                                etJenisAlergi.setText(obj.getString("Jenis_Alergi_Intoleransi"));
                                if(obj.getInt("Sulit_Menguyah_Menelan")==1){
                                    rbKesulitanMengunyahYa.setChecked(true);
                                }
                                if(obj.getInt("Sulit_Menguyah_Menelan")==0){
                                    rbKesulitanMengunyahTidak.setChecked(true);
                                }
                                etBeratBadanBiasa.setText(obj.getString("Berat_Badan_Biasa")+" kg");
                                if(obj.getInt("Perubahan_Berat_Badan")==1){
                                    rbPerubahanBeratBadanYa.setChecked(true);
                                }
                                if(obj.getInt("Perubahan_Berat_Badan")==0){
                                    rbPerubahanBeratBadanTidak.setChecked(true);
                                }
                                etBeratBadanSekarang.setText(obj.getString("Berat_Badan_Sekarang")+" kg");
                                etTinggiBadan.setText(obj.getString("Tinggi_Badan")+" cm");
                                etIMT.setText(obj.getString("IMT") +"kg/m"+Html.fromHtml("<sup><small>2</small></sup>"));
                                etKategoriIMT.setText(obj.getString("Kategori_IMT"));
                                if(obj.getInt("Turgor_Kulit")==1){
                                    rbTurgorNormal.setChecked(true);
                                }
                                if(obj.getInt("Turgor_Kulit")==0){
                                    rbTurgorMenurun.setChecked(true);
                                }
                                if(obj.getInt("Membran_Mukosa")==1){
                                    rbMukosaLembab.setChecked(true);
                                }
                                if(obj.getInt("Membran_Mukosa")==0){
                                    rbMukosaKering.setChecked(true);
                                }
                                if(obj.getInt("Edema_Umum")==1){
                                    cbUmum.setChecked(true);
                                }
                                if(obj.getInt("Edema_Tungkai")==1){
                                    cbTungkai.setChecked(true);
                                }
                                if(obj.getInt("Edema_Periorbital")==1){
                                    cbPeriorbital.setChecked(true);
                                }
                                if(obj.getInt("Edema_Asites")==1){
                                    cbAsites.setChecked(true);
                                }
                                etDerajat.setText(obj.getString("Derajat_Edema"));
                                if(obj.getInt("Pembesaran_Tiroid")==1){
                                    cbPembesaranKelenjarTiroid.setChecked(true);
                                }
                                if(obj.getInt("Hernia_Massa")==1){
                                    cbHernia.setChecked(true);
                                }
                                etKondisiGigi.setText(obj.getString("Kondisi_Gigi_Gusi"));
                                etPenampilanLidah.setText(obj.getString("Penampilan_Lidah"));
                                etGlukosaDarah.setText(obj.getString("Glukosa_Darah")+" mg/dl");
                                if(obj.getInt("Mobilitas")==1){
                                    rbMobilitasMandiri.setChecked(true);
                                }
                                if(obj.getInt("Mobilitas")==0){
                                    rbMobilitasDibantu.setChecked(true);
                                }
                                if(obj.getInt("Makan")==1){
                                    rbMakanMandiri.setChecked(true);
                                }
                                if(obj.getInt("Makan")==0){
                                    rbMakanDibantu.setChecked(true);
                                }
                                if(obj.getInt("Hygiene")==1){
                                    rbHygieneMandiri.setChecked(true);
                                }
                                if(obj.getInt("Hygiene")==0){
                                    rbHygieneDibantu.setChecked(true);
                                }
                                if(obj.getInt("Berpakaian")==1){
                                    rbBerpakaianMandiri.setChecked(true);
                                }
                                if(obj.getInt("Berpakaian")==0){
                                    rbBerpakaianDibantu.setChecked(true);
                                }
                                if(obj.getInt("Toileting")==1){
                                    rbTolietingMandiri.setChecked(true);
                                }
                                if(obj.getInt("Toileting")==0){
                                    rbToiletingDibantu.setChecked(true);
                                }
                                etDibantuOleh.setText(obj.getString("DIbantu_Oleh"));
                                etAlatBantu.setText(obj.getString("Alat_Bantu_Prostetik"));
                                etPenampilanUmum.setText(obj.getString("Penampilan_Umum"));
                                etCaraBerpakian.setText(obj.getString("Cara_Berpakaian"));
                                etKebiasaanPribadi.setText(obj.getString("Kebiasaan_Pribadi"));
                                if(obj.getInt("Bau_Badan") == 1){
                                    cbBauBadan.setChecked(true);
                                }
                                if(obj.getInt("Ada_Kutu")==1){
                                    cbAdanyaKutu.setChecked(true);
                                }
                                etKondisiKulitKepala.setText(obj.getString("Kulit_Kepala"));
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






    public void updatePengkajianAwal(int id_pasien,String Frekuensi_BAB,int BAB_Konstipasi,int BAB_Inkontinensia,int BAB_Diare,String Karakteristik_Feses,
                                     int Laksatif, int Enema,int Riwayat_Pendarahan,String Frekuensi_BAK,int BAK_Urgensi,int BAK_Retensi,int BAK_Rasa,int BAK_Frekuensi
                                     ,int BAK_Spasme,String Karakteristik_Urine,int Riwayat_Penyakit_Ginjal,int Diuretik,int Herbal,String Bising_Usus,int Lunak,
                                     int  Keras,int Nyeri_Tekan,String Lokasi_Nyeri_Tekan,int Teraba_Massa, String Lokasi_Teraba_Massa,String Lingkar_Abdomen,int Hemoroid,
                                     int Fistula,int Kateter_Urine,String Tgl_Pasang_Kateter,String Jumlah_Urine,int Stoma,String Tipe_Stoma,String Masalah_Eliminasi_Lainnya,
                                     String Kebiasaan_Diet,String Frek_Makan,String Pola_Diet_Sarapan,String Pola_Diet_Siang,String Pola_Diet_Malam,String Pola_Diet_Camilan,
                                     String Makanan_Terakhir,String Makanan_Disukai,int Hilang_Selera_Makan, int Muntah, int Mual, int Nyeri_Ulu_Hati, String Disebabkan_Oleh,
                                     String Disembuhkan_Oleh,int Alergi_Makanan,int Intoleransi_Makanan,String Jenis_Alergi_Intoleransi, int Sulit_Mengunyah_Menelan,
                                     String Berat_Badan_Biasa,int Perubahan_Berat_Badan,String Berat_Badan_Sekarang,String Tinggi_Badan,String IMT,String Kategori_IMT,int Turgor_Kulit,
                                     int Membran_Mukosa,int Edema_Umum,int Edema_Tungkai,int Edema_Periorbital, int Edema_Asites, String Derajat_Edema, int Pembesaran_Tiroid, int Hernia_Massa, String Kondisi_Gigi_Gusi,
                                     String Penampilan_Lidah,String Glukosa_Darah,int Mobilitas,int Makan,int Hygiene,int Berpakaian, int Toileting, String DIbantu_Oleh,String Alat_Bantu_Prostetik, String Penampilan_Umum,
                                     String Cara_Berpakaian, String Kebiasaan_Pribadi, int Bau_Badan, int Ada_Kutu, String Kulit_Kepala){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("id_pasien",id_pasien);
            objDetail.put("Frekuensi_BAB",Frekuensi_BAB);
            objDetail.put("BAB_Konstipasi",BAB_Konstipasi);
            objDetail.put("BAB_Inkontinensia",BAB_Inkontinensia);
            objDetail.put("BAB_Diare",BAB_Diare);
            objDetail.put("Karakteristik_Feses",Karakteristik_Feses);
            objDetail.put("Laksatif",Laksatif);
            objDetail.put("Enema",Enema);
            objDetail.put("Riwayat_Pendarahan",Riwayat_Pendarahan);
            objDetail.put("Frekuensi_BAK",Frekuensi_BAK);
            objDetail.put("BAK_Urgensi",BAK_Urgensi);
            objDetail.put("BAK_Retensi",BAK_Retensi);
            objDetail.put("BAK_Rasa",BAK_Rasa);
            objDetail.put("BAK_Frekuensi",BAK_Frekuensi);
            objDetail.put("BAK_Spasme",BAK_Spasme);
            objDetail.put("Karakteristik_Urine",Karakteristik_Urine);
            objDetail.put("Riwayat_Penyakit_Ginjal",Riwayat_Penyakit_Ginjal);
            objDetail.put("Diuretik",Diuretik);
            objDetail.put("Herbal",Herbal);
            objDetail.put("Bising_Usus",Bising_Usus);
            objDetail.put("Lunak",Lunak);
            objDetail.put("Keras",Keras);
            objDetail.put("Nyeri_Tekan",Nyeri_Tekan);
            objDetail.put("Lokasi_Nyeri_Tekan",Lokasi_Nyeri_Tekan);
            objDetail.put("Teraba_Massa",Teraba_Massa);
            objDetail.put("Lokasi_Teraba_Massa",Lokasi_Teraba_Massa);
            objDetail.put("Lingkar_Abdomen",Lingkar_Abdomen);
            objDetail.put("Hemoroid",Hemoroid);
            objDetail.put("Fistula",Fistula);
            objDetail.put("Kateter_Urine",Kateter_Urine);
            objDetail.put("Tgl_Pasang_Kateter",Tgl_Pasang_Kateter);
            objDetail.put("Jumlah_Urine",Jumlah_Urine);
            objDetail.put("Stoma",Stoma);
            objDetail.put("Tipe_Stoma",Tipe_Stoma);
            objDetail.put("Masalah_Eliminasi_Lainnya",Masalah_Eliminasi_Lainnya);
            objDetail.put("Kebiasaan_Diet",Kebiasaan_Diet);
            objDetail.put("Frek_Makan",Frek_Makan);
            objDetail.put("Pola_Diet_Sarapan",Pola_Diet_Sarapan);
            objDetail.put("Pola_Diet_Siang",Pola_Diet_Siang);
            objDetail.put("Pola_Diet_Malam",Pola_Diet_Malam);
            objDetail.put("Pola_Diet_Camilan",Pola_Diet_Camilan);
            objDetail.put("Makanan_Terakhir",Makanan_Terakhir);
            objDetail.put("Makanan_Disukai",Makanan_Disukai);
            objDetail.put("Hilang_Selera_Makan",Hilang_Selera_Makan);
            objDetail.put("Muntah",Muntah);
            objDetail.put("Mual",Mual);
            objDetail.put("Nyeri_Ulu_Hati",Nyeri_Ulu_Hati);
            objDetail.put("Disebabkan_Oleh",Disebabkan_Oleh);
            objDetail.put("Disembuhkan_Oleh",Disembuhkan_Oleh);
            objDetail.put("Alergi_Makanan",Alergi_Makanan);
            objDetail.put("Intoleransi_Makanan",Intoleransi_Makanan);
            objDetail.put("Jenis_Alergi_Intoleransi",Jenis_Alergi_Intoleransi);
            objDetail.put("Sulit_Mengunyah_Menelan",Sulit_Mengunyah_Menelan);
            objDetail.put("Berat_Badan_Biasa",Berat_Badan_Biasa);
            objDetail.put("Perubahan_Berat_Badan",Perubahan_Berat_Badan);
            objDetail.put("Berat_Badan_Sekarang",Berat_Badan_Sekarang);
            objDetail.put("Tinggi_Badan",Tinggi_Badan);
            objDetail.put("IMT",IMT);
            objDetail.put("Kategori_IMT",Kategori_IMT);
            objDetail.put("Turgor_Kulit",Turgor_Kulit);
            objDetail.put("Membran_Mukosa",Membran_Mukosa);
            objDetail.put("Edema_Umum",Edema_Umum);
            objDetail.put("Edema_Tungkai",Edema_Tungkai);
            objDetail.put("Edema_Periorbital",Edema_Periorbital);
            objDetail.put("Edema_Asites",Edema_Asites);
            objDetail.put("Derajat_Edema",Derajat_Edema);
            objDetail.put("Pembesaran_Tiroid",Pembesaran_Tiroid);
            objDetail.put("Hernia_Massa",Hernia_Massa);
            objDetail.put("Kondisi_Gigi_Gusi",Kondisi_Gigi_Gusi);
            objDetail.put("Penampilan_Lidah",Penampilan_Lidah);
            objDetail.put("Glukosa_Darah",Glukosa_Darah);
            objDetail.put("Mobilitas",Mobilitas);
            objDetail.put("Makan",Makan);
            objDetail.put("Hygiene",Hygiene);
            objDetail.put("Berpakaian",Berpakaian);
            objDetail.put("Toileting",Toileting);
            objDetail.put("DIbantu_Oleh",DIbantu_Oleh);
            objDetail.put("Alat_Bantu_Prostetik",Alat_Bantu_Prostetik);
            objDetail.put("Penampilan_Umum",Penampilan_Umum);
            objDetail.put("Cara_Berpakaian",Cara_Berpakaian);
            objDetail.put("Kebiasaan_Pribadi",Kebiasaan_Pribadi);
            objDetail.put("Bau_Badan",Bau_Badan);
            objDetail.put("Ada_Kutu",Ada_Kutu);
            objDetail.put("Kulit_Kepala",Kulit_Kepala);




            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_PENGKAJIAN_AWAL_2_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Intent i = new Intent(PengkajianAwalActivity2.this,PengkajianAwalActivity3.class);
                                i.putExtra("panduan","insert");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(PengkajianAwalActivity2.this, "Gagal", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(PengkajianAwalActivity2.this);
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

