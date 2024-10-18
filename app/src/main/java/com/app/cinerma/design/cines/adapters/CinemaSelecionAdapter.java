package com.app.cinerma.design.cines.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.Cinema;
import java.util.List;

public class CinemaSelecionAdapter extends RecyclerView.Adapter<CinemaSelecionAdapter.CinemaViewHolder> {

    private List<Cinema> cinemaList;

    public CinemaSelecionAdapter(List<Cinema> cinemaList) {
        this.cinemaList = cinemaList;
    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itms_cinemas_selection, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        Cinema cinema = cinemaList.get(position);
        holder.txtCineTitle.setText(cinema.getName());

        // Bind formats
        holder.idiomasCine.removeAllViews();
        if (cinema.getFormat() != null) {
            for (String format : cinema.getFormat()) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(format);
                textView.setTextSize(14);
                textView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                textView.setPadding(4, 4, 4, 4);
                holder.idiomasCine.addView(textView);
            }
        }

        // Bind hours
        holder.horariosCine.removeAllViews();
        if (cinema.getHoras() != null) {
            for (String hour : cinema.getHoras()) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(hour);
                textView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                textView.setTextSize(14);
                textView.setPadding(4, 4, 4, 4);
                holder.horariosCine.addView(textView);
            }
        }
    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

    public static class CinemaViewHolder extends RecyclerView.ViewHolder {
        TextView txtCineTitle;
        LinearLayout idiomasCine;
        GridLayout horariosCine;

        public CinemaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCineTitle = itemView.findViewById(R.id.txt_cineTitle);
            idiomasCine = itemView.findViewById(R.id.idiomas_cine);
            horariosCine = itemView.findViewById(R.id.horarios_cine);
        }
    }
}