package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView cardListPasien;
    RequestQueue queue;

    List<Pasien> resultINAP = new ArrayList<>();
    List<Pasien> resultICU = new ArrayList<>();
    List<Pasien> result = new ArrayList<>();
    ListPasienAdapter lpa;
    SessionLogin sessionLogin;
    Context context;

    ImageView ivTotal,ivInap,ivIcu;
    TextView tvTotal,tvInap,tvIcu,tvJudul;

    String id_pasien,nama_pasien,tanggal_masuk,jenis_kamar,nomor_kamar;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    public String UPDATE_TOKEN_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updateTokenDokter.php";
    public String UPDATE_NULL_TOKEN_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updateNullTokenDokter.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        builder = new AlertDialog.Builder(this);
        sessionLogin = new SessionLogin(getApplicationContext());
        sessionLogin.checkLogin();
        queue = Volley.newRequestQueue(this);
        context = this;
        cardListPasien = (RecyclerView) findViewById(R.id.cardListPasien);
        cardListPasien.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardListPasien.setLayoutManager(llm);

        ivIcu = (ImageView) findViewById(R.id.ivIcu);
        ivInap = (ImageView) findViewById(R.id.ivInap);
        ivTotal = (ImageView) findViewById(R.id.ivTotal);
        tvIcu = (TextView) findViewById(R.id.tvIcu);
        tvInap  = (TextView) findViewById(R.id.tvInap);
        tvTotal =(TextView)  findViewById(R.id.tvTotal);
        tvJudul =(TextView) findViewById(R.id.tvJudul);

        HashMap<String, String> user = sessionLogin.getUserDetails();
//        String urlDokter = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDokterByNip.php?nip="+user.get(sessionLogin.KEY_NIP);
//        JsonObjectRequest reqDokter = new JsonObjectRequest(urlDokter, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        JSONArray users = null;
//                        try {
//                            users = response.getJSONArray("result");
//                            JSONObject obj = users.getJSONObject(0);
//                            getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>"+obj.getString("Nama_Dokter")+"</font>"));
//                            getSupportActionBar().setSubtitle(Html.fromHtml("<'font color='#ffffff'>"+obj.getString("Spesialisasi")+"</font>"));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
//            }
//        });
//
//        queue.add(reqDokter);


        String urlJumlah = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectJumlahPasien.php?nip="+user.get(sessionLogin.KEY_NIP);
        JsonObjectRequest reqJumlah = new JsonObjectRequest(urlJumlah, null,
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
                                tvInap.setText(obj.getString("Jumlah_Inap"));
                                tvIcu.setText(obj.getString("Jumlah_Icu"));
                                tvTotal.setText(obj.getString("Jumlah_Total"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
            }
        });

        queue.add(reqJumlah);


        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectPasienDokter.php?nip="+user.get(sessionLogin.KEY_NIP);
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
                                id_pasien = obj.getString("Id_Pasien");
                                nama_pasien = obj.getString("Nama_Pasien");
                                tanggal_masuk = obj.getString("Tanggal_Masuk");
                                jenis_kamar = obj.getString("Jenis_Kamar");
                                nomor_kamar = obj.getString("Nomor_Kamar");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                                result.add(new Pasien(id_pasien,nama_pasien,tanggal_masuk,jenis_kamar,nomor_kamar));
                                lpa = new ListPasienAdapter(context,result);
                                cardListPasien.setAdapter(lpa);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
            }
        });

        queue.add(req);


        ivTotal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ivTotal.setImageResource(R.drawable.total_b);
                ivIcu.setImageResource(R.drawable.icu_a);
                ivInap.setImageResource(R.drawable.inap_a);
                tvJudul.setText("PASIEN");
                lpa = new ListPasienAdapter(context,result);
                cardListPasien.setAdapter(lpa);
            }
        });

        ivInap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultINAP.clear();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getJenis_Kamar().toLowerCase().trim().equals("inap")) {
                        resultINAP.add(result.get(i));
                    }
                }
                ivTotal.setImageResource(R.drawable.total_a);
                ivIcu.setImageResource(R.drawable.icu_a);
                ivInap.setImageResource(R.drawable.inap_b);
                tvJudul.setText("INAP");
                lpa = new ListPasienAdapter(context, resultINAP);
                cardListPasien.setAdapter(lpa);
                cardListPasien.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }
        });

        ivIcu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resultICU.clear();
                for (int i = 0; i < result.size(); i++) {
                    if (result.get(i).getJenis_Kamar().toLowerCase().trim().equals("icu")) {
                            resultICU.add(result.get(i));
                    }
                }
                ivTotal.setImageResource(R.drawable.total_a);
                ivIcu.setImageResource(R.drawable.icu_b);
                ivInap.setImageResource(R.drawable.inap_a);
                tvJudul.setText("ICU");
                lpa = new ListPasienAdapter(context, resultICU);
                cardListPasien.setAdapter(lpa);
                cardListPasien.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
            }
        });


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.btnProfil:
               startActivity(new Intent(MainActivity.this,ProfilActivity.class));
                return true;
            case R.id.btnKeluar:
                updateNullToken(sessionLogin.getUserDetails().get(sessionLogin.KEY_NIP));
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        builder.setMessage("Ingin keluar dari aplikasi ?");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                startActivity(intent);
                return;
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialog = builder.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateToken(sessionLogin.getUserDetails().get(SessionLogin.KEY_NIP), FirebaseInstanceId.getInstance().getToken());
        //Toast.makeText(context, FirebaseInstanceId.getInstance().getToken(), Toast.LENGTH_SHORT).show();
    }
    public void updateToken(String nik,String token){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("nik",nik);
            objDetail.put("token",token);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_TOKEN_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {

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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }

    public void updateNullToken(String nik){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("nik",nik);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_NULL_TOKEN_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                sessionLogin.logoutUser();
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
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
        requestQueue.add(stringRequest);
    }
}
