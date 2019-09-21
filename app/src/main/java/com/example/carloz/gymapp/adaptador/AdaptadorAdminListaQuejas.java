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
import com.example.carloz.gymapp.AdminMenuQuejasCliente;
import com.example.carloz.gymapp.AdminVisualizarQuejaInfo;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.example.carloz.gymapp.items.ItemListaQuejasAdmin;

import java.util.ArrayList;

public class AdaptadorAdminListaQuejas extends RecyclerView.Adapter<AdaptadorAdminListaQuejas.ViewHolderClientes> {

    ArrayList<ItemListaQuejasAdmin> listaQuejas;
    public Context contexto;

    public AdaptadorAdminListaQuejas(ArrayList<ItemListaQuejasAdmin> listaClientes) {
        this.listaQuejas = listaClientes;
    }

    @NonNull
    @Override
    public ViewHolderClientes onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listaquejasadmin,null,false);
        return new ViewHolderClientes(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderClientes viewHolderClientes, int i) {
        final int j = i;
        viewHolderClientes.nombre.setText(listaQuejas.get(i).getNombre()+" "+ listaQuejas.get(i).getApellido());
        viewHolderClientes.registro.setText(listaQuejas.get(i).getRegistro());
        viewHolderClientes.quejaid.setText(listaQuejas.get(i).getQueja());

        String opcion = contexto.getClass().getSimpleName();

        switch (AdminMenuQuejasCliente.Usuario){
            case "CLIENTE":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesClientes/"+listaQuejas.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "INSTRUCTOR":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesInstructor/"+listaQuejas.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
                break;
            case "NUTRIOLOGO":
                Glide.with(contexto).load("http://thegymlife.online/php/fotos/imagenesNutriologo/"+listaQuejas.get(j).getRegistro()+".jpg").apply(RequestOptions.skipMemoryCacheOf(true))
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).error(R.drawable.logonb).into(viewHolderClientes.foto);
        }


        viewHolderClientes.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        Intent intent = new Intent(contexto,AdminVisualizarQuejaInfo.class);
                        intent.putExtra("ID",listaQuejas.get(j).getQueja());
                        intent.putExtra("REGISTRO",listaQuejas.get(j).getRegistro());
                        intent.putExtra("ESTADO",listaQuejas.get(j).getEstado());
                        contexto.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaQuejas.size();
    }

    public class ViewHolderClientes extends RecyclerView.ViewHolder {

        TextView nombre,registro,quejaid;
        LinearLayout linearLayout;
        ImageView foto;

        public ViewHolderClientes(@NonNull View itemView) {
            super(itemView);
            foto = (ImageView) itemView.findViewById(R.id.imgvCliente_listaquejas) ;
            nombre = (TextView) itemView.findViewById(R.id.txtvNombre_listaquejas);
            quejaid = (TextView) itemView.findViewById(R.id.txtvQuejaId_listaquejas);
            registro = (TextView) itemView.findViewById(R.id.txtvRegistro_listaquejas);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.layoutItem_listaquejas);

        }
    }
}
