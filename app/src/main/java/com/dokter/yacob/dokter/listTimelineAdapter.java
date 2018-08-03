package com.dokter.yacob.dokter;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class listTimelineAdapter extends RecyclerView.Adapter<listTimelineAdapter.TimelineViewHolder> {
    private List<Timeline> timelineList;
    public int count = 0;
    Context context;
    String resep="";
    RequestQueue queue;
    SessionIdPasien sessionIdPasien;
    public listTimelineAdapter(Context c, List<Timeline> timelineList) {
        this.timelineList=timelineList;
        this.context=c;
    }


    @Override
    public void onBindViewHolder(final TimelineViewHolder timelineViewHolder, final int i) {

        final Timeline tl = timelineList.get(i);
        timelineViewHolder.tvTanggal.setText(tl.Tanggal);
        timelineViewHolder.tvAktivitas.setText(tl.Aktivitas);
        timelineViewHolder.tvNik.setText(tl.NIK_Perawat);
        timelineViewHolder.tvNama.setText(tl.Nama_Perawat);
        timelineViewHolder.tvNikDokter.setText(tl.NIK_Dokter);
        timelineViewHolder.tvNamaDokter.setText(tl.Nama_Dokter);

        if (tl.NIK_Dokter.equals("null") || tl.Nama_Dokter.equals(null)) {
            timelineViewHolder.linearDokter.setVisibility(View.GONE);
            timelineViewHolder.linearPerawat.setVisibility(View.VISIBLE);
        }
        else if(tl.NIK_Perawat.equals("null") || tl.Nama_Perawat.equals(null)){
            timelineViewHolder.linearPerawat.setVisibility(View.GONE);
            timelineViewHolder.linearDokter.setVisibility(View.VISIBLE);
        }

        if(!tl.Gambar.equals("null") && !tl.Gambar.equals("")){
            timelineViewHolder.linearGambar.setVisibility(View.VISIBLE);
            Picasso.with(context)
                    .load(tl.Gambar)
                    .config(Bitmap.Config.RGB_565)
                    .placeholder(R.drawable.default_)
                    .error(R.drawable.default_)
                    .fit()
                    .centerInside()
                    .into(timelineViewHolder.ivGambar);
        }
        else{
            timelineViewHolder.linearGambar.setVisibility(View.GONE);
        }

        sessionIdPasien = new SessionIdPasien(context);
        queue = Volley.newRequestQueue(context);
        timelineViewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tl.Aktivitas.equals("Cek TTV")){
                    Intent i = new Intent(context,ActivityTTV.class);
                    i.putExtra("aktivitas",tl.Aktivitas);
                    i.putExtra("tanggal",tl.Date.substring(0,23));
                    context.startActivity(i);
                }
                else if(tl.Aktivitas.equals("Pengkajian Awal")){
                    Intent i = new Intent(view.getContext(),PengkajianAwalActivity.class);
                    context.startActivity(i);
                }
                else if(tl.Aktivitas.equals("Pemberian Obat")){
//                    getResep(sessionIdPasien.getIdPasienDetails().get(SessionIdPasien.KEY_ID_PASIEN));
                    Intent i = new Intent(context,PemberianObat.class);
                    i.putExtra("panduanIdResep",tl.Id_Resep);
                    context.startActivity(i);
                }
                else {
                    if(!tl.Gambar.equals("null") && !tl.Gambar.equals("")){
                        showPopup(view,tl.Gambar);
                    }
                    else{

                    }
                }
            }
        });


    }


    @Override
    public int getItemCount() {
        return timelineList.size();
    }

    @Override
    public listTimelineAdapter.TimelineViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_timeline, viewGroup, false);

        return new listTimelineAdapter.TimelineViewHolder(itemView);
    }

    public static class TimelineViewHolder extends  RecyclerView.ViewHolder {
        protected TextView tvTanggal;
        protected TextView tvAktivitas;
        protected TextView tvNik;
        protected TextView tvNama;
        protected LinearLayout btnDetail;
        protected LinearLayout linearPerawat;
        protected LinearLayout linearDokter,linearGambar;
        protected TextView tvNikDokter;
        protected TextView tvNamaDokter;
        ImageView ivGambar;


        public TimelineViewHolder(View v) {
            super(v);
            tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);

            tvAktivitas = v.findViewById(R.id.tvAktivitas);
            tvNik = v.findViewById(R.id.tvNik);
            tvNama = v.findViewById(R.id.tvNama);

            tvNikDokter = v.findViewById(R.id.tvNikDokter);
            tvNamaDokter = v.findViewById(R.id.tvNamaDokter);

            linearDokter=v.findViewById(R.id.linearDokter);
            linearPerawat = v.findViewById(R.id.linearPerawat);
            btnDetail = (LinearLayout) v.findViewById(R.id.btnDetail);

            linearGambar = v.findViewById(R.id.linearGambar);
            ivGambar = v.findViewById(R.id.ivGambar);
        }
    }


    public void getResep(String id){

        String url = "http://rumahsakit.gearhostpreview.com/Rumah_Sakit/selectResepPasien.php?id_pasien="+id;
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
                        if(users.length() == 0){

                            Toast.makeText(context, "Pasien belum memiliki resep", Toast.LENGTH_SHORT).show();

                        }
                        else {
                            try {
                                JSONObject obj = users.getJSONObject(0);
                                Intent i = new Intent(context,PemberianObat.class);
                                i.putExtra("panduanIdResep",obj.getString("Id_Resep"));
                                context.startActivity(i);

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


    public void showPopup(View view,String Gambar) {
        View popupView = LayoutInflater.from(context).inflate(R.layout.popup_image, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        ImageView imageView = popupView.findViewById(R.id.imageView);
        Picasso.with(context)
                .load(Gambar)
                .config(Bitmap.Config.RGB_565)
                .placeholder(R.drawable.default_)
                .error(R.drawable.default_)
                .fit()
                .centerInside()
                .into(imageView);
       Button btn = popupView.findViewById(R.id.btn);

       btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        popupWindow.showAsDropDown(popupView, 0, 0);
    }



}