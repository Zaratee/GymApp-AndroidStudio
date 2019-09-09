package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorAdminHistorial;
import com.example.carloz.gymapp.adaptador.AdaptadorQueja;
import com.example.carloz.gymapp.items.ItemQueja;
import com.example.carloz.gymapp.items.ItemUsuariosAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminHistorialClientes extends AppCompatActivity {

    ArrayList<ItemUsuariosAdmin> listaClientes;
    RecyclerView recyclerClientes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_historial_clientes);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        Typeface Condensed = Typeface.createFromAsset(getAssets(),"fonts/RobotoCondensed-Light.ttf");

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvHistorialClientes_AdminHistorialClientes);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        conexionBDUsuarioss();
    }

    private void conexionBDUsuarioss() {
        String url = "http://thegymlife.online/php/admin/Administrador_Lista_Usuarios_Estado.php";
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
                                    JSONArray jsonArray = response.getJSONArray("Clientes");

                                    AdaptadorAdminHistorial adapter = new AdaptadorAdminHistorial(listaClientes);
                                    adapter.contexto= AdminHistorialClientes.this;
                                    for (int i =0; i<jsonArray.length();i++){
                                        JSONObject clientes = jsonArray.getJSONObject(i);
                                        String registro =clientes.getString("Usuario_Registro");
                                        String nombre =clientes.getString("Usuario_Nombre");
                                        String apellido =clientes.getString("Usuario_Apellido");
                                        String telefono =clientes.getString("Usuario_Telefono");
                                        String Fecha =clientes.getString("Usuario_Fecha");
                                        String Estado =clientes.getString("Usuario_Estado");

                                        listaClientes.add(new ItemUsuariosAdmin(registro, nombre, apellido, telefono, Fecha, Estado));
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
                        Toast.makeText(AdminHistorialClientes.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(AdminHistorialClientes.this);
        x.add(peticion);

    }
}
