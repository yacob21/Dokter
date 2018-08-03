package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.getbase.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailPasienActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        RequestQueue queue;

        SessionIdPasien sessionIdPasien;
        AlertDialog dialog;
        AlertDialog.Builder builder;
        String DELETE_LIST_DOKTER_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/deleteListDokter.php";
        String UPDATE_PEMULANGAN_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updatePemulanganPasien.php";

    FloatingActionButton btnPesan;

    RecyclerView cardListTimeline;
    List<Timeline> result = new ArrayList<>();
    listTimelineAdapter lta;
    Context context;
    String tanggal,nik,nama,aktivitas,nikdokter,namadokter,date,gambar,id_resep;

    public static String temp_nama;
    sessionToken sessionToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pasien);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        queue = Volley.newRequestQueue(this);
        context=this;
        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        sessionToken = new sessionToken(getApplicationContext());
        HashMap<String, String> idpasien = sessionIdPasien.getIdPasienDetails();
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Detail Pasien</font>"));
        builder = new AlertDialog.Builder(this);
        //////////////////////////////////////////////////////////////////////////////////////////////////
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeaderView = navigationView.getHeaderView(0);
        final TextView tvNama= (TextView) navHeaderView.findViewById(R.id.tvNama);
        final TextView tvJenisKelamin= (TextView) navHeaderView.findViewById(R.id.tvJenisKelamin);
        final TextView tvKamar= (TextView) navHeaderView.findViewById(R.id.tvKamar);
        final TextView tvTanggalMasuk= (TextView) navHeaderView.findViewById(R.id.tvTanggalMasuk);
        final TextView tvTanggalLahir= (TextView) navHeaderView.findViewById(R.id.tvTanggalLahir);
        final TextView tvGolonganDarah= (TextView) navHeaderView.findViewById(R.id.tvGolonganDarah);
        final TextView tvTinggi= (TextView) navHeaderView.findViewById(R.id.tvTinggi);
        final TextView tvBerat= (TextView) navHeaderView.findViewById(R.id.tvBerat);
        final TextView tvAlergi= (TextView) navHeaderView.findViewById(R.id.tvAlergi);
        final TextView tvDiagnosa= (TextView) navHeaderView.findViewById(R.id.tvDiagnosa);
        final TextView tvDiagnosa2= (TextView) navHeaderView.findViewById(R.id.tvDiagnosa2);


        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPasienById.php?id_pasien="+idpasien.get(sessionIdPasien.KEY_ID_PASIEN);
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
                        JSONObject obj = null;
                        try {
                            obj = users.getJSONObject(0);
                            tvNama.setText(obj.getString("Nama_Pasien"));
                            temp_nama = obj.getString("Nama_Pasien");
                            tvJenisKelamin.setText(obj.getString("Jenis_Kelamin"));
                            tvKamar.setText(obj.getString("Nomor_Kamar"));
                            tvTanggalMasuk.setText(obj.getString("Tanggal_Masuk"));
                            tvTanggalLahir.setText(obj.getString("Tanggal_Lahir"));
                            tvGolonganDarah.setText(obj.getString("Golongan_Darah"));
                            tvTinggi.setText(obj.getString("Tinggi")+" cm");
                            tvBerat.setText(obj.getString("Berat")+" Kg");
                            tvAlergi.setText(obj.getString("Alergi"));
                            tvDiagnosa.setText(obj.getString("Diagnosa_1"));
                            tvDiagnosa2.setText(obj.getString("Diagnosa_2"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
            }
        });

        queue.add(req);

        cardListTimeline = findViewById(R.id.cardListTimeline);
        cardListTimeline.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardListTimeline.setLayoutManager(llm);

        getTimeline(idpasien.get(SessionIdPasien.KEY_ID_PASIEN));

        btnPesan = findViewById(R.id.btnPesan);
        btnPesan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailPasienActivity.this,PesanActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            startActivity(new Intent(DetailPasienActivity.this,MainActivity.class));
        }
    }

    public void getTimeline(String id){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectAktivitasPasien.php?id_pasien="+id;
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
                        if(users.length() == 0){
                            cardListTimeline.setVisibility(View.GONE);
                        }
                        else {
                            cardListTimeline.setVisibility(View.VISIBLE);
                            for (int i = 0; i < users.length(); i++) {
                                try {
                                    JSONObject obj = users.getJSONObject(i);
                                    tanggal = obj.getString("Tanggal");
                                    nama = obj.getString("Nama_Perawat");
                                    nik = obj.getString("NIK_Perawat");
                                    aktivitas = obj.getString("Aktivitas");
                                    nikdokter = obj.getString("NIK_Dokter");
                                    namadokter = obj.getString("Nama_Dokter");
                                    date = obj.getJSONObject("Date").getString("date");
                                    gambar = obj.getString("Gambar");
                                    id_resep = obj.getString("Id_Resep");

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                result.add(new Timeline(tanggal, aktivitas, nik, nama,nikdokter,namadokter,date,gambar,id_resep));
                                lta = new listTimelineAdapter(DetailPasienActivity.this, result);
                                cardListTimeline.setAdapter(lta);
                            }
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
            }
        });
        queue.add(req);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_beranda) {

        } else if (id == R.id.nav_diagnosa) {
            startActivity(new Intent(DetailPasienActivity.this,DiagnosaActivity.class));

        } else if (id == R.id.nav_resep) {
            Intent i = new Intent(DetailPasienActivity.this,ResepActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_discharge) {
            builder.setMessage("Apakah Anda yakin ingin memulangkan pasien ?");
            builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    JSONObject objAdd = new JSONObject();
                    try {
                        JSONArray arrData = new JSONArray();
                        JSONObject objDetail = new JSONObject();
                        objDetail.put("id_pasien",sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
                        arrData.put(objDetail);
                        objAdd.put("data",arrData);
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, DELETE_LIST_DOKTER_URL, objAdd,
                            new Response.Listener<JSONObject>() {
                                @Override

                                public void onResponse(JSONObject response) {
                                    try {
                                        if(response.getString("status").equals("OK")) {
                                            JSONObject objAdd = new JSONObject();
                                            try {
                                                JSONArray arrData = new JSONArray();
                                                JSONObject objDetail = new JSONObject();
                                                objDetail.put("id_pasien",sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
                                                arrData.put(objDetail);
                                                objAdd.put("data",arrData);
                                            } catch (JSONException e1) {
                                                e1.printStackTrace();
                                            }
                                            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_PEMULANGAN_URL, objAdd,
                                                    new Response.Listener<JSONObject>() {
                                                        @Override

                                                        public void onResponse(JSONObject response) {
                                                            try {
                                                                if(response.getString("status").equals("OK")) {

                                                                    String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectTokenPerawat.php?nip="+sessionToken.getToken().get(sessionToken.KEY_TOKEN);
                                                                    JsonObjectRequest req = new JsonObjectRequest(url, null,
                                                                            new Response.Listener<JSONObject>() {
                                                                                @Override
                                                                                public void onResponse(JSONObject response) {
                                                                                    JSONArray users = null;
                                                                                    try {
                                                                                        users = response.getJSONArray("result");
                                                                                        JSONObject obj = users.getJSONObject(0);
                                                                                        String urlJumlah = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/notifikasi/notifPesanPerawat.php?title=Pemulangan pasien&pesan=Pasien:"+temp_nama+" sudah dapat dipulangkan&token="+obj.getString("Token");
                                                                                        Log.v("urlNotif",urlJumlah);
                                                                                        JsonObjectRequest reqJumlah = new JsonObjectRequest(urlJumlah, null,
                                                                                                new Response.Listener<JSONObject>() {
                                                                                                    @Override
                                                                                                    public void onResponse(JSONObject response) {
                                                                                                        Intent i = new Intent(DetailPasienActivity.this,MainActivity.class);
                                                                                                        startActivity(i);
                                                                                                    }
                                                                                                }, new Response.ErrorListener() {
                                                                                            @Override
                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                            }
                                                                                        });
                                                                                        queue.add(reqJumlah);
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }
                                                                                }
                                                                            }, new Response.ErrorListener() {
                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                        }
                                                                    });
                                                                    queue.add(req);

//
                                                                }
                                                                else{
                                                                }
                                                            } catch (JSONException e1) {
                                                                e1.printStackTrace();
                                                            }

                                                        }
                                                    },
                                                    new Response.ErrorListener() {
                                                        @Override
                                                        public void onErrorResponse(VolleyError error) {

                                                        }
                                                    }) ;
                                            RequestQueue requestQueue = Volley.newRequestQueue(DetailPasienActivity.this);
                                            requestQueue.add(stringRequest);
                                        }
                                        else{
                                        }
                                    } catch (JSONException e1) {
                                        e1.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {

                                }
                            }) ;
                    RequestQueue requestQueue = Volley.newRequestQueue(DetailPasienActivity.this);
                    requestQueue.add(stringRequest);
                }
            });

            builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });

            dialog = builder.show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }


}
