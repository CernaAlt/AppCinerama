package com.app.cinerma.design.cines.services;

import com.app.cinerma.design.cines.entities.Cine;
import com.app.cinerma.design.cines.entities.City;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICityApi {

    @GET("/cities")
    Call<List<City>> getAll();
    @GET("/cities")
    Call<List<City>> getAll(@Query("status") String status);

    @GET("/cities/{id}")
    Call<City> find(@Path("id") int id);

    @POST("/cities")
    Call<City> create(@Body City contact);

    @PUT("/cities/{id}")
    Call <City> update(@Path("id") int id, @Body City movie);

    @DELETE("/cities/{id}")
    Call <Void> delete(@Path("id") int id);
    @GET("/cities")
    Call<List<City>> getCinesByIds(@Query("ids") String ids);
}
