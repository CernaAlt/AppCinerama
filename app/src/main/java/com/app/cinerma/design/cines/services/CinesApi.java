package com.app.cinerma.design.cines.services;

import com.app.cinerma.design.cines.entities.Cinema;
import com.app.cinerma.design.cines.entities.Cines;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface CinesApi {
    @GET("/cines")
    Call<List<Cines>> getCines();
}
