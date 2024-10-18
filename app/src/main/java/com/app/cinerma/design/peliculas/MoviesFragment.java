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
import com.app.cinerma.design.peliculas.adapters.MoviesImageAdapter;
import com.app.cinerma.design.peliculas.entities.CineHorarios;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.entities.MovieCard;
import com.app.cinerma.design.peliculas.entities.Pelicula;
import com.app.cinerma.design.peliculas.services.MovieApi;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.cinerma.network.RetrofitClient;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesFragment extends Fragment {
    // RecyclerView para mostrar las películas
    private RecyclerView recyclerView;
    // Adaptador personalizado
    private MoviesImageAdapter moviesAdapter;

    // Lista de películas
    private List<Pelicula> peliculas;
    // Firebase Firestore
    private FirebaseFirestore firestore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Obtener las películas
        fetchMovies();

        return view;
    }


    private void fetchMovies() {
        // Acceder a la colección "peliculas" en Firestore
        firestore.collection("peliculas")
                .get()
                .addOnCompleteListener(task -> {
                    if (isAdded() && task.isSuccessful() && task.getResult() != null) { // Check if the fragment is still attached
                        peliculas = new ArrayList<>();
                        // Recorremos los documentos de la colección y los convertimos en objetos Pelicula
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Pelicula pelicula = document.toObject(Pelicula.class);
                            peliculas.add(pelicula);  // Añadimos a la lista
                        }
                        // Configuramos el adaptador con la lista de películas
                        moviesAdapter = new MoviesImageAdapter(peliculas, requireContext());
                        recyclerView.setAdapter(moviesAdapter);
                    } else if (isAdded()) {
                        Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    // Manejo de errores
                    if (isAdded()) {
                        Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
