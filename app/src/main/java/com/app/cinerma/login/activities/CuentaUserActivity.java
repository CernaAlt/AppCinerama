package com.app.cinerma.login.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.cinerma.R;
import com.app.cinerma.login.Entities.User;
import com.app.cinerma.login.adapters.UserAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class CuentaUserActivity extends AppCompatActivity {


    //Variables para mostrar datos
    RecyclerView mRecycler;
    UserAdapter uAdaper;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_cuenta);


        // Inicializar Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        // Verificar si hay un usuario logueado
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Inicializar Firestore
            mFirestore = FirebaseFirestore.getInstance();
            mRecycler = findViewById(R.id.rV_showData);
            mRecycler.setLayoutManager(new LinearLayoutManager(this));

            // Consulta para obtener el usuario actual
            Query query = mFirestore.collection("User").whereEqualTo("userId", userId);

            // Configurar opciones para FirestoreRecyclerAdapter
            FirestoreRecyclerOptions<User> options = new FirestoreRecyclerOptions.Builder<User>()
                    .setQuery(query, User.class)
                    .build();

            // Inicializar el adaptador y configurarlo para el RecyclerView
            uAdaper = new UserAdapter(options, this);
            mRecycler.setAdapter(uAdaper);

        } else {
            // Mostrar mensaje si no hay usuario logueado
            Toast.makeText(this, "No se ha encontrado el ID del usuario. Por favor, inicia sesión.", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
        // Iniciar el adaptador solo si está inicializado
        if (uAdaper != null) {
            uAdaper.startListening();
        }
    }

    @Override
    protected void onStop(){
        super.onStop();
        // Detener el adaptador solo si está inicializado
        if (uAdaper != null) {
            uAdaper.stopListening();
        }
    }
}