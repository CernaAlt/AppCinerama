package com.app.cinerma.design.cines.frament;


import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.app.cinerma.R;
import com.app.cinerma.databinding.FragmentCinesBinding;
import com.app.cinerma.design.cines.adapters.CineAdapter;
import com.app.cinerma.design.cines.adapters.CiudadOpcionAdater;
import com.app.cinerma.design.cines.entities.Cine;
import com.app.cinerma.design.cines.entities.City;
import com.app.cinerma.design.cines.services.ICineApi;
import com.app.cinerma.design.cines.services.ICityApi;
import com.app.cinerma.design.cines.services.IFiltroCity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
public class CinesFragment extends Fragment implements IFiltroCity,OnMapReadyCallback  {



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //declaramos las siguientes varriables
    FragmentCinesBinding binding;
    private Retrofit retrofit;
    ICineApi service;
    RecyclerView recyclerView;
    CineAdapter adapter;
    BottomSheetDialog bottomSheetDialog;
    BottomSheetDialog bottomSheetDialogSala;
    //
    private Retrofit retrofitCiudad;
    ICityApi serviceCiudad;
    RecyclerView recyclerViewCiudad;
    CiudadOpcionAdater adapterCiudad;

    List<Cine> ciness;

    //Para usar estas propiedades tener importado una dependencia de google maps
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private Marker currentLocationMarker;
    private ListView distanceListView;
    Boolean adapaterCargado = false;

    public CinesFragment() {
        // Required empty public constructor
    }

    //Preconstruido
    // TODO: Rename and change types and number of parameters
    public static CinesFragment newInstance(String param1, String param2) {
        CinesFragment fragment = new CinesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Prueba01","Este es una prueba");

        //obtenemos la ubicacion del dispositivo
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        //difinimos el callback
        //locationCallback, es un objeto que recibe actualizaciones de ubicacion
        locationCallback = new LocationCallback() {

            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {

                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    Log.i("Prueba02","Esta es una prueba 02");

                    //para cada ubicacion llamamos al metodo updateLocation
                    updateLocation(location);

                }
            }

        };
        //Vetificacion de permisos
        //verificamos si la aplicacion tiene tiene persimsos para acceder a ña ubicacion precisa
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //si no tiene solicitamos al usuario mediante requestPermissions
            ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            //si ya tiene perimisos llamos a un metodo startLocationUpdates
        } else {
            startLocationUpdates();
        }


    }

    //Metodo onCreateView es crucial para el ciclo de vida de un framento en Android
    //llamos a este metodo para dibujar la interfaz de usuario
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Este código utiliza ViewBinding, una característica que genera una clase de binding para cada archivo de layout XML.
        //FragmentCinesBinding es la clase generada para tu layout de fragmento.
        binding = FragmentCinesBinding.inflate(inflater, container, false);

        // retornamos el framento
        return binding.getRoot();
    }

    //Se usa cuando el fragmento se vuelve visible al usuario
    //este metodo va a relizar una llamada a nuestra api que estamos usando
    @Override
    public void onResume() {
        super.onResume();
        //Obtiene el contexto del fragmento, que se usará más adelante para el RecyclerView.
        Context context = this.getContext();
        //Llamada a la API:
        //instancia de una interfaz Retrofit,getAll() método que obtiene una lista de cines.
        // realiza la llamada de manera asíncrona.
        service.getAll().enqueue(new Callback<List<Cine>>() {

            //Manejo de la respuesta exitosa si es exito el metodo se ejecuta
            @Override
            public void onResponse(Call<List<Cine>> call, Response<List<Cine>> response) {
                if (response.body() != null) {

                    //Almacena la lista de cines recibida en una varriable
                    ciness = response.body();

                    //configuracion el RecyclerView y configura su LayoutManager para mostrar los elementos en una lista vertical.
                    recyclerView = binding.RvCinesMain;
                    recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

                    //Configuración del adaptador
                    adapter = new CineAdapter(response.body());
                    adapter.setResultados(response.body());
                    //lo asingamos a un RecyclerView.
                    recyclerView.setAdapter(adapter);
                    //marcador de carga completa
                    adapaterCargado = true;
                }
            }

            //llamos a este metodo se la api falla
            @Override
            public void onFailure(Call<List<Cine>> call, Throwable t) {
                Toast.makeText(context, "Error loading cinerama", Toast.LENGTH_SHORT).show();
                Log.e("CinesFragment", "Error: ", t);

            }
        });
    }










    //Metodo updateLocation
    private void updateLocation(Location location) {
       //creamos un objeto latLng
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        //verificamos si ya exite un marcador para la ubicacion actual
        if (currentLocationMarker != null) {
            //Si exite actulizamos la posicion
            currentLocationMarker.setPosition(latLng);
        } else {
            //si no exite creamos un nuevp MarkerOptions
            MarkerOptions markerOptions = new MarkerOptions().position(latLng).title("Ubicación Actual");
            //currentLocationMarker = mMap.addMarker(markerOptions);
        }

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));

        calculateDistances(location);
    }

    //configura y comienza las actulizaciones de ubicacion
    private void startLocationUpdates() {
        // Crea un objeto LocationRequest, que define cómo la app quiere recibir la ubicación
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000); // Solicita actualizaciones cada 10 segundos
        locationRequest.setFastestInterval(5000); // No acepta actualizaciones más frecuentes que cada 5 segundos
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // Solicita la mayor precisión posible (GPS)

        // Verifica si el permiso ACCESS_FINE_LOCATION ha sido concedido
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Si el permiso ha sido concedido, comienza las actualizaciones de ubicación
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
        } else {
            // Si no se ha concedido el permiso, solicita al usuario que lo conceda
            requestLocationPermission();
        }
    }

    private void requestLocationPermission() {
        // Verifica si el usuario previamente denegó el permiso
        if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Explica al usuario por qué necesitas el permiso se usa en frament
            Toast.makeText(requireContext(), "Permiso de ubicación denegado.", Toast.LENGTH_LONG).show();

        }
        // Solicita el permiso
        ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    //Metodo para calcular la distancia
    private void calculateDistances(Location currentLocation) {
        //Log.i("sdfdsgf",String.valueOf(currentLocation.getLatitude()));


        if(adapaterCargado){
            for (Cine cinema : ciness) {
                Location cinemaLocation = new Location("");
                cinemaLocation.setLatitude(cinema.getLatitude());
                cinemaLocation.setLongitude(cinema.getLongitude());

                float distance = currentLocation.distanceTo(cinemaLocation);

                cinema.setDistancia(distance);

            }
            adapter.notifyDataSetChanged();
        }



    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        start();
    }

    void start(){
        //configuracion de listnners para filtros
        //Configura listeners de clic para los botones de filtro de ciudad y sala.
        binding.filtroCiudadCineMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerViewCiudad.setAdapter(adapterCiudad);
                bottomSheetDialog.show();
            }
        });
        //Cuando se hace clic, se muestra el BottomSheetDialog correspondiente.
        binding.btnSalaFiltro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialogSala.show();
            }
        });

        //Configuramos la api para cines
        retrofit = new Retrofit.Builder()
                .baseUrl("https://661d25e3e7b95ad7fa6c4a63.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //Creamos una implementacion de la interfaz
        service = retrofit.create(ICineApi.class);

        //configuramos la api para ciudades
        retrofitCiudad = new Retrofit.Builder()
                .baseUrl("https://664b8b4835bbda10987d536c.mockapi.io/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //creamos una implementacion de la interfaz
        serviceCiudad = retrofitCiudad.create(ICityApi.class);

        //Llama a métodos que probablemente inicializan los BottomSheetDialogs para filtrar por ciudad y sala.

        //createFiltroCiudad();
        createFiltroSala();

        CinesFragment cinesFragment = this;
        serviceCiudad.getAll().enqueue(new Callback<List<City>>() {
            @Override
            public void onResponse(Call<List<City>> call, Response<List<City>> response) {
                if(response.isSuccessful()) {
                    adapterCiudad = new CiudadOpcionAdater(response.body(),cinesFragment.getContext(),cinesFragment);
                }
            }

            //Si falla, registra el error en el log.
            @Override
            public void onFailure(Call<List<City>> call, Throwable t) {
                Log.e("CinesFragment", "Error al obtener ciudades", t);
            }


        });



    }

    //Metodo para filtrar la sala
    void createFiltroSala(){

        bottomSheetDialogSala = new BottomSheetDialog(requireContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.filtro_sala, null);
        bottomSheetDialogSala.setContentView(view1);

        eventBtnSalas(view1);



        FrameLayout bottomSheet = bottomSheetDialogSala.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
            int windowHeight = getResources().getDisplayMetrics().heightPixels;
            layoutParams.height = windowHeight;
            bottomSheet.setLayoutParams(layoutParams);
            behavior.setPeekHeight(windowHeight);
        }

    }

    //
    void eventBtnSalas(View view1){
        LinearLayout txt2d = view1.findViewById(R.id.txt_2d);
        LinearLayout txtRegular = view1.findViewById(R.id.txt_regular);
        LinearLayout txt3d = view1.findViewById(R.id.txt_3d);
        LinearLayout txtPrime = view1.findViewById(R.id.txt_prime);
        LinearLayout txtXtreme = view1.findViewById(R.id.txt_xtreme);
        LinearLayout txtScreenX = view1.findViewById(R.id.txt_screenx);

        txt2d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("2D");
            }
        });

        txtRegular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("REGULAR");
            }
        });

        txt3d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("3D");
            }
        });

        txtPrime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("PRIME");
            }
        });

        txtXtreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("XTREME");
            }
        });

        txtScreenX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filtroFromSala("SCREENX");
            }
        });
    }

    //Metodo filtroFromSala
    void filtroFromSala(String data){

        adapter.getResultados().clear();
        binding.txtNameSala.setText(data);
        binding.txtNameCine.setText("Ciudad");
        for (Cine cineDomain: adapter.getItems()){
            for(String dataCine: cineDomain.getAvaliable()){
                if(dataCine.equals(data)){
                    adapter.getResultados().add(cineDomain);
                }
            }

        }

        adapter.notifyDataSetChanged();
        bottomSheetDialogSala.dismiss();
    }


    //medoto para filtrar la ciudad
    void createFiltroCiudad(){
        bottomSheetDialog = new BottomSheetDialog(requireContext());
        View view1 = LayoutInflater.from(getContext()).inflate(R.layout.filtrar_ciudad_peliculas_main, null);
        bottomSheetDialog.setContentView(view1);
        Context context = this.getContext();

        recyclerViewCiudad = view1.findViewById(R.id.rv_filtro_ciudadd);
        recyclerViewCiudad.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));



        FrameLayout bottomSheet = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet);
        if (bottomSheet != null) {
            BottomSheetBehavior<FrameLayout> behavior = BottomSheetBehavior.from(bottomSheet);
            ViewGroup.LayoutParams layoutParams = bottomSheet.getLayoutParams();
            int windowHeight = getResources().getDisplayMetrics().heightPixels;
            layoutParams.height = windowHeight;
            bottomSheet.setLayoutParams(layoutParams);
            behavior.setPeekHeight(windowHeight);
        }
    }

    //Filtro para cada cine y un nombre como parametros
    @Override
    public void clickFiltroCiudad(List<Integer> idCines, String name) {

        //Borra los resultados actuales del adaptador para prepararse para los nuevos resultados filtrados.
        adapter.getResultados().clear();
        // Establece el nombre de la ciudad seleccionada.
        binding.txtNameCine.setText(name);
        //Resetea el texto de la sala a "Sala"
        binding.txtNameSala.setText("Sala");
        //Fltrado de cines
        //Itera sobre todos los cines en el adaptador.
        for (Cine cineDomain: adapter.getItems()){

            //Para cada cine, verifica si su ID está en la lista de IDs proporcionada.
            for(Integer i: idCines){
                //Si el ID coincide, añade ese cine a los resultados del adaptador.
                if(String.valueOf(i).equals(cineDomain.getId())){


                    adapter.getResultados().add(cineDomain);
                }
            }

        }

        ////Notifica al adaptador que los datos han cambiado para que actualice la vista.
        adapter.notifyDataSetChanged();
        //Cierra el BottomSheetDialog, que probablemente contenía las opciones de filtro.
        bottomSheetDialog.dismiss();
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    //method the google maps
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }

}