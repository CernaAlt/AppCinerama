package com.app.cinerma.design.peliculas.services;

import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.entities.MovieCard;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;



public interface MovieApi {
    //Vamos a solicitar datos a la api metodo "get"
    @GET("Movie") // Endpoint de tu API
    Call<List<MovieCard>> getMovies();
}
