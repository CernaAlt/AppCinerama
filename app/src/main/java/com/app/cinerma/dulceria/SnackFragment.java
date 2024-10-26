package com.app.cinerma.dulceria;


import android.os.Bundle;
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
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SnackFragment extends Fragment {

    private RecyclerView recyclerView;
    private DulceriaAdapter dulceriaAdapter;
    private List<Dulceria> dulceriaList;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_snack, container, false);


        // Configurar el RecyclerView
        recyclerView = view.findViewById(R.id.ryv_mostarCombos);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        dataApi();

        return view;
    }

    private void dataApi() {
        // Inicializar el API
        SnackApi snackApi = RetrofitSnack.getRetrofitInstance().create(SnackApi.class);

        // Obtener la lista de dulceria
        Call<List<Dulceria>> call = snackApi.getDulceria();
        call.enqueue(new Callback<List<Dulceria>>() {
            @Override
            public void onResponse(Call<List<Dulceria>> call, Response<List<Dulceria>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    dulceriaList = response.body();
                    //Configurar el adaptador con la lista de dulceria
                    dulceriaAdapter = new DulceriaAdapter(dulceriaList, requireContext());
                    recyclerView.setAdapter(dulceriaAdapter);
                } else {
                    Toast.makeText(getContext(), "Error al obtener datos", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dulceria>> call, Throwable t) {
                // Manejo de errores en el telefono
                Toast.makeText(requireContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                //Imprimimos en la consola el error
                Log.e("RetrofitError", t.getMessage(), t);
            }
        });

    }
}