package com.app.cinerma.dulceria;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface SnackApi {

    /*
    //Obtener la lista de dulceria de la API
    @GET("Dulceria")
    Call<List<Dulceria>> getDulceria();
    */

    /******************************************************************/
    /**************************Paginacion**************************************/
    @GET("Dulceria")
    Call<List<Dulceria>> getDulceria(@Query("page") int page, @Query("limit") int limit);

}
