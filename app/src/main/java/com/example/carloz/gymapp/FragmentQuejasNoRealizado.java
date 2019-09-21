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
import com.example.carloz.gymapp.adaptador.AdaptadorAdminListaQuejas;
import com.example.carloz.gymapp.items.ItemListaQuejasAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmentQuejasNoRealizado extends Fragment {
    TextView Titulo;
    RecyclerView recyclerClientes;
    ArrayList<ItemListaQuejasAdmin> listaClientes;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragmentrealizado, container, false);
        Titulo = v.findViewById(R.id.txtvNoActionTitulo_fragmentRealizado);
        Typeface Thin = Typeface.createFromAsset(getActivity().getAssets(),"fonts/Roboto-Thin.ttf");
        Titulo.setTypeface(Thin);

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) v.findViewById(R.id.recycler_viewRealizado);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        switch (AdminMenuQuejasCliente.Usuario){
            case "CLIENTE":
                Titulo.setText("Quejas Cliente");
                conexionBDQuejasClienteRealizado();
                break;
            case "INSTRUCTOR":
                conexionBDQuejasInstructorRealizado();
                Titulo.setText("Quejas Instructor");

                break;
            case "NUTRIOLOGO":
                conexionBDQuejasNutriologoRealizado();
                Titulo.setText("Quejas Nutriologo");
                break;
        }



        return v;
    }
    private void conexionBDQuejasInstructorRealizado() {

        listaClientes.clear();
        String url = "http://thegymlife.online/php/admin/Administrador_Lista_Entrenador_Quejas.php";
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

                                    JSONArray jsonArray = response.getJSONArray("Clientes");

                                    AdaptadorAdminListaQuejas adapter = new AdaptadorAdminListaQuejas(listaClientes);
                                    String registro, nombre, quejaRegistro,apellido;
                                    adapter.contexto= getActivity();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        registro =clientes.getString("Entrenador_Registro");
                                        nombre =clientes.getString("Entrenador_Nombre");
                                        apellido =clientes.getString("Entrenador_Apellido");
                                        quejaRegistro =clientes.getString("Queja_id");

                                        listaClientes.add(new ItemListaQuejasAdmin(nombre,registro,apellido,quejaRegistro,"NoRealizado"));
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
                        Toast.makeText(getActivity(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getActivity());
        x.add(peticion);

    }
    private void conexionBDQuejasNutriologoRealizado() {

        listaClientes.clear();
        String url = "http://thegymlife.online/php/admin/Administrador_Lista_Clientes_Quejas_Realizado.php";
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

                                    JSONArray jsonArray = response.getJSONArray("Clientes");

                                    AdaptadorAdminListaQuejas adapter = new AdaptadorAdminListaQuejas(listaClientes);
                                    String registro, nombre, quejaRegistro,apellido;
                                    adapter.contexto= getActivity();
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        registro =clientes.getString("Nutriologo_Registro");
                                        nombre =clientes.getString("Nutriologo_Nombre");
                                        apellido =clientes.getString("Nutriologo_Apellido");
                                        quejaRegistro =clientes.getString("Queja_id");

                                        listaClientes.add(new ItemListaQuejasAdmin(nombre,registro,apellido,quejaRegistro,"NoRealizado"));
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
                        Toast.makeText(getActivity(),"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(getActivity());
        x.add(peticion);

    }
    private void conexionBDQuejasClienteRealizado() {
        {
            listaClientes.clear();
            String url = "http://thegymlife.online/php/admin/Administrador_Lista_Clientes_Quejas.php";
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

                                        JSONArray jsonArray = response.getJSONArray("Clientes");

                                        AdaptadorAdminListaQuejas adapter = new AdaptadorAdminListaQuejas(listaClientes);
                                        String registro, nombre, quejaRegistro,apellido;
                                        adapter.contexto= getActivity();
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            registro =clientes.getString("Cliente_Registro");
                                            nombre =clientes.getString("Cliente_Nombre");
                                            apellido =clientes.getString("Cliente_Apellido");
                                            quejaRegistro =clientes.getString("Queja_id");

                                            listaClientes.add(new ItemListaQuejasAdmin(nombre,registro,apellido,quejaRegistro,"NoRealizado"));
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
                            Toast.makeText(getActivity(),"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(getActivity());
            x.add(peticion);

        }
    }
}
