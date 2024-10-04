package com.app.cinerma.design.peliculas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.Frament.SynopsisFragment;
import com.app.cinerma.design.peliculas.adapters.MovieDetailPagerAdapter;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.services.MovieApi;
import com.app.cinerma.login.activities.InicioSesionActivity;
import com.app.cinerma.login.activities.RegisterActivity;
import com.app.cinerma.network.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movie_detailactivity extends AppCompatActivity {

    //Declarar variables
    private TextView txtStatus, peliculaTitle, generoHoraEdad, peliculaSinopsis;
    private ImageView urlImagenDetail;
    private TabLayout tabPeliculas;
    private ViewPager2 viewPager;
    private MovieApi movieApi;
    private Button buttonVer; // Nueva variable para el botón

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.movie_detail_activity);



        initViews();

        // Configurar el adaptador del ViewPager
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        // Conectar el TabLayout con el ViewPager
        new TabLayoutMediator(tabPeliculas, viewPager,
                (tab, position) -> {
                    switch (position) {
                        case 0:
                            tab.setText("Otros");
                            break;
                        case 1:
                            tab.setText("Cines");
                            break;
                    }
                }).attach();

        setupMovieApi();

        int movieId = getIntent().getIntExtra("MOVIE_ID", -1);
        if (movieId != -1) {
            fetchMovieDetails(movieId);
        } else {
            Toast.makeText(this, "Error: No se pudo obtener el ID de la película", Toast.LENGTH_SHORT).show();
        }
    }

    private void initViews() {
        txtStatus = findViewById(R.id.txtStatus);
        peliculaTitle = findViewById(R.id.pelicula_title);
        generoHoraEdad = findViewById(R.id.genero_hora_edad);
        urlImagenDetail = findViewById(R.id.urlImagenDetail);
        peliculaSinopsis = findViewById(R.id.pelicula_sinopsis); // Nueva variable para la sinopsis

        tabPeliculas = findViewById(R.id.tab_peliculas);
        viewPager = findViewById(R.id.fragment_container_peliculas_detail);

        // inicializer el buttón "Ver"
        buttonVer = findViewById(R.id.btn_ver); // Asegúrate de que el ID sea correcto

        //Agregamos la logica para el ir a otra actividad
        buttonVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(movie_detailactivity.this, buys01Activity.class));
            }
        });
    }



    private void setupMovieApi() {
        movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);
    }

    private void fetchMovieDetails(int movieId) {
        txtStatus.setText("Cargando...");

        Call<List<Movie>> call = movieApi.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Movie selectedMovie = null;
                    for (Movie movie : response.body()) {
                        if (movie.getId() == movieId) {
                            selectedMovie = movie;
                            break;
                        }
                    }
                    if (selectedMovie != null) {
                        updateUI(selectedMovie);
                    } else {
                        Toast.makeText(movie_detailactivity.this, "Película no encontrada", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(movie_detailactivity.this, "Error al cargar los datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(movie_detailactivity.this, "Error de red: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateUI(Movie movie) {
        txtStatus.setText("Cargado");
        peliculaTitle.setText(movie.getTitle());
        generoHoraEdad.setText(String.format("%s / %s / %s", movie.getGenreMovie(), movie.getDurationMovie(), movie.getAge()));
        peliculaSinopsis.setText(movie.getSinopsis()); // Actualizar la sinopsis
        Glide.with(this).load(movie.getUrlImagenDetail()).into(urlImagenDetail);
    }
}
