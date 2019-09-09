package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.adaptador.AdaptadorSolicitudAlimento;
import com.example.carloz.gymapp.items.ItemQueja;
import com.example.carloz.gymapp.items.ItemSolicitudesAlimento;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NutriologoSolicitudAlimentoLista extends AppCompatActivity {

    ArrayList<ItemSolicitudesAlimento> listaClientes;
    RecyclerView recyclerClientes;
    String Nombre, Marca, TipoAlimento, Cantidad, CantidadTipo, ID;
    FloatingActionButton  AgregarAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_solicitud_alimento_lista);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        AgregarAlimento = findViewById(R.id.floatingbtnAgregarAlimento_NutriologoSolicitudAlimentoLista);
        listaClientes = new ArrayList<>();
        recyclerClientes = (RecyclerView) findViewById(R.id.listvSolicitudes_NutriologoSolicitudAlimentoLista);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        conexionBDSolicitudes();
        AgregarAlimento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoSolicitudAlimentoLista.this,ClienteSolicitudAlimento.class);
                intent.putExtra("USUARIO","NUTRIOLOGO");
                startActivity(intent);
            }
        });
    }

    private void conexionBDSolicitudes() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Lista_Alimentos_Solicitudes.php";
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
                                    JSONArray jsonArray = response.getJSONArray("Alimentos");

                                    AdaptadorSolicitudAlimento adapter = new AdaptadorSolicitudAlimento(listaClientes);
                                    adapter.contexto= NutriologoSolicitudAlimentoLista.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);

                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        TipoAlimento =clientes.getString("Alimento_Tipo");
                                        Cantidad =clientes.getString("Alimento_Cantidad");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        ID =clientes.getString("Alimento_id");

                                        listaClientes.add(new ItemSolicitudesAlimento(Nombre, Marca, TipoAlimento, Cantidad, CantidadTipo, ID));
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
                        Toast.makeText(NutriologoSolicitudAlimentoLista.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoSolicitudAlimentoLista.this);
        x.add(peticion);

    }

    public void onBackPressed(){
        Intent intent = new Intent(NutriologoSolicitudAlimentoLista.this,NutriologoPerfil.class);
        startActivity(intent);
    }
}
