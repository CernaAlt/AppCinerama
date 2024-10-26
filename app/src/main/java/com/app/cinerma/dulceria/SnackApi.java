package com.app.cinerma.dulceria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface SnackApi {
    @GET("Dulceria")
    Call<List<Dulceria>> getDulceria();
}
