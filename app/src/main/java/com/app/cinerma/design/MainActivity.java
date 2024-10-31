package com.app.cinerma.design;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.app.cinerma.R;
import com.app.cinerma.databinding.ActivityMainBinding;
import com.app.cinerma.design.cines.frament.CinemaFragment;
import com.app.cinerma.design.peliculas.Frament.payment_summary_Fragment;
import com.app.cinerma.design.peliculas.MoviesFragment;
import com.app.cinerma.design.start.MainHomeFragment;
import com.app.cinerma.dulceria.SnackFragment;
import com.app.cinerma.login.activities.InicioSesionActivity;

public class MainActivity extends AppCompatActivity {

    //variable para manejar la vinculacion de vistas
    ActivityMainBinding bilding;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bilding = ActivityMainBinding.inflate(getLayoutInflater());

        // Initialize Firebase
        //FirebaseApp.initializeApp(this);



        setContentView(bilding.getRoot());
        replaceFragment(new MainHomeFragment());
        bilding.btnNavegation.setOnItemSelectedListener(item -> {
            if(item.getItemId()== R.id.homeM){
                replaceFragment(new MainHomeFragment());
            }
            if(item.getItemId()==R.id.moviesM){
                replaceFragment(new MoviesFragment());
            }
            if(item.getItemId()==R.id.cinemaM){
                replaceFragment(new CinemaFragment() );
            }
            if(item.getItemId()==R.id.foodM){
                replaceFragment(new SnackFragment());
            }
            return true;
        });

        /*bilding.buscadorSearchMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BuscadorMain.class);
                startActivity(intent);
            }
        });*/

        //button que nos redirige al inicion de sesion
        bilding.btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, InicioSesionActivity.class);
                startActivity(intent);
            }
        });

    }

    public void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransition = fragmentManager.beginTransaction();
        fragmentTransition.replace(R.id.fragment_container_view,fragment);
        fragmentTransition.commit();
    }


}