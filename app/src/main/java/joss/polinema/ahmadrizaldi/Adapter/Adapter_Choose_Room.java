package joss.polinema.ahmadrizaldi.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import joss.polinema.ahmadrizaldi.Model.RoomHotel_Fix;
import joss.polinema.ahmadrizaldi.R;

public class Adapter_Choose_Room extends RecyclerView.Adapter<Adapter_Choose_Room.ViewHolder> {

    private Context context;
    private ArrayList<RoomHotel_Fix> rvData;
    private int poin;
    private String poinPending;

    public Adapter_Choose_Room(Context context, ArrayList<RoomHotel_Fix> rvData) {
        this.context = context;
        this.rvData = rvData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listroom, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        poin = Integer.parseInt(rvData.get(position).getHarga());
        int panjang = String.valueOf(poin).length();

        String uang = rvData.get(position).getHarga();

        if (panjang == 6){
            holder.poinHotel.setText(uang.substring(0,2) + " poin");
            poinPending = uang.substring(0,2);
        }else if (panjang == 7){
            holder.poinHotel.setText(uang.substring(0,3)+ " poin");
            poinPending = uang.substring(0,3);
        }else if (panjang == 8){
            holder.poinHotel.setText(uang.substring(0,4)+ " poin");
            poinPending = uang.substring(0,4);
        }else if (panjang == 9){
            holder.poinHotel.setText(uang.substring(0,5)+ " poin");
            poinPending = uang.substring(0,5);
        }else if (panjang == 10){
            holder.poinHotel.setText(uang.substring(0,6)+ " poin");
            poinPending = uang.substring(0,6);
        }


        holder.namaRoom.setText(rvData.get(position).getNamaRoom());
        holder.hargaRoom.setText("Rp." + rvData.get(position).getHarga());

        loadImageFromUrl(rvData.get(position).getGm1(), holder.gambar);

    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView namaRoom, hargaRoom, poinHotel, lihatDetail;
        public Button pilih;
        public ImageView gambar;

        public ViewHolder(View itemView) {
            super(itemView);
            namaRoom = (TextView)itemView.findViewById(R.id.textView90);
            hargaRoom = (TextView)itemView.findViewById(R.id.hargaHotelllllll);
            poinHotel = (TextView)itemView.findViewById(R.id.poinHotel);
            lihatDetail = (TextView)itemView.findViewById(R.id.textView83saaaaa);

            pilih = (Button)itemView.findViewById(R.id.pilihRoom);

            gambar = (ImageView)itemView.findViewById(R.id.imageView17);
        }
    }


    private void loadImageFromUrl(String uri, ImageView gambar){

        if (!uri.isEmpty()) {
            Picasso.get().load(uri).into(gambar);
        }

    }
}

