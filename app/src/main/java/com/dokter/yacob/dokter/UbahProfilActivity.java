package com.dokter.yacob.dokter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.HashMap;


public class UbahProfilActivity extends AppCompatActivity {
    EditText etNama,etEmail,etPhone;
    ImageView ivProfil;
    RadioButton rbPria,rbWanita;
    Button btnSimpan;
    SessionLogin sessionLogin;
    RequestQueue queue;
    AlertDialog dialog;
    AlertDialog.Builder builder;
    Context context;
    String UBAH_PROFIL_URL = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/updateProfilDokter.php";
    String JK;

    private static final int SELECT_IMAGE = 1;
    private static final int TAKE_IMAGE = 2;
    String selectedPath="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah_profil);
        sessionLogin = new SessionLogin(getApplicationContext());
        btnSimpan = findViewById(R.id.btnSimpan);
        queue = Volley.newRequestQueue(this);
        getSupportActionBar().setTitle(Html.fromHtml("<'font color='#ffffff'>Ubah Profil</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final HashMap<String, String> user = sessionLogin.getUserDetails();
        getDetailPerawat(user.get(SessionLogin.KEY_NIP));
        builder = new AlertDialog.Builder(this);

        context =this;
        if(rbPria.isChecked()){
            JK="Pria";
        }
        else if(rbWanita.isChecked()){
            JK="Wanita";
        }
        rbPria.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    JK="Pria";
                }
            }
        });

        rbWanita.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    JK="Wanita";
                }
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!rbPria.isChecked() && !rbWanita.isChecked()){
                    builder.setMessage("Harap pilih jenis kelamin Anda");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    });
                    dialog = builder.show();
                }
                else{
                    if(selectedPath.equals("")){
                        updateProfilPerawat(etNama.getText().toString(),etEmail.getText().toString(),etPhone.getText().toString(),JK,user.get(SessionLogin.KEY_NIP),"");
                    }
                    else{
                        uploadProfil();
                        updateProfilPerawat(etNama.getText().toString(),etEmail.getText().toString(),etPhone.getText().toString(),JK,user.get(SessionLogin.KEY_NIP),
                                "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/profile/"+sessionLogin.getUserDetails().get(SessionLogin.KEY_NIP)+".jpg");
                    }


                }
            }
        });

        ivProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(context);
                builder.setTitle("Pilih gambar menggunakan?");
                builder.setItems(new CharSequence[]
                                {"Kamera", "Galeri", "Batal"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        takePhoto();
                                        break;
                                    case 1:
                                        selectGallery();
                                        break;
                                    case 2:

                                        break;
                                }
                            }
                        });
                builder.create().show();
            }
        });


    }


    private void selectGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK,
                Uri.parse(MediaStore.Images.Media.DATA));
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent, "Select a Image "), SELECT_IMAGE);

    }

    private void takePhoto() {
        Intent intent2 =  new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(Intent.createChooser(intent2, "Take a Image "), TAKE_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_IMAGE) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();
                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);
                Uri tempUri = getImageUri(getApplicationContext(),yourSelectedImage);
                File file = new File(getRealPath(tempUri));
                selectedPath = String.valueOf(file);
                ivProfil.setImageBitmap(yourSelectedImage);
            }
            else if(requestCode == TAKE_IMAGE){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                ivProfil.setImageBitmap(photo);
                Uri tempUri = getImageUri(getApplicationContext(),photo);
                File file = new File(getRealPath(tempUri));
                selectedPath = String.valueOf(file);
        }
        }
    }

    public Uri getImageUri(Context context, Bitmap bit){
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.JPEG,100,bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(),bit,"Title",null);
        return Uri.parse(path);
    }

    public String getRealPath(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    private void uploadProfil() {
        class UploadProfil extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(UbahProfilActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadProfil(selectedPath,getApplicationContext());
                return msg;
            }
        }
        UploadProfil uv = new UploadProfil();
        uv.execute();
    }

    public void updateProfilPerawat(String nama,String email,String phone,String JK, String nik,String path ){
        if (nama.equals("") || email.equals("") || phone.equals("")) {
            builder.setMessage("Harap isi data Anda");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
                objDetail.put("email",email);
                objDetail.put("nama",nama);
                objDetail.put("phone",phone);
                objDetail.put("jenis_kelamin",JK);
                objDetail.put("nik",nik);
                objDetail.put("gambar",path);
                arrData.put(objDetail);
                obj.put("data",arrData);
            } catch (JSONException e1) {
                e1.printStackTrace();
            }
            JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, UBAH_PROFIL_URL, obj,
                    new Response.Listener<JSONObject>() {
                        @Override

                        public void onResponse(JSONObject response) {
                            try {
                                if(response.getString("status").equals("OK")) {
                                    Toast.makeText(context, "Berhasil ubah profil", Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                                else if(response.getString("status").equals("FALSE")){
                                    Toast.makeText(context, "Gagal mengubah profil", Toast.LENGTH_SHORT).show();
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
            RequestQueue requestQueue = Volley.newRequestQueue(UbahProfilActivity.this);
            requestQueue.add(stringRequest);

        }
    }

    public void getDetailPerawat(String nik){
        ivProfil = findViewById(R.id.ivProfil);
        etNama = findViewById(R.id.etNama);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        rbPria = findViewById(R.id.rbPria);
        rbWanita = findViewById(R.id.rbWanita);
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
                                etNama.setText(obj.getString("Nama_Dokter"));
                                etEmail.setText(obj.getString("Email"));
                                if(obj.getString("Jenis_Kelamin").equals("Wanita")){
                                    rbWanita.setChecked(true);
                                }
                                else if(obj.getString("Jenis_Kelamin").equals("Pria")){
                                    rbPria.setChecked(true);
                                }
                                etPhone.setText(obj.getString("Nomor_Handphone"));
                                if(obj.getString("Gambar").equals("") || obj.getString("Gambar").equals(null)) {

                                }
                                else{
                                    Picasso.with(UbahProfilActivity.this)
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
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                return true;
        }


        return super.onOptionsItemSelected(item);
    }

}
