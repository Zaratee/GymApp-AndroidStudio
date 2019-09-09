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
import android.widget.Toast;

import com.example.carloz.gymapp.ClienteDietaDiarioEdit;
import com.example.carloz.gymapp.NutriologoEditDietAlimento;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemAlimentoAsignado;
import com.example.carloz.gymapp.items.ItemAlimentoEditar;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;

import java.util.ArrayList;

public class AdaptadorEditarDieta extends RecyclerView.Adapter<AdaptadorEditarDieta.ViewHolderAlimentos>{
    private ArrayList<ItemAlimentoEditar> listaClientes;
    public Context contexto;

    public AdaptadorEditarDieta(ArrayList<ItemAlimentoEditar> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorEditarDieta.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alimentocliente_item,null,false);
        return new AdaptadorEditarDieta.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorEditarDieta.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.marca.setText(listaClientes.get(i).getMarca());
        viewHolderAlimento.TipoAlimento.setText(listaClientes.get(i).getTipoAlimento());
        viewHolderAlimento.Cantidad.setText(listaClientes.get(i).getCantidadAsignada());
        viewHolderAlimento.CantidadTipo.setText(listaClientes.get(i).getCantidadTipo());
        viewHolderAlimento.tiempo.setText(listaClientes.get(i).getTiempo());

        if (listaClientes.get(i).getTiempo().equals("0")){
            viewHolderAlimento.tiempo.setTextColor(Color.parseColor("#9C402C"));
            viewHolderAlimento.linearLayout.setBackgroundColor(Color.parseColor("#FFD5CC"));
            viewHolderAlimento.tiempo.setText("Desayuno");
        }else if (listaClientes.get(i).getTiempo().equals("1")){
            viewHolderAlimento.tiempo.setTextColor(Color.parseColor("#2C9C88"));
            viewHolderAlimento.linearLayout.setBackgroundColor(Color.parseColor("#B8FFF2"));
            viewHolderAlimento.tiempo.setText("Almuerzo");
        } else if (listaClientes.get(i).getTiempo().equals("2")){
            viewHolderAlimento.tiempo.setTextColor(Color.parseColor("#9C2C2C"));
            viewHolderAlimento.linearLayout.setBackgroundColor(Color.parseColor("#FFB3B3"));
            viewHolderAlimento.tiempo.setText("Cena");
        } else if (listaClientes.get(i).getTiempo().equals("3")){
            viewHolderAlimento.tiempo.setTextColor(Color.parseColor("#892C9C"));
            viewHolderAlimento.linearLayout.setBackgroundColor(Color.parseColor("#F1ABFF"));
            viewHolderAlimento.tiempo.setText("Pasabocas");
        }


        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opcion = contexto.getClass().getSimpleName();
                switch (opcion) {
                    case "NutriologoEditarDieta":
                    Intent intent = new Intent(contexto, NutriologoEditDietAlimento.class);
                    intent.putExtra("ID", listaClientes.get(j).getID());
                    intent.putExtra("HORA", listaClientes.get(j).getTiempo());
                    intent.putExtra("CANTIDAD", listaClientes.get(j).getCantidadAsignada());
                    intent.putExtra("LISTA", listaClientes.get(j).getLista());
                    contexto.startActivity(intent);
                    //Toast.makeText(contexto, "" + listaClientes.get(j).getID(), Toast.LENGTH_SHORT).show();
                    break;
                    case "ClienteAlimEditarDiario":
                        Intent intent2 = new Intent(contexto, ClienteDietaDiarioEdit.class);
                        intent2.putExtra("ID", listaClientes.get(j).getID());
                        intent2.putExtra("HORA", listaClientes.get(j).getTiempo());
                        intent2.putExtra("CANTIDAD", listaClientes.get(j).getCantidadAsignada());
                        intent2.putExtra("LISTA", listaClientes.get(j).getLista());
                        contexto.startActivity(intent2);
                        break;

                }
            }
        });
        //String opcion = contexto.getClass().getSimpleName();


    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,marca,TipoAlimento,Cantidad, CantidadTipo, tiempo;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_alimentoCliente);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.Item_alimentoCliente);
            marca = (TextView) itemView.findViewById(R.id.txtvMarca_alimentoCliente);
            TipoAlimento = (TextView) itemView.findViewById(R.id.txtvTipo_alimentoCliente);
            Cantidad = (TextView) itemView.findViewById(R.id.txtvCantidad_alimentoCliente);
            CantidadTipo = (TextView) itemView.findViewById(R.id.txtvTipoCantidad_alimentoCliente);
            tiempo = (TextView) itemView.findViewById(R.id.txtvHorario_alimentoCliente);

        }
    }


}


