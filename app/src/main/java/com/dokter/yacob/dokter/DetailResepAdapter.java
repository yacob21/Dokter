package com.dokter.yacob.dokter;

/**
 * Created by yacob on 12/11/2017.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yacob on 12/11/2017.
 */



public class DetailResepAdapter extends RecyclerView.Adapter<DetailResepAdapter.DetailResepViewHolder> {
    private List<Resep> resepList;
    public int count = 0;
    Context context;


    public DetailResepAdapter(Context c, List<Resep> resepList) {
        this.resepList=resepList;
        this.context=c;
    }


    @Override
    public void onBindViewHolder(final DetailResepViewHolder detailResepViewHolder, final int i) {

        final Resep re = resepList.get(i);
        detailResepViewHolder.tvNamaObat.setText(re.Nama_Obat);
        detailResepViewHolder.tvDosis.setText(re.Dosis);
        detailResepViewHolder.tvSehari.setText(re.Sehari+" kali");
        detailResepViewHolder.tvSekali.setText(re.Sekali);
        detailResepViewHolder.tvTotalHari.setText(re.Total_Hari+" hari");
        detailResepViewHolder.tvInstruksi.setText(re.Instruksi.replace("- ","\n- ").replaceFirst("\n",""));
        if(!re.Tambahan.equals("")){
            detailResepViewHolder.layoutTambahan.setVisibility(View.VISIBLE);
        }
        detailResepViewHolder.tvTambahan.setText(re.Tambahan);

    }


    @Override
    public int getItemCount() {
        return resepList.size();
    }

    @Override
    public DetailResepAdapter.DetailResepViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view_detail_resep, viewGroup, false);

        return new DetailResepAdapter.DetailResepViewHolder(itemView);
    }

    public static class DetailResepViewHolder extends  RecyclerView.ViewHolder {
        protected TextView tvNamaObat;
        protected TextView tvDosis;
        protected TextView tvSehari;
        protected TextView tvSekali;
        protected TextView tvTotalHari;
        protected TextView tvInstruksi;
        protected  TextView tvTambahan;
        protected LinearLayout layoutTambahan;



        public DetailResepViewHolder(View v) {
            super(v);
            tvNamaObat = v.findViewById(R.id.tvNamaObat);
            tvDosis = v.findViewById(R.id.tvDosis);
            tvSehari = v.findViewById(R.id.tvSehari);
            tvSekali = v.findViewById(R.id.tvSekali);
            tvTotalHari = v.findViewById(R.id.tvTotalHari);
            tvInstruksi = v.findViewById(R.id.tvInstruksi);
            tvTambahan = v.findViewById(R.id.tvTambahan);
            layoutTambahan = v.findViewById(R.id.layoutTambahan);
        }
    }
}