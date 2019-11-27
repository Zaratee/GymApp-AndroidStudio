package com.example.carloz.gymapp;

import android.content.Intent;
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
import com.example.carloz.gymapp.adaptador.AdaptadorEditarDieta;
import com.example.carloz.gymapp.items.ItemAlimentoEditar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClienteAlimEditarDiario extends AppCompatActivity {

    ArrayList<ItemAlimentoEditar> listaClientes;
    RecyclerView recyclerClientes;
    TextView txtVTitulo;
    String regCliente, Nombre,TipoAlimento, Marca, Tiempo, Cantidad,CantidadTipo,ID, CantidadAsignada, Lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_alim_editar_diario);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtVTitulo = findViewById(R.id.txtvNoActionTitulo_ClienteAlimEditarDiario);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtVTitulo.setTypeface(Thin);

        listaClientes = new ArrayList<>();
        recyclerClientes = (RecyclerView) findViewById(R.id.listvEditarDieta_ClienteAlimEditarDiario);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        conexionBDAlimentos();
    }

    private void conexionBDAlimentos() {
        String url = "http://thegymlife.online/php/cliente/Cliente_Lista_Alimentos_Diarios.php?registro="+Login.Registro;
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

                                    AdaptadorEditarDieta adapter = new AdaptadorEditarDieta(listaClientes);
                                    adapter.contexto= ClienteAlimEditarDiario.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);

                                        Nombre =clientes.getString("Alimento_Nombre");
                                        Marca =clientes.getString("Alimento_Marca");
                                        Tiempo =clientes.getString("Tipo_Alimento");
                                        Cantidad =clientes.getString("Alimento_Cantidad");
                                        CantidadTipo =clientes.getString("Alimento_Medida");
                                        TipoAlimento =clientes.getString("Alimento_Tipo");
                                        ID =clientes.getString("id_Alimento");
                                        CantidadAsignada = clientes.getString("Cantidad");
                                        Lista = clientes.getString("id_Lista");
                                        int cant = Integer.parseInt(Cantidad)*Integer.parseInt(CantidadAsignada);
                                        listaClientes.add(new ItemAlimentoEditar(Nombre,TipoAlimento, Marca, Tiempo, Cantidad,CantidadTipo,ID,""+cant,Lista));
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
                        Toast.makeText(ClienteAlimEditarDiario.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(ClienteAlimEditarDiario.this);
        x.add(peticion);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,ClienteAlimentacion.class));
    }
}
