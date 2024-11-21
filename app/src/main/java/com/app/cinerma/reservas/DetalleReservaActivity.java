package com.app.cinerma.reservas;

import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.PaymentInfo;
import com.app.cinerma.design.peliculas.entities.Reserva;
import com.app.cinerma.design.peliculas.entities.Ticket;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class DetalleReservaActivity extends AppCompatActivity {

    TextView tvMovie, tvCinema, tvHour, tvDate,codigoReserva, tvSeat;
    LinearLayout ticketsLayout, paymentsLayout;

    //private List<Reserva> reservas = new ArrayList<>();
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_detalle_reserva);

        // Obtain the reserva ID from intent
        String reservaId = getIntent().getStringExtra("reservaId");

        // Initialize Firebase database reference
        databaseReference = FirebaseDatabase.getInstance().getReference("reservas");

        // Initialize UI elements
        //tvCity = findViewById(R.id.tvCity);
        tvMovie = findViewById(R.id.tvMovie);
        tvCinema = findViewById(R.id.tvCinema);
        tvHour = findViewById(R.id.tvHour);
        tvDate = findViewById(R.id.tvDate);
        tvSeat = findViewById(R.id.tvSeat);
        ticketsLayout = findViewById(R.id.ticketsLayout);
        paymentsLayout = findViewById(R.id.paymentsLayout);
        codigoReserva=findViewById(R.id.txt_codReserva);

        // Fetch the reserva details by ID
        fetchReserva(reservaId);

    }


    private void fetchReserva(String reservaId) {
        databaseReference = FirebaseDatabase.getInstance().getReference("reservas");

        // Query all reservations and find the one with matching `id`
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Reserva reserva = snapshot.getValue(Reserva.class);

                    if (reserva != null && reservaId.equals(reserva.getId())) {
                        // Process and update UI with the matching reserva
                        updateReservaDetails(reserva);
                        return;  // Exit loop once the matching reserva is found
                    }
                }
                // Handle case where no matching reserva was found
                Log.e("FetchReserva", "No reserva found with id: " + reservaId);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FetchReserva", "Database error: " + databaseError.getMessage());
            }
        });
    }


    private void updateReservaDetails(Reserva reserva) {
        // Set reserva details in TextViews
        //tvCity.setText(reserva.getCity());
        tvMovie.setText(reserva.getMovie());
        tvCinema.setText(reserva.getCinema());
        tvHour.setText(reserva.getHour());
        tvDate.setText(reserva.getDate());
        tvSeat.setText(reserva.getSeat());
        codigoReserva.setText(reserva.getId());

        // Clear layouts to avoid duplicate entries
        ticketsLayout.removeAllViews();
        paymentsLayout.removeAllViews();

        // Dynamically add tickets to ticketsLayout
        for (Ticket ticket : reserva.getTickets()) {
            TextView ticketView = new TextView(this);
            ticketView.setText("Ticket: " + ticket.getType() + " - Price: $" + ticket.getPrice());
            ticketsLayout.addView(ticketView);
        }

        // Dynamically add payments to paymentsLayout
        for (PaymentInfo payment : reserva.getPayments()) {
            TextView paymentView = new TextView(this);
            paymentView.setText("Payment: " + payment.getNombre() + ", Method: " + payment.getMetodoPago() + ", Total: $" + payment.getTotal());
            paymentsLayout.addView(paymentView);
        }
    }
}