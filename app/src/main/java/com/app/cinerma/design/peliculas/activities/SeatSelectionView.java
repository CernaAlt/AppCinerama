package com.app.cinerma.design.peliculas.activities;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionView extends View {
    private Paint seatPaint;
    private Paint selectedSeatPaint;
    private List<Integer> selectedSeats = new ArrayList<>(); // Lista de asientos seleccionados
    private int maxSelectableSeats = 5; // Límite de asientos seleccionados
    private int totalSeats = 48;  // 8 filas * 6 columnas (ajusta según el total que necesites)
    private int columns = 6;// Número de columnas de la cuadrícula
    private int seatRadius = 30; // Radio de cada asiento
    private OnSeatSelectedListener seatSelectedListener; // Listener para enviar el asiento seleccionado


    public SeatSelectionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void setOnSeatSelectedListener(OnSeatSelectedListener listener) {
        this.seatSelectedListener = listener;
    }

    public List<Integer> getSelectedSeats() {
        return selectedSeats;
    }


    private void init() {
        seatPaint = new Paint();
        seatPaint.setColor(Color.GRAY); // Color de los asientos disponibles
        seatPaint.setStyle(Paint.Style.FILL);

        selectedSeatPaint = new Paint();
        selectedSeatPaint.setColor(Color.BLUE); // Color del asiento seleccionado
        selectedSeatPaint.setStyle(Paint.Style.FILL);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int rowCount = (int) Math.ceil((double) totalSeats / columns);
        int seatSpacing = 80;

        for (int i = 0; i < totalSeats; i++) {
            int row = i / columns;
            int col = i % columns;

            float cx = col * seatSpacing + seatRadius + 20;
            float cy = row * seatSpacing + seatRadius + 20;

            // Dibuja el asiento en color de selección si está en la lista de seleccionados
            canvas.drawCircle(cx, cy, seatRadius, selectedSeats.contains(i) ? selectedSeatPaint : seatPaint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int seatSpacing = 80;
            for (int i = 0; i < totalSeats; i++) {
                int row = i / columns;
                int col = i % columns;

                float cx = col * seatSpacing + seatRadius + 20;
                float cy = row * seatSpacing + seatRadius + 20;

                if (Math.pow(event.getX() - cx, 2) + Math.pow(event.getY() - cy, 2) <= Math.pow(seatRadius, 2)) {
                    if (selectedSeats.contains(i)) {
                        // Si el asiento ya está seleccionado, lo deselecciona
                        selectedSeats.remove(Integer.valueOf(i));
                    } else if (selectedSeats.size() < maxSelectableSeats) {
                        // Solo agrega el asiento si no se ha alcanzado el máximo
                        selectedSeats.add(i);
                    }
                    invalidate(); // Redibuja la vista

                    // Obtiene los identificadores de los asientos seleccionados
                    List<String> seatIdentifiers = new ArrayList<>();
                    for (int seatIndex : selectedSeats) {
                        seatIdentifiers.add(getSeatIdentifier(seatIndex));
                    }

                    // Llamar al listener para actualizar el TextView con los asientos seleccionados
                    if (seatSelectedListener != null) {
                        seatSelectedListener.onSeatSelected(seatIdentifiers);
                    }
                    return true;
                }
            }
        }
        return super.onTouchEvent(event);
    }

    private String getSeatIdentifier(int seatIndex) {
        char row = (char) ('A' + seatIndex / columns);
        int col = (seatIndex % columns) + 1;
        return row + String.valueOf(col);
    }

    public interface OnSeatSelectedListener {
        void onSeatSelected(List<String> seatIdentifiers);
    }
}
