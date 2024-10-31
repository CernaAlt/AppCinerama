package com.app.cinerma.design.peliculas.Frament;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.activities.movie_selection_Activity;
import com.app.cinerma.design.peliculas.entities.Ticket;
import java.util.ArrayList;
import java.util.List;

public class ticket_amount_Fragment extends Fragment {
    // Botones y TextViews para cada tipo de entrada
    private Button incrementGeneralButton, decrementGeneralButton;
    private Button incrementChildButton, decrementChildButton;
    private Button incrementSeniorButton, decrementSeniorButton;
    private TextView generalTicketAmountText, childTicketAmountText, seniorTicketAmountText;
    private TextView remainingSeatsText;

    // Variables para almacenar la cantidad de cada tipo de entrada
    private int generalTicketCount = 0;
    private int childTicketCount = 0;
    private int seniorTicketCount = 0;

    // Número total de asientos seleccionados (simulado)
    // Por ahora es un valor fijo, simulado para este ejemplo
    //private int totalSelectedSeats = 3;

    /***************************************************************************************/

    //acientos restantes
    private int remainingSeats;
    //total de asientos seleccionados
    private int totalSelectedSeats;

    // Lista de tickets seleccionados
    private List<Ticket> selectedTickets = new ArrayList<>();

    // Método que me va a permitir acceder a la lista de tickets seleccionados
    public List<Ticket> getSelectedTickets() {
        return selectedTickets;
    }



    /****************************************************************/
    // Interfaz para comunicar a la actividad
    private OnSeatTypeSelectedListener listener;

    // Interfaz para comunicar a la actividad que se ha seleccionado un tipo de entrada
    public interface OnSeatTypeSelectedListener {
        void onSeatTypeSelected();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnSeatTypeSelectedListener) {
            listener = (OnSeatTypeSelectedListener) context;
        } else {
            throw new RuntimeException(context.toString() + " debe implementar OnSeatTypeSelectedListener");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_ticket_amount, container, false);

        // Obtén el número de asientos seleccionados desde el anterior
        // Canvas
        if (getArguments() != null) {
            totalSelectedSeats = getArguments().getInt("selectedSeatCount", 0);
            Toast.makeText(getContext(), "Asientos seleccionados: " + totalSelectedSeats, Toast.LENGTH_SHORT).show();
        }

        // Configuración de botones y textos para cada tipo de entrada del xml
        incrementGeneralButton = view.findViewById(R.id.btn_increment_general);
        decrementGeneralButton = view.findViewById(R.id.btn_decrement_general);
        generalTicketAmountText = view.findViewById(R.id.txt_general_amount);

        incrementChildButton = view.findViewById(R.id.btn_increment_child);
        decrementChildButton = view.findViewById(R.id.btn_decrement_child);
        childTicketAmountText = view.findViewById(R.id.txt_child_amount);

        incrementSeniorButton = view.findViewById(R.id.btn_increment_senior);
        decrementSeniorButton = view.findViewById(R.id.btn_decrement_senior);
        seniorTicketAmountText = view.findViewById(R.id.txt_senior_amount);

        // TextView para mostrar los asientos restantes en el xml
        remainingSeatsText=view.findViewById(R.id.txt_remaining_seats);

        // Inicializamos el número de asientos restantes
        remainingSeats = totalSelectedSeats;
        updateRemainingSeats();

        // Configuración de listeners de los botones
        incrementGeneralButton.setOnClickListener(v -> updateTicketCount("general", true));
        decrementGeneralButton.setOnClickListener(v -> updateTicketCount("general", false));

        incrementChildButton.setOnClickListener(v -> updateTicketCount("child", true));
        decrementChildButton.setOnClickListener(v -> updateTicketCount("child", false));

        incrementSeniorButton.setOnClickListener(v -> updateTicketCount("senior", true));
        decrementSeniorButton.setOnClickListener(v -> updateTicketCount("senior", false));

        return view;
    }

    // Método para actualizar el conteo de boletos
    private void updateTicketCount(String ticketType, boolean isIncrement) {
        int totalTickets = generalTicketCount + childTicketCount + seniorTicketCount;

        switch (ticketType) {
            case "general":
                if (isIncrement && remainingSeats > 0) {
                    generalTicketCount++;
                    remainingSeats--;
                    addTicket("general"); // Agrega el boleto "General" a la lista
                } else if (!isIncrement && generalTicketCount > 0) {
                    generalTicketCount--;
                    remainingSeats++;
                    removeTicket("general"); // Remueve un boleto "General" de la lista
                }
                generalTicketAmountText.setText(String.valueOf(generalTicketCount));
                break;

            case "child":
                if (isIncrement && remainingSeats > 0) {
                    childTicketCount++;
                    remainingSeats--;
                    addTicket("child"); // Agrega el boleto "Niño" a la lista
                } else if (!isIncrement && childTicketCount > 0) {
                    childTicketCount--;
                    remainingSeats++;
                    removeTicket("child"); // Remueve un boleto "Niño" de la lista
                }
                childTicketAmountText.setText(String.valueOf(childTicketCount));
                break;

            case "senior":
                if (isIncrement && remainingSeats > 0) {
                    seniorTicketCount++;
                    remainingSeats--;
                    addTicket("senior"); // Agrega el boleto "Adulto Mayor" a la lista

                } else if (!isIncrement && seniorTicketCount > 0) {
                    seniorTicketCount--;
                    remainingSeats++;
                    removeTicket("senior"); // Remueve un boleto "Adulto Mayor" de la lista
                }
                seniorTicketAmountText.setText(String.valueOf(seniorTicketCount));
                break;
        }
        updateRemainingSeats(); // Actualiza los asientos restantes en el TextView
        showSelectedTickets();  // Muestra la lista actualizada de boletos en la consola

        // Si el usuario ha seleccionado el número total de asientos
        if (remainingSeats == 0) {
            // Notifica a la actividad que el tipo de entrada está listo
            if (listener != null) {
                listener.onSeatTypeSelected();
            }
        }
    }

    // Llama a este método cuando el usuario haya terminado de seleccionar los boletos
    private void notifySeatTypeSelected() {
        if (listener != null) {
            listener.onSeatTypeSelected();
        }
    }

    // Método para actualizar el TextView de asientos restantes
    private void updateRemainingSeats() {
        remainingSeatsText.setText("Asientos restantes: " + remainingSeats);
    }

    // Remueve el último ticket de un tipo específico
    private void removeTicket(String ticketType) {
        for (int i = selectedTickets.size() - 1; i >= 0; i--) {
            if (selectedTickets.get(i).getType().equals(ticketType)) {
                selectedTickets.remove(i);
                break;
            }
        }
    }

    // Agrega una entrada seleccionada
    private void addTicket(String ticketType) {
        int price = getTicketPrice(ticketType);
        Ticket ticket = new Ticket(ticketType, price);
        selectedTickets.add(ticket);
    }

    // Obtén el precio de un tipo de boleto específico
    private int getTicketPrice(String ticketType) {
        switch (ticketType) {
            case "general":
                return 20;
            case "child":
                return 10;
            case "senior":
                return 15;
            default:
                return 0;
        }
    }

    // Muestra todos los tickets seleccionados en la consola
    private void showSelectedTickets() {
        for (Ticket ticket : selectedTickets) {
            Log.d("Selected Ticket", "Tipo: " + ticket.getType() + ", Precio: S/ " + ticket.getPrice());
        }
    }

}