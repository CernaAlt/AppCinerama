package com.app.cinerma.login.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.activities.movie_detailactivity;
import com.app.cinerma.design.peliculas.activities.movie_selection_Activity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class InicioSesionActivity extends AppCompatActivity {

    private TextInputEditText emailEditText;
    private TextInputEditText passwordEditText;
    private FirebaseAuth mAuth;
    private Button btnIngresar;
    private Button btnUnete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_inicio_sesion);

        mAuth = FirebaseAuth.getInstance();

        emailEditText = findViewById(R.id.txt_emailUser);
        passwordEditText = findViewById(R.id.txt_passwordUser);
        btnIngresar = findViewById(R.id.btn_ingresar);
        btnUnete = findViewById(R.id.btn_addUser);

        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailUser = emailEditText.getText().toString().trim();
                String passwordUser = passwordEditText.getText().toString().trim();

                if (emailUser.isEmpty() || passwordUser.isEmpty()) {
                    Toast.makeText(InicioSesionActivity.this, "Por favor, ingrese todos los datos", Toast.LENGTH_SHORT).show();
                } else {
                    loginUser(emailUser, passwordUser);
                }
            }
        });

        btnUnete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InicioSesionActivity.this, CreateUserActivity.class));
            }
        });
    }

    private void loginUser(String emailUser, String passwordUser) {
        mAuth.signInWithEmailAndPassword(emailUser, passwordUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(InicioSesionActivity.this, movie_detailactivity.class));
                    finish();
                    Toast.makeText(InicioSesionActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InicioSesionActivity.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(InicioSesionActivity.this, "Error al iniciar sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}