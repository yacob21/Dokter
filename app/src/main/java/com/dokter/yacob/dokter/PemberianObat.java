package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.MenuItem;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PemberianObat extends AppCompatActivity {

    List<Resep> result = new ArrayList<>();
    DetailResepAdapter dra;
    RecyclerView cardListDetailResep;
    RequestQueue queue;
    Context context;

    String namaobat,dosis,sehari,sekali,totalhari,instruksi,tambahan;
    AlertDialog dialog;
    AlertDialog.Builder builder;

    SessionIdPasien sessionIdPasien;
    SessionLogin sessionLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pemberian_obat);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pemberian Obat</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        context = this;

        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        sessionLogin = new SessionLogin(getApplicationContext());
        builder = new AlertDialog.Builder(this);
        queue = Volley.newRequestQueue(this);
        cardListDetailResep = findViewById(R.id. cardListDetailResep);
        cardListDetailResep.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        cardListDetailResep.setLayoutManager(llm);
        final Intent i = getIntent();
        getDetailResep(i.getStringExtra("panduanIdResep"));



    }

    public void getDetailResep(String id){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDetailResep.php?id_resep="+id;
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
                        for (int i = 0 ;i< users.length();i++) {
                            try {
                                JSONObject obj = users.getJSONObject(i);
                                namaobat = obj.getString("Nama_Obat");
                                dosis = obj.getString("Strength");
                                sehari = obj.getString("Sehari");
                                sekali = obj.getString("sekali");
                                totalhari = obj.getString("Total_Hari");
                                instruksi = obj.getString("Cara_Pemakaian");
                                tambahan = obj.getString("Instruksi_Tambahan");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            result.add(new Resep(namaobat,dosis,sehari,sekali,totalhari,instruksi,tambahan));
                            dra = new DetailResepAdapter(context,result);
                            cardListDetailResep.setAdapter(dra);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });

        queue.add(req);
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
