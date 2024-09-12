package com.app.cinerma.design.peliculas;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.cinerma.R;
import com.google.android.material.tabs.TabLayout;

public class activity_pelicula_show extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pelicula_show);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        //Recibir los datos de las peliculas
        //Obtener los datos enviados desde Intent
        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String genero = getIntent().getStringExtra("genero");
        int year = getIntent().getIntExtra("year", 0); // "year" debe coincidir exactamente

        //Referenciar los TestViews en el layout de la activida
        TextView textViewDescription = findViewById(R.id.textViewDescription);
        TextView textViewGenero = findViewById(R.id.textViewGenero);
        TextView textViewYear = findViewById(R.id.textViewYear);

        //01:Referenciar los TestViews en el layout de la actividad
        TextView textViewTitle1=findViewById(R.id.textViewTitle1);
        // Referenciar el TabLayout en el layout de la actividad
        TabLayout tabDescription = findViewById(R.id.tab_peliculas);

        // Agregar una nueva pestaña al TabLayout y asignarle un texto
        TabLayout.Tab tab = tabDescription.newTab();
        tab.setText("Información General"); // Puedes cambiar este texto
        tabDescription.addTab(tab);




        //configuracion de los textViews con los datos recibidos.
        textViewDescription.setText(description);
        textViewGenero.setText(genero);
        textViewYear.setText(String.valueOf(year));

        //01: configuracion de los textViews con los datos recibidos.
        textViewTitle1.setText(title);


    }
}