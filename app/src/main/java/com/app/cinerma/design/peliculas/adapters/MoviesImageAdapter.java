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
import com.bumptech.glide.Glide;
import java.util.List;
import com.app.cinerma.design.peliculas.entities.Movie;



public class MoviesImageAdapter extends RecyclerView.Adapter<MoviesImageAdapter.ViewHolder>{
    private List<Movie> movies;
    private Context context;
    public MoviesImageAdapter(List<Movie> movies,Context context) {
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
        Movie movie = movies.get(position);
        // Usar una biblioteca como Glide o Picasso para cargar la imagen
        Glide.with(holder.itemView.getContext())
                .load(movie.getImageUrl())
                .into(holder.imageView);

        // Configurar el OnClickListener para el botón
        holder.viewButton.setOnClickListener(v -> {
            Intent intent = new Intent(context, movie_detailactivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        View viewButton;

        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.movie_image);
            viewButton = itemView.findViewById(R.id.view_button);
        }
    }
}
