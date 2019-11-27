package com.example.carloz.gymapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NutriologoSolicitudAlimentoSolicitud extends AppCompatActivity {

    EditText etxtNombreAlimento, etxtMarcaAlimento, etxtTipoDeAlimento, etxtCantidadAlimento,
    etxtCantidadDeGrasas, etxtCantidadCarboh, etxtCantidadProteinas, etxtCantidadCalorias,
            etxtMedidaTipo;
    Button btnSubir, btnEliminar;
    String stringID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutriologo_solicitud_alimento_solicitud);

        etxtNombreAlimento = (EditText) findViewById(R.id.etxtNombre_NutriologoSolicitudAlimentoSolicitud);
        etxtMarcaAlimento = (EditText) findViewById(R.id.etxtMarca_NutriologoSolicitudAlimentoSolicitud);
        etxtTipoDeAlimento = (EditText) findViewById(R.id.etxtTipo_NutriologoSolicitudAlimentoSolicitud);
        etxtCantidadAlimento = (EditText) findViewById(R.id.etxtCantidad_NutriologoSolicitudAlimentoSolicitud);
        etxtMedidaTipo = (EditText) findViewById(R.id.etxtMedida_NutriologoSolicitudAlimentoSolicitud);
        etxtCantidadDeGrasas = (EditText) findViewById(R.id.etxtGrasas_NutriologoSolicitudAlimentoSolicitud);
        etxtCantidadCarboh = (EditText) findViewById(R.id.etxtCarboh_NutriologoSolicitudAlimentoSolicitud);
        etxtCantidadProteinas = (EditText) findViewById(R.id.etxtProteinas_NutriologoSolicitudAlimentoSolicitud);
        etxtCantidadCalorias = (EditText) findViewById(R.id.etxtCalorias_NutriologoSolicitudAlimentoSolicitud);
        btnSubir = (Button) findViewById(R.id.btnAgregar_NutriologoSolicitudAlimentoSolicitud);
        btnEliminar = (Button) findViewById(R.id.btnEliminar_NutriologoSolicitudAlimentoSolicitud);

        recibirDatos();
        conexionBDSolInfo();
        clickbtnSubir();
        clickbtnEliminar();

    }

    private void clickbtnEliminar() {
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDEliminar();
            }
        });
    }

    private void conexionBDEliminar() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Eliminar_Alimento_Solicitud.php?alimento="+stringID;
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

                                            Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Solicitud Eliminada",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(NutriologoSolicitudAlimentoSolicitud.this,NutriologoSolicitudAlimentoLista.class);
                                            startActivity(intent);
                                            break;
                                        case "Error":
                                            Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Error",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoSolicitudAlimentoSolicitud.this);
        x.add(peticion);

    }

    private void conexionBDSubir() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Modificar_Alimento_Solicitud.php?alimento="+stringID+
        "&nombre="+etxtNombreAlimento.getText().toString().trim()+"&marca="+etxtMarcaAlimento.getText().toString().trim()+
                "&tipo="+etxtTipoDeAlimento.getText().toString().trim()+"&cantidad="+etxtCantidadAlimento.getText().toString().trim()+
                "&medida="+etxtMedidaTipo.getText().toString().trim()+"&grasa="+etxtCantidadDeGrasas.getText().toString().trim()
                +"&carbohidratos="+etxtCantidadCarboh.getText().toString().trim()+"&proteinas="+etxtCantidadProteinas.getText().toString().trim()
                +"&calorias="+etxtCantidadCalorias.getText().toString();
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
                                            Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Solicitud subida",Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(NutriologoSolicitudAlimentoSolicitud.this,NutriologoSolicitudAlimentoLista.class);
                                            startActivity(intent);
                                            finish();
                                            break;
                                        case "ERROR":
                                            String txtNombreAlimento, txtTipoAlimento, txtCantidadAlimento, txtMedidaTiempo, txtGrasas,txtCarbohidratos,
                                                    txtproteinanas, txtCalorias;
                                            txtNombreAlimento = etxtNombreAlimento.getText().toString();
                                            txtTipoAlimento = etxtTipoDeAlimento.getText().toString();
                                            txtCantidadAlimento = etxtCantidadAlimento.getText().toString();
                                            txtMedidaTiempo = etxtMedidaTipo.getText().toString();
                                            txtGrasas = etxtCantidadDeGrasas.getText().toString();
                                            txtCarbohidratos = etxtCantidadCarboh.getText().toString();
                                            txtproteinanas = etxtCantidadProteinas.getText().toString();
                                            txtCalorias = etxtCantidadCalorias.getText().toString();
                                            if (txtNombreAlimento.isEmpty()){
                                                etxtNombreAlimento.setError("Rellenar Nombre");
                                            }
                                            if (txtTipoAlimento.isEmpty()){
                                                etxtTipoDeAlimento.setError("Rellenar Tipo");
                                            }
                                            if (txtCantidadAlimento.isEmpty()){
                                                etxtCantidadAlimento.setError("Rellenar Cantidad");
                                            }
                                            if (txtMedidaTiempo.isEmpty()){
                                                etxtMedidaTipo.setError("Rellenar Medida");
                                            }
                                            if (txtGrasas.isEmpty()){
                                                etxtCantidadDeGrasas.setError("Rellenar Grasa");
                                            }
                                            if (txtCarbohidratos.isEmpty()){
                                                etxtCantidadCarboh.setError("Rellenar Carbohidratos");
                                            }
                                            if (txtproteinanas.isEmpty()){
                                                etxtCantidadProteinas.setError("Rellenar Proteinas");
                                            }
                                            if (txtCalorias.isEmpty()){
                                                etxtCantidadCalorias.setError("Rellenar Calorias");
                                            }

                                            Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Rellene todos los campos",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoSolicitudAlimentoSolicitud.this);
        x.add(peticion);

    }

    private void conexionBDSolInfo() {
        String url = "http://thegymlife.online/php/nutriologo/Nutriologo_Visualizar_Alimento_Solicitud.php?alimento="+stringID;
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
                                            String bdNombre = response.getString("Alimento_Nombre");
                                            String bdMarca = response.getString("Alimento_Marca");
                                            String bdTipo = response.getString("Alimento_Tipo");
                                            String bdCantidad = response.getString("Alimento_Cantidad");
                                            String bdCantidadMedida = response.getString("Alimento_Medida");
                                            String bdGrasas = response.getString("Alimento_Grasas");
                                            String bdCarboh = response.getString("Alimento_Carbohidratos");
                                            String bdProteinas = response.getString("Alimento_Proteinas");
                                            String bdCalorias = response.getString("Alimento_Calorias");

                                            etxtNombreAlimento.setText(bdNombre);
                                            etxtMarcaAlimento.setText(bdMarca);
                                            etxtTipoDeAlimento.setText(bdTipo);
                                            etxtCantidadAlimento.setText(bdCantidad);
                                            etxtMedidaTipo.setText(bdCantidadMedida);
                                            if (bdGrasas.equals("0")){
                                                etxtCantidadDeGrasas.setText("");
                                            }else {
                                                etxtCantidadDeGrasas.setText(bdGrasas);
                                            }

                                            if (bdCarboh.equals("0")){
                                                etxtCantidadCarboh.setText("");
                                            }else {
                                                etxtCantidadCarboh.setText(bdCarboh);
                                            }

                                            if (bdProteinas.equals("0")){
                                                etxtCantidadProteinas.setText("");
                                            }else {
                                                etxtCantidadProteinas.setText(bdProteinas);
                                            }

                                            if (bdCalorias.equals("0")){
                                                etxtCantidadCalorias.setText("");
                                            }else {
                                                etxtCantidadCalorias.setText(bdCalorias);
                                            }


                                            break;
                                        case "Error":
                                            Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Queja no enviada",Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(NutriologoSolicitudAlimentoSolicitud.this,"Error php",Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue x = Volley.newRequestQueue(NutriologoSolicitudAlimentoSolicitud.this);
        x.add(peticion);

    }

    private void recibirDatos() {
        Intent intent= getIntent();
        Bundle bundle = intent.getExtras();
        stringID =(String) bundle.get("ID");

    }

    private void clickbtnSubir() {
        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                conexionBDSubir();
            }
        });
    }

    public void onBackPressed(){
        Intent intent = new Intent(NutriologoSolicitudAlimentoSolicitud.this,NutriologoSolicitudAlimentoLista.class);
        startActivity(intent);
    }

}
