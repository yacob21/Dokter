package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class PengkajianAwalActivity5 extends AppCompatActivity {

    Button btnSimpan;
    Context context;
    SessionIdPasien sessionIdPasien;
    RequestQueue queue;
    TextView textView11;
    ImageView iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pengkajian_awal_activiy5);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Pengkajian Awal</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
          btnSimpan = findViewById(R.id.btnSimpan);

        textView11 = findViewById(R.id.textView11);
        iv= findViewById(R.id.iv);
        context = this;
        queue = Volley.newRequestQueue(this);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());

        btnSimpan.setText("Kembali");
        getPengkajianAwal(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
        textView11.setText("Gambar luka Bakar pasien:");

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(PengkajianAwalActivity5.this,DetailPasienActivity.class);
                    startActivity(i);
            }
        });
    }


    public  void getPengkajianAwal(String id){
        iv.setVisibility(View.VISIBLE);
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
                                Picasso.with(context)
                                        .load(obj.getString("Gambar_Luka_Bakar"))
                                        .config(Bitmap.Config.RGB_565)
                                        .placeholder(R.drawable.badan)
                                        .error(R.drawable.badan)
                                        .fit()
                                        .centerInside()
                                        .into(iv);
                                //Toast.makeText(context, obj.getString("Gambar_Luka_Bakar"), Toast.LENGTH_SHORT).show();
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
