package com.example.carloz.gymapp;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorEjercicio;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.items.ItemEjercicio;
import com.example.carloz.gymapp.items.ItemQueja;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NutriologoBuzonQuejas extends AppCompatActivity {

    Button btnCrearQueja;
    TextView txtvTitulo;
    ArrayList<ItemQueja> listaClientes;
    RecyclerView recyclerClientes;
    String queja,estado;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_buzon_quejas);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        btnCrearQueja = (Button) findViewById(R.id.btnCrearQueja_NutriologoBuzonQuejas);
        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionTitulo_NutriologoBuzonQuejas);

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvQuejas_NutriologoBuzonQuejas);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));


        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        txtvTitulo.setTypeface(Thin);
        btnCrearQueja.setTypeface(Condensed);
        clicBtnCrearQueja();
        conexionBDQuejas();
    }

    private void conexionBDQuejas() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Lista_Mostrar_Quejas.php?registro="+Login.Registro;
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
                                    JSONArray jsonArray = response.getJSONArray("Quejas");

                                    AdaptadorQueja adapter = new AdaptadorQueja(listaClientes);
                                    adapter.contexto= NutriologoBuzonQuejas.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        queja =clientes.getString("Queja_Descripcion");
                                        estado =clientes.getString("Queja_Tipo");

                                        if (estado.equals("0")){
                                            estado = "No realizado";
                                        }else if (estado.equals("1")) {
                                            estado = "Realizado";
                                        }

                                        listaClientes.add(new ItemQueja(queja,estado));
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
                        Toast.makeText(NutriologoBuzonQuejas.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoBuzonQuejas.this);
        x.add(peticion);
    }

    private void clicBtnCrearQueja() {
        btnCrearQueja.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NutriologoBuzonQuejas.this,NutriologoCrearQueja.class);
                startActivity(intent);
            }
        });
    }
}
