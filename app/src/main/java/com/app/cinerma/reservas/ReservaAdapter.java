package com.app.cinerma.reservas;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.Reserva;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ReservaAdapter extends RecyclerView.Adapter<ReservaAdapter.ReservaViewHolder> {

    private Context context;
    private List<Reserva> reservaList;
    private DatabaseReference databaseReference;

    //Contructor
    public ReservaAdapter(Context context, List<Reserva> reservas) {
        this.context = context;
        this.reservaList = reservas;

        // Inicializar la referencia a Firebase
        databaseReference = FirebaseDatabase.getInstance().getReference("reservas");
    }


    //Metodo para utilizar el layout item_reserva en el recycler view
    @NonNull
    @Override
    public ReservaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_reserva, parent, false);
        return new ReservaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservaViewHolder holder, int position) {
        Reserva reserva = reservaList.get(position);
        holder.tvMovie.setText(reserva.getMovie());

        holder.tvCinema.setText(reserva.getCinema());
        holder.tvHour.setText(reserva.getHour());



        holder.btnViewDetails.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleReservaActivity.class);
            intent.putExtra("reservaId", reserva.getId()); // Pasa el ID de la reserva
            context.startActivity(intent);
        });

        // Configurar el botón "Eliminar" con el listener
        holder.btnDelete.setOnClickListener(v -> {
            // Obtener el ID de la reserva seleccionada
            String reservaId = reserva.getId();
            deleteReserva(reservaId);  // Llamar al método para eliminar la reserva
        });
    }

    @Override
    public int getItemCount() {
        return reservaList.size();
    }


    public static class ReservaViewHolder extends RecyclerView.ViewHolder {
        TextView tvMovie, tvCinema, tvHour, tvDate, tvSeat;
        TextView btnViewDetails;
        Button btnDelete;

        public ReservaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMovie = itemView.findViewById(R.id.tv_movie);
            tvCinema = itemView.findViewById(R.id.tv_cinema);
            tvHour = itemView.findViewById(R.id.tv_hour);
            btnViewDetails = itemView.findViewById(R.id.btn_view_details);
            btnDelete = itemView.findViewById(R.id.btn_cancel);
        }
    }

    // Método para eliminar la reserva en Firebase
    private void deleteReserva(String reservaId) {
        databaseReference.child(reservaId).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Log.d("Firebase", "Reserva eliminada con éxito");
                Toast.makeText(context, "Reserva eliminada con éxito", Toast.LENGTH_SHORT).show();
                // Actualizar la lista después de eliminar
                for (int i = 0; i < reservaList.size(); i++) {
                    if (reservaList.get(i).getId().equals(reservaId)) {
                        reservaList.remove(i);
                        notifyItemRemoved(i);
                        break;
                    }
                }
            } else {
                Log.e("Firebase", "Error al eliminar la reserva", task.getException());
                Toast.makeText(context, "Error al eliminar", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
