package com.app.cinerma.design.cines.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.app.cinerma.design.cines.entities.City;
import com.app.cinerma.design.cines.services.IFiltroCity;

import java.util.List;

public class CiudadOpcionAdater extends RecyclerView.Adapter<CiudadOpcionAdater.Viewholder> {

    //variables
    List<City> items;
    Context context;
    //usamos la interface
    IFiltroCity IFiltroCiudad;

    public CiudadOpcionAdater(List<City> items, Context context, IFiltroCity IFiltroCiudad) {
        this.items = items;
        this.context = context;
        this.IFiltroCiudad = IFiltroCiudad;
    }




    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View infalter = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_city_main,parent,false);
        context  = parent.getContext();
        return new Viewholder(infalter);

    }



    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, @SuppressLint("RecyclerView") int position) {
        holder.name.setText(items.get(position).getName());
        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IFiltroCiudad.clickFiltroCiudad(items.get(position).getIdCinemas(),items.get(position).getName());
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class Viewholder extends RecyclerView.ViewHolder{
        TextView name;
        LinearLayout container;
        public Viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_name_ciudad_filtro);
            container = itemView.findViewById(R.id.filtro_ciudad_container);
        }
    }
}
