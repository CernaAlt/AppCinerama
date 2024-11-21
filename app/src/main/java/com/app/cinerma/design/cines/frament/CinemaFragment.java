package com.app.cinerma.design.cines.frament;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.adapters.CinemaAdapter;
import com.app.cinerma.design.cines.adapters.CinemaSelecionAdapter;
import com.app.cinerma.design.cines.entities.Cinema;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class CinemaFragment extends Fragment {
    //Instanciamos la base de datos de firestore
    private FirebaseFirestore firestore;
    //Instanciamos el RecyclerView del nuestro Xml
    private RecyclerView verCine;
    //Instanciamos el adaptador de nuestro RecyclerView
    private CinemaAdapter cinemaAdapter;
    //Instanciamos una lista de cines
    private List<Cinema> cinemaList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //
        View view = inflater.inflate(R.layout.fragment_cinema, container, false);

        //Instanciamos la base de datos de firestore
        firestore = FirebaseFirestore.getInstance();
        //Instanciamos el RecyclerView del nuestro Xml
        verCine = view.findViewById(R.id.ryv_Cinemas);
        //Configuramos el RecyclerView
        verCine.setLayoutManager(new LinearLayoutManager(getContext()));

        //
        cinemaList = new ArrayList<>();
        cinemaAdapter = new CinemaAdapter(cinemaList);
        verCine.setAdapter(cinemaAdapter);

        fetchCinemas();
        return view;
    }

    //Método para obtener los cines
    private void fetchCinemas() {
        //Limpiamos la lista de cines
        cinemaList.clear();
        //Obtenemos los cines de la base de datos
        firestore.collection("cines")
                //Añadimos un listener para obtener los cines
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    //Si hay un error al obtener los cines
                    if (e != null) {
                        Toast.makeText(getContext(), "Error al cargar los cines: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }
                    //Si se obtienen los cines
                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                       //Limpiamos la lista de cines
                        cinemaList.clear();
                        //Recorremos los documentos de la colección y los convertimos en objetos Cinema
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            //Convertimos el documento en un objeto Cinema
                            Cinema cinema = document.toObject(Cinema.class);
                            //Añadimos el cine a la lista
                            cinemaList.add(cinema);
                        }
                        //Notificamos al adaptador que los datos han cambiado
                        cinemaAdapter.notifyDataSetChanged();

                    } else {
                        //Si no se encuentran cines
                        Toast.makeText(getContext(), "null.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}