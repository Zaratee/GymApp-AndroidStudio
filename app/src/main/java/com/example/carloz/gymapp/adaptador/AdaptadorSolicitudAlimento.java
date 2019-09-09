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
import com.example.carloz.gymapp.NutriologoSolicitudAlimentoSolicitud;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemSolicitudesAlimento;

import java.util.ArrayList;

public class AdaptadorSolicitudAlimento extends RecyclerView.Adapter<AdaptadorSolicitudAlimento.ViewHolderAlimentos> {
    private ArrayList<ItemSolicitudesAlimento> listaClientes;
    public Context contexto;

    public AdaptadorSolicitudAlimento(ArrayList<ItemSolicitudesAlimento> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorSolicitudAlimento.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listalimento,null,false);
        return new AdaptadorSolicitudAlimento.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorSolicitudAlimento.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.marca.setText(listaClientes.get(i).getMarca());
        viewHolderAlimento.tipo.setText(listaClientes.get(i).getTipoAlimento());
        viewHolderAlimento.medida.setText(listaClientes.get(i).getCantidad());
        viewHolderAlimento.medidaTipo.setText(listaClientes.get(i).getCantidadTipo());
        //String opcion = contexto.getClass().getSimpleName();

        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto,NutriologoSolicitudAlimentoSolicitud.class);
                intent.putExtra("ID",listaClientes.get(j).getID());
                contexto.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre, marca, tipo, medida, medidaTipo;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_listalimento);
            marca = (TextView) itemView.findViewById(R.id.txtvMarca_listalimento);
            tipo = (TextView) itemView.findViewById(R.id.txtvTipo_listalimento);
            medida = (TextView) itemView.findViewById(R.id.txtvCantidad_listalimento);
            medidaTipo = (TextView) itemView.findViewById(R.id.txtvTipoCantidad_listalimento);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_listalimento);
        }
    }

}
