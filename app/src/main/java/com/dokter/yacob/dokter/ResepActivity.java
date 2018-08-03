package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ResepActivity extends AppCompatActivity {
    TextView tvKosong;
    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    public static final String INSERT_RESEP_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertResep.php";


    RecyclerView cardListResep;
    List<Resep> result = new ArrayList<>();
    listResepAdapter lra;
    Context context;
    String tanggal,jumlah,id_resep;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resep);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        context = this;
        queue = Volley.newRequestQueue(this);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        final HashMap<String, String> idpasien = sessionIdPasien.getIdPasienDetails();
        tvKosong  = findViewById(R.id.tvKosong);

        cardListResep = (RecyclerView) findViewById(R.id.cardListResep);
        cardListResep.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardListResep.setLayoutManager(llm);

        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectResepById.php?id_pasien="+idpasien.get(sessionIdPasien.KEY_ID_PASIEN);
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
                            tvKosong.setVisibility(View.VISIBLE);
                        }
                        else {
                            cardListResep.setVisibility(View.VISIBLE);
                            for (int i = 0; i < users.length(); i++) {
                                try {
                                    JSONObject obj = users.getJSONObject(i);
                                    jumlah = obj.getString("Jumlah");
                                    tanggal = obj.getString("Tanggal_Dibuat");
                                    id_resep = obj.getString("Id_Resep");
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                result.add(new Resep(tanggal, jumlah, id_resep));
                                lra = new listResepAdapter(context, result);
                                cardListResep.setAdapter(lra);
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

        final FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fab.setEnabled(false);
                JSONObject objAdd2 = new JSONObject();
                try {
                    JSONArray arrData = new JSONArray();
                    JSONObject objDetail = new JSONObject();
                    objDetail.put("id_pasien",idpasien.get(sessionIdPasien.KEY_ID_PASIEN));
                    arrData.put(objDetail);
                    objAdd2.put("data",arrData);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.POST, INSERT_RESEP_URL, objAdd2,
                        new Response.Listener<JSONObject>() {
                            @Override

                            public void onResponse(JSONObject response) {
                                try {
                                    if(response.getString("status").equals("OK")) {
                                        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectIdResep.php?id_pasien="+idpasien.get(sessionIdPasien.KEY_ID_PASIEN);
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
                                                        try {
                                                                JSONObject obj = users.getJSONObject(0);
                                                                Intent z = new Intent(ResepActivity.this,TambahResepActivity.class);
                                                                //z.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                                z.putExtra("panduanIdResep",obj.getString("Id_Resep"));
                                                                startActivity(z);
                                                                fab.setEnabled(true);
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
                                    }
                                    else{
                                        fab.setEnabled(true);
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
                RequestQueue requestQueue2 = Volley.newRequestQueue(ResepActivity.this);
                requestQueue2.add(stringRequest2);
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(ResepActivity.this,DetailPasienActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ResepActivity.this,DetailPasienActivity.class));
    }
}
