package com.app.cinerma.design.peliculas;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.adapters.MoviesAdapter;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.entities.MovieCard;
import com.app.cinerma.design.peliculas.services.MovieApi;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import com.app.cinerma.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    // Adaptador personalizado para la lista de películas que construimos
    private MoviesAdapter moviesAdapter;

    //Usamos nuetro servicio
    private MovieApi movieApi;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar(crear) el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializar Retrofit y la interfaz API
        movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);


        // Llamar a la API para obtener las imagenes de cada una
        fetchMovies();

        /*
        // Crear una lista de películas (esto podría venir de una base de datos o API)
        List<Movie> movieList = new ArrayList<>();
        //Agregamos valores a decha lista
        movieList.add(new Movie(1, "sinson","https://www.example.com/image1.jpg","https://www.example.com/image1.jpg","ssss","romantico",1,"no","ssss","dddd","90 minutos"));
        movieList.add(new Movie("Beetlejuice", "https://www.example.com/image2.jpg"));
        movieList.add(new Movie("Masacre en el Tren", "https://www.example.com/image3.jpg"));
        movieList.add(new Movie("Lluvia Ácida", "https://www.example.com/image4.jpg"));
        movieList.add(new Movie("Masacre en el Tren", "https://www.example.com/image3.jpg"));
        movieList.add(new Movie("Lluvia Ácida", "https://www.example.com/image4.jpg"));

        // Configurar el adaptador y la lista de películas
        moviesAdapter = new MoviesAdapter(movieList);
        recyclerView.setAdapter(moviesAdapter);
        */

        return view;
    }

    private void fetchMovies() {
        Call<List<MovieCard>> call = movieApi.getMovies();
        call.enqueue(new Callback<List<MovieCard>>() {
            @Override
            public void onResponse(@NonNull Call<List<MovieCard>> call, @NonNull Response<List<MovieCard>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<MovieCard> movies = response.body();
                    // Configurar el adaptador con los datos obtenidos
                    moviesAdapter = new MoviesAdapter(movies);
                    recyclerView.setAdapter(moviesAdapter);
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<MovieCard>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
