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
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemQueja;

import java.util.ArrayList;

public class AdaptadorQueja extends RecyclerView.Adapter<AdaptadorQueja.ViewHolderAlimentos>{
    private ArrayList<ItemQueja> listaClientes;
    public Context contexto;

    public AdaptadorQueja(ArrayList<ItemQueja> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorQueja.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.queja_item,null,false);
        return new AdaptadorQueja.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorQueja.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getQueja());
        viewHolderAlimento.estado.setText(listaClientes.get(i).getEstado());

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,estado;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_quejaItem);
            estado = (TextView) itemView.findViewById(R.id.txtvEstado_quejaItem);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_quejaItem);
        }
    }

}
