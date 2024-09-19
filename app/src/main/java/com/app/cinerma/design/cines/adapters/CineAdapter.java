package com.app.cinerma.design.cines.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.Cine;

import java.util.ArrayList;
import java.util.List;

public class CineAdapter extends RecyclerView.Adapter<CineAdapter.Viewholder> {
    //declaramos variables
    List<Cine> items;
    List<Cine> resultados;
    Context context;

    //contructor
    public List<Cine> getItems() {
        return items;
    }
    public void setItems(List<Cine> items) {
        this.items = items;
    }

    public List<Cine> getResultados() {
        return resultados;
    }

    public void setResultados(List<Cine> resultados) {
        this.resultados = resultados;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public CineAdapter(List<Cine> items) {

        this.items = new ArrayList<>(items);
        this.resultados = new ArrayList<>(items);
    }


    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infalter = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cine_main,parent,false);
        context  = parent.getContext();
        return  new CineAdapter.Viewholder(infalter );
    }

    @Override
    public void onBindViewHolder(@NonNull CineAdapter.Viewholder holder, int position) {

        holder.name.setText(resultados.get(position).getName());
        holder.address.setText(resultados.get(position).getAddress());

        String dis = "";
        if (resultados.get(position).getDistancia()>= 1000) {
            dis  = String.format("%.2f", resultados.get(position).getDistancia() / 1000) + " km";
        } else {
            dis = String.format("%.0f", resultados.get(position).getDistancia()) + " m";
        }

        holder.distancia.setText( dis);
    }

    @Override
    public int getItemCount() {
        return resultados.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder{

        TextView name;
        TextView address;
        TextView distancia;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name_cine);
            address  =itemView.findViewById(R.id.txt_direction_cine);
            distancia  =itemView.findViewById(R.id.distancia);
        }
    }
}
