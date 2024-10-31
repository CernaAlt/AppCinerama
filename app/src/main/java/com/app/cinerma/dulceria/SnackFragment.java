package com.app.cinerma.dulceria;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.app.cinerma.R;
import com.app.cinerma.network.RetrofitSnack;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnackFragment extends Fragment {

    // Declaración de variables
    private RecyclerView recyclerView;
    private DulceriaAdapter dulceriaAdapter;
    private List<Dulceria> dulceriaList = new ArrayList<>();

    /*****************************************************************/
    /************************Paginacion*******************************/
    private boolean isLoading = false;
    private int currentPage = 1;
    private static final int PAGE_SIZE = 10; // Tamaño de la página (número de elementos por carga)

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_snack, container, false);

        // Usamos las variables para referenciar los elementos del layout
        recyclerView = view.findViewById(R.id.ryv_mostarCombos);
        // Configurar el layout manager
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Configurar el adaptador con la lista inicial
        dulceriaAdapter = new DulceriaAdapter(dulceriaList, requireContext());
        recyclerView.setAdapter(dulceriaAdapter);

        // Cargar la primera página
        loadPage(currentPage);

        // Configurar el scroll listener para la paginación
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                GridLayoutManager layoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
                int totalItemCount = layoutManager.getItemCount();
                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                // Cargar más datos si estamos cerca del final y no estamos cargando
                if (!isLoading && totalItemCount <= (lastVisibleItem + 2)) {
                    loadPage(++currentPage);
                    isLoading = true;
                }
            }
        });

        return view;
    }

    // Método para cargar una página específica de la API
    private void loadPage(int page) {
        SnackApi snackApi = RetrofitSnack.getRetrofitInstance().create(SnackApi.class);

        // Llama al método con paginación en la API
        Call<List<Dulceria>> call = snackApi.getDulceria(page, PAGE_SIZE); // Aquí limitamos a PAGE_SIZE por página
        call.enqueue(new Callback<List<Dulceria>>() {
            @Override
            public void onResponse(Call<List<Dulceria>> call, Response<List<Dulceria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Dulceria> newItems = response.body();
                    dulceriaList.addAll(newItems);
                    dulceriaAdapter.notifyDataSetChanged();

                    // Verificar el tamaño de los datos cargados
                    Log.d("LoadPage", "Página: " + page + ", Elementos cargados: " + newItems.size());
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
                isLoading = false;
            }

            @Override
            public void onFailure(Call<List<Dulceria>> call, Throwable t) {
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("RetrofitError", t.getMessage(), t);
                isLoading = false;
            }
        });
    }
}