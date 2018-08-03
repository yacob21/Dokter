package com.dokter.yacob.dokter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import java.util.HashMap;

public class UbahPasswordActivity extends AppCompatActivity {
    EditText etKonfirmasi,etBaru,etLama;
    Button btnSimpan;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    RequestQueue queue;
    Context context;
    SessionLogin sessionLogin;
    String GANTI_PASSWORD_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/ubahPasswordDokter.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_password);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Ubah Kata Sandi</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        etLama = findViewById(R.id.etLama);
        etBaru = findViewById(R.id.etBaru);
        etKonfirmasi = findViewById(R.id.etKonfirmasi);
        btnSimpan = findViewById(R.id.btnSimpan);
        builder = new AlertDialog.Builder(this);
        context = this;
        sessionLogin = new SessionLogin(getApplicationContext());
        final HashMap<String, String> user = sessionLogin.getUserDetails();

        queue = Volley.newRequestQueue(this);
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updatePassword(etLama.getText().toString(),etBaru.getText().toString(),etKonfirmasi.getText().toString(),user.get(SessionLogin.KEY_NIP));
            }
        });
    }

    public void updatePassword(String lama,String baru,String konfirmasi,String nik){

        if(lama.equals("")||baru.equals("")||konfirmasi.equals("")){
            builder.setMessage("Kata sandi tidak boleh kosong");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog = builder.show();
        }
        else if(lama.length()<6 || baru.length()<6 || konfirmasi.length() <6){
            builder.setMessage("Kata sandi minimal 6 karakter");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog = builder.show();
        }
        else if(!baru.equals(konfirmasi)){
            builder.setMessage("Kata sandi baru tidak boleh berbeda dengan konfirmasi kata sandi");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog = builder.show();
        }
        else{
            JSONObject obj = new JSONObject();
            try {
                JSONArray arrData = new JSONArray();
                JSONObject objDetail = new JSONObject();
                objDetail.put("nik",nik);
                objDetail.put("baru",baru);
                objDetail.put("lama",lama);
                arrData.put(objDetail);
                obj.put("data",arrData);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, GANTI_PASSWORD_URL, obj,
                    new Response.Listener<JSONObject>() {
                        @Override

                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("status").equals("OK")) {
                                    Toast.makeText(context, "Berhasil Ganti Kata Sandi", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(response.getString("status").equals("FALSE")){
                                    Toast.makeText(context, "Ubah Kata Sandi Gagal", Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(UbahPasswordActivity.this);
            requestQueue.add(stringRequest);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
