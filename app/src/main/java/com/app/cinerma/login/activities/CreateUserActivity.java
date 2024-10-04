package com.app.cinerma.login.activities;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.app.cinerma.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity {

    //variables
    Button btnRegister;

    EditText nombre, correo, contrasena;

    //Creamos una variable privada
    private FirebaseFirestore mfirestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_user);

        this.setTitle("Create user");
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mfirestore=FirebaseFirestore.getInstance();

        //logic Edit in this activity
        String id =getIntent().getStringExtra("id_User");
        mfirestore=FirebaseFirestore.getInstance();



        //obtenemos los datos de nuestro xml
        nombre=findViewById(R.id.txt_nameUser);
        correo=findViewById(R.id.txt_emailUser);
        contrasena=findViewById(R.id.txt_passwordUser);
        btnRegister=findViewById(R.id.btn_addUser);

        /*Cuando todo es nulo vamos a insertar user*/
        if (id==null || id == ""){

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //obtener la informacion dentro de los txt
                    String nameUser=nombre.getText().toString().trim();
                    String emailUser=correo.getText().toString().trim();
                    String passwordUser=contrasena.getText().toString().trim();

                    if (nameUser.isEmpty() && emailUser.isEmpty() && passwordUser.isEmpty()){
                        Toast.makeText(CreateUserActivity.this,"please, enter an email and password",Toast.LENGTH_SHORT).show();
                    }else {
                        insertData(nameUser,emailUser,passwordUser);

                    }


                }
            });

        }else {
            //actulizamos el boton
            btnRegister.setText("Update");

            //si no es nulo ni vacio vamos actualizar
            getUser(id);

            btnRegister.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    //obtener la informacion dentro de los txt
                    String nameUser=nombre.getText().toString().trim();
                    String emailUser=correo.getText().toString().trim();
                    String passwordUser=contrasena.getText().toString().trim();


                    if (nameUser.isEmpty() && emailUser.isEmpty() && passwordUser.isEmpty()){
                        Toast.makeText(CreateUserActivity.this,"please, enter an email and password",Toast.LENGTH_SHORT).show();
                    }else {
                        updateUser(nameUser,emailUser,passwordUser,id);
                    }

                }
            });
        }
    }

    //Logic update user: Actulizamos Datos
    private void updateUser(String nameUser, String emailUser,String passwordUser,String id){


        //Enviar los datos
        Map<String,Object> map=new HashMap<>();
        map.put("name",nameUser);
        map.put("email",emailUser);
        map.put("password",passwordUser);

        mfirestore.collection("User").document(id).update(map).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(getApplicationContext(),"Data update",Toast.LENGTH_SHORT).show();
                finish();

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Error update data user",Toast.LENGTH_SHORT).show();
            }
        });

    }


    //logic post user: Insertamos datos
    private void insertData(String name,String email,String password){
        //Enviar los datos
        Map<String,Object> map=new HashMap<>();
        map.put("name",name);
        map.put("email",email);
        map.put("password",password);



        mfirestore.collection("User").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {

                Toast.makeText(getApplicationContext(),"create user ",Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                //esta parte se va ejecutar si hay algun error
                Toast.makeText(getApplicationContext(),"Error al register",Toast.LENGTH_SHORT).show();
            }
        });

    }

    //Logic get user: OBTENEMOS LA INFORMACION
    private void getUser(String id){
        mfirestore.collection("User").document(id).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                String nameUser=documentSnapshot.getString("name");
                String emailUser=documentSnapshot.getString("email");
                String passwordUser=documentSnapshot.getString("password");

                //
                nombre.setText(nameUser);
                correo.setText(emailUser);
                contrasena.setText(passwordUser);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data error",Toast.LENGTH_SHORT).show();
            }
        });
    }



    @Override
    public boolean onSupportNavigateUp() {
        // Usamos finish() para cerrar la actividad actual en lugar de onBackPressed() que está deprecado
        finish();
        return true;
    }
}