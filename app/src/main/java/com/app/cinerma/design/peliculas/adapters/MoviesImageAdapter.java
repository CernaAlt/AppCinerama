package com.app.cinerma.design.peliculas.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;
import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.activities.movie_detailactivity;
import com.app.cinerma.design.peliculas.entities.Pelicula;
import com.bumptech.glide.Glide;
import java.util.List;
import com.google.firebase.firestore.FirebaseFirestore;


public class MoviesImageAdapter extends RecyclerView.Adapter<MoviesImageAdapter.ViewHolder>{
    //Declaramos las variables
    List<Pelicula> movies;
    Context context;
    FirebaseFirestore firestore;

    //
    public MoviesImageAdapter(List<Pelicula> movies, Context context) {
        this.movies = movies;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pelicula,
                parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pelicula movie = movies.get(position);

        // Cargar la imagen usando Glide o Picasso
        Glide.with(context)
                .load(movie.getUrl())  // Asegúrate de que el campo `url` existe
                .into(holder.imageView);

        // Configurar el click para ver detalles de la película
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, movie_detailactivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());  // Pasar el ID de la película
            context.startActivity(intent);
        });
    }



    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_image);
        }
    }
}
