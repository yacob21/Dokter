package com.dokter.yacob.dokter;

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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class PesanActivity extends AppCompatActivity {
    EditText etPesan;
    Button btnSimpan;
    ImageView iv1,iv2,iv3,iv4;
    String selectedPath1="";
    private static final int SELECT_IMAGE = 1;
    private static final int TAKE_IMAGE = 2;
    int flag=1;

    AlertDialog dialog;
    AlertDialog.Builder builder;
    Context context;
    String  INSERT_PESAN_URL="http://rumahsakit.gearhostpreview.com/Rumah_Sakit/insertPesanDokter.php";

    SessionIdPasien sessionIdPasien;
    SessionLogin sessionLogin;
    sessionToken sessionToken;

    public static String formattedDate;

    RequestQueue queue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pesan);
        etPesan = findViewById(R.id.etPesan);
        btnSimpan= findViewById(R.id.btnSimpan);
        context = this;
        queue = Volley.newRequestQueue(this);
        sessionIdPasien = new SessionIdPasien(getApplicationContext());
        sessionLogin = new SessionLogin(getApplicationContext());
        sessionToken = new sessionToken(getApplicationContext());

        iv1 = findViewById(R.id.iv1);
        builder = new AlertDialog.Builder(this);
        iv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag = 1;
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Pilih gambar menggunakan?");
                builder.setItems(new CharSequence[]
                                {"Kamera", "Galeri", "Batal"},
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // The 'which' argument contains the index position
                                // of the selected item
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



        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
                formattedDate = df.format(c);
                if(etPesan.getText().toString().equals("")){
                    Toast.makeText(context, "Pesan tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else {
                    send("Pesan baru untuk pasien bernama "+DetailPasienActivity.temp_nama,etPesan.getText().toString(),sessionToken.getToken().get(sessionToken.KEY_TOKEN));
                    if (selectedPath1.equals("")) {
                        insertPesan(Integer.parseInt(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN)), sessionLogin.getUserDetails().get(SessionLogin.KEY_NIP),
                                etPesan.getText().toString(),
                                "");
                    } else {
                        uploadPesan();
                        insertPesan(Integer.parseInt(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN)), sessionLogin.getUserDetails().get(SessionLogin.KEY_NIP),
                                etPesan.getText().toString(),
                                "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/pesan/" + formattedDate + "_" + sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN) + ".jpg");
                    }
                }

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
            if (requestCode == SELECT_IMAGE && flag==1) {
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
                selectedPath1 = String.valueOf(file);
                iv1.setImageBitmap(yourSelectedImage);
                //iv2.setVisibility(View.VISIBLE);
            }
            else if(requestCode == TAKE_IMAGE && flag==1){
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                iv1.setImageBitmap(photo);
                Uri tempUri = getImageUri(getApplicationContext(),photo);
                File file = new File(getRealPath(tempUri));
                selectedPath1 = String.valueOf(file);
               // iv2.setVisibility(View.VISIBLE);
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

    private void uploadPesan() {
        class UploadPesan extends AsyncTask<Void, Void, String> {

            ProgressDialog uploading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                uploading = ProgressDialog.show(PesanActivity.this, "Uploading File", "Please wait...", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                uploading.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                Upload u = new Upload();
                String msg = u.uploadPesan(selectedPath1,getApplicationContext());
                return msg;
            }
        }
        UploadPesan uv = new UploadPesan();
        uv.execute();
    }

    public void insertPesan(int Id_Pasien,String NIK_Dokter,String Aktivitas,String Gambar){
        JSONObject objAdd = new JSONObject();
        try {
            JSONArray arrData = new JSONArray();
            JSONObject objDetail = new JSONObject();
            objDetail.put("Id_Pasien",Id_Pasien);
            objDetail.put("NIK_Dokter",NIK_Dokter);
            objDetail.put("Aktivitas",Aktivitas);
            objDetail.put("Gambar",Gambar);
            arrData.put(objDetail);
            objAdd.put("data",arrData);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.POST, INSERT_PESAN_URL, objAdd,
                new Response.Listener<JSONObject>() {
                    @Override

                    public void onResponse(JSONObject response) {
                        try {
                            if(response.getString("status").equals("OK")) {
                                Intent i = new Intent(PesanActivity.this, DetailPasienActivity.class);
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(PesanActivity.this, "Gagal", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                }) ;
        RequestQueue requestQueue = Volley.newRequestQueue(PesanActivity.this);
        requestQueue.add(stringRequest);
    }

    public void send(final String title,final String pesan,String nik){
        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectTokenPerawat.php?nip="+nik;
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray users = null;
                        try {
                            users = response.getJSONArray("result");
                            JSONObject obj = users.getJSONObject(0);
                            //Toast.makeText(context, obj.getString("Token"), Toast.LENGTH_SHORT).show();
                            String urlJumlah = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/notifikasi/notifPesanPerawat.php?title="+title+"&pesan="+pesan+"&token="+obj.getString("Token");
                            Log.v("URL Pesan:",urlJumlah);
                            JsonObjectRequest reqJumlah = new JsonObjectRequest(urlJumlah, null,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            JSONArray users = null;
                                            try {
                                                users = response.getJSONArray("result");
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
                            queue.add(reqJumlah);
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



}
