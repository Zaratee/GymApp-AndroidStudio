package com.example.carloz.gymapp;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.carloz.gymapp.adaptador.AdaptadorAdminListaQuejas;
import com.example.carloz.gymapp.adaptador.AdaptadorInstructorClientesAsignados;
import com.example.carloz.gymapp.items.ItemClienteInstructor;
import com.example.carloz.gymapp.items.ItemListaQuejasAdmin;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AdminMenuQuejasCliente extends AppCompatActivity {

    TextView txtvTitulo;
    ArrayList<ItemListaQuejasAdmin> listaClientes;
    RecyclerView recyclerClientes;
    String registro, nombre, quejaRegistro,apellido;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu_quejas_cliente);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        txtvTitulo = (TextView) findViewById(R.id.txtvNoActionAgregar_AdminMenuQuejasCliente);
        Typeface Thin = Typeface.createFromAsset(getAssets(),"fonts/Roboto-Thin.ttf");
        txtvTitulo.setTypeface(Thin);

        listaClientes = new ArrayList<>();

        recyclerClientes = (RecyclerView) findViewById(R.id.listvClientesEliminar_AdminMenuQuejasCliente);
        recyclerClientes.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));

        conexionBDQuejas();
    }

    private void conexionBDQuejas() {
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

                                        adapter.contexto= AdminMenuQuejasCliente.this;
                                        for (int i =0; i<jsonArray.length();i++){
                                            JSONObject clientes = jsonArray.getJSONObject(i);
                                            registro =clientes.getString("Cliente_Registro");
                                            nombre =clientes.getString("Cliente_Nombre");
                                            apellido =clientes.getString("Cliente_Apellido");
                                            quejaRegistro =clientes.getString("Queja_id");

                                            listaClientes.add(new ItemListaQuejasAdmin(nombre,registro,apellido,quejaRegistro));
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
                            Toast.makeText(AdminMenuQuejasCliente.this,"Error php",Toast.LENGTH_SHORT).show();
                        }
                    });
            RequestQueue x = Volley.newRequestQueue(AdminMenuQuejasCliente.this);
            x.add(peticion);


    }
}
