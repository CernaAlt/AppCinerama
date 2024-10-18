package com.app.cinerma.design.cines.frament;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.cinerma.R;
import com.app.cinerma.design.cines.adapters.CinemaSelecionAdapter;
import com.app.cinerma.design.cines.entities.Cinema;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class Fragment_Cine_selection extends Fragment {

    private FirebaseFirestore firestore;
    private RecyclerView cinemaRecyclerView;
    private CinemaSelecionAdapter cinemaAdapter;
    private List<Cinema> cinemaList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__cine_selection, container, false);

        firestore = FirebaseFirestore.getInstance();
        cinemaRecyclerView = view.findViewById(R.id.rv_cinemas);
        cinemaRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cinemaList = new ArrayList<>();
        cinemaAdapter = new CinemaSelecionAdapter(cinemaList);
        cinemaRecyclerView.setAdapter(cinemaAdapter);

        fetchCinemas();

        return view;
    }

    private void fetchCinemas() {
        cinemaList.clear();
        cinemaAdapter.notifyDataSetChanged();

        firestore.collection("cines")
                .addSnapshotListener((queryDocumentSnapshots, e) -> {
                    if (e != null) {
                        Toast.makeText(getContext(), "Error al cargar los cines: " + e.getMessage(), Toast.LENGTH_LONG).show();
                        return;
                    }

                    if (queryDocumentSnapshots != null && !queryDocumentSnapshots.isEmpty()) {
                        cinemaList.clear();

                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Cinema cinema = document.toObject(Cinema.class);
                            cinemaList.add(cinema);
                        }

                        cinemaAdapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(getContext(), "No se encontraron cines.", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}