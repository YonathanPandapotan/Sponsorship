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


public class RecyclerViewAdapterSponsor extends RecyclerView.Adapter<RecyclerViewAdapterSponsor.ViewHolder> {

    ArrayList<DataSponsoran> mValues;
    Context mContext;
    protected ItemListener mListener;

    public RecyclerViewAdapterSponsor(Context context, ArrayList values, ItemListener itemListener) {

        mValues = values;
        mContext = context;
        mListener=itemListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView judulSponsoran, instansi, op, deskripsi;
        public ImageView imageView;
        public ProgressBar prog;
        public RelativeLayout relativeLayout;
        DataSponsoran item;

        public ViewHolder(View v) {

            super(v);

            v.setOnClickListener(this);
            judulSponsoran = v.findViewById(R.id.judulSponsoran);
            instansi = v.findViewById(R.id.instansi);
            op = v.findViewById(R.id.user);
            deskripsi = v.findViewById(R.id.deskripsi);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);

        }

        public void setData(DataSponsoran item) {
            this.item = item;

            judulSponsoran.setText(item.getJudulSponsor());
            instansi.setText(item.getInstansi());
            op.setText(item.getNamaPembuat());
            deskripsi.setText(item.getDeskripsi());
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
    public RecyclerViewAdapterSponsor.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.recycler_view_item_sponsor, parent, false);

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
        void onItemClick(DataSponsoran item);
    }
}
