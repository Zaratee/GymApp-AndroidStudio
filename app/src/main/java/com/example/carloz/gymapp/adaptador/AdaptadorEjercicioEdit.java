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
import com.example.carloz.gymapp.InstructorEditarEjer;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;

import java.util.ArrayList;

public class AdaptadorEjercicioEdit extends RecyclerView.Adapter<AdaptadorEjercicioEdit.ViewHolderAlimentos>{

    private ArrayList<ItemEjercicioEditar> listaClientes;
    public Context contexto;

    public AdaptadorEjercicioEdit(ArrayList<ItemEjercicioEditar> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEjercicioEdit.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ejercicioedit_item,null,false);
        return new AdaptadorEjercicioEdit.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEjercicioEdit.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.area.setText(listaClientes.get(i).getArea());
        viewHolderAlimento.series.setText(listaClientes.get(i).getSeries());
        viewHolderAlimento.repeticiones.setText(listaClientes.get(i).getRepeticiones());
        //String opcion = contexto.getClass().getSimpleName();

        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto,InstructorEditarEjer.class);
                intent.putExtra("ID",listaClientes.get(j).getID());
                intent.putExtra("EjerID",listaClientes.get(j).getEjercicioID());
                intent.putExtra("SERIES",listaClientes.get(j).getSeries());
                intent.putExtra("REPETI",listaClientes.get(j).getRepeticiones());
                intent.putExtra("PESO",listaClientes.get(j).getPeso());
                intent.putExtra("REGISTRO",listaClientes.get(j).getRegistro());

                contexto.startActivity(intent);
            }
        });

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
