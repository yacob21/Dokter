package com.dokter.yacob.dokter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 27/07/2017.
 */

public class ListPasienAdapter extends RecyclerView.Adapter<ListPasienAdapter.PasienViewHolder> {
    private List<Pasien> pasienList;
    public int count = 0;
    Context context;
    SessionIdPasien sessionIdPasien;
    sessionToken sessionToken;
    int shift;
    RequestQueue queue;

    public ListPasienAdapter(Context c, List<Pasien> pasienList) {
        this.pasienList=pasienList;
        this.context=c;
    }


    @Override
    public void onBindViewHolder(final PasienViewHolder pasienViewHolder, final int i) {

        final Pasien pi = pasienList.get(i);
        pasienViewHolder.tvTanggal.setText(pi.Tanggal_Masuk);
        pasienViewHolder.tvNama.setText(pi.Nama_Pasien);
        pasienViewHolder.tvKamar.setText(pi.Nomor_Kamar);
        sessionToken = new sessionToken(context);
        queue = Volley.newRequestQueue(context);

        DateFormat jam = new SimpleDateFormat("HH");
        DateFormat menit = new SimpleDateFormat("mm");
        Date date = new Date();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 1);
        if(Integer.parseInt(jam.format(date)) > 07 && Integer.parseInt(jam.format(date))<15){
            if(Integer.parseInt(jam.format(date))== 15 && Integer.parseInt(menit.format(date))>=00){
                shift =2;
            }
            else if(Integer.parseInt(jam.format(date))== 07 && Integer.parseInt(menit.format(date))>=00){
                shift =1;
            }
            else{
                shift =1;
            }
        }
        else if(Integer.parseInt(jam.format(date)) >= 15 && Integer.parseInt(jam.format(date))<=21){
            if(Integer.parseInt(jam.format(date)) == 15 && Integer.parseInt(menit.format(date))>=00){
                shift =2;
            }
            else if(Integer.parseInt(jam.format(date))== 21 && Integer.parseInt(menit.format(date))>=00){
                shift =3;
            }
            else{
                shift =2;
            }
        }
        else if(Integer.parseInt(jam.format(date)) >= 21 || Integer.parseInt(jam.format(date))<=07){
            if(Integer.parseInt(jam.format(date))== 21 && Integer.parseInt(menit.format(date))>=00){
                shift =3;
            }
            else if(Integer.parseInt(jam.format(date))== 07 && Integer.parseInt(menit.format(date))>=00){
                shift =1;
            }
            else{
                shift =3;
            }
        }


        pasienViewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getNIKPerawat(pi.Id_Pasien,shift);
                sessionIdPasien = new SessionIdPasien(view.getContext());
                sessionIdPasien.createIdPasienSession(pi.Id_Pasien);
                Intent i = new Intent(view.getContext(), DetailPasienActivity.class);
                view.getContext().startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return pasienList.size();
    }

    @Override
    public ListPasienAdapter.PasienViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_pasien, viewGroup, false);

        return new ListPasienAdapter.PasienViewHolder(itemView);
    }

    public static class PasienViewHolder extends  RecyclerView.ViewHolder {
        protected TextView tvTanggal;
        protected TextView tvNama;
        protected TextView tvKamar;
        protected LinearLayout btnDetail;



        public PasienViewHolder(View v) {
            super(v);
            tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);
            tvNama = (TextView) v.findViewById(R.id.tvNama);
            tvKamar = (TextView) v.findViewById(R.id.tvKamar);
            btnDetail = (LinearLayout) v.findViewById(R.id.btnDetail);
        }
    }


    public void getNIKPerawat(String id,int shift){
        //Toast.makeText(context, String.valueOf(shift), Toast.LENGTH_SHORT).show();
        if(shift==1){
            String urlDokter = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectNikPerawat1.php?id="+id;
            JsonObjectRequest reqDokter = new JsonObjectRequest(urlDokter, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray users = null;
                            try {
                                users = response.getJSONArray("result");
                                JSONObject obj = users.getJSONObject(0);
                                sessionToken.createToken(obj.getString("NIK_Perawat_Shift_1"));
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
            queue.add(reqDokter);
        }
        else if(shift ==2){
            String urlDokter = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectNikPerawat2.php?id="+id;
            JsonObjectRequest reqDokter = new JsonObjectRequest(urlDokter, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray users = null;
                            try {
                                users = response.getJSONArray("result");
                                JSONObject obj = users.getJSONObject(0);
                                sessionToken.createToken(obj.getString("NIK_Perawat_Shift_2"));
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
            queue.add(reqDokter);
        }
        else if(shift==3){
            String urlDokter = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectNikPerawat3.php?id="+id;
            JsonObjectRequest reqDokter = new JsonObjectRequest(urlDokter, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            JSONArray users = null;
                            try {
                                users = response.getJSONArray("result");
                                JSONObject obj = users.getJSONObject(0);
                                sessionToken.createToken(obj.getString("NIK_Perawat_Shift_3"));
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
            queue.add(reqDokter);
        }
    }

}