package com.app.cinerma.design.peliculas.Frament;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;
import com.app.cinerma.R;
import com.app.cinerma.design.peliculas.entities.PaymentInfo;

import java.util.ArrayList;
import java.util.List;

public class payment_summary_Fragment extends Fragment {
    //Variables
    // Variables para los elementos del layout
    private RadioGroup radioGroupPrimary;
    private Spinner spinnerSecondary;
    private EditText nombre, email;
    private double total = 100.00;  // Total simulado

    // Lista de pagos simulados por el momento
    private List<PaymentInfo> paymentInfoList;

    public List<PaymentInfo> getPaymentInfoList() {
        Log.d("PaymentInfoFragment", "Lista de PaymentInfo: " + paymentInfoList);
        return paymentInfoList != null ? paymentInfoList : new ArrayList<>();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Inicializar la lista y llenarla con datos
        paymentInfoList = new ArrayList<>();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el layout
        View view = inflater.inflate(R.layout.fragment_payment, container, false);

        // Referencia de los elementos del layout
        nombre = view.findViewById(R.id.txt_name);
        email = view.findViewById(R.id.txt_email);
        radioGroupPrimary = view.findViewById(R.id.radioGroupPrimary);
        spinnerSecondary = view.findViewById(R.id.spinnerSecondary);

        // Configurar los listeners de los radio buttons
        setupRadioGroupListeners();

        // Configurar el botón para agregar PaymentInfo
        Button btnAddPaymentInfo = view.findViewById(R.id.btn_proceed_payment);
        btnAddPaymentInfo.setOnClickListener(v -> initializePaymentInfoList());

        return view;
    }

    private void initializePaymentInfoList() {
        // Obtener datos ingresados por el usuario
        String nombreUsuario = nombre.getText().toString();
        String emailUsuario = email.getText().toString();
        String tipoPago = "N/A";
        String proveedor = "N/A";

        // Obtener el tipo de pago seleccionado
        int checkedId = radioGroupPrimary.getCheckedRadioButtonId();
        if (checkedId == R.id.radio_tarjeta) {
            tipoPago = "Tarjeta";
            proveedor = spinnerSecondary.getSelectedItem().toString(); // Obtener el proveedor seleccionado
        } else if (checkedId == R.id.radio_appAgorra) {
            tipoPago = "Aplicación Agora";
            proveedor = spinnerSecondary.getSelectedItem().toString(); // Obtener el proveedor seleccionado
        } else if (checkedId == R.id.radio_billeteras) {
            tipoPago = "Billetera Electrónica";
            proveedor = spinnerSecondary.getSelectedItem().toString(); // Obtener el proveedor seleccionado
        }

        // Agregar datos a la lista
        paymentInfoList.add(new PaymentInfo(nombreUsuario, emailUsuario, tipoPago, proveedor, total));

        // Log para verificar los datos agregados
        Log.d("PaymentInfoFragment", "Datos agregados: " + paymentInfoList);
    }






    // Método para configurar los listeners de los radio buttons
    private void setupRadioGroupListeners() {
        radioGroupPrimary.setOnCheckedChangeListener((group, checkedId) -> {
            // Si un radio button está marcado, mostrar el spinner y poblarlo según la opción primaria seleccionada
            if (checkedId != -1) {
                // Mostrar el spinner y poblarlo basado en la opción primaria seleccionada
                spinnerSecondary.setVisibility(View.VISIBLE);
                ArrayAdapter<CharSequence> adapter = null;

                if (checkedId == R.id.radio_tarjeta) {
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.credit_card_providers_array, android.R.layout.simple_spinner_item);
                } else if (checkedId == R.id.radio_appAgorra) {
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.app_agora_options_array, android.R.layout.simple_spinner_item);
                } else if (checkedId == R.id.radio_billeteras) {
                    adapter = ArrayAdapter.createFromResource(getContext(),
                            R.array.e_wallet_options_array, android.R.layout.simple_spinner_item);
                }

                if (adapter != null) {
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spinnerSecondary.setAdapter(adapter);
                }
            }
        });
    }
}