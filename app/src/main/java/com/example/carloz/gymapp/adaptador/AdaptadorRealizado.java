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

import com.example.carloz.gymapp.InstructorAsignarEjercicioInfo;
import com.example.carloz.gymapp.InstructorGraficaCliente;
import com.example.carloz.gymapp.InstructorRealizInfo;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemRealizado;

import java.util.ArrayList;

public class AdaptadorRealizado extends RecyclerView.Adapter<AdaptadorRealizado.ViewHolderAlimentos>{

    private ArrayList<ItemRealizado> listaClientes;
    public Context contexto;

    public AdaptadorRealizado(ArrayList<ItemRealizado> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorRealizado.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.realizado_item,null,false);
        return new AdaptadorRealizado.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorRealizado.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.fecha.setText(listaClientes.get(i).getFecha());
        viewHolderAlimento.repeticiones.setText(listaClientes.get(i).getRepeticiones());
        //String opcion = contexto.getClass().getSimpleName();
        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(contexto, InstructorRealizInfo.class);
                intent.putExtra("IDREALIZADO",listaClientes.get(j).getRealizado());
                contexto.startActivity(intent);
            }


        });
    }


    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre, fecha, repeticiones;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_relizado);
            fecha = (TextView) itemView.findViewById(R.id.txtvFecha_relizado);
            repeticiones = (TextView) itemView.findViewById(R.id.txtvRepeticiones_relizado);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_relizado);
        }
    }

}
