package com.app.cinerma.design.cines.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.Cinema;
import com.bumptech.glide.Glide;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.cinemaViewHolder>{
    private List<Cinema> cinemaList;

    //Constructor
    public CinemaAdapter(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    @NonNull
    @Override
    public cinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cinema_item, parent, false);
        return new cinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull cinemaViewHolder holder, int position) {
        Cinema cinema = cinemaList.get(position);
        holder.cinemaName.setText(cinema.getName());
        holder.cinemaAddress.setText(cinema.getAddress());
        holder.cinemaformat.setText(String.join(", ", cinema.getFormat()));

        Glide.with(holder.itemView.getContext())
                .load(cinema.getUrlImage())
                .into(holder.cinemaImage);

    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

    public static class cinemaViewHolder extends RecyclerView.ViewHolder {
        ImageView cinemaImage;
        TextView cinemaName;
        TextView cinemaAddress;
        TextView cinemaformat;

        public cinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            cinemaImage = itemView.findViewById(R.id.img_cinema);
            cinemaName = itemView.findViewById(R.id.txt_titleCinema);
            cinemaAddress = itemView.findViewById(R.id.txt_address);
            cinemaformat = itemView.findViewById(R.id.txt_formats);
        }
    }

}
