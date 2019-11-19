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
import android.widget.Toast;

import com.example.carloz.gymapp.InstructorEditarEjer;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemAlimentoAsignado;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;

import java.util.ArrayList;

public class AdaptadorAlimentoAsignado extends RecyclerView.Adapter<AdaptadorAlimentoAsignado.ViewHolderAlimentos>{


    private ArrayList<ItemAlimentoAsignado> listaClientes;
    public Context contexto;

    public AdaptadorAlimentoAsignado(ArrayList<ItemAlimentoAsignado> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorAlimentoAsignado.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alimentoasignado_item,null,false);
        return new AdaptadorAlimentoAsignado.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorAlimentoAsignado.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.marca.setText(listaClientes.get(i).getMarca());
        viewHolderAlimento.TipoAlimento.setText(listaClientes.get(i).getTiempo());
        viewHolderAlimento.Cantidad.setText(listaClientes.get(i).getCantidad()+" ");
        viewHolderAlimento.CantidadTipo.setText(listaClientes.get(i).getCantidadTipo());

        //String opcion = contexto.getClass().getSimpleName();


    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,marca,TipoAlimento,Cantidad, CantidadTipo;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_listaAlimentoAsignado);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.Item_listaAlimentoAsignado);
            marca = (TextView) itemView.findViewById(R.id.txtvMarca_listaAlimentoAsignado);
            TipoAlimento = (TextView) itemView.findViewById(R.id.txtvTipo_listaAlimentoAsignado);
            Cantidad = (TextView) itemView.findViewById(R.id.txtvCantidad_listaAlimentoAsignado);
            CantidadTipo = (TextView) itemView.findViewById(R.id.txtvTipoCantidad_listaAlimentoAsignado);

        }
    }

}


