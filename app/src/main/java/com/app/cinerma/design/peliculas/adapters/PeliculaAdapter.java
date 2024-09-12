package com.app.cinerma.design.peliculas.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.Pelicula;

import java.util.List;

import com.app.cinerma.design.peliculas.activity_pelicula_show;

public class PeliculaAdapter extends RecyclerView.Adapter<PeliculaAdapter.ViewHolder> {

    private final List<Pelicula> peliculas;

    public PeliculaAdapter(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pelicula, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Pelicula item = peliculas.get(position);
        //holder.textViewTitle.setText(item.getTitle());
        holder.textViewDescription.setText(item.getDescription());
        holder.textViewGenero.setText(item.getGenero());
        holder.textViewYear.setText(String.valueOf(item.getYear()));

        // Configurar el clic del elemento
        holder.itemView.setOnClickListener(view -> {
            // Crear un Intent para iniciar la nueva actividad
            Intent intent = new Intent(view.getContext(), activity_pelicula_show.class);

            // Agregar los datos al Intent
            intent.putExtra("title", item.getTitle());
            intent.putExtra("description", item.getDescription());
            intent.putExtra("genero", item.getGenero());
            intent.putExtra("year", item.getYear()); // Aquí puedes pasar directamente el entero

            // Iniciar la nueva actividad
            view.getContext().startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return peliculas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //final TextView textViewTitle;
        final TextView textViewDescription;
        final TextView textViewGenero;
        final TextView textViewYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDescription = itemView.findViewById(R.id.textViewDescription);
            textViewGenero = itemView.findViewById(R.id.textViewGenero);
            textViewYear=itemView.findViewById(R.id.textViewYear);
        }
    }
}
