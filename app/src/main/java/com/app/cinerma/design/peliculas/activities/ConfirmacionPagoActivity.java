package com.app.cinerma.design.peliculas.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.app.cinerma.R;
import com.app.cinerma.design.MainActivity;
import com.app.cinerma.reservas.ReservasFragment;

public class ConfirmacionPagoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirmacion_pago);


        // Obtén el `reservaId` pasado a esta actividad
        String reservaId = getIntent().getStringExtra("RESERVA_ID");



        // Busca el TextView y establece el `reservaId`, con verificación para nulo
        TextView txtCodReserva = findViewById(R.id.txt_codReserva);
        if (reservaId != null) {
            txtCodReserva.setText(reservaId);
        } else {
            txtCodReserva.setText("Error: Código de reserva no disponible.");
        }




        // Button para ir a historial de reservas
        Button btnHistorial = findViewById(R.id.btn_historial);

        btnHistorial.setOnClickListener(v -> {
            // Reemplazar el contenido de la actividad con el fragmento
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, new ReservasFragment())
                    .addToBackStack(null) // Opcional, para que puedas volver atrás
                    .commit();
        });

    }
}