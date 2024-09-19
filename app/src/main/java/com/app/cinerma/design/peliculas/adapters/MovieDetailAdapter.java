package com.app.cinerma.design.peliculas.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.Movie;

import java.util.List;

import com.bumptech.glide.Glide;

public class MovieDetailAdapter extends RecyclerView.Adapter<MovieDetailAdapter.ViewHolder> {

    private List<Movie> movies;

    public MovieDetailAdapter(List<Movie> movies) {
        this.movies = movies;
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_detail_activity, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.textViewStatus.setText(movie.getStatus().get(0)); // Asumiendo que status es una lista
        holder.textViewTitle.setText(movie.getTitle());

        // Configurar el texto de género, duración y edad
        String genreDurationAge = movie.getGenreMovie() + " | " +
                movie.getDurationMovie() + " | " +
                movie.getAge();
        holder.textViewGenreDurationAge.setText(genreDurationAge);

        // Cargar la imagen usando Glide (asegúrate de tener Glide en tus dependencias)
        Glide.with(holder.itemView.getContext())
                .load(movie.getUrlImagenDetail())
                .into(holder.imageViewDetail);


    }



    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewStatus;
        final TextView textViewTitle;
        final TextView textViewGenreDurationAge;
        final ImageView imageViewDetail;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewStatus = itemView.findViewById(R.id.txtStatus);
            textViewTitle = itemView.findViewById(R.id.pelicula_title);
            textViewGenreDurationAge = itemView.findViewById(R.id.genero_hora_edad);
            imageViewDetail = itemView.findViewById(R.id.urlImagenDetail);
        }
    }
}
