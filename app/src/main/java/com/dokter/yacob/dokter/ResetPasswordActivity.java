package com.dokter.yacob.dokter;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
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

public class ResetPasswordActivity extends AppCompatActivity {
    EditText etPhone,etVerifikasi,etBaru,etKonfirmasiBaru;
    Button btnReset;
    RequestQueue queue;
    public static String request_id="",temp_phone="";

    LinearLayout linearPhone,linearVerifikasi,linearReset;

    TextView tvUlang;

    String URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/resetPasswordDokter.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        etPhone = findViewById(R.id.etPhone);
        etVerifikasi = findViewById(R.id.etVerifikasi);
        btnReset = findViewById(R.id.btnReset);
        queue = Volley.newRequestQueue(this);
        linearPhone = findViewById(R.id.linearPhone);
        linearVerifikasi = findViewById(R.id.linearVerifikasi);
        linearReset = findViewById(R.id.linearReset);
        etBaru = findViewById(R.id.etBaru);
        etKonfirmasiBaru = findViewById(R.id.etKonfirmasiBaru);
        tvUlang = findViewById(R.id.tvUlang);



        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btnReset.getText().toString().equals("Kirim kode verifikasi")) {
                    if (etPhone.getText().toString().equals("")) {
                        Toast.makeText(ResetPasswordActivity.this, "Harap masukkan nomor handphone terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        cekPhone(etPhone.getText().toString());
                        temp_phone=etPhone.getText().toString();
                    }
                }
                else if(btnReset.getText().toString().equals("Cek kode verifikasi")){
                    if (etVerifikasi.getText().toString().equals("")) {
                        Toast.makeText(ResetPasswordActivity.this, "Harap masukkan kode verifikasi terlebih dahulu", Toast.LENGTH_SHORT).show();
                    } else {
                        cekVerif(request_id,etVerifikasi.getText().toString());
                    }
                }
                else if(btnReset.getText().toString().equals("Reset kata sandi")){
                    if(etBaru.getText().toString().equals("") || etKonfirmasiBaru.getText().toString().equals("")){
                        Toast.makeText(ResetPasswordActivity.this, "Kata sandi tidak boleh kosong", Toast.LENGTH_SHORT).show();
                    }
                    else if(!etBaru.getText().toString().equals(etKonfirmasiBaru.getText().toString())){
                        Toast.makeText(ResetPasswordActivity.this, "Kata sandi baru tidak boleh berbeda dengan konfirmasi kata sandi baru", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        doUpdate(etBaru.getText().toString(),temp_phone);
                    }
                }
            }
        });
        tvUlang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendVerif(temp_phone);
                Toast.makeText(ResetPasswordActivity.this, "Mengirim kode verifikasi....", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cekPhone(final String phone){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDokterByNomor.php?no="+phone;
        Log.v("URL:",url);
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray users = null;
                        try {
                            users = response.getJSONArray("result");
                            if(users.length() == 0){
                                Toast.makeText(ResetPasswordActivity.this, "Nomor handphone tidak terdaftar", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                linearPhone.setVisibility(View.GONE);
                                btnReset.setText("Cek kode verifikasi");
                                linearVerifikasi.setVisibility(View.VISIBLE);
                                sendVerif(phone);
                                Toast.makeText(ResetPasswordActivity.this, "Mengirim kode verifikasi....", Toast.LENGTH_SHORT).show();
                            }
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
    public void sendVerif(String phone){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/reset/resetPassword.php?number=62"+phone.substring(1,phone.length());
        Log.v("URL:",url);
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            request_id = response.getString("request_id");
                            Log.v("Request_Id",request_id);
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
    public void cekVerif(String request,String code){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/reset/cekVerif.php?request="+request+"&code="+code;
        Log.v("URL:",url);
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("16")){
                                Toast.makeText(ResetPasswordActivity.this, "Kode verifikasi yang Anda masukkan salah", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.getString("status").equals("6")){
                                Toast.makeText(ResetPasswordActivity.this, "Kode verifikasi Anda sudah terpakai", Toast.LENGTH_SHORT).show();
                            }
                            else if(response.getString("status").equals("0")){
                                linearVerifikasi.setVisibility(View.GONE);
                                btnReset.setText("Reset kata sandi");
                                linearReset.setVisibility(View.VISIBLE);
                            }

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
    public void doUpdate(String password,String phone){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("phone",phone);
            objDetail.put("password",password);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Toast.makeText(ResetPasswordActivity.this, "Berhasil reset kata sandi silahkan melalukan login kembali", Toast.LENGTH_SHORT).show();
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

                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(ResetPasswordActivity.this);
        requestQueue.add(stringRequest);
    }


}
