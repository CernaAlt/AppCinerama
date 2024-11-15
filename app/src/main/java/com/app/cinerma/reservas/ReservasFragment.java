package com.app.cinerma.reservas;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.Reserva;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservasFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservaAdapter reservaAdapter;
    private List<Reserva> reservaList;
    private DatabaseReference databaseReference; // Referencia a la base de datos de Firebase

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        reservaList = new ArrayList<>();
        reservaAdapter = new ReservaAdapter(getContext(), reservaList);
        recyclerView.setAdapter(reservaAdapter);
        loadReservas();
        return view;
    }

    // Método para cargar las reservas desde Firebase
    private void loadReservas() {
        // Inicializamos la referencia a "reservas"
        databaseReference = FirebaseDatabase.getInstance().getReference("reservas");

        // Escuchador de cambios en los datos
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reservaList.clear();  // Limpiamos la lista antes de agregar nuevas reservas

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    // Obtenemos la reserva del snapshot y la agregamos a la lista
                    Reserva reserva = dataSnapshot.getValue(Reserva.class);
                    if (reserva != null) {
                        reservaList.add(reserva);
                    }
                }

                // Notificamos al adaptador que los datos han cambiado
                if (reservaAdapter != null) {
                    reservaAdapter.notifyDataSetChanged();
                } else {
                    Log.e("Firebase", "El adaptador de reservas es nulo");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error al cargar reservas", error.toException());
            }
        });
    }

}