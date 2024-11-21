package com.app.cinerma.login.adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.login.Entities.User;
import com.app.cinerma.login.activities.CreateUserActivity;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

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


        // Obtener el ID del documento
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());
        final String id = documentSnapshot.getId();


        // Mostrar los datos del usuario en la vista
        holder.name.setText(user.getName());
        holder.email.setText(user.getEmail());
        holder.password.setText(user.getPassword());

        // Lógica para editar el usuario
        holder.btn_editUser.setOnClickListener(v -> {
            Intent i = new Intent(activity, CreateUserActivity.class);
            i.putExtra("id_User", id);
            activity.startActivity(i);
        });

        // Lógica para eliminar el usuario
        holder.btn_eliminar.setOnClickListener(v -> userDelete(id));
    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Conectamos nuestro adapter con la vista
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_show_user,parent,false);
        return new ViewHolder(v);

        //
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        //Especificamos las variables para los datos de las personas
        TextView name, email, password;
        ImageView btn_eliminar, btn_editUser;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // Referencias a las vistas
            name = itemView.findViewById(R.id.tvName);
            email = itemView.findViewById(R.id.tvUsername);
            password = itemView.findViewById(R.id.tvUserPassword);
            btn_eliminar = itemView.findViewById(R.id.btn_delete);
            btn_editUser = itemView.findViewById(R.id.btn_editUser);
        }
    }

    //delete user data
    private void userDelete(String id) {
        mFirestore.collection("User").document(id).delete()
                .addOnSuccessListener(aVoid ->
                        Toast.makeText(activity, "Usuario eliminado con éxito", Toast.LENGTH_SHORT).show())
                .addOnFailureListener(e ->
                        Toast.makeText(activity, "Error al eliminar usuario: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
}
