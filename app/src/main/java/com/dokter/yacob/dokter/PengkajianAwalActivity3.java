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

public class PengkajianAwalActivity3 extends AppCompatActivity {
    Button btnLanjut;

    EditText etLokasiSakitKepala,etFrekuensiSakitKepala,etLokasiNeurosensori
            ,etStroke,etTipeKejang,etAuraKejang,etFrekuensiKejang,
            etCaraMengontrolKejang,etStatusPostikalKejang,etPemeriksaanMata,
            etPemeriksaanTelinga,etAfek,etUkuran,etReflekCahaya,etLokasiParalisis,
            etLokasiNyeri,etIntensitasNyeri,etFrekuensiNyeri,etKualitasNyeri,
            etPenjalaranNyeri,etDurasiNyeri,etFaktorNyeri,etCaraNyeri,etResponsEmosional,
            etJumlahMerokok,etLamaMerokok,etFrekuensiNapas,etKarakteristikSputum;

    CheckBox cbKesemutan,cbKebas,cbKelemahan,cbKehilanganPenghilatan,cbGlaukoma,
    cbKatarak,cbKehilanganPendengaran,cbPerubahanPenciuman,cbPerubahanPengecap,
    cbEpistasis,cbTerorientasi,cbDisorientasi,cbOrang,cbWaktu,cbTempat,
    cbKooperatif,cbMenyerang,cbDelusi,cbHalusinasi,cbMengantuk,cbLetargi
            ,cbStupor,cbKoma,cbKacamata,cbLensaKontak,cbAlatBantuDengar,cbMengkerutkanMuka
            ,cbMenjagaArea,cbPenyempitanFokus,cbDispnea,cbBatuk,cbBronkhitis,cbAsma,cbTuberkolosis
            ,cbPneumonia,cbEmfisema,cbPenggunaanOtot,cbNapasCuping,cbSputum,cbVersikuler,cbRonkhi,cbWheezing;

    int kesemutan=0,kebas=0,kelemahan=0,penglihatan=0,glaukoma=0,katarak=0,pendengaran=0,penciuman=0,pengecap=0,
        epistasis=0,terorientasi=0,disorientasi=0,orang=0,waktu=0,tempat=0,kooperatif=0,menyerang=0,delusi=0,halusinasi=0,
        mengantuk=0,letargi=0,stupor=0,koma=0,kacamata=0,lensakontak=0,alatbantu=0,mengkerutkan=0,menjaga=0,penyempitan=0,
        dispnea=0,batuk=0,bronkhitis=0,asma=0,tuberkolosis=0,pneumonia=0,emfisema=0,otot=0,cuping=0,sputum=0,versikuler=0,
        ronkhi=0,wheezing=0;

    RadioButton rbCederaOtakYa,rbCederaOtakTidak,rbSaatIniBaik,rbSaatIniTerganggu,
            rbLampauBaik,rbLampauTerganggu,rbFacialDropYa,rbFacialDropTidak,
    rbRefleksTendonNormal,rbRefleksTendonTerganggu,rbParalisisYa,rbParalisisTidak,
    rbTerpajanYa,rbTerpajanTidak,rbSimetris,rbTidakSimetris,rbKedalamanNormal,rbKedalamanDangkal,
    rbKedalamanDalam,rbFremitusNormal,rbFremitusMenurun,rbFremitusMeningkat;

    int otak=0,saatini=0,lampau=0,facial=0,tendon=0,paralisis=0,terpajan=0,simetris=0;
    String kedalaman="",fremitus="";

    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    Context context;
    String INSERT_PENGKAJIAN_AWAL_3_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertPengkajianAwal3.php";

    AlertDialog dialog;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengkajian_awal3);
        context = this;
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pengkajian Awal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());

        queue = Volley.newRequestQueue(this);

        btnLanjut = findViewById(R.id.btnLanjut);
        builder = new AlertDialog.Builder(this);

        etLokasiSakitKepala = findViewById(R.id.etLokasiSakitKepala);
        etFrekuensiSakitKepala = findViewById(R.id.etFrekuensiSakitKepala);
        etLokasiNeurosensori = findViewById(R.id.etLokasiNeurosensori);
        etStroke = findViewById(R.id.etStroke);
        etTipeKejang = findViewById(R.id.etTipeKejang);
        etAuraKejang = findViewById(R.id.etAuraKejang);
        etFrekuensiKejang = findViewById(R.id.etFrekuensiKejang);
        etCaraMengontrolKejang = findViewById(R.id.etCaraMengontrolKejang);
        etStatusPostikalKejang = findViewById(R.id.etStatusPostikalKejang);
        etPemeriksaanMata = findViewById(R.id.etPemeriksaanMata);
        etPemeriksaanTelinga = findViewById(R.id.etPemeriksaanTelinga);
        etAfek = findViewById(R.id.etAfek);
        etUkuran = findViewById(R.id.etUkuran);
        etReflekCahaya = findViewById(R.id.etReflekCahaya);
        etLokasiParalisis = findViewById(R.id.etLokasiParalisis);
        etLokasiNyeri = findViewById(R.id.etLokasiNyeri);
        etIntensitasNyeri = findViewById(R.id.etIntensitasNyeri);
        etFrekuensiNyeri = findViewById(R.id.etFrekuensiNyeri);
        etKualitasNyeri = findViewById(R.id.etKualitasNyeri);
        etPenjalaranNyeri = findViewById(R.id.etPenjalaranNyeri);
        etDurasiNyeri = findViewById(R.id.etDurasiNyeri);
        etFaktorNyeri = findViewById(R.id.etFaktorNyeri);
        etCaraNyeri = findViewById(R.id.etCaraNyeri);
        etResponsEmosional = findViewById(R.id.etResponsEmosional);
        etJumlahMerokok = findViewById(R.id.etJumlahMerokok);
        etLamaMerokok = findViewById(R.id.etLamaMerokok);
        etFrekuensiNapas = findViewById(R.id.etFrekuensiNapas);
        etKarakteristikSputum = findViewById(R.id.etKarakteristikSputum);

        cbKesemutan = findViewById(R.id.cbKesemutan);
        cbKebas = findViewById(R.id.cbKebas);
        cbKelemahan = findViewById(R.id.cbKelemahan);
        cbKehilanganPenghilatan = findViewById(R.id.cbKehilanganPenglihatan);
        cbGlaukoma = findViewById(R.id.cbGlaukoma);
        cbKatarak = findViewById(R.id.cbKatarak);
        cbKehilanganPendengaran = findViewById(R.id.cbKehilanganPendengaran);
        cbPerubahanPenciuman = findViewById(R.id.cbPerubahanPenciuman);
        cbPerubahanPengecap = findViewById(R.id.cbPerubahanPengecap);
        cbEpistasis = findViewById(R.id.cbEpistasis);
        cbTerorientasi = findViewById(R.id.cbTerorientasi);
        cbDisorientasi = findViewById(R.id.cbDisorientasi);
        cbOrang = findViewById(R.id.cbOrang);
        cbWaktu = findViewById(R.id.cbWaktu);
        cbTempat = findViewById(R.id.cbTempat);
        cbKooperatif = findViewById(R.id.cbKooperatif);
        cbMenyerang = findViewById(R.id.cbMenyerang);
        cbDelusi = findViewById(R.id.cbDelusi);
        cbHalusinasi = findViewById(R.id.cbHalusinasi);
        cbMengantuk = findViewById(R.id.cbMengantuk);
        cbLetargi = findViewById(R.id.cbLetargi);
        cbStupor = findViewById(R.id.cbStupor);
        cbKoma = findViewById(R.id.cbKoma);
        cbKacamata = findViewById(R.id.cbKacamata);
        cbLensaKontak = findViewById(R.id.cbLensaKontak);
        cbAlatBantuDengar = findViewById(R.id.cbAlatBantuDengar);
        cbMengkerutkanMuka = findViewById(R.id.cbMengkerutkanMuka);
        cbMenjagaArea = findViewById(R.id.cbMenjagaArea);
        cbPenyempitanFokus = findViewById(R.id.cbPenyempitanFokus);
        cbDispnea = findViewById(R.id.cbDispnea);
        cbBatuk = findViewById(R.id.cbBatuk);
        cbBronkhitis = findViewById(R.id.cbBronkhitis);
        cbAsma = findViewById(R.id.cbAsma);
        cbTuberkolosis = findViewById(R.id.cbTuberkulosis);
        cbPneumonia = findViewById(R.id.cbPneumonia);
        cbEmfisema = findViewById(R.id.cbEmfisema);
        cbPenggunaanOtot = findViewById(R.id.cbPenggunaanOtot);
        cbNapasCuping = findViewById(R.id.cbNapasCuping);
        cbSputum = findViewById(R.id.cbSputum);
        cbVersikuler = findViewById(R.id.cbVersikuler);
        cbRonkhi = findViewById(R.id.cbRonkhi);
        cbWheezing = findViewById(R.id.cbWheezing);

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
        cbKelemahan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kelemahan=1;
                }
                else{
                    kelemahan=0;
                }
            }
        });
        cbKehilanganPenghilatan.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    penglihatan=1;
                }
                else{
                    penglihatan=0;
                }
            }
        });
        cbGlaukoma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    glaukoma=1;
                }
                else{
                    glaukoma=0;
                }
            }
        });
        cbKatarak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    katarak=1;
                }
                else{
                    katarak=0;
                }
            }
        });
        cbKehilanganPendengaran.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pendengaran=1;
                }
                else{
                    pendengaran=0;
                }
            }
        });
        cbPerubahanPenciuman.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    penciuman=1;
                }
                else{
                    penciuman=0;
                }
            }
        });
        cbPerubahanPengecap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pengecap=1;
                }
                else{
                    pengecap=0;
                }
            }
        });
        cbEpistasis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    epistasis=1;
                }
                else{
                    epistasis=0;
                }
            }
        });
        cbTerorientasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    terorientasi=1;
                }
                else{
                    terorientasi=0;
                }
            }
        });
        cbDisorientasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    disorientasi=1;
                }
                else{
                    disorientasi=0;
                }
            }
        });
        cbOrang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    orang=1;
                }
                else{
                    orang=0;
                }
            }
        });
        cbWaktu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    waktu=1;
                }
                else{
                    waktu=0;
                }
            }
        });
        cbTempat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tempat=1;
                }
                else{
                    tempat=0;
                }
            }
        });
        cbKooperatif.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kooperatif=1;
                }
                else{
                    kooperatif=0;
                }
            }
        });
        cbMenyerang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    menyerang=1;
                }
                else{
                    menyerang=0;
                }
            }
        });
        cbDelusi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    delusi=1;
                }
                else{
                    delusi=0;
                }
            }
        });
        cbHalusinasi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    halusinasi=1;
                }
                else{
                    halusinasi=0;
                }
            }
        });
        cbMengantuk.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mengantuk=1;
                }
                else{
                    mengantuk=0;
                }
            }
        });
        cbLetargi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    letargi=1;
                }
                else{
                    letargi=0;
                }
            }
        });
        cbStupor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    stupor=1;
                }
                else{
                    stupor=0;
                }
            }
        });
        cbKoma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    koma=1;
                }
                else{
                    koma=0;
                }
            }
        });
        cbKacamata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kacamata=1;
                }
                else{
                    kacamata=0;
                }
            }
        });
        cbLensaKontak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lensakontak=1;
                }
                else{
                    lensakontak=0;
                }
            }
        });
        cbAlatBantuDengar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    alatbantu=1;
                }
                else{
                    alatbantu=0;
                }
            }
        });
        cbMengkerutkanMuka.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    mengkerutkan=1;
                }
                else{
                    mengkerutkan=0;
                }
            }
        });
        cbMenjagaArea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    menjaga=1;
                }
                else{
                    menjaga=0;
                }
            }
        });
        cbPenyempitanFokus.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    penyempitan=1;
                }
                else{
                    penyempitan=0;
                }
            }
        });
        cbDispnea.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    dispnea=1;
                }
                else{
                    dispnea=0;
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
        cbBronkhitis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    bronkhitis=1;
                }
                else{
                    bronkhitis=0;
                }
            }
        });
        cbAsma.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    asma=1;
                }
                else{
                    asma=0;
                }
            }
        });
        cbTuberkolosis.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tuberkolosis=1;
                }
                else{
                    tuberkolosis=0;
                }
            }
        });
        cbPneumonia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    pneumonia=1;
                }
                else{
                    pneumonia=0;
                }
            }
        });
        cbEmfisema.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    emfisema=1;
                }
                else{
                    emfisema=0;
                }
            }
        });
        cbPenggunaanOtot.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    otot=1;
                }
                else{
                    otot=0;
                }
            }
        });
        cbNapasCuping.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    cuping=1;
                }
                else{
                    cuping=0;
                }
            }
        });
        cbSputum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    sputum=1;
                }
                else{
                    sputum=0;
                }
            }
        });
        cbVersikuler.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    versikuler=1;
                }
                else{
                    versikuler=0;
                }
            }
        });
        cbRonkhi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    ronkhi=1;
                }
                else{
                    ronkhi=0;
                }
            }
        });
        cbWheezing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    wheezing=1;
                }
                else{
                    wheezing=0;
                }
            }
        });


        rbCederaOtakYa = findViewById(R.id.rbCederaOtakYa);
        rbCederaOtakTidak = findViewById(R.id.rbCederaOtakTidak);
        rbSaatIniBaik = findViewById(R.id.rbSaatIniBaik);
        rbSaatIniTerganggu = findViewById(R.id.rbSaatIniTerganggu);
        rbLampauBaik = findViewById(R.id.rbLampauBaik);
        rbLampauTerganggu = findViewById(R.id.rbLampauTerganggu);
        rbFacialDropYa = findViewById(R.id.rbFacialDropYa);
        rbFacialDropTidak = findViewById(R.id.rbFacialDropTidak);
        rbRefleksTendonNormal = findViewById(R.id.rbRefleksTendonNormal);
        rbRefleksTendonTerganggu = findViewById(R.id.rbRefleksTendonTerganggu);
        rbParalisisYa = findViewById(R.id.rbParalisisYa);
        rbParalisisTidak = findViewById(R.id.rbParalisisTidak);
        rbTerpajanYa = findViewById(R.id.rbTerpajanYa);
        rbTerpajanTidak = findViewById(R.id.rbTerpajanTidak);
        rbSimetris = findViewById(R.id.rbSimetris);
        rbTidakSimetris = findViewById(R.id.rbTidakSimetris);
        rbKedalamanNormal = findViewById(R.id.rbKedalamanNormal);
        rbKedalamanDangkal = findViewById(R.id.rbKedalamanDalam);
        rbKedalamanDalam = findViewById(R.id.rbKedalamanDalam);
        rbFremitusNormal = findViewById(R.id.rbFremitusNormal);
        rbFremitusMenurun = findViewById(R.id.rbFremitusMenurun);
        rbFremitusMeningkat = findViewById(R.id.rbFremitusMenigkat);


        rbCederaOtakYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    otak=1;
                }
            }
        });
        rbCederaOtakTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    otak=0;
                }
            }
        });
        rbSaatIniBaik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    saatini=1;
                }
            }
        });
        rbSaatIniTerganggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    saatini=0;
                }
            }
        });
        rbLampauBaik.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lampau=1;
                }
            }
        });
        rbLampauTerganggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    lampau=0;
                }
            }
        });
        rbFacialDropYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    facial=1;
                }
            }
        });
        rbFacialDropTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    facial=0;
                }
            }
        });
        rbRefleksTendonNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tendon=1;
                }
            }
        });
        rbRefleksTendonTerganggu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tendon=0;
                }
            }
        });
        rbParalisisYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    paralisis=1;
                }
            }
        });
        rbParalisisTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    paralisis=0;
                }
            }
        });
        rbTerpajanYa.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    terpajan=1;
                }
            }
        });
        rbTerpajanTidak.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    terpajan=0;
                }
            }
        });
        rbSimetris.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    simetris=1;
                }
            }
        });
        rbTidakSimetris.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    simetris=0;
                }
            }
        });
        rbKedalamanNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kedalaman="Normal";
                }
            }
        });
        rbKedalamanDalam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kedalaman="Dalam";
                }
            }
        });
        rbKedalamanDangkal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    kedalaman="Dangkal";
                }
            }
        });
        rbFremitusNormal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fremitus="Normal";
                }
            }
        });
        rbFremitusMeningkat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fremitus="Meningkat";
                }
            }
        });
        rbFremitusMenurun.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    fremitus="Menurun";
                }
            }
        });


        getPengkajianAwal(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));

        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(PengkajianAwalActivity3.this,PengkajianAwalActivity4.class);
                    startActivity(i);
            }
        });
    }

    public  void getPengkajianAwal(String id){

        cbKesemutan.setClickable(false);cbKebas.setClickable(false);cbKelemahan.setClickable(false);cbKehilanganPenghilatan.setClickable(false);cbGlaukoma.setClickable(false);
        cbKatarak.setClickable(false);cbKehilanganPendengaran.setClickable(false);cbPerubahanPenciuman.setClickable(false);cbPerubahanPengecap.setClickable(false);
        cbEpistasis.setClickable(false);cbTerorientasi.setClickable(false);cbDisorientasi.setClickable(false);cbOrang.setClickable(false);cbWaktu.setClickable(false);
        cbTempat.setClickable(false);cbKooperatif.setClickable(false);cbMenyerang.setClickable(false);cbDelusi.setClickable(false);
        cbHalusinasi.setClickable(false);cbMengantuk.setClickable(false);cbLetargi.setClickable(false);
        cbStupor.setClickable(false);cbKoma.setClickable(false);cbKacamata.setClickable(false);cbLensaKontak.setClickable(false);
        cbAlatBantuDengar.setClickable(false);cbMengkerutkanMuka.setClickable(false);cbMenjagaArea.setClickable(false);
        cbPenyempitanFokus.setClickable(false);cbDispnea.setClickable(false);cbBatuk.setClickable(false);
        cbBronkhitis.setClickable(false);cbAsma.setClickable(false);cbTuberkolosis.setClickable(false);
        cbPneumonia.setClickable(false);cbEmfisema.setClickable(false);cbPenggunaanOtot.setClickable(false);cbNapasCuping.setClickable(false);cbSputum.setClickable(false);cbVersikuler.setClickable(false);cbRonkhi.setClickable(false);cbWheezing.setClickable(false);

        rbCederaOtakYa.setClickable(false);rbCederaOtakTidak.setClickable(false);rbSaatIniBaik.setClickable(false);rbSaatIniTerganggu.setClickable(false);
        rbLampauBaik.setClickable(false);rbLampauTerganggu.setClickable(false);rbFacialDropYa.setClickable(false);rbFacialDropTidak.setClickable(false);
        rbRefleksTendonNormal.setClickable(false);rbRefleksTendonTerganggu.setClickable(false);rbParalisisYa.setClickable(false);rbParalisisTidak.setClickable(false);
        rbTerpajanYa.setClickable(false);rbTerpajanTidak.setClickable(false);rbSimetris.setClickable(false);rbTidakSimetris.setClickable(false);
        rbKedalamanNormal.setClickable(false);rbKedalamanDangkal.setClickable(false);rbKedalamanDalam.setClickable(false);rbFremitusNormal.setClickable(false);
        rbFremitusMenurun.setClickable(false);rbFremitusMeningkat.setClickable(false);

        etLokasiSakitKepala.setKeyListener(null);etFrekuensiSakitKepala.setKeyListener(null);etLokasiNeurosensori.setKeyListener(null);
        etStroke.setKeyListener(null);etTipeKejang.setKeyListener(null);etAuraKejang.setKeyListener(null);etFrekuensiKejang.setKeyListener(null);etCaraMengontrolKejang.setKeyListener(null);etStatusPostikalKejang.setKeyListener(null);etPemeriksaanMata.setKeyListener(null);etPemeriksaanTelinga.setKeyListener(null);etAfek.setKeyListener(null);etUkuran.setKeyListener(null);etReflekCahaya.setKeyListener(null);etLokasiParalisis.setKeyListener(null);
        etLokasiNyeri.setKeyListener(null);etIntensitasNyeri.setKeyListener(null);etFrekuensiNyeri.setKeyListener(null);etKualitasNyeri.setKeyListener(null);
        etPenjalaranNyeri.setKeyListener(null);etDurasiNyeri.setKeyListener(null);etFaktorNyeri.setKeyListener(null);etCaraNyeri.setKeyListener(null);
        etResponsEmosional.setKeyListener(null);etJumlahMerokok.setKeyListener(null);etLamaMerokok.setKeyListener(null);etFrekuensiNapas.setKeyListener(null);
        etKarakteristikSputum.setKeyListener(null);

        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPengkajianAwal3.php?id_pasien="+id;
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
                                if(obj.getInt("Riwayat_Cedera_Otak") == 1){
                                    rbCederaOtakYa.setChecked(true);
                                }

                                if(obj.getInt("Riwayat_Cedera_Otak")== 0){
                                    rbCederaOtakTidak.setChecked(true);
                                }

                                if(obj.getInt("Memori_Saat_Ini") == 1){
                                    rbSaatIniBaik.setChecked(true);
                                }

                                if(obj.getInt("Memori_Saat_Ini")== 0){
                                    rbSaatIniTerganggu.setChecked(true);
                                }

                                if(obj.getInt("Memori_Masa_Lalu") == 1){
                                    rbLampauBaik.setChecked(true);
                                }

                                if(obj.getInt("Memori_Masa_Lalu")== 0){
                                    rbLampauTerganggu.setChecked(true);
                                }

                                if(obj.getInt("Facial_Drop") == 1){
                                    rbFacialDropYa.setChecked(true);
                                }

                                if(obj.getInt("Facial_Drop")== 0){
                                    rbFacialDropTidak.setChecked(true);
                                }

                                if(obj.getInt("Refleks_Tendon_Dalam") == 1){
                                    rbRefleksTendonNormal.setChecked(true);
                                }

                                if(obj.getInt("Refleks_Tendon_Dalam")== 0){
                                    rbRefleksTendonTerganggu.setChecked(true);
                                }

                                if(obj.getInt("Paralisis_Parastesia") == 1){
                                    rbParalisisYa.setChecked(true);
                                }

                                if(obj.getInt("Paralisis_Parastesia")== 0){
                                    rbParalisisTidak.setChecked(true);
                                }

                                if(obj.getInt("Terpajan_Udara_berbahaya") == 1){
                                    rbTerpajanYa.setChecked(true);
                                }

                                if(obj.getInt("Terpajan_Udara_berbahaya")== 0){
                                    rbTerpajanTidak.setChecked(true);
                                }

                                if(obj.getString("Kedalaman_Napas").equals("Normal")){
                                    rbKedalamanNormal.setChecked(true);
                                }

                                if(obj.getString("Kedalaman_Napas").equals("Dalam")){
                                    rbKedalamanDalam.setChecked(true);
                                }

                                if(obj.getString("Kedalaman_Napas").equals("Dangkal")){
                                    rbKedalamanDangkal.setChecked(true);
                                }

                                if(obj.getInt("Simetrisitas") == 1){
                                    rbSimetris.setChecked(true);
                                }

                                if(obj.getInt("Simetrisitas") == 0){
                                    rbTidakSimetris.setChecked(true);
                                }

                                if(obj.getString("Fremitus").equals("Normal")){
                                    rbFremitusNormal.setChecked(true);
                                }

                                if(obj.getString("Fremitus").equals("Meningkat")){
                                    rbFremitusMeningkat.setChecked(true);
                                }

                                if(obj.getString("Fremitus").equals("Menurun")){
                                    rbFremitusMenurun.setChecked(true);
                                }

                                etLokasiSakitKepala.setText(obj.getString("Lokasi_Sakit_Kepala"));
                                etFrekuensiSakitKepala.setText(obj.getString("Frek_Sakit_Kepala"));

                                etLokasiNeurosensori.setText(obj.getString("Lokasi_Keluhan_Neurosensori"));
                                etStroke.setText(obj.getString("Stroke_Gejala_Sisa"));
                                etTipeKejang.setText(obj.getString("Tipe_Kejang"));
                                etAuraKejang.setText(obj.getString("Aura_Kejang"));
                                etFrekuensiKejang.setText(obj.getString("Frek_Kejang"));
                                etCaraMengontrolKejang.setText(obj.getString("Cara_Kontrol_Kejang"));
                                etStatusPostikalKejang.setText(obj.getString("Status_Postikal"));
                                etPemeriksaanMata.setText(obj.getString("Pemeriksaan_Mata_Terakhir"));
                                etPemeriksaanTelinga.setText(obj.getString("Pemeriksaan_Telinga_Terakhir"));
                                etAfek.setText(obj.getString("Afek_Mental"));
                                etUkuran.setText(obj.getString("Ukuran_Pupil"));
                                etReflekCahaya.setText(obj.getString("Reflek_Cahaya"));
                                etLokasiParalisis.setText(obj.getString("Lokasi_Paralisis"));
                                etLokasiNyeri.setText(obj.getString("Lokasi_Nyeri"));
                                etIntensitasNyeri.setText(obj.getString("Intensitas_Nyeri"));
                                etFrekuensiNyeri.setText(obj.getString("Frek_Nyeri"));
                                etKualitasNyeri.setText(obj.getString("Kualitas_Nyeri"));
                                etPenjalaranNyeri.setText(obj.getString("Penjalaran_Nyeri"));
                                etDurasiNyeri.setText(obj.getString("Durasi_Nyeri"));
                                etFaktorNyeri.setText(obj.getString("Faktor_Pencetus_Nyeri"));
                                etCaraNyeri.setText(obj.getString("Cara_Memghilangkan_Nyeri"));
                                etResponsEmosional.setText(obj.getString("Respons_Emosional"));
                                etJumlahMerokok.setText(obj.getString("Jumlah_Rokok_Harian"));
                                etLamaMerokok.setText(obj.getString("Lama_Merokok"));
                                etFrekuensiNapas.setText(obj.getString("Frek_Napas"));
                                etKarakteristikSputum.setText(obj.getString("Karakteristik_Sputum"));

                                if(obj.getInt("Kesemutan") == 1){
                                    cbKesemutan.setChecked(true);
                                }

                                if(obj.getInt("Kebas") == 1){
                                    cbKebas.setChecked(true);
                                }

                                if(obj.getInt("Kelemahan") == 1){
                                    cbKelemahan.setChecked(true);
                                }

                                if(obj.getInt("Kehilangan_Penglihatan") == 1){
                                    cbKehilanganPenghilatan.setChecked(true);
                                }

                                if(obj.getInt("Glaukoma") == 1){
                                    cbGlaukoma.setChecked(true);
                                }

                                if(obj.getInt("Katarak") == 1){
                                    cbKatarak.setChecked(true);
                                }

                                if(obj.getInt("Kehilangan_Pendengaran") == 1){
                                    cbKehilanganPendengaran.setChecked(true);
                                }

                                if(obj.getInt("Perubahan_Penciuman") == 1){
                                    cbPerubahanPenciuman.setChecked(true);
                                }

                                if(obj.getInt("Perubahan_Pengecap") == 1){
                                    cbPerubahanPengecap.setChecked(true);
                                }

                                if(obj.getInt("Epistasis") == 1){
                                    cbEpistasis.setChecked(true);
                                }

                                if(obj.getInt("Mental_Terorientasi") == 1){
                                    cbTerorientasi.setChecked(true);
                                }

                                if(obj.getInt("Mental_Disorentasi") == 1){
                                    cbDisorientasi.setChecked(true);
                                }

                                if(obj.getInt("Disorientasi_Orang") == 1){
                                    cbOrang.setChecked(true);
                                }

                                if(obj.getInt("Disorientasi_Waktu") == 1){
                                    cbWaktu.setChecked(true);
                                }

                                if(obj.getInt("Disorientasi_Tempat") == 1){
                                    cbTempat.setChecked(true);
                                }

                                if(obj.getInt("Mental_Kooperatif") == 1){
                                    cbKooperatif.setChecked(true);
                                }

                                if(obj.getInt("Mental_Delusi") == 1){
                                    cbDelusi.setChecked(true);
                                }

                                if(obj.getInt("Mental_Menyerang") == 1){
                                    cbMenyerang.setChecked(true);
                                }

                                if(obj.getInt("Mental_Halusinasi") == 1){
                                    cbHalusinasi.setChecked(true);
                                }

                                if(obj.getInt("Mengatuk") == 1){
                                    cbMengantuk.setChecked(true);
                                }

                                if(obj.getInt("Letargi") == 1){
                                    cbLetargi.setChecked(true);
                                }

                                if(obj.getInt("Stupor") == 1){
                                    cbStupor.setChecked(true);
                                }

                                if(obj.getInt("Koma") == 1){
                                    cbKoma.setChecked(true);
                                }

                                if(obj.getInt("Kacamata") == 1){
                                    cbKacamata.setChecked(true);
                                }

                                if(obj.getInt("Lensa_Kontak") == 1){
                                    cbLensaKontak.setChecked(true);
                                }

                                if(obj.getInt("Alat_Bantu_Dengar") == 1){
                                    cbAlatBantuDengar.setChecked(true);
                                }

                                if(obj.getInt("Mengkerutkan_Muka") == 1){
                                    cbMengkerutkanMuka.setChecked(true);
                                }

                                if(obj.getInt("Menjaga_Area_Sakit") == 1){
                                    cbMenjagaArea.setChecked(true);
                                }

                                if(obj.getInt("Penyempitan_Fokus") == 1){
                                    cbPenyempitanFokus.setChecked(true);
                                }

                                if(obj.getInt("Dispnea") == 1){
                                    cbDispnea.setChecked(true);
                                }

                                if(obj.getInt("Batuk") == 1){
                                    cbBatuk.setChecked(true);
                                }

                                if(obj.getInt("Bronkhitis") == 1){
                                    cbBronkhitis.setChecked(true);
                                }

                                if(obj.getInt("Asma") == 1){
                                    cbAsma.setChecked(true);
                                }

                                if(obj.getInt("Tuberkulosis") == 1){
                                    cbTuberkolosis.setChecked(true);
                                }

                                if(obj.getInt("Pneumonia") == 1){
                                    cbPneumonia.setChecked(true);
                                }

                                if(obj.getInt("Emfisema") == 1){
                                    cbEmfisema.setChecked(true);
                                }

                                if(obj.getInt("Penggunaan_Otot_Asesorius") == 1){
                                    cbPenggunaanOtot.setChecked(true);
                                }

                                if(obj.getInt("Napas_Cuping_Hidung") == 1){
                                    cbNapasCuping.setChecked(true);
                                }

                                if(obj.getInt("Versikuler") == 1){
                                    cbVersikuler.setChecked(true);
                                }

                                if(obj.getInt("Ronkhi") == 1){
                                    cbRonkhi.setChecked(true);
                                }

                                if(obj.getInt("Wheezing") == 1){
                                    cbWheezing.setChecked(true);
                                }

                                if(obj.getInt("Sputum") == 1){
                                    cbSputum.setChecked(true);
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




    public void insertPengkajianAwal(int Riwayat_Cedera_Otak,String Lokasi_Sakit_Kepala,String Frek_Sakit_Kepala,
                                     int Kesemutan,int Kebas,int Kelemahan,String Lokasi_Keluhan_Neurosensori,
                                     String Stroke_Gejala_Sisa,String Tipe_Kejang,String Aura_Kejang,
                                     String Frek_Kejang, String Cara_Kontrol_Kejang,String Status_Postikal,int Kehilangan_Penglihatan,
                                     int Glaukoma,int Katarak,String Pemeriksaan_Mata_Terakhir,int Kehilangan_Pendengaran,String Pemeriksaan_Telinga_Terakhir,
                                     int Perubahan_Penciuman,int Perubahan_Pengecap,int Epistasis,int Mental_Terorientasi,int Mental_Disorentasi,int Disorientasi_Orang,
                                     int Disorientasi_Waktu,int Disorientasi_Tempat,int Mental_Kooperatif,int Mental_Delusi,int Mental_Menyerang,int Mental_Halusinasi,
                                     String Afek_Mental,int Mengatuk,int Stupor,int Letargi,int Koma,int Memori_Saat_Ini,int Memori_Masa_Lalu,int Kacamata,int Lensa_Kontak,
                                     int Alat_Bantu_Dengar,String Ukuran_Pupil,String Reflek_Cahaya,int Facial_Drop,int Refleks_Tendon_Dalam,int Paralisis_Parastesia,String Lokasi_Paralisis,
                                     String Lokasi_Nyeri,String Intensitas_Nyeri,String Frek_Nyeri,String Kualitas_Nyeri,String Penjalaran_Nyeri,String Durasi_Nyeri,String Faktor_Pencetus_Nyeri,
                                     String Cara_Memghilangkan_Nyeri,int Mengkerutkan_Muka,int Menjaga_Area_Sakit,int Penyempitan_Fokus,String Respons_Emosional,int Dispnea,int Batuk,int Bronkhitis,
                                     int Asma,int Tuberkulosis,int Pneumonia,int Emfisema,int Terpajan_Udara_berbahaya,String Jumlah_Rokok_Harian,String Lama_Merokok,String Frek_Napas,String Kedalaman_Napas,
                                     int Simetrisitas,int Penggunaan_Otot_Asesorius,int Napas_Cuping_Hidung,String Fremitus,int Versikuler,int Ronkhi,int Wheezing,int Sputum,String Karakteristik_Sputum,int id_pasien){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("Riwayat_Cedera_Otak",Riwayat_Cedera_Otak);
            objDetail.put("Lokasi_Sakit_Kepala",Lokasi_Sakit_Kepala);
            objDetail.put("Frek_Sakit_Kepala",Frek_Sakit_Kepala);
            objDetail.put("Kesemutan",Kesemutan);
            objDetail.put("Kebas",Kebas);
            objDetail.put("Kelemahan",Kelemahan);
            objDetail.put("Lokasi_Keluhan_Neurosensori",Lokasi_Keluhan_Neurosensori);
            objDetail.put("Stroke_Gejala_Sisa",Stroke_Gejala_Sisa);
            objDetail.put("Tipe_Kejang",Tipe_Kejang);
            objDetail.put("Aura_Kejang",Aura_Kejang);
            objDetail.put("Frek_Kejang",Frek_Kejang);
            objDetail.put("Cara_Kontrol_Kejang",Cara_Kontrol_Kejang);
            objDetail.put("Status_Postikal",Status_Postikal);
            objDetail.put("Kehilangan_Penglihatan",Kehilangan_Penglihatan);
            objDetail.put("Glaukoma",Glaukoma);
            objDetail.put("Katarak",Katarak);
            objDetail.put("Pemeriksaan_Mata_Terakhir",Pemeriksaan_Mata_Terakhir);
            objDetail.put("Kehilangan_Pendengaran",Kehilangan_Pendengaran);
            objDetail.put("Pemeriksaan_Telinga_Terakhir",Pemeriksaan_Telinga_Terakhir);
            objDetail.put("Perubahan_Penciuman",Perubahan_Penciuman);
            objDetail.put("Perubahan_Pengecap",Perubahan_Pengecap);
            objDetail.put("Epistasis",Epistasis);
            objDetail.put("Mental_Terorientasi",Mental_Terorientasi);
            objDetail.put("Mental_Disorentasi",Mental_Disorentasi);
            objDetail.put("Disorientasi_Orang",Disorientasi_Orang);
            objDetail.put("Disorientasi_Waktu",Disorientasi_Waktu);
            objDetail.put("Disorientasi_Tempat",Disorientasi_Tempat);
            objDetail.put("Mental_Kooperatif",Mental_Kooperatif);
            objDetail.put("Mental_Delusi",Mental_Delusi);
            objDetail.put("Mental_Menyerang",Mental_Menyerang);
            objDetail.put("Mental_Halusinasi",Mental_Halusinasi);
            objDetail.put("Afek_Mental",Afek_Mental);
            objDetail.put("Mengatuk",Mengatuk);
            objDetail.put("Stupor",Stupor);
            objDetail.put("Letargi",Letargi);
            objDetail.put("Koma",Koma);
            objDetail.put("Memori_Saat_Ini",Memori_Saat_Ini);
            objDetail.put("Memori_Masa_Lalu",Memori_Masa_Lalu);
            objDetail.put("Kacamata",Kacamata);
            objDetail.put("Lensa_Kontak",Lensa_Kontak);
            objDetail.put("Alat_Bantu_Dengar",Alat_Bantu_Dengar);
            objDetail.put("Ukuran_Pupil",Ukuran_Pupil);
            objDetail.put("Reflek_Cahaya",Reflek_Cahaya);
            objDetail.put("Facial_Drop",Facial_Drop);
            objDetail.put("Refleks_Tendon_Dalam",Refleks_Tendon_Dalam);
            objDetail.put("Paralisis_Parastesia",Paralisis_Parastesia);
            objDetail.put("Lokasi_Paralisis",Lokasi_Paralisis);
            objDetail.put("Lokasi_Nyeri",Lokasi_Nyeri);
            objDetail.put("Intensitas_Nyeri",Intensitas_Nyeri);
            objDetail.put("Frek_Nyeri",Frek_Nyeri);
            objDetail.put("Kualitas_Nyeri",Kualitas_Nyeri);
            objDetail.put("Penjalaran_Nyeri",Penjalaran_Nyeri);
            objDetail.put("Durasi_Nyeri",Durasi_Nyeri);
            objDetail.put("Faktor_Pencetus_Nyeri",Faktor_Pencetus_Nyeri);
            objDetail.put("Cara_Memghilangkan_Nyeri",Cara_Memghilangkan_Nyeri);
            objDetail.put("Mengkerutkan_Muka",Mengkerutkan_Muka);
            objDetail.put("Menjaga_Area_Sakit",Menjaga_Area_Sakit);
            objDetail.put("Penyempitan_Fokus",Penyempitan_Fokus);
            objDetail.put("Respons_Emosional",Respons_Emosional);
            objDetail.put("Dispnea",Dispnea);
            objDetail.put("Batuk",Batuk);
            objDetail.put("Bronkhitis",Bronkhitis);
            objDetail.put("Asma",Asma);
            objDetail.put("Tuberkulosis",Tuberkulosis);
            objDetail.put("Pneumonia",Pneumonia);
            objDetail.put("Emfisema",Emfisema);
            objDetail.put("Terpajan_Udara_berbahaya",Terpajan_Udara_berbahaya);
            objDetail.put("Jumlah_Rokok_Harian",Jumlah_Rokok_Harian);
            objDetail.put("Lama_Merokok",Lama_Merokok);
            objDetail.put("Frek_Napas",Frek_Napas);
            objDetail.put("Kedalaman_Napas",Kedalaman_Napas);
            objDetail.put("Simetrisitas",Simetrisitas);
            objDetail.put("Penggunaan_Otot_Asesorius",Penggunaan_Otot_Asesorius);
            objDetail.put("Napas_Cuping_Hidung",Napas_Cuping_Hidung);
            objDetail.put("Fremitus",Fremitus);
            objDetail.put("Versikuler",Versikuler);
            objDetail.put("Ronkhi",Ronkhi);
            objDetail.put("Wheezing",Wheezing);
            objDetail.put("Sputum",Sputum);
            objDetail.put("Karakteristik_Sputum",Karakteristik_Sputum);
            objDetail.put("id_pasien",id_pasien);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, INSERT_PENGKAJIAN_AWAL_3_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Intent i = new Intent(PengkajianAwalActivity3.this,PengkajianAwalActivity4.class);
                                i.putExtra("panduan","insert");
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(PengkajianAwalActivity3.this, "Gagal", Toast.LENGTH_SHORT).show();
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
        RequestQueue requestQueue = Volley.newRequestQueue(PengkajianAwalActivity3.this);
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
