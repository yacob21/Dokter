package com.dokter.yacob.dokter;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.HashMap;

public class ProfilActivity extends AppCompatActivity {
    ImageView ivProfil,ivGender;
    TextView tvNama,tvEmail,tvPhone;
    LinearLayout btnUbahProfil,btnUbahPassword;
    RequestQueue queue;
    SessionLogin sessionLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);
        queue = Volley.newRequestQueue(this);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Profil</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sessionLogin = new SessionLogin(getApplicationContext());
        btnUbahPassword = findViewById(R.id.btnUbahPassword);
        btnUbahProfil = findViewById(R.id.btnUbahProfil);
        HashMap<String, String> user = sessionLogin.getUserDetails();
        getDetailPerawat(user.get(SessionLogin.KEY_NIP));

        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProfilActivity.this,UbahPasswordActivity.class));
            }
        });

        btnUbahProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ProfilActivity.this,UbahProfilActivity.class);
                //i.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(i);
            }
        });

    }

    public void getDetailPerawat(String nik){
        ivProfil = findViewById(R.id.ivProfil);
        ivGender = findViewById(R.id.ivGender);
        tvNama = findViewById(R.id.tvNama);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectDetailDokter.php?nip="+nik;
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
                                tvNama.setText(obj.getString("Nama_Dokter"));
                                tvEmail.setText(obj.getString("Email"));
                                if(obj.getString("Jenis_Kelamin").equals("Wanita")){
                                    ivGender.setImageResource(R.drawable.female);
                                }
                                else if(obj.getString("Jenis_Kelamin").equals("Pria")){
                                    ivGender.setImageResource(R.drawable.male);
                                }
                                tvPhone.setText(obj.getString("Nomor_Handphone"));
                                if(obj.getString("Gambar").equals("") || obj.getString("Gambar").equals(null)) {

                                }
                                else{
                                    Picasso.with(ProfilActivity.this)
                                            .load(obj.getString("Gambar"))
                                            .config(Bitmap.Config.RGB_565)
                                            .placeholder(R.drawable.profile)
                                            .error(R.drawable.profile)
                                            .fit()
                                            .centerInside()
                                            .into(ivProfil);
                                }
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
    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0, 0);
    }
}
