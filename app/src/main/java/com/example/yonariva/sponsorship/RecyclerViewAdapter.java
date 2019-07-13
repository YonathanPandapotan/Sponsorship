package com.example.yonariva.sponsorship;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<DataPostingan> mValues;
    Context mContext;
    protected ItemListener mListener;

    public RecyclerViewAdapter(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView namaAcara, tanggal, alamat, instansi, op;
        public ImageView imageView;
        public ProgressBar prog;
        public RelativeLayout relativeLayout;
        DataPostingan item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            namaAcara = (TextView) v.findViewById(R.id.NamaAcara);
            tanggal = (TextView) v.findViewById(R.id.Tanggal);
            alamat = (TextView) v.findViewById(R.id.Alamat);
            instansi = (TextView) v.findViewById(R.id.Instansi);
            op = (TextView) v.findViewById(R.id.op);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(DataPostingan item) {
            this.item = item;

            namaAcara.setText(item.getNamaPost());
            tanggal.setText(item.getTanggal());
            alamat.setText(item.getAlamat());
            instansi.setText(item.getInstansi());
            op.setText(item.getOp());
//            imageView.setImageResource(R.drawable.ic_launcher_background);
            FirebaseUtilities.downloadImage(item.getId(), imageView);
        }


        @Override
        public void onClick(View view) {
            if (mListener != null) {
                mListener.onItemClick(item);
            }
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder Vholder, int position) {
        Vholder.setData(mValues.get(position));

    }

    @Override
    public int getItemCount() {

        return mValues.size();
    }

    public interface ItemListener {
        void onItemClick(DataPostingan item);
    }
}
