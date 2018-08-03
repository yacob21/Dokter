package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;

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

public class TambahResepActivity extends AppCompatActivity {
    Spinner spinnerObat,spinnerDosis,spinnerAturan,spinnerQty,spinnerWaktu;
    RadioButton rbSesudah,rbSebelum;
    CheckBox cbDemam,cbProrenata;
    EditText etTambahan;
    Button btnSimpan,btnLanjut;

    LinearLayout layoutDosis;

    RequestQueue queue;
    List<String> listObat = new ArrayList<String>();
    List<String> listDosis = new ArrayList<String>();
    List<String> listAturan = new ArrayList<String>();
    List<String> listQty = new ArrayList<String>();
    List<String> listWaktu = new ArrayList<String>();
    Context context;
    SessionIdPasien sessionIdPasien;

    AlertDialog dialog;
    AlertDialog.Builder builder;


    String INSERT_DETAIL_RESEP_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertDetailResep.php";
    String tempCaraPemakaian="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_resep);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        spinnerAturan = findViewById(R.id.spinnerAturan);
        spinnerDosis = findViewById(R.id.spinnerDosis);
        spinnerObat = findViewById(R.id.spinnerObat);
        spinnerQty = findViewById(R.id.spinnerQty);
        spinnerWaktu = findViewById(R.id.spinnerWaktu);
        rbSebelum = findViewById(R.id.rbSebelum);
        rbSesudah = findViewById(R.id.rbSesudah);
        cbDemam = findViewById(R.id.cbDemam);
        cbProrenata = findViewById(R.id.cbProrenata);
        etTambahan = findViewById(R.id.etTambahan);
        btnLanjut = findViewById(R.id.btnLanjut);
        btnSimpan = findViewById(R.id.btnSimpan);
        layoutDosis = findViewById(R.id.layoutDosis);
        queue = Volley.newRequestQueue(this);
        context = this;
        builder = new AlertDialog.Builder(this);


        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        final HashMap<String, String> idpasien = sessionIdPasien.getIdPasienDetails();


        listAturan.add("1x");listAturan.add("2x");listAturan.add("3x");
        ArrayAdapter<String> aturanAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listAturan);
        aturanAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAturan.setAdapter(aturanAdapter);
        spinnerAturan.setSelection(0);

        listQty.add("1/4");listQty.add("1/2");listQty.add("1");listQty.add("2");listQty.add("3");listQty.add("4");listQty.add("5");
        ArrayAdapter<String> qtyAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listQty);
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerQty.setAdapter(qtyAdapter);
        spinnerQty.setSelection(2);

        listWaktu.add("1");listWaktu.add("2");listWaktu.add("3");listWaktu.add("4");listWaktu.add("5");listWaktu.add("6");listWaktu.add("7");listWaktu.add("8");listWaktu.add("9");listWaktu.add("10");
        listWaktu.add("11");listWaktu.add("12");listWaktu.add("13");listWaktu.add("14");listWaktu.add("15");listWaktu.add("16");listWaktu.add("17");listWaktu.add("18");listWaktu.add("19");listWaktu.add("20");
        listWaktu.add("21");listWaktu.add("22");listWaktu.add("23");listWaktu.add("24");listWaktu.add("25");listWaktu.add("26");listWaktu.add("27");listWaktu.add("28");listWaktu.add("29");listWaktu.add("30");
        ArrayAdapter<String> waktuAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, listWaktu);
        waktuAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWaktu.setAdapter(waktuAdapter);
        spinnerWaktu.setSelection(0);

        spinnerQty.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerQty.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerWaktu.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerWaktu.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        spinnerAturan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerAturan.setSelection(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectObat.php";
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
                                listObat.add(obj.getString("Nama_Obat"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            ArrayAdapter<String> obatAdapter = new ArrayAdapter<String>(context,
                                    android.R.layout.simple_spinner_item, listObat);
                            obatAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerObat.setAdapter(obatAdapter);

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
            }
        });
        queue.add(req);
        spinnerObat.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerObat.setSelection(i);
                listDosis.clear();
                String urlDosis = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDosisObat.php?obat="+String.valueOf(spinnerObat.getSelectedItem());
                JsonObjectRequest reqDosis = new JsonObjectRequest(urlDosis, null,
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
                                        listDosis.add(obj.getString("Strength"));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    ArrayAdapter<String> dosisAdapter = new ArrayAdapter<String>(context,
                                            android.R.layout.simple_spinner_item, listDosis);
                                    dosisAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                                    spinnerDosis.setAdapter(dosisAdapter);
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(ListPasien.this,"Terjadi Kendala Koneksi",Toast.LENGTH_LONG ).show();
                    }
                });
                queue.add(reqDosis);
                layoutDosis.setVisibility(View.VISIBLE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        rbSesudah.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tempCaraPemakaian = tempCaraPemakaian+"- "+"Sesudah makan";
                }
                else{
                    tempCaraPemakaian = tempCaraPemakaian.replace("- "+"Sesudah makan","");
                }
            }
        });

        rbSebelum.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tempCaraPemakaian = tempCaraPemakaian+"- "+"Sebelum makan";
                }
                else{
                    tempCaraPemakaian = tempCaraPemakaian.replace("- "+"Sebelum makan","");
                }
            }
        });

        cbDemam.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tempCaraPemakaian = tempCaraPemakaian+"- "+"Diberikan hanya jika masih demam (38 C)";
                }
                else{
                    tempCaraPemakaian = tempCaraPemakaian.replace("- "+"Diberikan hanya jika masih demam (38 C)","");
                }
            }
        });

        cbProrenata.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    tempCaraPemakaian = tempCaraPemakaian+"- "+"Prorenata (berikan jika perlu)";
                }
                else{
                    tempCaraPemakaian = tempCaraPemakaian.replace("- "+"Prorenata (berikan jika perlu)","");
                }
            }
        });







        btnLanjut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rbSesudah.isChecked() && !rbSebelum.isChecked()){
                    builder.setMessage("Harap pilih cara pemakaian obat");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog = builder.show();
                }
                else{
                    builder.setMessage("Apakah Anda yakin dengan resep yang sekarang?");
                    builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectIdObat.php?nama_obat="+String.valueOf(spinnerObat.getSelectedItem()).replace(" ","+")
                                    +"&strength="+String.valueOf(spinnerDosis.getSelectedItem()).replace(" ","+");
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
                                                final JSONObject obj = users.getJSONObject(0);
                                                Intent i = getIntent();
                                                //Toast.makeText(TambahResepActivity.this, obj.getString("Id_Obat"), Toast.LENGTH_SHORT).show();
                                                JSONObject objAdd2 = new JSONObject();
                                                try {
                                                    JSONArray arrData = new JSONArray();
                                                    JSONObject objDetail = new JSONObject();
                                                    objDetail.put("id_resep",i.getStringExtra("panduanIdResep"));
                                                    objDetail.put("id_obat",obj.getString("Id_Obat"));
                                                    objDetail.put("sehari",String.valueOf(spinnerAturan.getSelectedItem()).replace("x",""));
                                                    objDetail.put("sekali",String.valueOf(spinnerQty.getSelectedItem()));
                                                    objDetail.put("total_hari",String.valueOf(spinnerWaktu.getSelectedItem()));
                                                    objDetail.put("instruksi_tambahan",etTambahan.getText().toString());
                                                    objDetail.put("cara_pemakaian",tempCaraPemakaian);
                                                    arrData.put(objDetail);
                                                    objAdd2.put("data",arrData);
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();
                                                }
                                                JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.POST, INSERT_DETAIL_RESEP_URL, objAdd2,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override

                                                            public void onResponse(JSONObject response) {
                                                                try {
                                                                    if(response.getString("status").equals("OK")) {
                                                                        Intent z = getIntent();
                                                                        Intent i = new Intent(TambahResepActivity.this,TambahResepActivity.class);
                                                                        i.putExtra("panduanIdResep",z.getStringExtra("panduanIdResep"));
                                                                        i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                                        startActivity(i);
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
                                                RequestQueue requestQueue2 = Volley.newRequestQueue(TambahResepActivity.this);
                                                requestQueue2.add(stringRequest2);
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
                    });

                    builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    dialog = builder.show();
                }


            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rbSesudah.isChecked() && !rbSebelum.isChecked()){
                    builder.setMessage("Harap pilih cara pemakaian obat");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog = builder.show();
                }
                else{
                    builder.setMessage("Apakah Anda yakin dengan resep yang sekarang?");
                    builder.setPositiveButton("YA", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectIdObat.php?nama_obat="+String.valueOf(spinnerObat.getSelectedItem())
                                    +"&strength="+String.valueOf(spinnerDosis.getSelectedItem());
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
                                                final JSONObject obj = users.getJSONObject(0);
                                                Intent i = getIntent();
                                                JSONObject objAdd2 = new JSONObject();
                                                try {
                                                    JSONArray arrData = new JSONArray();
                                                    JSONObject objDetail = new JSONObject();
                                                    objDetail.put("id_resep",i.getStringExtra("panduanIdResep"));
                                                    objDetail.put("id_obat",obj.getString("Id_Obat"));
                                                    objDetail.put("sehari",String.valueOf(spinnerAturan.getSelectedItem()).replace("x",""));
                                                    objDetail.put("sekali",String.valueOf(spinnerQty.getSelectedItem()));
                                                    objDetail.put("total_hari",String.valueOf(spinnerWaktu.getSelectedItem()));
                                                    objDetail.put("instruksi_tambahan",etTambahan.getText().toString());
                                                    objDetail.put("cara_pemakaian",tempCaraPemakaian);
                                                    arrData.put(objDetail);
                                                    objAdd2.put("data",arrData);
                                                } catch (JSONException e1) {
                                                    e1.printStackTrace();
                                                }
                                                JsonObjectRequest stringRequest2 = new JsonObjectRequest(Request.Method.POST, INSERT_DETAIL_RESEP_URL, objAdd2,
                                                        new Response.Listener<JSONObject>() {
                                                            @Override

                                                            public void onResponse(JSONObject response) {
                                                                try {
                                                                    if(response.getString("status").equals("OK")) {
                                                                        Intent i = new Intent(TambahResepActivity.this,ResepActivity.class);
                                                                       // i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                                                        startActivity(i);
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
                                                RequestQueue requestQueue2 = Volley.newRequestQueue(TambahResepActivity.this);
                                                requestQueue2.add(stringRequest2);
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
                    });

                    builder.setNegativeButton("TIDAK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                        }
                    });

                    dialog = builder.show();
                }

            }
        });


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent i = new Intent(TambahResepActivity.this,ResepActivity.class);
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(TambahResepActivity.this,ResepActivity.class);
        startActivity(i);
    }

}
