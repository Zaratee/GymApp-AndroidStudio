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

import com.example.carloz.gymapp.ClienteEjercicio;
import com.example.carloz.gymapp.NutriologoSolicitudAlimentoSolicitud;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicioClienteAyuda;
import com.example.carloz.gymapp.items.ItemSolicitudesAlimento;

import java.util.ArrayList;

public class AdaptadorEjeClientAyuda extends RecyclerView.Adapter<AdaptadorEjeClientAyuda.ViewHolderAlimentos> {
    private ArrayList<ItemEjercicioClienteAyuda> listaClientes;
    public Context contexto;

    public AdaptadorEjeClientAyuda(ArrayList<ItemEjercicioClienteAyuda> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEjeClientAyuda.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_ejeclienteayuda,null,false);
        return new AdaptadorEjeClientAyuda.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEjeClientAyuda.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.area.setText(listaClientes.get(i).getArea());
        viewHolderAlimento.peso.setText(listaClientes.get(i).getPeso());
        viewHolderAlimento.series.setText(listaClientes.get(i).getSeries());
        viewHolderAlimento.repeticiones.setText(listaClientes.get(i).getRepeticiones());

        if (listaClientes.get(i).getEstado().equals("REALIZADO")){
            viewHolderAlimento.estado.setText("Realizado");
            viewHolderAlimento.estado.setTextColor(Color.parseColor("#33cc33"));
        }else if (listaClientes.get(i).getEstado().equals("OK")){
            viewHolderAlimento.estado.setText("No realizado");
        }
        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(contexto,ClienteEjercicio.class);
                intent.putExtra("IDEJER",listaClientes.get(j).getId());
                intent.putExtra("REGISTROINSTRU",listaClientes.get(j).getInstructor());
                contexto.startActivity(intent);
            }
        });

        //String opcion = contexto.getClass().getSimpleName();

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {
        TextView nombre, area, peso, series, repeticiones, estado;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_EjeClienteAyuda);
            area = (TextView) itemView.findViewById(R.id.txtvArea_EjeClienteAyuda);
            peso = (TextView) itemView.findViewById(R.id.txtvPeso_EjeClienteAyuda);
            series = (TextView) itemView.findViewById(R.id.txtvSeries_EjeClienteAyuda);
            repeticiones = (TextView) itemView.findViewById(R.id.txtvRepeticiones_EjeClienteAyuda);
            estado = (TextView) itemView.findViewById(R.id.txtvEstado_EjeClienteAyuda);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_EjeClienteAyuda);
        }
    }

}
