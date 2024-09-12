package com.app.cinerma.design.peliculas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.cinerma.design.peliculas.adapters.PeliculaAdapter;
import com.app.cinerma.databinding.FragmentMainPeliculasBinding;
import com.app.cinerma.design.peliculas.entities.Pelicula;

import java.util.ArrayList;
import java.util.List;


public class MainPeliculasFragment extends Fragment {


    // Utilizamos View Binding para acceder a las vistas
    private FragmentMainPeliculasBinding binding;  // Usa el binding correcto

    public MainPeliculasFragment() {
        // Constructor vacío requerido
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflar el layout del fragmento usando View Binding
        binding = FragmentMainPeliculasBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Crear una lista de datos de ejemplo
        List<Pelicula> peliculaItems = new ArrayList<>();
        peliculaItems.add(new Pelicula("El Señor de los Anillos", "Historicos de los incas", "Romantica", 2023));
        peliculaItems.add(new Pelicula("Caminos de Incas", "Historicos de los incas", "Drama", 2021));
        peliculaItems.add(new Pelicula("Historias del Tahuantinsuyo", "Historicos de los incas", "Aventura", 2022));
        peliculaItems.add(new Pelicula("Los Misterios del Cuzco", "Historicos de los incas", "Suspenso", 2020));

        // Configurar el ViewPager2 con el adaptador
        PeliculaAdapter adapter = new PeliculaAdapter(peliculaItems);
        binding.fragmentContainerPeliculas.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Evitar pérdidas de memoria
    }
}
