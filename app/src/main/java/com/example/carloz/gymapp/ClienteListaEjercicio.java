package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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
import com.example.carloz.gymapp.adaptador.AdaptadorEjercicioSemana;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.items.ItemEjercicioSemana;
import com.example.carloz.gymapp.items.ItemQueja;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClienteListaEjercicio extends AppCompatActivity {

    ArrayList<ItemEjercicioSemana> listaClientes;
    RecyclerView recyclerClientes;
    String nombre, series,repeticiones,peso,dia;
    TextView txtvTitulo;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_lista_ejercicio);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        listaClientes = new ArrayList<>();
        recyclerClientes = (RecyclerView) findViewById(R.id.listvEjercicios_ClienteListaEjercicio);
        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_InstructorBuzonQuejas);
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTitulo.setTypeface(Thin);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        
        conexionBDLista();
    }

    private void conexionBDLista() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Lista_Visualizar_Rutina_Semanal.php?registro="+Login.Registro;
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
                                    JSONArray jsonArray = response.getJSONArray("Ejercicio");

                                    AdaptadorEjercicioSemana adapter = new AdaptadorEjercicioSemana(listaClientes);
                                    adapter.contexto= ClienteListaEjercicio.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        nombre =clientes.getString("Ejercicio_Nombre");
                                        series =clientes.getString("Numero_Series");
                                        repeticiones =clientes.getString("Numero_Repeticiones");
                                        peso =clientes.getString("Numero_Peso");
                                        dia =clientes.getString("Dia_Semana");
                                        switch (dia){
                                            case "1":
                                                dia = "Lunes";
                                                break;
                                            case "2":
                                                dia = "Martes";
                                                break;
                                            case "3":
                                                dia = "Miercoles";
                                                break;
                                            case "4":
                                                dia = "Jueves";
                                                break;
                                            case "5":
                                                dia = "Viernes";
                                                break;
                                            case "6":
                                                dia = "Sabado";
                                                break;
                                            case "7":
                                                dia = "Domingo";
                                                break;
                                        }

                                        listaClientes.add(new ItemEjercicioSemana(nombre, series,repeticiones,peso,dia));
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
                        Toast.makeText(ClienteListaEjercicio.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteListaEjercicio.this);
        x.add(peticion);

    }
}
