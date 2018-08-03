package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ActivityTTV extends AppCompatActivity {
    EditText etTekananDarah,etDenyutNadi,etLajuPernapasan,etSuhuTubuh;
    RequestQueue queue;
    Context context;
    SessionIdPasien sessionIdPasien;
    SessionLogin sessionLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttv);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Tanda-tanda Vital</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        queue =  Volley.newRequestQueue(this);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        sessionLogin = new SessionLogin(getApplicationContext());
        context = this;
        etTekananDarah = findViewById(R.id.etTekananDarah);
        etDenyutNadi = findViewById(R.id.etDenyutNadi);
        etLajuPernapasan = findViewById(R.id.etLajuPernapasan);
        etSuhuTubuh = findViewById(R.id.etSuhuTubuh);

        Intent i = getIntent();
        etSuhuTubuh.setKeyListener(null);
        etTekananDarah.setKeyListener(null);
        etDenyutNadi.setKeyListener(null);
        etLajuPernapasan.setKeyListener(null);
        getTtv(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN),i.getStringExtra("aktivitas"),i.getStringExtra("tanggal"));

    }

    public void getTtv(String id,String aktivitas,String tanggal){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDetailAktivitas.php?id_pasien="+id+"&aktivitas="+aktivitas
                +"&tanggal="+tanggal;
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
                                etDenyutNadi.setText(obj.getString("Denyut_Nadi"));
                                etLajuPernapasan.setText(obj.getString("Laju_Pernapasan"));
                                etSuhuTubuh.setText(obj.getString("Suhu")+"\u00b0C");
                                etTekananDarah.setText(obj.getString("Tekanan_Darah")+" MmHg");
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
