package com.dokter.yacob.dokter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
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

import java.util.ArrayList;

public class DiagnosaActivity extends AppCompatActivity {
    LinearLayout linear1,linear2;
    EditText etDiagnosa1,etDiagnosa2;
    Button btnSimpan;
    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    ListView lv1,lv2;

    String UPDATE_DIAGNOSA_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updateDiagnosa.php";
    ArrayList<String> listICD = new ArrayList<>();
    Context context;
    ArrayAdapter<String> Adapter;
    ArrayAdapter<String> Adapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosa);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Diagnosis</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        linear1 = findViewById(R.id.linear1);
        linear2 = findViewById(R.id.linear2);
        etDiagnosa1 = findViewById(R.id.etDiagnosa1);
        etDiagnosa2 = findViewById(R.id.etDiagnosa2);
        lv1 = findViewById(R.id.lv1);
        lv2 = findViewById(R.id.lv2);
        btnSimpan = findViewById(R.id.btnSimpan);
        queue = Volley.newRequestQueue(this);
        context = this;
        sessionIdPasien = new SessionIdPasien(getApplicationContext());

        getDiagnosa(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
        getICD();
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateDiagnosa(etDiagnosa1.getText().toString(),etDiagnosa2.getText().toString(),sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
            }
        });

        etDiagnosa1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv1.setVisibility(View.VISIBLE);
                lv2.setVisibility(View.GONE);

            }
        });
        etDiagnosa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lv2.setVisibility(View.VISIBLE);
                lv1.setVisibility(View.GONE);

            }
        });


    }


    public void getDiagnosa(String id){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDiagnosaPasien.php?id_pasien="+id;
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
                                etDiagnosa1.setText(obj.getString("Diagnosa_1"));
                                etDiagnosa2.setText(obj.getString("Diagnosa_2"));
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

    public void getICD(){
        String url = "https://firebasestorage.googleapis.com/v0/b/admin-eca06.appspot.com/o/penyakit.json?alt=media&token=9002af8d-cf22-4abe-acf4-cb69f2bf88d5";
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
                                listICD.add(obj.getString("Diagnosa"));
                                Adapter = new ArrayAdapter<String>(context, R.layout.list_item,R.id.tvDiagnosa,listICD );
                                Adapter2 = new ArrayAdapter<String>(context, R.layout.list_item,R.id.tvDiagnosa,listICD );

                                lv1.setAdapter(Adapter);
                                lv2.setAdapter(Adapter2);

                                etDiagnosa1.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        DiagnosaActivity.this.Adapter.getFilter().filter(charSequence);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

                                etDiagnosa2.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                    }

                                    @Override
                                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                        DiagnosaActivity.this.Adapter2.getFilter().filter(charSequence);
                                    }

                                    @Override
                                    public void afterTextChanged(Editable editable) {

                                    }
                                });

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

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lv1.setVisibility(View.GONE);
                etDiagnosa1.setText(lv1.getItemAtPosition(i).toString());
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                lv2.setVisibility(View.GONE);
                etDiagnosa2.setText(lv2.getItemAtPosition(i).toString());
            }
        });




    }
    public  void updateDiagnosa(String diagnosa_1, String diagnosa_2,String id){
        JSONObject objAdd2 = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("diagnosa_1",diagnosa_1);
            objDetail.put("diagnosa_2",diagnosa_2);
            objDetail.put("id_pasien",id);
            arrData.put(objDetail);
            objAdd2.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UPDATE_DIAGNOSA_URL, objAdd2,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Toast.makeText(DiagnosaActivity.this, "Berhasil menyimpan diagnosis", Toast.LENGTH_SHORT).show();
                                finish();
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
                        Toast.makeText(DiagnosaActivity.this, "error", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(DiagnosaActivity.this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
