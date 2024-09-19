package com.app.cinerma.design.cines.services;

import com.app.cinerma.design.cines.entities.Cine;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ICineApi {
    @GET("/cinemas")
    Call<List<Cine>> getAll();

    @GET("/cinemas")
    Call<List<Cine>> getAll(@Query("status") String status);

    @GET("/cinemas/{id}")
    Call<Cine> find(@Path("id") int id);

    @POST("/cinemas")
    Call<Cine> create(@Body Cine contact);

    @PUT("/cinemas/{id}")
    Call <Cine> update(@Path("id") int id, @Body Cine movie);

    @DELETE("/cinemas/{id}")
    Call <Void> delete(@Path("id") int id);
    @GET("/cinemas")
    Call<List<Cine>> getCinesByIds(@Query("ids") String ids);
}
