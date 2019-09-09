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

import com.example.carloz.gymapp.InstructorEditarEjer;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicioEditar;
import com.example.carloz.gymapp.items.ItemUsuariosAdmin;

import java.util.ArrayList;

public class AdaptadorAdminHistorial extends RecyclerView.Adapter<AdaptadorAdminHistorial.ViewHolderAlimentos> {


    private ArrayList<ItemUsuariosAdmin> listaClientes;
    public Context contexto;

    public AdaptadorAdminHistorial(ArrayList<ItemUsuariosAdmin> listaClientes) {
        this.listaClientes = listaClientes;
    }

    @NonNull
    @Override
    public AdaptadorAdminHistorial.ViewHolderAlimentos onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.usuario_item,null,false);
        return new AdaptadorAdminHistorial.ViewHolderAlimentos(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AdaptadorAdminHistorial.ViewHolderAlimentos viewHolderAlimento, int i) {
        final int j = i;
        viewHolderAlimento.nombre.setText(listaClientes.get(i).getNombre()+" "+listaClientes.get(i).getApellido());
        viewHolderAlimento.Registro.setText(listaClientes.get(i).getRegistro());
        viewHolderAlimento.telefono.setText(listaClientes.get(i).getTelefono());
        viewHolderAlimento.fecha.setText(listaClientes.get(i).getFecha());
        viewHolderAlimento.estado.setText(listaClientes.get(i).getEstado());


        if (listaClientes.get(i).getEstado().equals("Eliminado")){
            viewHolderAlimento.estado.setTextColor(Color.parseColor("#C00303"));
        }else if (listaClientes.get(i).getEstado().equals("Creado")){
            viewHolderAlimento.estado.setTextColor(Color.parseColor("#04B91A"));
        }


    }

    @Override
    public int getItemCount() {
        return listaClientes.size();
    }

    public class ViewHolderAlimentos extends RecyclerView.ViewHolder {

        TextView nombre,Registro,telefono, fecha, estado;
        LinearLayout linearLayout;

        public ViewHolderAlimentos(@NonNull View itemView) {
            super(itemView);
            nombre = (TextView) itemView.findViewById(R.id.txtvNombres_usuario_item);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_usuario_item);
            Registro = (TextView) itemView.findViewById(R.id.txtvRegistro_admin_menu_usuario_item);
            telefono = (TextView) itemView.findViewById(R.id.txtvTelefono_usuario_item);
            fecha = (TextView) itemView.findViewById(R.id.txtvFecha_usuario_item);
            estado = (TextView) itemView.findViewById(R.id.txtvEstado_usuario_item);

        }
    }

}
