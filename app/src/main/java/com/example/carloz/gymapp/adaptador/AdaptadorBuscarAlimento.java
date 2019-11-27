package com.example.carloz.gymapp.adaptador;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.carloz.gymapp.AdminModificarSelecUsuario;
import com.example.carloz.gymapp.BuscarAlimentoSeleccionado;
import com.example.carloz.gymapp.ClienteBuscarAlimSelec;
import com.example.carloz.gymapp.InstructorMenuCliente;
import com.example.carloz.gymapp.NutriologoClienteAsignar;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemAlimentoBuscado;
import com.example.carloz.gymapp.items.ItemClienteInstructor;

import java.util.ArrayList;

public class AdaptadorBuscarAlimento extends RecyclerView.Adapter<AdaptadorBuscarAlimento.ViewHolderAlimentos> {

    private ArrayList<ItemAlimentoBuscado> listaClientes;
    public Context contexto;

    public AdaptadorBuscarAlimento(ArrayList<ItemAlimentoBuscado> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorBuscarAlimento.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.alimento_buscado,null,false);
        return new AdaptadorBuscarAlimento.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorBuscarAlimento.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre());
        viewHolderAlimento.cantidad.setText(listaClientes.get(i).getCantidad()+" ");
        viewHolderAlimento.marca.setText(listaClientes.get(i).getMarca());



        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String opcion = contexto.getClass().getSimpleName();
                switch (opcion){
                    case "NutriologoClienteAsignarComida":
                        Intent intent = new Intent(contexto,BuscarAlimentoSeleccionado.class);
                        intent.putExtra("Alimento",listaClientes.get(j).getId());
                        intent.putExtra("Tipo",listaClientes.get(j).getTiempo());
                        contexto.startActivity(intent);
                        break;
                    case "ClienteBuscarAlimento":
                        Intent intent2 = new Intent(contexto,ClienteBuscarAlimSelec.class);
                        intent2.putExtra("Alimento",listaClientes.get(j).getId());
                        intent2.putExtra("Tipo",listaClientes.get(j).getTiempo());
                        //Toast.makeText(contexto, ""+listaClientes.get(j).getId(), Toast.LENGTH_SHORT).show();
                        contexto.startActivity(intent2);
                        break;

                }

                //Toast.makeText(contexto,"Cliente queja "+listaClientes.get(j).getNombre(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,marca, cantidad;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_buscarAlimento);
            cantidad = (TextView) itemView.findViewById(R.id.txtvCantidad_buscarAlimento);
            marca = (TextView) itemView.findViewById(R.id.txtvMarca_buscarAlimento);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_buscarAlimento);
        }
    }
}
