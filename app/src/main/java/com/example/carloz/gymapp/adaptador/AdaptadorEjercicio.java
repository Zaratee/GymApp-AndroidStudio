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

import com.example.carloz.gymapp.BuscarAlimentoSeleccionado;
import com.example.carloz.gymapp.InstructorAsignarEjercicioInfo;
import com.example.carloz.gymapp.InstructorGraficaCliente;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemAlimentoBuscado;
import com.example.carloz.gymapp.items.ItemEjercicio;

import java.util.ArrayList;

public class AdaptadorEjercicio extends RecyclerView.Adapter<AdaptadorEjercicio.ViewHolderAlimentos>{

    private ArrayList<ItemEjercicio> listaClientes;
    public Context contexto;

    public AdaptadorEjercicio(ArrayList<ItemEjercicio> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEjercicio.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.ejericio_item_,null,false);
        return new AdaptadorEjercicio.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEjercicio.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        //String opcion = contexto.getClass().getSimpleName();
        if (!listaClientes.get(j).getDia().equals("20")){
            viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contexto,InstructorAsignarEjercicioInfo.class);
                    intent.putExtra("ID",listaClientes.get(j).getId());
                    intent.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                    intent.putExtra("DIANUM",listaClientes.get(j).getDia());
                    contexto.startActivity(intent);
                }
            });
        }else{
            viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(contexto,InstructorGraficaCliente.class);
                    intent.putExtra("ID",listaClientes.get(j).getId());
                    intent.putExtra("REGISTRO",listaClientes.get(j).getRegistro());
                    intent.putExtra("DIANUM",listaClientes.get(j).getDia());
                    contexto.startActivity(intent);
                }
            });
        }



    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_itemEjercicio);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_Ejercicio);
        }
    }

}
