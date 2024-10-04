package com.app.cinerma.login.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.adapters.MovieDetailAdapter;
import com.app.cinerma.login.Domain.User;
import com.app.cinerma.login.activities.CreateUserActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.annotation.Documented;

public class UserAdapter extends FirestoreRecyclerAdapter<User,UserAdapter.ViewHolder> {


    //variable privada
    private FirebaseFirestore mFirestore=FirebaseFirestore.getInstance();
    Activity activity;

    //constructor
    public UserAdapter(@NonNull FirestoreRecyclerOptions<User> options, Activity activity) {
        super(options);
        this.activity=activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull User user) {

        //
        DocumentSnapshot documentedSnapshot=getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id=documentedSnapshot.getId();




        //Mostramos nuestros datos
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.password.setText(user.getPassword());

        //Logic para Editar
        holder.btn_editUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(activity, CreateUserActivity.class);
                i.putExtra("id_User",id);
                activity.startActivity(i);

            }
        });

        //Icono eliminar
        holder.btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Funcion para eliminar un registro
                userDelete(id);


            }
        });

    }

    //funtionality delete user data
    public void userDelete(String id){
        //logic delete
        mFirestore.collection("User").document(id).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(activity,"user delete successfull",Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(activity,"Error",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Conectamos nuestro adapter con la vista
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_user,parent,false);
        return new ViewHolder(v);

        //
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        //especificamos las variables para los datos de las personas
        TextView name,email,password;
        ImageView btn_eliminar,btn_editUser;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            //obtenemos los datos de la la vista
            name=itemView.findViewById(R.id.tvName);
            email=itemView.findViewById(R.id.tvUsername);
            password=itemView.findViewById(R.id.tvUserPassword);
            btn_eliminar=itemView.findViewById(R.id.btn_delete);
            btn_editUser=itemView.findViewById(R.id.btn_editUser);
        }
    }
}
