package com.example.carloz.gymapp.adaptador;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.carloz.gymapp.ClienteDietaDiarioEdit;
import com.example.carloz.gymapp.InstructorAsignarEjercicioInfo;
import com.example.carloz.gymapp.InstructorGraficaCliente;
import com.example.carloz.gymapp.NutriologoEditDietAlimento;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemAlimentoEditar;
import com.example.carloz.gymapp.items.ItemEjercicioCliente;

import java.util.ArrayList;

public class AdaptadorEjercicioCliente extends RecyclerView.Adapter<AdaptadorEjercicioCliente.ViewHolderAlimentos> {

    private ArrayList<ItemEjercicioCliente> listaClientes;
    public Context contexto;

    public AdaptadorEjercicioCliente(ArrayList<ItemEjercicioCliente> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEjercicioCliente.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ejercicioedit_item, null, false);
        return new AdaptadorEjercicioCliente.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEjercicioCliente.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.area.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.repeticiones.setText(listaClientes.get(i).getRepeticiones());
        viewHolderAlimento.series.setText(listaClientes.get(i).getSeries());
    }



    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,area,series,repeticiones;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_ejercicioEdit);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_ejercicioEdit);
            area = (TextView) itemView.findViewById(R.id.txtvArea_ejercicioEdit);
            series = (TextView) itemView.findViewById(R.id.txtvSeries_ejercicioEdit);
            repeticiones = (TextView) itemView.findViewById(R.id.txtvRepeticiones_ejercicioEdit);

        }
    }

}
