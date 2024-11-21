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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReservasFragment extends Fragment {

    private RecyclerView recyclerView;
    private ReservaAdapter reservaAdapter;
    private List<Reserva> reservaList;

    /**************************Paginacion Firebase*******************************/
    private static final int PAGE_SIZE = 10;  // Número de registros a cargar por página
    private String lastLoadedKey = null;     // Almacena la clave del último registro cargado
    private boolean isLoading = false;      // Evita múltiples solicitudes simultáneas


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflamos el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_reservas, container, false);

        // Inicializamos RecyclerView y adaptador
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Inicializamos la lista de reservas y el adaptador
        reservaList = new ArrayList<>();
        reservaAdapter = new ReservaAdapter(getContext(), reservaList);
        recyclerView.setAdapter(reservaAdapter);

        // Cargamos las reservas iniciales
        loadReservas();

        // Agregamos un OnScrollListener para implementar la carga progresiva
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && layoutManager.findLastVisibleItemPosition() == reservaList.size() - 1) {
                    // Si el usuario llega al final, cargamos más reservas
                    if (!isLoading) { // Aseguramos que no hay una carga en progreso
                        loadReservas();
                    }
                }
            }
        });

        return view;
    }

    private void loadReservas() {
        // Evita múltiples solicitudes simultáneas
        if (isLoading) return;
        isLoading = true;

        DatabaseReference reservasRef = FirebaseDatabase.getInstance().getReference("reservas");
        Query query;

        if (lastLoadedKey == null) {
            // Primera carga: ordenamos por clave en orden descendente y limitamos a PAGE_SIZE
            query = reservasRef.orderByKey().limitToLast(PAGE_SIZE); // Carga los últimos PAGE_SIZE registros
            Log.d("Firebase", "Cargando primeras reservas");
        } else {
            // Carga paginada: empieza antes del último elemento cargado
            query = reservasRef.orderByKey().endBefore(lastLoadedKey).limitToLast(PAGE_SIZE);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    List<Reserva> tempReservaList = new ArrayList<>();
                    String tempLastKey = lastLoadedKey;

                    // Recorremos los datos en orden
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Reserva reserva = dataSnapshot.getValue(Reserva.class);
                        if (reserva != null) {
                            tempReservaList.add(reserva); // Agregamos temporalmente
                            tempLastKey = dataSnapshot.getKey(); // Actualizamos la última clave cargada
                        }
                    }

                    // Invertimos la lista temporal para mantener el orden descendente
                    Collections.reverse(tempReservaList);

                    // Agregamos los datos al inicio de la lista principal
                    reservaList.addAll(0, tempReservaList);

                    // Actualizamos lastLoadedKey después de procesar los datos
                    lastLoadedKey = tempLastKey;

                    // Notificamos al adaptador
                    if (reservaAdapter != null) {
                        reservaAdapter.notifyDataSetChanged();
                    }
                }

                // Permitir más cargas
                isLoading = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error al cargar reservas", error.toException());
                isLoading = false;
            }
        });
    }


    /*private void loadReservas() {
        // Evita múltiples solicitudes simultáneas
        if (isLoading) return;
        isLoading = true;

        DatabaseReference reservasRef = FirebaseDatabase.getInstance().getReference("reservas");
        Query query;

        if (lastLoadedKey == null) {
            // Primera carga: ordenamos por clave y limitamos a PAGE_SIZE
            query = reservasRef.orderByKey().limitToFirst(PAGE_SIZE);
            Log.d("Firebase", "Cargando primeras reservas");
        } else {
            // Carga paginada: empieza después del último elemento cargado
            query = reservasRef.orderByKey().startAfter(lastLoadedKey).limitToFirst(PAGE_SIZE);
        }

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    // Cargamos los datos y actualizamos lastLoadedKey
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Reserva reserva = dataSnapshot.getValue(Reserva.class);
                        if (reserva != null) {
                            reservaList.add(reserva); // Agregamos los registros al final
                            lastLoadedKey = dataSnapshot.getKey(); // Actualizamos la última clave cargada
                        }
                    }
                    if (reservaAdapter != null) {
                        reservaAdapter.notifyDataSetChanged();
                    }
                }
                // Permitir más cargas
                isLoading = false;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Firebase", "Error al cargar reservas", error.toException());
                isLoading = false;
            }
        });
    }*/
}