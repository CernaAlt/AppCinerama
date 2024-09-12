package com.app.cinerma.design.peliculas.adapters;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.MovieCard;
import com.bumptech.glide.Glide;
import java.util.List;
import com.app.cinerma.design.peliculas.entities.Movie;



public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>{

    private List<MovieCard> movieList;

    public MoviesAdapter(List<MovieCard> movieList) {
        this.movieList = movieList;
    }


    //
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelicula, parent, false);
        return new MovieViewHolder(view);
    }


    //Metodo hacer toda la logica
    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieCard movie = movieList.get(position);
        //holder.title.setText(movie.getImageUrl());

        // Usar Glide para cargar la imagen
        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .into(holder.image);

        // Configurar el evento de clic para "Ver Horarios"
        holder.viewButton.setOnClickListener(v -> {
            // Aquí puedes agregar la lógica para navegar a otra actividad o fragmento
        });
    }

    //
    @Override
    public int getItemCount() {
        return movieList.size();
    }

    static class MovieViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        View viewButton;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.movie_image);
            viewButton = itemView.findViewById(R.id.view_button);
        }
    }
}
