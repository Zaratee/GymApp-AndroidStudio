package com.example.carloz.gymapp.adaptador;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carloz.gymapp.InstructorEditarEjer;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;
import com.example.carloz.gymapp.items.ItemEjercicioSemana;

import java.util.ArrayList;

public class AdaptadorEjercicioSemana  extends RecyclerView.Adapter<AdaptadorEjercicioSemana.ViewHolderAlimentos>{

    private ArrayList<ItemEjercicioSemana> listaClientes;
    public Context contexto;

    public AdaptadorEjercicioSemana(ArrayList<ItemEjercicioSemana> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEjercicioSemana.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ejerciciosemana_item,null,false);
        return new AdaptadorEjercicioSemana.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEjercicioSemana.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.peso.setText(listaClientes.get(i).getPeso());
        viewHolderAlimento.series.setText(listaClientes.get(i).getSeries());
        viewHolderAlimento.repeticiones.setText(listaClientes.get(i).getRepeticiones());
        viewHolderAlimento.dia.setText(listaClientes.get(i).getDia());

        //String opcion = contexto.getClass().getSimpleName();

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,dia,series,repeticiones,peso;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_ejercicioSemana);
            peso = (TextView) itemView.findViewById(R.id.txtvPeso_ejercicioSemana);
            dia = (TextView) itemView.findViewById(R.id.txtvDia_ejercicioSemana);
            series = (TextView) itemView.findViewById(R.id.txtvSeries_ejercicioSemana);
            repeticiones = (TextView) itemView.findViewById(R.id.txtvRepeticiones_ejercicioSemana);

        }
    }

}
