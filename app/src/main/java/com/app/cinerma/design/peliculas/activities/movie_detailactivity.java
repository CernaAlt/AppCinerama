package com.app.cinerma.design.peliculas.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.adapters.CinemaSelecionAdapter;
import com.app.cinerma.design.cines.entities.CineHorario;
import com.app.cinerma.design.peliculas.adapters.MovieDetailPagerAdapter;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.services.MovieApi;
import com.app.cinerma.network.RetrofitClient;
import com.bumptech.glide.Glide;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class movie_detailactivity extends AppCompatActivity {

    //private FirebaseFirestore firestore;

    private TextView peliculaTitle,
            generoHoraEdad,
            peliculaSinopsis,
            directorMovie,
            disponibleMovie;

    private ImageView url;
    VideoView urlTrailer;
    private ChipGroup idiomaPelicula;
    private TabLayout tabPeliculas;
    private ViewPager2 viewPager;

    //Traemos el adapter de CinemaSelecionAdapter
    //private CinemaSelecionAdapter cinemaSelecionAdapter;
    //private List<CineHorarios> cinesHorarios;

    //private MovieDetailAdapter movieAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail_activity);

        // Inicializar Firestore
        //firestore = FirebaseFirestore.getInstance();

        //Inicialimos movieApi
        //MovieApi movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);

        // Inicializar vistas
        initViews();

        // Inicializar la lista
        //cinesHorarios = new ArrayList<>();

        // Obtener ID de la película desde el Intent
        /*String movieId = getIntent().getStringExtra("MOVIE_ID");
        if (movieId != null) {
            fetchMovieDetails(movieId);
        } else {
            showErrorToast("Error: No se pudo obtener el ID de la película");
        }*/


        // Obtener el ID de la pelicula pasado desde la actividad anterior
        String movieId = getIntent().getStringExtra("MOVIE_ID");
        //Llamar a la API para obtener los detalles de la película
        fetchMovieDetails(movieId);

        HorarioData();

        // Configurar el ViewPager
        setupViewPager();
    }

    private void HorarioData() {
        MovieApi movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);
        Call<List<Movie>> call = movieApi.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body();

                    for (Movie movie : movies) {
                        Log.d(TAG, "Movie Title: " + movie.getTitle());

                        // Accede a cineHorarios de cada película
                        List<CineHorario> cineHorarios = movie.getCineHorarios();
                        if (cineHorarios != null && !cineHorarios.isEmpty()) {
                            for (CineHorario horario : cineHorarios) {
                                Log.d(TAG, "Cine Name: " + horario.getName());
                                List<String> horarios = horario.getHorarios();
                                if (horarios != null && !horarios.isEmpty()) {
                                    for (String time : horarios) {
                                        Log.d(TAG, "Horario: " + time);
                                    }
                                } else {
                                    Log.d(TAG, "No horarios available for this cine.");
                                }
                            }
                        } else {
                            Log.d(TAG, "No cineHorarios available for this movie.");
                        }
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful or body is null");
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e(TAG, "Error fetching movies: " + t.getMessage());
            }
        });
    }


    // Método para inicializar las vistas
    private void initViews() {
        peliculaTitle = findViewById(R.id.pelicula_title);
        generoHoraEdad = findViewById(R.id.genero_hora_edad);
        urlTrailer = findViewById(R.id.video_Trailer);
        peliculaSinopsis = findViewById(R.id.pelicula_sinopsis);
        url = findViewById(R.id.img_url);
        idiomaPelicula = findViewById(R.id.idioma_pelicula);
        directorMovie = findViewById(R.id.director_name);
        disponibleMovie = findViewById(R.id.disponible_content);
        tabPeliculas = findViewById(R.id.tab_peliculas);
        viewPager = findViewById(R.id.fragment_container_peliculas_detail);

        Button buttonVer = findViewById(R.id.btn_ver);
        if (buttonVer != null) {
            buttonVer.setOnClickListener(view -> {
                Intent intent = new Intent(movie_detailactivity.this, movie_selection_Activity.class);
                startActivity(intent);
            });
        } else {
            Log.e("Error", "El botón btnVer es nulo");
        }
    }



    // Llamada a la API para obtener detalles de la película
    private void fetchMovieDetails(String movieId) {
        MovieApi movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);
        movieApi.getMovieDetails(movieId).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                if (response.isSuccessful()) {
                    Movie movie = response.body();
                    if (movie != null) {
                        updateUI(movie);

                        List<CineHorario> cineHorariosList = new ArrayList<>();
                        List<String> horarios = new ArrayList<>();
                        horarios.addAll(Arrays.asList("", "2", "3", "4"));
                        cineHorariosList.add(new CineHorario("1", horarios, "panel" )); // Agrega un horario de cine simulado
                        setupCineHorariosRecyclerView(cineHorariosList);

                       /* List<CineHorario> cineHorariosList = movie.getCineHorarios();
                        if (cineHorariosList != null) {
                            cineHorariosList.forEach(cineHorarios -> {
                                Log.d("fetchMovieDetails", "Cine: " + cineHorarios.getName());
                                cineHorarios.getHorarios().forEach(horario -> Log.d("fetchMovieDetails", "Horario: " + horario));
                            });
                            Log.d("fetchMovieDetails", "cineHorariosList no es nulo y tiene tamaño: " + cineHorariosList.size());
                            setupCineHorariosRecyclerView(cineHorariosList);
                        } else {
                            Log.d("fetchMovieDetails", "Respuesta de la API: " + response.body());

                            showErrorToast("No hay horarios de cine disponibles  00");
                        }*/
                    } else {
                        Log.e("fetchMovieDetails", "La respuesta de la película es nula.");
                        showErrorToast("Error: No se pudo obtener los detalles de la película");
                    }
                } else {
                    Log.e("fetchMovieDetails", "Respuesta no exitosa de la API.");
                    showErrorToast("Error: No se pudo obtener los detalles de la película");
                }

            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                showErrorToast("Error: " + t.getMessage());
            }
        });

    }

    private void setupCineHorariosRecyclerView(List<CineHorario> cineHorariosList) {
        RecyclerView recyclerView = findViewById(R.id.ryv_mostrarCine);
        if (cineHorariosList != null && !cineHorariosList.isEmpty()) {
            Log.d("SetupRecyclerView", "Tamaño de cineHorariosList: " + cineHorariosList.size());

            // Configura el adaptador y el LayoutManager solo si la lista no está vacía
            CinemaSelecionAdapter adapter = new CinemaSelecionAdapter(cineHorariosList);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            Log.e("SetupRecyclerView", "cineHorariosList está vacía o es nula.");
            showErrorToast("No hay horarios de cine disponibles");
        }
    }


    /*private void fetchMovieDetails(String movieId) {
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
    }*/

    private void updateUI(Movie movie) {
        peliculaTitle.setText(movie.getTitle());
        peliculaSinopsis.setText(movie.getSinopsis());
        directorMovie.setText(movie.getDirector());
        disponibleMovie.setText(String.join(", ", movie.getDisponible()));
        generoHoraEdad.setText(String.format("%s | %s min | %s", movie.getGenre(), movie.getDurationMovie(), movie.getAge()));

        //Glide.with(this).load(pelicula.getUrlTrailer()).into(urlTrailer);
        Glide.with(this).load(movie.getUrl()).into(url);

        // Actualizar los idiomas en los chips
        idiomaPelicula.removeAllViews();
        if (movie.getIdioma() != null) {
            for (String idioma : movie.getIdioma()) {
                Chip chip = new Chip(this);
                chip.setText(idioma);
                chip.setTextColor(getResources().getColor(R.color.white));
                chip.setChipBackgroundColorResource(R.color.black);
                idiomaPelicula.addView(chip);
            }
        }

        // Actualizar los formatos disponibles
        List<String> disponibleList = movie.getDisponible();
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

    //Mostramos los cines y horarios
    private void setupViewPager() {
        MovieDetailPagerAdapter pagerAdapter = new MovieDetailPagerAdapter(this);
        viewPager.setAdapter(pagerAdapter);

        new TabLayoutMediator(tabPeliculas, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("LA FUNCION PERFECTA PARA TI.");
        }).attach();
    }
}