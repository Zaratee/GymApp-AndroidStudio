package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.adaptador.AdaptadorRealizado;
import com.example.carloz.gymapp.items.ItemQueja;
import com.example.carloz.gymapp.items.ItemRealizado;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class InstructorRelizLista extends AppCompatActivity {

    TextView txtvTitulo;
    ArrayList<ItemRealizado> listaClientes;
    RecyclerView recyclerClientes;
    String registroCLiente,estado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructor_reliz_lista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        registroCLiente = getIntent().getStringExtra("REGISTRO");

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvClientesEliminar_InstructorRelizLista);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        conexionBDQuejas();


    }

    private void conexionBDQuejas() {
        String url = "http://thegymlife.online/php/instructor/Entrenador_Lista_Ejercicios_Cliente_Realizado.php?registro="+registroCLiente;
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
                                    JSONArray jsonArray = response.getJSONArray("Realizado");

                                    AdaptadorRealizado adapter = new AdaptadorRealizado(listaClientes);
                                    adapter.contexto= InstructorRelizLista.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        String nombre =clientes.getString("Ejercicio_Nombre");
                                        String Fecha =clientes.getString("Fecha_Realizada");
                                        String repeticiones =clientes.getString("Repeticiones_Totales");
                                        String realizado =clientes.getString("id_Realizado");


                                        listaClientes.add(new ItemRealizado(nombre, Fecha, repeticiones,realizado));
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
                        Toast.makeText(InstructorRelizLista.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(InstructorRelizLista.this);
        x.add(peticion);
    }
}
