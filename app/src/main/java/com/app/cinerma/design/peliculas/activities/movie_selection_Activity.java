package com.app.cinerma.design.peliculas.activities;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.Frament.butaca_selection_Fragment;

public class movie_selection_Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);


        //Mostrar el framento de butaca_selection llamos al metodo
        mostrarButacaSelection();

    }




    //Metodo para mostrar el fragmento de butaca_selection
    public void mostrarButacaSelection(){
        getSupportFragmentManager().beginTransaction()  //recuperamos el fragmenManager para interactuar con los fragments de la actividad
                .replace(R.id.fragment_container, new butaca_selection_Fragment()) //reemplazamos el fragmento actual por el fragmento de butaca_selection
                .commit(); //confirmamos el cambio
    }
}