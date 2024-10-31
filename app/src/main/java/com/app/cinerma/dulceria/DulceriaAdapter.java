package com.app.cinerma.dulceria;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.cinerma.R;
import com.bumptech.glide.Glide;

import java.util.List;

public class DulceriaAdapter extends RecyclerView.Adapter<DulceriaAdapter.ViewHolder> {


    private List<Dulceria> dulceriaList;
    private Context context;

    public DulceriaAdapter(List<Dulceria> dulceriaList, Context context) {
        this.dulceriaList = dulceriaList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dulceria, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(DulceriaAdapter.ViewHolder holder, int position) {
        Dulceria dulceria = dulceriaList.get(position);

        // Check if the URL is not null before loading it with Glide
        if (dulceria.getUrlImage() != null) {
            Glide.with(context)
                    .load(dulceria.getUrlImage())
                    .into(holder.imageView);
        } else {
            // Handle the case where the URL is null (e.g., set a placeholder image)
            holder.imageView.setImageResource(R.drawable.ic_close); // Replace with your placeholder image resource
        }

        // Set other data to the views if needed
        holder.title.setText(dulceria.getTitle());
        holder.cost.setText(dulceria.getCost());

        //Obtenermos la lista de descripciones de cada combo
        List<String> description = dulceria.getDescription();
        if(description!=null && !description.isEmpty()){
            StringBuilder sb = new StringBuilder();
            for (String s : description) {
                sb.append(s).append("\n");
            }
            holder.description.setText(sb.toString());

            holder.description.setText(description.toString().trim());  // Trim para eliminar el último salto de línea
        }else {
            holder.description.setText("No description available");
        }

        Log.d("DulceriaAdapter", "Tamaño total de la lista: " + dulceriaList.size());


    }

    @Override
    public int getItemCount() {
        return dulceriaList != null ? dulceriaList.size() : 0;

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView title, cost, description;

        //Falta usar la categoria
        

        public ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.img_Combo);
            title = view.findViewById(R.id.combo_title);
            cost = view.findViewById(R.id.txt_cost);
            description = view.findViewById(R.id.combo_description);
        }
    }
}
