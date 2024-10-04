package com.app.cinerma.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.app.cinerma.R;
import com.app.cinerma.design.MainActivity;
import com.app.cinerma.design.peliculas.MoviesFragment;
import com.app.cinerma.login.Domain.User;
import com.app.cinerma.login.adapters.UserAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class loginActivity extends AppCompatActivity {

    //Variables
    Button btn_add;
    //Variables para mostrar datos
    RecyclerView mRecycler;
    UserAdapter uAdaper;
    FirebaseFirestore mFirestore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);



        btn_add=findViewById(R.id.registerButton);
        //creamos la accion para ir a la pantalla crateUser
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginActivity.this, CreateUserActivity.class));
            }
        });


        //Mostramos los datos en nuestro reclerView
        mFirestore=FirebaseFirestore.getInstance();
        mRecycler=findViewById(R.id.rV_showData);
        //llamos nuevamente
        mRecycler.setLayoutManager(new LinearLayoutManager(this));
        //
        Query q=mFirestore.collection("User");

        FirestoreRecyclerOptions<User> FirestoreRecyclerOptions=
                new FirestoreRecyclerOptions.Builder<User>().setQuery(q,User.class).build();

        uAdaper=new UserAdapter(FirestoreRecyclerOptions,this);
        uAdaper.notifyDataSetChanged();
        mRecycler.setAdapter(uAdaper);
    }

    @Override
    protected void onStart(){
        super.onStart();
        uAdaper.startListening();
    }

    @Override
    protected void onStop(){
        super.onStop();
        uAdaper.stopListening();
    }
}