package com.app.cinerma.design.peliculas.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.adapters.MovieDetailPagerAdapter;
import com.app.cinerma.design.peliculas.entities.CineHorarios;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.entities.Pelicula;
import com.app.cinerma.design.peliculas.services.MovieApi;
import com.app.cinerma.network.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movie_detailactivity extends AppCompatActivity {

    private FirebaseFirestore firestore;
    private TextView peliculaTitle,
            generoHoraEdad, peliculaSinopsis,directorMovie,disponibleMovie;
    private ImageView url, urlTrailer;
    private ChipGroup idiomaPelicula;
    private TabLayout tabPeliculas;
    private ViewPager2 viewPager;
    private List<CineHorarios> cinesHorarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        // Inicializar Firestore
        firestore = FirebaseFirestore.getInstance();

        // Inicializar vistas
        initViews();

        // Inicializar la lista
        cinesHorarios = new ArrayList<>();

        // Obtener ID de la película desde el Intent
        String movieId = getIntent().getStringExtra("MOVIE_ID");
        if (movieId != null) {
            fetchMovieDetails(movieId);
        } else {
            showErrorToast("Error: No se pudo obtener el ID de la película");
        }

        // Configurar el ViewPager
        setupViewPager();

    }

    // Método para inicializar las vistas
    private void initViews() {
        peliculaTitle = findViewById(R.id.pelicula_title);
        generoHoraEdad = findViewById(R.id.genero_hora_edad);
        urlTrailer = findViewById(R.id.txt_urlTrailer);
        peliculaSinopsis = findViewById(R.id.pelicula_sinopsis);
        url = findViewById(R.id.img_url);
        idiomaPelicula = findViewById(R.id.idioma_pelicula);
        directorMovie=findViewById(R.id.director_name);

        //disponible movie
        disponibleMovie=findViewById(R.id.disponible_content);

        //Configurar la pestaña de las peliculas
        tabPeliculas = findViewById(R.id.tab_peliculas);
        viewPager = findViewById(R.id.fragment_container_peliculas_detail);

        //inicializar el boton de ver
         Button buttonVer = findViewById(R.id.btn_ver);

        if (buttonVer!= null) {
            // Configura el listener del botón
            buttonVer.setOnClickListener(view -> {
                Intent intent = new Intent(movie_detailactivity.this, movie_selection_Activity.class);
                startActivity(intent);
            });
        } else {
            Log.e("Error", "El botón btnVer es nulo");
        }
    }

    private void fetchMovieDetails(String movieId) {
        if (firestore != null) {
            firestore.collection("peliculas").document(movieId).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            Pelicula pelicula = documentSnapshot.toObject(Pelicula.class);
                            if (pelicula != null) {
                                updateUI(pelicula);
                            } else {
                                showErrorToast("Película no encontrada");
                            }
                        } else {
                            showErrorToast("Película no encontrada");
                        }
                    })
                    .addOnFailureListener(e -> showErrorToast("Error al cargar los datos: " + e.getMessage()));
        } else {
            showErrorToast("Firestore no inicializado");
        }
    }

    private void updateUI(Pelicula pelicula) {
        peliculaTitle.setText(pelicula.getTitle());
        generoHoraEdad.setText(String.format("%s / %s / %s", pelicula.getGenre(), pelicula.getDurationMovie(), pelicula.getAge()));
        peliculaSinopsis.setText(pelicula.getSinopsis());
        directorMovie.setText(pelicula.getDirector());

        Glide.with(this).load(pelicula.getUrlTrailer()).into(urlTrailer);
        Glide.with(this).load(pelicula.getUrl()).into(url);

        // Actualizar los idiomas en los chips
        idiomaPelicula.removeAllViews();
        if (pelicula.getIdioma() != null) {
            for (String idioma : pelicula.getIdioma()) {
                Chip chip = new Chip(this);
                chip.setText(idioma);
                chip.setTextColor(getResources().getColor(R.color.white));
                chip.setChipBackgroundColorResource(R.color.black);
                idiomaPelicula.addView(chip);
            }
        }

        // Update the available movie formats
        List<String> disponibleList = pelicula.getDisponible();
        if (disponibleList != null) {
            String disponibleText = joinListToString(disponibleList);
            disponibleMovie.setText(disponibleText);
        }
    }

    private String joinListToString(List<String> list) {
        return list != null ? String.join(", ", list) : "";
    }

    private void showErrorToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    private void setupViewPager() {
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabPeliculas, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("La función perfecta para ti.");
            else tab.setText("Cines");
        }).attach();
    }
}