package com.example.carloz.gymapp.adaptador;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.ClienteBuzonQuejas;
import com.example.carloz.gymapp.ClienteCrearQueja;
import com.example.carloz.gymapp.InstructorAsignarEjercicioInfo;
import com.example.carloz.gymapp.MainActivity;
import com.example.carloz.gymapp.R;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemQueja;

import org.json.JSONException;
import org.json.JSONObject;

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
        viewHolderAlimento.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertbox = new AlertDialog.Builder(contexto);
                alertbox.setMessage("¿Estas seguro que deseas eliminar la queja?");
                alertbox.setTitle("Confirmación");

                alertbox.setNegativeButton("Cancelar",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                            }
                        });

                alertbox.setPositiveButton("Eliminar",
                        new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface arg0,
                                                int arg1) {
                                String url = "http://thegymlife.online/php/cliente/Cliente_Eliminar_Queja.php?queja="+listaClientes.get(j).getId();
                                url = url.replaceAll(" ", "%20");

                                JsonObjectRequest peticion = new JsonObjectRequest
                                        (
                                                Request.Method.GET,
                                                url,
                                                null,
                                                new Response.Listener<JSONObject>() {
                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        try {
                                                            String valor = response.getString("Estado");
                                                            switch(valor) {
                                                                case "OK":
                                                                    Toast.makeText(contexto, "Queja eliminada", Toast.LENGTH_SHORT).show();
                                                                    break;
                                                                case "Error":
                                                                    Toast.makeText(contexto,"Queja no enviada",Toast.LENGTH_SHORT).show();
                                                                    break;
                                                            }


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }
                                                    }
                                                }
                                                , new Response.ErrorListener()
                                        {
                                            @Override
                                            public void onErrorResponse(VolleyError error)
                                            {
                                                Toast.makeText(contexto,"Error php",Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                RequestQueue x = Volley.newRequestQueue(contexto);
                                x.add(peticion);

                            }
                        });

                alertbox.show();
            }
        });

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
