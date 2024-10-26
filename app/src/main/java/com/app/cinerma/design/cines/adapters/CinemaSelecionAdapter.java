package com.app.cinerma.design.cines.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.CineHorario;
import com.app.cinerma.design.peliculas.activities.movie_selection_Activity;
import com.app.cinerma.design.peliculas.entities.Movie;

import java.util.List;

public class CinemaSelecionAdapter extends RecyclerView.Adapter<CinemaSelecionAdapter.CinemaViewHolder> {

    private List<CineHorario> movieHorarios;
    private Movie currentMovie;
    private Context context;


    public CinemaSelecionAdapter(List<CineHorario> cineHorariosList) {
        this.movieHorarios = cineHorariosList;

    }

    @NonNull
    @Override
    public CinemaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itms_cinemas_selection, parent, false);
        return new CinemaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CinemaViewHolder holder, int position) {
        CineHorario h = movieHorarios.get(position);
        holder.txtCineTitle.setText(h.getName());

        //Bind formats
        /*holder.idiomasCine.removeAllViews();
        if (cinema.getFormat() != null) {
            for (String format : cinema.getFormat()) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(format);
                textView.setTextSize(14);
                textView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                textView.setPadding(4, 4, 4, 4);
                holder.idiomasCine.addView(textView);
            }
        }*/


        // Bind hours
        bindHours(holder, h);

        //set initial visibility
        visibilityDataCine(holder);
    }

    // Horarios de las peliculas
    public void bindHours(CinemaViewHolder holder, CineHorario cineHorarios) {
        holder.horariosCine.removeAllViews();
        if (cineHorarios.getHorarios() != null) {
            for (String hour : cineHorarios.getHorarios()) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(hour);
                textView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                textView.setTextSize(20);


                // Agregar el click listener al horario
                textView.setOnClickListener(v -> {
                    Intent intent = new Intent(context, movie_selection_Activity.class);
                    // Pasar los datos de la película
                    intent.putExtra("MOVIE_ID",currentMovie.getId() );
                    // Pasar los datos del cine y horario
                    intent.putExtra("cineHorario", (CharSequence) cineHorarios);
                    intent.putExtra("horarioSeleccionado", hour);
                    context.startActivity(intent);
                });

                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0;
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
                params.setMargins(0, 0, 8, 0);
                textView.setLayoutParams(params);
                holder.horariosCine.addView(textView);
            }
        }
    }

    /*public void bindHours(CinemaViewHolder holder, Cinema cinema){
        // Bind hours
        holder.horariosCine.removeAllViews();
        // If there are hours
        if (cinema.getHoras() != null) {
            // For each hour
            for (String hour : cinema.getHoras()) {
                TextView textView = new TextView(holder.itemView.getContext());
                textView.setText(hour);
                textView.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.white));
                textView.setTextSize(20);
                // Set layout parameters for each TextView
                GridLayout.LayoutParams params = new GridLayout.LayoutParams();
                params.width = 0; // Use 0 to make the width flexible
                params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f); // Take up one column
                params.setMargins(0, 0, 8, 0); // Add margin to the end of each TextView
                textView.setLayoutParams(params);

                //
                textView.setOnClickListener(v->{
                    Intent intent=new Intent(holder.itemView.getContext(), movie_selection_Activity.class);
                    intent.putExtra("cinema", cinema.getName());
                    intent.putExtra("city", cinema.getCity());
                    intent.putExtra("formato", cinema.getFormat().toString());
                    intent.putExtra("address", cinema.getAddress());
                    intent.putExtra("hora", hour);

                    holder.itemView.getContext().startActivity(intent);

                });

                // Add the textView to the GridLayout
                holder.horariosCine.addView(textView);
            }
        }
    }*/

    public void visibilityDataCine(CinemaViewHolder holder) {
        holder.idiomasCine.setVisibility(View.GONE);
        holder.horariosCine.setVisibility(View.GONE);
        holder.txtCineTitle.setOnClickListener(v -> {
            if (holder.idiomasCine.getVisibility() == View.GONE) {
                holder.idiomasCine.setVisibility(View.VISIBLE);
                holder.horariosCine.setVisibility(View.VISIBLE);
            } else {
                holder.idiomasCine.setVisibility(View.GONE);
                holder.horariosCine.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public int getItemCount() {
        int itemCount = movieHorarios != null ? movieHorarios.size() : 0;
        Log.d("CinemaSelecionAdapter", "getItemCount: " + itemCount);
        return itemCount;
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