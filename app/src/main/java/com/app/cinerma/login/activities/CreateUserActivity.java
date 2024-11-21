package com.app.cinerma.login.activities;


import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.app.cinerma.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CreateUserActivity extends AppCompatActivity {

    // Declaramos variable de firebase
    private FirebaseFirestore mfirestore;
    private FirebaseAuth mAuth;

    //variable para la contraseña y confirmacion de contraseña
    TextView passwordPrimary, passwordConfirm;

    //Variable of text
    EditText nombre, correo, apellidosPaterno, apellidoMaterno, telefono;
    RadioGroup gender;

    //button para register los data of the user
    Button btnRegister;

    //Variable para cargar los cines en el formulario
    private Spinner cineFavorito;
    private ArrayList<String> cineName;
    private HashMap<String, String> cineMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_user);

        //logic Edit in this activity
        String id =getIntent().getStringExtra("id_User");


        //Obtenemos los datos de los txt del formulario
        gender = findViewById(R.id.rb_genero);
        nombre = findViewById(R.id.txt_nameUser);
        correo = findViewById(R.id.txt_emailUser);
        apellidosPaterno = findViewById(R.id.txt_apellidoPaterno);
        apellidoMaterno = findViewById(R.id.txt_apellidoMaterno);
        telefono = findViewById(R.id.txt_phone);
        passwordPrimary = findViewById(R.id.txt_passworPrimary);
        passwordConfirm = findViewById(R.id.txt_passwordConfirm);
        cineFavorito = findViewById(R.id.spinnerCineplanet);
        btnRegister = findViewById(R.id.btn_addUser);

        //Inicializamos la instancia de firestore y firebaseAuth
        mfirestore=FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        //Validar en tiempo real si las contraseñas son iguales
        passwordConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //no lo usamos por el momento.
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //we don't use it
            }
        });

        //Inicilizamos el Spinner y las listas
        cineFavorito=findViewById(R.id.spinnerCineplanet);
        cineName=new ArrayList<>();
        cineMap=new HashMap<>();

        //Cargamos los datos de firestore
        datosCineFirestore();



        // Logic for insert user:Si es nuevo usuario
        //id==null || id == ""
        if (id == null || id.isEmpty()){
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Obtener la información de los campos de texto
                    String nameUser=nombre.getText().toString().trim();
                    String surnamePaternal = apellidosPaterno.getText().toString().trim();
                    String surnameMaternal = apellidoMaterno.getText().toString().trim();
                    String phone=telefono.getText().toString().trim();
                    String emailUser=correo.getText().toString().trim();
                    String passwordUser=passwordConfirm.getText().toString().trim();

                    //Llamar al metodo para obtener el genero seleccionado
                    String generoSeleccionado=genderSelected();
                    // Si no se seleccionó ningún género, mostrar un mensaje de error.
                    if (generoSeleccionado == null) {
                        Toast.makeText(CreateUserActivity.this, "Seleccione un genero", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    // Obtener el nombre del cine seleccionado en el Spinner
                    String cineSeleccionadoNombre=cineFavorito.getSelectedItem().toString();
                    //Obtener el ID del cine usando el mapa cineMap
                    String cineFavoritoId = cineMap.get(cineSeleccionadoNombre);
                    if (cineFavoritoId == null) {
                        Toast.makeText(CreateUserActivity.this, "Por favor selecciona un cine favorito", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    //validar password
                    if (!passwordPrimary.getText().toString().equals(passwordConfirm.getText().toString())) {
                        Toast.makeText(CreateUserActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (nameUser.isEmpty() && emailUser.isEmpty() && passwordUser.isEmpty()){
                        Toast.makeText(CreateUserActivity.this,"please, enter an email and password",Toast.LENGTH_SHORT).show();
                    }else {
                        insertUser(nameUser,emailUser,passwordUser,surnameMaternal,surnamePaternal,phone,cineFavoritoId,generoSeleccionado);

                    }
                }
            });

        }else {
            // Actualizamos el botón
            btnRegister.setText("Update");

            // Si no es nulo ni vacío vamos a actualizar
            getUser(id);

            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Obtener la información dentro de los txt
                    String nameUser = nombre.getText().toString().trim();
                    String surnamePaternal = apellidosPaterno.getText().toString().trim();
                    String surnameMaternal = apellidoMaterno.getText().toString().trim();
                    String phone = telefono.getText().toString().trim();
                    String emailUser = correo.getText().toString().trim();
                    String passwordUser = passwordConfirm.getText().toString().trim();
                    String generoSeleccionado = genderSelected();
                    String cineSeleccionadoNombre = cineFavorito.getSelectedItem().toString();
                    String cineFavoritoId = cineMap.get(cineSeleccionadoNombre);

                    if (nameUser.isEmpty() || emailUser.isEmpty() || passwordUser.isEmpty()) {
                        Toast.makeText(CreateUserActivity.this, "Por favor, ingrese un email y una contraseña", Toast.LENGTH_SHORT).show();
                    } else {
                        updateUser(nameUser, emailUser, passwordUser, surnamePaternal, surnameMaternal, phone, cineFavoritoId, generoSeleccionado);
                    }
                }
            });
        }
    }


    // Logic update user: Actualizamos Datos
    private void updateUser(String name, String email, String password, String surnamePaternal, String surnameMaternal, String phone, String idCine, String genero) {
        String userId = mAuth.getCurrentUser().getUid();
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("email", email);
        user.put("password", password);
        user.put("surnamePaternal", surnamePaternal);
        user.put("surnameMaternal", surnameMaternal);
        user.put("phone", phone);
        user.put("gender", genero);
        user.put("cineFavorito", idCine);

        mfirestore.collection("User").document(userId).update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getApplicationContext(), "Data updated", Toast.LENGTH_SHORT).show();
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(), "Error updating user data", Toast.LENGTH_SHORT).show();
            }
        });
    }



    //logic post user: Insertamos datos del usuario
    private void insertUser(String name,String email,String password,String surnamePaternal, String surnameMaternal, String phone, String idCine, String genero){
       mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    // Guardar los datos del usuario en Firestore
                    String userId = mAuth.getCurrentUser().getUid();
                    Map<String,Object> user=new HashMap<>();
                    user.put("name",name);
                    user.put("email",email);
                    user.put("password",password);
                    user.put("surnamePatrnal",surnamePaternal);
                    user.put("surnameMaternal",surnameMaternal);
                    user.put("phone",phone);
                    user.put("gender",genero);
                    user.put("cineFavorito",idCine);

                    mfirestore.collection("User").document(userId).set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(CreateUserActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(CreateUserActivity.this, "Error al registrar usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else {
                    Toast.makeText(getApplicationContext(), "Error al register", Toast.LENGTH_SHORT).show();
                }
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
                passwordConfirm.setText(passwordUser);

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getApplicationContext(),"Data error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo para validar que las contraseñas sean iguales.
    private void validatePassword(){
        String password=passwordPrimary.getText().toString().trim();
        String confirmPassword=passwordConfirm.getText().toString().trim();

        if (!confirmPassword.equals(password)){
            passwordConfirm.setError("Passwords do not mach");
        }
    }

    //Metodo que nos permiten tener la lista de cines
    private void datosCineFirestore() {
        FirebaseFirestore cines = FirebaseFirestore.getInstance();
        cines.collection("cines").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                QuerySnapshot cineSnapshots = task.getResult();

                // Limpiar la lista antes de llenarla nuevamente.
                cineName.clear();
                cineMap.clear();

                if (cineSnapshots != null && !cineSnapshots.isEmpty()) {
                    // Iterar sobre todos los documentos en la colección "Cines".
                    for (DocumentSnapshot document : cineSnapshots.getDocuments()) {
                        String idcine = document.getId();  // Obtener el ID del documento.
                        String nameCine = document.getString("name");  // Obtener el nombre del cine.

                        // Verificar que el nombre del cine no sea nulo.
                        if (nameCine != null) {
                            // Agregar el nombre del cine a la lista.
                            cineName.add(nameCine);
                            cineMap.put(nameCine, idcine);  // Almacenar el ID del cine.

                            // Log para verificar los datos obtenidos.
                            Log.d("FirestoreData", "Cine encontrado: " + nameCine + " (ID: " + idcine + ")");
                        } else {
                            Log.w("FirestoreData", "Documento sin nombre de cine: " + document.getId());
                        }
                    }

                    // Crear un adaptador para el Spinner si hay cines.
                    if (!cineName.isEmpty()) {
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(CreateUserActivity.this,
                                android.R.layout.simple_spinner_item, cineName);
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        cineFavorito.setAdapter(adapter);
                    } else {

                    }
                } else {
                    Log.w("FirestoreData", "No se encontraron documentos en la colección 'cines'.");
                    Toast.makeText(CreateUserActivity.this, "No se encontraron cines", Toast.LENGTH_SHORT).show();
                }

            } else {
                Toast.makeText(CreateUserActivity.this, "Error al cargar cines", Toast.LENGTH_SHORT).show();
                Log.e("FirestoreData", "Error al obtener cines", task.getException());
            }
        });
    }


    //Metodo para obtener el genero selecionado
    private String genderSelected(){
        // Obtener el ID del RadioButton seleccionado
        int selectedId = gender.getCheckedRadioButtonId();

        if (selectedId != -1) {
            // Encontrar el RadioButton correspondiente usando el ID
            RadioButton selectedRadioButton = findViewById(selectedId);

            // Obtener el texto del RadioButton seleccionado
            return selectedRadioButton.getText().toString();
        } else {
            return null;
        }
    }


    @Override
    public boolean onSupportNavigateUp() {
        // Usamos finish() para cerrar la actividad actual en lugar de onBackPressed() que está deprecado
        finish();
        return true;
    }
}