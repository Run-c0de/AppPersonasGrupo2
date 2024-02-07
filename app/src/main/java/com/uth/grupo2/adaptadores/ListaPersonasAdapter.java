package com.uth.grupo2.adaptadores;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.uth.grupo2.Activities.VerActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.uth.grupo2.Models.Personas;
import com.uth.grupo2.R;

public class ListaPersonasAdapter extends RecyclerView.Adapter<ListaPersonasAdapter.PersonaViewHolder> {

    ArrayList<Personas> listaPersonas;
    ArrayList<Personas> listaOriginal;

    public ListaPersonasAdapter(ArrayList<Personas> listaPersonas) {
        this.listaPersonas = listaPersonas;
        listaOriginal = new ArrayList<>();
        listaOriginal.addAll(listaPersonas);
    }

    @NonNull
    @Override
    public PersonaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lista_item_persona, null, false);
        return new PersonaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonaViewHolder holder, int position) {
        holder.viewNombres.setText(listaPersonas.get(position).getNombres() + " " + listaPersonas.get(position).getApellidos());
        holder.viewEdad.setText(listaPersonas.get(position).getEdad() + " Años");
        holder.viewCorreo.setText(" - " + listaPersonas.get(position).getCorreo());
    }

    public void filtrado(final String txtBuscar) {
        int longitud = txtBuscar.length();
        if (longitud == 0) {
            listaPersonas.clear();
            listaPersonas.addAll(listaOriginal);
            return;
        }

        boolean isSDKGood = android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N;
         if (isSDKGood) {
                List<Personas> collecion = listaPersonas.stream()
                        .filter(i -> i.getNombres().toLowerCase().contains(txtBuscar.toLowerCase()))
                        .collect(Collectors.toList());
                listaPersonas.clear();
                listaPersonas.addAll(collecion);
                return;
        }
          for (Personas c : listaOriginal) {
                    if (c.getNombres().toLowerCase().contains(txtBuscar.toLowerCase())) {
                        listaPersonas.add(c);
                    }
                }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return listaPersonas.size();
    }

    public class PersonaViewHolder extends RecyclerView.ViewHolder {

        TextView viewNombres, viewEdad, viewCorreo;

        public PersonaViewHolder(@NonNull View itemView) {
            super(itemView);

            viewNombres = itemView.findViewById(R.id.viewNombres);
            viewEdad = itemView.findViewById(R.id.viewEdad);
            viewCorreo = itemView.findViewById(R.id.viewCorreo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VerActivity.class);
                    intent.putExtra("ID", listaPersonas.get(getAdapterPosition()).getId());
                    context.startActivity(intent);
                }
            });
        }
    }
}
