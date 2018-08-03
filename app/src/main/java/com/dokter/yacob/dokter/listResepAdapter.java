package com.dokter.yacob.dokter;

/**
 * Created by yacob on 12/11/2017.
 */

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by user on 27/07/2017.
 */

public class listResepAdapter extends RecyclerView.Adapter<listResepAdapter.ResepViewHolder> {
    private List<Resep> resepList;
    public int count = 0;
    Context context;


    public listResepAdapter(Context c, List<Resep> resepList) {
        this.resepList=resepList;
        this.context=c;
    }


    @Override
    public void onBindViewHolder(final ResepViewHolder resepViewHolder, final int i) {

        final Resep re = resepList.get(i);
        resepViewHolder.tvTanggal.setText(re.Tanggal_Dibuat);
        resepViewHolder.tvJumlah.setText(re.Jumlah);

        resepViewHolder.btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                sessionIdPasien = new SessionIdPasien(view.getContext());
//                sessionIdPasien.createIdPasienSession(pi.Id_Pasien);
                Intent i = new Intent(view.getContext(), DetailResepActivity.class);
                i.putExtra("panduanIdResep",re.Id_Resep);
                view.getContext().startActivity(i);
            }
        });


    }


    @Override
    public int getItemCount() {
        return resepList.size();
    }

    @Override
    public listResepAdapter.ResepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_resep, viewGroup, false);

        return new listResepAdapter.ResepViewHolder(itemView);
    }

    public static class ResepViewHolder extends  RecyclerView.ViewHolder {
        protected TextView tvTanggal;
        protected TextView tvJumlah;
        protected LinearLayout btnDetail;



        public ResepViewHolder(View v) {
            super(v);
            tvTanggal = (TextView) v.findViewById(R.id.tvTanggal);
            tvJumlah = (TextView) v.findViewById(R.id.tvJumlah);
            btnDetail = (LinearLayout) v.findViewById(R.id.btnDetail);
        }
    }
}