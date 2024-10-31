package com.app.cinerma.design.peliculas.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.Cines;
import com.app.cinerma.design.cines.services.CinesApi;
import com.app.cinerma.design.peliculas.Frament.payment_summary_Fragment;
import com.app.cinerma.design.peliculas.Frament.ticket_amount_Fragment;
import com.app.cinerma.design.peliculas.entities.Movie;
import com.app.cinerma.design.peliculas.entities.PaymentInfo;
import com.app.cinerma.design.peliculas.entities.Reserva;
import com.app.cinerma.design.peliculas.entities.Ticket;
import com.app.cinerma.design.peliculas.services.MovieApi;
import com.app.cinerma.network.RetrofitClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class movie_selection_Activity extends AppCompatActivity implements ticket_amount_Fragment.OnSeatTypeSelectedListener {

    // Lista de movies
    private List<Movie> movies;
    //Lista de cines
    private List<Cines> cines;
    // Variables para los spinner del xml
    private Spinner spinnerCity,spinnerMovie,spinnerCines,spinnerHora,spinnerDate;
    //Variable button para guardar la reserva
    private Button saveButton;
    // Texto para mostrar la butaca seleccionada
    private TextView selectedSeatTextView;

    // Vista personalizada para la selección de asientos
    private SeatSelectionView seatSelectionView;

    // Variables para el botón de acción multipropósito
    private boolean isSeatTypeSelected = false;
    private Button actionButton;

    // Método para obtener el número de asientos seleccionados
    private int getSelectedSeatCount() {
        return seatSelectionView.getSelectedSeats().size();
    }

    //multipropósito 2 payment_summary_Fragment
    private boolean isPaymentSummary = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_selection);

        // Configuramos las variables del xml con las variables de la clase
        spinnerCity = findViewById(R.id.spinner_city);
        spinnerMovie=findViewById(R.id.spinner_movie);
        spinnerCines = findViewById(R.id.spinner_cinema);
        spinnerHora=findViewById(R.id.spinner_hora);
        spinnerDate=findViewById(R.id.spinner_dia);
        selectedSeatTextView = findViewById(R.id.selectedSeatTextView);
        seatSelectionView = findViewById(R.id.seat_selection_view);


        // Mostrar la ciudad seleccionada
        showCity();
        // Mostrar la pelicula seleccionada
        showMovie();
        // Mostrar el cine seleccionado
        showCinema();
        // Mostrar la fecha seleccionada
        showDate();
        //Obtener el circulo de butacas seleccionado
        showSelectedSeat();


        /****************************************************************************/
        actionButton = findViewById(R.id.btn_accion);
        actionButton.setText("Seleccionar Tipo de Entrada");


        actionButton.setOnClickListener(view -> {
            Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.seat_selection_container);

            Log.d("FragmentState", "Current Fragment: " + (currentFragment != null ? currentFragment.getClass().getSimpleName() : "null"));

            if (currentFragment instanceof ticket_amount_Fragment) {
                if (!isSeatTypeSelected) {
                    Log.d("FragmentState", "Selecciona un tipo de asiento primero.");
                    openSeatTypeFragment();
                } else {
                    Log.d("FragmentState", "Mostrando resumen de pago.");
                    showPaymentSummaryFragment();
                }
            } else if (currentFragment instanceof payment_summary_Fragment) {
                Log.d("FragmentState", "Guardando datos de reserva.");
                saveReservationData();
            } else {
                Log.d("FragmentState", "Fragmento inesperado, abriendo selección de asientos.");
                openSeatTypeFragment(); // Puedes ajustar esto según la lógica de tu aplicación
            }
        });





    }

    // Implementación de la interfaz para recibir notificación desde el fragmento
    @Override
    public void onSeatTypeSelected() {
        isSeatTypeSelected = true;
        actionButton.setText("Ver Resumen de Pago");
    }


    private void openSeatTypeFragment() {
        ticket_amount_Fragment seatTypeFragment = new ticket_amount_Fragment();

        // Pasa los asientos seleccionados al fragmento como lista de Strings
        //List<String> selectedSeats = seatSelectionView.getSelectedSeats(); // Asumiendo que tienes un método para obtener los asientos
        Bundle args = new Bundle();
        args.putInt("selectedSeatCount", getSelectedSeatCount());
        seatTypeFragment.setArguments(args);

        // Reemplaza el FrameLayout con el nuevo fragmento
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.seat_selection_container, seatTypeFragment, "TICKET_AMOUNT_FRAGMENT")
                .addToBackStack(null)
                .commit();
    }


    // Método para abrir el nuevo fragmento `payment_summary_Fragment`
    private void showPaymentSummaryFragment() {
        Fragment paymentSummaryFragment = new payment_summary_Fragment();

        // Reemplaza el FrameLayout con el fragmento de resumen de pago
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.seat_selection_container,paymentSummaryFragment ,"PAYMENT_SUMMARY_FRAGMENT")
                .addToBackStack(null)
                .commit();

        actionButton.setText("Pagar"); // Cambia el texto del botón
        isPaymentSummary = true; // Marca que el resumen de pago está activo
    }




    // Metodo para mostrar la ciudad seleccionada
    private void showCity() {
        // Lógica para mostrar la ciudad seleccionada
        String[] cities={"Bogotá","Medellín","Cali","Barranquilla","Cartagena"};

        // Configuramos el adaptador para el spinner de ciudades
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
    }

    // Metodo para mostrar la pelicula seleccionada
    private void showMovie(){
        // Instanciamos la interfaz de la API de películas
        MovieApi movieApi= RetrofitClient.getRetrofitInstance().create(MovieApi.class);
        // Realizamos la petición HTTP para obtener las películas
        movieApi.getMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    movies = response.body();

                    //Creamos una lista de titulos de peliculas
                    List<String> moviesTitle = new ArrayList<>();
                    for (Movie movie : movies) {
                        moviesTitle.add(movie.getTitle());
                    }

                    // Configuramos el adaptador para el spinner de películas
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(movie_selection_Activity.this, android.R.layout.simple_spinner_item, moviesTitle);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerMovie.setAdapter(adapter);
                }
            }

            //En caso de error
            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {
                Log.e("MovieSelectionActivity", "Error al obtener las películas", t);
            }
        });
    }

    // Metodo para mostrar el cine seleccionado
    private void showCinema(){
        // Instanciamos la interfaz de la API de cines
        CinesApi cinesApi = RetrofitClient.getRetrofitInstance().create(CinesApi.class);
        // Realizamos la petición HTTP para obtener los cines
        cinesApi.getCines().enqueue(new Callback<List<Cines>>() {
            @Override
            public void onResponse(Call<List<Cines>> call, Response<List<Cines>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cines = response.body();

                    //Creamos una lista de nombres de cines
                    List<String> cinesName = new ArrayList<>();
                    for (Cines cine : cines) {
                        cinesName.add(cine.getName());
                    }

                    // Configuramos el adaptador para el spinner de cines
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(movie_selection_Activity.this, android.R.layout.simple_spinner_item, cinesName);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerCines.setAdapter(adapter);

                    // Añadir el listener para seleccionar el cine y mostrar horarios
                    spinnerCines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // Obtener el cine seleccionado basado en la posición
                            Cines selectedCine = cines.get(position);
                            showHour(selectedCine);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {
                            // Opcional: manejar el caso donde no se selecciona nada
                        }
                    });
                }
            }

            //En caso de error
            @Override
            public void onFailure(Call<List<Cines>> call, Throwable t) {
                Log.e("MovieSelectionActivity", "Error al obtener los cines", t);
            }
        });
    }

    // Metodo para mostrar la hora seleccionada
    private void showHour(Cines selectedCine) {
        // Obtenemos los horarios del cine seleccionado
        List<String> horarios = selectedCine.getHorarios();

        // Configuramos el adaptador para el spinner de horas con los horarios del cine seleccionado
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, horarios);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHora.setAdapter(adapter);
    }

    // Metodo que nos permite elegir el dia para ver las peliculas
    private void showDate(){
        // Get current date and next day's date
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String today = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        String tomorrow = dateFormat.format(calendar.getTime());

        // Create a list of dates
        List<String> dates = new ArrayList<>();
        dates.add(today);
        dates.add(tomorrow);

        // Set up the ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.select_dialog_item, dates);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDate.setAdapter(adapter);
    }

    // Metodo para visualizar los asientos seleccionados
    private void showSelectedSeat() {
        // Configurar el listener para obtener los asientos seleccionados

        seatSelectionView.setOnSeatSelectedListener(new SeatSelectionView.OnSeatSelectedListener() {
            @Override
            public void onSeatSelected(List<String> seatIdentifiers) {
                // Actualizar el TextView con los asientos seleccionados
                selectedSeatTextView.setText(String.join(" ", seatIdentifiers));
            }
        });
    }


    /*********************************************************************************************************************/
    /**************************************************Metodos para obtner los datos de los framents************************/
    // Obtener el tipo de boleto seleccionado
    private List<Ticket> getSelectedTicketsFromFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("TICKET_AMOUNT_FRAGMENT");

        if (fragment instanceof ticket_amount_Fragment) {
            ticket_amount_Fragment ticketFragment = (ticket_amount_Fragment) fragment;
            List<Ticket> ticketList = ticketFragment.getSelectedTickets();

            if (ticketList == null || ticketList.isEmpty()) {
                Log.e("Ticket", "La lista de Ticket es nula o está vacía, agregando datos mock.");
                ticketList = new ArrayList<>();
                ticketList.add(new Ticket("General", 100.0)); // Ejemplo de ticket
            }

            return ticketList;
        } else {
            Log.e("Ticket", "El fragmento no es una instancia de ticket_amount_Fragment.");
            return new ArrayList<>();
        }
    }

    // Obtener la lista de pagos
    private List<PaymentInfo> getPaymentInfoListFromFragment() {
        Fragment fragment = getSupportFragmentManager().findFragmentByTag("PAYMENT_SUMMARY_FRAGMENT");
        if (fragment instanceof payment_summary_Fragment) {
            List<PaymentInfo> paymentInfoList = ((payment_summary_Fragment) fragment).getPaymentInfoList();

            // Verificar si la lista es nula o está vacía
            if (paymentInfoList == null || paymentInfoList.isEmpty()) {
                Log.e("PaymentInfo", "La lista de PaymentInfo está vacía, agregando datos mock");
                // Llenar manualmente la lista para pruebas
                paymentInfoList = new ArrayList<>();
                paymentInfoList.add(new PaymentInfo("Prueba 1", "prueba@123", "Tarjeta", "BCP", 100.0));
                paymentInfoList.add(new PaymentInfo("Prueba 2", "prueba@456", "Efectivo", "Interbank", 50.0));
                // Agregar más datos si es necesario
            } else {
                Log.d("PaymentInfo", "Datos de PaymentInfo obtenidos: " + paymentInfoList.toString());
            }

            return paymentInfoList;
        }
        Log.e("PaymentInfo", "Fragmento no encontrado, retornando lista vacía");
        return new ArrayList<>();
    }



    /******************************************************Guardamos los datos en realDatabase(firebase)**********************************************/
    // Método para guardar los datos de la reserva
    private void saveReservationData() {
        // Obtener los datos de la reserva
        String selectedCity = spinnerCity.getSelectedItem().toString();
        String selectedMovie = spinnerMovie.getSelectedItem().toString();
        String selectedCinema = spinnerCines.getSelectedItem().toString();
        String selectedHour = spinnerHora.getSelectedItem().toString();
        String selectedDate = spinnerDate.getSelectedItem().toString();
        String selectedSeat = selectedSeatTextView.getText().toString();

        // Obtener los boletos seleccionados del fragmento actual
        List<Ticket> selectedTickets = getSelectedTicketsFromFragment();

        // Obtener los pagos seleccionados del fragmento actual
        List<PaymentInfo> selectedPayments = getPaymentInfoListFromFragment();

        // Verificar los datos obtenidos en el log
        Log.d("ReservationData", "Ciudad: " + selectedCity);
        Log.d("ReservationData", "Película: " + selectedMovie);
        Log.d("ReservationData", "Cine: " + selectedCinema);
        Log.d("ReservationData", "Hora: " + selectedHour);
        Log.d("ReservationData", "Fecha: " + selectedDate);
        Log.d("ReservationData", "Asiento: " + selectedSeat);
        // En el método saveReservationData
        for (Ticket ticket : selectedTickets) {
            Log.d("ReservationData", "Boleto: " + ticket.getType() + ", Precio: S/ " + ticket.getPrice());
        }
        for (PaymentInfo paymentInfo : selectedPayments) {
            Log.d("ReservationData", "Pago: " + paymentInfo.getNombre() + ", Monto: S/ " + paymentInfo.getTotal());
        }


        // Guardar los datos en Firebase
        saveDataFarebase(selectedCity, selectedMovie, selectedCinema, selectedHour, selectedDate, selectedSeat, selectedTickets, selectedPayments);
    }


    private void saveDataFarebase(String selectedCity,
                                  String selectedMovie,
                                  String selectedCinema,
                                  String selectedHour,
                                  String selectedDate,
                                  String selectedSeat,
                                  List<Ticket> selectedTickets,
                                  List<PaymentInfo> selectedPayments) {
        // Crear la instancia de reserva con los datos seleccionados
        Reserva reserva = new Reserva(
                selectedCity,
                selectedMovie,
                selectedCinema,
                selectedHour,
                selectedDate,
                selectedSeat,
                selectedTickets,
                selectedPayments
        );

        // Referencia de Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reservasRef = database.getReference("reservas");

        // Generar ID único para la reserva y guardar los datos
        String reservaId = reservasRef.push().getKey();
        if (reservaId != null) {
            reservasRef.child(reservaId).setValue(reserva)
                    .addOnSuccessListener(aVoid -> Log.d("Firebase", "Reserva guardada correctamente"))
                    .addOnFailureListener(e -> Log.e("Firebase", "Error al guardar la reserva", e));
        }
    }


    //Metodo para obtner el precio de boleto
}