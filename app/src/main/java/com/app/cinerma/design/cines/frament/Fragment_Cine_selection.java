package com.app.cinerma.design.cines.frament;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.app.cinerma.R;
import com.app.cinerma.design.cines.adapters.CinemaSelecionAdapter;
import com.app.cinerma.design.cines.entities.CineHorario;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.services.MovieApi;
import com.app.cinerma.network.RetrofitClient;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Fragment_Cine_selection extends Fragment {

    //private FirebaseFirestore firestore;
    //private List<Cinema> cinemaList;

    private RecyclerView cinemaRecyclerView;
    private CinemaSelecionAdapter cinemaAdapter;
    private List<CineHorario> cinemaList;
    private MovieApi movieApi;
    Spinner spinnerEleccionDia;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__cine_selection, container, false);

        //firestore = FirebaseFirestore.getInstance();
        cinemaRecyclerView = view.findViewById(R.id.rv_cinemas);
        cinemaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cinemaList = new ArrayList<>();
        cinemaAdapter = new CinemaSelecionAdapter(cinemaList);
        cinemaRecyclerView.setAdapter(cinemaAdapter);

        //Spinner que nos va a permitir elegir el dia para ver las peliculas
        spinnerEleccionDia = view.findViewById(R.id.spinner_eleccionDia);
        daysSpiner();



        //Motodo que nos permite obtener los cines de la base de datos mockApi usando nuestro servicio MovieApi
        fetchCinemas();
        return view;
    }

    //funcion que nos permite elegir el dia para ver las peliculas
    private void daysSpiner(){
        // Get current date and next day's date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String today = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrow = dateFormat.format(calendar.getTime());

        // Create a list of dates
        List<String> dates = new ArrayList<>();
        dates.add(today);
        dates.add(tomorrow);

        // Set up the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.select_dialog_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerEleccionDia.setAdapter(adapter);
    }

    //metodo que nos permite obtener los cines de la base de datos mockApi
    private void fetchCinemas() {

        movieApi = RetrofitClient.getRetrofitInstance().create(MovieApi.class);

        Call<List<Movie>> call = movieApi.getMovies();
        call.enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> peliculas = response.body();

                    // Suponiendo que solo queremos la primera película para este ejemplo
                    Movie movie = peliculas.get(1);

                    // Mostrar la lista de cines y horarios de la película
                    fetchCineHorariosFromMovie(movie);

                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchCineHorariosFromMovie(Movie movie) {
        // Limpiar la lista y notificar al adaptador
        cinemaList.clear();
        cinemaAdapter.notifyDataSetChanged();

        // Obtenemos la lista de cinesHorarios desde la entidad Movie
        List<CineHorario> cineHorariosList = movie.getCineHorarios();

        if (cineHorariosList != null && !cineHorariosList.isEmpty()) {
            // Agregar los datos al adaptador y notificar cambios
            this.cinemaList.addAll(cineHorariosList);
            cinemaAdapter.notifyDataSetChanged();
        } else {
            Toast.makeText(getContext(), "No se encontraron horarios de cines.", Toast.LENGTH_SHORT).show();
        }
    }



    /*private void fetchCinemas() {
        cinemaList.clear();
        cinemaAdapter.notifyDataSetChanged();

        firestore.collection("cines")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(getContext(), "Error al cargar los cines: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        cinemaList.clear();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Cinema cinema = document.toObject(Cinema.class);
                            cinemaList.add(cinema);
                        }

                        cinemaAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No se encontraron cines.", Toast.LENGTH_SHORT).show();
                    }
                });
    }*/
}