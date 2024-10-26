package com.app.cinerma.design.peliculas.services;

import com.app.cinerma.design.peliculas.entities.Movie;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;


public interface MovieApi {
    //Vamos a solicitar datos a la api metodo "get"
    @GET("/Movie") // Endpoint de tu API
    Call<List<Movie>> getMovies();

    @GET("/Movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") String id);
}
