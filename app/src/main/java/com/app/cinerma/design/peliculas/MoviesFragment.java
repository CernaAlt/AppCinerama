package com.app.cinerma.design.peliculas;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.adapters.MoviesImageAdapter;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.services.MovieApi;

import org.jetbrains.annotations.Nullable;

import java.util.List;

import com.app.cinerma.network.RetrofitClient;
import com.google.firebase.firestore.FirebaseFirestore;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {
    // RecyclerView para mostrar las películas
    private RecyclerView recyclerView;

    // Adaptador para las películas mostrar en el RecyclerView las imágenes
    private MoviesImageAdapter moviesAdapter;

    //cambios nuevos mockApi
    private List<Movie> peliculas;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));



        // Obtener las películas
        fetchMovies();
        return view;
    }

    private void fetchMovies() {
       //Inicialimos movieApi
        MovieApi movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);

        // Acceder a la API de películas
        Call<List<Movie>> call = movieApi.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    peliculas = response.body();
                    //Configurar el adaptador con la lista de películas
                    moviesAdapter = new MoviesImageAdapter(peliculas, requireContext());
                    recyclerView.setAdapter(moviesAdapter);
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                // Manejo de errores en el telefono
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                //Imprimimos en la consola el error
                Log.e("RetrofitError", t.getMessage(), t);

            }
        });
    }
}
