package com.example.carloz.gymapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorEjeClientAyuda;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.items.ItemEjercicioClienteAyuda;
import com.example.carloz.gymapp.items.ItemQueja;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentAyudaRutina extends Fragment {

    TextView txtvDia;
    ArrayList<ItemEjercicioClienteAyuda> listaClientes;
    RecyclerView recyclerClientes;
    String Nombre, Peso, Repeticiones, Series, Estado, Area, ID,instructor;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.cliente_ejercicio_ayuda_rutina, container, false);

        txtvDia = v.findViewById(R.id.txtvDia_FragmentAyudaRutina);
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
        txtvDia.setTypeface(Thin);

        //Toast.makeText(getContext(), ""+ClienteEjercicio.diaSemana, Toast.LENGTH_SHORT).show();
        //Toast.makeText(getContext(), ""+Login.Registro, Toast.LENGTH_SHORT).show();
        switch (ClienteEjercicio.diaSemana){
            case "1":
                txtvDia.setText("Lunes");
                break;

            case "2":
                txtvDia.setText("Martes");
                break;

            case "3":
                txtvDia.setText("Miercoles");
                break;

            case "4":
                txtvDia.setText("Jueves");
                break;

            case "5":
                txtvDia.setText("Viernes");
                break;

            case "6":
                txtvDia.setText("Sabado");
                break;
            case "7":
                txtvDia.setText("Domingo");
                break;

        }

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) v.findViewById(R.id.listvEjercicios_FragmentAyudaRutina);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        conexionBDEjercicios();
        return v;
    }

    private void conexionBDEjercicios() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Lista_Visualizar_Rutina_Diaria.php?registro="+Login.Registro;
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
                                    //String valor = response.getString("Estado");
                                    JSONArray jsonArray = response.getJSONArray("Rutina");

                                    AdaptadorEjeClientAyuda adapter = new AdaptadorEjeClientAyuda(listaClientes);
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        ID = clientes.getString("id_Rutina");
                                        Nombre  =clientes.getString("Ejercicio_Nombre");
                                        Peso  =clientes.getString("Numero_Peso");
                                        Repeticiones  =clientes.getString("Numero_Repeticiones");
                                        Series  =clientes.getString("Numero_Series");
                                        Estado  =clientes.getString("Estado");
                                        Area  =clientes.getString("Ejercicio_Area");
                                        instructor = clientes.getString("id_Entrenador");

                                        listaClientes.add(new ItemEjercicioClienteAyuda(Nombre, Peso, Repeticiones, Series, Estado, Area, ID, instructor));
                                    }

                                    recyclerClientes.setAdapter(adapter);

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
                        Toast.makeText(getContext(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getContext());
        x.add(peticion);

    }
}